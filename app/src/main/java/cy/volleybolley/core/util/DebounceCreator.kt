package cy.volleybolley.core.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun <T> createDebounceMethod(
    delayMillis: Long,
    coroutineScope: CoroutineScope,
    restartActionOnLastParam: Boolean,
    action: (T) -> Unit
): (T) -> Unit {
    var debounceJob: Job? = null
    return { param: T ->
        if (restartActionOnLastParam) {
            debounceJob?.cancel()
        }
        if (debounceJob?.isCompleted != false || restartActionOnLastParam) {
            debounceJob = coroutineScope.launch {
                delay(delayMillis)
                action(param)
            }
        }
    }
}
