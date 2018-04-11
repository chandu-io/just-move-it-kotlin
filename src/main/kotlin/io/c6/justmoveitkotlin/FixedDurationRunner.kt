package io.c6.justmoveitkotlin

import io.c6.justmoveitkotlin.Utils.MILLIS_PER_SECOND
import io.c6.justmoveitkotlin.Utils.ONE_SECOND
import java.time.Duration
import java.time.Duration.ZERO
import java.util.concurrent.Executors.newScheduledThreadPool
import java.util.concurrent.TimeUnit.MILLISECONDS

internal class FixedDurationRunner internal constructor(
    executionDuration: Duration,
    private val task: FixedDurationTask
) : IntervalRunner {

  private val executor = newScheduledThreadPool(EXECUTOR_POOL_SIZE)

  private var elapsedDuration = ZERO
  private var remainingDuration = executionDuration

  init {
    executor.scheduleAtFixedRate(::run, EXECUTOR_DELAY_MILLIS, MILLIS_PER_SECOND, MILLISECONDS)
  }

  override fun isDone() = executor.isShutdown

  override fun stop() {
    if (!isDone()) {
      executor.shutdownNow()
    }
  }

  private fun run() {
    task(elapsedDuration, remainingDuration)
    remainingDuration -= ONE_SECOND
    elapsedDuration += ONE_SECOND
  }

  private companion object {
    private const val EXECUTOR_POOL_SIZE = 1
    private const val EXECUTOR_DELAY_MILLIS = 0L
  }
}
