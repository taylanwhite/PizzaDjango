package com.example.taylanwhite.pizzadjango.models


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

class MeatToppingResults(val id: Int? = null, val name: String? = null, val price: String? = null, var selected: Boolean = false) : Parcelable {


    companion object {
        @JvmField val CREATOR: Parcelable.Creator<MeatToppingResults> = object : Parcelable.Creator<MeatToppingResults> {
            override fun createFromParcel(source: Parcel): MeatToppingResults = MeatToppingResults(source)
            override fun newArray(size: Int): Array<MeatToppingResults?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readInt(), source.readString(), source.readString(), 1.equals(source.readInt()))

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(id ?: -1)
        dest?.writeString(name)
        dest?.writeString(price)
        dest?.writeInt((if (selected) 1 else 0))
    }
}