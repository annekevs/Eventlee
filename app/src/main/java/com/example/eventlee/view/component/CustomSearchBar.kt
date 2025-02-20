package com.example.eventlee.view.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSearchBar(
    searchText: String,
    active: Boolean,
    placeholder:  @Composable (() -> Unit)? = null,
    onQueryChange: (text: String) -> Unit,
    onSearchText: (text: String) -> Unit,
    onActiveChange: (active: Boolean) -> Unit,
    onClear: () -> Unit,
    items: List<String>
){
    SearchBar(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .padding(vertical = 10.dp)
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        query = searchText,
        onQueryChange = onQueryChange,
        onSearch = onSearchText,
        active = active,
        onActiveChange = onActiveChange,
        placeholder = placeholder,
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        trailingIcon = {
            if(active) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = null,
                    modifier = Modifier.clickable { onClear() }
                )
            }
        },
        colors = SearchBarDefaults.colors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),


        ) {
        LazyColumn {
            items(items) {
                Text(
                    text = it,
                    fontSize = 15.sp,
                    modifier = Modifier
                        .padding(
                            start = 8.dp,
                            top = 4.dp,
                            end = 8.dp,
                            bottom = 4.dp
                        )
                        .clickable {
                            onQueryChange(it)
                            onActiveChange(false)
                        }
                )
            }

        }
    }
}


val countries = listOf(
    "Afghanistan",
    "Albania",
    "Algeria",
    "Andorra",
    "Angola",
    "Antigua and Barbuda",
    "Argentina",
    "Armenia",
    "Australia",
    "Austria",
    "Azerbaijan",
    "Bahamas",
    "Bahrain",
    "Bangladesh")
