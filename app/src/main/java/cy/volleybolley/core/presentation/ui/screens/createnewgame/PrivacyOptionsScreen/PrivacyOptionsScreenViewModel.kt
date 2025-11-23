package cy.volleybolley.core.presentation.ui.screens.createnewgame.PrivacyOptionsScreen

import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.screens.createnewgame.CreateNewGameRepository.CreateNewGameRepository
import cy.volleybolley.core.presentation.ui.screens.createnewgame.CreateNewGameRepository.FakeCreateNewGameRepository
import cy.volleybolley.core.presentation.ui.screens.createnewgame.CreateNewGameRepository.GameData
import cy.volleybolley.core.presentation.ui.screens.createnewgame.GameEnteringConditionsScreen.GameEnteringConditionsScreenViewModel
import kotlinx.coroutines.flow.MutableStateFlow

open class PrivacyOptionsScreenViewModel(private val gameRepository: CreateNewGameRepository ) : BaseViewModel<PrivacyOptionsScreenState, PrivacyOptionsScreenEvent, PrivacyOptionsScreenEffect>(
    PrivacyOptionsScreenState()
) {
    override val tag: String = "PrivacyOptionsScreenViewModel"

    override fun obtainEvent(event: PrivacyOptionsScreenEvent) {
        when (event) {
            is PrivacyOptionsScreenEvent.OnBackClicked -> {
                sendUiEffect(PrivacyOptionsScreenEffect.NavigateBack)
            }
            is PrivacyOptionsScreenEvent.OnAddSelectedClick -> {
                //CreateNewGameRepository.AddSelected(uiState.value.players)
                sendUiEffect(PrivacyOptionsScreenEffect.NavigateBack)
            }
            is PrivacyOptionsScreenEvent.OnPlayerClick -> {
            // ограничиваем длину в ViewModel — можно бы убрать ограничение в MessageField
            //val limited = getLimitedText(MAX_LENGTH, event.text)
           // uiStateMutable.value = uiStateMutable.value.copy(message = limited)
            }
            is PrivacyOptionsScreenEvent.SearchTextChanged -> {

            }
            is PrivacyOptionsScreenEvent.AllOrFavoritesSelected -> {

            }
        }
    }
}
// Специальный ViewModel для Preview
class PrivacyOptionsScreenViewModelPreview : PrivacyOptionsScreenViewModel( FakeCreateNewGameRepository(
    MutableStateFlow(
        GameData()
    )
) ) {
}
