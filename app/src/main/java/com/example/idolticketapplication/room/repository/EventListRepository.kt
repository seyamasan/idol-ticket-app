package com.example.idolticketapplication.room.repository

import com.example.idolticketapplication.room.EventListDao
import com.example.idolticketapplication.room.EventListEntity

/*
* event_list_databaseのevent_list_tableを操作する
*/

class EventListRepository(private val eventListDao: EventListDao) {

    // DBにデータを保存
    suspend fun insert(
        eventListEntity: EventListEntity
    ): Boolean {
        return try {
            eventListDao.insert(
                eventListEntity
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