package com.example.stockmarketapp.presentation.company_info

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.AndroidPath
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stockmarketapp.domain.model.DailyInfo
import java.util.Calendar
import kotlin.math.round
import kotlin.math.roundToInt

@Composable
fun StockChart(
    modifier: Modifier = Modifier,
    dailyInfo: List<DailyInfo> = emptyList(),
    graphColor: Color = MaterialTheme.colorScheme.primary
) {
    val spacing = 100f
    val gradient = remember {
        graphColor.copy(alpha = 0.7f)
    }
    val upperValue = remember(dailyInfo) {
        (dailyInfo.maxOfOrNull { it.close })?.plus(1)?.roundToInt() ?: 0
    }
    val lowerValue = remember(dailyInfo) {
        (dailyInfo).minOfOrNull { it.close }?.toInt() ?: 0
    }
    val density = LocalDensity.current
    val onBackgroundColor = MaterialTheme.colorScheme.onBackground
    val textPaint = remember(density) {
        Paint().apply {
            color = onBackgroundColor.toArgb()
            textAlign = Paint.Align.CENTER
            textSize = density.run { 12.sp.toPx() }
        }
    }
    Canvas(modifier = modifier) {
        //Ось X
        val spacePerDay = (size.width - spacing) / dailyInfo.size
        (0 until dailyInfo.size - 1 step 3).forEach { i ->
            val info = dailyInfo[i]
            val calendar = Calendar.getInstance().apply {
                time = info.date
            }
            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    dayOfMonth.toString(),
                    spacing + i * spacePerDay,
                    size.height - 5,
                    textPaint
                )
            }
        }
        var lastX = 0f
        // Ось Y
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
        val strokePath = Path().apply {
            val height = size.height
            for (i in dailyInfo.indices) {
                val info = dailyInfo[i]
                val nextInfo = dailyInfo.getOrNull(i + 1) ?: dailyInfo.last()
                val leftRatio = (info.close - lowerValue) / (upperValue - lowerValue)
                val rightRatio = (nextInfo.close - lowerValue) / (upperValue - lowerValue)
                val x1 = spacing + i * spacePerDay
                val y1 = height - spacing - (leftRatio * height).toFloat()
                val x2 = spacing + (i * 1) * spacePerDay
                val y2 = height - spacing - (rightRatio * height).toFloat()
                if (i == 0) {
                    moveTo(x1, y1)
                }
                lastX = (x1 + x2) / 2f
                quadraticBezierTo(x1, y1, (x1 + x2) / 2f, (y1 + y2) / 2f)

            }
        }
        val fillPath = android.graphics.Path(strokePath.asAndroidPath()).asComposePath().apply {
            lineTo(lastX, size.height - spacing)
            lineTo(spacing, size.height - spacing)
            close()
        }
        drawPath(
            path = fillPath,
            brush = Brush.verticalGradient(
                colors = listOf(
                    gradient,
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