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
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.gson.JsonObject
import com.example.mobile_volksway.R
import com.example.mobile_volksway.apis.EndpointInterface
import com.example.mobile_volksway.apis.RetrofitConfig
import com.example.mobile_volksway.databinding.FragmentChecklistBinding
import com.example.mobile_volksway.models.Checklist
import com.squareup.picasso.Picasso
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Part
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.DecimalFormat

class ChecklistFragment : Fragment() {
    private var _binding: FragmentChecklistBinding? = null
    private val binding get() = _binding!!

    private val clienteRetrofit = RetrofitConfig.obterAzureIA()
    private val clienteRetrofitJava = RetrofitConfig.obterInstanciaRetrofit()

    private val endpointsJava = clienteRetrofitJava.create(EndpointInterface::class.java)
    private val endpoints = clienteRetrofit.create(EndpointInterface::class.java)

    private val IMAGEM_REQUEST_CODE = 123

    private val subscriptionKey = "4b14454a59cb4397b0c9dee26d28ee7b"

    private lateinit var textViewResultado: TextView
    private lateinit var textViewChecklist: TextView
    private lateinit var arCondicionadoCampo: CheckBox
    private lateinit var documentosCampo: CheckBox
    private lateinit var freioCampo: CheckBox
    private lateinit var oleoCampo: CheckBox
    private lateinit var combustivelCampo: CheckBox
    private var idUsuario: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentChecklistBinding.inflate(inflater, container, false)
        textViewResultado = binding.root.findViewById(R.id.textViewResultado)
        textViewChecklist = binding.root.findViewById(R.id.textViewChecklist)
        val root: View = binding.root
        val sharedPreferences = requireContext().getSharedPreferences("idUsuario", Context.MODE_PRIVATE)

        idUsuario = sharedPreferences.getString("idUsuario", "")

        val icone_camera = root.findViewById<ImageView>(R.id.camera)

        arCondicionadoCampo = root.findViewById<CheckBox>(R.id.ckbArCondicionado)

        documentosCampo = root.findViewById<CheckBox>(R.id.ckbDocumentos)

        freioCampo = root.findViewById<CheckBox>(R.id.ckbFreios)

        oleoCampo = root.findViewById<CheckBox>(R.id.cbkOleo)

        combustivelCampo = root.findViewById<CheckBox>(R.id.ckbCombustivel)

        icone_camera.setOnClickListener {
            mostrarOpcoesEscolhaImagem()
        }

        return root
    }

    private fun mostrarOpcoesEscolhaImagem() {
        // Criar Intents para escolher uma imagem da galeria ou capturar uma nova pela câmera
        val escolherImagemIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        val capturarImagemIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        // Obter os títulos para as opções de escolha de imagem
        val escolherImagemTitle = resources.getString(R.string.escolher_imagem)
        val capturarImagemTitle = resources.getString(R.string.capturar_imagem)

        // Criar um Intent Chooser para oferecer opções entre galeria e câmera
        val intentEscolhaImagem = Intent.createChooser(escolherImagemIntent, escolherImagemTitle)
        intentEscolhaImagem.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(capturarImagemIntent))

        // Iniciar a atividade esperando um resultado
        startActivityForResult(intentEscolhaImagem, IMAGEM_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val imagemSelecionada = view?.findViewById<ImageView>(R.id.camera)

        if (requestCode == IMAGEM_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data?.data != null) {
                val imagemSelecionadaUri = data.data

                val inputStream: InputStream? = requireContext().contentResolver.openInputStream(imagemSelecionadaUri!!)

                val imagemSelecionadaBitmap = BitmapFactory.decodeStream(inputStream)

                imagemSelecionada?.setImageURI(imagemSelecionadaUri)

                analisarImagem(imagemSelecionadaBitmap)

            } else if (data?.action == "inline-data") {
                // Imagem capturada pela câmera
                val imagemCapturada = data.extras?.get("data") as Bitmap
                imagemSelecionada?.setImageBitmap(imagemCapturada)

                analisarImagem(imagemCapturada)
            }
        }
    }

    private fun realizarChecklist(imagem: Bitmap, qualidade: String){
        var combustivel: Boolean

        if(combustivelCampo.isChecked){
            combustivel = true
        } else {
            combustivel = false
        }

        var arCondicionado: Boolean

        if(arCondicionadoCampo.isChecked){
            arCondicionado = true
        } else {
            arCondicionado = false
        }

        var documentos: Boolean

        if(documentosCampo.isChecked){
            documentos = true
        } else {
            documentos = false
        }

        var freio: Boolean

        if(freioCampo.isChecked){
            freio = true
        } else {
            freio = false
        }

        var oleo: Boolean
        if(oleoCampo.isChecked){
            oleo = true
        } else {
            oleo = false
        }

        val file = File(requireContext().cacheDir, "temp_image.png")
        file.createNewFile()

        // Salvar a imagem Bitmap no arquivo temporário
        val outputStream = FileOutputStream(file)
        imagem.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        outputStream.close()

        // Criar partes Multipart para a imagem
        val requestFile = RequestBody.create(MediaType.parse("image/*"), file)
        val imagemPart = MultipartBody.Part.createFormData("foto_pneu", file.name, requestFile)

        endpointsJava.cadastrarChecklist(imagemPart, freio = RequestBody.create(MediaType.parse("text/plain"), freio.toString()), combustivel = RequestBody.create(MediaType.parse("text/plain"), combustivel.toString()), oleo = RequestBody.create(MediaType.parse("text/plain"), oleo.toString()), ar_condicionado =  RequestBody.create(MediaType.parse("text/plain"), arCondicionado.toString()), id_usuario =  RequestBody.create(MediaType.parse("text/plain"), idUsuario.toString()), estado_pneu =  RequestBody.create(MediaType.parse("text/plain"), qualidade))
            .enqueue(object : Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if (response.isSuccessful) {
                        Toast.makeText(requireContext(), "Funcionou", Toast.LENGTH_SHORT).show()

                        textViewChecklist.text = "Checklist Feito"
                        textViewChecklist.visibility = View.VISIBLE
                    } else {
                        // Se a resposta não for bem-sucedida, exibir uma mensagem de erro
                        Toast.makeText(requireContext(), "Erro ao cadastrar", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    // Exibir mensagem de erro
                    Toast.makeText(requireContext(), "Erro ao analisar a imagem.", Toast.LENGTH_SHORT).show()
                }
            })
    }


    private fun analisarImagem(imagem: Bitmap) {
        // Criar um arquivo temporário para armazenar a imagem
        val file = File(requireContext().cacheDir, "temp_image.png")
        file.createNewFile()

        // Salvar a imagem Bitmap no arquivo temporário
        val outputStream = FileOutputStream(file)
        imagem.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        outputStream.close()

        // Criar partes Multipart para a imagem
        val requestFile = RequestBody.create(MediaType.parse("image/*"), file)
        val imagemPart = MultipartBody.Part.createFormData("imagem", file.name, requestFile)

        endpoints.analisarImagemPneu(predictionKey = subscriptionKey, imagemPart)
            .enqueue(object : Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if (response.isSuccessful) {
                        val predictions = response.body()?.getAsJsonArray("predictions")
                        if (predictions != null) {
                            var probabilidadeBoa = 0f
                            var probabilidadeRuim = 0f

                            for (prediction in predictions) {
                                val probability = prediction.asJsonObject.get("probability").asFloat
                                val tagName = prediction.asJsonObject.get("tagName").asString

                                if (tagName == "Bom") {
                                    probabilidadeBoa = probability
                                } else if (tagName == "Ruim") {
                                    probabilidadeRuim = probability
                                }
                            }
                            val formato = DecimalFormat("#.##")
                            val probabilidadeConvertidaBoa = formato.format(probabilidadeBoa * 100);
                            val probabilidadeConvertidaRuim = formato.format(probabilidadeRuim * 100);
                            val mensagem: String = when {
                                probabilidadeBoa >= 0.8f -> "Foto boa com $probabilidadeConvertidaBoa% de certeza."
                                probabilidadeRuim >= 0.8f -> "Foto ruim com $probabilidadeConvertidaRuim% de certeza."
                                probabilidadeBoa < 0.7f -> "Não é possível concluir, probabilidade de ser boa é de $probabilidadeConvertidaBoa%."
                                else -> "Não é possível concluir, probabilidade de ser boa é de $probabilidadeConvertidaBoa%."
                            }

                            val qualidade: String = when {
                                probabilidadeBoa >= 0.8f -> "Bom"
                                probabilidadeRuim >= 0.8f -> "Ruim"
                                probabilidadeBoa < 0.7f -> "Indeterminado"
                                else -> "Indeterminado"
                            }

                            textViewResultado.text = mensagem
                            textViewResultado.visibility = View.VISIBLE
                            realizarChecklist(imagem, qualidade)
                            //Toast.makeText(requireContext(), mensagem, Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        // Se a resposta não for bem-sucedida, exibir uma mensagem de erro
                        Toast.makeText(requireContext(), "Erro ao obter as previsões", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    // Exibir mensagem de erro
                    Toast.makeText(requireContext(), "Erro ao analisar a imagem.", Toast.LENGTH_SHORT).show()
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}