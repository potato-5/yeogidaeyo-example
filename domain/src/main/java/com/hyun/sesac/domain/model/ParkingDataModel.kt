package com.hyun.sesac.domain.model

data class Parking(
    var id: String = "",
    var name: String = "",
    var address: String = "",
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,
    var currentCnt: Int = 0,
    var totalCnt: Int = 0,
    var baseFee: String = "",
    var baseTime: String = "",
    var extraFee: String = "",
    var extraTime: String = "",
    var isBookmarked: Boolean = false,
    var updatedTime: String = ""
)