package com.example.edekaqaapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.random.Random

private data class ShoppingItem(val name: String, val weightGrams: Int)

private val shoppingListNames = listOf(
    "Äpfel", "Bananen", "Birnen", "Trauben", "Orangen",
    "Kartoffeln", "Zwiebeln", "Karotten", "Tomaten", "Gurken",
    "Paprika", "Salat", "Brokkoli", "Zucchini", "Champignons",
    "Vollkornbrot", "Brötchen", "Toastbrot", "Butter", "Margarine",
    "Milch", "Sahne", "Joghurt", "Quark", "Käse",
    "Eier", "Hähnchenbrust", "Hackfleisch", "Wurst", "Lachs",
    "Reis", "Nudeln", "Mehl", "Zucker", "Salz",
    "Olivenöl", "Essig", "Senf", "Ketchup", "Mayonnaise",
    "Kaffee", "Tee", "Orangensaft", "Mineralwasser", "Cola",
    "Schokolade", "Kekse", "Chips", "Müsli", "Honig"
)

private val shoppingListItems = shoppingListNames.map { name ->
    ShoppingItem(name = name, weightGrams = Random.nextInt(50, 3001))
}

private fun formatWeight(grams: Int): String {
    return if (grams >= 1000) {
        val kg = grams / 1000.0
        "%.1f kg".format(kg)
    } else {
        "$grams g"
    }
}

@Composable
fun SecondScreen(onNavigateBack: () -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        items(shoppingListItems.size) { index ->
            var checked by remember { mutableStateOf(false) }
            val item = shoppingListItems[index]

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = checked,
                    onCheckedChange = { checked = it }
                )
                Column {
                    Text(
                        text = item.name,
                        style = MaterialTheme.typography.bodyLarge,
                        textDecoration = if (checked) TextDecoration.LineThrough else TextDecoration.None
                    )
                    Text(
                        text = formatWeight(item.weightGrams),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray,
                        textDecoration = if (checked) TextDecoration.LineThrough else TextDecoration.None
                    )
                }
            }
            HorizontalDivider()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SecondScreenPreview() {
    SecondScreen(onNavigateBack = {})
}



