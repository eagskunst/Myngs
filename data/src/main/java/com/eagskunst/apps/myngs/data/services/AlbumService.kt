package com.eagskunst.apps.myngs.data.services

import com.eagskunst.apps.myngs.data.responses.TunesQueryResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by eagskunst in 25/7/2020.
 */
interface AlbumService {

    @GET("lookup")
    suspend fun getAlbumById(
        @Query("id") id: Long,
        @Query("entity") entity: String = "song"
    ): TunesQueryResponse
}