package com.example.taylanwhite.pizzadjango.presenter

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.taylanwhite.pizzadjango.R
import com.example.taylanwhite.pizzadjango.models.*
import java.util.*

/**
 * Created by taylanwhite on 1/16/17.
 */
class ExtrasAdapterRecycler(val idExtraList: ArrayList<ExtrasResults>, private val extraList: List<ExtrasResults>, val crustDietaryRestrictions: ArrayList<DietListResults>) : RecyclerView.Adapter<ExtrasAdapterRecycler.MyViewHolder>() {



    inner class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var extrasName: TextView
        var i = 0


        init {
            extrasName = view.findViewById(R.id.pizza_name) as TextView
        }

        fun bind(topping: ExtrasResults) {
            extrasName.setTextColor(Color.WHITE)
            try {
                idExtraList[0].name?.forEach { item ->
                    if (topping.name == idExtraList[0].name) {
                        extrasName.setTextColor(Color.GRAY)
                        extrasName.text = topping.name
                        topping.selected = true
                        idExtraList.add(topping)
                    }
                    i++
                }


            } catch (e: IndexOutOfBoundsException) {
            }


            var v = 0
            var t = 0
            try {
                for (z in 0..crustDietaryRestrictions.size - 1)
                {

                    crustDietaryRestrictions[t].extra.forEach {
                        item ->
                        if (topping.id == item) {

                            extrasName.setTextColor(Color.RED)


                        }
                        v++
                    }
                    t++
                }
            }
            catch (e:Exception) {
                println("Error " + e.message)
            }


            view.setOnClickListener {



                if (!(topping.selected))
                {
                    topping.selected = true
                    //extrasName.text ="✓ " + topping.name + " ✓"
                    extrasName.setTextColor(Color.GRAY)
                    idExtraList.add(topping)

                }
                else
                {
                    topping.selected = false
                    extrasName.text = topping.name
                    extrasName.setTextColor(Color.WHITE)
                    idExtraList.remove(topping)

                }
            }

//            extrasName.setBackgroundColor(0xffffff)
            //holder.deal_layout.RelativeLayout = .setBackgroundColor(Color.parseColor(fastBackground))

            extrasName.text = topping.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.pizza_list_row, parent, false)


        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(extraList[position])
    }

    override fun getItemCount(): Int {
        return extraList.size
    }

    override fun getItemViewType(position: Int): Int {

        return position
    }
}