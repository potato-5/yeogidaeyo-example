package com.hyun.sesac.data.remote.dto

import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.PropertyName

@IgnoreExtraProperties
data class ParkingInfoDto(
    @get:PropertyName("parking_cd") @set:PropertyName("parking_cd")
    var parkingCd: String = "",
    @get:PropertyName("parking_nm") @set:PropertyName("parking_nm")
    var parkingNm: String = "",
    @get:PropertyName("address") @set:PropertyName("address")
    var address : String = "",
    @get:PropertyName("location") @set:PropertyName("location")
    var location : GeoPoint? = null,
    @get:PropertyName("current_parking_cars") @set:PropertyName("current_parking_cars")
    var currentCnt : String = "",
    @get:PropertyName("total_capacity") @set:PropertyName("total_capacity")
    var totalCnt : String = "",
    @get:PropertyName("base_fee") @set:PropertyName("base_fee")
    var baseFee : String = "",
    @get:PropertyName("base_time") @set:PropertyName("base_time")
    var baseTime : String = "",
    @get:PropertyName("extra_fee") @set:PropertyName("extra_fee")
    var extraFee : String = "",
    @get:PropertyName("extra_time") @set:PropertyName("extra_time")
    var extraTime : String = "",
    @get:PropertyName("is_book_marked") @set:PropertyName("is_book_marked")
    var isBookmarked : Boolean = false
)

