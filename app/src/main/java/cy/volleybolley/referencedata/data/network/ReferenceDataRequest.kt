package cy.volleybolley.referencedata.data.network

sealed class ReferenceDataRequest(
    val path: String
) {
    class CountriesRequest() : ReferenceDataRequest("countries")

    class CurrencyRequest() : ReferenceDataRequest("currencies")

    class FaqRequest() : ReferenceDataRequest("faq")
}
