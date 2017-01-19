package com.example.taylanwhite.pizzadjango.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose



/**
 * Created by taylanwhite on 1/5/17.
 */


class ExtrasResults (val id: Int? = null, val name: String? = null, val price: String? = null, var selected: Boolean = false) : Parcelable {



    companion object {
        @JvmField val CREATOR: Parcelable.Creator<ExtrasResults> = object : Parcelable.Creator<ExtrasResults> {
            override fun createFromParcel(source: Parcel): ExtrasResults = ExtrasResults(source)
            override fun newArray(size: Int): Array<ExtrasResults?> = arrayOfNulls(size)
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
