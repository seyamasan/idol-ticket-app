package com.example.idolticketapplication.room.repository

import com.example.idolticketapplication.room.OwnedTicketsDao
import com.example.idolticketapplication.room.OwnedTicketsEntity

/*
* owned_tickets_databaseのowned_tickets_tableを操作する
*/

class OwnedTicketsRepository(private val ownedTicketsDao: OwnedTicketsDao) {

    // DBにデータを保存
    suspend fun insert(
        date: String,
        startTime: String,
        endTime: String,
        place: String,
        eventName: String,
        genre: Int,
        idolName: String,
        detail: String,
        numberOfTickets: Int,
        enable: Boolean
    ): Boolean {
        return try {
            ownedTicketsDao.insert(
                OwnedTicketsEntity(
                    id = 0, // 自動的にIDを入れるときは0を入れる
                    date = date,
                    startTime = startTime,
                    endTime = endTime,
                    place = place,
                    eventName = eventName,
                    genre = genre,
                    idolName = idolName,
                    detail = detail,
                    numberOfTickets = numberOfTickets,
                    enable = enable
                )
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