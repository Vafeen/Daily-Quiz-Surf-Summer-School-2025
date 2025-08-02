import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import ru.vafeen.presentation.ui.theme.White75
import ru.vafeen.presentation.ui.theme.White80
import ru.vafeen.presentation.ui.theme.White85
import ru.vafeen.presentation.ui.theme.White90
import ru.vafeen.presentation.ui.theme.White95
import kotlin.math.sin

@Composable
internal fun RadialGradientLoader(
    modifier: Modifier = Modifier,
    strokeWidth: Float = 12f,
    gradientColors: List<Color> = listOf(
        Color.White,
        White95,
        White90,
        White85,
        White80,
        White75
    ),
    animationDuration: Int = 2000
) {
    val infiniteTransition = rememberInfiniteTransition()
    val rotationAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = animationDuration, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    // Анимация для смещения градиента
    val gradientProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = animationDuration, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Canvas(modifier = modifier.size(72.dp)) {
        val center = size.center
        val radius = size.minDimension / 2 - strokeWidth
        val barLength = radius * 0.5f

        // Создаем градиент с помощью доступной функции sweepGradient
        val gradient = Brush.sweepGradient(
            colors = gradientColors,
            center = center
        )


        // Рисуем 10 палочек с вращением и градиентом
        repeat(10) { i ->
            val angle = i * 36f + rotationAngle

            rotate(angle, center) {
                val start = Offset(center.x, center.y - radius * 0.5f)
                val end = Offset(center.x, center.y - radius * 0.5f - barLength)

                drawLine(
                    brush = gradient,
                    start = start,
                    end = end,
                    strokeWidth = strokeWidth,
                    cap = StrokeCap.Round,
                    alpha = 0.7f + 0.3f * sin((gradientProgress * 2 * Math.PI + i * 0.2f).toFloat())
                )
            }
        }
    }
}