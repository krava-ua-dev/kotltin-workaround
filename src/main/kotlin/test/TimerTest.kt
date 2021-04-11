package test

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import task.TimerTask

class TimerTest private constructor() {

    private val timer = TimerTask()

    init {
        GlobalScope.launch {
            startTimer(1, 2000)
            startTimer(2, 3000)
            startTimer(3, 4000)
        }
        Thread.sleep(9100)
    }

    private suspend fun startTimer(index: Int, time: Long) {
        println("execute timer$index")
        timer.launchIn(GlobalScope, time) {
            println("[$index] TICK! time left: $it")
            if (it == 0L) println()
        }
    }

    companion object {
        fun start() = TimerTest()
    }
}