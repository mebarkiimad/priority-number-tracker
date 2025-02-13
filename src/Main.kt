import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(): Unit = runBlocking {
    val dataChannel = Channel<Int>()
    val producerJob = launch {
        repeat(10000) { // launch a lot of coroutines
            launch {
                delay(500L)
                val randomClassNumber = (0..5).random()
                dataChannel.send(randomClassNumber)
            }
        }
    }
    launch {
        for (received in dataChannel) {
            println("Received: $received")
        }
    }
}