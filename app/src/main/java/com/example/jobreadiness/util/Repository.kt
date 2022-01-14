package com.example.jobreadiness.util

import com.example.myapplication.data.api.ProductService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Repository {

    private var myInterface: ApiClient? = null
    private val gitHubService: ProductService? = null



     fun getProduct(){
        //val retrofit = Retrofit.Builder().baseUrl("").addConverterFactory(GsonConverterFactory.create())
          //  .build()

        //val service = retrofit.create(ProductService::class.java)
       //service.getCategories("",1,"")

    }
}