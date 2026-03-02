package cy.volleybolley.core.presentation.ui.screens.games.archive.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.net.toUri
import cy.volleybolley.R
import cy.volleybolley.courts.domain.model.Location

fun Context.openMap(location: Location) {
    val uri = "geo:${location.latitude},${location.longitude}?q=${location.latitude},${location.longitude}(${
        Uri.encode(location.courtName)
    })".toUri()
    val intent = Intent(Intent.ACTION_VIEW, uri)
    val chooser = Intent.createChooser(intent, this.getString(R.string.open_with))
    this.startActivity(chooser)
}
