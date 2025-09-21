package cy.volleybolley.core.presentation.ui.screens.createnewgame

data class GameEnteringConditionsScreenState(
    val maximumPlayers: Int = 4,
    val selectedPrivacy: Privacy? = null,
    val accountState: AccountState = AccountState.NotLinked
)

sealed class Privacy {
    object Public : Privacy()
    object Private : Privacy()
}

sealed class AccountState {
    object NotLinked : AccountState()
    object Loading : AccountState()
    data class Linked(val accountNumber: String) : AccountState()
}

sealed class Event {
   // data class ShowToast(val message: String) : Event()
    data class NavigateToPrivacyOptionsScreen(val accountNumber: String) : Event()
}
