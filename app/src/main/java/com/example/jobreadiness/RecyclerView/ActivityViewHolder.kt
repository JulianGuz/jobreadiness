package com.example.jobreadiness.RecyclerView

import android.annotation.SuppressLint
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.jobreadiness.databinding.ActivityProductItemBinding
import com.example.jobreadiness.response.ItemDetailsResponseItem
import com.example.jobreadiness.views.ItemsActivity
import com.squareup.picasso.Picasso

class ActivityViewHolder(var view: View, var activity: ItemsActivity, var listOfFav: List<String>?) :
    RecyclerView.ViewHolder(view) {

    private val binding = ActivityProductItemBinding.bind(view)

    @SuppressLint("SetTextI18n")
    fun bind(cardPosition: ItemDetailsResponseItem) {
        Picasso.get().load(cardPosition.body.secure_thumbnail).into(binding.IVItemImage)
        binding.itemTitle.text = cardPosition.body.title
        binding.itemPrice.text = "$ ${cardPosition.body.price.toInt().toString()} "
        binding.itemInfo.text = "Cantidad Disponible :${cardPosition.body.available_quantity}"
        if (cardPosition.body.shipping != null) {
            binding.itemEnvio.text = if (cardPosition.body.shipping.free_shipping) {
                "envio Gratis"
            } else {
                "Calcula tu envio"
            }
        }
        if(listOfFav?.contains(cardPosition.body.id) == true){
            binding.icHeart.isSelected=true
        }
        binding.itemEnvio.text = "Calcula tu envio"
        binding.CVItems.setOnClickListener {
            activity.detailItem(cardPosition)
            Toast.makeText(view.context, cardPosition.body.title, Toast.LENGTH_SHORT).show()
        }
        binding.icHeart.setOnClickListener {
            activity.saveFavoriteItem(cardPosition.body.id)
            if (binding.icHeart.isSelected) {
                binding.icHeart.isSelected = false
                println("le di click al corazon vacio")
            } else {
                binding.icHeart.isSelected = true
                //activity.deleteFavoriteItem(cardPosition.body.id)
            }
        }
    }

}
