package test.singleTask

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import task.SingleTask

class HelloTask: SingleTask<String, String>() {

    override fun createFlow(params: String): Flow<String> {
        return flow {
            delay(2000)
            emit("Hello, $params!")
        }
    }

}