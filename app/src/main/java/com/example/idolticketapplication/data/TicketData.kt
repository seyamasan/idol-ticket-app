package com.example.idolticketapplication.data

data class TicketData(
    val id: Int,
    val date: String,
    val startTime: String,
    val endTime: String,
    val place: String,
    val eventName: String,
    val genre: Int,
    val idolName: String,
    val detail: String,
    val numberOfTickets: Int,
    val enable: Boolean
)

var demoTickets = listOf(
    TicketData(
        id = 0,
        date = "2025/04/01",
        startTime = "17:00",
        endTime = "18:00",
        place = "タワーレコード渋谷店",
        eventName = "めっちゃチェキ会",
        genre = 1,
        idolName = "カミヤサキ",
        detail = "カミヤサキとチェキ撮れます。",
        numberOfTickets = 3,
        enable = true
    ),
    TicketData(
        id = 1,
        date = "2025/04/02",
        startTime = "18:00",
        endTime = "21:00",
        place = "日本武道館",
        eventName = "めちゃめちゃライブ",
        genre = 0,
        idolName = "ギャンパレ",
        detail = "日本武道館でライブします。",
        numberOfTickets = 5,
        enable = true
    ),
    TicketData(
        id = 2,
        date = "2025/04/03",
        startTime = "17:00",
        endTime = "19:00",
        place = "タワーレコード渋谷店",
        eventName = "チーチーと握手会",
        genre = 2,
        idolName = "チッチチチーチーチー",
        detail = "チーチーと握手できます。",
        numberOfTickets = 10,
        enable = true
    )
)