package com.example.taylanwhite.pizzadjango.presenter

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.taylanwhite.pizzadjango.models.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


/**
 * Created by taylanwhite on 9/19/16.
 */
object PizzaService {

    var BASE_URL = "http://10.0.2.2:8000/"
    val api by lazy { buildApi() }

    fun buildApi(): ServiceInterface {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
//        val uri = Uri.parse("http://127.0.0.1:8000/veggieToppings/?format=json&page=2")
//        val page = uri.getQueryParameters("page")[0]

        return Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).client(client).build().create(ServiceInterface::class.java)
    }

    //http://127.0.0.1:8000/users/?format=json
    //http://10.0.2.2:8000/users/?format=json
    interface ServiceInterface
    {
        @GET("meatToppings/")
        fun getMeatToppings(@Query("page")page: Int? = null): Call<MeatToppings>

        @GET("specialtyPizza/")
        fun getSpecialtyPizzas(@Query("page")page: Int? = null, @Query("format")apiKey: String = "json" ): Call<SpecializedPizzas>

        @GET("crust/")
        fun getCrusts(@Query("page")page: Int? = null, @Query("format")apiKey: String = "json" ): Call<CrustType>

        @GET("pizzaSize/")
        fun getSize(@Query("page")page: Int? = null, @Query("format")apiKey: String = "json" ): Call<Size>

        @GET("sauce/")
        fun getSauce(@Query("page")page: Int? = null, @Query("format")apiKey: String = "json" ): Call<SauceType>

        @GET("veggieToppings/")
        fun getVeggieToppings(@Query("page")page: Int? = null, @Query("format")apiKey: String = "json" ): Call<VeggieToppings>

        @GET("extra/")
        fun getExtras(@Query("page")page: Int? = null, @Query("format")apiKey: String = "json" ): Call<Extras>

        @GET("orderedPizzas/")
        fun getOrders(@Header("Authorization") token: String, @Query("page")page: Int? = null): Call<PastOrder>

//        @GET("users/current_user/")
        @GET("users/current_user/")
        fun getCurrentUser(@Header("Authorization") token: String): Call<UserResults>

        @POST("api-token-auth/")
        fun signIn(@Body UserLogin: UserLogin): Call<UserToken>

        @Headers("Content-Type: application/json")
        @POST("orderedPizzas/")
        fun postUser(@Header("Authorization") token: String, @Body order: CompleteOrder): Call<CompleteOrder>

//
//        # @api_view(['GET'])
//        # def current_user(self):
//        #     serializer = UserSerializer(self.request.user).data
//        #     return Response(serializer.data)
//
//        # @detail_route(['GET'], permission_classes=[IsAuthenticated])
//        # def current_user(self):
//        #     return Response(UserSerializer(self.request.user).data)



    }

}


