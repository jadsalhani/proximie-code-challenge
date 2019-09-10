package com.jadsalhani.proximiedatafeed.features.home.api

import com.jadsalhani.proximiedatafeed.features.home.models.Volume

class GetVolumesAPIResponse(
    val kind: String,
    val totalItems: Int,
    val items: Array<Volume>
)
