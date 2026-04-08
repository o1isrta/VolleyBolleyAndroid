package cy.volleybolley.referencedata.data.network

sealed interface ReferenceDataRequest {
    class CountriesRequest : ReferenceDataRequest {
        companion object {
            const val PATH = "/countries/"
        }
    }

    class CurrencyRequest : ReferenceDataRequest {
        companion object {
            const val PATH = "/currencies/"
        }
    }

    class FaqRequest : ReferenceDataRequest {
        companion object {
            const val PATH = "/faq/"
        }
    }
}
