package com.example.mysterymessage

import android.app.Application
import com.example.mysterymessage.data.source.DefaultRepository
import com.example.mysterymessage.data.source.Repository
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {
}
