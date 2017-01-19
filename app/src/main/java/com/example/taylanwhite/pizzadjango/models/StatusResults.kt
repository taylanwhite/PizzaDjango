package com.example.taylanwhite.pizzadjango.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose



/**
 * Created by taylanwhite on 1/5/17.
 */

data class StatusResults(val id: Int? = null, val status: String? = null) : Parcelable {

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<StatusResults> = object : Parcelable.Creator<StatusResults> {
            override fun createFromParcel(source: Parcel): StatusResults = StatusResults(source)
            override fun newArray(size: Int): Array<StatusResults?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readInt(), source.readString())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(id ?: -1)
        dest?.writeString(status)
    }
}

