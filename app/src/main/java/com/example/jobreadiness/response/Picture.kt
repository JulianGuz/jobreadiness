package com.example.jobreadiness.response

import java.io.Serializable

data class Picture(
    val id: String,
    val max_size: String,
    val quality: String,
    val secure_url: String,
    val size: String,
    val url: String
): Serializable