package com.example.mobile_volksway.apis

import com.google.gson.JsonObject
import com.example.mobile_volksway.models.Login
import com.example.mobile_volksway.models.Empresa
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import java.util.UUID

interface EndpointInterface {

    @POST("login")
    fun login(@Body usuario: Login): Call<JsonObject>

    @GET("usuarios/{idUsuario}")
    fun buscarUsuarioPorID(@Path(value = "idUsuario", encoded = true) idUsuario: UUID): Call<JsonObject>

    @GET("empresa")
    fun listarempresa(): Call<List<Empresa>>

    @GET("empresas/{idEmpresa}")
    fun buscarEmpresaPorID(@Path(value = "idEmpresa", encoded = true) idEmpresa: UUID): Call<JsonObject>

    @Multipart
    @PUT("usuarios/editarImagem/{idUsuario}")
    fun editarImagemUsuario(
        @Part imagem: MultipartBody.Part,
        @Path(value = "idUsuario", encoded = true) idUsuario: UUID
    ) : Call<JsonObject>

    @Multipart
    @POST("image")
    fun analisarImagemPneu(
        @Header("Prediction-Key") predictionKey: String,
        @Part imagem: MultipartBody.Part,
    ) : Call<JsonObject>


    @Multipart
    @POST("checklists")
    fun cadastrarChecklist(
        @Part foto_pneu: MultipartBody.Part,
        @Part("freio") freio: RequestBody,
        @Part("combustivel") combustivel: RequestBody,
        @Part("oleo") oleo: RequestBody,
        @Part("ar_condicionado") ar_condicionado: RequestBody,
        @Part("id_usuario") id_usuario: RequestBody,
        @Part("estado_pneu") estado_pneu: RequestBody
    ) : Call<JsonObject>
}