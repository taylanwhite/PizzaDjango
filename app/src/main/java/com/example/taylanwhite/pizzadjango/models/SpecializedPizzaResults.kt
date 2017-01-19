package com.example.taylanwhite.pizzadjango.models

import android.os.Parcel
import android.os.Parcelable
import java.util.*


class SpecializedPizzaResults(
        var id: Int? = null,
        var image: Int = 0,
        var name: String? = null,
        var priceSmall: String? = null,
        var priceMedium: String? = null,
        var priceLarge: String? = null,
        var priceXLarge: String? = null,
        var meatToppings: ArrayList<MeatToppingResults> = arrayListOf(),
        var sauceToppings: ArrayList<SauceTypeResults> = arrayListOf(),
        var veggieToppings: ArrayList<VeggieToppingResults> = arrayListOf()


) : Parcelable{


    override fun toString(): String {
        return name ?: ""
    }

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<SpecializedPizzaResults> = object : Parcelable.Creator<SpecializedPizzaResults> {
            override fun createFromParcel(source: Parcel): SpecializedPizzaResults = SpecializedPizzaResults(source)
            override fun newArray(size: Int): Array<SpecializedPizzaResults?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readInt(), source.readInt(), source.readString(), source.readString(), source.readString(), source.readString(), source.readString(), source.createTypedArrayList(MeatToppingResults.CREATOR), source.createTypedArrayList(SauceTypeResults.CREATOR), source.createTypedArrayList(VeggieToppingResults.CREATOR))

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(id!!)
        dest?.writeInt(image)
        dest?.writeString(name)
        dest?.writeString(priceSmall)
        dest?.writeString(priceMedium)
        dest?.writeString(priceLarge)
        dest?.writeString(priceXLarge)
        dest?.writeTypedList(meatToppings)
        dest?.writeTypedList(sauceToppings)
        dest?.writeTypedList(veggieToppings)
    }
}

