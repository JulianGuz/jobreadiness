package com.example.jobreadiness.response

import com.google.gson.annotations.SerializedName

data class CategorieResponse(

    @SerializedName("domain_id")
    var domainId: String,
    @SerializedName("domain_name")
    var domainName: String,
    @SerializedName("category_id")
    var categoryId: String,
    @SerializedName("category_name")
    var categoryName: String,
    @SerializedName("attributes")
    var attributes: ArrayList<String>
)