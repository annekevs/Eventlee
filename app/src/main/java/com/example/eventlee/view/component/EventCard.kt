package com.example.eventlee.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.eventlee.model.DateTime
import com.example.eventlee.model.Event
import java.time.ZoneId

@Composable
fun EventCard(
    event: Event,
    onClick: () -> Unit
){
    val paddingModifier = Modifier.padding(16.dp)

    Column(
        modifier = paddingModifier
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardColors(
                containerColor = Color.White,
                contentColor = Color.Black,
                disabledContainerColor = Color.White,
                disabledContentColor = Color.Black,
            )
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .height(150.dp)
                        .fillMaxWidth()
                ){
                    AsyncImage(
                        model = event.imageUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(10.dp)),
                        //.blur(1.dp),
                        contentScale = ContentScale.Crop
                    )
                    Column (
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ){
                        Row (
                            modifier = paddingModifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                        ){
                            Icon(
                                imageVector = Icons.Rounded.Star,
                                contentDescription = null,
                                modifier = Modifier
                                    .background(color = Color.Yellow, shape = CircleShape)
                                    .size(24.dp)

                            )
                            Box(modifier = Modifier
                                .padding(2.dp)
                                .background(color = Color.White, shape = RoundedCornerShape(20.dp))
                                .height(24.dp),
                                //.clip(RoundedCornerShape(60.dp)),
                                contentAlignment = Alignment.CenterStart
                            ){
                                Text(
                                    modifier = Modifier.padding(horizontal = 8.dp),
                                    text = DateTime.getFormattedDate(
                                        date = event.startOn.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                                        outputFormat = "dd MMM yyyy"
                                    ),
                                    fontSize = 10.sp,
                                )
                            }
                        }

                        Text(
                            modifier = paddingModifier,
                            //.background(),
                            text = event.title,
                            color = Color.White,
                            fontSize = 16.sp,
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Bold
                        )
                    }

                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 5.dp),
                    contentAlignment = Alignment.CenterEnd,
                ){
                    Button(
                        colors = ButtonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = Color.Black,
                            disabledContainerColor = Color.LightGray,
                            disabledContentColor = Color.Black,
                        ),
                        onClick = onClick
                    ) {
                        Text(
                            text = "Wants join"
                        )
                    }
                }
            }


        }
    }
}

@Composable
fun EventCardList(
    selectedEventGroup: Int?,
    onSelect: (eventId: String) -> Unit,
    eventsByDate: Map<Int, List<Event>>?
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())

    ) {
        eventsByDate?.get(selectedEventGroup)?.forEach{ event ->
            EventCard(event = event, onClick= { onSelect(event.id) })
        }
    }
}