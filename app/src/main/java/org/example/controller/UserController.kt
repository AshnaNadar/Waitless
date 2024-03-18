package org.example.controller

import QueueApiFunctions.getQueueCount
import QueueApiFunctions.joinQueue
import QueueApiFunctions.leaveAllQueues
import QueueApiFunctions.leaveQueue
import kotlinx.coroutines.delay
import org.example.model.UserModel
import org.example.model.Workout

// If making changes to any variable via the frontend, invoke from here

class UserController(val model: UserModel) {

    fun refetchQueueAPIdata() {
        // Get Machine Wait Times
        model.allMachines.forEach { machine ->
            model.machineWaitTimes[machine] = 0
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
        model.workoutOngoing = true
        joinQueue(model.selectedWorkout.machines.first(), model.userid) { response ->
            println("Joined Queue")
        }
        model.timeStarted = System.currentTimeMillis()
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
    }

    fun moveToNextMachine(){
        refetchQueueAPIdata()
        model.currentMachine = model.selectedWorkout.machines[1]
        joinQueue(model.currentMachine, model.userid) {} // in case Last Set was not clicked
        model.selectedWorkout.inQueue.add(model.currentMachine)

        if (model.machineWaitTimes[model.currentMachine] == 0) { // Check if no one is waiting
            leaveQueue(model.selectedWorkout.machines.first(), model.userid) {}
            model.selectedWorkout.machines = model.selectedWorkout.machines.drop(1).toMutableList()
            model.selectedWorkout.inQueue.remove(model.currentMachine)
            model.timeStarted = System.currentTimeMillis()
        }
    }
 }