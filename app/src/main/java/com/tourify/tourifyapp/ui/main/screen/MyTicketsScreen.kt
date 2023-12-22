package com.tourify.tourifyapp.ui.main.screen

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.tourify.tourifyapp.R
import com.tourify.tourifyapp.data.factory.ViewModelDestinationsFactory
import com.tourify.tourifyapp.data.factory.ViewModelMyTicketsFactory
import com.tourify.tourifyapp.data.viewmodels.DestinationsViewModel
import com.tourify.tourifyapp.data.viewmodels.MyTicketsViewModel
import com.tourify.tourifyapp.di.Injection
import com.tourify.tourifyapp.model.DataDestinationsResponse
import com.tourify.tourifyapp.model.DestinationsResponse
import com.tourify.tourifyapp.model.MyTicketsResponse
import com.tourify.tourifyapp.route.Routes
import com.tourify.tourifyapp.ui.common.UiState
import com.tourify.tourifyapp.ui.component.BoxLoading
import com.tourify.tourifyapp.ui.component.CardMyTickets
import com.tourify.tourifyapp.ui.component.CardWisataMaxWidth
import com.tourify.tourifyapp.ui.component.ModalBottomSheetDetailTickets
import com.tourify.tourifyapp.ui.component.ModalBottomSheetDetailWisata
import com.tourify.tourifyapp.ui.component.TopBarSearch
import com.tourify.tourifyapp.ui.theme.ColorTransparent
import com.tourify.tourifyapp.ui.theme.ColorWhite
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTicketsScreen(
    context: Context,
    navController: NavController,
    paddingValues: PaddingValues,
    userId: Int? = 0,
    myTicketsViewModel: MyTicketsViewModel = viewModel(
        factory = ViewModelMyTicketsFactory(
            Injection.provideMyTicketsRepository()
        )
    ),
    destinationsViewModel: DestinationsViewModel = viewModel(navController.getBackStackEntry(route = Routes.Home.routes))
) {
    var keywords by rememberSaveable { mutableStateOf("") }
    val destinationsResult = destinationsViewModel.uiState.collectAsState().value
    val myTicketsResult by myTicketsViewModel.uiState.collectAsState(initial = UiState.Loading)
    LaunchedEffect(myTicketsViewModel) {
        if (myTicketsResult is UiState.Loading) {
            myTicketsViewModel.getMyTickets(userId ?: 0)
        }
    }
    val modalDetailTickets = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var idDetailTickets by rememberSaveable { mutableIntStateOf(0) }
    var showDetailTickets by rememberSaveable { mutableStateOf(false) }
    Scaffold(
        containerColor = ColorWhite,
        topBar = {
            TopBarSearch(
                context = context,
                placeholder = R.string.search,
                value = keywords,
                onKeywords = {
                    keywords = it
                }
            )
        }
    ) { paddingTopValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingTopValues.calculateTopPadding()),
            content = {
                when(myTicketsResult) {
                    is UiState.Loading -> {

                    }
                    is UiState.Success -> {
                        val daftarDestinations = (destinationsResult as UiState.Success<DestinationsResponse>).data.data
                        val daftarMyTickets = (myTicketsResult as UiState.Success<MyTicketsResponse>).data.data
                        if (daftarMyTickets!!.isEmpty()) {
                            BoxLoading(
                                modifier = Modifier.fillMaxSize()
                            )
                        } else {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(start = 18.dp, end = 18.dp, top = 2.dp),
                                content = {
                                    items(daftarMyTickets, key = { it.id }) { item ->
                                        val itemIndex = daftarMyTickets.indexOf(item)
                                        val spacerModifier = if (itemIndex == 0) {
                                            Modifier.padding(top = 2.dp)
                                        } else {
                                            Modifier.padding(top = 10.dp)
                                        }
                                        val destination = daftarDestinations?.find { destination ->
                                            destination.id == item.destinationId
                                        }
                                        Spacer(modifier = spacerModifier)
                                        CardMyTickets(
                                            destination = destination,
                                            item = item,
                                            onClick = {
                                                when (item.statusPayment) {
                                                    0 -> { }
                                                    1 -> {
                                                        idDetailTickets = item.id
                                                        showDetailTickets = true
                                                    }
                                                    else -> { }
                                                }
                                            },
                                        )
                                        if (itemIndex == daftarMyTickets.size - 1) {
                                            Spacer(modifier = Modifier.height(18.dp))
                                        }
                                    }
                                }
                            )
                        }
                    }
                    is UiState.Error -> {}
                }
            }
        )
    }
    if (showDetailTickets) {
        ModalBottomSheet(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.68f),
            onDismissRequest = {
                showDetailTickets = false
            },
            sheetState = modalDetailTickets,
            windowInsets = WindowInsets(0.dp),
            containerColor = ColorTransparent,
            dragHandle = {},
            shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp),
            content = {
                val daftarDestinations = (destinationsResult as UiState.Success<DestinationsResponse>).data.data
                val daftarMyTickets = (myTicketsResult as UiState.Success<MyTicketsResponse>).data.data
                val dataMyTickets = daftarMyTickets?.find { data ->
                    data.id == idDetailTickets
                }
                val destination = daftarDestinations?.find { destination ->
                    destination.id == dataMyTickets?.destinationId
                }
                ModalBottomSheetDetailTickets(
                    dataMyTickets = dataMyTickets,
                    dataDestinations = destination,
                    context = context
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MyTicketsScreenPreview() {
    val context = LocalContext.current
    MyTicketsScreen(
        context = context,
        navController = NavController(context),
        paddingValues = PaddingValues()
    )
}


