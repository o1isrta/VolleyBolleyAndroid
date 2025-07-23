package cy.volleybolley.courts.data

import android.net.http.HttpException
import cy.volleybolley.core.data.network.api.NetworkClient
import cy.volleybolley.core.data.network.model.ApiRequest
import cy.volleybolley.core.data.network.model.ApiResponse
import io.ktor.http.HttpMethod
import io.ktor.http.URLProtocol
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.ConcurrentHashMap

class CourtsRepository (private val networkClient: NetworkClient ) {

    suspend fun getCourts(searchQuery: String): Result<List<ApiResponse.CourtResponse>> = withContext(Dispatchers.IO) {
        val request = ApiRequest.ClassicRequest(
            protocol = URLProtocol.HTTPS,
            method = HttpMethod.Get,
            path = "courts",
            parameters = ConcurrentHashMap<String, String>().apply {
                put("search_query", searchQuery)
            }
        )

        when (val response = networkClient.getResponse(request)) {
            is ApiResponse.BadResponse -> {
                Result.failure(Exception(response.message))
            }

            else -> Result.success(emptyList())

        }
    }

}