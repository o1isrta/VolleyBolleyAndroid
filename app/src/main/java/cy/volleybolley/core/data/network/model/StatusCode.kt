package cy.volleybolley.core.data.network.model

import cy.volleybolley.core.data.network.model.StatusCode.Companion.CODE_BAD_REQUEST
import cy.volleybolley.core.data.network.model.StatusCode.Companion.CODE_NOT_FOUND
import cy.volleybolley.core.data.network.model.StatusCode.Companion.CODE_SERVER_ERROR
import cy.volleybolley.core.domain.model.ErrorType

class StatusCode(val code: Int) {
    companion object {
        const val CODE_BAD_REQUEST = 400
        const val CODE_NOT_FOUND = 404
        const val CODE_SERVER_ERROR = 500
    }
}

fun StatusCode.mapToErrorType(): ErrorType {
    return when (code) {
        -1 -> ErrorType.NO_CONNECTION
        CODE_BAD_REQUEST -> ErrorType.BAD_REQUEST
        CODE_NOT_FOUND -> ErrorType.NOT_FOUND
        CODE_SERVER_ERROR -> ErrorType.SERVER_ERROR
        else -> ErrorType.UNKNOWN_ERROR
    }
}
