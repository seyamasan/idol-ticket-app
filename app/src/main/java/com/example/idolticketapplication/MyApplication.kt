package com.example.idolticketapplication

import android.app.Application
import com.example.idolticketapplication.di.idolTicketModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Koinの初期化
        startKoin {
            androidContext(this@MyApplication)
            modules(idolTicketModule) // 定義したDIモジュールを登録
        }
    }
}