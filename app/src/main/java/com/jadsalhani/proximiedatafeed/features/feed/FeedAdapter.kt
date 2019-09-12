package com.jadsalhani.proximiedatafeed.features.feed

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jadsalhani.proximiedatafeed.R
import com.jadsalhani.proximiedatafeed.domain.volume.Volume
import com.jadsalhani.proximiedatafeed.features.details.DetailsActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.feed_item.view.*

class FeedAdapter(private val myDataset: Array<Volume>) :
    RecyclerView.Adapter<FeedAdapter.VolumeViewHolder>() {

    class VolumeViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val volumeTitle = view.feed_item_title
        val volumeAuthors = view.feed_item_authors
        val volumeThumbnail = view.feed_item_thumbnail_image
        lateinit var volumeID: String

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val intent = Intent(v.context, DetailsActivity::class.java)
            intent.putExtra("volumeID", volumeID)
            v.context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VolumeViewHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.feed_item, parent, false) as View
        return VolumeViewHolder(textView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: VolumeViewHolder, position: Int) {
        holder.volumeID = myDataset[position].id
        holder.volumeTitle.text = myDataset[position].volumeInfo.title

        Picasso.get()
            .load(myDataset[position].volumeInfo.imageLinks.thumbnail)
            .into(holder.volumeThumbnail)

        myDataset[position].volumeInfo.authors?.let {
            holder.volumeAuthors.text = it.joinToString()
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size
}
