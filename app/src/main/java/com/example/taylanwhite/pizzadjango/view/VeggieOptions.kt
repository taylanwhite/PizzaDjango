package com.example.taylanwhite.pizzadjango.view

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.widget.*
import com.example.taylanwhite.pizzadjango.R
import com.example.taylanwhite.pizzadjango.models.*
import com.example.taylanwhite.pizzadjango.presenter.PizzaService
import izeni.pizza.models.Pizza
import izeni.pizza.presenter.DividerItemDecoration
import izeni.pizza.presenter.VeggieAdapterRecycler
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class VeggieOptions : AppCompatActivity() {
    lateinit var mAdapter: VeggieAdapterRecycler

    lateinit var recyclerView: RecyclerView
    val pizzaList = ArrayList<VeggieToppingResults>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meat_options)
        val idVeggieList = ArrayList<VeggieToppingResults>()
        val currentLayout = findViewById(R.id.activity_meat_options) as RelativeLayout
        currentLayout.setBackgroundResource(R.mipmap.dark_background)
        val mActionBar = supportActionBar
        mActionBar?.setDisplayShowHomeEnabled(false)
        mActionBar?.setDisplayShowTitleEnabled(false)
        val mInflater = LayoutInflater.from(this)
        val mCustomView = mInflater.inflate(R.layout.activity_custom_title_bar, null)
        mActionBar?.customView = mCustomView
        mActionBar?.setDisplayShowCustomEnabled(true)
        mActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#212121")))
        val mTitle = mCustomView.findViewById(R.id.txtTitle) as TextView
        mTitle.text = " Choose the Veggies "
        val mHome = mCustomView.findViewById(R.id.txtHome) as ImageView
        val btnNext = findViewById(R.id.txtNext) as ImageView
        mTitle.setTextColor(Color.parseColor("#BDBDBD"))

        val extras = intent.extras
        var pizzaSelected = extras.getParcelableArrayList<SpecializedPizzaResults>("pizzaID")
        var crustSelected = extras.getParcelableArrayList<CrustTypeResults>("crustID")
        var sizeSelected = extras.getParcelableArrayList<SizeResults>("sizeID")
        var sauceSelected = extras.getParcelableArrayList<SauceTypeResults>("sauceID")
        var meatList = extras.getParcelableArrayList<MeatToppingResults>("meatList")
        var dietRestrictions = extras.getParcelableArrayList<DietListResults>("dietID")
        btnNext.setOnClickListener {
            val intent = Intent(this, ExtraOptions::class.java)
            intent.putExtra("pizzaID", pizzaSelected)
            intent.putExtra("crustID", crustSelected)
            intent.putExtra("sizeID", sizeSelected)
            intent.putExtra("sauceID", sauceSelected)
            intent.putExtra("meatList", meatList)
            intent.putExtra("veggieList", idVeggieList)
            intent.putExtra("dietID", dietRestrictions)
            startActivity(intent)
            finish()
        }
        mHome.setOnClickListener {
            val intent = Intent(this, MainMenu::class.java)
            startActivity(intent)
            finish()
        }

        mAdapter = VeggieAdapterRecycler(idVeggieList, pizzaSelected, pizzaList, dietRestrictions)
        recyclerView = findViewById(R.id.recycler_view) as RecyclerView
        val mLayoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        recyclerView.adapter = mAdapter
        getVeggies()


    }

    fun getVeggies(page:Int? = null){
        var subStr = ""
        PizzaService.api.getVeggieToppings(page).enqueue(object: Callback<VeggieToppings> {
            override fun onFailure(call: Call<VeggieToppings>?, t: Throwable?) {
                val connectionError = "Could not connect to service. (Are you connected to the internet?)"
                Toast.makeText(this@VeggieOptions, connectionError, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<VeggieToppings>?, response: Response<VeggieToppings>?) {
                if (response?.isSuccessful ?: false) {
                    response?.body()?.let { response ->


                        if(response.next != null) {

                            subStr = response.next.substring(response.next.length - 1)

                        }


                        if(response.next != null)
                        {
                            getVeggies(subStr.toInt())
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
