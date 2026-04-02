package com.example.simongame

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
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
    // Lo stato scende "verso il basso" come parametro
    // Gli eventi, invece, salgono verso l'alto come funzioni lambda. Nel nostro caso l'evento parte da Riquadro e deve arrivare a Schermata1
    var sequence by rememberSaveable { mutableStateOf("") }

    if(orientation == Configuration.ORIENTATION_PORTRAIT) { // Layout verticale
        Column(modifier = modifier
            .fillMaxSize()
            .padding(16.dp), // Aggiungo del padding per non avere tutto a filo dello schermo
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            // Matrice 3x2
            Matrice(
                modifier = Modifier.weight(3f),
                onColorClick = { coloreCliccato -> sequence = "$sequence$coloreCliccato, " }
            )

            // Testo multi riga non editabile
            Text(modifier = modifier
                .padding(vertical = 24.dp), // Aggiungo padding solo in verticale, non ai lati
                text = sequence.ifEmpty { "Premi un colore" }, // Metodo che permette di eliminare la verbosità di un blocco if/else
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            // I due bottoni
            Pulsanti(modifier = Modifier.fillMaxWidth())

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
fun Matrice(modifier: Modifier = Modifier, onColorClick: (String) -> Unit){
    // Per costruire la matrice 3x2 utilizzo una Column con 3 Row al suo interno
    Column(modifier = modifier){
        Row( modifier = Modifier.weight(1f)){
            // Richiamo le stringe da utilizzare nelle lambda "onClick" dei riquadri
            val testoRosso = stringResource(R.string.r)
            val testoVerde = stringResource(R.string.g)

            Riquadro(Color.Red, { onColorClick(testoRosso) }, Modifier.weight(1f))
            Riquadro(Color.Green, { onColorClick(testoVerde) }, Modifier.weight(1f))
        }
        Row( modifier = Modifier.weight(1f)){
            val testoBlu = stringResource(R.string.b)
            val testoMagenta = stringResource(R.string.m)

            Riquadro(Color.Blue, { onColorClick(testoBlu) }, Modifier.weight(1f))
            Riquadro(Color.Magenta, { onColorClick(testoMagenta) }, Modifier.weight(1f))
        }
        Row( modifier = Modifier.weight(1f)){
            val testoGiallo = stringResource(R.string.y)
            val testoCiano = stringResource(R.string.c)

            Riquadro(Color.Yellow, { onColorClick(testoGiallo) }, Modifier.weight(1f))
            Riquadro(Color.Cyan,  { onColorClick(testoCiano) }, Modifier.weight(1f))
        }
    }
}

@Composable
fun Riquadro(
    // Funzione compose che costruisce un singolo riquadro secondo i parametri che gli vengono passati
    // Tale funzione viene chiamata sei volte dalla funzione Matrice, per costruire tutti i Box
    coloreRiquadro: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp) // Padding per spaziare i singoli Box
            .background(
                color = coloreRiquadro,
                shape = RoundedCornerShape(16.dp) // Arrotonda gli angoli dei Box
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center // Centra tutto ciò che posiziono nel Box
    ){}
}

@Composable
fun Pulsanti(modifier : Modifier = Modifier) {
    Row(
        modifier = modifier,
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

