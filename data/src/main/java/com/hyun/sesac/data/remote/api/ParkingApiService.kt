package com.hyun.sesac.data.remote.api

import com.hyun.sesac.data.remote.dto.CityDataResponse
import retrofit2.http.GET
import retrofit2.http.Path

// REST 일 경우 구현체 샘플코드 ( EX. 실시간 데이터 )
interface ParkingApiService {
    // TODO 12/22 API KEY는 SharedPreference에 넣어서 interceptor로 헤더 세팅 해야 함
    // TODO 12/22 근데 해당 공공데이터는 잘못 만들어진 거라 이대로 두기
    @GET("{apiKey}/json/GetParkingInfo/{startIdx}/{endIdx}/")
    suspend fun getCityData(
        @Path("apiKey") apiKey: String,
        @Path("startIdx") startIdx: Int = 1,
        @Path("endIdx") endIdx: Int = 180,
    ): CityDataResponse
}
