package com.example.simongame

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.simongame.data.PartitaDao

/* Utilizzo la seguente classe per separare completamente la logica di gioco dall'interfaccia utente.
*  Così facendo sarò in grado di gestire la logica del gioco in maniera indipendente dalle varie ricomposizioni della UI,
*  potendo quindi gestire i cambi di configurazione durante la riproduzione, le coroutine... */

class GameViewModel(private val dao: PartitaDao) : ViewModel() {

    // All'interno del ViewModel colloco tutte le variabili necessarie al funzionamento del gioco

    var sequenzaComputer by mutableStateOf(listOf<String>())

    var sequenzaGiocatore by mutableStateOf(listOf<String>())

    var isTurnoComputer by mutableStateOf(false)

    var partitaInCorso by mutableStateOf(false)

    var isInPausa by mutableStateOf(false)

}