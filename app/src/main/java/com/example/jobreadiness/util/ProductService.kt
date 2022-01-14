package com.example.myapplication.data.api

import com.example.jobreadiness.BuildConfig
import com.example.jobreadiness.response.CategorieResponse
import com.example.jobreadiness.response.ItemDescriptionResponse
import com.example.jobreadiness.response.ItemDetailsResponseItem
import com.example.jobreadiness.response.TopResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductService {
    //Function that accepts a COUNTRY_ID(MLA, MLC, MLO..) and a query to predict a possible
    //category as a response, by default the response its an array but we are only using the
    // first value. Limit only defines how many results you ask for.
    @Headers("Authorization: Bearer ${BuildConfig.API_KEY}")
    @GET("sites/{COUNTRY_ID}/domain_discovery/search")
    suspend fun getCategories(@Path("COUNTRY_ID")siteId:String,
                              @Query("limit") limit: Int = 1,
                              @Query("q") searchTerm:String
    ) : Response<ArrayList<CategorieResponse>>


    //Function that takes a COUNTRY_ID(MLA, MLC, MLO..) and a CATEGORY_ID and returns a list of
    //the top 20 (Highlight) products of that category
    @Headers("Authorization: Bearer  ${BuildConfig.API_KEY}")
    @GET("highlights/{COUNTRY_ID}/category/{CATEGORY_ID}")
    suspend fun getHighlight(@Path("COUNTRY_ID")siteId:String,
                             @Path("CATEGORY_ID") categoryId:String) : Response<TopResponse>

    //Function that accepts a list of ids separated by comma and returns a list of products
    @Headers("Authorization: Bearer  ${BuildConfig.API_KEY}")
    @GET("/items")
    suspend fun getProductList(@Query("ids") productIdList: String): Response<List<ItemDetailsResponseItem>>

    //Function that accepts a list of ids separated by comma and returns a list of products
    @Headers("Authorization: Bearer  ${BuildConfig.API_KEY}")
    @GET("/items/{ITEM_ID}/description")
    suspend fun getItemDescription(@Path("ITEM_ID")itemId:String): Response<ItemDescriptionResponse>

    //(Optional!)Function that by getting a product id returns the details of that particular product
    @Headers("Authorization: Bearer  ${BuildConfig.API_KEY}")
    @GET("/products/{PRODUCT_ID}")
    suspend fun getProductById(@Path("PRODUCT_ID")productId:String): Response<String>


}