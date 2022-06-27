package com.example.jpmcnycschools.api

import com.example.jpmcnycschools.UIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface SchoolRepository {
    suspend fun getSchools(): Flow<UIState>
    suspend fun getScores(): Flow<UIState>
}

class SchoolRepositoryImpl @Inject constructor(private val apiService: ApiService): SchoolRepository {
    override suspend fun getSchools() = flow {
        try {
            val response = apiService.getSchoolList()
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(UIState.Success(it))
                } ?: throw Exception("Error null")
            } else {
                throw Exception("Error failure")
            }
        } catch (e: Exception) {
            emit(UIState.Error(e))
        }
    }

    override suspend fun getScores() = flow {
        emit(UIState.Loading)
        try {
            val response = apiService.getSatScores()
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(UIState.Success(it))
                } ?: throw Exception("Error null")
            } else {
                throw Exception("Error failure")
            }
        } catch (e: Exception) {
            emit(UIState.Error(e))
        }
    }
}