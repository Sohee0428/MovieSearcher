package com.example.moviesearcher

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.moviesearcher.data.Item

class RecyclerViewAdapter(val context: Context, val movieList: ArrayList<Item>) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    fun addMovieList(list: List<Item>) {
        movieList.clear()
        movieList.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.raw_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(movieList[position], context)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imageView = itemView.findViewById<ImageView>(R.id.imageView)
        private val title = itemView.findViewById<TextView>(R.id.title)
        private val actor = itemView.findViewById<TextView>(R.id.actor)
        private val director = itemView.findViewById<TextView>(R.id.director)

        fun bindItem(item: Item, context: Context) {
            Glide.with(itemView).load(item.image)
                .apply(RequestOptions().override(300, 450))
                .apply(RequestOptions.centerCropTransform())
                .into(imageView)

            title.text = item.title
            actor.text = "출연 ${item.actor}"
            director.text = "감독 ${item.director}"

            itemView.setOnClickListener {
                val webPage = Uri.parse(item.link)
                val webIntent = Intent(Intent.ACTION_VIEW, webPage)
                itemView.context.startActivity(webIntent)
            }
        }
    }
}