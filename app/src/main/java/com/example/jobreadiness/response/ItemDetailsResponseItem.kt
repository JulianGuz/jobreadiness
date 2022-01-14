package com.example.jobreadiness.response

import java.io.Serializable

data class ItemDetailsResponseItem(
    val body: Body,
    val code: Int
) : Serializable
