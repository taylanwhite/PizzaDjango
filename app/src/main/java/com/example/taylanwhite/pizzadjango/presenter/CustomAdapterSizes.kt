package com.example.taylanwhite.pizzadjango.presenter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v4.app.ActivityCompat.startActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.taylanwhite.pizzadjango.R
import com.example.taylanwhite.pizzadjango.models.CrustTypeResults
import com.example.taylanwhite.pizzadjango.models.SizeResults
import com.example.taylanwhite.pizzadjango.models.SpecializedPizzaResults
import com.example.taylanwhite.pizzadjango.view.SauceOptions
import com.example.taylanwhite.pizzadjango.view.SizeOptions
import java.util.*


class SizeListAdapter(val context: Activity, val pizza_ID: ArrayList<SpecializedPizzaResults>, val crust_ID: ArrayList<CrustTypeResults>, val sizeListDisplay: ArrayList<SizeResults>) : ArrayAdapter<SizeResults>(context, R.layout.activity_custom_pizza_list, sizeListDisplay) {





    override fun getView(i:Int, view: View?, viewGroup: ViewGroup): View {
        var sizeList = SizeResults()

        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val viewRow = layoutInflater.inflate(R.layout.activity_custom_crust_list, null, true)
        val mtextView = viewRow.findViewById(R.id.text_view) as TextView
        sizeList.size = sizeListDisplay[i].size
        sizeList.id = sizeListDisplay[i].id
        sizeList.price = sizeListDisplay[i].price
       // pizza_ID[0].name
       mtextView.text = sizeListDisplay[i].size



        viewRow.setOnClickListener {
            val sizeSelectedList = ArrayList<SizeResults>()
            sizeSelectedList.add(sizeList)
//            val sizeSelected = sizeListDisplay[i].id!!
//            Log.d("SELECTED_SIZE_ID::", "$sizeSelected")
            val intent = Intent(context, SauceOptions::class.java)
            intent.putExtra("pizzaID", pizza_ID)
            intent.putExtra("crustID", crust_ID)
            intent.putExtra("sizeID", sizeSelectedList)
            context.startActivity(intent)


        }


        return viewRow
    }
}

