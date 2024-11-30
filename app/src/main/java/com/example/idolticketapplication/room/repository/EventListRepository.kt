package com.example.idolticketapplication.room.repository

import com.example.idolticketapplication.room.EventListDao
import com.example.idolticketapplication.room.EventListEntity

/*
* event_list_databaseのevent_list_tableを操作する
*/

class EventListRepository(private val eventListDao: EventListDao) {

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
        price: Int,
        stock: Int,
        sold: Int,
        enable: Boolean
    ): Boolean {
        return try {
            eventListDao.insert(
                EventListEntity(
                    id = 0, // 自動的にIDを入れるときは0を入れる
                    date = date,
                    startTime = startTime,
                    endTime = endTime,
                    place = place,
                    eventName = eventName,
                    genre = genre,
                    idolName = idolName,
                    detail = detail,
                    price = price,
                    stock = stock,
                    sold = sold,
                    enable = enable
                )
            )
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun update(eventListEntity: EventListEntity): Boolean {
        return try {
            eventListDao.update(
                eventListEntity
            )
            true
        } catch (e: Exception) {
            false
        }
    }

    // DBのデータ全部取得
    suspend fun selectAll(): List<EventListEntity>? {
        val data = eventListDao.selectAll()
        data.ifEmpty {
            return null
        }
        return data
    }

    // DBの内容を全て消す
    suspend fun deleteAll(): Boolean {
        return try {
            eventListDao.deleteAll()
            true
        } catch (e: Exception) {
            false
        }
    }
}