package com.durhack.sharpshot.util

import com.durhack.sharpshot.core.state.Container
import com.durhack.sharpshot.gui.container.Extract
import com.durhack.sharpshot.gui.util.CoordinateRange2D
import javafx.beans.property.SimpleObjectProperty

val globalContainer: Container = Container(DEFAULT_CONTAINER_SIZE.x, DEFAULT_CONTAINER_SIZE.y)

val globalExtractProp = SimpleObjectProperty<Extract>(null)
var globalExtract: Extract?
    get() = globalExtractProp.value
    set(value) {
        globalExtractProp.value = value
    }

val globalSelectionProp = SimpleObjectProperty<CoordinateRange2D>(null)
var globalSelection: CoordinateRange2D?
    get() = globalSelectionProp.value
    set(value) {
        globalSelectionProp.value = value
    }