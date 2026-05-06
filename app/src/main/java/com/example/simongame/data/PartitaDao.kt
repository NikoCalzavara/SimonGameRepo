package com.example.simongame.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PartitaDao {

    @Query("SELECT * FROM tabella_partite")
    fun getTuttePartite(): List<Partita> // Query che ritorna la lista di tutte le partite salvate nel database

    @Insert
    suspend fun inserisciPartita(partita: Partita) // Inserimento di una singola partita nel database
    /* L'indicazione "suspend" dice ad Android di eseguire la query in un thread diverso da quello su cui sta girando l'app,
    *  per evitare che la UI si possa bloccare qualora la query dovesse richiedere del tempo.
    */

}