package com.example.edekaqaapp.ui.screens

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.edekaqaapp.ui.theme.EdekaBlue
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun FirstScreen(onNavigateToSecond: () -> Unit) {
    var count by remember { mutableIntStateOf(0) }
    val minValue = 0
    val maxValue = 100

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Hello Iulia",
            style = MaterialTheme.typography.displayMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            RepeatingIconButton(
                onClick = { if (count > minValue) count-- },
                enabled = count > minValue,
                icon = Icons.Default.Remove,
                contentDescription = "Verringern"
            )

            Text(
                text = count.toString(),
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            RepeatingIconButton(
                onClick = { if (count < maxValue) count++ },
                enabled = count < maxValue,
                icon = Icons.Default.Add,
                contentDescription = "Erhöhen"
            )
        }
    }
}

@Composable
private fun RepeatingIconButton(
    onClick: () -> Unit,
    enabled: Boolean,
    icon: ImageVector,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    val containerColor = if (enabled) EdekaBlue else EdekaBlue.copy(alpha = 0.38f)
    val interactionSource = remember { MutableInteractionSource() }

    Surface(
        modifier = modifier
            .size(64.dp)
            .indication(interactionSource, ripple())
            .repeatingClickable(
                interactionSource = interactionSource,
                enabled = enabled,
                delayMillis = 200L,
                onClick = onClick
            ),
        shape = CircleShape,
        color = containerColor,
        contentColor = Color.White
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                modifier = Modifier.size(36.dp)
            )
        }
    }
}

/**
 * Repeatedly invokes [onClick] every [delayMillis] while the pointer is held down.
 * Fires once immediately on press, covering the normal single-tap case as well.
 * Also emits press/release interactions so a ripple can be driven via [indication].
 */
private fun Modifier.repeatingClickable(
    interactionSource: MutableInteractionSource,
    enabled: Boolean,
    delayMillis: Long = 200L,
    onClick: () -> Unit
): Modifier = composed {
    val currentOnClick by rememberUpdatedState(onClick)
    val currentEnabled by rememberUpdatedState(enabled)
    val scope = rememberCoroutineScope()

    pointerInput(Unit) {
        awaitEachGesture {
            val down = awaitFirstDown(requireUnconsumed = false)
            val press = PressInteraction.Press(down.position)
            scope.launch { interactionSource.emit(press) }

            if (currentEnabled) {
                val job = scope.launch {
                    while (true) {
                        currentOnClick()
                        delay(delayMillis)
                    }
                }
                val released = waitForUpOrCancellation() != null
                job.cancel()
                scope.launch {
                    interactionSource.emit(
                        if (released) PressInteraction.Release(press) else PressInteraction.Cancel(press)
                    )
                }
            } else {
                waitForUpOrCancellation()
                scope.launch { interactionSource.emit(PressInteraction.Cancel(press)) }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FirstScreenPreview() {
    FirstScreen(onNavigateToSecond = {})
}








