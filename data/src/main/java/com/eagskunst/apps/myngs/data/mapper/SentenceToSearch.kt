package com.eagskunst.apps.myngs.data.mapper

import com.eagskunst.apps.myngs.data.entities.Search

/**
 * Created by eagskunst in 25/7/2020.
 */
class SentenceToSearch : Mapper<Pair<Int, String>, Search> {

    override suspend fun map(value: Pair<Int, String>): Search {
        return Search(
            sentence = value.second,
            startedFrom = value.first
        )
    }
}
