package com.example.plantdiseasedetector.ui.components

import android.graphics.drawable.PaintDrawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.plantdiseasedetector.R
import kotlin.getValue

@Composable
fun MarkableIcon(
    modifier : Modifier = Modifier,
    marked: Boolean,
    onMarkChange: (Boolean) -> Unit,
    description: String? = "Закладка",
    stylePicker: StylePicker = StylePicker(
        MarkStyle(
            MaterialTheme.colorScheme.primary,
            R.drawable.filledbookmark,
            MaterialTheme.colorScheme.onPrimary,
            "Отмечено",
            MaterialTheme.colorScheme.onPrimary
        ),
        MarkStyle(
            Color.LightGray,
            R.drawable.crossedfilledbookmark,
            Color.Gray,
            "Не отмечено",
            Color.Gray,
        )
    )
) {
    var isMarked by remember { mutableStateOf(marked) }

    Box(

        modifier = modifier
            .background(
            stylePicker.getMainColor(isMarked),
            RoundedCornerShape(50)
            )
            .clickable(onClick = {
                isMarked = !isMarked
                onMarkChange(isMarked)
            })

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(vertical = 4.dp, horizontal = 8.dp)
        ) {
            Text(
                text = stylePicker.getText(isMarked),
                color = stylePicker.getTextColor(isMarked),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(end = 8.dp)
            )

            Icon(
                painter = painterResource(id = stylePicker.getIconId(isMarked)),
                contentDescription = description,
                tint = stylePicker.getIconColor(isMarked),
                modifier = Modifier
                    .size(16.dp)
            )
        }
    }
}

class MarkStyle (
    val mainColor: Color,
    val iconId : Int,
    val iconColor: Color,
    val text: String,
    val textColor: Color,
)

class StylePicker (val markedStyle: MarkStyle, val notMarkedStyle: MarkStyle){
    fun getMainColor (mark: Boolean) : Color {
        if (mark) return markedStyle.mainColor
        return notMarkedStyle.mainColor
    }

    fun getIconId(mark: Boolean) : Int {
        if (mark) return markedStyle.iconId
        return notMarkedStyle.iconId
    }

    fun getIconColor(mark: Boolean) : Color {
        if (mark) return markedStyle.iconColor
        return notMarkedStyle.iconColor
    }

    fun getText(mark: Boolean) : String {
        if (mark) return markedStyle.text
        return notMarkedStyle.text
    }

    fun getTextColor(mark: Boolean) : Color {
        if (mark) return markedStyle.textColor
        return notMarkedStyle.textColor
    }
}