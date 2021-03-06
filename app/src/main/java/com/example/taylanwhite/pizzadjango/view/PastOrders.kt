package com.example.taylanwhite.pizzadjango.view

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.example.taylanwhite.pizzadjango.R
import com.example.taylanwhite.pizzadjango.models.*
import com.example.taylanwhite.pizzadjango.presenter.PastOrderRecycler
import com.example.taylanwhite.pizzadjango.presenter.PizzaService
import izeni.pizza.presenter.DividerItemDecoration
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import android.widget.AdapterView.OnItemClickListener
import izeni.pizza.presenter.RecyclerTouchListener


class PastOrders : AppCompatActivity() {

    lateinit var mAdapter: PastOrderRecycler

    lateinit var Handler: Handler
    lateinit var recyclerView: RecyclerView
    val orderList = ArrayList<PastOrderResults>()
    var noOrders: Boolean = false

    var transferOrders = ArrayList<PastOrderResults>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meat_options)
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
        mTitle.text = " Past Orders "
        mTitle.setTextColor(Color.parseColor("#BDBDBD"))


        Handler = Handler()


        val mHome = mCustomView.findViewById(R.id.txtHome) as ImageView
        val txtNext = findViewById(R.id.txtNext) as ImageView
        txtNext.visibility = View.GONE



        mHome.setOnClickListener {
            val intent = Intent(this, MainMenu::class.java)
            startActivity(intent)
            finish()
        }

        mAdapter = PastOrderRecycler(orderList)
        recyclerView = findViewById(R.id.recycler_view) as RecyclerView
        val mLayoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        recyclerView.adapter = mAdapter



        recyclerView.addOnItemTouchListener(
                RecyclerTouchListener(this@PastOrders, recyclerView, object:RecyclerTouchListener.ClickListener {
                    val preferences = PreferenceManager.getDefaultSharedPreferences(this@PastOrders)
                    var token: String = preferences.getString("token", "")
                    override fun onLongClick(view: View, position: Int) {

                        val items = arrayOf<CharSequence>("Delete this Order?")
                        val builder = AlertDialog.Builder(this@PastOrders)
                        builder.setTitle("")
                        builder.setItems(items) { dialog, item ->

                                PizzaService.api.deleteOrder(orderList[position].id.toString(), "Token " + token).enqueue(object : Callback<PastOrder> {
                                    override fun onFailure(call: Call<PastOrder>?, t: Throwable?) {
                                        val connectionError = "Could not delete Past Order"
                                        Toast.makeText(this@PastOrders, connectionError, Toast.LENGTH_SHORT).show()
                                        Log.d("onFailure", connectionError, t)
                                    }

                                    override fun onResponse(call: Call<PastOrder>?, response: Response<PastOrder>?) {
                                        if (response?.isSuccessful ?: false) {

                                            response?.body()?.let { response ->

                                                //

                                            }
                                        }
//
                                        orderList.removeAt(position)
                                        mAdapter.notifyItemRemoved(position)
                                    }

                                })

                        }
                        builder.show()
//


//

                    }

                    override fun onClick(view: View, position: Int) {

                        val intent = Intent(this@PastOrders, PastOrdersComplete::class.java)
                        intent.putExtra("name", orderList[position].name)
                        intent.putExtra("price", orderList[position].price)
                        intent.putExtra("crust", orderList[position].crustType[0].name)
                        intent.putExtra("sauce", orderList[position].sauceToppings[0].name)
                        intent.putExtra("size", orderList[position].size[0].size)
                        intent.putExtra("meatToppings", orderList[position].meatToppings)
                        intent.putExtra("veggieToppings", orderList[position].veggieToppings)
                        intent.putExtra("extras", orderList[position].extra)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish()

                    }

                })
        )

        getOrders()



    }

    private fun getOrders(page: Int? = null) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        var token: String = preferences.getString("token", "")
        token.let { it1 ->
            PizzaService.api.getOrders("Token " + it1, page).enqueue(object : Callback<PastOrder> {
                override fun onFailure(call: Call<PastOrder>?, t: Throwable?) {
                    val connectionError = "Could not retrieve Past Orders, Are you connected to the internet?"
                    Toast.makeText(this@PastOrders, connectionError, Toast.LENGTH_SHORT).show()
                    Log.d("onFailure", connectionError, t)
                    finish()
                }

                override fun onResponse(call: Call<PastOrder>?, response: Response<PastOrder>?) {
                    if (response?.isSuccessful ?: false) {
                        response?.body()?.let { response ->

                            for (item in response.results) {
                                transferOrders.add(item)

                            }

                            for (item in response.results) {
                                orderList.add(item)
                                //reverse arraylist to show todays deal first
                                //Collections.reverse(pizzaList)
//

                                mAdapter.notifyDataSetChanged()
                            }
                        }
                    }
                }

            })
        }
    }
}
