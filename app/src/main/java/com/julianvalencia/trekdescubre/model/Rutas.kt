package com.julianvalencia.trekdescubre.model

data class Rutas(
    var id: String? = null,
    var nombre: String? = null,
    var ubicacion: String? = null,
    var distancia: String? = null,
    var seguridad: String? = null,
    var dificultad: String? = null,
    var descripcion: String? = null,
    var urlpicture: String? = null
)
