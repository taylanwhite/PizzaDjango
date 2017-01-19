package com.example.taylanwhite.pizzadjango.view

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.example.taylanwhite.pizzadjango.R
import com.example.taylanwhite.pizzadjango.models.*
import com.example.taylanwhite.pizzadjango.models.models_not_used.ToppingsObject
import com.example.taylanwhite.pizzadjango.presenter.PizzaService
import com.example.taylanwhite.pizzadjango.presenter.SauceListAdapter
import izeni.pizza.models.PizzaObject
import izeni.pizza.presenter.DatabaseHelper
import izeni.pizza.presenter.DividerItemDecoration
import izeni.pizza.presenter.MeatAdapterRecycler
import izeni.pizza.presenter.RecyclerTouchListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MeatOptions : AppCompatActivity() {
    lateinit var mAdapter: MeatAdapterRecycler

    lateinit var recyclerView: RecyclerView
    val pizzaList = ArrayList<MeatToppingResults>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meat_options)
        val idMeatList = ArrayList<MeatToppingResults>()
        val currentLayout = findViewById(R.id.activity_meat_options) as RelativeLayout
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
        mTitle.text = " Choose the Meats "
        val mHome = mCustomView.findViewById(R.id.txtHome) as ImageButton
        val btnNext = findViewById(R.id.txtNext) as ImageButton
        val extras = intent.extras
        var pizzaSelected = extras.getParcelableArrayList<SpecializedPizzaResults>("pizzaID")
        var crustSelected = extras.getParcelableArrayList<CrustTypeResults>("crustID")
        var sizeSelected = extras.getParcelableArrayList<SizeResults>("sizeID")
        var sauceSelected = extras.getParcelableArrayList<SauceTypeResults>("sauceID")
        btnNext.setOnClickListener {
            val intent = Intent(this, VeggieOptions::class.java)
            intent.putExtra("pizzaID", pizzaSelected)
            intent.putExtra("crustID", crustSelected)
            intent.putExtra("sizeID", sizeSelected)
            intent.putExtra("sauceID", sauceSelected)
            intent.putExtra("meatList", idMeatList)
            startActivity(intent)
        }
        mHome.setOnClickListener {
            val intent = Intent(this, MainMenu::class.java)
            startActivity(intent)
            finish()
        }


        mAdapter = MeatAdapterRecycler(idMeatList, pizzaSelected, pizzaList)
        recyclerView = findViewById(R.id.recycler_view) as RecyclerView
        val mLayoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        recyclerView.adapter = mAdapter
        getMeats()




    }

    private fun getMeats(page:Int? = null){
        var subStr = ""
        PizzaService.api.getMeatToppings(page).enqueue(object: Callback<MeatToppings> {
            override fun onFailure(call: Call<MeatToppings>?, t: Throwable?) {
                throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<MeatToppings>?, response: Response<MeatToppings>?) {
                if (response?.isSuccessful ?: false) {
                    response?.body()?.let { response ->


                        if(response.next != null) {

                            subStr = response.next.substring(response.next.length - 1)

                        }


                        if(response.next != null)
                        {
                            getMeats(subStr.toInt())
//                           page = response.next
                        }

                        for (item in response.results) {
                            pizzaList.add(item)
                            //reverse arraylist to show todays deal first
                            //Collections.reverse(pizzaList)

                            mAdapter.notifyDataSetChanged()


                        }
                    }
                }
            }

        })
    }
}
