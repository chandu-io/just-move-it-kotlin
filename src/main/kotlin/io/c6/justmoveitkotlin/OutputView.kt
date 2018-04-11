package io.c6.justmoveitkotlin

import java.awt.Container
import java.awt.FlowLayout
import java.awt.GridLayout
import java.awt.event.KeyEvent
import java.time.Duration
import java.time.Duration.ZERO
import java.util.*
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.SwingUtilities.invokeLater

internal class OutputView internal constructor(
    private val stopHandler: () -> Unit,
    private val exitHandler: () -> Unit
) {

  private val panel = JPanel(GridLayout(4, 1))
  private val stopButton = JButton(Strings.LABEL_STOP)
  private val exitButton = JButton(Strings.LABEL_EXIT)
  private val intervalLabel = JLabel(Strings.EMPTY)
  private val elapsedLabel = JLabel(Strings.EMPTY)
  private val remainingLabel = JLabel(Strings.EMPTY)

  init {
    addIntervalLabel()
    addElapsedLabel()
    addRemainingLabel()
    addControlButtons()
  }

  private fun addIntervalLabel() {
    val panel1 = JPanel(FlowLayout())
    panel1.add(JLabel(Strings.LABEL_TIME_INTERVAL))
    panel1.add(intervalLabel)
    panel.add(panel1)
  }

  private fun addElapsedLabel() {
    val panel2 = JPanel(FlowLayout())
    panel2.add(JLabel(Strings.LABEL_ELAPSED_TIME))
    panel2.add(elapsedLabel)
    panel.add(panel2)
  }

  private fun addRemainingLabel() {
    val panel3 = JPanel(FlowLayout())
    panel3.add(JLabel(Strings.LABEL_REMAINING_TIME))
    panel3.add(remainingLabel)
    panel.add(panel3)
  }

  private fun addControlButtons() {
    stopButton.addActionListener { stopHandler() }
    stopButton.mnemonic = KeyEvent.VK_O
    exitButton.addActionListener { exitHandler() }
    exitButton.mnemonic = KeyEvent.VK_X
    val panel4 = JPanel(FlowLayout())
    panel4.add(stopButton)
    panel4.add(exitButton)
    panel.add(panel4)
  }

  internal val data = OutputViewData(this)

  internal val container: Container = panel

  internal class OutputViewData internal constructor(private val view: OutputView) {

    internal var intervalDuration: Duration = ZERO
      set(value) {
        field = value
        view.intervalLabel.update("${value.seconds} ${Strings.LABEL_TIME_INTERVAL_SUFFIX}")
      }

    internal var elapsedDuration: Duration = ZERO
      set(value) {
        field = value
        view.elapsedLabel.update(value.formattedString)
      }

    internal var remainingDuration: Duration? = null
      set(value) {
        field = value
        val text = Optional.ofNullable(value).map { it.formattedString }.orElse(Strings.LABEL_FOREVER)
        view.remainingLabel.update(text)
      }

    private fun JLabel.update(value: String) {
      invokeLater { text = value }
    }
  }
}
