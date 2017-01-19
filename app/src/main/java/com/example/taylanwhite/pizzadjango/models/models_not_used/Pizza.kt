package izeni.pizza.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*


class Pizza() {
    var id: String? = null

    var toppings: List<String> = ArrayList()

    var extras: List<String> = ArrayList()

    var price: String? = null

    var crust: String? = null

    var size: String? = null

    var sauce: String? = null

}





