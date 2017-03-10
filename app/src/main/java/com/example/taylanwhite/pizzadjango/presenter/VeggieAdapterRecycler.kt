package izeni.pizza.presenter

import android.graphics.Color
import android.os.Parcelable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.taylanwhite.pizzadjango.R
import com.example.taylanwhite.pizzadjango.models.DietListResults
import com.example.taylanwhite.pizzadjango.models.SpecializedPizzaResults
import com.example.taylanwhite.pizzadjango.models.VeggieToppingResults
import java.util.*


class VeggieAdapterRecycler(val idVeggieList: ArrayList<VeggieToppingResults>, val pizza_ID: ArrayList<SpecializedPizzaResults>, private val veggieList: List<VeggieToppingResults>, val crustDietaryRestrictions: ArrayList<DietListResults>) : RecyclerView.Adapter<VeggieAdapterRecycler.MyViewHolder>() {


    inner class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var VeggieName: TextView
        var i = 0


        init {
            VeggieName = view.findViewById(R.id.pizza_name) as TextView
        }

        fun bind(topping: VeggieToppingResults) {
            try {
                for (item in pizza_ID[0].veggieToppings) {

                    if (topping.name == pizza_ID[0].veggieToppings[i].name) {
                        VeggieName.setTextColor(Color.GRAY)
                        VeggieName.text = topping.name
                        topping.selected = true
                        idVeggieList.add(topping)
                    }
                    else
                    {
                        VeggieName.setTextColor(Color.WHITE)
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

                    crustDietaryRestrictions[t].veggieToppings.forEach {
                        item ->
                        if (topping.id == item) {

                            VeggieName.setTextColor(Color.RED)

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
                    //VeggieName.text ="✓ " + topping.name + " ✓"
                    VeggieName.setTextColor(Color.GRAY)
                    idVeggieList.add(topping)

                }
                else
                {
                    topping.selected = false
                    VeggieName.text =topping.name
                    VeggieName.setTextColor(Color.WHITE)
                    idVeggieList.remove(topping)

                }
            }

            VeggieName.setBackgroundColor(0xffffff)
            //holder.deal_layout.RelativeLayout = .setBackgroundColor(Color.parseColor(fastBackground))

            VeggieName.text = topping.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.pizza_list_row, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(veggieList[position])
    }

    override fun getItemCount(): Int {
        return veggieList.size
    }

    override fun getItemViewType(position: Int): Int {

        return position
    }
}













