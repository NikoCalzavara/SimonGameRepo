package com.example.simongame

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simongame.data.PartitaDao
import kotlinx.coroutines.launch

/* Utilizzo la seguente classe per separare completamente la logica di gioco dall'interfaccia utente.
*  Così facendo sarò in grado di gestire la logica del gioco in maniera indipendente dalle varie ricomposizioni della UI,
*  potendo quindi gestire i cambi di configurazione durante la riproduzione, le coroutine... */

class GameViewModel(private val dao: PartitaDao) : ViewModel() {

    // All'interno del ViewModel colloco tutte le variabili necessarie al funzionamento del gioco
    // Utilizzo mutableStateOf in modo da scatenare la ricomposizione della UI dopo i cambiamenti

    private val coloriDisponibili = listOf("R", "G", "B", "M", "Y", "C")
    var coloreAttivo by mutableStateOf<String?>(null) // Colore che sta "suonando"
        private set // Imposto tutte le variabili private set così posso solamente leggerle dalla UI ma non modificarle. Il metodo get è pubblico, il metodo set è privato

    var sequenzaComputer by mutableStateOf(listOf<String>())
        private set

    var sequenzaGiocatore by mutableStateOf(listOf<String>())
        private set

    var isTurnoComputer by mutableStateOf(false)
        private set

    var partitaInCorso by mutableStateOf(false)
        private set

    var isInPausa by mutableStateOf(false)
        private set

    fun avviaPartita() { // Invocata quando si preme "Avvia partita"
        partitaInCorso = true
        isTurnoComputer = true
        sequenzaComputer = emptyList()
        sequenzaGiocatore = emptyList()

        sequenzaComputer = listOf(coloriDisponibili.random()) // Primo colore casuale

        riproduciSequenzaComputer()
    }

    private fun riproduciSequenzaComputer(){ // Riproduzione di un colore alla volta
        viewModelScope.launch { // "Launch" fa partire l'operazione in background
            for (colore in sequenzaComputer){

                while (isInPausa){
                    kotlinx.coroutines.delay(100) // Ogni 100 millisecondi controlla lo stato della variabile isInPausa. Se è True NON procede
                }

                coloreAttivo = colore // "Accendo" il colore

                kotlinx.coroutines.delay(500) // Tengo acceso il colore per mezzo secondo

                coloreAttivo = null // "Spengo" il colore

                kotlinx.coroutines.delay(250) // Pausa prima di procedere con il prossimo colore

            }

            isTurnoComputer = false // Finito di suonare la sequenza tocca al giocatore

        }
    }

    fun pausaRiprendi() {
        isInPausa = !isInPausa // Inverte il valore (se era false diventa true, e viceversa)
    }

    fun colorePremuto(coloreCliccato: String) { // Chiamata quando l'utente clicca un riquadro
    }

    fun finePartita() {
        partitaInCorso = false

    }
}