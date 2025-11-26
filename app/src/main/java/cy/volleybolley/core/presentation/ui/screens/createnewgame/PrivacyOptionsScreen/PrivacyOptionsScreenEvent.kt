package cy.volleybolley.core.presentation.ui.screens.createnewgame.PrivacyOptionsScreen

import cy.volleybolley.core.presentation.base.UiEvent
import cy.volleybolley.players.domain.model.Player

sealed class PrivacyOptionsScreenEvent  : UiEvent {
    object OnBackClicked : PrivacyOptionsScreenEvent()     // нажатие на стрелку "Назад"
    data class OnQueryChanged(val text: String) : PrivacyOptionsScreenEvent() // изменение текста в поисковом поле
    object OnAddSelectedClick  : PrivacyOptionsScreenEvent()     // нажатие на кнопку "ADD SELECTED"
    data class AllOrFavoritesSelected(val isFavorites: Boolean) : PrivacyOptionsScreenEvent()
    data class OnPlayerSelectionChange(val player: Player, val isSelected: Boolean) : PrivacyOptionsScreenEvent()     // нажатие на кнопку в строке игрока (выбор или удаление игрока из команды)
    object LoadPlayers : PrivacyOptionsScreenEvent()
}
