package com.example.taylanwhite.pizzadjango.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.taylanwhite.pizzadjango.R
import android.R.id.icon2
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.ArrayAdapter
import java.util.*
import com.example.taylanwhite.pizzadjango.R.mipmap.ic_launcher
import com.example.taylanwhite.pizzadjango.presenter.PizzaListAdapter
import com.example.taylanwhite.pizzadjango.models.MeatToppings
import com.example.taylanwhite.pizzadjango.models.SpecializedPizzaResults
import com.example.taylanwhite.pizzadjango.models.SpecializedPizzas
import com.example.taylanwhite.pizzadjango.presenter.PizzaService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SelectPizza : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_pizza)
        val currentLayout = findViewById(R.id.activity_select_pizza) as LinearLayout
        currentLayout.setBackgroundColor(Color.parseColor("#D2B48C"))
        val mActionBar = supportActionBar
        mActionBar?.setDisplayShowHomeEnabled(false)
        mActionBar?.setDisplayShowTitleEnabled(false)
        val mInflater = LayoutInflater.from(this)
        val mCustomView = mInflater.inflate(R.layout.activity_custom_title_bar, null)
        mActionBar?.customView = mCustomView
        mActionBar?.setDisplayShowCustomEnabled(true)
        mActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#C0C0C0")))
        val mTitle = mCustomView.findViewById(R.id.txtTitle) as TextView
        mTitle.text = " Pick a Pizza "
        val mHome = mCustomView.findViewById(R.id.txtHome) as ImageButton
        val txtNext = findViewById(R.id.txtNext) as ImageButton
        txtNext.visibility = View.GONE
        mHome.setOnClickListener {
            val intent = Intent(this, MainMenu::class.java)
            startActivity(intent)
            finish()
        }



        PizzaService.api.getSpecialtyPizzas().enqueue(object : Callback<SpecializedPizzas> {
            override fun onFailure(call: Call<SpecializedPizzas>?, t: Throwable?) {
                throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<SpecializedPizzas>?, response: Response<SpecializedPizzas>?) {
                if (response?.isSuccessful ?: false) {
                    response?.body()?.let { response ->
//                        response
//                        // response.user.results[0].url
                        val pizzaList = ArrayList<SpecializedPizzaResults>()

                        pizzaList.add(SpecializedPizzaResults().apply {
                            name = "Build Your Own"
                            id = -1
                            image = R.mipmap.pizza_icon
                        })

                        response.results.forEach { i ->
                            pizzaList.add(i.apply { image = R.mipmap.pizza_icon })
                            //do something with i
                        }

                        val PizzaListAdapter = PizzaListAdapter(this@SelectPizza, pizzaList)
                        (findViewById(R.id.list_of_pizzas) as ListView).adapter = PizzaListAdapter




                    }
                }


            }

        })

    }

}
