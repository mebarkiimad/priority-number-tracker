import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


fun classify(serie: MutableList<Int>): HashMap<Int, Int> {
    val map: HashMap<Int, Int> = hashMapOf()
    serie.forEachIndexed { index, value ->
        serie.forEachIndexed { subIndex, subValue ->
            if (subValue == value) {
                map[index] = map.getOrDefault(index, 0) + 1
            }
        }
    }
    return map
}

fun main(): Unit = runBlocking {
    val dataChannel = Channel<MutableList<Int>>() // Channel for communication
    val serie: MutableList<Int> = mutableListOf()

    val producerJob = launch {
        var counter = 0
        repeat(10) {
            repeat(5) {
                delay(200L)
                val randomClassNumber = (0..5).random()
                serie.add(randomClassNumber)
                counter += 1
                if (counter == 5) {
                    dataChannel.send(serie.toMutableList()) // Send a copy to avoid modification issues
                    counter = 0
                    serie.clear()
                }
            }
        }
        dataChannel.close() // Close the channel when done
    }

    val consumerJob = launch {
        for (received in dataChannel) { // Reads until the channel is closed
            val message = classify(serie = received)
            println("Received: [$message]")
        }
        println("End") // Only prints when the channel is closed
    }

    producerJob.join() // Wait for the producer to finish
    consumerJob.join() // Wait for the consumer to finish
}
