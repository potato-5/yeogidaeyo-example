package com.hyun.sesac.data.remote.dto // 패키지명은 프로젝트 구조에 맞게 조정

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class KakaoSearchResponse(
    @Json(name = "meta")
    val meta: Meta,
    @Json(name = "documents")
    val documents: List<PlaceDocument>
)

@JsonClass(generateAdapter = true)
data class Meta(
    @Json(name = "total_count")
    val totalCount: Int,
    @Json(name = "pageable_count")
    val pageableCount: Int,
    @Json(name = "is_end")
    val isEnd: Boolean
)

@JsonClass(generateAdapter = true)
data class PlaceDocument(
    @Json(name = "id")
    val id: String,
    @Json(name = "place_name")
    val placeName: String,          // 장소명 (예: 세싹 코딩학원)
    @Json(name = "address_name")
    val addressName: String,        // 지번 주소
    @Json(name = "road_address_name")
    val roadAddressName: String,    // 도로명 주소
    @Json(name = "x")
    val x: String,                  // 경도 (Longitude)
    @Json(name = "y")
    val y: String,                  // 위도 (Latitude)
    @Json(name = "phone")
    val phone: String,              // 전화번호
    @Json(name = "distance")
    val distance: String            // 중심좌표로부터의 거리 (단위: 미터)
)