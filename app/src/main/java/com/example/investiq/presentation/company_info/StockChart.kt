package com.example.investiq.presentation.company_info

import android.graphics.Paint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.investiq.domain.model.IntraDayInfo
import java.time.format.TextStyle
import java.util.Locale
import kotlin.math.round
import kotlin.math.roundToInt


@Composable
fun StockChart(
    modifier: Modifier=Modifier,
    infos:List<IntraDayInfo> = emptyList(),
    graphColor:Color= Color.Green
) {

    val spacing = 100f
    val transparentGraphColor = remember {
        graphColor.copy(alpha = 0.5f)
    }

    val upperValue = remember(infos) {
        (infos.maxOfOrNull { it.close }?.plus(1))?.roundToInt() ?: 0
    }

    val lowerValue = remember(infos) {
        infos.minOfOrNull { it.close }?.toInt() ?: 0
    }

    // creating text design
    val density = LocalDensity.current
    val textPaint = remember(density) {
        Paint().apply {
            color = android.graphics.Color.WHITE
            textAlign = Paint.Align.CENTER
            textSize = density.run { 12.sp.toPx() }
        }
    }

    // setting up hour text in x direction
    Canvas(modifier = modifier) {



        val filteredInfos = infos.filterIndexed { index, _ ->
            Log.d("infos",infos[index].date.dayOfWeek.toString())

                (index + 1) % 2 != 0
        }
        val chartWidth = (size.width-spacing)/infos.size
//        val spacerPerWeek = (size.width-spacing)/(infos.size-filteredInfos.size)
        val spacerPerWeek = (size.width-spacing)/5

        (0 until filteredInfos.size).forEach(){
            val info=filteredInfos[it]
            val hour = info.date.dayOfWeek

            // Format the day of the week to its short form
            val shortDayOfWeek: String = hour.getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
            Log.d("hour", shortDayOfWeek)

            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    shortDayOfWeek,
                    spacing + it*spacerPerWeek,
                    size.height-5,
                    textPaint
                )
            }
        }

        val priceStep = (upperValue - lowerValue) / 5f

        (0..4).forEach { i ->
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    round(lowerValue + priceStep * i).toString(),
                    30f,
                    size.height - spacing - i * size.height / 5f,
                    textPaint
                )
            }
        }

        var lastX = 0f
        val strokePath = Path().apply {
            val height = size.height

            for ( i in infos.indices){

                val info = infos[i]
                val nextInfo = infos.getOrNull(i+1) ?: infos.last()

                val leftRatio = (info.close - lowerValue)/ (upperValue-lowerValue)
                val rightRatio = (nextInfo.close - lowerValue)/ (upperValue-lowerValue)

                val x1 = spacing + i * chartWidth
                val y1 = height - spacing - (leftRatio * height).toFloat()
                val x2 = spacing + (i + 1) * chartWidth
                val y2 = height - spacing - (rightRatio * height).toFloat()
                if(i == 0) {
                    moveTo(x1, y1)
                }
                lastX = (x1 + x2) / 2f
                quadraticBezierTo(
                    x1, y1, lastX, (y1 + y2) / 2f
                )
            }
        }

        val fillPath = android.graphics.Path(strokePath.asAndroidPath())
            .asComposePath()
            .apply {
                lineTo(lastX, size.height - spacing)
                lineTo(spacing, size.height - spacing)
                close()
            }

        drawPath(
            path = fillPath,
            brush = Brush.verticalGradient(
                colors = listOf(
                    transparentGraphColor,
                    Color.Transparent
                ),
                endY = size.height - spacing
            )
        )

        drawPath(
            path = strokePath,
            color = graphColor,
            style = Stroke(
                width = 3.dp.toPx(),
                cap = StrokeCap.Round
            )
        )

    }

}