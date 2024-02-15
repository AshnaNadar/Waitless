package org.example.userinterface

import androidx.compose.runtime.mutableStateOf
import org.example.model.UserModel

class LoginViewModel(val model: UserModel) : ISubscriber {
    var firstname = mutableStateOf("")
    var lastname = mutableStateOf("")

    init {
        model.subscribe(this)
    }

    override fun update() {
        firstname.value = model.firstname
        lastname.value = model.lastname
    }
}