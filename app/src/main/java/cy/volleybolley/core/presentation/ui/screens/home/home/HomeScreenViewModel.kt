package cy.volleybolley.core.presentation.ui.screens.home.home

import cy.volleybolley.core.presentation.base.BaseViewModel

class HomeScreenViewModel(

): BaseViewModel<HomeScreenState, HomeScreenEvent, HomeScreenEffect>(
    initialState = HomeScreenState()
) {
    override val tag: String = HomeScreenViewModel::class.simpleName ?: "HomeScreenViewModel"

    override fun obtainEvent(event: HomeScreenEvent) {

    }
}
