package com.example.moviesearcher

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class RecyclerViewAdapter(val homefeed: Homefeed): RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return homefeed.item.count()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.raw_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerViewAdapter.ViewHolder, position: Int) {
        holder.bindItem(homefeed.item.get(position))
    }

    class ViewHolder( val view: View): RecyclerView.ViewHolder(view){

        fun bindItem(data: Item){
            Glide.with(view.context).load(data.image)
                .apply(RequestOptions().override(300, 450))
                .apply(RequestOptions.centerCropTransform())
                .into(view.imageview)

            itemView.title.text = data.title
            itemView.actor.text = "출연 ${data.actor}"
            itemView.director.text = "감독 ${data.director}"

            itemView.setOnClickListener {
                val webpage = Uri.parse("${data.link}")
                val webIntent = Intent(Intent.ACTION_VIEW, webpage)
                view.getContext().startActivity(webIntent)
            }
        }
    }

}