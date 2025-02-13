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
fun main(){
    print("Enter the number of series to iterate: ")
    val seriesCount = readln().toInt()

    print("Enter the length of the series: ")
    val serieLength = readln().toInt()
    process(seriesCount,serieLength)
}
fun process(seriesCount:Int,serieLength:Int): Unit = runBlocking {
    val dataChannel = Channel<MutableList<Int>>() // Channel for communication
    val serie: MutableList<Int> = mutableListOf()
    val producerJob = launch {
        repeat(seriesCount) {
            repeat(serieLength) {
                delay(200L)
                val randomClassNumber = (0..5).random()
                serie.add(randomClassNumber)
                if (serie.size == serieLength) {
                    dataChannel.send(serie.toMutableList()) // Send a copy to avoid modification issues
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
