package com.example.simongame

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun DettaglioPartita(
    lunghezza: Int,
    indiceErrore: Int,
    sequenza: String,
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,    // Centra tutti gli elementi in verticale
        horizontalAlignment = Alignment.CenterHorizontally // Centra tutti gli elementi in orizzontale
    ) {
        Text(
            text = stringResource(R.string.maximum_correct_length),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text( // Lunghezza effettiva della sequenza
            text = "$lunghezza",
            fontWeight = FontWeight.SemiBold,
            fontSize = 30.sp,
        )

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            // Mostro la sequenza per intero
            text = stringResource(R.string.complete_sequence),
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 4. Sotto, la sequenza elaborata con l'errore colorato
        Text(
            text = coloraSequenza(sequenza, indiceErrore),
            fontSize = 26.sp,
            fontWeight = FontWeight.SemiBold,
            )
    }
}