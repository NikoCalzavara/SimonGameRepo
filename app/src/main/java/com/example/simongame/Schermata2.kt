package com.example.simongame

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.navigation.NavController
import com.example.simongame.data.PartitaDao

@Composable
fun Schermata2(modifier : Modifier = Modifier, navController: NavController, dao: PartitaDao) {

    val partite by dao.getTuttePartite().collectAsState(initial = emptyList()) // Si collega al database in background e aggiorna la lista delle partite ogni volta che salvo una nuova partita

    Box( // Il Box mi permette di posizionare degli oggetti uno sopra l'altro. Mi serve per posizionare il FloatingActionButton sopra la lista delle partite
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text( // Testo visualizzato in alto, al centro
                text = stringResource(R.string.partite_concluse),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            // Utilizzo una LazyColumn alla quale passo la lista di partite. La LazyColumn eseguirà il blocco di codice specificato per ogni singolo elemento della lista "partite"
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(partite) { partita -> // Ogni oggetto è di tipo Partita
                    // Ora specifico il codice da eseguire per ogni elemento della lista di partite
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp) // Spazio tra un elemento e l'altro della lista
                            .clickable{ // Cosa fare quando una riga della lista viene cliccata
                                val sequenzaEncode = Uri.encode(partita.sequenza)
                                navController.navigate("dettaglio_partita/${partita.lunghezza}/${partita.indiceErrore}/$sequenzaEncode") // Passo 3 parametri al navigation controller
                            },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text( // Testo che sta a sinistra e mostra il numero di rettangoli premuti
                            modifier = modifier.padding(end = 12.dp), // Per spaziare il numero dalla stringa con la sequenza di tasti
                            text = "${partita.lunghezza}", // Ho gia il parametro lunghezza salvato nell'oggetto Partita
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        )
                        Text( // Testo che sta a destra e mostra la sequenza di colori premuti
                            text = coloraSequenza(partita.sequenza, partita.indiceErrore),
                            // Ora devo far si che se la sequenza è troppo lunga essa venga troncata
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis // In caso di troncamento aggiunge "..." alla fine della sequenza
                        )
                    }
                }
            }
        }

        FloatingActionButton( // Pulsante "fluttuante" collocato in basso a destra che si sovrappone alla lista delle partite
            onClick = {navController.navigate("gioco")},
            modifier = Modifier
                .align(Alignment.BottomEnd) // Allineo il pulsante in basso a destra
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.PlayArrow,
                "Nuova partita"
            )
        }

    }
}

@Composable // Funzione per colorare la stringa mostrata a destra
fun coloraSequenza(sequenza: String, indiceErrore: Int): androidx.compose.ui.text.AnnotatedString { // AnnotatedString serve per impostare diversi stili all'interno dello stesso Text
    if (sequenza.isEmpty()) return buildAnnotatedString { "" }
    val elementi = sequenza.split(", ") // Split mi ritorna una lista

    val parteCorretta = elementi.take(indiceErrore).joinToString(", ") // Prende gli elementi FINO all'indice d'errore
    val parteSbagliata = elementi.drop(indiceErrore).joinToString(", ") // Prende gli elementi DOPO l'indice d'errore

    return buildAnnotatedString {
        append(parteCorretta) // La parte corretta non ha bisogno di alcuna modifica
        if(parteSbagliata.isNotEmpty()){ // La devo colorare di rosso
            if (parteCorretta.isNotEmpty()) append(", ")
            withStyle(style = SpanStyle(color = Color.Red, fontWeight = FontWeight.Bold)) {
                append(parteSbagliata)
            }
        }
    }
}