package com.example.taylanwhite.pizzadjango.models

import android.R
import android.R.attr.name
import android.os.Parcel
import android.os.Parcelable


class CrustTypeResults(var id: Int? = null, var name: String? = null) : Parcelable {


    override fun toString(): String {
        return name ?: ""
    }

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<CrustTypeResults> = object : Parcelable.Creator<CrustTypeResults> {
            override fun createFromParcel(source: Parcel): CrustTypeResults = CrustTypeResults(source)
            override fun newArray(size: Int): Array<CrustTypeResults?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readInt(), source.readString())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(id ?: -1)
        dest?.writeString(name)
    }
}


