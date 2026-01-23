package cy.volleybolley.courts.presentation.model

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import cy.volleybolley.courts.domain.model.Court

object CourtUiMapper {
    fun Court.toCourtUI(userLocation: LatLng? = null): CourtUi {
        val distanceText = userLocation?.let { userLoc ->
            val results = FloatArray(1)
            Location.distanceBetween(
                userLoc.latitude, userLoc.longitude,
                this.location.latitude, this.location.longitude,
                results
            )
            "${(results[0] / 1000).toInt()} km"
        } ?: "-- km"

        return CourtUi(
            courtId = this.courtId,
            price = this.price,
            description = this.description,
            contacts = this.contacts,
            photo = this.photo,
            tags = this.tags,
            location = this.location,
            distanceText = distanceText
        )
    }

    fun CourtUi.updateDistance(userLocation: LatLng?): CourtUi {
        val newDistance = userLocation?.let { userLoc ->
            val results = FloatArray(1)
            Location.distanceBetween(
                userLoc.latitude, userLoc.longitude,
                this.location.latitude, this.location.longitude,
                results
            )
            "${(results[0] / 1000).toInt()} km"
        } ?: "-- km"

        return this.copy(distanceText = newDistance)
    }
}
