package io.c6.justmoveitkotlin

import io.c6.justmoveitkotlin.AppLogger.info
import io.c6.justmoveitkotlin.InputView.InputViewData
import io.c6.justmoveitkotlin.OutputView.OutputViewData
import java.time.Duration
import java.time.Duration.ZERO

internal object Application {

  private val inputView = InputView(::onStartHandler, ::onExitHandler)
  private val outputView = OutputView(::onStopHandler, ::onExitHandler)
  private val window = MainView(inputView, outputView, ::cleanup)

  private var runner: IntervalRunner? = null

  private fun tryPressingKey(elapsed: Duration, intervalDuration: Duration) {
    if (elapsed.isDivisibleInSeconds(intervalDuration)) {
      info(Strings.LOG_MSG_KEY_PRESSED)
      window.pressKey()
    }
  }

  private fun newForeverRunner(input: InputViewData, output: OutputViewData): ForeverRunner {
    val task: ForeverTask = { elapsed ->
      tryPressingKey(elapsed, input.intervalDuration)
      output.elapsedDuration = elapsed
    }
    return ForeverRunner(task)
  }

  private fun newFixedDurationRunner(input: InputViewData, output: OutputViewData): FixedDurationRunner {
    val task: FixedDurationTask = { elapsed, remaining ->
      if (ZERO == remaining) {
        onExitHandler()
      } else {
        tryPressingKey(elapsed, input.intervalDuration)
        output.elapsedDuration = elapsed
        output.remainingDuration = remaining
      }
    }
    return FixedDurationRunner(input.executionDuration, task)
  }

  private fun startIntervalRunner(input: InputViewData, output: OutputViewData) {
    runner = if (input.fixedTimeEnabled) {
      newFixedDurationRunner(input, output)
    } else {
      newForeverRunner(input, output)
    }
  }

  private fun initOutputView(input: InputViewData, output: OutputViewData) {
    output.intervalDuration = input.intervalDuration
    output.remainingDuration = null
  }

  private fun cleanup() {
    runner?.stop()
  }

  private fun onStartHandler() {
    window.switchToOutputView()
    val inputViewData = inputView.data
    val outputViewData = outputView.data
    initOutputView(inputViewData, outputViewData)
    startIntervalRunner(inputViewData, outputViewData)
  }

  private fun onStopHandler() {
    window.switchToInputView()
    cleanup()
  }

  private fun onExitHandler() {
    info(Strings.LOG_MSG_EXITING_APP)
    window.close()
  }

  internal fun start() {
    window.open()
  }
}
