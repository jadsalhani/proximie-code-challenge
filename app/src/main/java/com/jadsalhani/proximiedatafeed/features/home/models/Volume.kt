package com.jadsalhani.proximiedatafeed.features.home.models

data class Volume(
    val kind: String,
    val id: String,
    val etag: String,
    val selfLink: String,
    val volumeInfo: VolumeInfo,
    val saleInfo: VolumeSaleInfo,
    val accessInfo: VolumeAccessInfo,
    val searchInfo: VolumeSearchInfo
)
