package com.example.jobreadiness.response

import java.io.Serializable

data class Body(
    val accepts_mercadopago: Boolean,
    val available_quantity: Int,
    val base_price: Double,
    val buying_mode: String,
    val category_id: String,
    val condition: String,
    val domain_id: String,
    val id: String,
    val initial_quantity: Int,
    val original_price: Int,
    val permalink: String,
    val pictures: List<Picture>,
    val price: Double,
    val secure_thumbnail: String,
    val shipping: Shipping,
    val site_id: String,
    val sold_quantity: Int,
    val thumbnail: String,
    val title: String,
    val warranty: String
):Serializable {}