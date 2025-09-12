package cy.volleybolley.core.presentation.ui.screens.games.archive.viewmodel

import androidx.lifecycle.ViewModel
import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.screens.games.archive.effect.ArchiveScreenEffect
import cy.volleybolley.core.presentation.ui.screens.games.archive.event.ArchiveScreenEvent
import cy.volleybolley.core.presentation.ui.screens.games.archive.model.ArchiveScreenState

class ArchiveScreenViewModel(
    override val tag: String,
    initialState: ArchiveScreenState
) : BaseViewModel<ArchiveScreenState, ArchiveScreenEvent, ArchiveScreenEffect>(initialState) {

    override fun obtainEvent(event: ArchiveScreenEvent) {
        TODO("Not yet implemented")
    }
}
