package com.example.practico_1.ui.Models

import android.graphics.Color
import java.io.Serializable

class Person (
    var id: Int,
    var titulo: String,
    var description: String,
    var estado: String,
    var color: Int = 0xFFFFFFFF.toInt()
): Serializable