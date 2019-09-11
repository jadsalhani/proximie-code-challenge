package com.jadsalhani.proximiedatafeed.network.volume.response

import com.jadsalhani.proximiedatafeed.domain.volume.Volume

class GetVolumesAPIResponse(
    val kind: String,
    val totalItems: Int,
    val items: Array<Volume>
)
