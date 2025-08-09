package cy.volleybolley.core.presentation.ui.model

import androidx.compose.ui.graphics.Color

object VolleyColor {
    val YELLOW = Color(CustomColorValues.YELLOW)
    val BLACK = Color(CustomColorValues.BLACK)
    val WHITE = Color(CustomColorValues.WHITE)
    val YELLOW_GRADIENT = Color(CustomColorValues.YELLOW_GRADIENT)
    val GREEN_GRADIENT = Color(CustomColorValues.GREEN_GRADIENT)
    val SEAWAVE_BACKGROUND = Color(CustomColorValues.SEAWAVE_BACKGROUND)
    val SEAWAVE_HEADER = Color(CustomColorValues.SEAWAVE_HEADER)
    val SEAWAVE_FOOTER = Color(CustomColorValues.SEAWAVE_FOOTER)
    val TEXT_FIELD = Color(CustomColorValues.TEXT_FIELD)
    val TEXT_DARK = Color(CustomColorValues.TEXT_DARK)
    val TEXT_CALENDAR_DARK = Color(CustomColorValues.TEXT_CALENDAR_DARK)
    val TEXT_CALENDAR_LIGHT_GREY = Color(CustomColorValues.TEXT_CALENDAR_LIGHT_GREY)
    val ALERT = Color(CustomColorValues.ALERT)
}

private object CustomColorValues {
    const val YELLOW = 0xFFE6C953
    const val BLACK = 0xFF000000
    const val WHITE = 0xFFFFFFFF
    const val YELLOW_GRADIENT = 0xFFF4E998
    const val GREEN_GRADIENT = 0xFF5CF08D
    const val SEAWAVE_BACKGROUND = 0xFF32716B
    const val SEAWAVE_HEADER = 0xFF53A8A1
    const val SEAWAVE_FOOTER = 0xFF295E6D
    const val TEXT_FIELD = 0xFF7C7C7C
    const val TEXT_DARK = 0xFF423F39
    const val TEXT_CALENDAR_DARK = 0xFF333333
    const val TEXT_CALENDAR_LIGHT_GREY = 0xFFE0E0E0
    const val ALERT = 0xFFE33222
    /**
     * оранжевый (стрелки, звездочки, кнопка HARD)
     */
    val OrangeHard = Color(color = 0xFFE6AC53)

    /**
     * желтый (кнопки, в том числе кнопка PRO)
     */
    val YellowPro = Color(color = 0xFFE6C953)

    /**
     * желтый для градиента на кнопках
     */
    val YellowForGradient = Color(color = 0xFFF4E998)

    /**
     * зеленый для градиента на кнопках
     */
    val GreenForGradient = Color(color = 0xFF5CF08D)

    /**
     * бирюзовый (кнопка MEDIUM)
     */
    val TurquoiseMedium = Color(color = 0xFF2DB69A)

    /**
     * бирюзовый (светлый фон, хедер)
     */
    val Turquoise = Color(color = 0xFF53A8A1)

    /**
     * темный бирюзовый (темный фон)
     */
    val TurquoiseDark = Color(color = 0xFF32716B)

    /**
     * самый темный бирюзовый (для BottomNavigationBar)
     */
    val TurquoiseBottom = Color(color = 0xFF295E6D)

    /**
     * голубой (кнопка LIGHT)
     */
    val BlueLight = Color(color = 0xFF53A4E6)

    /**
     * темный серый
     */
    val GreyDark = Color(color = 0xFF516372)

    /**
     * серый (для неактивных кнопок)
     */
    val GreyDisabled = Color(color = 0xFF8CA5A3)

    /**
     * белый
     */
    val White = Color(color = 0xFFFFFFFF)

    /**
     * чёрный
     */
    val Black = Color(color = 0xFF000000)

    /**
     * текст в полях ввода
     */
    val TextField = Color(color = 0xFF7C7C7C)

    /**
     * цвет темного текста
     */
    val TextDark = Color(color = 0xFF423F39)

    /**
     * цвет текста и компонентов при ошибках, инвалидации и тд.
     */
    val Alert = Color(color = 0xFFE33222)

    val TextCalendarDark = Color(color = 0xFF333333)
    val TextCalendarLightGrey = Color(color = 0xFFE0E0E0)
}
