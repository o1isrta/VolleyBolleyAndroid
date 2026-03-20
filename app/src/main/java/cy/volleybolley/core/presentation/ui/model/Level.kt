package cy.volleybolley.core.presentation.ui.model

enum class Level(val id: Int, val displayText: String) {
    Light(1, "Light"),
    Medium(2, "Medium"),
    Hard(3, "Hard"),
    Pro(4, "Pro");

    companion object {
        fun getById(id: Int): Level = entries.find { it.id == id } ?: Light
    }
}
