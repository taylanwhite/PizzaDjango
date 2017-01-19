package com.example.taylanwhite.pizzadjango.models

import android.os.Parcel
import android.os.Parcelable

data class VeggieToppingResults(val id: Int? = null, val name: String? = null, val price: String? = null,
                                var selected: Boolean = false) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<VeggieToppingResults> = object : Parcelable.Creator<VeggieToppingResults> {
            override fun createFromParcel(source: Parcel): VeggieToppingResults = VeggieToppingResults(source)
            override fun newArray(size: Int): Array<VeggieToppingResults?> = arrayOfNulls(size)
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
