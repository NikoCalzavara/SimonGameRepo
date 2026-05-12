package com.example.simongame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.simongame.data.AppDatabase
import com.example.simongame.ui.theme.SimonGameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Creo un'istanza del DataBase
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "simonGameDb"
        ).build()

        val dao = db.partitaDao()

        enableEdgeToEdge()
        setContent {
            SimonGameTheme {

                val gameViewModel: GameViewModel = viewModel() // Creo il ViewModel
                gameViewModel.dao = dao // Associo immediatamente il dao al ViewModel prima che venga utilizzato per evitare errori

                // Implementazione della navigazione tra schermate
                val navigationController = rememberNavController()

                Scaffold( modifier = Modifier.fillMaxSize()){ innerPadding ->
                    NavHost(
                        navController = navigationController,
                        startDestination = "schermata2",
                        modifier = Modifier.padding(innerPadding)
                    ){ // Definizione del grafo di navigazione
                        composable("schermata2"){
                            Schermata2( // Passo il navigationController e il dao
                                navController = navigationController,
                                dao = dao
                            )
                        }
                        composable("gioco"){
                            // Alla Schermata1 passo una funzione lambda che verrà chiamata quando verrà premuto il pulsante "fine partita"
                            // La lamba RICEVE una lista e la salva in "partite", inoltre ESEGUE l'istruzione per cambiare schermata
                            Schermata1(
                                navController = navigationController,
                                viewModel = gameViewModel // Devo passare il viewModel in quanto Schermata1 si occupa solo di fare da "Tramite" tra l'utente e i dati. I dati vengono gestiti dal ViewModel
                            )
                        }
                        //TODO: aggiungere la schermata "dettaglio partita"

                    }
                }
            }
        }
    }
}