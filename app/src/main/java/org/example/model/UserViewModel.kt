package org.example.model

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf

class UserViewModel(val model: UserModel) : ISubscriber {
    var username = mutableStateOf("")
    var password = mutableStateOf("")
    var selectedWorkout = mutableIntStateOf(0)

    init {
        model.subscribe(this)
    }

    override fun update() {
        username.value = model.username
        password.value = model.password
        selectedWorkout.intValue = model.selectedWorkout
    }
}