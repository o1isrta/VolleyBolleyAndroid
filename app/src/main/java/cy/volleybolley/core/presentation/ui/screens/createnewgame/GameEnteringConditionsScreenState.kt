package cy.volleybolley.core.presentation.ui.screens.createnewgame

sealed class GameEnteringConditionsScreenState {
    data class Content(
        val maximumPlayers: Int = 4,
        val selectedPrivacy: Privacy = Privacy.Public,
        val accountState: AccountState = AccountState.NotLinked
    ) : GameEnteringConditionsScreenState()
}

sealed class Privacy {
    object Public : Privacy()
    object Private : Privacy()
}

sealed class AccountState {
    object NotLinked : AccountState()
    object Loading : AccountState()
    data class Linked(val accountNumber: String) : AccountState()
    //data class Error(val message: String) : AccountState() // Optional: Add an Error state
}

