package com.example.simongame.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Partita::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun partitaDao(): PartitaDao // Collego il DAO al database

}