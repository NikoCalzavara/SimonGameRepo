package com.example.simongame.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PartitaDao {

    @Query("SELECT * FROM tabella_partite")
    fun getTuttePartite(): Flow<List<Partita>> // Query che ritorna la lista di tutte le partite salvate nel database.
    // Flow permette di non bloccare l'app ogni volta che si esegue la funzione, inoltre l'app aggiornerà la UI automaticamente ogni volta che finisce una partita
    // In particolare ritorna una lista di oggetti di tipo Partita

    @Insert
    suspend fun inserisciPartita(partita: Partita) // Inserimento di una singola partita nel database
    /* L'indicazione "suspend" dice ad Android di eseguire la query in un thread diverso da quello su cui sta girando l'app,
    *  per evitare che la UI si possa bloccare qualora la query dovesse richiedere del tempo. */

}