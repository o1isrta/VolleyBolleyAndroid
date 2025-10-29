package cy.volleybolley.core.presentation.ui.screens.courts

import cy.volleybolley.courts.domain.model.Contact
import cy.volleybolley.courts.domain.model.Court
import cy.volleybolley.courts.domain.model.Location

object CourtsMockData {
    private const val PHONE = "phone"
    private const val OUTDOOR = "Outdoor"
    private const val LIGHTS = "Lights"
    private const val TURF = "Turf"
    private const val INDOOR = "Indoor"
    private const val RENTAL = "Rental"
    private const val EVENING_LIGHTS = "Evening lights"
    private const val INDOOR_OUTDOOR = "Indoor/Outdoor"
    private const val SUNSET_VIEW = "Sunset view"
    private const val PREMIUM = "Premium"
    private const val BEACH = "Beach"
    private const val STANDARD = "Standard"

    private val DESC_1 = """
        An outdoor volleyball court close to the center with artificial turf,
        lighting, shower and locker room.
    """.trimIndent()

    private val DESC_2 = """
        Luxury resort with professional volleyball facilities and beach view.
    """.trimIndent()

    private val DESC_3 = """
        Premium beachfront volleyball court with professional equipment.
    """.trimIndent()

    private val DESC_4 = """
        Exclusive resort court with ocean view and professional maintenance.
    """.trimIndent()

    private val DESC_5 = """
        Cozy ground‑level court with lighting, near local amenities and easy access.
    """.trimIndent()

    private val DESC_6 = """
        High‑end indoor/outdoor facility, air‑conditioned lounge,
        near beach view sunset court.
    """.trimIndent()

    private val DESC_7 = """
        Standard beach court with nets provided, shower & locker available,
        great for mid‑level players.
    """.trimIndent()

    val sampleCourts = listOf(
        Court(
            courtId = 1,
            price = "800",
            description = DESC_1,
            contacts = listOf(Contact(PHONE, "+66-2-2134567")),
            photo = "",
            tags = listOf(OUTDOOR, LIGHTS, TURF),
            location = Location(
                longitude = 98.3923,
                latitude = 7.8804,
                courtName = "Karon Beach Club",
                locationName = "Patak Rd, Mueang"
            )
        ),
        Court(
            courtId = 2,
            price = "750",
            description = DESC_2,
            contacts = listOf(Contact(PHONE, "+66-2-2134568")),
            photo = "",
            tags = listOf(INDOOR, RENTAL),
            location = Location(
                longitude = 98.4023,
                latitude = 7.8904,
                courtName = "Metadee Resort & Villas",
                locationName = "56 Kata Rd, Karon, Mueang"
            )
        ),
        Court(
            courtId = 3,
            price = "900",
            description = DESC_3,
            contacts = listOf(Contact(PHONE, "+66-2-2134569")),
            photo = "",
            tags = listOf(OUTDOOR, LIGHTS, TURF),
            location = Location(
                longitude = 98.4123,
                latitude = 7.9004,
                courtName = "Pullman Phuket Karon Beach",
                locationName = "333 Patak Rd, Tombion Karon"
            )
        ),
        Court(
            courtId = 4,
            price = "850",
            description = DESC_4,
            contacts = listOf(Contact(PHONE, "+66-2-2134570")),
            photo = "",
            tags = listOf(INDOOR, RENTAL),
            location = Location(
                longitude = 98.4223,
                latitude = 7.9104,
                courtName = "The Shore at Katathani Resort",
                locationName = "16 Kata Rd, Karon"
            )
        ),
        Court(
            courtId = 5,
            price = "700",
            description = DESC_5,
            contacts = listOf(Contact(PHONE, "+66‑2‑2134571")),
            photo = "",
            tags = listOf(OUTDOOR, EVENING_LIGHTS),
            location = Location(
                longitude = 98.4300,
                latitude = 7.9200,
                courtName = "Kata Beach Sports Complex",
                locationName = "34 Kata Rd, Karon"
            )
        ),
        Court(
            courtId = 6,
            price = "950",
            description = DESC_6,
            contacts = listOf(Contact(PHONE, "+66‑2‑2134572")),
            photo = "",
            tags = listOf(INDOOR_OUTDOOR, SUNSET_VIEW, PREMIUM),
            location = Location(
                longitude = 98.4350,
                latitude = 7.9300,
                courtName = "Sunset Court Resort",
                locationName = "8 Kata Noi Beach, Karon"
            )
        ),
        Court(
            courtId = 7,
            price = "820",
            description = DESC_7,
            contacts = listOf(Contact(PHONE, "+66‑2‑2134573")),
            photo = "",
            tags = listOf(BEACH, STANDARD),
            location = Location(
                longitude = 98.4400,
                latitude = 7.9400,
                courtName = "BeachSide Volley Karon",
                locationName = "Karon Beachfront, Mueang"
            )
        )
    )
}
