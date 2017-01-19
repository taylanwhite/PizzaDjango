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

class PastOrdersComplete : AppCompatActivity() {

    val completeExtras = ArrayList<String>()

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
        mTitle.text = " Order Info "
        val image_Toppings = findViewById(R.id.image_customers) as ImageView
        App.picasso.load(R.mipmap.pizza3).into(image_Toppings)
        val mHome = mCustomView.findViewById(R.id.txtHome) as ImageButton
        val mBack = mCustomView.findViewById(R.id.txtNext) as ImageButton
        val btnNext = findViewById(R.id.txtNext) as ImageButton
        val txtDetails = findViewById(R.id.txt_order_details) as TextView
        val txtName = findViewById(R.id.txt_chosen_pizza) as TextView
        val txtExtras = findViewById(R.id.txt_extras) as TextView
        val txtPrice = findViewById(R.id.txt_price) as TextView
        val txtSize = findViewById(R.id.txt_size) as TextView
        val txtSauce = findViewById(R.id.txt_sauce) as TextView
        val txtCrust = findViewById(R.id.txt_crust) as TextView
        val txtToppings = findViewById(R.id.txt_toppings) as TextView

        mHome.setOnClickListener {
            val intent = Intent(this, MainMenu::class.java)
            startActivity(intent)
            finish()
        }
        mBack.setOnClickListener {
            val intent = Intent(this, PastOrders::class.java)
            startActivity(intent)
            finish()
        }


        val extras = intent.extras
        var name = extras.getString("name")
        var price = extras.getString("price")
        var crust = extras.getString("crust")
        var sauce = extras.getString("sauce")
        var size = extras.getString("size")
        var meatToppings = extras.getParcelableArrayList<MeatToppingResults>("meatToppings")
        var veggieToppings = extras.getParcelableArrayList<VeggieToppingResults>("veggieToppings")
        var extrasList = extras.getParcelableArrayList<ExtrasResults>("extras")
        var toppingList = ArrayList<String>()
        val completeExtras = ArrayList<String>()

        val toppingBuilder = StringBuilder()
        val extraBuilder = StringBuilder()
        for (i in 0..meatToppings.size - 1) {

            meatToppings[i].name?.let { toppingList.add(it) }

        }
        for (i in 0..veggieToppings.size - 1) {

            veggieToppings[i].name?.let { toppingList.add(it) }
        }
        for (i in 0..extrasList.size - 1){

            extrasList[i].name?.let { completeExtras.add(it) }
        }


        completeExtras.map { it }.forEachIndexed { i, it ->

            extraBuilder.append(it)
            if(i != completeExtras.lastIndex)
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


        if (meatToppings != null && veggieToppings != null) {
            txtToppings.text = "Toppings: " + toppingBuilder.toString()
        } else {
            txtToppings.text = "Toppings: None"
        }
        if (extrasList != null) {
            txtExtras.text = "Extras: " + extraBuilder.toString()
        } else {
            txtExtras.text = "Extras: None"
        }


        txtName.text = name
        txtPrice.text = "$$price"
        txtCrust.text = crust
        txtSauce.text = sauce
        txtSize.text = size


    }





}
