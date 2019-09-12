package com.jadsalhani.proximiedatafeed.network.volume

import com.jadsalhani.proximiedatafeed.domain.volume.Volume
import com.jadsalhani.proximiedatafeed.network.RestAPI
import com.jadsalhani.proximiedatafeed.network.volume.response.GetVolumesAPIResponse
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody

class VolumeAPIImpl : RestAPI() {

    private val volumeAPI: VolumeAPI = retrofit.create(
        VolumeAPI::class.java)

    fun getVolumesAsync(searchQuery: String): Deferred<GetVolumesAPIResponse> {
        return volumeAPI.getVolumesAsync(searchQuery)
    }

    fun getVolumeByIDAsync(volumeID: String): Deferred<Volume> {
        return volumeAPI.getVolumeByIDAsync(volumeID)
    }

    fun downloadVolumePDFAsync(fileUrl: String): Deferred<ResponseBody> {
        return volumeAPI.downloadVolumePDFAsync(fileUrl)
    }
}
