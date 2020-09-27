package com.gojekgithub.trending.data.api

import com.gojekgithub.trending.data.model.GitRepositoryModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TrendingApiService {

    companion object {
        private const val SINCE = "daily"
    }

    @GET("repositories")
    suspend fun getRepositories(
        @Query("language") language: String? = null,
        @Query("since") since: String? = TrendingApiService.SINCE,
        @Query("spoken_language_code") spoken_language_code: String? = null
    ): Response<List<GitRepositoryModel>>

}