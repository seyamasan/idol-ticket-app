package com.example.idolticketapplication.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface OwnedTicketsDao {
    @Insert
    suspend fun insert(moodEntity: OwnedTicketsEntity)

    @Update
    suspend fun update(moodEntity: OwnedTicketsEntity)

    @Query("SELECT * FROM owned_tickets_table")
    suspend fun selectAll(): List<OwnedTicketsEntity>

    @Query("DELETE FROM owned_tickets_table")
    suspend fun deleteAll()
}

@Dao
interface EventListDao {
    @Insert
    suspend fun insert(eventListEntity: EventListEntity)

    @Update
    suspend fun update(eventListEntity: EventListEntity)

    @Query("SELECT * FROM event_list_table")
    suspend fun selectAll(): List<EventListEntity>

    @Query("DELETE FROM event_list_table")
    suspend fun deleteAll()
}