package com.jadsalhani.proximiedatafeed.features.feed

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jadsalhani.proximiedatafeed.R
import com.jadsalhani.proximiedatafeed.domain.volume.Volume
import com.jadsalhani.proximiedatafeed.network.volume.VolumeAPIImpl
import kotlinx.android.synthetic.main.activity_feed.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FeedActivity : AppCompatActivity() {
    private val apiImpl: VolumeAPIImpl =
        VolumeAPIImpl()

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var searchQuery: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        viewManager = LinearLayoutManager(this)

        handleSearch()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                onSearchRequested()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun handleSearch() {
        // Verify the action and get the query
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                searchQuery = query
            }
        }

        if (searchQuery.isEmpty()) {
            return
        }

        searchVolumes()
    }

    private fun searchVolumes() {
        GlobalScope.launch(Dispatchers.Main) {

            home_progress_bar.visibility = View.VISIBLE

            val volumes = getVolumes(searchQuery)

            if (volumes != null) {
                viewAdapter = FeedAdapter(volumes)

                home_progress_bar.visibility = View.GONE

                recyclerView = home_volumes_recycler_view.apply {
                    // use this setting to improve performance if you know that changes
                    // in content do not change the layout size of the RecyclerView
                    setHasFixedSize(true)

                    // use a linear layout manager
                    layoutManager = viewManager

                    // specify an viewAdapter (see also next example)
                    adapter = viewAdapter
                }
            }
        }
    }

    private suspend fun getVolumes(searchQuery: String): Array<Volume>? {
        try {
            val result = apiImpl.getVolumesAsync(searchQuery).await()
            return result.items
        } catch (e: Throwable) {
            Log.e("API ERROR", e.localizedMessage!!)
        }
        return null
    }
}
