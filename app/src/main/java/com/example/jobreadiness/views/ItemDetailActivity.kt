package com.example.jobreadiness.views

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.jobreadiness.databinding.ActivityItemDetailsBinding
import com.example.jobreadiness.response.ItemDescriptionResponse
import com.example.jobreadiness.response.ItemDetailsResponseItem
import com.example.jobreadiness.response.TopResponse
import com.example.jobreadiness.util.ApiClient
import com.example.myapplication.data.api.ProductService
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class ItemDetailActivity: AppCompatActivity() {
    private lateinit var binding: ActivityItemDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUp()
    }

    private fun setUp() {
        val itemDetails =intent.getSerializableExtra("ITEMS_DETAILS") as ItemDetailsResponseItem
        getProductDescription(itemDetails)
    }

    private fun getProductDescription(itemDetails: ItemDetailsResponseItem) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val call: Response<ItemDescriptionResponse> =
                    ApiClient.getRetrofit().create(ProductService::class.java)
                        .getItemDescription(itemDetails.body.id)
                val activityResponse: ItemDescriptionResponse? = call.body()
                runOnUiThread {
                    if (call.isSuccessful) {
                        println("it description was succesfull")
                        activityResponse?.let {
                            if (activityResponse.plain_text.isNotEmpty()) {
                                println(activityResponse.plain_text)
                                configView(activityResponse.plain_text,itemDetails)
                            } else
                                showErrorMessage()
                        }
                    } else
                        showErrorMessage()
                }
            } catch (e: IOException) {
                showErrorMessage(connectionFail = true)
            }
        }
    }

    private fun configView(plainText: String, itemDetails: ItemDetailsResponseItem) {
        with(binding){
            Picasso.get().load(itemDetails.body.secure_thumbnail).into(binding.IVDetailImage)
            TVCondition.text = itemDetails.body.condition
            TVSold.text = itemDetails.body.sold_quantity.toString()+ " vendidos"
            TVDetailTittle.text= itemDetails.body.title
            TVDetailDescription.text=plainText
            TVDetailPrice.text="$ "+itemDetails.body.price.toString()
            TVDetailStockValue.text = "1 of "+itemDetails.body.available_quantity
        }

    }

    private fun showErrorMessage(connectionFail: Boolean = false) {
        var message = "not found"
        if (connectionFail) {
            message = "not internet"
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}