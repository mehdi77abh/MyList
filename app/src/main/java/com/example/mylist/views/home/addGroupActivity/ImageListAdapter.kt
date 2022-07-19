package com.example.mylist.views.home.addGroupActivity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mylist.R
import com.example.mylist.tools.getDrawables
import kotlinx.android.synthetic.main.image_item.view.*

class ImageListAdapter(val context: Context,val imageClickedEventListener: ImageClickedEventListener) : RecyclerView.Adapter<ImageListAdapter.ImageViewHolder>() {
    val imageList:List<Int> = getDrawables()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder =
        ImageViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.image_item, parent, false))

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bindImage(imageList[position])
    }

    override fun getItemCount(): Int =imageList.size


    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindImage(color:Int){
            val imageView = itemView.imageItemView
            imageView.setImageDrawable(ContextCompat.getDrawable(context,color))
            itemView.setOnClickListener {
                imageClickedEventListener.imageClicked(color)
            }
        }
    }

    interface ImageClickedEventListener{
        fun imageClicked(image: Int)
    }

}