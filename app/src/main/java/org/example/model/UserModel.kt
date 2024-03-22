package org.example.model

import QueueApiFunctions.getQueueCount
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

// All the values are stored here. UserController invokes this
@Serializable
data class Exercise(
    val name: String,
    val description: String,
    val totalNumberOfMachines: Int,
    val numberOfAvailableMachines: Int,
    val gymId: Int,
    val queueSize: Int)

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

    suspend fun fetchHerokuData(client: HttpClient) {
        try {
            val gym_id = 1
            val responseText = client.get("https://cs346-server-d1175eb4edfc.herokuapp.com/gyms/${gym_id}/exercises").toString()
            val exercises = Json.decodeFromString<List<Exercise>>(responseText)
            allMachines = exercises.map { it.name }
            notifySubscribers()
        } catch (e: Exception) {
            println("Failed to retrieve data from Heroku: ${e.message}")
        }
    }

    suspend fun fetchDatabaseStuff() {
        // add calls to Supabase here to get workouts, machine info, etc
        try {
//            allMachines = listOf(
//                "Treadmill",
//                "Stationarybike",
//                "Ellipticaltrainer",
//                "Rowingmachine",
//                "Smith machine",
//                "Legpressmachine",
//                "Chestpress",
//                "Latpulldownmachine",
//                "Legextensionmachine",
//                "Legcurlmachine",
//                "Seatedcalfraisemachine",
//                "Cablecrossovermachine",
//                "Shoulderpressmachine",
//                "Dumbbellrack",
//                "Adjustablebench",
//                "Abdominalcrunchmachine",
//                "Hipabduction",
//                "Assistedpull-up",
//                "Smithmachine",
//                "Hacksquatmachine"
//            )
            // Get full list of Exercises
            val client = HttpClient()
            val gymId = 1
            val responseExercises: HttpResponse = client.get("https://cs346-server-d1175eb4edfc.herokuapp.com/gyms/${gymId}/exercises")
            val exercises: String = responseExercises.body()
            val jsonArray = Json.parseToJsonElement(exercises).jsonArray
            allMachines = jsonArray.map { it.jsonObject["name"]!!.jsonPrimitive.content }

            // Get Saved workouts
            val userId = 5
            val responseSavedWorkotus: HttpResponse = client.get("https://cs346-server-d1175eb4edfc.herokuapp.com/workouts/user/${userId}")
            val savedWorkoutsUser: String = responseSavedWorkotus.body()
            val jsonArrayTmp = Json.parseToJsonElement(savedWorkoutsUser).jsonArray
            val workouts = jsonArrayTmp.map { workoutElement ->
                val workoutName = workoutElement.jsonObject["name"]?.jsonPrimitive?.content ?: ""
                val exercisesJsonArray = workoutElement.jsonObject["exercises"]?.jsonArray

                val exerciseNames = exercisesJsonArray?.mapNotNull { exercise ->
                    exercise.jsonObject["name"]?.jsonPrimitive?.content
                } ?: emptyList()

                Workout(workoutName, exerciseNames.toMutableList())
            }
            savedWorkouts = workouts.toMutableList()
            notifySubscribers()
        } catch (e: Exception) {
            println("Failed to retrieve data from Heroku: ${e.message}")
        }
        // temporarily hardcoding values for testing:

//        savedWorkouts = mutableListOf(
//            Workout("Cardio Blast",
//                mutableListOf(
//                    "Stationarybike",
//                    "Treadmill",
//                    "Ellipticaltrainer")
//            ),
//            Workout("Upper Body Sculpt",
//                mutableListOf(
//                    "Smith machine",
//                    "Chestpress",
//                    "Latpulldownmachine",
//                    "Shoulderpressmachine",
//                    "Dumbbellrack"
//                )),
//            Workout("Leg Day",
//                mutableListOf(
//                    "Legpressmachine",
//                    "Legextensionmachine",
//                    "Legcurlmachine",
//                    "Seatedcalfraisemachine",
//                    "Hacksquatmachine")
//            ),
//            Workout("Full Body Burn",
//                mutableListOf(
//                    "Rowingmachine",
//                    "Cablecrossovermachine",
//                    "Adjustablebench",
//                    "Abdominalcrunchmachine",
//                    "Hipabduction")
//            ),
//            Workout("Strength and Stability",
//                mutableListOf(
//                    "Assistedpull-up",
//                    "Smith machine")
//            )
//
//        )

//        notifySubscribers()
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

    // Queue Management Functions

    
}