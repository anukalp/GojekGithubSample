package com.gojekgithub.trending.data.repo

import com.gojekgithub.trending.constants.NetworkResponse
import com.gojekgithub.trending.data.api.TrendingApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class TrendingRepository @Inject constructor(private val apiService: TrendingApiService) {

    suspend fun getRepositories() = flow {
        return@flow apiService.getRepositories().let {
            if (it.isSuccessful) {
                emit(NetworkResponse.Success(it.body()))
            } else {
                emit(NetworkResponse.Error(Exception(it.errorBody().toString())))
            }
        }
    }.flowOn(Dispatchers.IO)

}