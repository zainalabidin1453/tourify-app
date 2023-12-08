package com.tourify.tourifyapp.ui.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tourify.tourifyapp.R
import com.tourify.tourifyapp.ui.theme.ColorDanger
import com.tourify.tourifyapp.ui.theme.ColorPrimary
import com.tourify.tourifyapp.ui.theme.ColorWhite
import com.tourify.tourifyapp.ui.theme.Shapes
import com.tourify.tourifyapp.ui.theme.StyleText
import com.tourify.tourifyapp.ui.theme.TextLight
import com.tourify.tourifyapp.ui.theme.TextPrimary
import com.tourify.tourifyapp.ui.theme.fonts
import com.tourify.tourifyapp.utils.DateRangePickerScreen
import com.tourify.tourifyapp.utils.changeDateToLong
import com.tourify.tourifyapp.utils.modifyDateRange
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldDateBooking(
    placeholder: String,
    icon: Int,
    iconDescription: Int,
    height: Dp = 50.dp,
    fontSize: TextUnit = 14.sp,
    iconSize: Dp = 22.dp,
    keyboardType: KeyboardType,
    onStartDate: (String) -> Unit,
    onEndDate: (String) -> Unit,
    onAllDate: (String) -> Unit,
    isError: Boolean
) {
    var text by rememberSaveable { mutableStateOf("") }
    var startDate by rememberSaveable { mutableStateOf("") }
    var endDate by rememberSaveable { mutableStateOf("") }
    var showDateTo by rememberSaveable { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val modalDatePickerBottomState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    val dateRange = modifyDateRange(startDate, endDate)
    text = dateRange
    OutlinedTextField(
        value = text,
        onValueChange = {},
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = ColorWhite,
            focusedBorderColor = ColorPrimary,
            unfocusedContainerColor = ColorWhite,
            unfocusedBorderColor = TextLight,
            errorContainerColor = ColorWhite,
            errorBorderColor = ColorDanger,
            disabledContainerColor = ColorWhite,
            cursorColor = ColorPrimary,
        ),
        isError = isError,
        enabled = false,
        maxLines = 1,
        shape = Shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .clip(Shapes.medium)
            .clickable { showDateTo = true  },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        textStyle = StyleText.copy(
            color = TextPrimary,
            fontFamily = fonts,
            fontWeight = FontWeight.Normal,
            fontSize = fontSize,
            lineHeight = fontSize,
        ),
        placeholder = {
            Text(
                text = placeholder,
                color = TextLight,
                style = StyleText.copy(
                    fontFamily = fonts,
                    fontWeight = FontWeight.Normal,
                    fontSize = fontSize,
                    lineHeight = fontSize
                )
            )
        },
        trailingIcon = {
            Icon(
                modifier = Modifier
                    .size(iconSize)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick ={
                            showDateTo = true
                        }
                    ),
                painter = painterResource(icon),
                contentDescription = stringResource(id = iconDescription),
                tint = TextLight
            )
        }
    )

    if (showDateTo) {
        ModalBottomSheet(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f),
            onDismissRequest = {
                showDateTo = false
            },
            sheetState = modalDatePickerBottomState,
            containerColor = ColorWhite,
            shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp),
            dragHandle = {},
            content = {
                Scaffold(
                    containerColor = ColorWhite,
                    topBar = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(18.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            content = {
                                Text(
                                    text = "\uD83D\uDCC5 Pilih Tanggal Perjalanan",
                                    style = StyleText.copy(
                                        color = TextPrimary,
                                        fontFamily = fonts,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 14.sp,
                                        lineHeight = 14.sp
                                    )
                                )
                            }
                        )
                    }) { paddingValues ->
                    Column(
                        modifier = Modifier
                            .padding(top = paddingValues.calculateTopPadding())
                            .background(ColorWhite),
                        content = {
                            DateRangePickerScreen(
                                onStartDate = {
                                    startDate = it
                                    onStartDate(startDate)
                                    onAllDate(dateRange)
                                }
                            ) {
                                endDate = it
                                onEndDate(startDate)
                                onAllDate(dateRange)
                            }
                        }
                    )
                }
            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun TextFieldDateBookingPreview() {
    val isError by rememberSaveable { mutableStateOf(false) }
    TextFieldDateBooking(
        placeholder = "Pilih tanggal perjalanan",
        icon = R.drawable.ic_mail,
        iconDescription = R.string.email_address,
        keyboardType = KeyboardType.Email,
        isError = isError,
        onStartDate = {},
        onEndDate = {},
        onAllDate = {}
    )
}