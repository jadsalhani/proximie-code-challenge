package com.jadsalhani.proximiedatafeed.features.details

import android.os.Bundle
import android.text.Html
import android.text.Html.FROM_HTML_MODE_COMPACT
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.jadsalhani.proximiedatafeed.R
import com.jadsalhani.proximiedatafeed.domain.volume.Volume
import com.jadsalhani.proximiedatafeed.network.volume.VolumeAPIImpl
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailsActivity : AppCompatActivity() {

    private val apiImpl: VolumeAPIImpl =
        VolumeAPIImpl()
    lateinit var volumeID: String
    var volume: Volume? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        intent.getStringExtra("volumeID")?.also { id ->
            volumeID = id
        }

        GlobalScope.launch(Dispatchers.Main) {

            details_progress_bar.visibility = View.VISIBLE

            volume = getVolumeDetails(volumeID)

            if (volume != null) {
                details_progress_bar.visibility = View.GONE

                volume_title_text.text = volume?.volumeInfo?.title

                volume?.volumeInfo?.imageLinks?.small?.let {
                    Picasso.get().load(it).into(volume_thumbnail_image)
                }

                val publishingInfo = volume?.volumeInfo?.publisher + " - " + volume?.volumeInfo?.publishedDate
                volume_publisher_text.text = publishingInfo
                volume_average_rating_text.text = volume?.volumeInfo?.averageRating.toString()

                // fromHTML needed since description returns in HTML format to be displayed as is

                if (!volume?.volumeInfo?.description.isNullOrEmpty()) {
                    volume_description_text.text = Html.fromHtml(volume?.volumeInfo?.description, FROM_HTML_MODE_COMPACT)
                }

                if (volume?.volumeInfo?.authors != null) {
                    volume_authors_text.text = volume?.volumeInfo?.authors?.joinToString()
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private suspend fun getVolumeDetails(volumeID: String): Volume? {
        try {
            val result = apiImpl.getVolumeByIDAsync(volumeID).await()
            return result
        } catch (e: Throwable) {
            Log.e("API ERROR", e.localizedMessage!!)
        }
        return null
    }
}
