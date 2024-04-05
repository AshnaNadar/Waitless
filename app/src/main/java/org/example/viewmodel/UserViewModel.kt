package org.example.viewmodel

import QueueApiFunctions.addUser
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import org.example.model.UserModel
import org.example.model.Workout
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.example.model.Machine

// Read all values needed in the UI from here
class UserViewModel(val model: UserModel) : ViewModel(), ISubscriber {
    // User Info
    var userid: String = ""

    // Today's Workout (Home Page)
    var selectedWorkout = mutableStateOf(Workout(0, "", mutableListOf<Int>(), mutableListOf<String>())) // Empty if no workout selected
    var workoutOngoing = mutableStateOf(false)
    var machineStartTime = mutableLongStateOf(System.currentTimeMillis())
    var currentMachine = mutableStateOf("")
    var currentMachineID = mutableIntStateOf(0)
    var waiting = mutableStateOf(true)
    var lastSet = mutableStateOf(false)
    var lastSetStartTime = mutableLongStateOf(System.currentTimeMillis())
    var showWorkoutSummary =  mutableStateOf(false)
    var workoutStartTime = mutableLongStateOf(System.currentTimeMillis())
    var machinesCompleted = mutableStateOf(mutableListOf<String>())

    // Creating and Editing Workouts (Saved Page)
    var creatingWorkout = mutableStateOf(false)
    var editingWorkout = mutableStateOf(false)

    // Equipment Info
    var selectedMachine = mutableStateOf(Machine(0, "", null, 0, 0, 0, 0, "", "", false, 0))
    var allMachineData = mutableStateOf(emptyList<Machine>())

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

    fun removeCreatedWorkout() {
        model.removeCreatedWorkout()
    }

    fun deleteWorkout(id: Int) {
        model.deleteWorkout(id)
    }

    fun editWorkout(workout: Workout? = null) {
        model.editWorkout(workout)
    }

    fun noMachinesAdded() : Boolean {
        return model.noMachinesAdded()
    }

    fun addMachine(id: Int, name: String) {
        model.addMachine(id, name)
    }

    fun removeMachine(id: Int, name: String) {
        model.removeMachine(id, name)
    }

    // Equipment Info
    fun selectMachine(machineName: String) {
        model.selectMachine(machineName)
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
        machineStartTime.longValue = model.machineStartTime
        currentMachine.value = model.currentMachine
        currentMachineID.value = model.currentMachineID
        waiting.value = model.waiting
        lastSet.value = model.lastSet
        lastSetStartTime.longValue = model.lastSetStartTime
        showWorkoutSummary.value = model.showWorkoutSummary
        workoutStartTime.longValue = model.workoutStartTime
        machinesCompleted.value = model.machinesCompleted.toMutableList()

        // Editing Workouts
        creatingWorkout.value = model.creatingWorkout
        editingWorkout.value = model.editingWorkout

        // Equipment Info
        selectedMachine.value = model.selectedMachine
        allMachineData.value = model.allMachineData

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