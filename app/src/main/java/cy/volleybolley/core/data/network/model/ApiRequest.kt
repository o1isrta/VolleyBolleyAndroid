package cy.volleybolley.core.data.network.model

import io.ktor.http.HttpMethod
import io.ktor.http.URLProtocol
import java.util.concurrent.ConcurrentHashMap

sealed class ApiRequest {
    abstract var protocol: URLProtocol
    abstract var method: HttpMethod
    abstract var host: String?
    abstract var path: String?
    abstract val headers: ConcurrentHashMap<String, String>
    abstract val parameters: ConcurrentHashMap<String, String>
    abstract val body: Any?

    class ClassicAuthRequest(
        override var protocol: URLProtocol = URLProtocol.HTTP,
        override var method: HttpMethod = HttpMethod.Get,
        override var host: String? = null,
        override var path: String? = null,
        override val headers: ConcurrentHashMap<String, String> = ConcurrentHashMap<String, String>(),
        override val parameters: ConcurrentHashMap<String, String> = ConcurrentHashMap<String, String>(),
        override val body: Any? = null,
    ): ApiRequest()

}






