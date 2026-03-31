package com.example.simongame

import android.R.attr.onClick
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simongame.ui.theme.SimonGameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimonGameTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Schermata1(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Schermata1(modifier: Modifier = Modifier) {
    val orientation = LocalConfiguration.current.orientation
    if(orientation == Configuration.ORIENTATION_PORTRAIT) { // Layout verticale
        Column(modifier = modifier){
            // Matrice 3x2

            // Testo multiriga non editabile

            // I due bottoni
        }
    }
    else { // Layout orizzontale, matrice con affianco testo e pulsanti. Testo e pulsanti uno sotto l'altro
        Row(modifier = modifier){
            // Matrice 3x2


            // Colonna con dentro testo e pulsanti

        }
    }

}

@Composable
fun Matrice(modifier: Modifier = Modifier){

}

@Composable
fun Riquadro(
    // Funzione compose che costruisce un singolo riquadro secondo i parametri che gli vengono passati
    // Tale funzione viene chiamata volte dalla funzione Matrice, per costruire tutti i Box
    colore: Color,
    lettera: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(4.dp)
            .background(colore)
            .clickable { onClick() },
        contentAlignment = Alignment.Center // Centra tutto ciò che posiziono nel Box
    ) {
        Text( // Lettera dell'iniziale del colore del box
            text = lettera,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SimonGameTheme {
        Schermata1()
    }
}
