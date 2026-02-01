package com.hyun.sesac.data.mapper

import com.hyun.sesac.domain.model.KakaoApiModel

// 카카오 응답에서 가장 적절한 주소 문자열 뽑아내는 함수
fun KakaoApiModel.toBestAddressString(): String? {
    val document = this.documents.firstOrNull() ?: return null

    return when {
        // 1순위: 건물 이름 (성수역)
        !document.road_address?.building_name.isNullOrEmpty() -> {
            document.road_address?.building_name
        }
        // 2순위: 도로명 주소 (아차산로 107)
        document.road_address != null -> {
            document.road_address!!.address_name
        }
        // 3순위: 지번 주소
        else -> {
            document.address?.address_name
        }
    }
}