package com.jadsalhani.proximiedatafeed.network.volume

import com.jadsalhani.proximiedatafeed.domain.volume.Volume
import com.jadsalhani.proximiedatafeed.network.volume.response.GetVolumesAPIResponse
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface VolumeAPI {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @GET("volumes")
    fun getVolumesAsync(@Query("q") searchQuery: String): Deferred<GetVolumesAPIResponse>

    @GET("volumes/{volumeID}")
    fun getVolumeByIDAsync(@Path("volumeID") volumeID: String): Deferred<Volume>

    @GET
    fun downloadVolumePDFAsync(@Url fileUrl: String): Deferred<ResponseBody>
}
