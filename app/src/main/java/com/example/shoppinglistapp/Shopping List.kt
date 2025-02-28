package com.example.shoppinglistapp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

data class ShoppingItem(
    val id: Int,
    var name: String,
    var quantity: Int,
    var isEditing: Boolean = false
) {

}

@Composable
fun ShoppingListApp() {
    //state variable called sItems, which holds a list of ShoppingItem objects.
    var sItems by remember {
        mutableStateOf(listOf<ShoppingItem>())
    }
    //state variable to show whether the alert dialog box is shown on screen
    var showDialog by remember {
        mutableStateOf(false)
    }
    var itemName by remember {
        mutableStateOf("")
    }
    var itemQuantity by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            //upon clicking, the dialog alert will appear on screen
            onClick = { showDialog = true },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Add Item")

        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(sItems) {
                ShoppingListItem(it, {}, {})
            }
        }
    }
    if (showDialog) {
        AlertDialog(onDismissRequest = { showDialog = false }, confirmButton = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween /*This puts as much space as possible between the add and the cancel buttons*/
            ) {
                Button(onClick = {
                    if (itemName.isNotBlank()) {
                        val newItem = ShoppingItem(
                            id = sItems.size + 1,
                            name = itemName,
                            quantity = itemQuantity.toInt()//convert what we enter to int
                        )
                        sItems = sItems + newItem
                        showDialog = false
                        itemName =
                            "" //reset the item name to an empty string to avoid manually deleting every time we enter a new shopping item
                        itemQuantity = ""
                    }
                }) {
                    Text(text = "Add")
                }
                Button(onClick = {
                    showDialog = false
                }) { //This button deactivates the alert dialog
                    Text(text = "Cancel")
                }
            }
        },
            title = { Text(text = "Add Shopping Item") },
            text = {
                Column {
                    OutlinedTextField(
                        value = itemName,
                        onValueChange = { itemName = it },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(6.dp),
                        label = { Text(text = "Enter Item") }
                    )
                    OutlinedTextField(
                        value = itemQuantity,
                        onValueChange = { itemQuantity = it },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(6.dp),
                        label = { Text(text = "Enter Quantity") }
                    )
                }
            }
        )
    }
}

@Composable
fun ShoppingListItem(
    item: ShoppingItem,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .border(
                border = BorderStroke(3.dp, Color(206, 20, 255)),
                shape = RoundedCornerShape(20)
            )
    ) {
        Text(text = item.name, modifier = Modifier.padding(8.dp))
    }
}
/*TODO: Also display the quantity*/