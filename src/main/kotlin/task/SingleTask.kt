package task

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class SingleTask<P, R> {

    private val workFlow = MutableSharedFlow<P>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_LATEST
    )

    private val resultFlow = MutableSharedFlow<R>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    init {
        GlobalScope.launch {
            workFlow
                .buffer(capacity = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)
                .flatMapLatest { createFlow(it) }
                .collect { resultFlow.emit(it) }
        }
    }

    protected abstract fun createFlow(params: P): Flow<R>

    fun execute(params: P): Flow<R> {
        workFlow.tryEmit(params)
        return resultFlow.asSharedFlow()
    }
}
