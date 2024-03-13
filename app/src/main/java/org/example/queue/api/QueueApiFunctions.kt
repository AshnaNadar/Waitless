// This file consists of API calls of QueueApiInterface.kt implemented as functions

import android.util.Log
import org.example.queue.api.QueueData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object QueueApiFunctions {

    // Initializing API interface
    private val retrofitBuilder: QueueApiInterface = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("http://10.0.2.2:8000") // localhost for testing purposes
//        .baseUrl("https://waitless-queue.onrender.com")
        .build()
        .create(QueueApiInterface::class.java)

    var result: QueueData? = null

    // Testing
    fun getRoot(): QueueData? {
        val retrofitCall = retrofitBuilder.getRoot()
        retrofitCall.enqueue(object : Callback<QueueData?> {
            override fun onResponse(call: Call<QueueData?>, response: Response<QueueData?>) {
                result = response.body()
            }
            override fun onFailure(call: Call<QueueData?>, t: Throwable) {}
        })
        return result
    }


    // Function to add a new user (POST)
    fun addUser(userId: String, onResponse: (Response<QueueData?>) -> Unit) {
        val retrofitCall = retrofitBuilder.addUser(userId)
        retrofitCall.enqueue(object : Callback<QueueData?> {
            override fun onResponse(call: Call<QueueData?>, response: Response<QueueData?>) {
                onResponse(response)
            }
            override fun onFailure(call: Call<QueueData?>, t: Throwable) {
                println("API Error")
            }
        })
    }


    // Function to remove a user
    fun removeUser(userId: String): QueueData? {
        val retrofitCall = retrofitBuilder.removeUser(userId)
        retrofitCall.enqueue(object : Callback<QueueData?> {
            override fun onResponse(call: Call<QueueData?>, response: Response<QueueData?>) {
                result = response.body()
            }

            override fun onFailure(call: Call<QueueData?>, t: Throwable) {
                Log.e("API Error", t.message ?: "Unknown error")
            }
        })
        return result
    }

    // Function to join a queue
    fun joinQueue(machineId: String, userId: String): QueueData? {
        val retrofitCall = retrofitBuilder.joinQueue(machineId, userId)
        retrofitCall.enqueue(object : Callback<QueueData?> {
            override fun onResponse(call: Call<QueueData?>, response: Response<QueueData?>) {
                result = response.body()
            }

            override fun onFailure(call: Call<QueueData?>, t: Throwable) {
                Log.e("API Error", t.message ?: "Unknown error")
            }
        })
        return result
    }

    // Function to leave a queue
    fun leaveQueue(machineId: String, userId: String) : QueueData? {
        val retrofitCall = retrofitBuilder.leaveQueue(machineId, userId)
        retrofitCall.enqueue(object : Callback<QueueData?> {
            override fun onResponse(call: Call<QueueData?>, response: Response<QueueData?>) {
                result = response.body()
            }

            override fun onFailure(call: Call<QueueData?>, t: Throwable) {
                Log.e("API Error", t.message ?: "Unknown error")
            }
        })
        return result
    }

    // Function to get the count of people in a queue for a specific machine
    fun getQueueCount(machineId: String): QueueData? {
        val retrofitCall = retrofitBuilder.getQueueCount(machineId)
        retrofitCall.enqueue(object : Callback<QueueData?> {
            override fun onResponse(call: Call<QueueData?>, response: Response<QueueData?>) {
                result = response.body()
            }
            override fun onFailure(call: Call<QueueData?>, t: Throwable) {
                Log.e("API Error", t.message ?: "Unknown error")
            }
        })
        return result
    }

    // Function to get the queues that a user is currently waiting in (GET)
    fun getUserQueues(userId: String, callback: (Response<QueueData?>) -> Unit) {
        val retrofitCall = retrofitBuilder.getUserQueues(userId)
        retrofitCall.enqueue(object : Callback<QueueData?> {
            override fun onResponse(call: Call<QueueData?>, response: Response<QueueData?>) {
                callback(response)
            }
            override fun onFailure(call: Call<QueueData?>, t: Throwable) {
                Log.e("API Error", t.message ?: "Unknown error")
            }
        })
    }
}

