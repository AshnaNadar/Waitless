// Class defining the structure of the JSON object returned by API calls

package org.example.queue.api

data class QueueData(
    val message: String?,
    val queues: List<Int>?,
    val count: Int?,
    val successCode: Int?
)