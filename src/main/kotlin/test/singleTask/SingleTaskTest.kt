package test.singleTask

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SingleTaskTest private constructor() {

    private val helloTask = HelloTask()

    init {
        GlobalScope.launch {
            println("[1] execute")
            helloTask.execute("Max")
                .collect {
                    println("[1] collect: $it")
                }
        }

        GlobalScope.launch {
            delay(500)
            println("[2] execute")
            helloTask.execute("Peter")
                .collect {
                    println("[2] collect: $it")
                }
        }

        Thread.sleep(4000)
    }


    companion object {
        fun start() = SingleTaskTest()
    }
}