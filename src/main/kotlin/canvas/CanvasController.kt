package canvas

import javafx.beans.property.SimpleBooleanProperty
import javafx.collections.FXCollections
import javafx.scene.Node
import javafx.scene.input.MouseEvent
import tornadofx.*

class CanvasController : Controller() {

    val canvas: Canvas by inject()
    val agentController: AgentPanelController by inject()

    val isDrawingLinesProperty = SimpleBooleanProperty(this, "isDrawingLines", false)
    val isDrawingLines by isDrawingLinesProperty
    var lastClickedState: State? = null

    var deltaX = 0.0
    var deltaY = 0.0

    fun handleMPress(item: State, event: MouseEvent){
        if (!isDrawingLines)
            setDragDelta(item, event)
    }

    fun setDragDelta(item: State, event: MouseEvent){
        deltaX = item.xPos - event.sceneX
        deltaY = item.yPos - event.sceneY
    }

    fun handleMDrag(item: State, event: MouseEvent) {
        if (isDrawingLines.not())
            dragItem(item, event)
    }

    fun handleDragEnd(item: State){
        if (isDrawingLines && lastClickedState != null) {
            val agents = agentController.getSelected()
            if (!agents.isEmpty()) {
                canvas.edges.add(Edge(lastClickedState!!, item, ArrayList(agents)))
            } else {
                //TODO Provide visual feedback
                println("Can't create edge without selecting agents first")
            }
        }
    }

    fun dragItem(item: State, event:MouseEvent){
        item.xPos = deltaX + event.sceneX
        item.yPos = deltaY + event.sceneY
    }

    fun startLineDrawing(item: State, node: Node) {
        if (isDrawingLines) {
            lastClickedState = item
            node.startFullDrag()
        }
    }

    fun handleCanvasClick(event: MouseEvent) {
        if (!isDrawingLines)
            addState(event)
    }

    private fun addState(event: MouseEvent) {
        //TODO Add props to state
        val posX = event.sceneX - STATE_CIRCLE_RADIUS
        val posY = event.sceneY - STATE_CIRCLE_RADIUS
        canvas.states.add(State("s${canvas.states.size + 1}", posX, posY))
    }
}