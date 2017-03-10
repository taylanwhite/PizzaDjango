package com.example.taylanwhite.pizzadjango.view

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.*
import com.example.taylanwhite.pizzadjango.App
import com.example.taylanwhite.pizzadjango.R
import com.example.taylanwhite.pizzadjango.models.*
import com.example.taylanwhite.pizzadjango.presenter.CrustListAdapter
import com.example.taylanwhite.pizzadjango.presenter.PizzaService
import com.example.taylanwhite.pizzadjango.presenter.SauceListAdapter
import com.example.taylanwhite.pizzadjango.presenter.SizeListAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class SauceOptions : AppCompatActivity() {

    val sauceListDisplay = ArrayList<SauceTypeResults>()
    var listView: ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_pizza)

        listView = findViewById(R.id.list_of_pizzas) as ListView?
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
        mTitle.text = " Choose a Sauce "
        val mHome = mCustomView.findViewById(R.id.txtHome) as ImageView
        val txtNext = findViewById(R.id.txtNext) as ImageView
        txtNext.visibility = View.GONE
        mTitle.setTextColor(Color.parseColor("#BDBDBD"))

        mHome.setOnClickListener {
            val intent = Intent(this, MainMenu::class.java)
            startActivity(intent)
            finish()
        }
        val extras = intent.extras
        var pizzaSelected = extras.getParcelableArrayList<SpecializedPizzaResults>("pizzaID")
        var crustSelected = extras.getParcelableArrayList<CrustTypeResults>("crustID")
        var sizeSelected = extras.getParcelableArrayList<SizeResults>("sizeID")
        var dietRestrictions = extras.getParcelableArrayList<DietListResults>("dietID")



        if(pizzaSelected[0].name != "Build Your Own") {
            val dialog = Dialog(this@SauceOptions)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.custom_dialog_layout)
            dialog.show()
            val txtPreToppings = dialog.findViewById(R.id.textView4) as? TextView
            val btnOkay = dialog.findViewById(R.id.btn_okay) as? Button
            txtPreToppings?.setTextColor(Color.WHITE)
            btnOkay!!.text = "Sounds Good!"
            txtPreToppings!!.text = "The Sauce and Toppings for the " + pizzaSelected[0].name + " Pizza" + " have been pre-selected " +
                    "but you can add or change whatever you wish!"

            btnOkay.setOnClickListener {
                dialog.hide()
            }
        }

            listView?.adapter = SauceListAdapter(this@SauceOptions, pizzaSelected, crustSelected, sizeSelected, sauceListDisplay, dietRestrictions)
            getSauce()






    }

    private fun getSauce(page:Int? = null)
    {
        var subStr = ""
        PizzaService.api.getSauce().enqueue(object: Callback<SauceType> {
            override fun onFailure(call: Call<SauceType>?, t: Throwable?) {

                val connectionError = "Could not connect to service. (Are you connected to the internet?)"
                Toast.makeText(this@SauceOptions, connectionError, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<SauceType>?, response: Response<SauceType>?) {
                if (response?.isSuccessful ?: false) {
                    response?.body()?.let { response ->


                        if(response.next != null) {

                            subStr = response.next.substring(response.next.length - 1)
                        }


                        if(response.next != null)
                        {
                            getSauce(subStr.toInt())
//                           page = response.next
                        }


                        response.results?.forEach { i ->
                            sauceListDisplay.add(i)
                            (listView?.adapter as SauceListAdapter?)?.notifyDataSetChanged()
                        }
//                        crustList



                    }
                }


            }

        })

    }
}
