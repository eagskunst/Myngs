package com.eagskunst.apps.myngs.data.services

import com.eagskunst.apps.myngs.data.responses.TunesQueryResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by eagskunst in 25/7/2020.
 */
interface SearchService {

    @GET("/search")
    suspend fun searchSentence(
        @Query("term") sentence: String,
        @Query("mediaType") mediaType: String = "music",
        @Query("offset") page: Int,
        @Query("limit") limit: Int = 20
    ): TunesQueryResponse
}
