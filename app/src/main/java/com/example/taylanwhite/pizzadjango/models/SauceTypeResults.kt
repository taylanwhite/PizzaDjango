package com.example.taylanwhite.pizzadjango.models

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose



class SauceTypeResults(var id: Int? = null, var name: String? = null) : Parcelable {




    companion object {
        @JvmField val CREATOR: Parcelable.Creator<SauceTypeResults> = object : Parcelable.Creator<SauceTypeResults> {
            override fun createFromParcel(source: Parcel): SauceTypeResults = SauceTypeResults(source)
            override fun newArray(size: Int): Array<SauceTypeResults?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readInt(), source.readString())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(id ?: -1)
        dest?.writeString(name)
    }
}
