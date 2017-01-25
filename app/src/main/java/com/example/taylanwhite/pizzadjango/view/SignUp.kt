package com.example.taylanwhite.pizzadjango.view

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.example.taylanwhite.pizzadjango.R
import com.example.taylanwhite.pizzadjango.models.NewUser
import com.example.taylanwhite.pizzadjango.presenter.PizzaService
import kotlinx.android.synthetic.main.activity_main.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUp : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

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
        mTitle.text = ""
        val txtCreate = findViewById(R.id.btnLogin) as Button

        val txtUsername = findViewById(R.id.txt_Username_Login) as EditText
        val txtPassword = findViewById(R.id.txt_Pass_Login) as EditText
        val txtEmail = findViewById(R.id.txt_Email) as EditText

        val email = txtEmail.text
        val username = txtUsername.text
        val password = txtPassword.text

        txtCreate.setOnClickListener {

                    PizzaService.api.createUser(NewUser(email = email.toString(), username = username.toString(), password = password.toString()
        ) ).enqueue(object : Callback<NewUser> {
            override fun onFailure(call: Call<NewUser>?, t: Throwable?) {
                //To change body of created functions use File | Settings | File Templates.
                val connectionError = "Could not connect to service. (Are you connected to the internet?)"
                Toast.makeText(this@SignUp, connectionError, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<NewUser>?, response: Response<NewUser>?) {
                if (response?.isSuccessful ?: false) {
                    response?.body()?.let { response ->



                        Toast.makeText(this@SignUp, "New User Creation Successful", Toast.LENGTH_LONG).show()
                        val intent = Intent(this@SignUp, MainActivity::class.java)
                        startActivity(intent)
                        finish()

                    }
                } else {
                    Toast.makeText(this@SignUp, response?.errorBody()?.string(), Toast.LENGTH_LONG).show()

                }
            }

        })

        }




    }
}
