package com.jadsalhani.proximiedatafeed.features.home.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jadsalhani.proximiedatafeed.R
import com.jadsalhani.proximiedatafeed.features.home.models.Volume
import kotlinx.android.synthetic.main.volume_item.view.*

class VolumeAdapter(private val myDataset: Array<Volume>) :
    RecyclerView.Adapter<VolumeAdapter.VolumeViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class VolumeViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        // Holds the TextView that will add each animal to
        val volumeTitle = view.volume_title
        val volumeAuthors = view.volume_authors

        // 3
        init {
            view.setOnClickListener(this)
        }

        // 4
        override fun onClick(v: View) {
            Log.d("RecyclerView", "CLICK!")
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VolumeViewHolder {
        // create a new view
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.volume_item, parent, false) as View
        // set the view's size, margins, paddings and layout parameters
        return VolumeViewHolder(textView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: VolumeViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.volumeTitle.text = myDataset[position].volumeInfo.title
        if(myDataset[position].volumeInfo.authors != null) {
            holder.volumeAuthors.text = myDataset[position].volumeInfo.authors?.joinToString()
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size
}
