package org.example.userinterface

import QueueApiFunctions.addUser
import QueueApiFunctions.joinQueue
import android.annotation.SuppressLint
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.delay
import org.example.model.UserModel
import org.example.model.Workout

// Read all values needed in the UI from here

class UserViewModel(val model: UserModel) : ISubscriber {
    // User Infi
    var userid: String = ""

    // Today's Workout (Home Page)
    var selectedWorkout = mutableStateOf(Workout("", mutableListOf<String>(), mutableSetOf<String>())) // Empty if no workout selected
    var workoutOngoing = mutableStateOf(false)
    var timeStarted = mutableLongStateOf(System.currentTimeMillis())
    var currentMachine = mutableStateOf("")

    // Creating and Editing Workouts (Saved Page)
    var creatingWorkout = mutableStateOf(false)
    var editingWorkout = mutableStateOf(false)

    // Queue Management Stuff
    var userQueueCount = mutableIntStateOf(12)
    var machineWaitTimes = mutableStateOf(mutableMapOf<String, Int>())

    // Database Stuff
    var savedWorkouts = mutableStateOf(emptyList<Workout>())
    var allMachineNames = mutableStateOf(emptyList<String>())

    init {
        model.subscribe(this)
        model.fetchDatabaseStuff()
        addUser(model.userid) {}
        model.fetchQueueAPIdata()
    }

    // Saved Workout Functions

    // if no workout supplied, sets creating workout state to true
    // else, adds workout to saved workouts and sets creating workout state to false
    fun addNewWorkout() {
        model.addNewWorkout()
    }

    fun addWorkoutName(workoutName: String) {
        model.addWorkoutName(workoutName)
    }

    fun removeWorkout() {
        model.removeWorkout()
    }

    fun editWorkout() {
        model.editWorkout()
    }

    fun addMachine(machine: String) {
        model.addMachine(machine)
    }

    fun removeMachine(machine: String) {
        model.removeMachine(machine)
    }

    // Queue Management Functions


    override fun update() {
        userid = model.userid

        // Today's Workout (Home)
        workoutOngoing.value = model.workoutOngoing
        timeStarted.longValue = model.timeStarted
        currentMachine.value = model.currentMachine

        // Editing Workouts
        creatingWorkout.value = model.creatingWorkout
        editingWorkout.value = model.editingWorkout

        // Queue Management
        selectedWorkout.value = model.selectedWorkout
        userQueueCount.intValue = model.userQueueCount
        machineWaitTimes.value = model.machineWaitTimes

        // Database Stuff
        savedWorkouts.value = model.savedWorkouts
        allMachineNames.value = model.allMachines
    }
}