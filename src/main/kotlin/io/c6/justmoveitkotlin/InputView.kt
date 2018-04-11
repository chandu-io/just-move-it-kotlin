package io.c6.justmoveitkotlin

import io.c6.justmoveitkotlin.Utils.HOURS_PER_DAY
import io.c6.justmoveitkotlin.Utils.MINUTES_PER_HOUR
import io.c6.justmoveitkotlin.Utils.numberedComboBoxModel
import java.awt.Container
import java.awt.FlowLayout
import java.awt.GridLayout
import java.awt.event.KeyEvent
import java.time.Duration
import java.time.Duration.*
import javax.swing.*

internal class InputView internal constructor(
    private val startHandler: () -> Unit,
    private val exitHandler: () -> Unit
) {

  private val panel = JPanel(GridLayout(4, 1))
  private val startButton = JButton(Strings.LABEL_START)
  private val exitButton = JButton(Strings.LABEL_EXIT_1)
  private val fixedTimeCheckBox = JCheckBox(Strings.LABEL_FIXED_TIME)
  private val hoursComboBox = JComboBox(numberedComboBoxModel(endExclusive = HOURS_PER_DAY))
  private val minutesComboBox = JComboBox(numberedComboBoxModel(endExclusive = MINUTES_PER_HOUR))
  private val secondsComboBox = JComboBox(numberedComboBoxModel(1, MAX_INTERVAL_SECONDS + 1))

  init {
    addFixedTimeCheckBox()
    addDurationComboBoxes()
    addIntervalComboBox()
    addControlButtons()
  }

  private fun addFixedTimeCheckBox() {
    fixedTimeCheckBox.mnemonic = KeyEvent.VK_F
    fixedTimeCheckBox.isSelected = false
    fixedTimeCheckBox.addItemListener { toggleCheckBox() }
    val panel1 = JPanel(FlowLayout())
    panel1.add(fixedTimeCheckBox)
    panel.add(panel1)
  }

  private fun addDurationComboBoxes() {
    hoursComboBox.selectedIndex = 1
    hoursComboBox.isEnabled = false
    minutesComboBox.isEnabled = false
    val panel2 = JPanel(FlowLayout())
    panel2.add(JLabel(Strings.LABEL_HOURS))
    panel2.add(hoursComboBox)
    panel2.add(JLabel(Strings.LABEL_MINUTES))
    panel2.add(minutesComboBox)
    panel.add(panel2)
  }

  private fun addIntervalComboBox() {
    secondsComboBox.selectedIndex = (MINUTES_PER_HOUR - 1).toInt()
    val panel3 = JPanel(FlowLayout())
    panel3.add(JLabel(Strings.LABEL_TIME_INTERVAL_SEC))
    panel3.add(secondsComboBox)
    panel.add(panel3)
  }

  private fun addControlButtons() {
    startButton.addActionListener { startHandler() }
    startButton.mnemonic = KeyEvent.VK_S
    exitButton.addActionListener { exitHandler() }
    exitButton.mnemonic = KeyEvent.VK_X
    val panel4 = JPanel(FlowLayout())
    panel4.add(startButton)
    panel4.add(exitButton)
    panel.add(panel4)
  }

  private fun toggleCheckBox() {
    val fixedTimeEnabled = fixedTimeCheckBox.isSelected
    hoursComboBox.isEnabled = fixedTimeEnabled
    minutesComboBox.isEnabled = fixedTimeEnabled
  }

  internal val data = InputViewData(this)

  internal val container: Container = panel

  internal companion object {
    private val MAX_INTERVAL_SECONDS: Long = ofMinutes(2).seconds
  }

  internal class InputViewData internal constructor(private val view: InputView) {

    internal var fixedTimeEnabled: Boolean = false
      get() {
        val value = view.fixedTimeCheckBox.isSelected
        field = value
        return field
      }
      private set

    internal var executionDuration: Duration = ZERO
      get() {
        val value = calcExecutionDuration()
        field = value
        return field
      }
      private set

    internal var intervalDuration: Duration = ZERO
      get() {
        val value = ofSeconds(view.secondsComboBox.selected)
        field = value
        return field
      }
      private set

    private fun calcExecutionDuration(): Duration {
      val minutes = view.minutesComboBox.selected
      val hours = view.hoursComboBox.selected
      return ofHours(hours).plus(ofMinutes(minutes))
    }
  }
}
