package com.uv.routinesappuv.model

import java.io.Serializable

data class Ejercicio (
    val id: String,
    val nombre_ejercicio: String,
    val descripcion_ejercicio: String,
    val equipamento: String,
    val series: Int,
    val repeticiones: Int
): Serializable