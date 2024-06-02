package com.uv.routinesappuv.model

import java.io.Serializable

data class Rutina (
    val id: String,
    val nombre_rutina: String,
    val descripcion_rutina: String
) : Serializable