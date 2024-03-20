package org.example.model

import QueueApiFunctions.getQueueCount
import QueueApiFunctions.joinQueue
import android.util.Log

// All the values are stored here. UserController invokes this

data class Workout(
    var name: String,
    var machines: MutableList<String>,
    var inQueue: MutableSet<String>
) {
    constructor(name: String, machines: MutableList<String>) : this (
        name = name,
        machines = machines,
        inQueue = mutableSetOf<String>()
    )
    fun copy(): Workout { // For deep copy
        val newMachines = mutableListOf<String>()
        newMachines.addAll(machines)
        val newQueue = mutableSetOf<String>()
        newQueue.addAll(inQueue)
        return Workout(name, newMachines, newQueue)
    }
}

class UserModel : IPresenter() {

    // User Info
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

    // Today's Workout (Home Page)
    var selectedWorkout: Workout = Workout("", mutableListOf<String>())
        set(value) {
            field = value
            notifySubscribers()
        }
    var currentMachine: String = ""
        set(value) {
            field = value
            notifySubscribers()
        }
    var workoutOngoing: Boolean = false
        set(value) {
            field = value
            notifySubscribers()
        }
    var timeStarted: Long = 0 // Start time of ongoing machine
        set(value) {
            field = value
            notifySubscribers()
        }

    // Creating and Editing workouts (Saved Page)
    var creatingWorkout: Boolean = false
        set(value) {
            field = value
            notifySubscribers()
        }
    var editingWorkout: Boolean = false
        set(value) {
            field = value
            notifySubscribers()
        }

    // Queue API Stuff
    var userQueueCount: Int = 10
        set(value) {
            field = value
            notifySubscribers()
        }
    var machineWaitTimes: MutableMap<String, Int> = mutableMapOf()
        set(value) {
            field = value
            notifySubscribers()
        }

    // Database Stuff
    var savedWorkouts: MutableList<Workout> = emptyList<Workout>().toMutableList()
    var allMachines: List<String> = emptyList()

    fun fetchQueueAPIdata() {
        // Get Machine Wait Times
        allMachines.forEach { machine ->
            machineWaitTimes[machine] = 0
            getQueueCount(machine) { response ->
                machineWaitTimes[machine] = response.body()?.count ?: -1
                notifySubscribers()
            }
        }
    }

    fun fetchDatabaseStuff() {
        // add calls to Supabase here to get workouts, machine info, etc

        // temporarily hardcoding values for testing:
        allMachines = listOf(
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
            "Hipabduction",
            "Assistedpull-up",
            "Smithmachine",
            "Hacksquatmachine"
        )

        savedWorkouts = mutableListOf(
            Workout("Cardio Blast",
                mutableListOf(
                    "Stationarybike",
                    "Treadmill",
                    "Ellipticaltrainer")
            ),
            Workout("Upper Body Sculpt",
                mutableListOf(
                    "Smith machine",
                    "Chestpress",
                    "Latpulldownmachine",
                    "Shoulderpressmachine",
                    "Dumbbellrack"
                )),
            Workout("Leg Day",
                mutableListOf(
                    "Legpressmachine",
                    "Legextensionmachine",
                    "Legcurlmachine",
                    "Seatedcalfraisemachine",
                    "Hacksquatmachine")
            ),
            Workout("Full Body Burn",
                mutableListOf(
                    "Rowingmachine",
                    "Cablecrossovermachine",
                    "Adjustablebench",
                    "Abdominalcrunchmachine",
                    "Hipabduction")
            ),
            Workout("Strength and Stability",
                mutableListOf(
                    "Assistedpull-up",
                    "Smith machine")
            )

        )

        notifySubscribers()
    }

    // creates new Workout and adds it to savedWorkouts
    fun addWorkout(workoutName: String? = null) {
        if (workoutName == null) { // start creating new workout in selectedWorkout
            removeWorkout() // remove selected workout
            creatingWorkout = true
        } else { // add selectedWorkout to savedWorkouts
            selectedWorkout.name = workoutName
            savedWorkouts.add(selectedWorkout.copy())
            currentMachine = selectedWorkout.machines.first() // newly added workout remains selected
            creatingWorkout = false
        }
    }

    fun noMachinesAdded() : Boolean {
        return selectedWorkout.machines.isEmpty()
    }

    // clears selectedWorkout
    fun removeWorkout() {
        creatingWorkout = false

        // clear selected workout
        selectedWorkout.name = ""
        selectedWorkout.machines.clear()
        selectedWorkout.inQueue.clear()
        currentMachine = ""
    }

    fun editWorkout(workout: Workout? = null) {
        if (workout != null) { // select workout to edit
            editingWorkout = true
            selectedWorkout = workout.copy()
        } else { // apply edits made to selectedWorkout in savedWorkouts
            editingWorkout = false
            savedWorkouts.forEachIndexed { i, savedWorkout ->
                if (savedWorkout.name == selectedWorkout.name) {
                    savedWorkouts[i] = selectedWorkout.copy()
                }
            }
            removeWorkout() // remove selected workout
        }
    }

    // adds machine to the selected workout
    fun addMachine(machine: String) {
        selectedWorkout.machines.add(machine)
    }

    // removes machine from the selected workout
    fun removeMachine(machine: String) {
        selectedWorkout.machines.remove(machine)
    }

    // Queue Management Functions

    
}