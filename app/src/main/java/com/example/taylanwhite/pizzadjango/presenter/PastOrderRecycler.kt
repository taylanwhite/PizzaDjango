package com.example.taylanwhite.pizzadjango.presenter

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.taylanwhite.pizzadjango.R
import com.example.taylanwhite.pizzadjango.models.MeatToppingResults
import com.example.taylanwhite.pizzadjango.models.PastOrder
import com.example.taylanwhite.pizzadjango.models.PastOrderResults
import java.util.*

/**
 * Created by taylanwhite on 1/17/17.
 */

class PastOrderRecycler(private val orderList: List<PastOrderResults>) : RecyclerView.Adapter<PastOrderRecycler.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var pizzaName: TextView
        var pizzaSize: TextView
        var pizzaPrice: TextView

        init {
            pizzaName = view.findViewById(R.id.pizza_name) as TextView
            pizzaSize = view.findViewById(R.id.pizza_size) as TextView
            pizzaPrice = view.findViewById(R.id.pizza_price) as TextView
        }

        fun bind(orderDetails: PastOrderResults) {
            orderDetails.name
            pizzaName.setTextColor(Color.BLACK)
            pizzaSize.setTextColor(Color.GRAY)
            pizzaPrice.setTextColor(Color.GRAY)
            pizzaName.text = orderDetails.name + "                                                 "
            pizzaSize.text = orderDetails.size[0].size
            pizzaPrice.text = "$ " + orderDetails.price
//            pizzaName.text = "test"
//            pizzaSize.text = "test"
//            pizzaPrice.text = "test"


        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.order_list, parent, false)


        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(orderList[position])
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    override fun getItemViewType(position: Int): Int {

        return position
    }
}