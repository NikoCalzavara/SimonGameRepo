package com.example.simongame

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simongame.data.Partita
import com.example.simongame.data.PartitaDao
import kotlinx.coroutines.launch

/* Utilizzo la seguente classe per separare completamente la logica di gioco dall'interfaccia utente.
*  Così facendo sarò in grado di gestire la logica del gioco in maniera indipendente dalle varie ricomposizioni della UI,
*  potendo quindi gestire i cambi di configurazione durante la riproduzione, le coroutine... */

class GameViewModel : ViewModel() {

    lateinit var dao: PartitaDao

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

    var mostraErrore by mutableStateOf(false) // Per eseguire l'animazione di errore quando l'utente sbaglia a premere

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
            kotlinx.coroutines.delay(1000)
            sequenzaGiocatore = emptyList()
            for (colore in sequenzaComputer){ // Itero su ogni elemento della sequenza generata
                if(!partitaInCorso) return@launch
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

    fun colorePremuto(coloreCliccato: String) { // Chiamata ogni volta che l'utente clicca un riquadro
        if( !partitaInCorso || isTurnoComputer) return // Ignoro il click se non ho avviato la partita o è il turno del computer

        viewModelScope.launch {
            coloreAttivo = coloreCliccato
            kotlinx.coroutines.delay(150) // Illumino brevemente il colore anche quando lo preme l'utente
            coloreAttivo = null
        }

        sequenzaGiocatore += coloreCliccato

        val index = sequenzaGiocatore.size - 1 // Controllo l'ultimo tasto cliccato

        if( sequenzaGiocatore[index] == sequenzaComputer[index] ){ // Se l'utente ha premuto il colore corretto
            if( sequenzaGiocatore.size == sequenzaComputer.size ){ // Condizione in cui torna a essere il turno del computer
                sequenzaComputer += coloriDisponibili.random() // Aggiungo un colore solo se l'utente preme la sequenza corretta
                isTurnoComputer = true
                riproduciSequenzaComputer()
            }
        } else { // Il giocatore ha sbagliato colore
            partitaInCorso = false
            viewModelScope.launch {
                mostraErrore = true // Accende il rosso (la variabile errore va a 0.7f)
                kotlinx.coroutines.delay(600) // Aspetta 300 millisecondi
                mostraErrore = false // Spegne il rosso (la variabile errore torna a 0f con l'animazione prevista)
            }
            salvaPartitaDB()
        }
    }

    private fun salvaPartitaDB(){ // Funzione che "prepara" tutti i dati da salvare nel database e li salva
        val lunghezzaCorretta = maxOf(0, sequenzaComputer.size - 1 ) // -1 perchè se ho premuto 3 colori e ho sbagliato il terzo, quelli corretti sono 2
        val sequenzaStringa = sequenzaComputer.joinToString(", ")
        val indiceSbagliato = maxOf(0, sequenzaGiocatore.size - 1)
        val partitaDaSalvare = Partita(
            lunghezza = lunghezzaCorretta,
            sequenza = sequenzaStringa,
            indiceErrore = indiceSbagliato
        )
        // Lancio la coroutine per salvare i dati nel database sfruttando il DAO
        viewModelScope.launch{
            dao.inserisciPartita(partitaDaSalvare)
        }
    }

    fun finePartita() { // Invocata solamente quando l'utente preme il pulsante "Fine partita"
        partitaInCorso = false

        if( sequenzaComputer.size <= 1 ){ // Caso in cui viene premuto il tasto "Fine partita" durante la presentazione della prima sequenza
            sequenzaComputer = emptyList()
            sequenzaGiocatore = emptyList()
            return // Esco senza salvare dopo aver svuotato le due liste
        }

        salvaPartitaDB()
        sequenzaComputer = emptyList()
        sequenzaGiocatore = emptyList()
        coloreAttivo = null
    }
}