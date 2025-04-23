package com.example.idolticketapplication.room.repository

import com.example.idolticketapplication.room.OwnedTicketsDao
import com.example.idolticketapplication.room.OwnedTicketsEntity

/*
* owned_tickets_databaseのowned_tickets_tableを操作する
*/

class OwnedTicketsRepository(private val ownedTicketsDao: OwnedTicketsDao) {

    // DBにデータを保存
    suspend fun insert(
        ownedTicketsEntity: OwnedTicketsEntity
    ): Boolean {
        return try {
            ownedTicketsDao.insert(
                ownedTicketsEntity
            )
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun update(ownedTicketsEntity: OwnedTicketsEntity): Boolean {
        return try {
            ownedTicketsDao.update(
                ownedTicketsEntity
            )
            true
        } catch (e: Exception) {
            false
        }
    }

    // DBのデータ全部取得
    suspend fun selectAll(): List<OwnedTicketsEntity>? {
        val data = ownedTicketsDao.selectAll()
        data.ifEmpty {
            return null
        }
        return data
    }

    // DBにデータを保存
    suspend fun selectTicketIdAndEventName(
        ticketId: Int,
        eventName: String
    ): OwnedTicketsEntity? {
        return try {
            ownedTicketsDao.selectTicketIdAndEventName(ticketId = ticketId, eventName = eventName)
        } catch (e: Exception) {
            null
        }
    }

    // DBの内容を全て消す
    suspend fun deleteAll(): Boolean {
        return try {
            ownedTicketsDao.deleteAll()
            true
        } catch (e: Exception) {
            false
        }
    }
}