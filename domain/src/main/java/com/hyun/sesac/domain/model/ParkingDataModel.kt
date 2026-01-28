package com.hyun.sesac.domain.model

data class Parking(
    var id: String = "",
    var name: String = "",
    var address: String = "",
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,
    var currentCnt: Int? = 0,
    var totalCnt: Int = 0,
    var isBookmarked: Boolean = false,
    var updatedTime: String = ""
)