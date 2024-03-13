package org.example.userinterface

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import org.example.model.UserModel

// Read all values needed in the UI from here

class UserViewModel(val model: UserModel) : ISubscriber {
    var username = mutableStateOf("")
    var password = mutableStateOf("")

    // Home Page
    var selectedWorkout = mutableStateOf("") // Empty if no workout selected

    // Queue Management Stuff
    var userQueueCount = mutableIntStateOf(12)

    // Database Stuff
    var savedWorkouts = mutableStateOf(emptyMap<String, List<String>>())
    var allMachineNames = mutableStateOf(emptyList<String>())

    init {
        model.subscribe(this)
    }

    override fun update() {
        username.value = model.username
        password.value = model.password

        selectedWorkout.value = model.selectedWorkout
        userQueueCount.intValue = model.userQueueCount

        // Database Stuff
        savedWorkouts.value = model.savedWorkouts
        allMachineNames.value = model.allMachineNames
    }
}