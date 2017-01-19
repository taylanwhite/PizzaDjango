package com.example.taylanwhite.pizzadjango.view

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import com.example.taylanwhite.pizzadjango.App
import com.example.taylanwhite.pizzadjango.R
import com.example.taylanwhite.pizzadjango.models.*
import kotlinx.android.synthetic.main.activity_complete_pizza.*
import java.text.DecimalFormat
import java.util.*
import android.content.SharedPreferences
import android.preference.PreferenceManager.getDefaultSharedPreferences
import android.preference.PreferenceManager
import com.example.taylanwhite.pizzadjango.presenter.PizzaService
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CompletePizza : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complete_pizza)
        val idVeggieList = ArrayList<Int>()
        val currentLayout = findViewById(R.id.activity_complete_pizza) as RelativeLayout
        val scrollLayout = findViewById(R.id.activity_scroll_view) as ScrollView
        scrollLayout.setBackgroundColor(Color.parseColor("#D2B48C"))
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
        mTitle.text = " Add to Order "
        val image_Toppings = findViewById(R.id.image_customers) as ImageView
        App.picasso.load(R.mipmap.pizza3).into(image_Toppings)
        val mHome = mCustomView.findViewById(R.id.txtHome) as ImageButton
        val btnNext = findViewById(R.id.txtNext) as ImageButton
        val txtDetails = findViewById(R.id.txt_order_details) as TextView
        val chosenPizza = findViewById(R.id.txt_chosen_pizza) as TextView
        val extrasChosen = findViewById(R.id.txt_extras) as TextView
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
        var extraList = extras.getParcelableArrayList<ExtrasResults>("extrasList")
        var toppingList = ArrayList<String>()
        val extrasChosenList = ArrayList<String>()
        var veggiePrices = 0.00
        var meatPrices = 0.00
        var extraPrices = 0.00
        var priceList = 0.00


        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        var token: String = preferences.getString("token", "")


        chosenPizza.text = pizzaSelected[0].name


//        if(pizzaSelected[0].name == "Build Your Own") {

        for (i in 0..veggieSelected.size - 1) {
            veggiePrices += veggieSelected[i].price!!.toDouble()
        }
        for (i in 0..meatList.size - 1) {
            meatPrices += meatList[i].price!!.toDouble()
        }
        for (i in 0..extraList.size - 1) {
            extraPrices += extraList[i].price!!.toDouble()
        }

        sizeSelected[0].price
        priceList = sizeSelected[0].price!!.toDouble() + veggiePrices + meatPrices + extraPrices


//        }


        txt_size.text = sizeSelected[0].size
        txt_sauce.text = sauceSelected[0].name
        txt_crust.text = crustSelected[0].name

        val toppingBuilder = StringBuilder()
        val extraBuilder = StringBuilder()
        for (i in 0..meatList.size - 1) {

            meatList[i].name?.let { toppingList.add(it) }

        }
        for (i in 0..veggieSelected.size - 1) {

            veggieSelected[i].name?.let { toppingList.add(it) }
        }
        for (i in 0..extraList.size - 1){

            extraList[i].name?.let { extrasChosenList.add(it) }
        }



        extraList.map { it?.name }.forEachIndexed { i, it ->

            extraBuilder.append(it)
            if(i != extraList.lastIndex)
            {
                extraBuilder.append(", ")
            }

        }

        toppingList.forEachIndexed { i, it ->

            toppingBuilder.append(it)
            if(i != toppingList.lastIndex)
            {
                toppingBuilder.append(", ")
            }

        }

        val decim = DecimalFormat("0.00")
        val priceFormatted = decim.format(priceList)
        if (meatList != null && veggieSelected != null) {
            txt_toppings.text = "Toppings: " + toppingBuilder.toString()
        } else {
            txt_toppings.text = "Toppings: None"
        }
        if (extraList != null) {
            txt_extras.text = "Extras: " + extraBuilder.toString()
        } else {
            txt_extras.text = "Extras: None"
        }


        txt_price.text = "$" + priceFormatted.toString()


        btnNext.setOnClickListener {
            val preferences = PreferenceManager.getDefaultSharedPreferences(this)
            var token: String = preferences.getString("token", "")
            val userID: String = preferences.getString("userID", "")


            PizzaService.api.postUser(token, CompleteOrder(user = userID, price = priceFormatted, name = pizzaSelected[0].name as String, veggieToppings = veggieSelected.map { it.id!! }, size = sizeSelected.map { it.id!! }, meatToppings = meatList.map { it.id!! }, sauceToppings = sauceSelected.map { it.id!! }, crustType = crustSelected.map { it.id!! }, extra = extraList.map { it?.id ?: 0 }
            ) ).enqueue(object : Callback<CompleteOrder> {
                override fun onFailure(call: Call<CompleteOrder>?, t: Throwable?) {
                    //To change body of created functions use File | Settings | File Templates.
                    val connectionError = "Could not connect to service. (Are you connected to the internet?)"
                    Toast.makeText(this@CompletePizza, connectionError, Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<CompleteOrder>?, response: Response<CompleteOrder>?) {
                    if (response?.isSuccessful ?: false) {
                        response?.body()?.let { response ->


                            val intent = Intent(this@CompletePizza, MainMenu::class.java)
                            startActivity(intent)
                            finish()
                            Toast.makeText(this@CompletePizza, "Sending pizza to server", Toast.LENGTH_LONG).show()

                        }
                    } else {
                        Toast.makeText(this@CompletePizza, response?.errorBody()?.string(), Toast.LENGTH_LONG).show()

                    }
                }

            })
        }
    }
}
