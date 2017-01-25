package com.example.taylanwhite.pizzadjango.view

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.example.taylanwhite.pizzadjango.App
import com.example.taylanwhite.pizzadjango.R
import com.example.taylanwhite.pizzadjango.models.PastOrder
import com.example.taylanwhite.pizzadjango.presenter.PizzaService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        val mCustomView = mInflater.inflate(R.layout.activity_custom_log_out, null)
        mActionBar?.customView = mCustomView
        mActionBar?.setDisplayShowCustomEnabled(true)
        mActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#C0C0C0")))
//        val mHome = findViewById(R.id.txtHome) as ImageButton
//        mHome.visibility = View.GONE
        val mTitle = findViewById(R.id.txtTitle) as TextView
        val txtLogOut = findViewById(R.id.txtNext) as TextView
        val txtProfile = findViewById(R.id.btn_profile) as ImageButton
        val default = "empty"
        val preferences = PreferenceManager.getDefaultSharedPreferences(this@MainMenu)
        val editor = preferences.edit()


        txtLogOut.setOnClickListener {

            val items = arrayOf<CharSequence>("Log Out")
            val builder = AlertDialog.Builder(this@MainMenu)
            builder.setTitle("Are you sure?")
            builder.setItems(items) { dialog, item ->


                editor.putString("token", default)
                editor.apply()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()


            }
            builder.show()
            //mTitle.text = "HOME"
        }


        btnOrderPizza.setOnClickListener {
            val intent = Intent(this, SelectPizza::class.java)
            startActivity(intent)
        }
        btnPastPizza.setOnClickListener {
            val intent = Intent(this, PastOrders::class.java)
            startActivity(intent)
        }

//        Stetho.initializeWithDefaults(this@MainActivity)
        App.picasso.load(R.mipmap.pizza_order).into(btnOrderPizza)
        App.picasso.load(R.mipmap.pizza_past).into(btnPastPizza)

    }


}



