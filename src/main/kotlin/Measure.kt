inline fun<T> measureTimeMillisPair(function: () -> T): Pair<T, Long> {
    val startTime = System.currentTimeMillis()
    val result: T = function.invoke()
    val endTime = System.currentTimeMillis()

    return Pair(result, endTime - startTime)
}