import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import tornadofx.*
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class State(name: String, xPos: Double, yPos: Double, props: String = "") {

    val nameProperty = SimpleStringProperty(this, "name", name)
    var name by nameProperty

    val xProperty = SimpleDoubleProperty(this, "xPos", xPos)
    var xPos by xProperty

    val yProperty = SimpleDoubleProperty(this, "yPos", yPos)
    var yPos by yProperty

    val inEdgesProperty = SimpleListProperty<Edge>(this, "inEdges", FXCollections.observableArrayList())
    var inEdges by inEdgesProperty

    val outEdgesProperty = SimpleListProperty<Edge>(this, "outEdges", FXCollections.observableArrayList())
    var outEdges by outEdgesProperty

    val propsProperty = SimpleStringProperty(this, "props", props)
    var props by propsProperty


}

