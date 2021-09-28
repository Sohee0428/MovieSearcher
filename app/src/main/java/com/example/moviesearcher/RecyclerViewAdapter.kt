package com.example.moviesearcher

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.moviesearcher.data.Item
import com.example.moviesearcher.databinding.RawItemBinding

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

        val rawItemBinding =
            RawItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(rawItemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(movieList[position])
    }

    class ViewHolder(private val binding: RawItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(item: Item) {

            glide(item)

            binding.item = item

            itemView.setOnClickListener {
                val webPage = Uri.parse(item.link)
                val webIntent = Intent(Intent.ACTION_VIEW, webPage)
                itemView.context.startActivity(webIntent)
            }
        }

        fun glide(item: Item) {
            Glide.with(itemView).load(item.image)
                .apply(RequestOptions().override(300, 450))
                .apply(RequestOptions.centerCropTransform())
                .into(binding.imageView)}
    }
}