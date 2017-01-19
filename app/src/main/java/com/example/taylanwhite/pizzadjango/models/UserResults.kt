package com.example.taylanwhite.pizzadjango.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose



/**
 * Created by taylanwhite on 1/5/17.
 */


data class UserResults(val id: String? = null, val username: String? = null) : Parcelable {

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<UserResults> = object : Parcelable.Creator<UserResults> {
            override fun createFromParcel(source: Parcel): UserResults = UserResults(source)
            override fun newArray(size: Int): Array<UserResults?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readString(), source.readString())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(id)
        dest?.writeString(username)
    }
}






