package ua.deti.pt.phoneapp.ui.components.sleepchart

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine

@Composable
fun SingleLineChartWithGridLines(pointsData: List<Point>) {
    val popUpLabel: (Float, Float) -> String = { x, y -> "${y.toInt()} sleep at ${x.toInt()} "}
    val xAxisData = AxisData.Builder()
        .axisStepSize(90.dp)
        .steps(pointsData.size - 1)
        .labelData { i -> pointsData[i].x.toInt().toString() }
        .axisLineColor(Color.White)
        .axisLabelColor(Color.White)
        .build()
    val yAxisData = AxisData.Builder()
        .axisLineColor(Color.White)
        .axisLabelColor(Color.White)
        .build()
    val data = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = pointsData,
                    LineStyle(color = Color.White),
                    IntersectionPoint(color = Color.White),
                    SelectionHighlightPoint(color = Color.White),
                    ShadowUnderLine(),
                    SelectionHighlightPopUp(popUpLabel = popUpLabel)
                )
            )
        ),
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        gridLines = GridLines(),
        backgroundColor = Color.Black
    )
    LineChart(
        modifier = Modifier
            .fillMaxWidth()
//            .padding(top = 20.dp)
            .height(300.dp),
        lineChartData = data
    )
}