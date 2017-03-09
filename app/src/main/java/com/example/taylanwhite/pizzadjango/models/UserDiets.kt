package com.example.taylanwhite.pizzadjango.models

/**
 * Created by taylanwhite on 1/30/17.
 */
class UserDiets(val id: Int, val user: String, var dietaryRestrictionsList: List<Int> = arrayListOf())

class GetUserDiets(val id: Int, val user: String, var dietaryRestrictionsList: List<DietListResults> = arrayListOf())


//dietList
//dietaryRestrictionsList