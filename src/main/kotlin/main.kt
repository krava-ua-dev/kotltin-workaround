import test.TimerTest
import test.singleTask.SingleTaskTest


fun main(args: Array<String>) {
    println("start")
    TestRunner.timers()
    println("end")
}


object TestRunner {
    fun singleTask() = SingleTaskTest.start()
    fun timers() = TimerTest.start()
}