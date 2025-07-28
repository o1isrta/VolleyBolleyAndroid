package cy.volleybolley.referencedata.data.network

import cy.volleybolley.core.data.network.impl.KtorNetworkClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.path

class ReferenceDataNetworkClient : KtorNetworkClient<ReferenceDataRequest, ReferenceDataResponse>() {

    override suspend fun sendRequestByType(request: ReferenceDataRequest): HttpResponse {
        return httpClient.get("BuildConfig.BASE_URL") {
            url {
                path(request.path)
            }
        }
    }

    override suspend fun getResponseBodyByRequestType(
        requestType: ReferenceDataRequest,
        httpResponse: HttpResponse
    ): ReferenceDataResponse {
        return when (requestType) {
            is ReferenceDataRequest.CountriesRequest -> {
                httpResponse.body<ReferenceDataResponse.CountriesResponse>()
            }

            is ReferenceDataRequest.CurrencyRequest -> {
                httpResponse.body<ReferenceDataResponse.CurrencyResponse>()
            }

            is ReferenceDataRequest.FaqRequest -> {
                httpResponse.body<ReferenceDataResponse.FaqResponse>()
            }
        }
    }

}
