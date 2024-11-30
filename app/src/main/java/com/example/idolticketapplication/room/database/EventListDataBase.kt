package com.example.idolticketapplication.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.idolticketapplication.room.EventListDao
import com.example.idolticketapplication.room.EventListEntity

@Database(entities = [EventListEntity::class], version = 1, exportSchema = false)
abstract class EventListDataBase: RoomDatabase() {
    abstract  fun eventListDao(): EventListDao

    companion object {
        fun buildDatabase(context: Context): EventListDataBase {
            return Room.databaseBuilder(
                context,
                EventListDataBase::class.java,
                "event_list_database"
            ).build()
        }
    }
}