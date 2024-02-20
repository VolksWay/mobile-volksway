package com.example.mobile_volksway.models

import java.util.*

class Propaganda (
    val id: UUID,
    val usuario: String,
    val titulo: String,
    val url: String,
    val descricao: String,
    val img: String,
    val publico_alvo: String,
    val data_limitel: Date,
    val preco: String,
    val importancia: String,
    val nome_tipo_propaganda: String
)
