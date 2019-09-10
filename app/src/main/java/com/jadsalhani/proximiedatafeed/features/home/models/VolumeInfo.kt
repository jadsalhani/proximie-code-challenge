package com.jadsalhani.proximiedatafeed.features.home.models

data class VolumeInfo(
    val title: String,
    val authors: Array<String>?,
    val publishedDate: String,
    val industryIdentifiers: Array<VolumeIndustryIdentifier>,
    val readingModes: VolumeReadingMode,
    val pageCount: Int,
    val printType: String,
    val maturityRating: String,
    val allowAnonLogging: Boolean,
    val contentVersion: String,
    val imageLinks: VolumeImageLinks,
    val language: String,
    val previewLink: String,
    val infoLink: String,
    val canonicalVolumeLink: String
)
