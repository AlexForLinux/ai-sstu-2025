package com.example.plantdiseasedetector.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar(
    modifier : Modifier = Modifier,
    onQuery: (String) -> Unit
) {

    var query by remember { mutableStateOf("") }

    Row(
        modifier = modifier
            .background(
                MaterialTheme.colorScheme.surface
            )
            .border(
                1.dp,
                MaterialTheme.colorScheme.primary,
                RoundedCornerShape(12.dp)
            ),
        verticalAlignment = Alignment.CenterVertically
    ){
        BasicTextField(
            value = query,
            onValueChange = { newValue -> query = newValue },
            modifier = Modifier
                .padding(vertical = 4.dp, horizontal = 8.dp)
                .weight(1f)
            ,
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyLarge,
            decorationBox = { innerTextField ->
                if (query.isEmpty()) {
                    Text(
                        text = "Поиск по описанию ...",
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
                innerTextField()
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {  }
            )
        )

        IconButton(
            onClick = {
                onQuery(query)
            },
            modifier = Modifier
                .background(
                    MaterialTheme.colorScheme.primary,
                )
        ) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Поиск",
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.fillMaxSize().padding(4.dp)
            )
        }

        IconButton(
            onClick = {
                onQuery("")
            },
            modifier = Modifier
                .background(
                    MaterialTheme.colorScheme.secondary,
                    RoundedCornerShape(0.dp, 12.dp, 12.dp,0.dp)
                )
        ) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = "Поиск",
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.fillMaxSize().padding(4.dp)
            )
        }
    }
}