import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(): Unit = runBlocking {
    val dataChannel = Channel<MutableList<Int>>()
    val producerJob = launch {
        val serie  = mutableListOf<Int>()
        var TIMER = 0;
        repeat(2  ) {
            repeat(5) {
                delay(200L)
                val randomClassNumber = (0..5).random()
                serie.add(randomClassNumber)
                TIMER += 1;
                if (TIMER == 5) {
                    dataChannel.send(serie)
                    TIMER = 0;

                }
            }
        }
    }
    launch {
        for (received in dataChannel) {
            val message = received.joinToString(",")
            println("Received: [$message]")
        }
        println("End")
    }
}