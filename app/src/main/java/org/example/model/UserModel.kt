package org.example.model

import QueueApiFunctions.addUser
import QueueApiFunctions.getUserQueues
import android.util.Log

// All the values are stored here. UserController invokes this

data class Workout(
    var name: String,
    val machines: MutableList<String>
)
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

    // Saved Workout Stuff
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

    // Database Stuff
    var savedWorkouts: MutableList<Workout> = emptyList<Workout>().toMutableList()
    var allMachineNames: List<String> = emptyList()

    // Home Screen
    var selectedWorkout: Workout = Workout("", mutableListOf<String>())
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

        savedWorkouts = mutableListOf(
            Workout("Cardio Blast",
                mutableListOf(
                    "Treadmill",
                    "Stationarybike",
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
                    "Hipabduction/adductionmachine")
            ),
            Workout("Strength and Stability",
                mutableListOf(
                    "Assistedpull-up/dipmachine",
                    "Smith machine")
            )

        )

        notifySubscribers()
    }

    // creates empty Workout and adds it to savedWorkouts
    fun addNewWorkout() {
        creatingWorkout = true
        savedWorkouts.add(Workout("", mutableListOf<String>()))
    }

    // adds name to last created saved workout
    // ends creating workout process
    fun addWorkoutName(workoutName: String) {
        savedWorkouts.last().name = workoutName
        creatingWorkout = false
        selectedWorkout = savedWorkouts.last()
    }

    // removes last saved workout
    fun removeWorkout() {
        creatingWorkout = false
        savedWorkouts.removeLast()
    }

    // apply edits to selectedWorkout in savedWorkouts
    fun editWorkout() {
        editingWorkout = false
        savedWorkouts.forEachIndexed { i, workout ->
            if (workout.name == selectedWorkout.name) {
                savedWorkouts[i] = selectedWorkout
            }
        }
        selectedWorkout.name = ""
        selectedWorkout.machines.clear()
    }

    // adds machine to workoutName
    // or last Workout in savedWorkouts if not specified
    fun addMachine(machine: String) {
        if (creatingWorkout) {
            savedWorkouts.last()
                .machines.add(machine)
        } else { // editing workout
            selectedWorkout.machines.add(machine)
        }
    }

    // removes machine to workoutName
    // or last Workout in savedWorkouts if not specified
    fun removeMachine(machine: String) {
        if (creatingWorkout) {
            savedWorkouts.last()
                .machines.remove(machine)
        } else { // editing workout
            selectedWorkout.machines.remove(machine)
        }
    }
    
}