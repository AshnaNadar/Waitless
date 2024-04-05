package org.example.controller

import QueueApiFunctions.getQueueCount
import QueueApiFunctions.getUserQueues
import QueueApiFunctions.joinQueue
import QueueApiFunctions.leaveAllQueues
import QueueApiFunctions.leaveQueue
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import org.example.model.UserModel
import org.example.model.UserUpdate
import org.example.model.Workout

// If making changes to any variable via the frontend, invoke from here

class UserController(val model: UserModel) {

    fun refetchQueueAPIdata() {
        // Get Machine Wait Times
        model.allMachineData.forEach { machine ->
            getQueueCount(machine.id) { response ->
                model.machineWaitTimes[machine.name] = response.body()?.count ?: -1
            }
        }
        println("Fetched from Queue API")
    }

    fun selectWorkout(workout: Workout) {
        model.selectedWorkout = workout.copy()
        model.currentMachine = workout.machines.first()
        model.currentMachineID = workout.machineIds.first()
    }

    fun startWorkout() {
        refetchQueueAPIdata()
        model.workoutOngoing = true
        model.currentMachineID = model.selectedWorkout.machineIds.first()
        model.currentMachine = model.selectedWorkout.machines.first()
        joinQueue(model.currentMachineID, model.email) { response ->
            println("Joined Queue")
            println(model.userid)
        }
        if (model.machineWaitTimes[model.currentMachine] != 0) { // Queue is not empty
            model.waiting = true
        } else {
            model.machineStartTime = System.currentTimeMillis()
            model.waiting = false
        }
        model.workoutStartTime = System.currentTimeMillis()
        model.machinesCompleted = mutableListOf(model.currentMachine)
    }

    fun refreshQueueStatus() {
        var peopleWaiting = 0
        getQueueCount(model.currentMachineID) { response ->
            peopleWaiting = response.body()?.count ?: -1
            if (peopleWaiting == 1) {
                getUserQueues(model.email) { response ->
                    if (model.currentMachineID in (response.body()?.queues
                            ?: emptyList())
                    ) { // Check if current user is the one waiting in queue
                        leaveQueue(model.currentMachineID, model.email) {}
                        model.machineStartTime = System.currentTimeMillis()
                        model.waiting = false
                    }
                }
            }
        }
    }

    fun lastSet() {
        model.lastSet = true
        model.lastSetStartTime = System.currentTimeMillis()
        refetchQueueAPIdata()
        if (model.selectedWorkout.machineIds.size > 1) { // If more machines remaining in workout
            joinQueue(model.selectedWorkout.machineIds[1], model.email) {}
            refetchQueueAPIdata()
            model.selectedWorkout.inQueue.add(model.selectedWorkout.machines[1])
        }
    }

    fun endWorkout() {
        model.workoutOngoing = false
        model.lastSet = false
        model.selectedWorkout.name = ""
        model.selectedWorkout.machines = mutableListOf()
        model.selectedWorkout.machineIds = mutableListOf()
        model.currentMachine = ""
        model.currentMachineID = -1
        leaveAllQueues(model.email) {}
        model.selectedWorkout.inQueue.clear()
        model.waiting = false
        model.showWorkoutSummary = true
    }

    fun moveToNextMachine() {

        if (model.selectedWorkout.machines.size <= 1) { // If no more machines remaining in workout
            if (!(model.currentMachine in model.machinesCompleted)) {
                model.machinesCompleted += model.currentMachine
            }
            println("hiiiiii")
            endWorkout()
            return
        }

        leaveQueue(model.currentMachineID, model.email) {}

        model.currentMachine = model.selectedWorkout.machines[1]
        model.currentMachineID = model.selectedWorkout.machineIds[1]
        if (!(model.currentMachine in model.machinesCompleted)) {
            model.machinesCompleted += model.currentMachine
        }

        joinQueue(model.currentMachineID, model.email) {}


        model.lastSet = false
        model.selectedWorkout.machines = model.selectedWorkout.machines.drop(1).toMutableList()
        model.selectedWorkout.machineIds = model.selectedWorkout.machineIds.drop(1).toMutableList()



        refetchQueueAPIdata()
        if (model.machineWaitTimes[model.currentMachine] == 0) { // Check if no one is waiting
            model.machineStartTime = System.currentTimeMillis()
            model.waiting = false
            println("1hi")
        } else if (model.machineWaitTimes[model.currentMachine] == 1) { // Check if current user is the one waiting in queue
            getUserQueues(model.email) { response ->
                if (model.currentMachineID in (response.body()?.queues ?: emptyList())) {
                    leaveQueue(model.currentMachineID, model.email) {}
                    model.machineStartTime = System.currentTimeMillis()
                    model.waiting = false
                    println("21hi")
                } else { // join queue and wait
                    joinQueue(
                        model.currentMachineID,
                        model.email
                    ) {} // in case Last Set was not clicked
                    model.selectedWorkout.inQueue.add(model.currentMachine)
                    model.waiting = true
                    println("21hi")
                }
            }
            println("2hi")
        } else { // join queue and wait
            joinQueue(model.currentMachineID, model.email) {} // in case Last Set was not clicked
            model.waiting = true
            println("3hi")
        }
    }

    fun updateUserInfo(body: UserUpdate) {
        runBlocking {
            val client = HttpClient() {
                install(ContentNegotiation) {
                    json()
                }
            }
            client.put("https://cs346-server-d1175eb4edfc.herokuapp.com/sessions") {
                contentType(ContentType.Application.Json)
                setBody(body)
            }

            if (body.name.isNotEmpty()) {
                model.name = body.name
            } else if (body.email.isNotEmpty()) {
                model.email = body.email
            }
            println("Updating User Info")
        }
    }
}
        ////////////
//        model.currentMachine = model.selectedWorkout.machines[1]
//        joinQueue(model.currentMachine, model.email) {} // in case Last Set was not clicked
//        model.selectedWorkout.inQueue.add(model.currentMachine)
//
//        if (model.machineWaitTimes[model.currentMachine] == 0) { // Check if no one is waiting
//            leaveQueue(model.selectedWorkout.machines.first(), model.email) {}
//            model.selectedWorkout.machines = model.selectedWorkout.machines.drop(1).toMutableList()
//            model.selectedWorkout.inQueue.remove(model.currentMachine)
//            model.timeStarted = System.currentTimeMillis()
//            model.waiting = false
//            return true
//        } else {
//            model.waiting = true
//            return false
//        }
