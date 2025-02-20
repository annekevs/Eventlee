package com.example.eventlee.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.VerticalDivider
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.eventlee.R
import com.example.eventlee.model.DateTime
import com.example.eventlee.model.Event
import com.example.eventlee.view.component.ProfilePicture
import com.example.eventlee.view.component.TopBar
import com.example.eventlee.view.component.defaultSpacing
import java.time.ZoneId

/*
@Composable
fun EventDetailScreen(viewModel: EventDetailViewModel){
    val state = viewModel.uiState.collectAsState()
    var selectedEventId by remember {
        mutableStateOf("")
    }
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = selectedEventId, 
            onValueChange = {selectedEventId = it}, 
            label = { Text(text = "Event ID") }
        )
        Button(onClick = {
            scope.launch { viewModel.onSelectEvent(selectedEventId) }
        }) {
            Text(text = "Search Event")
        }
        state.value.eventData.let {
            if (it != null) {
                EventCard(event = it)
            } else {
                Text(text = "No event with this ID")
            }
        }
    }
}*/

/*
@Composable
fun EventDetailScreen(viewModel: EventViewModel){
    val state = viewModel.uiState.collectAsState()
    var selectedEventId by remember {
        mutableStateOf("")
    }
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = selectedEventId,
            onValueChange = {selectedEventId = it},
            label = { Text(text = "Event ID") }
        )
        Button(onClick = {
            scope.launch { viewModel.onSelectEvent(selectedEventId) }
        }) {
            Text(text = "Search Event")
        }
        state.value.eventData.let {
            EventCard(event = it)
        }
    }
}
*/

@Composable
fun EventDetailScreen(
    navController: NavHostController,
    event: Event,
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopBar(
            canNavigateBack = true,
            onNavigateBack = { navController.navigateUp()}
        ) }
    ) { innerPadding ->
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.surfaceContainerLow
        ) {
            EventDetailView(
                modifier = Modifier.padding(innerPadding),
                event = event
            )
        }
    }
}

@Composable
fun EventDetailView(
    modifier: Modifier = Modifier,
    event: Event
){

    @Composable
    fun DateBox(){
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card() {
                Column(
                    modifier = Modifier
                        .size(40.dp)
                        .padding(5.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(text = DateTime.getFormattedDate(
                        date = event.startOn,
                        outputFormat = "MMM")
                        , fontSize = 10.sp)
                    Text(
                        text = "${event.startOn.toInstant().atZone(ZoneId.systemDefault()).dayOfMonth}",
                        fontSize = 10.sp
                    )
                }
            }
            VerticalDivider(modifier = Modifier
                .fillMaxHeight(0.8F)
                .padding(horizontal = 10.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = DateTime.getFormattedDate(
                    date = event.startOn,
                    outputFormat = "EEEE, MMM yyyy"),
                    fontSize = 12.sp)
                Spacer(Modifier.size(10.dp))
                Text(
                    text = "" +
                        "${event.startOn.toInstant().atZone(ZoneId.systemDefault()).toLocalTime()} - " +
                        "${event.endOn.toInstant().atZone(ZoneId.systemDefault()).toLocalTime()}",
                    fontSize = 10.sp
                )
            }
        }
    }

    @Composable
    fun DirectionBox(){
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.map_direction),
                    contentDescription = "event map",
                    modifier = Modifier
                        .size(128.dp, 70.dp)
                        .clip(RoundedCornerShape(20.dp)),
                    contentScale = ContentScale.Crop,
                )
                Column {
                    Text(text = event.location, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        modifier = Modifier.size(87.dp, 20.dp),
                        //.wrapContentHeight(),
                        contentPadding = PaddingValues(1.dp),
                        shape = RoundedCornerShape(20.dp),
                        onClick = { /*TODO*/ }) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                modifier = Modifier.size(10.dp),
                                imageVector = Icons.Filled.LocationOn,
                                contentDescription = "get direction"
                            )
                            Text(text = "Get Direction", fontSize = 10.sp)
                        }
                    }
                }
            }

        }
    }

    @Composable
    fun DescriptionBox(){
        Column (
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ){
            Card {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(text = "Hosted by")
                    Text(text = event.publisher)
                }
                HorizontalDivider(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 1.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    val maxRuban = 4
                    val attendees: List<String> = List(event.nbAttendees) {""}
                    //Text(text = "People ongoing (${event.attendees?.size})")
                    Text(text = "People ongoing (${event.nbAttendees})")
                    Row(horizontalArrangement = Arrangement.spacedBy(-defaultSpacing)) {
                        attendees.take(maxRuban).forEachIndexed{ index, item ->
                            //if(index > 5) break
                            val enable = (
                                    (index != attendees.size-1) &&
                                            (attendees.size > maxRuban)
                                    )
                            ProfilePicture(
                                user = item,
                                cropped = enable,
                                showMore = (index > maxRuban)
                            )
                        }
                        if(attendees.size > maxRuban){
                            ProfilePicture(
                                user = "",
                                cropped = false,
                                showMore = true
                            )
                        }
                    }
                }
            }
            Text(
                text = "About",
                modifier = Modifier.padding(bottom = 10.dp, top = 5.dp),
                fontWeight = FontWeight.Bold
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ){
                Text(text = event.description)

            }

        }

    }


    Column (
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = event.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(128.dp)
                    .clip(RoundedCornerShape(20.dp)),
                contentScale = ContentScale.Crop,
            )
            Text(
                modifier = Modifier.padding(horizontal = 10.dp),
                text = event.title,
                fontSize = 24.sp,
            )
        }
        DateBox()
        DirectionBox()
        DescriptionBox()
    }
}

/*
@Preview(showBackground = true)
@Composable
fun EventDetailScreenPreview(){
    val eventViewModel = EventViewModel(EventRepository())
    EventleeTheme {
        EventDetailScreen(
            navController = rememberNavController()
            event
        )
    }
}*/