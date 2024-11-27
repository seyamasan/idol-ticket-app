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