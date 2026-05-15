package com.example.simongame.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("tabella_partite") // Definendo un'entità Room crea automaticamente la tabella a essa associata
data class Partita(
    @PrimaryKey (autoGenerate = true) // Così facendo lascio che sia Room a creare un identificativo unico progressivo a ogni partita inserita
    val id: Int = 0, // Devo fornire un parametro di default altrimenti Kotlin genera errore in compilazione. Room ignorerà tale valore e assegnerà l'ID corretto

    @ColumnInfo("lunghezza")
    val lunghezza: Int,

    @ColumnInfo("sequenza")
    val sequenza: String, // Devo fare la conversione della partita da lista a stringa in quanto SQLite non riesce a gestire le liste

    @ColumnInfo("errore")
    val indiceErrore: Int // Mi serve per sapere da dove colorare di rosso le partite mostrate nella lista
)