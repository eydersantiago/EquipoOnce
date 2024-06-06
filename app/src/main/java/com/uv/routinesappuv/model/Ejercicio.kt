package com.uv.routinesappuv.model

data class Ejercicio (
    val id: Int,
    val nombre_ejercicio: String,
    val descripcion_ejercicio: String,
    val equipamento: String,
    val series: Int,
    val repeticiones: Int,
)