package org.example.model

import QueueApiFunctions.addUser
import QueueApiFunctions.getUserQueues
import android.util.Log

// All the values are stored here. UserController invokes this

class UserModel : IPresenter() {

    var username: String = ""
        set(value) {
            field = value
            notifySubscribers()
        }

    var password: String = ""
        set(value) {
            field = value
            notifySubscribers()
        }

    var userid: String = "20871851"

    // Queue API Stuff
    var userQueueCount: Int = 10

    // Database Stuff
    var savedWorkouts: Map<String, List<String>> = emptyMap()
    var allMachineNames: List<String> = emptyList()

    // Home Screen
    var selectedWorkout: String = ""
        set(value) {
            field = value
            notifySubscribers()
        }


    fun fetchQueueAPIdata() {
        addUser(userid) { _ ->
            getUserQueues(userid) { response ->
                userQueueCount = response.body()?.count ?: 0
                notifySubscribers()
            }
        }
    }

    fun fetchDatabaseStuff() {
        // add calls to Supabase here to get workouts, machine info, etc

        // temporarily hardcoding values for testing:
        allMachineNames = listOf(
            "Treadmill",
            "Stationarybike",
            "Ellipticaltrainer",
            "Rowingmachine",
            "Smith machine",
            "Legpressmachine",
            "Chestpress",
            "Latpulldownmachine",
            "Legextensionmachine",
            "Legcurlmachine",
            "Seatedcalfraisemachine",
            "Cablecrossovermachine",
            "Shoulderpressmachine",
            "Dumbbellrack",
            "Adjustablebench",
            "Abdominalcrunchmachine",
            "Hipabduction/adductionmachine",
            "Assistedpull-up/dipmachine",
            "Smithmachine",
            "Hacksquatmachine"
        )
        savedWorkouts = mapOf(
            "Cardio Blast" to listOf(
                "Treadmill",
                "Stationarybike",
                "Ellipticaltrainer"
            ),
            "Upper Body Sculpt" to listOf(
                "Smith machine",
                "Chestpress",
                "Latpulldownmachine",
                "Shoulderpressmachine",
                "Dumbbellrack"
            ),
            "Leg Day" to listOf(
                "Legpressmachine",
                "Legextensionmachine",
                "Legcurlmachine",
                "Seatedcalfraisemachine",
                "Hacksquatmachine"
            ),
            "Full Body Burn" to listOf(
                "Rowingmachine",
                "Cablecrossovermachine",
                "Adjustablebench",
                "Abdominalcrunchmachine",
                "Hipabduction/adductionmachine"
            ),
            "Strength and Stability" to listOf(
                "Assistedpull-up/dipmachine",
                "Smith machine"
            )
        )
        notifySubscribers()
    }
    
}