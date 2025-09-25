package com.thanhtam.linhsondich.common

import android.app.Application
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.gson.Gson
import java.util.HashMap

class App : Application() {
    companion object {
        lateinit var shared: App

    }

    override fun onCreate() {
        super.onCreate()
        shared = this
    }

}