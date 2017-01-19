package com.example.taylanwhite.pizzadjango.presenter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.taylanwhite.pizzadjango.R
import com.example.taylanwhite.pizzadjango.models.SpecializedPizzaResults
import com.example.taylanwhite.pizzadjango.view.CrustOptions
import java.util.*


class PizzaListAdapter(val context: Activity, val pizzaListDisplay: MutableList<SpecializedPizzaResults>) : ArrayAdapter<SpecializedPizzaResults>(context, R.layout.activity_custom_pizza_list, pizzaListDisplay) {


    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
        var pizzaList = SpecializedPizzaResults()
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val viewRow = layoutInflater.inflate(R.layout.activity_custom_pizza_list, null, true)
        val mtextView = viewRow.findViewById(R.id.text_view) as TextView
        val mimageView = viewRow.findViewById(R.id.image_view) as ImageView
//        onstructor(source: Parcel) : this(
//        id = source.readInt(),
//        image = source.readInt(),
//        name = source.readString(),
//        priceSmall = source.readString(),
//        priceMedium = source.readString(),
//        priceLarge = source.readString(),
//        priceXLarge = source.readString()
//        ) {
//            source.readTypedList(meatToppings, null)
//            source.readTypedList(sauceToppings, null)
//            source.readTypedList(veggieToppings, null)
//        }

        pizzaList.id = pizzaListDisplay[i].id
        pizzaList.image = pizzaListDisplay[i].image
        pizzaList.name = pizzaListDisplay[i].name
        pizzaList.priceSmall = pizzaListDisplay[i].priceSmall
        pizzaList.priceMedium = pizzaListDisplay[i].priceMedium
        pizzaList.priceLarge = pizzaListDisplay[i].priceLarge
        pizzaList.priceXLarge = pizzaListDisplay[i].priceXLarge
        pizzaList.meatToppings = pizzaListDisplay[i].meatToppings
        pizzaList.sauceToppings = pizzaListDisplay[i].sauceToppings
        pizzaList.veggieToppings = pizzaListDisplay[i].veggieToppings



        mtextView.text = pizzaListDisplay[i].name
        mimageView.setImageResource(pizzaListDisplay[i].image)

        viewRow.setOnClickListener {
            val pizzaSelectedList = ArrayList<SpecializedPizzaResults>()
            pizzaSelectedList.add(pizzaList)
//            val pizzaSelected = pizzaListDisplay[i].id!!
//            Log.d("SELECTED_PIZZA_ID::", "$pizzaSelected")
            val intent = Intent(context, CrustOptions::class.java)
            intent.putExtra("pizzaID", pizzaSelectedList)
            context.startActivity(intent)

        }

        return viewRow
    }
}

