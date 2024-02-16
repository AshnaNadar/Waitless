package org.example.userinterface

import androidx.compose.runtime.mutableStateOf
import org.example.model.UserModel

class LoginViewModel(val model: UserModel) : ISubscriber {
    var username = mutableStateOf("")
    var password = mutableStateOf("")

    init {
        model.subscribe(this)
    }

    override fun update() {
        username.value = model.username
        password.value = model.password
    }
}