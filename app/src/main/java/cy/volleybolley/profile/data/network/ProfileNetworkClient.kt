package cy.volleybolley.profile.data.network

import cy.volleybolley.BuildConfig
import cy.volleybolley.core.data.network.impl.KtorNetworkClient
import cy.volleybolley.profile.data.dto.AvatarDto
import cy.volleybolley.profile.data.dto.PersonalDataDto
import cy.volleybolley.profile.data.network.model.ProfileRequest
import cy.volleybolley.profile.data.network.model.ProfileResponse
import io.ktor.client.call.body
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpMethod

class ProfileNetworkClient : KtorNetworkClient<ProfileRequest, ProfileResponse>() {
    override suspend fun sendRequestByType(request: ProfileRequest): HttpResponse {
        return httpClient.request(urlString = BuildConfig.BASE_URL) {
            when (request) {
                is ProfileRequest.GetPersonalData -> {
                    method = HttpMethod.Get
                    requestConfigure(request.path, request.accessToken)
                }

                is ProfileRequest.GetPayments -> {
                    method = HttpMethod.Get
                    requestConfigure(request.path, request.accessToken)
                }

                is ProfileRequest.UpdatePersonalData -> {
                    method = HttpMethod.Patch
                    requestConfigure(request.path, request.accessToken, request.body)
                }

                is ProfileRequest.UpdatePayments -> {
                    method = HttpMethod.Put
                    requestConfigure(request.path, request.accessToken, request.body)
                }

                is ProfileRequest.UpdateProfileAvatar -> {
                    method = HttpMethod.Put
                    requestConfigure(request.path, request.accessToken, request.body)
                }

                is ProfileRequest.DeleteProfile -> {
                    method = HttpMethod.Delete
                    requestConfigure(request.path, request.accessToken)
                }
            }
        }
    }

    override suspend fun getResponseBodyByRequestType(
        requestType: ProfileRequest,
        httpResponse: HttpResponse
    ): ProfileResponse {
        return when (requestType) {
            is ProfileRequest.GetPersonalData -> {
                val personalData = httpResponse.body<PersonalDataDto>()
                ProfileResponse.GetPersonalData(personalData)
            }

            is ProfileRequest.GetPayments -> {
                val payments = httpResponse.body<ProfileResponse.GetPayments>()
                payments
            }

            is ProfileRequest.UpdatePersonalData -> ProfileResponse.UpdatePersonalData

            is ProfileRequest.UpdatePayments -> ProfileResponse.UpdatePayments

            is ProfileRequest.UpdateProfileAvatar -> {
                val avatar = httpResponse.body<AvatarDto>()
                ProfileResponse.UpdateProfileAvatar(avatar)
            }

            is ProfileRequest.DeleteProfile -> ProfileResponse.DeleteProfile
        }
    }
}
