package com.example.jobreadiness.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.example.jobreadiness.RecyclerView.ItemAdapter
import com.example.jobreadiness.constants.Constants
import com.example.jobreadiness.databinding.ActivityItemsListBinding
import com.example.jobreadiness.response.ItemDetailsResponseItem
import com.example.jobreadiness.util.ApiClient
import com.example.myapplication.data.api.ProductService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class ItemsActivity : AppCompatActivity(), SearchView.OnQueryTextListener{

    private lateinit var adapter: ItemAdapter
    private lateinit var binding: ActivityItemsListBinding
    private var itemCardsList = mutableListOf<ItemDetailsResponseItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemsListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.SVItems.setOnQueryTextListener(this)
        configView()
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {

        val sharedPref = this.getSharedPreferences(Constants.PREF_ITEMS, Context.MODE_PRIVATE)
        var totalIds=sharedPref.getString(Constants.FAV_ITEMS,"")
        var listofFav=totalIds?.split(",")
        adapter = ItemAdapter(this,itemCardsList,listofFav)
        binding.RVItemCards.adapter = adapter
    }

    private fun configView() {
        binding.SVItems.clearFocus()
        binding.SVItems.visibility=View.GONE
        val items =intent.getStringExtra("ITEMS_LIST")
        makeCallItems(items)
    }

    fun makeCallItems(items: String?) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                println("hello items"+items)
                val call: Response<List<ItemDetailsResponseItem>>? =
                    items?.let {
                        ApiClient.getRetrofit().create(ProductService::class.java)
                            .getProductList(it)
                    }
                val activityResponse: List<ItemDetailsResponseItem>? = call?.body()
                runOnUiThread {
                    if (call != null && call.isSuccessful) {
                        println("items it was succesfull")
                        activityResponse?.let { it1 ->
                            if (!activityResponse.isNullOrEmpty()) {
                                itemCardsList.clear()
                                itemCardsList.addAll(activityResponse)
                                adapter.notifyDataSetChanged()
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
        println(message)


    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    fun detailItem(cardPosition: ItemDetailsResponseItem) {
        val intent = Intent(applicationContext, ItemDetailActivity::class.java)
        intent.putExtra("ITEMS_DETAILS",cardPosition)
        startActivity(intent)
    }

    fun saveFavoriteItem(id: String) {
        val sharedPref = this.getSharedPreferences(Constants.PREF_ITEMS, Context.MODE_PRIVATE)
        val totalIds=sharedPref.getString(Constants.FAV_ITEMS,"")
        val newIds = "$totalIds$id,"
        with (sharedPref.edit()) {
            putString(Constants.FAV_ITEMS, newIds)
            commit()
        }
        println(sharedPref.getString(Constants.FAV_ITEMS,""))
        println(newIds)

    }

    fun deleteFavoriteItem(id: String) {
        val sharedPref = this.getSharedPreferences(Constants.PREF_ITEMS, Context.MODE_PRIVATE)
        sharedPref.edit().remove(Constants.FAV_ITEMS).commit()

    }

    override fun onBackPressed() {
        super.onBackPressed()
        itemCardsList.clear()
    }
}
