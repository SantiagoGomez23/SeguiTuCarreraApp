package com.example.seguitucarreraapp.data.local.seed

import com.example.seguitucarreraapp.data.local.entity.CareerEntity

object CareerSeed {

    val careers = listOf(
        CareerEntity(
            id = "lic_informatica",
            name = "Lic. en Informática",
            years = 5
        ),
        CareerEntity(
            id = "lic_sistemas",
            name = "Lic. en Sistemas",
            years = 5
        ),
        CareerEntity(
            id = "ing_computacion",
            name = "Ingenieria en Computacion",
            years = 3
        )
    )
}
