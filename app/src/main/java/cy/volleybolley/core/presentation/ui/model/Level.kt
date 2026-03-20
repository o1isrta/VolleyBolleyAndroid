package cy.volleybolley.core.presentation.ui.model

enum class Level(val id: Int, val displayText: String) {
    Light(id = 1, displayText = "Light"),
    Medium(id = 2, displayText = "Medium"),
    Hard(id = 3, displayText = "Hard"),
    Pro(id = 4, displayText = "Pro");

    companion object {
        fun getById(id: Int): Level = entries.find { it.id == id } ?: Light
    }
}
