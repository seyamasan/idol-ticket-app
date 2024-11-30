package com.example.idolticketapplication.data

import com.example.idolticketapplication.R

enum class EventGenre(val id: Int) {
    LIVE(0),
    PHOTO(1),
    HANDSHAKE(2),
    OTHERS(999);

    fun stringResId(): Int {
        return when (this) {
            LIVE -> R.string.genre_live
            PHOTO -> R.string.genre_photo
            HANDSHAKE -> R.string.genre_handshake
            OTHERS -> R.string.genre_others
        }
    }

    companion object {
        // 全要素をリストとして返す
        fun getAllGenres(): List<EventGenre> = entries

        // IDからジャンルを取得
        fun fromId(id: Int): EventGenre? = entries.find { it.id == id }

        fun getStringResId(genre: Int): Int {
            return when (genre) {
                LIVE.id -> R.string.genre_live
                PHOTO.id -> R.string.genre_photo
                HANDSHAKE.id -> R.string.genre_handshake
                OTHERS.id -> R.string.genre_handshake
                else -> R.string.genre_handshake
            }
        }
    }
}