package com.tourify.tourifyapp.ui.main.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.tourify.tourifyapp.R
import com.tourify.tourifyapp.ui.component.TopBarScreen

@Composable
fun NoticeScreen(
    onBack: () -> Unit
) {
    Scaffold (
        topBar = {
            TopBarScreen(
                title = stringResource(id = R.string.notice),
                onBack = {
                    onBack()
                }
            )
        },
    ){
        Column(
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
                .fillMaxSize(),
            content = {

            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NoticeScreenPreview() {
    NoticeScreen( onBack = {} )
}
