package io.c6.justmoveitkotlin

import io.c6.justmoveitkotlin.Utils.getFormattedDuration
import java.time.Duration
import javax.swing.JComboBox

internal typealias ForeverTask = (elapsed: Duration) -> Unit

internal typealias FixedDurationTask = (elapsed: Duration, remaining: Duration) -> Unit

internal val Duration.formattedString
  get() = getFormattedDuration(seconds)

internal fun Duration.isDivisibleInSeconds(other: Duration) =
    if (other.seconds == 0L) false // to eliminate divide by zero error
    else seconds % other.seconds == 0L

internal val <E> JComboBox<E>.selected: E
  get() = getItemAt(selectedIndex)
