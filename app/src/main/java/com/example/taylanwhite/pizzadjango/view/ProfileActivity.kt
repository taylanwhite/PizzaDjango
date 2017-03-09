package com.example.taylanwhite.pizzadjango.view

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AlertDialog
import android.text.method.KeyListener
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.example.taylanwhite.pizzadjango.App
import com.example.taylanwhite.pizzadjango.R
import android.view.View.OnFocusChangeListener
import com.example.taylanwhite.pizzadjango.presenter.PizzaService
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.text.SpannableString
import android.text.style.UnderlineSpan
import com.example.taylanwhite.pizzadjango.models.*
import android.graphics.drawable.Drawable
import kotlinx.android.synthetic.main.nav_header_test_navigation_drawer.*


class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val currentLayout = findViewById(R.id.activity_profile) as RelativeLayout
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
        val txtLogOut = findViewById(R.id.txt_log_out) as ImageView
        val txtBack = findViewById(R.id.txt_profile) as ImageView
        val txtFirstName = findViewById(R.id.txt_first_name) as EditText
        val txtLastName = findViewById(R.id.txt_last_name) as EditText
        val txtEmail = findViewById(R.id.txt_email_address) as EditText
        val txtUsername = findViewById(R.id.txt_username) as EditText
        val btnEditSave = findViewById(R.id.btn_edit_save) as Button
        val textViewUsername = findViewById(R.id.textview_username) as TextView
        val textViewLastName = findViewById(R.id.textview_last_name) as TextView
        val textViewFirstName = findViewById(R.id.textview_first_name) as TextView
        val textViewEmail = findViewById(R.id.textview_email) as TextView
        val textDietaryRestriction = findViewById(R.id.txt_dietary_restrictions) as TextView
        txtBack.setImageResource(R.mipmap.back_arrow)
        mTitle.setTextColor(Color.parseColor("#BDBDBD"))

        val content = SpannableString("Any Dietary Restrictions?")
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        textDietaryRestriction.text = content

        var savedEmail = ""
        var savedFirstName = ""
        var savedLastName = ""
        var savedUserName = ""


        val default = "empty"
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = preferences.edit()
        mTitle.text = "Profile"
        textViewFirstName.setTextColor(Color.parseColor("#BDBDBD"))
        textViewLastName.setTextColor(Color.parseColor("#BDBDBD"))
        textViewEmail.setTextColor(Color.parseColor("#BDBDBD"))
        textViewUsername.setTextColor(Color.parseColor("#BDBDBD"))
        textDietaryRestriction.setTextColor(Color.parseColor("#BDBDBD"))

        val originalDrawable = txtEmail.background

        txtEmail.setBackgroundResource(R.color.transparent)
        txtLastName.setBackgroundResource(R.color.transparent)
        txtFirstName.setBackgroundResource(R.color.transparent)
        txtUsername.setBackgroundResource(R.color.transparent)


        val token: String = preferences.getString("token", "")
        val extras = intent.extras


//        txtFirstName.isClickable=true
//        txtLastName.isClickable=true
//        txtEmail.isClickable=true
        txtUsername.isEnabled = false
        txtFirstName.isEnabled = false
        txtLastName.isEnabled = false
        txtEmail.isEnabled = false

        txtFirstName.setTextColor(Color.parseColor("#BDBDBD"))
        txtLastName.setTextColor(Color.parseColor("#BDBDBD"))
        txtEmail.setTextColor(Color.parseColor("#BDBDBD"))
        txtUsername.setTextColor(Color.parseColor("#BDBDBD"))
        textDietaryRestriction.setOnClickListener {

            val intent = Intent(this, DietRestrictionsActivity::class.java)
//
            startActivity(intent)
            finish()


        }


        PizzaService.api.getProfileInfo("Token " + token).enqueue(object : Callback<UpdateUser> {
            override fun onFailure(call: Call<UpdateUser>?, t: Throwable?) {
                //To change body of created functions use File | Settings | File Templates.
                val connectionError = "Could not connect to service. (Are you connected to the internet?)"
                Toast.makeText(this@ProfileActivity, connectionError, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<UpdateUser>?, response: Response<UpdateUser>?) {
                if (response?.isSuccessful ?: false) {
                    response?.body()?.let { response ->

                        txtFirstName.setText(response.first_name)
                        txtLastName.setText(response.last_name)
                        txtEmail.setText(response.email)
                        txtUsername.setText(response.username)

                    }
                } else {

                    Toast.makeText(this@ProfileActivity, "Error getting User", Toast.LENGTH_SHORT).show()

                }
            }

        })


        txtUsername.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                textViewUsername.setTextColor(Color.GRAY)
            } else if (!hasFocus) {
                textViewUsername.setTextColor(Color.parseColor("#BDBDBD"))
            }
        }

        txtFirstName.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                textViewFirstName.setTextColor(Color.GRAY)
            } else if (!hasFocus) {
                textViewFirstName.setTextColor(Color.parseColor("#BDBDBD"))
            }
        }
            txtLastName.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    textViewLastName.setTextColor(Color.GRAY)
                } else if (!hasFocus) {
                    textViewLastName.setTextColor(Color.parseColor("#BDBDBD"))
                }
            }
            txtEmail.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    textViewEmail.setTextColor(Color.GRAY)
                } else if (!hasFocus) {
                    textViewEmail.setTextColor(Color.parseColor("#BDBDBD"))
                }
            }
            btnEditSave.setOnClickListener {

                if (btnEditSave.text == "   EDIT   ") {
                    txtFirstName.isEnabled = true
                    txtLastName.isEnabled = true
                    txtEmail.isEnabled = true
                    txtUsername.isEnabled = true
                    btnEditSave.text = "   SAVE   "
                    textViewEmail.setTextColor(Color.WHITE)
                    textViewLastName.setTextColor(Color.WHITE)
                    textViewFirstName.setTextColor(Color.WHITE)
                    textViewUsername.setTextColor(Color.WHITE)
                    txtEmail.setBackgroundDrawable(originalDrawable)
                    txtLastName.setBackgroundDrawable(originalDrawable)
                    txtFirstName.setBackgroundDrawable(originalDrawable)
                    txtUsername.setBackgroundDrawable(originalDrawable)
                    txtFirstName.background.mutate().setColorFilter(resources.getColor(R.color.silver), PorterDuff.Mode.SRC_ATOP)

                } else if (btnEditSave.text == "   SAVE   ") {
                    txtFirstName.isEnabled = false
                    txtLastName.isEnabled = false
                    txtEmail.isEnabled = false
                    txtUsername.isEnabled = false
                    btnEditSave.text = "   EDIT   "
                    textViewFirstName.setTextColor(Color.parseColor("#BDBDBD"))
                    textViewLastName.setTextColor(Color.parseColor("#BDBDBD"))
                    textViewEmail.setTextColor(Color.parseColor("#BDBDBD"))
                    textViewUsername.setTextColor(Color.parseColor("#BDBDBD"))

                    txtEmail.setBackgroundResource(R.color.transparent)
                    txtLastName.setBackgroundResource(R.color.transparent)
                    txtFirstName.setBackgroundResource(R.color.transparent)
                    txtUsername.setBackgroundResource(R.color.transparent)

                    savedEmail = txtEmail.text.toString()
                    savedFirstName = txtFirstName.text.toString()
                    savedLastName = txtLastName.text.toString()
                    savedUserName = txtUsername.text.toString()
                    val preferences = PreferenceManager.getDefaultSharedPreferences(this@ProfileActivity)
                    var userID: String = preferences.getString("userID", "")

                    PizzaService.api.updateUser(userID, "Token " + token, UpdateUser(email = savedEmail, username = savedUserName, first_name = savedFirstName, last_name = savedLastName
                    ) ).enqueue(object : Callback<UpdateUser> {
                        override fun onFailure(call: Call<UpdateUser>?, t: Throwable?) {
                            //To change body of created functions use File | Settings | File Templates.
                            val connectionError = "Could not connect to service. (Are you connected to the internet?)"
                            Toast.makeText(this@ProfileActivity, connectionError, Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse(call: Call<UpdateUser>?, response: Response<UpdateUser>?) {
                            if (response?.isSuccessful ?: false) {
                                response?.body()?.let { response ->

                                    Toast.makeText(this@ProfileActivity, "Account Updated", Toast.LENGTH_LONG).show()


                                }
                            } else {
                                Toast.makeText(this@ProfileActivity, response?.errorBody()?.string(), Toast.LENGTH_LONG).show()
                            }
                        }
                    })
                }
            }

            txtLogOut.setOnClickListener {

                val items = arrayOf<CharSequence>("Log Out")
                val builder = AlertDialog.Builder(this@ProfileActivity)
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

            txtBack.setOnClickListener {
                val intent = Intent(this, MainMenu::class.java)
                startActivity(intent)
                finish()
            }




        }

         override fun onBackPressed() {
             val intent = Intent(this, MainMenu::class.java)
             startActivity(intent)
             finish()
         }
    }

