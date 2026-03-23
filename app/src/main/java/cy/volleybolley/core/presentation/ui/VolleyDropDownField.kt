package cy.volleybolley.core.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyText

object VolleyDropDownField {
    @Composable
    fun DropDownGradientField(
        paddingValues: PaddingValues = PaddingValues(0.dp),
        cornerRadius: Int = 16,
        contentPaddingValues: PaddingValues = PaddingValues(16.dp),
        inputText: String,
        valuesList: List<String>,
        onValueClick: (String) -> Unit,
    ) {
        var showDropDown by remember { mutableStateOf(false) }

        Box(modifier = Modifier.padding(paddingValues)) {
            Column {
                GradientBox(
                    cornerRadius = cornerRadius,
                    contentPadding = contentPaddingValues,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        VolleyText.BodySmall(
                            text = inputText,
                            textAlign = TextAlign.Start,
                            color = VolleyColor.TextField,
                            maxLines = 1,
                            modifier = Modifier.weight(1f)
                        )

                        Spacer(Modifier.width(8.dp))

                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(18.dp)
                                .clip(CircleShape)
                                .clickable(
                                    interactionSource = null,
                                    indication = null,
                                    onClick = { showDropDown = !showDropDown }
                                )
                        ) {
                            Icon(
                                painter = painterResource(
                                    if (showDropDown) R.drawable.ic_arrow_up_small else R.drawable.ic_arrow_down_small
                                ),
                                contentDescription = null,
                                tint = VolleyColor.TextDark
                            )
                        }

                    }
                }

                if (showDropDown && valuesList.isNotEmpty()) {
                    Spacer(Modifier.height(6.dp))
                    GradientBox(
                        cornerRadius = cornerRadius,
                        contentPadding = contentPaddingValues,
                    ) {
                        DropDownList(
                            valuesList = valuesList,
                            onValueClick = { value ->
                                onValueClick(value)
                                showDropDown = false
                            }
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun DropDownList(
        valuesList: List<String>,
        onValueClick: (String) -> Unit,
    ) {
        LazyColumn(
            modifier = Modifier
                .heightIn(0.dp, 160.dp)
        ) {
            itemsIndexed(valuesList) { index, value ->
                VolleyText.BodySmall(
                    text = value,
                    textAlign = TextAlign.Start,
                    color = VolleyColor.TextField,
                    maxLines = 1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            interactionSource = null,
                            indication = null,
                            onClick = { onValueClick(value) }
                        )
                )

                if (index < valuesList.size - 1) {
                    DropDownDivider()
                }
            }
        }
    }

    @Composable
    private fun GradientBox(
        cornerRadius: Int = 16,
        contentPadding: PaddingValues = PaddingValues(16.dp),
        content: @Composable BoxScope.() -> Unit
    ) {
        val gradientBrush = remember {
            Brush.verticalGradient(
                colors = listOf(
                    VolleyColor.YellowForGradient,
                    VolleyColor.GreenForGradient
                )
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = VolleyColor.White,
                    shape = RoundedCornerShape(cornerRadius.dp)
                )
                .border(
                    width = 1.dp,
                    brush = gradientBrush,
                    shape = RoundedCornerShape(cornerRadius.dp)
                )
                .padding(contentPadding),
            content = content
        )
    }

    @Composable
    private fun DropDownDivider() {
        VolleySimpleComponent.DividerLine(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 16.dp)
        )
    }

}

@Preview
@Composable
private fun PreviewDropDownField() {
    VolleyContainersRootTransparent.Root {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(VolleyColor.TurquoiseDark)
        ) {
            VolleyDropDownField.DropDownGradientField(
                paddingValues = PaddingValues(16.dp),
                inputText = "Some hint on field...",
                valuesList = emptyList()
            ) { }
        }
    }
}
