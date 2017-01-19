package izeni.pizza.presenter

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import izeni.pizza.models.Customer
import izeni.pizza.models.Pizza
import izeni.pizza.models.PizzaObject
import java.util.*

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "izeni.pizza", null, 1) {

    companion object {
        //foreign key
        val PIZZA_ID = "PIZZA_ID"

        //Pizza Toppings
        val PEPPERONI = "PEPPERONI"
        val PHILLY_STEAK = "PHILLY_STEAK"
        val ITALIAN_SAUSAGE = "ITALIAN_SAUSAGE"
        val PREMIUM_CHICKEN = "PREMIUM_CHICKEN"
        val SLICED_ITALIAN_SAUSAGE = "SLICED_ITALIAN_SAUSAGE"
        val BEEF = "BEEF"
        val HAM = "HAM"
        val BACON = "BACON"
        val SALAMI = "SALAMI"
        val SARDINES = "SARDINES"
        val CHEDDAR = "CHEDDAR"
        val BANANA_PEPPERS = "BANANA_PEPPERS"
        val DICED_TOMATOES = "DICED_TOMATOES"
        val JALAPENO_PEPPERS = "JALAPENO_PEPPERS"
        val SHREDDED_PARMESAN = "SHREDDED_PARMESAN"
        val ONIONS = "ONIONS"
        val SPINACH = "SPINACH"
        val PINEAPPLE = "PINEAPPLE"
        val BLACK_OLIVES = "BLACK_OLIVES"
        val MUSHROOMS = "MUSHROOMS"

        //Customer info
        val CUSTOMER_NAME = "CUSTOMER_NAME"
        val CUSTOMER_EMAIL = "CUSTOMER_EMAIL"
        val CUSTOMER_PHONE = "CUSTOMER_PHONE"
        val CUSTOMER_ADDRESS = "CUSTOMER_ADDRESS"
        val CUSTOMER_ZIPCODE = "CUSTOMER_ZIPCODE"
        val CUSTOMER_STATUS = "CUSTOMER_STATUS"

        //EXTRA TO ORDER
        val EXTRA_BREADSTICKS = "EXTRA_BREADSTICKS"
        val EXTRA_CHEESESTICKS = "EXTRA_CHEESESTICKS"
        val EXTRA_CINNAMON_STICKS = "EXTRA_CINNAMON_STICKS"
        val EXTRA_RANCH_CUP = "EXTRA_RANCH_CUP"
        val EXTRA_BBQ_CUP = "EXTRA_BBQ_CUP"
        val EXTRA_GOOEY_BROWNIES = "EXTRA_GOOEY_BROWNIES"

        //PIZZA CRUST, SIZE, SAUCE
        val CRUST = "CRUST"
        val SIZE = "SIZE"
        val SAUCE = "SAUCE"


        //total price of pizza
        val PRICE = "PRICE"


    }

    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {


        //TABLE FOR PIZZA TOPPINGS
        sqLiteDatabase.execSQL("create table PIZZATOPPINGS(PIZZA_ID TEXT NOT NULL UNIQUE, ${PEPPERONI} TEXT, ${PHILLY_STEAK} TEXT, ${ITALIAN_SAUSAGE} TEXT, ${PREMIUM_CHICKEN} TEXT, ${SLICED_ITALIAN_SAUSAGE} TEXT, ${BEEF} TEXT,${HAM} TEXT,${BACON} TEXT,${SALAMI} TEXT, ${SARDINES} TEXT, ${CHEDDAR} TEXT, ${BANANA_PEPPERS} TEXT, ${DICED_TOMATOES} TEXT, ${JALAPENO_PEPPERS} TEXT, ${SHREDDED_PARMESAN} TEXT, ${ONIONS} TEXT, ${SPINACH} TEXT, ${PINEAPPLE} TEXT, ${BLACK_OLIVES} TEXT, ${MUSHROOMS} TEXT)" )
        //TABLE FOR CUSTOMER INFO
        sqLiteDatabase.execSQL("create table CUSTOMERINFO(PIZZA_ID TEXT NOT NULL UNIQUE, ${CUSTOMER_NAME} TEXT, ${CUSTOMER_EMAIL} TEXT, ${CUSTOMER_PHONE} TEXT, ${CUSTOMER_ADDRESS} TEXT, ${CUSTOMER_ZIPCODE} TEXT, ${CUSTOMER_STATUS} TEXT)")
        //TABLE FOR EXTRAS
        sqLiteDatabase.execSQL("create table EXTRAS(PIZZA_ID TEXT NOT NULL UNIQUE, ${EXTRA_BREADSTICKS} TEXT, ${EXTRA_CHEESESTICKS} TEXT, ${EXTRA_CINNAMON_STICKS} TEXT, ${EXTRA_RANCH_CUP} TEXT, ${EXTRA_BBQ_CUP} TEXT, ${EXTRA_GOOEY_BROWNIES} TEXT)")
        //table for prices
        sqLiteDatabase.execSQL("create table PRICE(PIZZA_ID TEXT NOT NULL UNIQUE, ${PRICE} TEXT)")
        //Table for crust,size,sauce of pizza
        sqLiteDatabase.execSQL("create table PIZZABASE(PIZZA_ID TEXT NOT NULL UNIQUE, ${SIZE} TEXT, ${CRUST} TEXT, ${SAUCE} TEXT)")




    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, i1: Int) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS PIZZATOPPINGS")
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS CUSTOMERINFO")
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS EXTRAS")
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS PRICE")
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS PIZZABASE")
        onCreate(sqLiteDatabase)
    }


    fun insert_toppings(pizzaID:String, pepperoni:String, phillySteak:String, italianSausage:String, premiumChicken:String, slicedItalianSausage:String, beef:String, ham:String, bacon:String, salami:String, sardines:String, cheddar:String, bananaPeppers:String, dicedTomatoes:String, jalapenoPeppers:String, shreddedParmesan:String, onions:String, spinach:String, pineapple:String, blackOlives:String, mushrooms:String) {
        var contentValues = ContentValues()
        contentValues.put("PIZZA_ID", pizzaID)
        contentValues.put(PEPPERONI, pepperoni)
        contentValues.put(PHILLY_STEAK, phillySteak)
        contentValues.put(ITALIAN_SAUSAGE, italianSausage)
        contentValues.put(PREMIUM_CHICKEN, premiumChicken)
        contentValues.put(SLICED_ITALIAN_SAUSAGE, slicedItalianSausage)
        contentValues.put(BEEF, beef)
        contentValues.put(HAM, ham)
        contentValues.put(BACON, bacon)
        contentValues.put(SALAMI, salami)
        contentValues.put(SARDINES, sardines)
        contentValues.put(CHEDDAR, cheddar)
        contentValues.put(BANANA_PEPPERS, bananaPeppers)
        contentValues.put(DICED_TOMATOES, dicedTomatoes)
        contentValues.put(JALAPENO_PEPPERS, jalapenoPeppers)
        contentValues.put(SHREDDED_PARMESAN, shreddedParmesan)
        contentValues.put(ONIONS, onions)
        contentValues.put(SPINACH, spinach)
        contentValues.put(PINEAPPLE, pineapple)
        contentValues.put(BLACK_OLIVES, blackOlives)
        contentValues.put(MUSHROOMS, mushrooms)

        this.writableDatabase.insertWithOnConflict("PIZZATOPPINGS", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE)

    }


    fun insert_customer_details(pizzaID:String, customerName:String, customerEmail:String, customerPhone:String, customerAddress:String, customerZipCode:String, customerStatus:String){
        var contentValues = ContentValues()
        contentValues.put("PIZZA_ID", pizzaID)
        contentValues.put(CUSTOMER_NAME, customerName)
        contentValues.put(CUSTOMER_EMAIL, customerEmail)
        contentValues.put(CUSTOMER_PHONE, customerPhone)
        contentValues.put(CUSTOMER_ADDRESS, customerAddress)
        contentValues.put(CUSTOMER_ZIPCODE, customerZipCode)
        contentValues.put(CUSTOMER_STATUS, customerStatus)
        this.writableDatabase.insertWithOnConflict("CUSTOMERINFO", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE)
    }
    fun insert_extras(pizzaID:String, extraBreadSticks:String, extraCheeseSticks:String, extraCinnamonSticks:String, extraRanchCup:String, extraBBQCup:String, extraGooeyBrownies:String){
        var contentValues = ContentValues()
        contentValues.put("PIZZA_ID", pizzaID)
        contentValues.put(EXTRA_BREADSTICKS, extraBreadSticks)
        contentValues.put(EXTRA_CHEESESTICKS, extraCheeseSticks)
        contentValues.put(EXTRA_CINNAMON_STICKS, extraCinnamonSticks)
        contentValues.put(EXTRA_RANCH_CUP, extraRanchCup)
        contentValues.put(EXTRA_BBQ_CUP, extraBBQCup)
        contentValues.put(EXTRA_GOOEY_BROWNIES, extraGooeyBrownies)


        this.writableDatabase.insertWithOnConflict("EXTRAS", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE)

    }
    fun insert_price(pizzaID:String, price:Double){
        var contentValues = ContentValues()
        contentValues.put("PIZZA_ID", pizzaID)
        contentValues.put(PRICE, price)
        this.writableDatabase.insertWithOnConflict("PRICE", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE)

    }
    fun insert_base_pizza(pizzaID:String, size:String, crust:String, sauce:String){
        var contentValues = ContentValues()
        contentValues.put("PIZZA_ID", pizzaID)
        contentValues.put(SIZE, size)
        contentValues.put(CRUST, crust)
        contentValues.put(SAUCE, sauce)
        this.writableDatabase.insertWithOnConflict("PIZZABASE", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE)

    }

//    fun delete_deal(video:String, photo:String){
//
//        this.writableDatabase.delete("DEALTABLE", "VIDEO=?", arrayOf(video))
//        this.writableDatabase.delete("DEALPHOTO", "PHOTO_URL=?", arrayOf(photo))
//
//    }
//
//    fun update_deal(oldVideo:String, newVideo:String, oldPhoto:String, newPhoto:String){
//        this.writableDatabase.execSQL("UPDATE DEALTABLE SET VIDEO='$newVideo' WHERE VIDEO='$oldVideo'")
//        this.writableDatabase.execSQL("UPDATE DEALPHOTO SET PRICE='$newPhoto' WHERE PRICE='$oldPhoto'")
//    }

    fun get_last_id()  : Int
    {
        val query = "SELECT * FROM PRICE"
        var id = 0
        val cursor = this.readableDatabase.rawQuery(query, null)
        if(cursor != null)
        {
            if (cursor.moveToLast()) {
        id = cursor.getInt(cursor.getColumnIndex("PIZZA_ID"))
            }
        }
        cursor.close()
        return id

    }

    fun get_all_pizza()  : List<PizzaObject> {
//

        var pizzas = ArrayList<PizzaObject>()



        var pizzaObject = ArrayList<PizzaObject>()
//
        val toppingsQuery = "SELECT * FROM PIZZATOPPINGS"
        val extrasQuery = "SELECT * FROM EXTRAS"
        val pizzaBaseQuery = "SELECT * FROM PIZZABASE"
        val priceQuery = "SELECT * FROM PRICE"
        val customerInfoQuery = "SELECT * FROM CUSTOMERINFO"

//
       // val toppingsCursor = this.readableDatabase.rawQuery(toppingsQuery, null)

//           val cursor = this.readableDatabase.query("DEALTABLE", null, null, null, null, null, null)
        val pizzaCursor = this.readableDatabase.rawQuery(pizzaBaseQuery, null)

        /*
        while (pizzaCursor.moveToNext) {
            get pizza info

            query toppings tabel with pizza id
            get all toppoings


        }
         */

        while(pizzaCursor.moveToNext())
        {
            var tmpCustomer = Customer()
            var tmpPizza = Pizza()
            val toppings = mutableListOf<String>()
            val extras = mutableListOf<String>()

            val pizzaID = pizzaCursor.getString(pizzaCursor.getColumnIndex("PIZZA_ID"))
            val extrasCursor = this.readableDatabase.query("EXTRAS", null, "PIZZA_ID=?", arrayOf(pizzaID), null, null, null)
            val priceCursor = this.readableDatabase.query("PRICE", null, "PIZZA_ID=?", arrayOf(pizzaID), null, null, null)
            val customerInfoCursor = this.readableDatabase.query("CUSTOMERINFO", null, "PIZZA_ID=?", arrayOf(pizzaID), null, null, null)
            val toppingsCursor =  this.readableDatabase.query("PIZZATOPPINGS", null, "PIZZA_ID=?", arrayOf(pizzaID), null, null, null)
            tmpPizza.crust = pizzaCursor.getString(pizzaCursor.getColumnIndex(CRUST))
            tmpPizza.sauce = pizzaCursor.getString(pizzaCursor.getColumnIndex(SAUCE))
            tmpPizza.size = pizzaCursor.getString(pizzaCursor.getColumnIndex(SIZE))
            tmpPizza.id = pizzaID

            //handles toppings
            while(toppingsCursor.moveToNext()) {

                toppings.add(toppingsCursor.getString(toppingsCursor.getColumnIndex(PEPPERONI)))
                toppings.add(toppingsCursor.getString(toppingsCursor.getColumnIndex(PHILLY_STEAK)))
                toppings.add(toppingsCursor.getString(toppingsCursor.getColumnIndex(ITALIAN_SAUSAGE)))
                toppings.add(toppingsCursor.getString(toppingsCursor.getColumnIndex(PREMIUM_CHICKEN)))
                toppings.add(toppingsCursor.getString(toppingsCursor.getColumnIndex(SLICED_ITALIAN_SAUSAGE)))
                toppings.add(toppingsCursor.getString(toppingsCursor.getColumnIndex(BEEF)))
                toppings.add(toppingsCursor.getString(toppingsCursor.getColumnIndex(HAM)))
                toppings.add(toppingsCursor.getString(toppingsCursor.getColumnIndex(BACON)))
                toppings.add(toppingsCursor.getString(toppingsCursor.getColumnIndex(SALAMI)))
                toppings.add(toppingsCursor.getString(toppingsCursor.getColumnIndex(SARDINES)))
                toppings.add(toppingsCursor.getString(toppingsCursor.getColumnIndex(CHEDDAR)))
                toppings.add(toppingsCursor.getString(toppingsCursor.getColumnIndex(BANANA_PEPPERS)))
                toppings.add(toppingsCursor.getString(toppingsCursor.getColumnIndex(DICED_TOMATOES)))
                toppings.add(toppingsCursor.getString(toppingsCursor.getColumnIndex(JALAPENO_PEPPERS)))
                toppings.add(toppingsCursor.getString(toppingsCursor.getColumnIndex(SHREDDED_PARMESAN)))
                toppings.add(toppingsCursor.getString(toppingsCursor.getColumnIndex(ONIONS)))
                toppings.add(toppingsCursor.getString(toppingsCursor.getColumnIndex(SPINACH)))
                toppings.add(toppingsCursor.getString(toppingsCursor.getColumnIndex(PINEAPPLE)))
                toppings.add(toppingsCursor.getString(toppingsCursor.getColumnIndex(BLACK_OLIVES)))
                toppings.add(toppingsCursor.getString(toppingsCursor.getColumnIndex(MUSHROOMS)))

                tmpPizza.toppings = toppings
            }
            toppingsCursor.close()


            //handles extras
            while(extrasCursor.moveToNext())
            {
                extras.add(extrasCursor.getString(extrasCursor.getColumnIndex(EXTRA_BREADSTICKS)))
                extras.add(extrasCursor.getString(extrasCursor.getColumnIndex(EXTRA_CHEESESTICKS)))
                extras.add(extrasCursor.getString(extrasCursor.getColumnIndex(EXTRA_CINNAMON_STICKS)))
                extras.add(extrasCursor.getString(extrasCursor.getColumnIndex(EXTRA_RANCH_CUP)))
                extras.add(extrasCursor.getString(extrasCursor.getColumnIndex(EXTRA_BBQ_CUP)))
                extras.add(extrasCursor.getString(extrasCursor.getColumnIndex(EXTRA_GOOEY_BROWNIES)))

                tmpPizza.extras = extras

            }
            extrasCursor.close()

            //handles price
            while(priceCursor.moveToNext())
            {
                tmpPizza.price = priceCursor.getString(priceCursor.getColumnIndex(PRICE))

            }
            priceCursor.close()

            //handles customer info
            while(customerInfoCursor.moveToNext())
            {
                tmpCustomer.name = customerInfoCursor.getString(customerInfoCursor.getColumnIndex(CUSTOMER_NAME))
                tmpCustomer.email = customerInfoCursor.getString(customerInfoCursor.getColumnIndex(CUSTOMER_EMAIL))
                tmpCustomer.phone = customerInfoCursor.getString(customerInfoCursor.getColumnIndex(CUSTOMER_PHONE))
                tmpCustomer.address = customerInfoCursor.getString(customerInfoCursor.getColumnIndex(CUSTOMER_ADDRESS))
                tmpCustomer.zipcode = customerInfoCursor.getString(customerInfoCursor.getColumnIndex(CUSTOMER_ZIPCODE))
                tmpCustomer.status = customerInfoCursor.getString(customerInfoCursor.getColumnIndex(CUSTOMER_STATUS))
            }
            customerInfoCursor.close()

            pizzas.add(PizzaObject(tmpPizza, tmpCustomer)  )
        }
        pizzaCursor.close()

        return pizzas
    }



}
