package com.example.taylanwhite.pizzadjango.presenter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.taylanwhite.pizzadjango.R
import com.example.taylanwhite.pizzadjango.models.CrustTypeResults
import com.example.taylanwhite.pizzadjango.models.SauceTypeResults
import com.example.taylanwhite.pizzadjango.models.SizeResults
import com.example.taylanwhite.pizzadjango.models.SpecializedPizzaResults
import com.example.taylanwhite.pizzadjango.view.MeatOptions
import com.example.taylanwhite.pizzadjango.view.SauceOptions
import java.util.*

class SauceListAdapter(val context: Activity, val pizza_ID: ArrayList<SpecializedPizzaResults>, val crust_ID: ArrayList<CrustTypeResults>, val size_ID: ArrayList<SizeResults>, val sauceListDisplay: MutableList<SauceTypeResults>) : ArrayAdapter<SauceTypeResults>(context, R.layout.activity_custom_pizza_list, sauceListDisplay) {





    override fun getView(i:Int, view: View?, viewGroup: ViewGroup): View {
        var sauceList = SauceTypeResults()
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val viewRow = layoutInflater.inflate(R.layout.activity_custom_crust_list, null, true)
        val mtextView = viewRow.findViewById(R.id.text_view) as TextView
        sauceList.name = sauceListDisplay[i].name
        sauceList.id = sauceListDisplay[i].id
        mtextView.text = sauceListDisplay[i].name

        try {
            if(sauceListDisplay[i].name == pizza_ID[0].sauceToppings[0].name)
            {
                mtextView.setTextColor(Color.GRAY)
                mtextView.text = sauceListDisplay[i].name
            }
            else mtextView.text = sauceListDisplay[i].name

        } catch (e: IndexOutOfBoundsException) {
        }




        viewRow.setOnClickListener {
            val sauceSelectedList = ArrayList<SauceTypeResults>()
            sauceSelectedList.add(sauceList)
//            sauceSelectedList.add(sauceListDisplay[i].name!!)
//            val sauceSelected = sauceListDisplay[i].id!!
//            Log.d("SELECTED_SAUCE_ID::", "$sauceSelected")
            val intent = Intent(context, MeatOptions::class.java)
            intent.putExtra("pizzaID", pizza_ID)
            intent.putExtra("crustID", crust_ID)
            intent.putExtra("sizeID", size_ID)
            intent.putExtra("sauceID", sauceSelectedList)
            context.startActivity(intent)


        }


        return viewRow
    }
}