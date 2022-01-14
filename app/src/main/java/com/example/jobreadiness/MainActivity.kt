package com.example.jobreadiness

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import com.example.jobreadiness.databinding.ActivityMainBinding
import com.example.jobreadiness.response.CategorieResponse
import com.example.jobreadiness.response.ItemDetailsResponseItem
import com.example.jobreadiness.response.TopResponse
import com.example.jobreadiness.util.ApiClient
import com.example.myapplication.data.api.ProductService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import android.content.Intent
import android.widget.Toast
import com.example.jobreadiness.views.ItemsActivity


class MainActivity : AppCompatActivity() , SearchView.OnQueryTextListener{

    private lateinit var binding: ActivityMainBinding
    private var items: String = ""
    var cont:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.searchItem.setOnQueryTextListener(this)
        configView()
    }

    private fun configView() {
        with(binding) {
        }
    }

    fun makeCallCategory(searchText: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val call: Response<ArrayList<CategorieResponse>> =
                    ApiClient.getRetrofit().create(ProductService::class.java)
                        .getCategories("MLA", 1, searchText)

                val activityResponse: ArrayList<CategorieResponse>? = call.body()
                runOnUiThread {
                    if (call.isSuccessful && !activityResponse.isNullOrEmpty()) {
                        activityResponse?.get(0).let {
                            println(it?.domainName)
                            if (!activityResponse.isNullOrEmpty()) {
                                println("hola mundo" + it?.categoryId)
                                it?.categoryId?.let { it1 -> makeCallHighLights(it1) }
                            }else
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

    private fun makeCallHighLights(categoryId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val call: Response<TopResponse> =
                    ApiClient.getRetrofit().create(ProductService::class.java)
                        .getHighlight("MLA", categoryId)


                val activityResponse: TopResponse? = call.body()

                runOnUiThread {
                    if (call.isSuccessful) {
                        println("itwas succesfull")
                        activityResponse?.let { it1 ->
                            println(it1?.query_data?.id)
                            if (!activityResponse.content.isNullOrEmpty()) {
                                it1?.content?.forEach {
                                    if (it.type == "ITEM"){
                                        cont =1
                                        items = items + it.id + ","
                                    }
                                }
                                if (cont==1){
                                    val intent = Intent(applicationContext, ItemsActivity::class.java)
                                    intent.putExtra("ITEMS_LIST",items)
                                    items=""
                                    startActivity(intent)
                                }
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

    fun makeCallItems() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                println(items)
                val call: Response<List<ItemDetailsResponseItem>> =
                    ApiClient.getRetrofit().create(ProductService::class.java)
                        .getProductList(items)


                val activityResponse: List<ItemDetailsResponseItem>? = call.body()

                runOnUiThread {
                    if (call.isSuccessful) {
                        println("items it was succesfull")
                        activityResponse?.let { it1 ->
                            //println(it1.get(0).body.title)
                            if (!activityResponse.isNullOrEmpty()) {
                                it1.forEach {
                                    println("tituli del producto")
                                    println(it.body.title)
                                }
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

    private fun showErrorMessage(connectionFail: Boolean = false) {
        var message = "not found"
        if (connectionFail) {
            message = "not internet"
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {

        return if (query.isNullOrEmpty()) {
            false
        }else {
            makeCallCategory(query.lowercase())
            true
        }
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }
}