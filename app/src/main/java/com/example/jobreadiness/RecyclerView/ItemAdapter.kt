package com.example.jobreadiness.RecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jobreadiness.R
import com.example.jobreadiness.response.ItemDetailsResponseItem
import com.example.jobreadiness.views.ItemsActivity

class ItemAdapter(
    var activity: ItemsActivity,
    var itemCards: MutableList<ItemDetailsResponseItem>,
    var listOfFav: List<String>?
) : RecyclerView.Adapter<ActivityViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        val layoutInflater= LayoutInflater.from(parent.context)
        return ActivityViewHolder(layoutInflater.inflate(R.layout.activity_product_item,parent,false),activity,listOfFav)
    }

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
       val cardPosition = itemCards[position]
        holder.bind(cardPosition)
    }

    override fun getItemCount()= itemCards.size

}
