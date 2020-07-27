package com.eagskunst.apps.myngs.data.mapper

/**
 * Created by eagskunst in 24/7/2020.
 */
interface Mapper<T, out R> {
    suspend fun map(value: T): R
}
