package cy.volleybolley.referencedata.data.network

sealed class ReferenceDataRequest(
    val path: String
) {
    class CountriesRequest() : ReferenceDataRequest("countries")

    class CurrencyRequest() : ReferenceDataRequest("currency")

    class FaqRequest() : ReferenceDataRequest("faq")
}
