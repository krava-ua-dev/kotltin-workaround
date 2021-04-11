package task

import kotlinx.coroutines.*
import measureTimeMillisPair

class TimerTask(
    private val tick: Long = 1000L
) {

    private var job = ConflatedJob()

    suspend fun launchIn(scope: CoroutineScope, total: Long, notifyStart: Boolean = true, onTick: (Long) -> Unit) {
        if (total % tick != 0L)
            throw IllegalArgumentException("Tick must be multiple of the total time")

        job.joinPreviousJob()
        job += scope.launch {
            val measure = measureTimeMillisPair {
                whileTimer(this, total, notifyStart, onTick)
            }
            println("Timer time=$total  work time = ${measure.second}")
        }
    }

    /**
     * Timer based on delay()
     */
    private suspend fun delayTimer(scope: CoroutineScope, total: Long, notifyStart: Boolean = true, onTick: (Long) -> Unit) {
        var timeLeft = total
        if (notifyStart) {
            onTick(timeLeft)
        }
        do {
            delay(tick)
            if (!scope.isActive) return

            timeLeft -= tick
            onTick(timeLeft)
        } while (timeLeft > 0)
    }


    /**
     * Timer based on while(true)
     */
    private fun whileTimer(scope: CoroutineScope, total: Long, notifyStart: Boolean = true, onTick: (Long) -> Unit) {
        val startTime = System.currentTimeMillis()
        var nextTick = tick
        if (notifyStart) {
            onTick(total)
        }
        while (true) {
            if (!scope.isActive) return

            if (System.currentTimeMillis() - startTime > nextTick) {
                onTick(total - nextTick)

                nextTick += tick
                if (nextTick > total) return
            }
        }
    }

}