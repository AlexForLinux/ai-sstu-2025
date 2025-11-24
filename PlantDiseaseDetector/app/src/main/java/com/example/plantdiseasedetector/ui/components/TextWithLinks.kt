package com.example.plantdiseasedetector.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextWithLinks(
    message: String,
    diseases: Map<Long, String>,
    onDiseaseClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    style: TextStyle,
    textAlign: TextAlign? = null,
) {
    val textMeasurer = rememberTextMeasurer()
    val density = LocalDensity.current

    val inlineContentMap = remember(diseases, style) {
        diseases.map { (id, name) ->

            val textWidth = textMeasurer.measure(
                text = AnnotatedString(name),
                style = style
            ).size.width

            val iconWidth = 32
            val totalWidth = with(density) { (textWidth + iconWidth).toSp() }

            "button_${id}" to InlineTextContent(
                Placeholder(
                    width = totalWidth,
                    height = 20.sp,
                    placeholderVerticalAlign = PlaceholderVerticalAlign.Center
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(
                            MaterialTheme.colorScheme.primary,
                            RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 2.dp)
                        .clickable { onDiseaseClick(id) },

                ) {
                    Text(
                        text = name,
                        style = style,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )

                    Spacer(modifier = Modifier.width(2.dp))

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                    )

                }
            }
        }.toMap()
    }

    val annotatedString = remember (message, diseases) {
        buildAnnotatedString {
            var currentText = message

            diseases.forEach { (id, name) ->
                val startIndex = currentText.indexOf(name)
                if (startIndex != -1) {
                    if (startIndex > 0) {
                        append(currentText.take(startIndex))
                    }
                    appendInlineContent("button_${id}")
                    currentText = currentText.substring(startIndex + name.length)
                }
            }

            if (currentText.isNotEmpty()) {
                append(currentText)
            }
        }
    }

    Text(
        text = annotatedString,
        modifier = modifier,
        style = style,
        inlineContent = inlineContentMap,
        textAlign = textAlign
    )
}