package cy.volleybolley.courts.data.network

sealed interface CourtsRequest {
    class GetCourts(
        val path: String = "/courts",
        val courtName: String,
    ) : CourtsRequest
}
