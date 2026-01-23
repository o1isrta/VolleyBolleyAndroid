package cy.volleybolley.courts.presentation.model

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import cy.volleybolley.courts.domain.model.Court
import java.util.Locale

object CourtUiMapper {
    private const val METERS_IN_KILOMETER = 1000f
    private const val DISTANCE_FORMAT = "%.1f km"
    private const val DISTANCE_UNKNOWN = "-- km"

    fun Court.toCourtUI(userLocation: LatLng? = null): CourtUi {
        val distanceText = userLocation?.let { userLoc ->
            val results = FloatArray(1)
            Location.distanceBetween(
                userLoc.latitude, userLoc.longitude,
                this.location.latitude, this.location.longitude,
                results
            )
            String.format(Locale.US, DISTANCE_FORMAT, results[0] / METERS_IN_KILOMETER)
        } ?: DISTANCE_UNKNOWN

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
            String.format(Locale.US, DISTANCE_FORMAT, results[0] / METERS_IN_KILOMETER)
        } ?: DISTANCE_UNKNOWN

        return this.copy(distanceText = newDistance)
    }
}
