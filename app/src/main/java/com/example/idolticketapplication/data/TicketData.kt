package com.example.idolticketapplication.data

data class TicketData(
    val id: Int,
    val date: String,
    val time: String,
    val place: String,
    val genre: String,
    val idolName: String,
    val numberOfTickets: Int
)

var demoTickets = listOf(
    TicketData(
        id = 0,
        date = "2025/04/01",
        time = "17:00",
        place = "タワーレコード渋谷店",
        genre = "チェキ会",
        idolName = "カミヤサキ",
        numberOfTickets = 3
    ),
    TicketData(
        id = 1,
        date = "2025/04/02",
        time = "18:00",
        place = "タワーレコード梅田大阪マルビル店",
        genre = "チェキ会",
        idolName = "テラシマユウカ",
        numberOfTickets = 1
    ),
    TicketData(
        id = 2,
        date = "2025/04/03",
        time = "19:00",
        place = "タワーレコード 福岡PARCO店",
        genre = "チェキ会",
        idolName = "ユメノユア",
        numberOfTickets = 2
    )
)