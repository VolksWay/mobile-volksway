package com.example.mobile_volksway.views

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mobile_volksway.R
import com.example.mobile_volksway.apis.EndpointInterface
import com.example.mobile_volksway.apis.RetrofitConfig
import com.example.mobile_volksway.databinding.FragmentChecklistBinding
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.*

class ChecklistFragment : Fragment() {

    private var _binding: FragmentChecklistBinding? = null
    private val binding get() = _binding!!

    private val IMAGEM_PNEU_REQUEST_CODE = 123
    private var imagemSelecionadaBitmap: Bitmap? = null // Declare a variável aqui

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChecklistBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val iconeCamera = root.findViewById<ImageView>(R.id.imagem_camera)
        iconeCamera.setOnClickListener {
            mostrarOpcoesEscolhaImagem()
        }

        return root
    }

    private fun mostrarOpcoesEscolhaImagem() {
        val escolherImagemIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        val capturarImagemIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        val escolherImagemTitle = resources.getString(R.string.escolher_imagem)
        val capturarImagemTitle = resources.getString(R.string.capturar_imagem)

        val intentEscolhaImagem = Intent.createChooser(escolherImagemIntent, escolherImagemTitle)
        intentEscolhaImagem.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(capturarImagemIntent))

        startActivityForResult(intentEscolhaImagem, IMAGEM_PNEU_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == IMAGEM_PNEU_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data?.data != null) {
                val imagemSelecionadaUri = data.data
                val inputStream = requireContext().contentResolver.openInputStream(imagemSelecionadaUri!!)
                imagemSelecionadaBitmap = BitmapFactory.decodeStream(inputStream)
            } else if (data?.action == "inline-data") {
                imagemSelecionadaBitmap = data.extras?.get("data") as Bitmap
            }
        }
    }

    private fun enviarImagemParaAnalise(imagem: Bitmap) {
        // Verificar se a imagem foi selecionada
        if (imagemSelecionadaBitmap == null) {
            // Exibir mensagem de erro
            return
        }

        val contentType = "application/octet-stream"
        val subscriptionKey = "4b14454a59cb4397b0c9dee26d28ee7b"

        val file = File(requireContext().cacheDir, "temp_image.png")
        file.createNewFile()

        val outputStream = FileOutputStream(file)
        imagem.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        outputStream.close()

        // Criar partes Multipart para a imagem
        val requestFile = RequestBody.create(MediaType.parse("image/*"), file)
        val imagemPart = MultipartBody.Part.createFormData("imagem", file.name, requestFile)

        /*endpoints.image(imagemPart).enqueue(object : Callback<JsonObject>{
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                // Exibir mensagem de sucesso
                Toast.makeText(requireContext(), "Foto analisada com sucesso!!!", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                // Exibir mensagem de erro
                Toast.makeText(requireContext(), "Erro ao atualizar imagem de Perfil.", Toast.LENGTH_SHORT).show()
            }

        })*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

