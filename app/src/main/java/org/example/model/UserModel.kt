package org.example.model

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

    var selectedWorkout: Int = 0
        set(value) {
            field = value
            notifySubscribers()
        }
    
}