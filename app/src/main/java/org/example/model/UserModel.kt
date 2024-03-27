package org.example.model

import QueueApiFunctions.getQueueCount
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import QueueApiFunctions.joinQueue
import android.util.Log
import org.example.R
import javax.crypto.Mac

// All the values are stored here. UserController invokes this

@Serializable
data class UserUpdate(
    var name: String,
    var email: String,
    var password: String
)

@Serializable
data class Exercise(
    val name: String,
    val description: String,
    val totalNumberOfMachines: Int,
    val numberOfAvailableMachines: Int,
    val gymId: Int,
    val queueSize: Int)

data class Machine (
    val name: String,
    val targetMuscleGroup: String, // new
    val formDescription: String, // new
    val workingStatus: Boolean, // new
    val visual: Int, // new
    val formVisual: Int, // new
    val totalNumberOfMachines: Int,
    val numberOfAvailableMachines: Int,
    val gymId: Int,
    val queueSize: Int
)

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

    var name: String = ""
        set(value) {
            field = value
            notifySubscribers()
        }

    var email: String = ""
        set(value) {
            field = value
            notifySubscribers()
        }

    var userId: Int = 0
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
    var workoutOngoing: Boolean = false // Currently in a workout or not
        set(value) {
            field = value
            notifySubscribers()
        }
    var machineStartTime: Long = 0 // Start time of current ongoing machine
        set(value) {
            field = value
            notifySubscribers()
        }
    var waiting: Boolean = true // Waiting for next machine during a workout
        set(value) {
            field = value
            notifySubscribers()
        }
    var lastSet: Boolean = false // In the last set of current machine
        set(value) {
            field = value
            notifySubscribers()
        }
    var lastSetStartTime: Long = 0 // Start time of Last Set current ongoing machine
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

    // Equipment Info
    var selectedMachine: Machine = Machine("", "", "", false, 0, 0, 0, 0, 0, 0)
        set(value) {
            field = value
            notifySubscribers()
        }

    /* TEMP FOR TESTING -- START */

    var treadmillData = Machine(
        name = "Treadmill",
        targetMuscleGroup = "Quads, Glutes, Hamstrings, Calves",
        formDescription = "Draw your shoulders back and engage your core as you slightly lean forward. " +
                "Maintain an erect spine. Keep your shoulders directly above your hips. " +
                "Relax your arms, gaze straight ahead, and avoid looking down or at the monitor.",
        workingStatus = true,
        visual = R.drawable.treadmill,
        formVisual = R.drawable.treadmill,
        totalNumberOfMachines = 17,
        numberOfAvailableMachines = 38,
        gymId = 1,
        queueSize = 69
    )

    var chestpressData = Machine(
        name = "Chestpress",
        targetMuscleGroup = "Pectorals, Deltoids, Triceps",
        formDescription = "Step on foot lever and grasp handles approximately 1.5x shoulder width. Release foot lever." +
                "Slowly press forward until the arms are completely extended. " +
                "Reverse the pattern and return to the starting position moving through a maximum, comfortable range of motion.",
        workingStatus = false,
        visual = R.drawable.chestpress,
        formVisual = R.drawable.chestpress_form,
        totalNumberOfMachines = 17,
        numberOfAvailableMachines = 38,
        gymId = 1,
        queueSize = 69
    )

    var allMachineData: List<Machine> = listOf(treadmillData, chestpressData)

    /* TEMP FOR TESTING -- END */

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
            allMachines = listOf(
                "Treadmill",
//                "Stationarybike",
//                "Ellipticaltrainer",
//                "Rowingmachine",
//                "Smith machine",
//                "Legpressmachine",
                "Chestpress",
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
            )
            // Get full list of Exercises
            val client = HttpClient()
            val gymId = 1
            val responseExercises: HttpResponse = client.get("https://cs346-server-d1175eb4edfc.herokuapp.com/gyms/${gymId}/exercises")
            val exercises: String = responseExercises.body()
            val jsonArray = Json.parseToJsonElement(exercises).jsonArray
            //allMachines = jsonArray.map { it.jsonObject["name"]!!.jsonPrimitive.content }

            // Get Saved workouts
            val userId = 7
            val responseSavedWorkouts: HttpResponse = client.get("https://cs346-server-d1175eb4edfc.herokuapp.com/workouts/user/${userId}")
            val savedWorkoutsUser: String = responseSavedWorkouts.body()
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
    suspend fun signOut() {
        val client = HttpClient()
        client.post("https://cs346-server-d1175eb4edfc.herokuapp.com/auth/signout")
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

    // selects machine from allMachineData to display equipment info
    fun selectMachine(machineName: String) {
        allMachineData.forEach { machine ->
            if (machine.name == machineName) {
                selectedMachine = machine.copy()
                return
            }
        }
    }

    // Queue Management Functions


}