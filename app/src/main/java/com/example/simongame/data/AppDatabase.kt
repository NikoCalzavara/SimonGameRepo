package com.example.simongame.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Partita::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun partitaDao(): PartitaDao // Collego il DAO al database

//    /* Ora utilizzo il pattern di programmazione Singleton. In particolare utilizzo un companion object
//    * per assicurarmi di creare il database una sola volta. Se non facessi così, a ogni inserimento
//    * di una nuova partita aprirei una nuova copia del Database e sarebbe disastroso per la RAM. */
//
//    companion object {
//
//        @Volatile // Quando un thread modifica una variabile, tutti gli altri thread dell'app se ne accorgono all'istante
//        private var INSTANCE: AppDatabase? = null
//
//    }

}