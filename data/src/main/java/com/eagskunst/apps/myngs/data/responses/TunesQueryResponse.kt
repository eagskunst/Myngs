package com.eagskunst.apps.myngs.data.responses

import com.squareup.moshi.Json

data class TunesQueryResponse(
    @Json(name = "resultCount")
    val resultCount: Int,
    @Json(name = "results")
    val results: List<TunesQueryResult>
)
