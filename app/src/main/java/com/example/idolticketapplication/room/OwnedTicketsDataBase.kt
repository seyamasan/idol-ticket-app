package com.example.idolticketapplication.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [OwnedTicketsEntity::class], version = 1, exportSchema = false)
abstract class OwnedTicketsDataBase: RoomDatabase() {
    abstract  fun ownedTicketsDao(): OwnedTicketsDao

    companion object {
        fun buildDatabase(context: Context): OwnedTicketsDataBase {
            return Room.databaseBuilder(
                context,
                OwnedTicketsDataBase::class.java,
                "owned_tickets_database"
            ).build()
        }
    }
}