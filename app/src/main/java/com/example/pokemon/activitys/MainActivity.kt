package com.example.pokemon.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import com.example.pokemon.R
import com.example.pokemon.modelo.Pokemon
import com.example.pokemon.modelo.PokemonListResponse
import com.example.pokemon.servicio.PokeApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    lateinit var buttonLayout: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonLayout = findViewById<LinearLayout>(R.id.button_layout)
        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val pokeApiService = retrofit.create(PokeApiService::class.java)

        pokeApiService.getPokemonList().enqueue(object : Callback<PokemonListResponse> {
            override fun onResponse(
                call: Call<PokemonListResponse>,
                response: Response<PokemonListResponse>
            ) {
                if (response.isSuccessful) {
                    val pokemonList = response.body()?.results
                    pokemonList?.forEach {
                        crearBoton(it)
                    }
                } else {
                    // Error en la llamada
                }
            }

            override fun onFailure(call: Call<PokemonListResponse>, t: Throwable) {
                // Error en la conexión
            }
        })

    }

    private fun crearBoton(pokemon: Pokemon){
        val button = Button(this)
        button.text = "${pokemon.name}"
        button.setOnClickListener {
            //Toast.makeText(this, "Pokemon seleccionado: ${pokemon.name}", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, descripcionPokemon::class.java)
            intent.putExtra("url",pokemon.url)
            startActivity(intent)

        }
        buttonLayout.addView(button)
    }
}