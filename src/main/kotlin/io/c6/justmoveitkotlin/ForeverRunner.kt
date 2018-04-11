package io.c6.justmoveitkotlin

import io.c6.justmoveitkotlin.Utils.MILLIS_PER_SECOND
import io.c6.justmoveitkotlin.Utils.ONE_SECOND
import java.time.Duration.ZERO
import java.util.concurrent.Executors.newScheduledThreadPool
import java.util.concurrent.TimeUnit.MILLISECONDS

internal class ForeverRunner internal constructor(private val task: ForeverTask) : IntervalRunner {

  private val executor = newScheduledThreadPool(EXECUTOR_POOL_SIZE)

  private var elapsedDuration = ZERO

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
    task(elapsedDuration)
    elapsedDuration += ONE_SECOND
  }

  private companion object {
    private const val EXECUTOR_POOL_SIZE = 1
    private const val EXECUTOR_DELAY_MILLIS = 0L
  }
}
