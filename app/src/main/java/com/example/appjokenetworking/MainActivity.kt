package com.example.appjokenetworking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btJoke = findViewById<Button>(R.id.btJoke)

        btJoke.setOnClickListener{
            loadJoke()
        }
    }

    private fun loadJoke(){
        val tvJoke = findViewById<TextView>(R.id.tvJoke)
        //tvJoke.text = "Broma encontrada!!"

        //1. Creo una instancia de retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://geek-jokes.sameerkumar.website/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        //2. Creo una instancia de la interfaz
        var jokeService: JokeService
        jokeService = retrofit.create(JokeService:: class.java)

        //3. Creo una variable y a traves de la interfaz le asigno la funcion
        val request = jokeService.getJoke("json")

        //Implemento los metodos a traves de request
        request.enqueue(object :Callback<Joke>{
            //si funciona
            override fun onResponse(call: Call<Joke>, response: Response<Joke>) {
                if (response.isSuccessful){
                    tvJoke.text = response.body()!!.joke
                }

            }

            //si no funciona
            override fun onFailure(call: Call<Joke>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

    }
}