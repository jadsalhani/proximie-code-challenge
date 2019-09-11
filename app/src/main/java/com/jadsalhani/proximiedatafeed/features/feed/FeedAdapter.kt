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

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class VolumeViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        // Holds the TextView that will add each animal to
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

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VolumeViewHolder {
        // create a new view
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.feed_item, parent, false) as View
        // set the view's size, margins, paddings and layout parameters
        return VolumeViewHolder(textView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: VolumeViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.volumeID = myDataset[position].id
        holder.volumeTitle.text = myDataset[position].volumeInfo.title

        Picasso.get().load(myDataset[position].volumeInfo.imageLinks.thumbnail).into(holder.volumeThumbnail)

        if (myDataset[position].volumeInfo.authors != null) {
            holder.volumeAuthors.text = myDataset[position].volumeInfo.authors?.joinToString()
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size
}
