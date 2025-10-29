package cy.volleybolley.core.presentation.ui.screens.courts

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import cy.volleybolley.R
import cy.volleybolley.courts.domain.model.Court
import cy.volleybolley.courts.domain.model.Location

fun Location.toLatLng() = LatLng(latitude, longitude)

fun Court.getMarkerIcon(context: Context, selectedCourt: Court?): BitmapDescriptor {
    return if (this.courtId == selectedCourt?.courtId) {
        context.drawableToBitmapDescriptor(R.drawable.ic_pin_map)
    } else {
        BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
    }
}

private fun Context.drawableToBitmapDescriptor(@DrawableRes drawableRes: Int): BitmapDescriptor {
    val drawable = ContextCompat.getDrawable(this, drawableRes)!!
    val bitmap = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}
