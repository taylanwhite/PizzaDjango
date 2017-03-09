package com.example.taylanwhite.pizzadjango.presenter

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.taylanwhite.pizzadjango.R
import com.example.taylanwhite.pizzadjango.models.DietListResults
import com.example.taylanwhite.pizzadjango.models.MeatToppingResults
import com.example.taylanwhite.pizzadjango.models.SpecializedPizzaResults
import java.util.*

/**
 * Created by taylanwhite on 2/15/17.
 */
class TestFragmentRecycler(val pizza_ID: ArrayList<MeatToppingResults>) : RecyclerView.Adapter<TestFragmentRecycler.MyViewHolder>() {


    inner class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var meatName: TextView
        var i = 0
        var ii = 0


        init {
            meatName = view.findViewById(R.id.pizza_name) as TextView
        }

        fun bind(topping: MeatToppingResults) {


//            meatName.setBackgroundColor(0xffffff)
            //holder.deal_layout.RelativeLayout = .setBackgroundColor(Color.parseColor(fastBackground))

            meatName.text = topping.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.pizza_list_row, parent, false)


        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(pizza_ID[position])
    }

    override fun getItemCount(): Int {
        return pizza_ID.size
    }

    override fun getItemViewType(position: Int): Int {

        return position
    }

}



