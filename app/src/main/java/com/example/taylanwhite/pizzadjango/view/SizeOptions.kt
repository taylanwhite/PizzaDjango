package com.example.taylanwhite.pizzadjango.view

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import com.example.taylanwhite.pizzadjango.R
import com.example.taylanwhite.pizzadjango.models.CrustTypeResults
import com.example.taylanwhite.pizzadjango.models.Size
import com.example.taylanwhite.pizzadjango.models.SizeResults
import com.example.taylanwhite.pizzadjango.models.SpecializedPizzaResults
import com.example.taylanwhite.pizzadjango.presenter.CrustListAdapter
import com.example.taylanwhite.pizzadjango.presenter.SizeListAdapter
import com.example.taylanwhite.pizzadjango.presenter.PizzaService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class SizeOptions : AppCompatActivity() {

    val sizeListDisplay = ArrayList<SizeResults>()
    var listView: ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_pizza)
        listView = findViewById(R.id.list_of_pizzas) as ListView?
        val currentLayout = findViewById(R.id.activity_select_pizza) as LinearLayout
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
        mTitle.text = " Choose a Size "
        val mHome = mCustomView.findViewById(R.id.txtHome) as ImageButton
        val txtNext = findViewById(R.id.txtNext) as ImageButton
        txtNext.visibility = View.GONE
        mHome.setOnClickListener {
            val intent = Intent(this, MainMenu::class.java)
            startActivity(intent)
            finish()
        }
        val extras = intent.extras
        var pizzaSelected = extras.getParcelableArrayList<SpecializedPizzaResults>("pizzaID")
        var crustSelected = extras.getParcelableArrayList<CrustTypeResults>("crustID")


        listView?.adapter = SizeListAdapter(this@SizeOptions, pizzaSelected, crustSelected, sizeListDisplay)


        getSize()


    }

    private fun getSize(page:Int? = null){
        var subStr = ""
        PizzaService.api.getSize(page).enqueue(object: Callback<Size> {
            override fun onFailure(call: Call<Size>?, t: Throwable?) {
                throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<Size>?, response: Response<Size>?) {
                if (response?.isSuccessful ?: false) {
                    response?.body()?.let { response ->

//                        val sizeList = ArrayList<SizeResults>()
//
//                        response.results!!.forEach { i ->
//                            sizeList.add(i.apply {})
//                        }

                        if(response.next != null) {
                            subStr = response.next.substring(response.next.length - 1)
                        }


                        if(response.next != null)
                        {
                            getSize(subStr.toInt())
//                           page = response.next
                        }

                        response.results?.forEach { i ->
                            sizeListDisplay.add(i)
                            (listView?.adapter as SizeListAdapter?)?.notifyDataSetChanged()
                        }

                    }
                }
            }
        })

    }
}
