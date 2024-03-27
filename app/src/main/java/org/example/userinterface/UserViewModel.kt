package org.example.userinterface

import QueueApiFunctions.addUser
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import org.example.model.UserModel
import org.example.model.Workout
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

// Read all values needed in the UI from here
class UserViewModel(val model: UserModel) : ViewModel(), ISubscriber {
    // User Info
    var userid: String = ""

    // Today's Workout (Home Page)
    var selectedWorkout = mutableStateOf(Workout("", mutableListOf<String>(), mutableSetOf<String>())) // Empty if no workout selected
    var workoutOngoing = mutableStateOf(false)
    var timeStarted = mutableLongStateOf(System.currentTimeMillis())
    var currentMachine = mutableStateOf("")
    var waiting = mutableStateOf(true)

    // Creating and Editing Workouts (Saved Page)
    var creatingWorkout = mutableStateOf(false)
    var editingWorkout = mutableStateOf(false)

    // Queue Management Stuff
    var userQueueCount = mutableIntStateOf(12)
    var machineWaitTimes = mutableStateOf(mutableMapOf<String, Int>())

    // Database Stuff
    var savedWorkouts = mutableStateOf(emptyList<Workout>())
    var allMachineNames = mutableStateOf(emptyList<String>())

    // Session information
    var name = mutableStateOf("")
    var email = mutableStateOf("")

    init {
        viewModelScope.launch {
            model.fetchDatabaseStuff()
            model.fetchQueueAPIdata()
        }
        model.subscribe(this)
        addUser(model.userid) {}
    }

    // Saved Workout Functions

    // if no workout supplied, sets creating workout state to true
    // else, adds workout to saved workouts and sets creating workout state to false
    fun addWorkout(workoutName: String? = null) {
        model.addWorkout(workoutName)
    }

    fun removeWorkout() {
        model.removeWorkout()
    }

    fun editWorkout(workout: Workout? = null) {
        model.editWorkout(workout)
    }

    fun noMachinesAdded() : Boolean {
        return model.noMachinesAdded()
    }

    fun addMachine(machine: String) {
        model.addMachine(machine)
    }

    fun removeMachine(machine: String) {
        model.removeMachine(machine)
    }

    // Queue Management Functions

    // Session Management Functions

    suspend fun signOut() {
        model.signOut()
    }

    override fun update() {
        userid = model.userid

        // Today's Workout (Home)
        selectedWorkout.value = model.selectedWorkout
        workoutOngoing.value = model.workoutOngoing
        timeStarted.longValue = model.timeStarted
        currentMachine.value = model.currentMachine
        waiting.value = model.waiting

        // Editing Workouts
        creatingWorkout.value = model.creatingWorkout
        editingWorkout.value = model.editingWorkout

        // Queue Management
        userQueueCount.intValue = model.userQueueCount
        machineWaitTimes.value = model.machineWaitTimes

        // Database Stuff
        savedWorkouts.value = model.savedWorkouts
        allMachineNames.value = model.allMachines

        // Session information
        name.value = model.name
        email.value = model.email
    }
}