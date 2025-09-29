package cy.volleybolley.profile.presentation.ui.screens.changephoto

import cy.volleybolley.core.presentation.base.UiState

data class ChangePhotoScreenState(
    val avatarUrl: String? = null,
    val avatarBytes: ByteArray? = null,
    val buttonEnabled: Boolean = false,
) : UiState {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ChangePhotoScreenState

        if (buttonEnabled != other.buttonEnabled) return false
        if (avatarUrl != other.avatarUrl) return false
        if (!avatarBytes.contentEquals(other.avatarBytes)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = buttonEnabled.hashCode()
        result = 31 * result + (avatarUrl?.hashCode() ?: 0)
        result = 31 * result + (avatarBytes?.contentHashCode() ?: 0)
        return result
    }
}
