package com.example.taylanwhite.pizzadjango.models

import android.os.Parcel
import android.os.Parcelable
import java.util.*

/**
 * Created by taylanwhite on 1/17/17.
 */
class PastOrderResults(
        //        var id: Int? = null,
        var user: UserResults,
        var name: String? = null,
        var price: String,
        var status: ArrayList<StatusResults> = arrayListOf(),
        var veggieToppings: ArrayList<VeggieToppingResults> = arrayListOf(),
        var meatToppings: ArrayList<MeatToppingResults> = arrayListOf(),
        var size: ArrayList<SizeResults> = arrayListOf(),
        var sauceToppings: ArrayList<SauceTypeResults> = arrayListOf(),
        var crustType: ArrayList<CrustTypeResults> = arrayListOf(),
        var extra: ArrayList<ExtrasResults> = arrayListOf()


) : Parcelable {


    override fun toString(): String {
        return name ?: ""
    }

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<PastOrderResults> = object : Parcelable.Creator<PastOrderResults> {
            override fun createFromParcel(source: Parcel): PastOrderResults = PastOrderResults(source)
            override fun newArray(size: Int): Array<PastOrderResults?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readParcelable<UserResults>(UserResults.javaClass.classLoader), source.readString(), source.readString(), source.createTypedArrayList(StatusResults.CREATOR), source.createTypedArrayList(VeggieToppingResults.CREATOR), source.createTypedArrayList(MeatToppingResults.CREATOR), source.createTypedArrayList(SizeResults.CREATOR), source.createTypedArrayList(SauceTypeResults.CREATOR), source.createTypedArrayList(CrustTypeResults.CREATOR) , source.createTypedArrayList(ExtrasResults.CREATOR))

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeParcelable(user, 0)
        dest?.writeString(name)
        dest?.writeString(price)
        dest?.writeTypedList(status)
        dest?.writeTypedList(veggieToppings)
        dest?.writeTypedList(meatToppings)
        dest?.writeTypedList(size)
        dest?.writeTypedList(sauceToppings)
        dest?.writeTypedList(crustType)
        dest?.writeTypedList(extra)
    }
}
