package com.jadsalhani.proximiedatafeed.network.volume

import com.jadsalhani.proximiedatafeed.domain.volume.Volume
import com.jadsalhani.proximiedatafeed.network.volume.response.GetVolumesAPIResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface VolumeAPI {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @GET("volumes")
    fun getVolumes(@Query("q") searchQuery: String): Deferred<GetVolumesAPIResponse>

    @GET("volumes/{volumeID}")
    fun getVolumeByID(@Path("volumeID") volumeID: String): Deferred<Volume>
}
