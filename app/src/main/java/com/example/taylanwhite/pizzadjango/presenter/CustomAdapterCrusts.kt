package com.example.taylanwhite.pizzadjango.presenter
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.ActivityCompat.startActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.taylanwhite.pizzadjango.R
import com.example.taylanwhite.pizzadjango.models.CrustTypeResults
import com.example.taylanwhite.pizzadjango.models.SpecializedPizzaResults
import com.example.taylanwhite.pizzadjango.view.CrustOptions
import com.example.taylanwhite.pizzadjango.view.MainActivity
import com.example.taylanwhite.pizzadjango.view.SizeOptions
import izeni.pizza.models.Pizza
import java.util.*

class CrustListAdapter(val context: Activity, val lastPosition: ArrayList<SpecializedPizzaResults>, val crustListDisplay: ArrayList<CrustTypeResults>) : ArrayAdapter<CrustTypeResults>(context, R.layout.activity_custom_pizza_list, crustListDisplay) {

    override fun getView(i:Int, view: View?, viewGroup: ViewGroup): View {
        var crustList = CrustTypeResults()
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val viewRow = layoutInflater.inflate(R.layout.activity_custom_crust_list, null, true)
        val mtextView = viewRow.findViewById(R.id.text_view) as TextView
        crustList.name = crustListDisplay[i].name
        crustList.id = crustListDisplay[i].id

        mtextView.text = crustListDisplay[i].name


        viewRow.setOnClickListener {

            val crustSelectedList = ArrayList<CrustTypeResults>()
            crustSelectedList.add(crustList)
//            crustSelectedList.add(crustList[i].name!!)
//            val crustSelected = crustList[i].id!!
//            Log.d("SELECTED_CRUST_ID", "$crustSelected")
            val intent = Intent(context, SizeOptions::class.java)
            intent.putExtra("pizzaID", lastPosition)
            intent.putExtra("crustID", crustSelectedList)
            context.startActivity(intent)


        }

        return viewRow
    }
}

