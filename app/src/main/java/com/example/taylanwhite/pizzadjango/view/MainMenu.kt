package com.example.taylanwhite.pizzadjango.view

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.example.taylanwhite.pizzadjango.App
import com.example.taylanwhite.pizzadjango.R
import com.example.taylanwhite.pizzadjango.models.PastOrder
import com.example.taylanwhite.pizzadjango.models.UpdateUser
import com.example.taylanwhite.pizzadjango.presenter.PizzaService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.LinearLayout



class MainMenu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)


        val btnOrderPizza = findViewById(R.id.btn_order_pizza) as Button
        val btnPastPizza = findViewById(R.id.btn_past_orders) as Button


//        if(btnOrderPizza.isFocused) {
//            btnOrderPizza.setTextColor(Color.BLACK)
//        }
//        else
//        {
//            btnOrderPizza.setTextColor(Color.WHITE)
//        }
//        if(btnPastPizza.isFocused) {
//            btnPastPizza.setTextColor(Color.BLACK)
//        }
//        else
//        {
//            btnPastPizza.setTextColor(Color.WHITE)
//        }
//        btnOrderPizza.setOnClickListener {
//
//
//            }
//        }

        val currentLayout = findViewById(R.id.main_layout) as RelativeLayout
        currentLayout.setBackgroundResource(R.mipmap.dark_background)

        val mActionBar = supportActionBar
        mActionBar?.setDisplayShowHomeEnabled(false)
        mActionBar?.setDisplayShowTitleEnabled(false)
        val mInflater = LayoutInflater.from(this)
        val mCustomView = mInflater.inflate(R.layout.activity_custom_log_out, null)
        mActionBar?.customView = mCustomView
        mActionBar?.setDisplayShowCustomEnabled(true)
        mActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#212121")))

//        val mHome = findViewById(R.id.txtHome) as ImageButton
//        mHome.visibility = View.GONE
        val mTitle = findViewById(R.id.txt_title) as TextView
        mTitle.setTextColor(Color.parseColor("#BDBDBD"))
        val txtLogOut = findViewById(R.id.txt_log_out) as ImageView
        val txtProfile = findViewById(R.id.txt_profile) as ImageView

        txtLogOut.setBackgroundResource(R.mipmap.white_logout)
        txtProfile.setBackgroundResource(R.mipmap.white_profile_icon)
        val default = "empty"
        val preferences = PreferenceManager.getDefaultSharedPreferences(this@MainMenu)
        val editor = preferences.edit()
        var savedFirstName = ""



        txtProfile.setOnClickListener {

            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()

        }


        var token: String = preferences.getString("token", "")
        PizzaService.api.getProfileInfo("Token " + token).enqueue(object : Callback<UpdateUser> {
            override fun onFailure(call: Call<UpdateUser>?, t: Throwable?) {
                //To change body of created functions use File | Settings | File Templates.
                val connectionError = "Could not connect to service. (Are you connected to the internet?)"
                Toast.makeText(this@MainMenu, connectionError, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<UpdateUser>?, response: Response<UpdateUser>?) {
                if (response?.isSuccessful ?: false) {
                    response?.body()?.let { response ->

                        savedFirstName = response.first_name
                        if(savedFirstName != "")
                        {
                            mTitle.text= "Welcome, $savedFirstName!"
                        }
                        else
                        {
                            mTitle.text="Welcome!"
                        }
                    }
                } else {

                    Toast.makeText(this@MainMenu, "ERROR MM(NAME REQUEST)", Toast.LENGTH_SHORT).show()

                }
            }

        })



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

    }


}



