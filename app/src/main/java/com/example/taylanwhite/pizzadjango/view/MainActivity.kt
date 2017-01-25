package com.example.taylanwhite.pizzadjango.view

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.example.taylanwhite.pizzadjango.App
import com.example.taylanwhite.pizzadjango.R
import com.example.taylanwhite.pizzadjango.models.*
import com.example.taylanwhite.pizzadjango.presenter.PizzaService
import kotlinx.android.synthetic.main.activity_main.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import com.google.gson.Gson
import android.content.SharedPreferences
import android.preference.PreferenceManager.getDefaultSharedPreferences
import android.preference.PreferenceManager








class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentLayout = findViewById(R.id.activity_login) as RelativeLayout
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
        mTitle.text = "LOGIN/SIGN UP"

        val btnLogin = findViewById(R.id.btnLogin) as Button
        val txtUsername = findViewById(R.id.txt_Username_Login) as EditText
        val txtPassword = findViewById(R.id.txt_Pass_Login) as EditText
        val btnForgotPassword = findViewById(R.id.btnForgotPassword) as Button
        val btnSignUp = findViewById(R.id.btnSignUp) as Button
        btnForgotPassword.visibility = View.GONE


        btnSignUp.setOnClickListener {

            val intent = Intent(this@MainActivity, SignUp::class.java)
            startActivity(intent)
            finish()

        }

        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        var token: String = preferences.getString("token", "")
        if(token.equals("empty")) {
            btnLogin.setOnClickListener {

                var username = txtUsername.text.toString()
                var password = txtPassword.text.toString()

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(this@MainActivity, "Username or Password cannot be empty", Toast.LENGTH_SHORT).show()

                } else {


                    PizzaService.api.signIn(UserLogin = UserLogin(username, password)).enqueue(object : Callback<UserToken> {
                        override fun onFailure(call: Call<UserToken>?, t: Throwable?) {
                            //To change body of created functions use File | Settings | File Templates.
                            val connectionError = "Could not connect to service. (Are you connected to the internet?)"
                            Toast.makeText(this@MainActivity, connectionError, Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse(call: Call<UserToken>?, response: Response<UserToken>?) {
                            if (response?.isSuccessful ?: false) {
                                response?.body()?.let { response ->
                                    val preferences = PreferenceManager.getDefaultSharedPreferences(this@MainActivity)
                                    val editor = preferences.edit()
                                    editor.putString("token", response.token)
                                    editor.apply()

                                    response.token?.let { it1 ->
                                        PizzaService.api.getCurrentUser("Token " + it1).enqueue(object : Callback<UserResults> {
                                            override fun onFailure(call: Call<UserResults>?, t: Throwable?) {
                                                //To change body of created functions use File | Settings | File Templates.
                                                val connectionError = "Could not connect to service. (Are you connected to the internet?)"
                                                Toast.makeText(this@MainActivity, connectionError, Toast.LENGTH_SHORT).show()
                                            }

                                            override fun onResponse(call: Call<UserResults>?, response: Response<UserResults>?) {
                                                if (response?.isSuccessful ?: false) {
                                                    response?.body()?.let { response ->
                                                        val preferences = PreferenceManager.getDefaultSharedPreferences(this@MainActivity)
                                                        val editor = preferences.edit()
                                                        editor.putString("userID", response.id)
                                                        editor.apply()

                                                        val intent = Intent(this@MainActivity, MainMenu::class.java)
                                                        startActivity(intent)
                                                        finish()


                                                    }
                                                } else {

                                                    Toast.makeText(this@MainActivity, "Error getting User", Toast.LENGTH_SHORT).show()


                                                }
                                            }

                                        })
                                    }


                                }
                            } else {
                                val gson = Gson()
                                val loginError = gson.fromJson(response?.errorBody()?.string(), ErrorBody::class.java)



                                Toast.makeText(this@MainActivity, loginError.non_field_errors.first(), Toast.LENGTH_SHORT).show()


                            }
                        }

                    })
                }

            }

        }else{
            val intent = Intent(this@MainActivity, MainMenu::class.java)
            Toast.makeText(this@MainActivity, "Automatically Logged In Previous User", Toast.LENGTH_SHORT).show()
            startActivity(intent)
            finish()
        }


    }

}
