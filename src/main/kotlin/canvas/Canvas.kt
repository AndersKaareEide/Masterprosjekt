package canvas

import javafx.collections.FXCollections
import tornadofx.*

class Canvas : View("My View") {

    val controller: CanvasStateController by inject()
    val state1 = State("s1", 150.0, 200.0)
    val state2 = State("s2", 50.0, 70.0, "p, q")
    val states = FXCollections.observableArrayList(state1, state2)
    val edges = FXCollections.observableArrayList(Edge(state1, state2, "a, b, c"))

    override val root = borderpane {
        prefHeight = 600.0
        prefWidth = 600.0


        setOnMouseClicked { controller.handleCanvasClick(it) }

        center = stackpane {
            anchorpane {
                bindChildren(edges) {
                    EdgeFragment(it).root
                }
            }
            anchorpane {
                bindChildren(states) {
                    StateFragment(it).root
                }
            }
        }

        bottom = hbox {
            //TODO Prevent from being squeezed out of view
            checkbox {
                controller.isDrawingLinesProperty.bind(selectedProperty())
            }
            label("Line drawing mode")
        }
    }
}
