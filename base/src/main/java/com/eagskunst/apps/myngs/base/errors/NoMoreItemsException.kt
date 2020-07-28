package com.eagskunst.apps.myngs.base.errors


/**
 * Created by eagskunst in 27/7/2020.
 */
class NoMoreItemsException(msg: String = "The search did not return more items"): Exception(msg)