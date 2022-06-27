package com.example.jpmcnycschools.api

import com.example.jpmcnycschools.model.SatResponse
import com.example.jpmcnycschools.model.SchoolResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET(END_POINT_SCHOOLS)
    suspend fun getSchoolList(): Response<List<SchoolResponse>>

    @GET(END_POINT_SAT)
    suspend fun getSatScores(): Response<List<SatResponse>>

    companion object {
        const val BASE_URL = "https://data.cityofnewyork.us/"
        const val END_POINT_SCHOOLS = "resource/s3k6-pzi2.json"
        const val END_POINT_SAT = "resource/f9bf-2cp4.json"
    }
}
