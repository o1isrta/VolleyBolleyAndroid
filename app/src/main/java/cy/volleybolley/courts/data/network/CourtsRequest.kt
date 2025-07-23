package cy.volleybolley.courts.data.network

sealed interface CourtsRequest {
    class GetCourtsRequest (
        val path: String = "/courts",
        val courtName: String,
    ): CourtsRequest
}