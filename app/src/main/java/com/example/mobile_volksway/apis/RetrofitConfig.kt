package com.example.mobile_volksway.apis

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitConfig {
    companion object{

        fun obterInstanciaRetrofit(url: String = "http://172.16.24.173:8099/") : Retrofit{

            return Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        fun obterAzureIA(url: String = "https://ia-classificador-pneu.cognitiveservices.azure.com/customvision/v3.0/Prediction/dd721a4a-f361-4b29-9e69-406c954a468c/classify/iterations/treinamento-pneu-v1/") : Retrofit{
            return Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

    }
}