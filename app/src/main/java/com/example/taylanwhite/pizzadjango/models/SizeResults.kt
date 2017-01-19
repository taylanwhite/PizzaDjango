package com.example.taylanwhite.pizzadjango.models

import android.os.Parcel
import android.os.Parcelable


data class SizeResults(var id: Int? = null, var size: String? = null, var price: String? = null
                              ) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<SizeResults> = object : Parcelable.Creator<SizeResults> {
            override fun createFromParcel(source: Parcel): SizeResults = SizeResults(source)
            override fun newArray(size: Int): Array<SizeResults?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readInt(), source.readString(), source.readString())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(id ?: -1)
        dest?.writeString(size)
        dest?.writeString(price)
    }
}
