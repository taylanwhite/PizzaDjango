package com.example.taylanwhite.pizzadjango


import android.app.Application
import com.squareup.picasso.Picasso

class App : Application() {

    companion object {
        lateinit var instance: Application
        lateinit var picasso: Picasso
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
        picasso = Picasso.with(App.instance)

    }
}