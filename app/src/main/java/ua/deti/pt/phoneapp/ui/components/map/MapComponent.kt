package ua.deti.pt.phoneapp.ui.components.map

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
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
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        MarkerComposable(
            state = MarkerState(position = locationMap),
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