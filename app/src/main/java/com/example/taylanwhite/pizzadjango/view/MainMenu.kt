package com.example.taylanwhite.pizzadjango.view

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.taylanwhite.pizzadjango.App
import com.example.taylanwhite.pizzadjango.R

class MainMenu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)


        val btnOrderPizza = findViewById(R.id.btn_order_pizza) as ImageButton
        val btnPastPizza = findViewById(R.id.btn_past_orders) as ImageButton
        App.picasso.load(R.mipmap.pizza_order).into(btnOrderPizza)
        App.picasso.load(R.mipmap.pizza_past).into(btnPastPizza)
        val currentLayout = findViewById(R.id.main_layout) as RelativeLayout
        currentLayout.setBackgroundColor(Color.parseColor("#D2B48C"))

        val mActionBar = supportActionBar
        mActionBar?.setDisplayShowHomeEnabled(false)
        mActionBar?.setDisplayShowTitleEnabled(false)
        val mInflater = LayoutInflater.from(this)
        val mCustomView = mInflater.inflate(R.layout.activity_custom_title_bar, null)
        mActionBar?.customView = mCustomView
        mActionBar?.setDisplayShowCustomEnabled(true)
        mActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#C0C0C0")))
        val mHome = findViewById(R.id.txtHome) as ImageButton
        mHome.visibility = View.GONE
        val mTitle = findViewById(R.id.txtTitle) as TextView
        val txtNext = findViewById(R.id.txtNext) as ImageButton
        txtNext.visibility = View.GONE
        //mTitle.text = "HOME"



        btnOrderPizza.setOnClickListener {
            val intent = Intent(this, SelectPizza::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        btnPastPizza.setOnClickListener {
            val intent = Intent(this, PastOrders::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

//        Stetho.initializeWithDefaults(this@MainActivity)
        App.picasso.load(R.mipmap.pizza_order).into(btnOrderPizza)
        App.picasso.load(R.mipmap.pizza_past).into(btnPastPizza)

    }



    }

