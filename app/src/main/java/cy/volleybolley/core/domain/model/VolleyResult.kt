package cy.volleybolley.core.domain.model

sealed interface VolleyResult<Data, Error> {
    val isSuccess: Boolean
    val isFailure: Boolean

    data class Success<Data, Error>(val data: Data) : VolleyResult<Data, Error> {
        override val isSuccess = true
        override val isFailure = false
    }

    data class Failure<Data, Error>(val error: Error) : VolleyResult<Data, Error> {
        override val isSuccess = false
        override val isFailure = true
    }
}

// Утилиты
inline fun <Data, Error> VolleyResult<Data, Error>.onSuccess(action: (Data) -> Unit): VolleyResult<Data, Error> {
    if (this is VolleyResult.Success) action(data)
    return this
}

inline fun <Data, Error> VolleyResult<Data, Error>.onFailure(action: (Error) -> Unit): VolleyResult<Data, Error> {
    if (this is VolleyResult.Failure) action(error)
    return this
}