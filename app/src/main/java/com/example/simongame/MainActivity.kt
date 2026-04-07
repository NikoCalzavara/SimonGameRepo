package com.example.simongame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
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
                val navigationController = rememberNavController()

                Scaffold( modifier = Modifier.fillMaxSize()){ innerPadding ->
                    NavHost(
                        navController = navigationController, startDestination = "schermata1",
                        modifier = Modifier.padding(innerPadding)
                    ){
                        composable("schermata1"){
                            Schermata1(onStartClicked = { navigationController.navigate("schermata2") } )
                        }
                        composable("schermata2"){
                            Schermata2(onBackClicked = { navigationController.navigate("schermata1") })
                        }
                    }
                }
            }
        }
    }
}
