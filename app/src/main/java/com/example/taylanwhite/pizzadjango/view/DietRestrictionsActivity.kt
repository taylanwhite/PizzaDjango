package com.example.taylanwhite.pizzadjango.view

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import com.example.taylanwhite.pizzadjango.R
import com.example.taylanwhite.pizzadjango.models.*
import com.example.taylanwhite.pizzadjango.presenter.DietAdapterRecycler
import com.example.taylanwhite.pizzadjango.presenter.PizzaService
import izeni.pizza.presenter.DividerItemDecoration
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import android.util.Log
import android.widget.*


class DietRestrictionsActivity : AppCompatActivity() {

    lateinit var mAdapter: DietAdapterRecycler

    lateinit var recyclerView: RecyclerView
    val dietList = ArrayList<DietListResults>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meat_options)
        val idDietList = ArrayList<DietListResults>()
        var chosenDiets = DietListResults()
        val completeChosenDiets = ArrayList<DietListResults>()
        val chosenDietNames = ArrayList<String>()
        val currentLayout = findViewById(R.id.activity_meat_options) as RelativeLayout
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
        mTitle.text = "       Dietary Restrictions "
        val mBack = mCustomView.findViewById(R.id.txtHome) as ImageView
        val btnNext = findViewById(R.id.txtNext) as ImageView
        val btnBack = findViewById(R.id.txtHome) as ImageView
        btnBack.setImageResource(R.mipmap.back_arrow)
        mTitle.setTextColor(Color.parseColor("#BDBDBD"))

        mBack.setBackgroundResource(R.mipmap.back_arrow)
        btnNext.visibility = View.GONE
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = preferences.edit()
        val token: String = preferences.getString("token", "")
        val userID: String = preferences.getString("userID", "")



        //retrieve values
             //EDIT: gso to gson

        mBack.setOnClickListener {

            PizzaService.api.getChosenDietsId("Token " + token).enqueue(object : Callback<UserDietsDeleteResults> {
                override fun onFailure(call: Call<UserDietsDeleteResults>?, t: Throwable?) {
                    val connectionError = "Error(GCDI)"
                    Toast.makeText(this@DietRestrictionsActivity, connectionError, Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<UserDietsDeleteResults>?, response: Response<UserDietsDeleteResults>?) {
                    if (response?.isSuccessful ?: false) {
                        response?.body()?.let { response ->



                                try{
                                    val id = response.results[0].id
                                    PizzaService.api.deletePastDiets(id.toString(), "Token " + token).enqueue(object : Callback<UserDietsDeleteResults> {
                                        override fun onFailure(call: Call<UserDietsDeleteResults>?, t: Throwable?) {
                                            val connectionError = "Error(DPD)"
                                            Toast.makeText(this@DietRestrictionsActivity, connectionError, Toast.LENGTH_SHORT).show()
                                            Log.d("onFailure", connectionError, t)
                                        }

                                        override fun onResponse(call: Call<UserDietsDeleteResults>?, response: Response<UserDietsDeleteResults>?) {
                                            if (response?.isSuccessful ?: false) {

                                                response?.body()?.let { response ->



                                                }
                                            }
                                        }

                                    })
                                } catch (e: IndexOutOfBoundsException) {
                                }
                        }
                    }
                }

            })

            PizzaService.api.postUserDiets("Token " + token, UserDiets(id = 0, user = userID, dietaryRestrictionsList = idDietList.map { it.id!! }
            ) ).enqueue(object : Callback<UserDiets> {
                override fun onFailure(call: Call<UserDiets>?, t: Throwable?) {
                    //To change body of created functions use File | Settings | File Templates.
                    val connectionError = "Could not connect to service. (Are you connected to the internet?)"
                    Toast.makeText(this@DietRestrictionsActivity, connectionError, Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<UserDiets>?, response: Response<UserDiets>?) {
                    if (response?.isSuccessful ?: false) {
                        response?.body()?.let { response ->

                            Toast.makeText(this@DietRestrictionsActivity, "Updated Dietary Information", Toast.LENGTH_LONG).show()

                        }
                    } else {
                        Toast.makeText(this@DietRestrictionsActivity, response?.errorBody()?.string(), Toast.LENGTH_LONG).show()
                    }
                }
            })


            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }



        PizzaService.api.getChosenDiets("Token " + token).enqueue(object : Callback<GetUserDietsResults> {
            override fun onFailure(call: Call<GetUserDietsResults>?, t: Throwable?) {
                val connectionError = "Error(GCD)" + t
                Toast.makeText(this@DietRestrictionsActivity, connectionError, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<GetUserDietsResults>?, response: Response<GetUserDietsResults>?) {
                if (response?.isSuccessful ?: false) {
                    response?.body()?.let { response ->
                                var i = 0
                        try {
                            response.results[0].dietaryRestrictionsList.forEach {
                                item ->
                                chosenDiets = DietListResults()
                                chosenDiets.name = item.name
                                chosenDiets.selected = item.selected
                                chosenDiets.id = item.id
                                chosenDiets.meatToppings = item.meatToppings
                                chosenDiets.veggieToppings = item.veggieToppings
                                chosenDiets.sauceToppings = item.sauceToppings
                                completeChosenDiets.add(i, chosenDiets)
                                i++


                            }
                        }
                        catch (e: IndexOutOfBoundsException) {
                        }


                        }
                    }
                }
            })

        mAdapter = DietAdapterRecycler(dietList, idDietList, completeChosenDiets)
        recyclerView = findViewById(R.id.recycler_view) as RecyclerView
        val mLayoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        recyclerView.adapter = mAdapter
        getDiets()


    }

    fun getDiets(page: Int? = null) {
        var subStr = ""


        PizzaService.api.getDietList(page).enqueue(object : Callback<DietList> {
            override fun onFailure(call: Call<DietList>?, t: Throwable?) {
                val connectionError = "Could not connect to service. (Are you connected to the internet?)"
                Toast.makeText(this@DietRestrictionsActivity, connectionError, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<DietList>?, response: Response<DietList>?) {
                if (response?.isSuccessful ?: false) {
                    response?.body()?.let { response ->

                        if (response.next != null) {

                            subStr = response.next.substring(response.next.length - 1)

                        }


                        if (response.next != null) {
                            getDiets(subStr.toInt())
//                           page = response.next
                        }

                        for (item in response.results) {
                            dietList.add(item)
                            //Collections.reverse(pizzaList)

                            mAdapter.notifyDataSetChanged()


                        }
                    }
                }
            }

        })
    }
    override fun onBackPressed() {
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
        finish()
    }
}
