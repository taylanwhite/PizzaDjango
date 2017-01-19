package com.example.taylanwhite.pizzadjango.models

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import java.util.*


/**
 * Created by taylanwhite on 1/17/17.
 */
class PastOrder {

    val count: Int? = null
    val next: String? = null
    val previous: Any? = null
    val results: List<PastOrderResults> = ArrayList()

}