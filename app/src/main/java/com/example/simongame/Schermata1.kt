package com.example.simongame

import android.content.res.Configuration
import android.media.SoundPool
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
// Obbligatorio passare una funzione lambda che verrà chiamata quando verrà premuto il pulsante "fine partita"
// Il Modifier invece ha un valore di default
fun Schermata1(modifier: Modifier = Modifier, navController : NavController, viewModel: GameViewModel) {
    val orientation = LocalConfiguration.current.orientation

    val errore by animateFloatAsState(
        targetValue = if (viewModel.mostraErrore) 0.7f else 0f, // Opacità al 70% quando l'utente commette un errore
        animationSpec = if (viewModel.mostraErrore) {
            snap() // Accensione: Quando c'è l'errore, scatta a 0.7 istantaneamente
        } else {
            tween(durationMillis = 1000) // Spegnimento: Ci mette 1.5 secondi a tornare a 0
        },
        label = "animazione_errore"
    )

    val context = LocalContext.current
    val soundPool = remember { SoundPool.Builder().setMaxStreams(4).build() } // Inizializzo l'oggetto SoundPool

    // Ora carico i dati in memoria, questa operazione viene fatta una sola volta a ogni avvio dell'applicazione grazie al "remember"
    val suonoDo = remember { soundPool.load(context, R.raw.do_, 1) }
    val suonoRe = remember { soundPool.load(context, R.raw.re, 1) }
    val suonoMi = remember { soundPool.load(context, R.raw.mi, 1) }
    val suonoFa = remember { soundPool.load(context, R.raw.fa, 1) }
    val suonoSol = remember { soundPool.load(context, R.raw.sol, 1) }
    val suonoLa = remember { soundPool.load(context, R.raw.la, 1) }
    val suonoErrore = remember { soundPool.load(context, R.raw.game_over, 1) }

    // Leggo lo stato dal ViewModel
    val sequenzaGiocatore = viewModel.sequenzaGiocatore
    val coloreAttivo = viewModel.coloreAttivo

    LaunchedEffect(coloreAttivo) { // Osservo la variabile coloreAttivo per capire che suono riprodurre ogni volta che cambia
        if(coloreAttivo != null) {
            val suonoDaRiprodurre = when (coloreAttivo){
                context.getString(R.string.r) -> suonoDo
                context.getString(R.string.g) -> suonoRe
                context.getString(R.string.b) -> suonoMi
                context.getString(R.string.m) -> suonoFa
                context.getString(R.string.y) -> suonoSol
                context.getString(R.string.c) -> suonoLa
                else -> null
            }
            suonoDaRiprodurre?.let { id -> // Controllo con l'operatore "?." se la variabile non è null, se non lo è eseguo il codice dentro le parentesi
                soundPool.play(id, 1f, 1f, 0, 0, 1f)
            }
        }
    }

    LaunchedEffect(viewModel.mostraErrore) { // Riproduce il suono quando l'utente sbaglia
        if(viewModel.mostraErrore){
            soundPool.play(suonoErrore, 1f, 1f, 0, 0, 1f)
        }
    }

    // Lo stato scende "verso il basso" come parametro
    // Gli eventi, invece, salgono verso l'alto come funzioni lambda. Nel nostro caso l'evento parte da Riquadro e deve arrivare a Schermata1

    Box(modifier = modifier.fillMaxSize()) { // Box che serve solo come contenitore
        // Layout verticale
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp), // Aggiungo del padding per non avere tutto a filo dello schermo
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Matrice( // Matrice 3x2
                    modifier = Modifier.weight(3f),
                    coloreAttivo = coloreAttivo, // Passo alla matrice il colore che sta "suonando" il ViewModel
                    onColorClick = { coloreCliccato -> viewModel.colorePremuto(coloreCliccato) } // coloreCliccato rappresenta la stringa inviata dal riquadro
                )

                Text(
                    modifier = modifier // Testo non editabile
                        .padding(vertical = 24.dp), // Aggiungo padding solo in verticale, non ai lati
                    // Utilizzo il metodo joinToString in quanto mi permette di convertire la lista in stringa e scegliere il separatore che preferisco
                    text = if (!viewModel.partitaInCorso) stringResource(R.string.press_the_start_button) else sequenzaGiocatore.joinToString(
                        ", "
                    ),
                    maxLines = 2,
                    minLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                )

                // I due bottoni
                Pulsanti(
                    modifier = Modifier.fillMaxWidth(),
                    navController = navController,
                    viewModel = viewModel
                )
            }
        }

        // Layout orizzontale, matrice con affianco testo e pulsanti. Testo e pulsanti uno sotto l'altro
        else {
            Row(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // Matrice 3x2
                Matrice(
                    modifier = Modifier.weight(3f),
                    coloreAttivo = coloreAttivo,
                    onColorClick = { coloreCliccato -> viewModel.colorePremuto(coloreCliccato) } // coloreCliccato rappresenta la stringa inviata dal riquadro
                )
                // Colonna con dentro testo e pulsanti
                Column(
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = modifier
                            .padding(horizontal = 24.dp)
                            .padding(vertical = 12.dp),
                        minLines = 4,
                        maxLines = 4, // Supporto 2 righe in più rispetto al layout verticale
                        overflow = TextOverflow.Ellipsis,
                        text = if (!viewModel.partitaInCorso) stringResource(R.string.press_the_start_button) else sequenzaGiocatore.joinToString(
                            ", "
                        ),
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                    )
                    // I due bottoni
                    Pulsanti(
                        modifier = Modifier.fillMaxWidth(),
                        navController = navController,
                        viewModel = viewModel
                    )
                }

            }
        }
        Box( // Box che si sovrappone a tutto
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Red.copy(alpha = errore))
        )
    }
}

@Composable
fun Matrice(modifier: Modifier = Modifier, coloreAttivo: String?, onColorClick: (String) -> Unit){ // Ora passo alla matrice anche il colore che deve essere attivo, ma che può anche essere null
    // Per costruire la matrice 3x2 utilizzo una Column con 3 Row al suo interno
    Column(modifier = modifier){
        Row( modifier = Modifier.weight(1f)){
            // Richiamo le stringe da utilizzare nelle lambda "onClick" dei riquadri
            val testoRosso = stringResource(R.string.r)
            val testoVerde = stringResource(R.string.g)

            // Il parametro "isIlluminato" è true solo se la lettera del colore attivo corrisponde a quella del riquadro
            Riquadro(Color.Red, coloreAttivo == testoRosso, { onColorClick(testoRosso) }, Modifier.weight(1f))
            Riquadro(Color.Green, coloreAttivo == testoVerde,{ onColorClick(testoVerde) }, Modifier.weight(1f))
        }
        Row( modifier = Modifier.weight(1f)){
            val testoBlu = stringResource(R.string.b)
            val testoMagenta = stringResource(R.string.m)

            Riquadro(Color.Blue, coloreAttivo == testoBlu,{ onColorClick(testoBlu) }, Modifier.weight(1f))
            Riquadro(Color.Magenta, coloreAttivo == testoMagenta,{ onColorClick(testoMagenta) }, Modifier.weight(1f))
        }
        Row( modifier = Modifier.weight(1f)){
            val testoGiallo = stringResource(R.string.y)
            val testoCiano = stringResource(R.string.c)

            Riquadro(Color.Yellow, coloreAttivo == testoGiallo,{ onColorClick(testoGiallo) }, Modifier.weight(1f))
            Riquadro(Color.Cyan,  coloreAttivo == testoCiano,{ onColorClick(testoCiano) }, Modifier.weight(1f))
        }
    }
}

@Composable
fun Riquadro(
    // Funzione compose che costruisce un singolo riquadro secondo i parametri che gli vengono passati
    // Tale funzione viene chiamata sei volte dalla funzione Matrice, per costruire tutti i Box
    coloreRiquadro: Color,
    isIlluminato: Boolean, // Parametro per capire se deve essere illuminato o no
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val coloreMostrato = if (isIlluminato) coloreRiquadro else coloreRiquadro.copy(alpha = 0.3f) // Uso il colore pieno se è illuminato, altrimenti il riquadro è semitrasparente

    val scala by animateFloatAsState(
        targetValue = if (isIlluminato) 1.05f else 1.0f, // Calcola un'animazione tra la dimensione 1.0f e 1.05f
        label = "animazione_scala"
    )

    val isTemaScuro = isSystemInDarkTheme() // Restituisce true se il dispositivo è in modalità scura

    val coloreBordo = if(isTemaScuro) Color.White else Color.Black // Scelgo il colore del bordo del Box in base al tema del dispositivo

    Box(
        modifier = modifier
            .fillMaxSize()
            .scale(scala)
            .padding(8.dp) // Padding per spaziare i singoli Box all'interno della matrice
            .background(
                color = coloreMostrato,
                shape = RoundedCornerShape(16.dp) // Arrotonda gli angoli dei Box
            )
            .border(
                width = if (isIlluminato) 4.dp else 0.dp,
                color = if (isIlluminato) coloreBordo // Se ho il tema chiaro uso un bordo scuro
                else Color.Transparent,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center // Centra tutto ciò che posiziono nel Box
    ){}
}

@Composable
fun Pulsanti(modifier : Modifier = Modifier, navController: NavController, viewModel: GameViewModel) {
    Column( // Nella prima riga contiene due pulsanti, poi sotto il terzo pulsante centrato
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button( // Pulsante pausa
                onClick = { viewModel.pausaRiprendi() },
                enabled = viewModel.isTurnoComputer // Il pulsante pausa dev'essere attivo solamente quando tocca al computer
            ) {
                Text( if (viewModel.isInPausa) stringResource(R.string.riprendi) else stringResource(R.string.pause))
            }
            Button( // Pulsante fine partita
                onClick = {
                    if (viewModel.partitaInCorso){
                        viewModel.finePartita()
                    }
                    navController.popBackStack() // Torna alla schermata precedente
                }
            ){
                Text( if (viewModel.partitaInCorso) stringResource(R.string.fine_partita) else stringResource(R.string.back))
            }
        }

        Button( // Pulsante avvia partita
            onClick = { viewModel.avviaPartita() },
            enabled = !viewModel.partitaInCorso // Rendo il bottone visibile solo se non c'è nessuna partita in corso
        ) {
            Text(text = stringResource(R.string.avvia_partita))
        }
    }
}
