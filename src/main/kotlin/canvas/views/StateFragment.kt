package canvas.views

import canvas.STATE_CIRCLE_RADIUS
import canvas.data.State
import canvas.controllers.CanvasController
import canvas.styles.StateStyles
import javafx.geometry.Pos
import javafx.scene.paint.Color
import tornadofx.*


class StateFragment(val item: State) : Fragment() {
    val controller = find(CanvasController::class)

    override val root =
            borderpane {
                translateXProperty().bind(item.xProperty)
                translateYProperty().bind(item.yProperty)

                toggleClass(StateStyles.hidden, item.hiddenProperty)

                center = stackpane {
                    circle {
                        radius = STATE_CIRCLE_RADIUS
                        fill = Color.WHITE

                        setOnMousePressed { controller.handleMPress(item, it); it.consume() }
                        setOnDragDetected { controller.startLineDrawing(item, this); it.consume() }
                        setOnMouseDragged { controller.handleMDrag(item, it); it.consume() }
                        setOnMouseDragReleased { controller.handleDragEnd(item); it.consume() }
                        setOnMouseClicked { it.consume() } // Only used to stop events from bubbling upwards to the Canvas

                    }
                    label {
                        textProperty().bind(item.nameProperty)
                        isMouseTransparent = true
                    }
                }

                bottom = label {
                    textProperty().bind(stringBinding(item.propsProperty) {
                        item.propsProperty.value.joinToString(","){ it.propString }
                    })
                    useMaxWidth = true
                    alignment = Pos.CENTER
                }
            }
}

