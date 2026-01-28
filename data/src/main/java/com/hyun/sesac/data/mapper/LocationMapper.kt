package com.hyun.sesac.data.mapper

import android.location.Location
import com.hyun.sesac.domain.model.UserLocationModel

fun Location.toDomain(): UserLocationModel =
    UserLocationModel(
        latitude = latitude,
        longitude = longitude
    )