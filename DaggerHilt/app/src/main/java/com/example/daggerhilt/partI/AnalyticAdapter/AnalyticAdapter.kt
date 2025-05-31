package com.example.daggerhilt.partI.AnalyticAdapter

import android.util.Log
import com.example.daggerhilt.partI.AnalyticAdapter.inter.AnalyticsService
import javax.inject.Inject

class AnalyticAdapter @Inject constructor(
    private val service: AnalyticsService
) {
    fun trackScreen(screen: String){
        Log.d("Analytics","Tracking: $screen")
        service.sendEvent(screen)
    }
}