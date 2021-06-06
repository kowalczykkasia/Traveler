package com.example.traveler.adapters

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.traveler.PhotoItemDto
import com.example.traveler.R
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.image_item.view.*

class PhotosAdapter(private val listener: (Pair<PhotoItemDto, Bitmap?>) -> Unit) : ListAdapter<Pair<PhotoItemDto, Bitmap?>, PhotosAdapter.PhotosViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosViewHolder =
        PhotosViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.image_item, parent, false)
        )

    public override fun getItem(position: Int): Pair<PhotoItemDto, Bitmap?> {
        return super.getItem(position)
    }

    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) = holder.bind(getItem(position), listener)

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Pair<PhotoItemDto, Bitmap?>>() {

            override fun areItemsTheSame(
                oldItem: Pair<PhotoItemDto, Bitmap?>, newItem: Pair<PhotoItemDto, Bitmap?>
            ): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(
                oldItem: Pair<PhotoItemDto, Bitmap?>, newItem: Pair<PhotoItemDto, Bitmap?>
            ): Boolean =
                oldItem == newItem
        }
    }

    class PhotosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(photoItemDto: Pair<PhotoItemDto, Bitmap?>, listener: (Pair<PhotoItemDto, Bitmap?>) -> Unit) {
            itemView.apply {
                ivPhoto.setImageBitmap(photoItemDto.second)
                setOnClickListener { listener(photoItemDto) }
            }
        }
    }
}