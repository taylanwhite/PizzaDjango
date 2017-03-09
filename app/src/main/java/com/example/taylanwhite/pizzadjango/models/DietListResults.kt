package com.example.taylanwhite.pizzadjango.models

import android.os.Parcel
import android.os.Parcelable
import java.util.*

/**
 * Created by taylanwhite on 1/30/17.
 */


//class DietListResults{
//
//    var id: Int = 0
//    var name: String = ""
//    var meatToppings: List<Int> = arrayListOf()
//    var veggieToppings: List<Int> = arrayListOf()
//    var sauceToppings: List<Int> = arrayListOf()
//    var crustType: List<Int> = arrayListOf()
//    var selected: Boolean = false
//
//}


class DietListResults (var id: Int? = null, var name: String? = null, var meatToppings: ArrayList<Int> = arrayListOf(),
                       var veggieToppings: ArrayList<Int> = arrayListOf(), var sauceToppings: ArrayList<Int> = arrayListOf(),
                       var crustType: ArrayList<Int> = arrayListOf(), var extra: ArrayList<Int> = arrayListOf(),
                       var selected: Boolean = false) : Parcelable {



    companion object {
        @JvmField val CREATOR: Parcelable.Creator<DietListResults> = object : Parcelable.Creator<DietListResults> {
            override fun createFromParcel(source: Parcel): DietListResults = DietListResults(source)
            override fun newArray(size: Int): Array<DietListResults?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readInt(), source.readString(), ArrayList<Int>().apply{ source.readList(this, Int::class.java.classLoader) }, ArrayList<Int>().apply{ source.readList(this, Int::class.java.classLoader) }, ArrayList<Int>().apply{ source.readList(this, Int::class.java.classLoader) }, ArrayList<Int>().apply{ source.readList(this, Int::class.java.classLoader) }, ArrayList<Int>().apply{ source.readList(this, Int::class.java.classLoader) }, 1.equals(source.readInt()))

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(id ?: -1)
        dest?.writeString(name)
        dest?.writeList(meatToppings)
        dest?.writeList(veggieToppings)
        dest?.writeList(sauceToppings)
        dest?.writeList(crustType)
        dest?.writeList(extra)
        dest?.writeInt((if (selected) 1 else 0))
    }

}
