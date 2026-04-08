package com.example.simongame

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun Schermata2(modifier : Modifier = Modifier, partite: List<List<String>>) {
    Column( modifier = modifier
        .fillMaxSize()
        .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text( // Testo visualizzato in alto, al centro
            text = stringResource(R.string.partite_concluse),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        // Utilizzo una LazyColumn alla quale passo la lista di partite. La LazyColumn eseguirà il blocco di codice specificato per ogni singolo elemento della lista
        LazyColumn( modifier = Modifier.fillMaxSize()) {
            items(partite){ partita ->
                // Ora specifico il codice da eseguire per ogni elemento della lista di partite
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                    ) {
                    Text( // Testo che sta a sinistra e mostra il numero di rettangoli premuti
                        text = "${partita.size}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                    Text( // Testo che sta a destra e mostra la sequenza di colori premuti
                        text = if (partita.isEmpty()) stringResource(R.string.nessun_colore) else partita.joinToString(", "),
                        // Ora devo far si che se la sequenza è troppo lunga essa venga troncata
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis // In caso di troncamento aggiunge "..." alla fine della sequenza
                    )
                }
            }
        }
    }
}