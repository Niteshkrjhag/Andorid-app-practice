package com.example.daggerhilt.partI.AnalyticAdapter.inter

import android.util.Log
import javax.inject.Inject

interface AnalyticsService {
    fun sendEvent(event: String)
}

// Implementation of Interface

class FirebaseAnalyticsService @Inject constructor(): AnalyticsService{
    override fun sendEvent(event: String) {
        Log.e("Analytics","Event sent: $event")
    }
}