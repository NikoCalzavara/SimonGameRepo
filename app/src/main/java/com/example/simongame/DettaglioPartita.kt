package com.example.simongame

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simongame.ui.theme.SimonGameTheme

@Composable
fun DettaglioPartita(
    lunghezza: Int,
    indiceErrore: Int,
    sequenza: String,
) {
    // Uso un Box per centrare l'intero blocco al centro dello schermo
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        // La Row affianca il numero e la sequenza
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically // Centra la sequenza verticalmente rispetto al numero
        ) {
            // 1. Il numero a sinistra
            Text(
                modifier = Modifier.padding(end = 16.dp), // Spazio tra il numero e la sequenza
                text = "$lunghezza",
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp // L'ho fatto un po' più grande per dare enfasi
            )

            Spacer( modifier = Modifier.padding(16.dp)) // Spazio tra lunghezza e sequenza

            // 2. La sequenza a destra
            Text(
                modifier = Modifier.weight(1f), // Permette al testo di andare a capo riempiendo lo spazio
                text = coloraSequenza(sequenza, indiceErrore),
                fontSize = 28.sp,
                lineHeight = 36.sp // Aumenta leggermente lo spazio tra le righe per facilitare la lettura

            )
        }
    }
}

@Preview
@Preview(showBackground = true, name = "Dettaglio - Sequenza Lunga")
@Composable
fun DettaglioPartitaSequenzaLungaPreview() {
    SimonGameTheme {
        DettaglioPartita(
            lunghezza = 22,
            indiceErrore = 15,
            sequenza = "R, G, B, M, Y, C, R, G, B, M, Y, C, R, G, B, M, Y, C, R, G, B, M, G, B, M, Y, C, R, G, B, M, Y, C, R, G, B, M, Y, C, R, G, B, M, Y, C, R"
        )
    }
}

