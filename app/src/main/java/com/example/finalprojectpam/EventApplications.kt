package com.example.finalprojectpam

import android.app.Application
import com.example.finalprojectpam.di.AppContainer
import com.example.finalprojectpam.di.AppContainerEvent


class EventApplications: Application(){
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container= AppContainerEvent()
    }
}