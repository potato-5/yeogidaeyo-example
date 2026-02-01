package com.hyun.sesac.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CityDataResponse(
    @Json(name = "GetParkingInfo") val parkingInfo: ParkingResult?
)

@JsonClass(generateAdapter = true)
data class ParkingResult(
    @Json(name = "list_total_count") val totalCount: String?,
    @Json(name = "row") val rows: List<ParkingItem>?
)

// 4. 주차장 개별 아이템 (리스트 내부 객체)
@JsonClass(generateAdapter = true)
data class ParkingItem(
    @Json(name = "PKLT_CD") val parkingCode: String, // Firebase ID와 매칭될 코드
    @Json(name = "PKLT_NM") val parkingName: String?,
    @Json(name = "ADDR") val address: String?,
    @Json(name = "PRK_STTS_YN") val prk_yn: String?,

    // JSON에서 숫자가 174.0 처럼 실수형으로 오므로 Double로 받고 나중에 Int로 변환
    @Json(name = "TPKCT") val capacity: Double?,
    @Json(name = "NOW_PRK_VHCL_CNT") val currentCount: Double?,
    @Json(name = "NOW_PRK_VHCL_UPDT_TM") val updateTime: String?
)
