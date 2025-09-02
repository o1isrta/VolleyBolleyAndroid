package cy.volleybolley.core.presentation.ui.screens.profile.players

import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.screens.profile.players.model.BackPlayerHolder
import cy.volleybolley.core.presentation.ui.screens.profile.players.model.PlayerTemp
import kotlinx.serialization.json.Json

class PlayersScreenViewModel(
    private val backPlayerHolder: BackPlayerHolder,
    private val json: Json,
) : BaseViewModel<PlayersScreenState, PlayersScreenEvent, PlayersScreenEffect> (
    initialState = PlayersScreenState()
) {
    val mockPlayers: List<PlayerTemp> = listOf(
        PlayerTemp(id = 1, firstName = "Иван", lastName = "Иванов", avatarUrl = null, isFavorite = true, level = "LIGHT"),
        PlayerTemp(id = 2, firstName = "Анна", lastName = "Петрова", avatarUrl = null, isFavorite = false, level = "MEDIUM"),
        PlayerTemp(id = 3, firstName = "Сергей", lastName = "Смирнов", avatarUrl = null, isFavorite = true, level = "HARD"),
        PlayerTemp(id = 4, firstName = "Елена", lastName = "Васильева", avatarUrl = null, isFavorite = false, level = "PRO"),
        PlayerTemp(id = 5, firstName = "Дмитрий", lastName = "Попов", avatarUrl = null, isFavorite = false, level = "LIGHT"),
        PlayerTemp(id = 6, firstName = "Ольга", lastName = "Кузнецова", avatarUrl = null, isFavorite = true, level = "MEDIUM"),
        PlayerTemp(id = 7, firstName = "Алексей", lastName = "Новиков", avatarUrl = null, isFavorite = false, level = "HARD"),
        PlayerTemp(id = 8, firstName = "Марина", lastName = "Левина", avatarUrl = null, isFavorite = false, level = "PRO"),
        PlayerTemp(id = 9, firstName = "Максим", lastName = "Семенов", avatarUrl = null, isFavorite = false, level = "LIGHT"),
        PlayerTemp(id = 10, firstName = "Светлана", lastName = "Орлова", avatarUrl = null, isFavorite = false, level = "MEDIUM"),
    )

    override val tag = TAG

    override fun obtainEvent(event: PlayersScreenEvent) {
        when(event) {
            PlayersScreenEvent.ClickOnBackFromPlayers -> {}

            is PlayersScreenEvent.SearchTextChanged -> {}

            is PlayersScreenEvent.ClickOnSearchButton -> {}

            PlayersScreenEvent.ClickOnAllPlayers -> {}

            PlayersScreenEvent.ClickOnFavoritePlayers -> {}

            is PlayersScreenEvent.ClickOnListItem -> {}
        }

    }

    companion object {
        val TAG = PlayersScreenViewModel::class.simpleName ?: "PlayersScreenViewModel"
    }
}
