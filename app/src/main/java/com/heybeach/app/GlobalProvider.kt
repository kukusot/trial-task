package com.heybeach.app

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.heybeach.profile.domain.data.AuthService
import com.heybeach.profile.domain.data.UserRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi

@SuppressLint("StaticFieldLeak")
object GlobalProvider {

    val authRemoteDataSource: UserRemoteDataSource by lazy {
        UserRemoteDataSource(AuthService())
    }

    lateinit var appContext: Context
        private set

    lateinit var preferences: SharedPreferences
        private set

    lateinit var main: CoroutineDispatcher
        private set
    lateinit var io: CoroutineDispatcher
        private set

    fun init(app: App) {
        appContext = app
        preferences = appContext.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        main = Dispatchers.Main
        io = Dispatchers.IO
    }

    @ExperimentalCoroutinesApi
    fun testInit(app: App, sharedPreferences: SharedPreferences) {
        appContext = app
        preferences = sharedPreferences
        main = Dispatchers.Unconfined
        io = Dispatchers.Unconfined
    }


}