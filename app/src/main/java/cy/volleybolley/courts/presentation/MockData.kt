package cy.volleybolley.courts.presentation

import cy.volleybolley.courts.domain.model.Contact
import cy.volleybolley.courts.domain.model.Court
import cy.volleybolley.courts.domain.model.Location

object MockData {
    val sampleCourts = listOf(
        Court(
            courtId = 1,
            price = "800",
            description = "An outdoor volleyball court close to the center with artificial turf, lighting, shower and locker room.",
            contacts = listOf(Contact("phone", "+66-2-2134567")),
            photo = "",
            tags = listOf("Outdoor", "Lights", "Turf"),
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
            description = "Luxury resort with professional volleyball facilities and beach view.",
            contacts = listOf(Contact("phone", "+66-2-2134568")),
            photo = "",
            tags = listOf("Indoor", "Rental"),
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
            description = "Premium beachfront volleyball court with professional equipment.",
            contacts = listOf(Contact("phone", "+66-2-2134569")),
            photo = "",
            tags = listOf("Outdoor", "Lights", "Turf"),
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
            description = "Exclusive resort court with ocean view and professional maintenance.",
            contacts = listOf(Contact("phone", "+66-2-2134570")),
            photo = "",
            tags = listOf("Indoor", "Rental"),
            location = Location(
                longitude = 98.4223,
                latitude = 7.9104,
                courtName = "The Shore at Katathani Resort",
                locationName = "16 Kata Rd, Karon"
            )
        ), Court(
            courtId = 5,
            price = "700",
            description = "Cozy ground‑level court with lighting, near local amenities and easy access.",
            contacts = listOf(Contact("phone", "+66‑2‑2134571")),
            photo = "",
            tags = listOf("Outdoor", "Evening lights"),
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
            description = "High‑end indoor/outdoor facility, air‑conditioned lounge, near beach view sunset court.",
            contacts = listOf(Contact("phone", "+66‑2‑2134572")),
            photo = "",
            tags = listOf("Indoor/Outdoor", "Sunset view", "Premium"),
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
            description = "Standard beach court with nets provided, shower & locker available, great for mid‑level players.",
            contacts = listOf(Contact("phone", "+66‑2‑2134573")),
            photo = "",
            tags = listOf("Beach", "Standard"),
            location = Location(
                longitude = 98.4400,
                latitude = 7.9400,
                courtName = "BeachSide Volley Karon",
                locationName = "Karon Beachfront, Mueang"
            )
        )
    )
}
