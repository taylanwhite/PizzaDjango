package com.example.taylanwhite.pizzadjango.models

import android.os.Parcel
import android.os.Parcelable
import java.util.*

/**
 * Created by taylanwhite on 1/5/17.
 */
class CompleteOrder(
//        val id: Int,
        val user: String,
        val status: List<Int> = arrayListOf(),
        var veggieToppings: List<Int> = arrayListOf(),
        var meatToppings: List<Int> = arrayListOf(),
        var size: List<Int> = arrayListOf(),
        var sauceToppings: List<Int> = arrayListOf(),
        var crustType: List<Int> = arrayListOf(),
        var extra: List<Int> = arrayListOf(),
        var price: String,
        var name: String


)

