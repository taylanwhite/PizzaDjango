package izeni.pizza.presenter

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.taylanwhite.pizzadjango.R
import com.example.taylanwhite.pizzadjango.models.MeatToppingResults
import com.example.taylanwhite.pizzadjango.models.SpecializedPizzaResults
import java.util.*


class MeatAdapterRecycler(val idMeatList: ArrayList<MeatToppingResults>, val pizza_ID: ArrayList<SpecializedPizzaResults>, private val meatList: List<MeatToppingResults>) : RecyclerView.Adapter<MeatAdapterRecycler.MyViewHolder>() {


    inner class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var meatName: TextView
        var i = 0


        init {
            meatName = view.findViewById(R.id.pizza_name) as TextView
        }

        fun bind(topping: MeatToppingResults) {

            try {
                for (item in pizza_ID[0].meatToppings) {

                    if (topping.name == pizza_ID[0].meatToppings[i].name) {
                        meatName.setTextColor(Color.GRAY)
                        meatName.text = topping.name
                        topping.selected = true
                        idMeatList.add(topping)
                    }
                    i++
                }


            } catch (e: IndexOutOfBoundsException) {
            }


            view.setOnClickListener {



                if (!(topping.selected))
                {
                    topping.selected = true
                    //meatName.text ="✓ " + topping.name + " ✓"
                    meatName.setTextColor(Color.GRAY)
                    idMeatList.add(topping)

                }
                else
                {
                   topping.selected = false
                    meatName.text = topping.name
                    meatName.setTextColor(Color.BLACK)
                    idMeatList.remove(topping)

                }
            }

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
        holder.bind(meatList[position])
    }

    override fun getItemCount(): Int {
        return meatList.size
    }

    override fun getItemViewType(position: Int): Int {

        return position
    }

}













