package com.example.idolticketapplication.room

import android.os.Build
import android.os.Parcelable
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Parcelize
@Entity(tableName = "owned_tickets_table")
data class OwnedTicketsEntity (
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "start_time") val startTime: String,
    @ColumnInfo(name = "end_time") val endTime: String,
    @ColumnInfo(name = "place") val place: String,
    @ColumnInfo(name = "event_name") val eventName: String,
    @ColumnInfo(name = "genre") val genre: Int,
    @ColumnInfo(name = "idol_name") val idolName: String,
    @ColumnInfo(name = "detail") val detail: String,
    @ColumnInfo(name = "number_of_tickets") val numberOfTickets: Int,
    @ColumnInfo(name = "enable") val enable: Boolean
) : Parcelable {
    // dateをLocalDateに変換するプロパティ
    val localDate: LocalDate
        @RequiresApi(Build.VERSION_CODES.O)
        get() {
            // 日付フォーマットを確認し、適切に修正
            val formatter = DateTimeFormatter.ofPattern("yyyy/M/d")
            return LocalDate.parse(date, formatter)
        }
}

@Entity(tableName = "event_list_table")
data class EventListEntity (
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "start_time") val startTime: String,
    @ColumnInfo(name = "end_time") val endTime: String,
    @ColumnInfo(name = "place") val place: String,
    @ColumnInfo(name = "event_name") val eventName: String,
    @ColumnInfo(name = "genre") val genre: Int,
    @ColumnInfo(name = "idol_name") val idolName: String,
    @ColumnInfo(name = "detail") val detail: String,
    @ColumnInfo(name = "price") val price: Int,
    @ColumnInfo(name = "stock") val stock: Int,
    @ColumnInfo(name = "sold") val sold: Int,
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