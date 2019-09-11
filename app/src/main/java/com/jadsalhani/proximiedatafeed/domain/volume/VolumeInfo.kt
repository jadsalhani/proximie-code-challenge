package com.jadsalhani.proximiedatafeed.domain.volume

data class VolumeInfo(
    val title: String,
    val authors: Array<String>?,
    val publisher: String,
    val publishedDate: String,
    val description: String?,
    val industryIdentifiers: Array<VolumeIndustryIdentifier>,
    val readingModes: VolumeReadingMode,
    val pageCount: Int,
    val printType: String,
    val averageRating: Double,
    val ratingsCount: Int,
    val maturityRating: String,
    val allowAnonLogging: Boolean,
    val contentVersion: String,
    val imageLinks: VolumeImageLinks,
    val language: String,
    val previewLink: String,
    val infoLink: String,
    val canonicalVolumeLink: String
)
