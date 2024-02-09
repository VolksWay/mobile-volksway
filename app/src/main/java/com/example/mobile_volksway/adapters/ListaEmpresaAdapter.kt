package com.example.mobile_volksway.adapters
class ListaEmpresaAdapter {
}
/*
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_volksway.R
import com.example.mobile_volksway.models.Empresa

class ListaEmpresaAdapter (
    private val context: Context,
    private val listaEmpresas: List<Empresa>
) : RecyclerView.Adapter<ListaEmpresaAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        //Essa função é responsável por chamar e atribuir valores para as views do item da RecyclerView
        fun vincularDadosNoItem(empresa: Empresa) {
      //      val razao_socialEmpresa = itemView.findViewById<TextView>(R.id.razao_socialEmpresa)
      //      razao_socialEmpresa.text = empresa.razao_social

      //      val cidadeEmpresa = itemView.findViewById<TextView>(R.id.cidadeEmpresa)
        //    cidadeEmpresa.text = empresa.cidade

          //  val cnpjEmpresa = itemView.findViewById<TextView>(R.id.cnpjEmpresa)
          //  cnpjEmpresa.text = empresa.cnpj
        }
    }

   // override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaEmpresaAdapter.ViewHolder {
    //    val inflater = LayoutInflater.from(context);

      //  val view = inflater.inflate(R.layout.fragment_Empresa, parent, false)

      //  return ViewHolder(view)
   // }

    override fun onBindViewHolder(holder: ListaEmpresaAdapter.ViewHolder, position: Int) {
        val itemEmpresa = listaEmpresas[position]

        holder.vincularDadosNoItem(itemEmpresa)
    }

    override fun getItemCount(): Int {
        return listaEmpresas.size
    }

}*/