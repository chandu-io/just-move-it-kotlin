package io.c6.justmoveitkotlin

import io.c6.justmoveitkotlin.AppLogger.error
import io.c6.justmoveitkotlin.AppLogger.info
import java.awt.AWTException
import java.awt.CardLayout
import java.awt.Robot
import java.awt.event.KeyEvent
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.SwingUtilities.invokeLater
import javax.swing.WindowConstants.EXIT_ON_CLOSE

internal class MainView internal constructor(
    private val inputView: InputView,
    private val outputView: OutputView,
    private val cleanup: () -> Unit
) {

  private val frame = JFrame(Strings.FRAME_TITLE)
  private val cardLayout = CardLayout()
  private val panel = JPanel(cardLayout)

  private var robot: Robot? = null

  internal fun open() {
    panel.add(inputView.container)
    panel.add(outputView.container)
    with(frame) {
      contentPane = panel
      defaultCloseOperation = EXIT_ON_CLOSE
      addWindowListener(onBeforeClosing())
      isResizable = false
      setSize(WINDOW_WIDTH, WINDOW_HEIGHT)
      setLocationRelativeTo(null)
      isVisible = true
    }
    initRobot()
  }

  private fun onBeforeClosing(): WindowAdapter =
      object : WindowAdapter() {
        override fun windowClosing(e: WindowEvent) {
          super.windowClosing(e)
          info(Strings.LOG_MSG_EXITING_APP)
          cleanup()
        }
      }

  private fun initRobot() {
    try {
      robot = Robot()
    } catch (ex: AWTException) {
      error(Strings.LOG_ERR_ROBOT_INIT_ERROR, ex)
      info(Strings.LOG_MSG_EXITING_APP)
      System.exit(1)
    }
  }

  internal fun pressKey() {
    robot?.keyRelease(KeyEvent.VK_F23)
  }

  internal fun switchToInputView() {
    cardLayout.first(panel)
  }

  internal fun switchToOutputView() {
    cardLayout.last(panel)
  }

  internal fun close() {
    cleanup()
    invokeLater { frame.dispose() }
  }

  internal companion object {
    private const val WINDOW_WIDTH = 300
    private const val WINDOW_HEIGHT = 200
  }
}
