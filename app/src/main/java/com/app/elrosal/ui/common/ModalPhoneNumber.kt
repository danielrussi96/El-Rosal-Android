package com.app.elrosal.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.app.domain.user.Whatsapp
import com.app.elrosal.MainViewModel
import com.app.elrosal.R
import com.app.elrosal.ui.theme.CATEGORIES_ELEVATION
import com.app.elrosal.ui.theme.LOGO_MIN_HEIGHT
import com.app.elrosal.ui.theme.ROUND_CORNERS_8
import com.app.elrosal.utils.encryptPhoneNumber
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec


@Composable
fun ModalPhoneNumber(
    mainViewModel: MainViewModel,
    whatsapp: Whatsapp,
    onDismissRequest: () -> Unit,
) {
    var phoneNumber by rememberSaveable { mutableStateOf("") }
    var isError by rememberSaveable { mutableStateOf(false) }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            colors = cardColors(
                containerColor = colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(CATEGORIES_ELEVATION),
            shape = RoundedCornerShape(ROUND_CORNERS_8)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                SubTitleContent(subTitle = stringResource(id = R.string.contact_us))
                Image(
                    painter = painterResource(id = R.drawable.rosal_escudo),
                    contentDescription = stringResource(
                        id = R.string.el_rosal_logo
                    ),
                    modifier = Modifier
                        .width(LOGO_MIN_HEIGHT)
                        .height(LOGO_MIN_HEIGHT)
                )
                DescriptionProducts(
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 10,
                    descriptionProducts = stringResource(id = R.string.contact_us_description)
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { response ->
                        phoneNumber = response
                        isError = !isValidPhoneNumber(phoneNumber)
                    },
                    isError = isError,
                    supportingText = {
                        if (isError) {
                            Text(
                                text = stringResource(id = R.string.phone_error_message),
                                color = colorScheme.error,
                                style = typography.bodyMedium
                            )
                        }
                    },
                    trailingIcon = {
                        if (isError) {
                            Icon(
                                Icons.Filled.Info,
                                contentDescription = stringResource(id = R.string.error),
                                tint = colorScheme.error
                            )
                        }
                    },
                    label = {
                        Text(
                            stringResource(id = R.string.phone_number_hint),
                            color = colorScheme.tertiary,
                            style = typography.bodyMedium
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        cursorColor = colorScheme.tertiary,
                        focusedBorderColor = colorScheme.tertiary,
                        unfocusedBorderColor = colorScheme.tertiary,
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Send
                    ),
                    keyboardActions = KeyboardActions {
                        isError = !isValidPhoneNumber(phoneNumber)
                        if (!isError) {
                            mainViewModel.postPhoneNumber(whatsapp.copy(phoneNumber = phoneNumber))
                            onDismissRequest()
                        }
                    }
                )

                Button(onClick = {
                    if (!isError) {
                        mainViewModel.postPhoneNumber(whatsapp.copy(phoneNumber = phoneNumber))
                        onDismissRequest()
                    }
                }) {
                    Text(
                        text = stringResource(id = R.string.send),
                        color = colorScheme.secondary
                    )
                }


            }
        }
    }
}

fun isValidPhoneNumber(phoneNumber: String): Boolean {
    val phoneRegex = """^\d{10}$""".toRegex()
    return phoneRegex.matches(phoneNumber)
}


