package com.example.homework5

import java.io.Serializable

class CarData(val carOwnerName: String,
              val carModelName: String,
              val carGosNumber: String,
              val carImage: Int) : Serializable {
    private val serialVersionUID = 1L
}