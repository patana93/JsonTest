package com.patana93.jsontest

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class MyAdapter(var context: Context, var postList: ArrayList<Post>) :
    RecyclerView.Adapter<MyAdapter.ItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val viewHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_row, parent, false)
        return ItemHolder(viewHolder)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

        val postItem: Post = postList[position]
        val uri = Uri.fromFile(File(postItem.postImagePath!!))

        println("Ciao$uri")
        holder.icons.setImageURI(uri)
        holder.titles.text = postItem.postHeading
        holder.subtitles.text = postItem.postAuthor
    }

    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var icons: ImageView = itemView.findViewById(R.id.cover)
        var titles: TextView = itemView.findViewById(R.id.title_row)
        var subtitles: TextView = itemView.findViewById(R.id.sub_row)
    }

}