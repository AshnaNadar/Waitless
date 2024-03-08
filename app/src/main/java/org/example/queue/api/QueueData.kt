// Class defining the structure of the JSON object returned by API calls

package org.example

data class QueueData(
    val message: String?,
    val queues: List<String>?,
    val count: Int?
)