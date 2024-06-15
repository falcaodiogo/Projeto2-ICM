package ua.deti.pt.phoneapp.ui.components.map

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import ua.deti.pt.phoneapp.R

@Composable
fun MapScreen() {
    val aveiro = LatLng(40.638076, -8.653603)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(aveiro, 15f)
    }
    val locationMap = LatLng(40.638654, -8.655872)
    val locationMap2 = LatLng(40.639322, -8.651495)
    val locationMap3 = LatLng(40.636694, -8.653297)
    val locationMap4 = LatLng(40.633362, -8.658463)

    val openAlertDialog = remember { mutableStateOf(false) }

    when {
        openAlertDialog.value -> {
            AlertDialogMap(
                onDismissRequest = { openAlertDialog.value = false },
                onConfirmation = {
                    openAlertDialog.value = false
                    println("Confirmation registered")
                },
                dialogTitle = "Não sei como meter nos spots",
                dialogText = "This is an example of an alert dialog with buttons.",
                icon = Icons.Default.Info
            )
        }
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        onMapClick = {
            openAlertDialog.value = true
        }
    ) {
        MarkerComposable(
            state = MarkerState(position = locationMap),
            onInfoWindowClick = {
                openAlertDialog.value = true
            }
        ) {
            Image(
                painter = painterResource(id = R.drawable.locationpoint),
                contentDescription = "",
                modifier = Modifier.size(50.dp)
            )
        }
        MarkerComposable(
            state = MarkerState(position = locationMap2),
        ) {
            Image(
                painter = painterResource(id = R.drawable.locationpoint),
                contentDescription = "",
                modifier = Modifier.size(50.dp)
            )
        }
        MarkerComposable(
            state = MarkerState(position = locationMap3),
        ) {
            Image(
                painter = painterResource(id = R.drawable.locationpoint),
                contentDescription = "",
                modifier = Modifier.size(50.dp)
            )
        }
        MarkerComposable(
            state = MarkerState(position = locationMap4),
        ) {
            Image(
                painter = painterResource(id = R.drawable.locationpoint),
                contentDescription = "",
                modifier = Modifier.size(50.dp)
            )
        }
    }
}