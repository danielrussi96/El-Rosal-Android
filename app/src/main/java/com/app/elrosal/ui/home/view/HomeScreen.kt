package com.app.elrosal.ui.home.view


import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.app.domain.user.Whatsapp
import com.app.elrosal.MainViewModel
import com.app.elrosal.R
import com.app.elrosal.ui.common.ModalPhoneNumber
import com.app.elrosal.ui.common.SubTitleContent
import com.app.elrosal.ui.common.TitleContent
import com.app.elrosal.ui.home.WhatsappUiState
import com.app.elrosal.ui.theme.CATEGORIES_ELEVATION
import com.app.elrosal.ui.theme.ElRosalTheme
import com.app.elrosal.ui.theme.ICON_SIZE_40
import com.app.elrosal.ui.theme.LOGO_HEIGHT
import com.app.elrosal.ui.theme.PADDING_16
import com.app.elrosal.ui.theme.ROUND_CORNERS_8
import com.app.elrosal.utils.Constants.IMAGE_PRESENTATION
import com.app.elrosal.utils.ConstantsViews.WEIGHT_1F
import com.app.elrosal.utils.decryptedData

@Composable
fun HomeScreen(
    mainViewModel: MainViewModel,
    navigateToProductScreen: (String) -> Unit,
    permissionCall: () -> Unit
) {

    var showDialog by rememberSaveable { mutableStateOf(false) }

    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val context = LocalContext.current

    val uiState by produceState<WhatsappUiState?>(
        initialValue = null,
        key1 = lifecycle,
        key2 = mainViewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            mainViewModel.uiStatePhoneNumber.collect { whatsappUiState ->
                value = whatsappUiState
            }
        }
    }

    when (uiState) {
        is WhatsappUiState.Error -> {

        }

        is WhatsappUiState.Loading -> {
            showDialog = true
        }

        is WhatsappUiState.Success -> {
            showDialog = false

            val whatsappIntent = Intent(Intent.ACTION_VIEW)
            whatsappIntent.data = Uri.parse("https://wa.me/573128031680")
            startActivity(context, whatsappIntent, null)
        }

    }

    if (showDialog)
        LoadingMessageWhatsapp()

    CategoriesList(
        mainViewModel = mainViewModel,
        navigateToProductScreen = navigateToProductScreen,
        permissionCall = permissionCall
    )
}

@Composable
fun CategoriesList(
    mainViewModel: MainViewModel,
    navigateToProductScreen: (String) -> Unit,
    permissionCall: () -> Unit
) {
    var showDialog by rememberSaveable { mutableStateOf(false) }
    val whatsapp = Whatsapp(
        phoneNumber = "",
        message = stringResource(id = R.string.message_sent),
        image = IMAGE_PRESENTATION
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)
    ) {
        Row(
            modifier = Modifier.padding(PADDING_16), verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.rosal_escudo),
                contentDescription = stringResource(
                    id = R.string.el_rosal_logo
                ),
                modifier = Modifier
                    .width(LOGO_HEIGHT)
                    .height(LOGO_HEIGHT)
                    .weight(WEIGHT_1F)
            )

            Column(
                modifier = Modifier.weight(WEIGHT_1F),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                TitleContent(
                    title = stringResource(id = R.string.app_name)
                )

                Row(
                    modifier = Modifier
                        .padding(PADDING_16),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        modifier = Modifier
                            .height(ICON_SIZE_40)
                            .width(ICON_SIZE_40)
                            .clickable {
                                permissionCall()
                            },
                        imageVector = Icons.Rounded.Phone,
                        contentDescription = stringResource(id = R.string.call_us),
                        tint = colorScheme.tertiary,
                    )
                    Spacer(modifier = Modifier.width(PADDING_16))
                    Image(
                        modifier = Modifier
                            .clickable {
                                mainViewModel.getPhoneNumber()

                                if (mainViewModel.phoneNumber.value != null) {
                                    val phoneNumber =
                                        mainViewModel.phoneNumber.value?.decryptedData() ?: ""
                                    val updatedWhatsapp = whatsapp.copy(phoneNumber = phoneNumber)
                                    mainViewModel.postMessageWhatsapp(updatedWhatsapp)
                                } else {
                                    showDialog = true
                                }
                            },
                        painter = painterResource(id = R.drawable.ic_whatsapp),
                        contentDescription = stringResource(id = R.string.write_us)
                    )
                }

            }
        }

        CategoriesContent(
            mainViewModel = mainViewModel, navigateToProductScreen = navigateToProductScreen
        )

        if (showDialog)
            ModalPhoneNumber(mainViewModel = mainViewModel, whatsapp = whatsapp) {
                showDialog = false
            }
    }

}

@Composable
fun LoadingMessageWhatsapp() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading))

    Dialog(
        onDismissRequest = { },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
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

                SubTitleContent(subTitle = stringResource(id = R.string.sending_message))
                LottieAnimation(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    composition = composition,
                    iterations = LottieConstants.IterateForever,
                )
            }
        }
    }

}


@Preview
@Composable
fun LoadingMessageWhatsappPreview() {
    ElRosalTheme {
        LoadingMessageWhatsapp()
    }
}
