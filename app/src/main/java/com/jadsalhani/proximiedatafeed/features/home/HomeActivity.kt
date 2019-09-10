package com.jadsalhani.proximiedatafeed.features.home

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jadsalhani.proximiedatafeed.R
import com.jadsalhani.proximiedatafeed.features.home.models.Volume
import com.jadsalhani.proximiedatafeed.features.home.network.BooksRestAPI
import com.jadsalhani.proximiedatafeed.features.home.view.VolumeAdapter
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {
    private val api: BooksRestAPI = BooksRestAPI()

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var searchQuery: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        viewManager = LinearLayoutManager(this)

        // Verify the action and get the query
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                searchQuery = query
            }
        }

        GlobalScope.launch(Dispatchers.Main) {
            val volumes = getVolumes(searchQuery)

            if (volumes != null) {
                viewAdapter = VolumeAdapter(volumes)

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

    private suspend fun getVolumes(searchQuery: String): Array<Volume>? {
        try {
            val result = api.getVolumesAsync(searchQuery).await()
            val categories = result.items
            return categories
        } catch (e: Throwable) {
            Log.e("API ERROR", e.localizedMessage!!)
        }

        return null
    }
}
