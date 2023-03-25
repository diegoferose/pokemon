package com.example.pokemon.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.pokemon.R
import com.example.pokemon.modelo.PokemonUnitario
import com.example.pokemon.servicio.PokeApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class descripcionPokemon : AppCompatActivity() {
    lateinit var myImage: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_descripcion_pokemon)

        val url = intent.getStringExtra("url")
        myImage = findViewById<ImageView>(R.id.myImage)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val pokeApiService = retrofit.create(PokeApiService::class.java)

        val call = pokeApiService.getPokemon(obtenerUltimoNumero(url.toString()))
        call.enqueue(object : Callback<PokemonUnitario> {
            override fun onResponse(
                call: Call<PokemonUnitario>,
                response: Response<PokemonUnitario>
            ) {
                if (response.isSuccessful) {
                    runOnUiThread {
                        val pokemon = response.body()
                        println("ID: ${pokemon?.id}, Name: ${pokemon?.name} , IMG: ${pokemon?.sprites?.front_default}")
                        if (pokemon != null) {
                            obtenerImagen(pokemon.sprites.front_default)
                        }
                    }
                } else {
                    println("Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<PokemonUnitario>, t: Throwable) {
                println("Error: ${t.message}")
            }
        })


    }

    fun obtenerImagen(url: String) {
        Glide.with(this)
            .load(url)
            .into(myImage)
    }


    fun obtenerUltimoNumero(cadena: String): Int {
        val cadenaSinBarra = cadena.substringBeforeLast("/")
        val cadenaDespuesUltimaBarra = cadenaSinBarra.substringAfterLast("/")
        return cadenaDespuesUltimaBarra.toInt()
    }
}