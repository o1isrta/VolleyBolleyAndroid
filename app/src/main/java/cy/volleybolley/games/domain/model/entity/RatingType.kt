package cy.volleybolley.games.domain.model.entity

enum class RatingType(val checkId: Int) {
    UP(RatingIds.UP_LEVEL),
    CONFIRM(RatingIds.CONFIRM_LEVEL),
    DOWN(RatingIds.DOWN_LEVEL)
}

object RatingIds {
    const val UP_LEVEL = 1
    const val CONFIRM_LEVEL = 2
    const val DOWN_LEVEL = 3
}
