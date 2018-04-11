package io.c6.justmoveitkotlin

internal object Strings {

  internal const val APP_NAME = "JustMoveIt"
  internal const val VERSION = "1.0.2"

  internal const val EMPTY = ""
  internal const val LOGGER_NAME = APP_NAME
  internal const val LOG_FILE_NAME = "$APP_NAME.log"
  internal const val FRAME_TITLE = "$APP_NAME-$VERSION @ Chandu"

  internal const val LABEL_FIXED_TIME = "Fixed Time?"
  internal const val LABEL_HOURS = "Hours:"
  internal const val LABEL_MINUTES = "Minutes:"
  internal const val LABEL_TIME_INTERVAL_SEC = "Time interval (seconds):"
  internal const val LABEL_TIME_INTERVAL = "Time interval:"
  internal const val LABEL_TIME_INTERVAL_SUFFIX = " second(s)"
  internal const val LABEL_ELAPSED_TIME = "Elapsed time:"
  internal const val LABEL_REMAINING_TIME = "Remaining time:"
  internal const val LABEL_STOP = "STOP"
  internal const val LABEL_EXIT = " EXIT "
  internal const val LABEL_START = "START"
  internal const val LABEL_EXIT_1 = "  EXIT  "
  internal const val LABEL_FOREVER = "FOREVER"

  internal const val FMT_HH_MM_SS = "   %02d : %02d : %02d"

  internal const val LOG_MSG_KEY_PRESSED = "Key pressed..."
  internal const val LOG_MSG_EXITING_APP = "Exiting app..."
  internal const val LOG_ERR_ROBOT_INIT_ERROR = "Could not initialize java.awt.Robot"
}
