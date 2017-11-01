package formulafield

import canvas.controllers.CanvasController
import javafx.scene.control.TextField
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCombination
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import sidepanels.debugpanel.DebugController
import tornadofx.*

class FormulaField : View("My View") {

    val controller: FormulaFieldController by inject()
    val debugController: DebugController by inject()

    override val root = vbox {
        //Label used to display error messages
        label(controller.errorMsgProperty) {
            removeWhen {
                textProperty().isEmpty
            }
        }

        val debugArea = hbox()
        val inputField = TextField()
        initInputField(inputField, debugArea)


        hbox {
            add(inputField)
            button("Step through") {  //TODO Find better name for button
                action { debugController.startDebug(inputField.text) }
            }
        }

        setOnKeyPressed {
            if (it.code == KeyCode.UP) {
                controller.getPreviousFormula(inputField)
                it.consume()
            } else if (it.code == KeyCode.DOWN) {
                controller.getNextFormula(inputField)
                it.consume()
            }
        }
    }

    private fun FormulaField.initInputField(inputField: TextField, debugArea: HBox) {
        with(inputField) {
            //TODO Clear error field when user resumes editing formula
            hgrow = Priority.ALWAYS
            promptText = "Write formulas here"
            setOnAction { controller.validateFormString(text, debugArea) }
            accelerators.put(KeyCombination.keyCombination("Esc")) {
                controller.clearValidation()
            }

            setOnKeyPressed {
                if (it.code == KeyCode.UP) {
                    controller.getPreviousFormula(this)
                    it.consume()
                } else if (it.code == KeyCode.DOWN) {
                    controller.getNextFormula(this)
                    it.consume()
                }
            }
        }
    }
}