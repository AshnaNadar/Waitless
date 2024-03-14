package org.example.userinterface

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import org.example.model.UserModel
import org.example.model.Workout

// Read all values needed in the UI from here

class UserViewModel(val model: UserModel) : ISubscriber {
    var username = mutableStateOf("")
    var password = mutableStateOf("")

    // Home Page
    var selectedWorkout = mutableStateOf(Workout("", mutableListOf<String>())) // Empty if no workout selected

    // Queue Management Stuff
    var userQueueCount = mutableIntStateOf(12)

    // Saved Workouts
    var creatingWorkout = mutableStateOf(false)

    // Database Stuff
        var savedWorkouts = mutableStateOf(emptyList<Workout>())
    var allMachineNames = mutableStateOf(emptyList<String>())

    init {
        model.subscribe(this)
        model.fetchDatabaseStuff()
//        model.fetchQueueAPIdata()
    }

    fun addNewWorkout() {
        model.addNewWorkout()
    }

    fun addWorkoutName(workoutName: String) {
        model.addWorkoutName(workoutName)
    }

    fun removeWorkout() {
        model.removeWorkout()
    }
    fun addMachine(machine: String) {
        model.addMachine(machine)
    }

    fun removeMachine(machine: String) {
        model.removeMachine(machine)
    }

    override fun update() {
        username.value = model.username
        password.value = model.password

        selectedWorkout.value = model.selectedWorkout
        userQueueCount.intValue = model.userQueueCount
        creatingWorkout.value = model.creatingWorkout

        // Database Stuff
        savedWorkouts.value = model.savedWorkouts
        allMachineNames.value = model.allMachineNames
    }
}