package com.example.simongame

import android.R.attr.onClick
import android.content.res.Configuration
import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
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
        Column(modifier = modifier,
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ){


            // Matrice 3x2
            Matrice(
                modifier = Modifier.weight(1f),
                onColorClick = { }
            )

            Spacer(modifier = Modifier.weight(0.1f))

            // Testo multi riga non editabile
            Text(modifier = modifier,
                text = "Test",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.weight(0.1f))

            // I due bottoni
            Pulsanti(modifier = Modifier.weight(0.5f))

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
fun Matrice(modifier: Modifier = Modifier, onColorClick: () -> Unit){
    // Per costruire la matrice 3x2 utilizzo una Column con 3 Row al suo interno
    Column(modifier = modifier){
        Row( modifier = Modifier.weight(0.1f)){
            Riquadro(Color.Red, Color.White, stringResource(R.string.r), {}, Modifier.weight(1f))
            Riquadro(Color.Green, Color.White, stringResource(R.string.g), {}, Modifier.weight(1f))
        }
        Row( modifier = Modifier.weight(0.1f)){
            Riquadro(Color.Blue, Color.White, stringResource(R.string.b), {}, Modifier.weight(1f))
            Riquadro(Color.Magenta, Color.White, stringResource(R.string.m), {}, Modifier.weight(1f))
        }
        Row( modifier = Modifier.weight(0.1f)){
            Riquadro(Color.Yellow, Color.Black, stringResource(R.string.y), {}, Modifier.weight(1f))
            Riquadro(Color.Cyan, Color.White, stringResource(R.string.c), {}, Modifier.weight(1f))
        }
    }
}

@Composable
fun Riquadro(
    // Funzione compose che costruisce un singolo riquadro secondo i parametri che gli vengono passati
    // Tale funzione viene chiamata sei volte dalla funzione Matrice, per costruire tutti i Box
    coloreRiquadro: Color,
    coloreLettera: Color,
    lettera: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(4.dp)
            .background(coloreRiquadro)
            .clickable { onClick() },
        contentAlignment = Alignment.Center // Centra tutto ciò che posiziono nel Box
    ) {
        Text( // Lettera dell'iniziale del colore del box
            text = lettera,
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold,
            color = coloreLettera
        )
    }
}

@Composable
fun Pulsanti(modifier : Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(onClick = { /*TODO*/ }) {
            Text(text = stringResource(R.string.cancella))
        }
        Button(onClick = { /*TODO*/ }) {
            Text(text = stringResource(R.string.fine_partita))
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SimonGameTheme {
        Schermata1()
    }
}

