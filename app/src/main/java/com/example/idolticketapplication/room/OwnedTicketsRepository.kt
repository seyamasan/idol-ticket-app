package com.example.idolticketapplication.room

/*
* owned_tickets_databaseのowned_tickets_tableを操作する
*/

class OwnedTicketsRepository(private val ownedTicketsDao: OwnedTicketsDao) {

    // DBにデータを保存
    suspend fun insert(
        date: String,
        startTime: String,
        place: String,
        genre: String,
        idolName: String,
        numberOfTickets: Int,
        enable: Boolean
    ): Boolean {
        return try {
            ownedTicketsDao.insert(
                OwnedTicketsEntity(
                    id = 0, // 自動的にIDを入れるときは0を入れる
                    date = date,
                    startTime = startTime,
                    place = place,
                    genre = genre,
                    idolName = idolName,
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