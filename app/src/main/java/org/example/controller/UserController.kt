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
        model.allMachines.forEach { machine ->
            getQueueCount(machine) { response ->
                model.machineWaitTimes[machine] = response.body()?.count ?: -1
            }
        }
        println("Fetched from Queue API")
    }

    fun selectWorkout(workout: Workout) {
        model.selectedWorkout = workout.copy()
        model.currentMachine = workout.machines.first()
    }

    fun startWorkout() {
        refetchQueueAPIdata()
        model.workoutOngoing = true
        model.currentMachine = model.selectedWorkout.machines.first()
        if (model.machineWaitTimes[model.currentMachine] != 0) { // Queue is not empty
            joinQueue(model.currentMachine, model.userid) { response ->
                println("Joined Queue")
            }
            model.waiting = true
        } else {
            model.timeStarted = System.currentTimeMillis()
            model.waiting = false
        }
    }

    fun refreshQueueStatus() {
        var peopleWaiting = 0
        getQueueCount(model.currentMachine) { response ->
            peopleWaiting = response.body()?.count ?: -1
            if (peopleWaiting == 1) {
                getUserQueues(model.userid) { response ->
                    println(response.body()?.queues)
                    if (model.currentMachine in (response.body()?.queues ?: emptyList())) { // Check if current user is the one waiting in queue
                        leaveQueue(model.currentMachine, model.userid) {}
                        model.timeStarted = System.currentTimeMillis()
                        model.waiting = false
                    }
                }
            }
        }
    }

        fun lastSet() {
            refetchQueueAPIdata()
            joinQueue(model.selectedWorkout.machines[1], model.userid) {}
            model.selectedWorkout.inQueue.add(model.selectedWorkout.machines[1])
        }

        fun endWorkout() {
            model.workoutOngoing = false
            model.selectedWorkout.name = ""
            model.currentMachine = ""
            leaveAllQueues(model.userid) {}
            model.selectedWorkout.inQueue.clear()
            model.waiting = true
        }

        fun moveToNextMachine() {
            model.currentMachine = model.selectedWorkout.machines[1]
            model.selectedWorkout.machines = model.selectedWorkout.machines.drop(1).toMutableList()

            refetchQueueAPIdata()
            if (model.machineWaitTimes[model.currentMachine] == 0) { // Check if no one is waiting
                model.timeStarted = System.currentTimeMillis()
                model.waiting = false
            } else if (model.machineWaitTimes[model.currentMachine] == 1) { // Check if current user is the one waiting in queue
                getUserQueues(model.userid) { response ->
                    println(response.body()?.queues)
                    if (model.currentMachine in (response.body()?.queues ?: emptyList())) {
                        leaveQueue(model.currentMachine, model.userid) {}
                        model.timeStarted = System.currentTimeMillis()
                        model.waiting = false
                    } else { // join queue and wait
                        joinQueue(model.currentMachine, model.userid) {} // in case Last Set was not clicked
                        model.selectedWorkout.inQueue.add(model.currentMachine)
                        model.waiting = true
                    }
                }
            } else { // join queue and wait
                joinQueue(model.currentMachine, model.userid) {} // in case Last Set was not clicked
                model.waiting = true
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

        ////////////
//        model.currentMachine = model.selectedWorkout.machines[1]
//        joinQueue(model.currentMachine, model.userid) {} // in case Last Set was not clicked
//        model.selectedWorkout.inQueue.add(model.currentMachine)
//
//        if (model.machineWaitTimes[model.currentMachine] == 0) { // Check if no one is waiting
//            leaveQueue(model.selectedWorkout.machines.first(), model.userid) {}
//            model.selectedWorkout.machines = model.selectedWorkout.machines.drop(1).toMutableList()
//            model.selectedWorkout.inQueue.remove(model.currentMachine)
//            model.timeStarted = System.currentTimeMillis()
//            model.waiting = false
//            return true
//        } else {
//            model.waiting = true
//            return false
//        }
    }