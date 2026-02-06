package com.hyun.sesac.domain.model

data class KakaoApiModel(
    val documents: List<KakaoDocument>
)

data class KakaoDocument(
    val road_address: RoadAddress?, // 도로명 주소 정보
    val address: Address?           // 지번 주소 정보
)

data class RoadAddress(
    val address_name: String,      // 전체 도로명 주소
    val building_name: String      // [핵심] 건물 이름 (예: 성수역, 이마트 성수점)
)

data class Address(
    val address_name: String       // 전체 지번 주소
)

// 검색
data class KakaoSearchModel(
    val id: String,
    val placeName: String,
    val address: String,
    val roadAddress: String,
    val x: String,
    val y: String,
    val distance: String
)