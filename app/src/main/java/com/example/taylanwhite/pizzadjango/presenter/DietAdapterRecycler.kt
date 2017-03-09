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
 * Created by taylanwhite on 1/30/17.
 */
class DietAdapterRecycler(private val dietList: List<DietListResults>, private val idDietList: ArrayList<DietListResults>, val preChosenDiets: ArrayList<DietListResults>) : RecyclerView.Adapter<DietAdapterRecycler.MyViewHolder>() {



    inner class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var pizzaName: TextView
        var i = 0


        init {
            pizzaName = view.findViewById(R.id.pizza_name) as TextView

        }

        fun bind(dietDetails: DietListResults) {


            try {

                preChosenDiets.forEach { item ->
                    if (dietDetails.name == item.name) {
                        pizzaName.setTextColor(Color.GRAY)
                        //                        pizzaName.text = dietDetails.name
                        dietDetails.selected = true
                        idDietList.add(dietDetails)

                    }
                }



            } catch (e: IndexOutOfBoundsException) {
            }

            view.setOnClickListener {

                if (!(dietDetails.selected))
                {
                    dietDetails.selected = true
                    //VeggieName.text ="✓ " + dietDetails.name + " ✓"
                    pizzaName.setTextColor(Color.GRAY)
                    idDietList.add(dietDetails)

                }
                else
                {
                    dietDetails.selected = false
                    pizzaName.text = dietDetails.name
                    pizzaName.setTextColor(Color.BLACK)
                    idDietList.remove(dietDetails)

                }
            }

//            pizzaName.setTextColor(Color.BLACK)
            pizzaName.text = dietDetails.name
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.pizza_list_row, parent, false)


        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(dietList[position])
    }

    override fun getItemCount(): Int {
        return dietList.size
    }

    override fun getItemViewType(position: Int): Int {

        return position
    }


}