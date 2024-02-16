package com.example.mobile_volksway.apis

import com.google.gson.JsonObject
import com.example.mobile_volksway.models.Login
import com.example.mobile_volksway.models.Empresa
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
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
}