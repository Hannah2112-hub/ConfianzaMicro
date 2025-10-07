package com.example.confianzamicro.ui.menu

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.confianzamicro.R

class MenuAdapter(private val ctx: Context, private val data: List<MenuOption>) :
    RecyclerView.Adapter<MenuAdapter.VH>() {

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val icon: ImageView = v.findViewById(R.id.imgIcon)
        val title: TextView = v.findViewById(R.id.txtTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_menu, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val m = data[position]
        holder.icon.setImageResource(m.iconRes)
        holder.title.text = m.title
        holder.itemView.setOnClickListener {
            val intent = Intent(ctx, m.target.java)
            ctx.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = data.size
}
