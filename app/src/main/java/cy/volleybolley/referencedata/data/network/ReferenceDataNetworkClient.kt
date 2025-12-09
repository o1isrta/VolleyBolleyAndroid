package cy.volleybolley.referencedata.data.network

import cy.volleybolley.core.data.network.impl.KtorNetworkClient
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse

class ReferenceDataNetworkClient(
    lazyHttpClient: Lazy<HttpClient>
) : KtorNetworkClient<ReferenceDataRequest, ReferenceDataResponse>(lazyHttpClient) {

    override suspend fun sendRequestByType(request: ReferenceDataRequest): HttpResponse {
        return httpClient.get {
            requestConfigure(request.path)
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
                httpResponse.body<ReferenceDataResponse.CurrenciesResponse>()
            }

            is ReferenceDataRequest.FaqRequest -> {
                httpResponse.body<ReferenceDataResponse.FaqResponse>()
            }
        }
    }

}
