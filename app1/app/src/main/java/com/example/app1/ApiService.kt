package com.example.app1

// ApiService.kt
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET("veiculos")
    fun getVeiculos(): Call<List<Veiculo>>

    @POST("veiculos")
    fun addVeiculo(@Body veiculo: Veiculo): Call<Veiculo>

    @POST("comprar")
    fun comprarVeiculo(@Body idVeiculo: Int): Call<Void>

    @POST("vender")
    fun venderVeiculo(@Body veiculo: Int): Call<Void>

    @PUT("veiculos/{id}")
    fun atualizarPrecoVeiculo(@Path("id") id: Int, @Body preco: Double): Call<Void>
}