package com.jonathankalonga.jokaltemplaite.models

import androidx.annotation.DrawableRes
import com.jonathankalonga.jokaltemplaite.R

data class Shop(
    val name : String,
    val category : String,
    val adresse: String,
    val city : String,
    val livrateTIme : String,
    val rating: String,
    val description :String,
    val shoes : List<Product>
)

data class Product(
    @DrawableRes val image : Int,
    val name: String,
    val price: Double,
    val description: String )

val shoesProduct = Shop(
    name = "Lina boutique",
    category = "Habillement",
    adresse = "kasangulu n5,kabuya",
    city = "Lubumbashi",
    livrateTIme = "12H",
    rating= "4.9",
    description = "Boutique d'habillement chic et class, la haute qualitE",
    shoes = listOf(
        Product(R.drawable.all_star, "All Star",45.00, "chaussure de qualitE"),
        Product(R.drawable.jordan, "Air Jordan",65.00, "chaussure de qualitE"),
        Product(R.drawable.tallon, "Tallon Dame",50.00, "chaussure de qualitE"),
        Product(R.drawable.pantouf, "Pantouffle",35.00, "chaussure de qualitE"),
        Product(R.drawable.soulier, "Soulier Homme",100.00, "chaussure de qualitE"),
        Product(R.drawable.all_star, "All form",98.00, "chaussure de qualitE"),
        Product(R.drawable.nike, "Nike",85.00, "chaussure de qualitE")
    )
)

