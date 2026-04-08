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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.simongame.ui.theme.SimonGameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimonGameTheme {
                var partite by rememberSaveable { mutableStateOf( listOf<List<String>>()) } // Utilizzo una lista di liste di stringhe per memorizzare le partite

                // Implementazione della navigazione tra schermate
                val navigationController = rememberNavController()

                Scaffold( modifier = Modifier.fillMaxSize()){ innerPadding ->
                    NavHost(
                        navController = navigationController, startDestination = "schermata1",
                        modifier = Modifier.padding(innerPadding)
                    ){ // Definizione del grafo di navigazione
                        composable("schermata1"){
                            // Alla Schermata1 passo una funzione lambda che verrà chiamata quando verrà premuto il pulsante "fine partita"
                            // La lamba RICEVE una lista e la salva in "partite", inoltre ESEGUE l'istruzione per cambiare schermata
                            Schermata1(onFinePartitaClicked = { sequenza ->
                                partite = partite + listOf(sequenza)
                                navigationController.navigate("schermata2")
                            } )
                        }
                        composable("schermata2"){
                            Schermata2(partite = partite) // Passo alla schermata 2 la lista di partite che deve mostrare
                        }
                    }
                }
            }
        }
    }
}