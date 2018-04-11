package io.c6.justmoveitkotlin

import java.io.IOException
import java.util.logging.FileHandler
import java.util.logging.Level.*
import java.util.logging.SimpleFormatter

internal object AppLogger {

  private val LOG = java.util.logging.Logger.getLogger(Strings.LOGGER_NAME)

  init {
    LOG.level = ALL
    try {
      val fileHandler = FileHandler(Strings.LOG_FILE_NAME, true)
      fileHandler.formatter = SimpleFormatter()
      LOG.addHandler(fileHandler)
    } catch (e: IOException) {
      java.util.logging.Logger.getGlobal().log(SEVERE, e.message, e)
    }
  }

  internal fun debug(msg: Any) = LOG.log(FINE, msg.toString())

  internal fun info(msg: Any) = LOG.log(INFO, msg.toString())

  internal fun warn(msg: Any) = LOG.log(WARNING, msg.toString())

  internal fun error(msg: Any, thrown: Throwable? = null) = LOG.log(SEVERE, msg.toString(), thrown)
}
