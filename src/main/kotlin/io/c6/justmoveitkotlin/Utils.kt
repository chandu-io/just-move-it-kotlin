package io.c6.justmoveitkotlin

import java.time.Duration.*
import java.util.*
import javax.swing.ComboBoxModel
import javax.swing.DefaultComboBoxModel

internal object Utils {

  internal val ONE_SECOND = ofSeconds(1)
  internal val MILLIS_PER_SECOND = ONE_SECOND.toMillis()
  internal val SECONDS_PER_MINUTE = ofMinutes(1).seconds
  internal val MINUTES_PER_HOUR = ofHours(1).toMinutes()
  internal val HOURS_PER_DAY = ofDays(1).toHours()

  internal fun getFormattedDuration(seconds: Long) = String.format(Strings.FMT_HH_MM_SS,
      seconds / (MINUTES_PER_HOUR * SECONDS_PER_MINUTE),
      seconds % (MINUTES_PER_HOUR * SECONDS_PER_MINUTE) / MINUTES_PER_HOUR,
      seconds % SECONDS_PER_MINUTE)

  internal fun numberedComboBoxModel(startInclusive: Long = 0L, endExclusive: Long): ComboBoxModel<Long> =
      DefaultComboBoxModel((startInclusive..endExclusive.dec()).toCollection(Vector<Long>()))
}
