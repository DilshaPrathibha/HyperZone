package com.example.hyperzone.ui.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hyperzone.R
import com.example.hyperzone.data.model.Product

class CartAdapter(
    private val items: MutableList<Product>,
    private val onRemove: (Int) -> Unit
) : RecyclerView.Adapter<CartAdapter.VH>() {

    inner class VH(v: View) : RecyclerView.ViewHolder(v) {
        private val img: ImageView = v.findViewById(R.id.img)
        private val name: TextView = v.findViewById(R.id.name)
        private val price: TextView = v.findViewById(R.id.price)
        private val btnRemove: ImageButton = v.findViewById(R.id.btnRemove)

        fun bind(p: Product, pos: Int) {
            img.setImageResource(p.imageRes)
            name.text = p.name
            price.text = itemView.context.getString(R.string.price_format, p.price)
            btnRemove.setOnClickListener { onRemove(pos) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position], position)
    }

    override fun getItemCount() = items.size
}
