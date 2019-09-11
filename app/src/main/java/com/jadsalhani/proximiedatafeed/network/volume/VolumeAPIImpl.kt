package com.jadsalhani.proximiedatafeed.network.volume

import com.jadsalhani.proximiedatafeed.domain.volume.Volume
import com.jadsalhani.proximiedatafeed.network.RestAPI
import com.jadsalhani.proximiedatafeed.network.volume.response.GetVolumesAPIResponse
import kotlinx.coroutines.Deferred

class VolumeAPIImpl : RestAPI() {

    private val volumeAPI: VolumeAPI = retrofit.create(
        VolumeAPI::class.java)

    fun getVolumesAsync(searchQuery: String): Deferred<GetVolumesAPIResponse> {
        return volumeAPI.getVolumes(searchQuery)
    }

    fun getVolumeByIDAsync(volumeID: String): Deferred<Volume> {
        return volumeAPI.getVolumeByID(volumeID)
    }
}
