package com.jadsalhani.proximiedatafeed.domain.volume

data class VolumeAccessInfo(
    val country: String,
    val viewability: String,
    val embeddable: Boolean,
    val publicDomain: Boolean,
    val textToSpeechPermission: String,
    val epub: VolumeAccessInfoEpub,
    val pdf: VolumeAccessInfoPDF,
    val webReaderLink: String,
    val accessViewStatus: String,
    val quoteSharingAllowed: Boolean
)
