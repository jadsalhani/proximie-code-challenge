package com.jadsalhani.proximiedatafeed.features.home.api

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface BooksAPI {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @GET("volumes")
    fun getVolumes(@Query("q") searchQuery: String): Deferred<GetVolumesAPIResponse>
}
