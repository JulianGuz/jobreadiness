package com.example.jobreadiness.response

import java.io.Serializable

data class Shipping(
    val dimensions: Any,
    val free_shipping: Boolean,
    val local_pick_up: Boolean,
    val logistic_type: String,
    val methods: List<Any>,
    val mode: String,
    val store_pick_up: Boolean,
    val tags: List<Any>
) : Serializable