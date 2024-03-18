// This file defines the API interface for the Queue Management API using Retrofit

import org.example.queue.api.QueueData
import retrofit2.Call
import retrofit2.http.*

interface QueueApiInterface {

    @GET("/")
    fun getRoot(): Call<QueueData>
    @POST("/add_user/{user_id}")
    fun addUser(@Path("user_id") userId: String): Call<QueueData>

    @POST("/remove_user/{user_id}")
    fun removeUser(@Path("user_id") userId: String): Call<QueueData>

    @POST("/join/{machine_id}/{user_id}")
    fun joinQueue(@Path("machine_id") machineId: String, @Path("user_id") userId: String): Call<QueueData>

    @POST("/leave/{machine_id}/{user_id}")
    fun leaveQueue(@Path("machine_id") machineId: String, @Path("user_id") userId: String): Call<QueueData>

    @POST("/leave_all/{user_id}")
    fun leaveAllQueues(@Path("user_id") userId: String): Call<QueueData>

    @GET("/waiting/{machine_id}")
    fun getQueueCount(@Path("machine_id") machineId: String): Call<QueueData>

    @GET("/queues/{user_id}")
    fun getUserQueues(@Path("user_id") userId: String): Call<QueueData>
}
