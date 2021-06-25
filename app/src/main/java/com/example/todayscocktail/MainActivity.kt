package com.example.todayscocktail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.todayscocktail.data.DrinksListRemoteEntity
import com.example.todayscocktail.data.network.CocktailService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var drinkName: TextView
    private lateinit var drinkContainer: ConstraintLayout
    private lateinit var drinkImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drinkName = findViewById(R.id.drinkName)
        drinkContainer = findViewById(R.id.drinkContainer)
        drinkImage = findViewById(R.id.drinkImage)

        getDrink()

        drinkContainer.setOnClickListener {
            getDrink()
        }
    }

    private fun getDrink() {
        //executar requisição
        //a partir da resposta, escolher um drink aleatório
        //colocar o nome do drink aleatório escolhido no texto
        lifecycleScope.launch {
            try {
                val response = requestDrinks()
                val drink = response.drinksList.random()
                drinkName.text = drink.strDrink
                Glide.with(this@MainActivity)
                     .load(drink.strDrinkThumb)
                     .into(drinkImage)
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private suspend fun requestDrinks(): DrinksListRemoteEntity {
        return withContext(Dispatchers.IO) {
             CocktailService.service.getDrinks()
        }
    }
}