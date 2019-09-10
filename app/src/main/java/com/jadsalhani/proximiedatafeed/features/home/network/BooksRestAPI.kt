package com.jadsalhani.proximiedatafeed.features.home.network

import com.jadsalhani.proximiedatafeed.common.network.RestAPI
import com.jadsalhani.proximiedatafeed.features.home.api.BooksAPI
import com.jadsalhani.proximiedatafeed.features.home.api.GetVolumesAPIResponse
import kotlinx.coroutines.Deferred

class BooksRestAPI : RestAPI() {

    private val booksAPI: BooksAPI = retrofit.create(BooksAPI::class.java)

    fun getVolumesAsync(searchQuery: String): Deferred<GetVolumesAPIResponse> {
        return booksAPI.getVolumes(searchQuery)
    }
}
