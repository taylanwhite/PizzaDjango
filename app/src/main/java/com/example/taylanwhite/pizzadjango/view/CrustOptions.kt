package com.example.taylanwhite.pizzadjango.view

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.*
import com.example.taylanwhite.pizzadjango.R
import com.example.taylanwhite.pizzadjango.models.*
import com.example.taylanwhite.pizzadjango.presenter.CrustListAdapter
import com.example.taylanwhite.pizzadjango.presenter.PizzaListAdapter
import com.example.taylanwhite.pizzadjango.presenter.PizzaService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan



class CrustOptions : AppCompatActivity() {
    val pizzaSelected: ArrayList<SpecializedPizzaResults> = arrayListOf()
    val crustListDisplay = ArrayList<CrustTypeResults>()
    val completeChosenDiets = ArrayList<DietListResults>()
    var listView: ListView? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_pizza)
        listView = findViewById(R.id.list_of_pizzas) as ListView?
//        val crustList : CrustTypeResults
        val currentLayout = findViewById(R.id.activity_select_pizza) as LinearLayout
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
        mTitle.text = " Choose a Crust "
        mTitle.setTextColor(Color.parseColor("#BDBDBD"))
        val mHome = mCustomView.findViewById(R.id.txtHome) as ImageView
        val txtNext = findViewById(R.id.txtNext) as ImageView
        var chosenDiets = DietListResults()



        txtNext.visibility = View.GONE
        mHome.setOnClickListener {
            val intent = Intent(this, MainMenu::class.java)
            startActivity(intent)
            finish()
        }

        if(intent.extras != null) {
            val extras = intent.extras
            if (extras.containsKey("pizzaID")) {
                pizzaSelected.addAll(extras.getParcelableArrayList<SpecializedPizzaResults>("pizzaID"))
            }
        }



        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = preferences.edit()
        val token: String = preferences.getString("token", "")
        PizzaService.api.getChosenDiets("Token " + token).enqueue(object : Callback<GetUserDietsResults> {
            override fun onFailure(call: Call<GetUserDietsResults>?, t: Throwable?) {
                val connectionError = "Error(GCD)"
                Toast.makeText(this@CrustOptions, connectionError, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<GetUserDietsResults>?, response: Response<GetUserDietsResults>?) {
                if (response?.isSuccessful ?: false) {
                    response?.body()?.let { response ->
                        var i = 0
                        response.results[0].dietaryRestrictionsList.forEach {
                            item ->
                            chosenDiets = DietListResults()
                            chosenDiets.name = item.name
                            chosenDiets.selected = item.selected
                            chosenDiets.id = item.id
                            chosenDiets.crustType = item.crustType
                            chosenDiets.meatToppings = item.meatToppings
                            chosenDiets.veggieToppings = item.veggieToppings
                            chosenDiets.sauceToppings = item.sauceToppings
                            chosenDiets.extra = item.extra
                            completeChosenDiets.add(i, chosenDiets)
                            i++

                        }
                        val toppingBuilder = StringBuilder()
                        completeChosenDiets.forEachIndexed { i, it ->

                            if(completeChosenDiets.size == 1) {
                                toppingBuilder.append(completeChosenDiets[0].name)
                            }
                            else{
                                if (i == completeChosenDiets.lastIndex) {
                                    toppingBuilder.append("and ")
                                    toppingBuilder.append(completeChosenDiets[i].name)
                                } else toppingBuilder.append(completeChosenDiets[i].name)

                                if (i != completeChosenDiets.lastIndex) {
                                    toppingBuilder.append(", ")
                                }
                            }


                        }
                        if(completeChosenDiets.isNotEmpty()) {
                            val dialog = Dialog(this@CrustOptions)
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                            dialog.setContentView(R.layout.custom_dialog_layout)
                            dialog.show()
                            val txtPreToppings = dialog.findViewById(R.id.textView4) as? TextView
                            txtPreToppings?.setTextColor(Color.WHITE)
                            val WordtoSpan = SpannableString("Toppings and Sauces to avoid have been highlighted in RED for $toppingBuilder diets but you can still select any of these.")
                            WordtoSpan.setSpan(ForegroundColorSpan(Color.RED), 54, 57,
                                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                            txtPreToppings?.text = WordtoSpan
                            val btnOkay = dialog.findViewById(R.id.btn_okay) as? Button
                            btnOkay!!.text = "Awesome!"
//                            txtPreToppings?.text = "Toppings and Sauces have been highlighted in RED $toppingBuilder but you can still select any of these."


                            btnOkay.setOnClickListener {
                                dialog.dismiss()
                            }
                        }
                    }
                }
            }
        })


        listView?.adapter = CrustListAdapter(this@CrustOptions, pizzaSelected, crustListDisplay, completeChosenDiets)



        //api call
        getCrusts()

    }


    private fun getCrusts(page:Int? = null) {
        var subStr = ""
        PizzaService.api.getCrusts(page).enqueue(object : Callback<CrustType> {
            override fun onFailure(call: Call<CrustType>?, t: Throwable?) {

                val connectionError = "Could not connect to service. (Are you connected to the internet?)"
                Toast.makeText(this@CrustOptions, connectionError, Toast.LENGTH_SHORT).show()

            }

            override fun onResponse(call: Call<CrustType>?, response: Response<CrustType>?) {
                if (response?.isSuccessful ?: false) {
                    response?.body()!!.let { response ->


                        if(response.next != null) {
                            subStr = response.next.substring(response.next.length - 1)

                        }


                       if(response.next != null)
                       {
                            getCrusts(subStr.toInt())
//                           page = response.next
                       }

                        response.results!!.forEach { i ->
                            crustListDisplay.add(i)
                            (listView?.adapter as CrustListAdapter?)?.notifyDataSetChanged()
                        }
//                        crustList
                    }
                }
            }
        })
    }





}
