package com.example.idolticketapplication.room

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Entity(tableName = "owned_tickets_table")
data class OwnedTicketsEntity (
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "start_time") val startTime: String,
    @ColumnInfo(name = "place") val place: String,
    @ColumnInfo(name = "genre") val genre: String,
    @ColumnInfo(name = "idol_name") val idolName: String,
    @ColumnInfo(name = "number_of_tickets") val numberOfTickets: Int,
    @ColumnInfo(name = "enable") val enable: Boolean
) {
    // dateをLocalDateに変換するプロパティ
    val localDate: LocalDate
        @RequiresApi(Build.VERSION_CODES.O)
        get() {
            // 日付フォーマットを確認し、適切に修正
            val formatter = DateTimeFormatter.ofPattern("yyyy/M/d")
            return LocalDate.parse(date, formatter)
        }
}