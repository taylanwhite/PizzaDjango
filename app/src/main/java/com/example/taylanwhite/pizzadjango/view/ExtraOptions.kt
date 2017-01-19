package com.example.taylanwhite.pizzadjango.view

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.taylanwhite.pizzadjango.R
import com.example.taylanwhite.pizzadjango.models.*
import com.example.taylanwhite.pizzadjango.presenter.ExtrasAdapterRecycler
import com.example.taylanwhite.pizzadjango.presenter.PizzaService
import izeni.pizza.presenter.DividerItemDecoration
import izeni.pizza.presenter.VeggieAdapterRecycler
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class ExtraOptions : AppCompatActivity() {
    lateinit var mAdapter: ExtrasAdapterRecycler

    lateinit var recyclerView: RecyclerView
    val pizzaList = ArrayList<ExtrasResults>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_extra_options)
        val idExtraList = ArrayList<ExtrasResults>()
        val currentLayout = findViewById(R.id.activity_extra_options) as RelativeLayout
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
        mTitle.text = " Extras! "
        val mHome = mCustomView.findViewById(R.id.txtHome) as ImageButton
        val btnNext = findViewById(R.id.txtNext) as ImageButton
        mHome.setOnClickListener {
            val intent = Intent(this, MainMenu::class.java)
            startActivity(intent)
            finish()
        }


        val extras = intent.extras
        var pizzaSelected = extras.getParcelableArrayList<SpecializedPizzaResults>("pizzaID")
        var crustSelected = extras.getParcelableArrayList<CrustTypeResults>("crustID")
        var sizeSelected = extras.getParcelableArrayList<SizeResults>("sizeID")
        var sauceSelected = extras.getParcelableArrayList<SauceTypeResults>("sauceID")
        var veggieSelected = extras.getParcelableArrayList<VeggieToppingResults>("veggieList")
        var meatList = extras.getParcelableArrayList<MeatToppingResults>("meatList")
        var toppingList = ArrayList<String>()

        btnNext.setOnClickListener {
            val intent = Intent(this, CompletePizza::class.java)
            intent.putExtra("pizzaID", pizzaSelected)
            intent.putExtra("crustID", crustSelected)
            intent.putExtra("sizeID", sizeSelected)
            intent.putExtra("sauceID", sauceSelected)
            intent.putExtra("meatList", meatList)
            intent.putExtra("veggieList", veggieSelected)
            intent.putExtra("extrasList", idExtraList)
            startActivity(intent)
        }

        mAdapter = ExtrasAdapterRecycler(idExtraList, pizzaList)
        recyclerView = findViewById(R.id.recycler_view) as RecyclerView
        val mLayoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        recyclerView.adapter = mAdapter
        getExtras()






    }

    fun getExtras(page:Int? = null){
        var subStr = ""
        PizzaService.api.getExtras(page).enqueue(object: Callback<Extras> {
            override fun onFailure(call: Call<Extras>?, t: Throwable?) {
                throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<Extras>?, response: Response<Extras>?) {
                if (response?.isSuccessful ?: false) {
                    response?.body()?.let { response ->


                        if(response.next != null) {

                            subStr = response.next.substring(response.next.length - 1)

                        }


                        if(response.next != null)
                        {
                            getExtras(subStr.toInt())
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
