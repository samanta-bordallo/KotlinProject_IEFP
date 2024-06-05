package com.example.app1

// ApiService.kt
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET("veiculos")
    fun getVeiculos(): Call<List<Veiculo>>

    @POST("veiculos")
    fun addVeiculo(@Body veiculo: Veiculo): Call<Veiculo>

    @DELETE("/comprar/{id}")
    fun comprarVeiculo(@Path("id") idVeiculo: Int): Call<Void>

    @DELETE("vender/{id}")
    fun venderVeiculo(@Path("id") idVeiculo: Int): Call<Void>

    @PUT("veiculos/{id}")
    fun atualizarPrecoVeiculo(@Path("id") idVeiculo: Int, @Body preco: Double): Call<Void>
}
