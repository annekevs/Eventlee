package com.example.eventlee.view.component

import com.example.eventlee.model.DateTime
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eventlee.model.Event
import java.time.LocalDate
import java.time.ZoneId

@Composable
fun FilterTag(
    modifier: Modifier = Modifier,
    items: Map<Int, List<Event>>?,
    selectedItem: Int?,
    onSelectItem: (id: Int) -> Unit,
) {
    val paddingModifier = Modifier.padding(5.dp)

    Row(
        modifier = Modifier.fillMaxWidth().padding(top = 5.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        items?.forEach { (t, events) ->
            var eventDate = ""

            when{
                events[0].startOn.toInstant().atZone(ZoneId.systemDefault()).dayOfYear == LocalDate.now().dayOfYear ->
                    eventDate = "Today"
                events[0].startOn.toInstant().atZone(ZoneId.systemDefault()).dayOfYear == (LocalDate.now().dayOfYear + 1) ->
                    eventDate = "Tomorrow"
                else -> eventDate = DateTime.getFormattedDate(
                    date = events[0].startOn,
                    outputFormat = "dd MMM"
                )
            }

            var text = "$eventDate (${events.size})"
            var isSelected = (t == selectedItem)

            /*
            TagItem(
                text = "$eventDate (${events.size})",
                selected = (t == selectedItem),
                onClick = { onSelectItem( t )}
            )*/

            Card(
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(10.dp),
                //enabled = selected, --no update
                colors = CardColors(
                    containerColor = if(isSelected) MaterialTheme.colorScheme.primary else Color.LightGray,
                    contentColor = if(isSelected) Color.White  else Color.Black,
                    disabledContainerColor = Color.LightGray,
                    disabledContentColor = Color.Black,
                ),
                modifier = paddingModifier
                    .height(25.dp)
                    .weight(1f),
                onClick = { onSelectItem( t )},

                ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = text,
                        fontSize = 11.sp,
                        fontWeight = if(isSelected) FontWeight.Bold else FontWeight.Normal,
                        textAlign = TextAlign.Center,
                        modifier = paddingModifier
                    )
                }
            }
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun FilterTagPreview(){
    var selectedGroup by remember { mutableStateOf(0) } // Selected group

    FilterTag(
        items = eventsByDate,
        selectedItem = selectedGroup,
        onSelectItem = { selectedGroup = it},
        modifier = Modifier.padding(16.dp)
    )
}*/
