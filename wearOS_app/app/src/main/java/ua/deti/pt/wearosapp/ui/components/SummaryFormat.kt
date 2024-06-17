package ua.deti.pt.wearosapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text

@Composable
fun SummaryFormat(
    value: AnnotatedString,
    metric: String,
    modifier: Modifier = Modifier
) {
    Column {
        Row(horizontalArrangement = Arrangement.Center, modifier = modifier) {
            Text(
                textAlign = TextAlign.Center,
                text = value,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.secondary,
                fontSize = 25.sp
            )
        }
        Row(horizontalArrangement = Arrangement.Center, modifier = modifier) {
            Text(
                text = metric,
                textAlign = TextAlign.Center,
                fontSize = 10.sp
            )
        }
    }
}

@Preview
@Composable
fun SummaryFormatPreview() {
    SummaryFormat(value = buildAnnotatedString { append("9.13") }, metric = "km")
}