package com.example.navigationtest

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import org.json.JSONObject

class NavTracker() :
    NavController.OnDestinationChangedListener {
    override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
        val json = JSONObject()
            .put("destination", destination.route)
            .put("startDestination", controller.graph.startDestinationRoute)
            .put("bundleJsonData", bundleToJson(arguments ?: Bundle()))

        Log.d("navigation_test", json.toString())
    }
    private fun bundleToJson(bundle: Bundle) = JSONObject()
        .apply {
            bundle.keySet().forEach { key ->
                put(key, JSONObject.wrap(bundle.get(key)))
            }
        }
}