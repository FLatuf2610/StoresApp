package com.example.myapplication.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.data.room.entities.StoreEntity
import com.example.myapplication.databinding.ItemStoresBinding

class StoresAdapter(private var stores:List<StoreEntity>,
                    private val onClickListener :(StoreEntity) -> Unit)
    :RecyclerView.Adapter<StoresAdapter.ViewHolder>() {
    private lateinit var _context:Context

    inner class ViewHolder(view:View): RecyclerView.ViewHolder(view){
        private val binding = ItemStoresBinding.bind(view)
        fun render(store: StoreEntity){
            binding.tvStoreTitle.text = store.name
            Glide.with(_context)
                .load(store.image)
                .centerCrop()
                .error(R.drawable.baseline_image_24)
                .centerCrop()
                .into(binding.image)
            itemView.setOnClickListener { onClickListener(store) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        _context = parent.context
        val view = LayoutInflater.from(_context).inflate(R.layout.item_stores, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val store = stores[position]
        holder.render(store)
    }

    override fun getItemCount(): Int = stores.size

    @SuppressLint("NotifyDataSetChanged")
    fun subscribeToFlow(newList: List<StoreEntity>){
        stores = newList
        notifyDataSetChanged()
    }
}