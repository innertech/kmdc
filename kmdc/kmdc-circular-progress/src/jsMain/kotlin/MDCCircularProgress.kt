package dev.petuska.kmdc.circular.progress

import androidx.compose.runtime.Composable
import dev.petuska.kmdc.core.Builder
import dev.petuska.kmdc.core.MDCDsl
import dev.petuska.kmdc.core.MDCSideEffect
import dev.petuska.kmdc.core.aria
import dev.petuska.kmdc.core.initialiseMDC
import dev.petuska.kmdc.core.role
import org.jetbrains.compose.web.ExperimentalComposeWebSvgApi
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.AttrBuilderContext
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.svg.Circle
import org.jetbrains.compose.web.svg.Svg
import org.w3c.dom.HTMLDivElement

@JsModule("@material/circular-progress/dist/mdc.circular-progress.css")
private external val MDCCircularProgressCSS: dynamic

public data class MDCCircularProgressOpts(
  /**
   * Value capped between 0.0 and 1.0
   */
  var progress: Double = 0.0,
  var determinate: Boolean = false,
  var closed: Boolean = false,
  var label: String? = null,
  var size: Int = 48,
  var fourColor: Boolean = false,
)

/**
 * [JS API](https://github.com/material-components/material-components-web/tree/v13.0.0/packages/mdc-circular-progress)
 */
@MDCDsl
@Composable
public fun MDCCircularProgress(
  opts: Builder<MDCCircularProgressOpts>? = null,
  attrs: AttrBuilderContext<HTMLDivElement>? = null,
) {
  MDCCircularProgressCSS
  val options = MDCCircularProgressOpts().apply {
    opts?.invoke(this)
    progress = progress.coerceIn(0.0, 1.0)
  }
  Div(attrs = {
    classes("mdc-circular-progress")
    role("progressbar")
    aria("valuemin", 0)
    aria("valuemax", 1)
    aria("valuenow", options.progress)
    options.label?.let { attr("aria-label", it) }
    if (!options.determinate) classes("mdc-circular-progress--indeterminate")
    if (options.closed) classes("mdc-circular-progress--closed")

    style {
      width(options.size.px)
      height(options.size.px)
    }
    initialiseMDC(MDCCircularProgressModule.MDCCircularProgress::attachTo) {
      progress = options.progress
      determinate = options.determinate
    }
    attrs?.invoke(this)
  }) {
    MDCSideEffect(options.determinate, MDCCircularProgressModule.MDCCircularProgress::determinate)
    MDCSideEffect(options.progress, MDCCircularProgressModule.MDCCircularProgress::progress)
    MDCSideEffect<MDCCircularProgressModule.MDCCircularProgress>(options.size) {
      foundation.init()
      progress = options.progress
    }
    MDCCircularProgressDeterminateContainer(options.size)
    MDCCircularProgressIndeterminateContainer(options.size, options.fourColor)
  }
}

@OptIn(ExperimentalComposeWebSvgApi::class)
@MDCDsl
@Composable
private fun MDCCircularProgressDeterminateContainer(size: Int) {
  val cSize = size / 2
  val rSize = size / 2.666666667
  val daSize = size * 2.3561875
  val sSize = size / 12.0
  Div({ classes("mdc-circular-progress__determinate-container") }) {
    Svg(
      viewBox = "0 0 $size $size",
      attrs = { classes("mdc-circular-progress__determinate-circle-graphic") }
    ) {
      Circle(cx = cSize, cy = cSize, r = rSize, attrs = {
        classes("mdc-circular-progress__determinate-track")
        attr("stroke-width", "$sSize")
      })
      Circle(cx = cSize, cy = cSize, r = rSize, attrs = {
        classes("mdc-circular-progress__determinate-circle")
        attr("stroke-dasharray", "$daSize")
        attr("stroke-dashoffset", "$daSize")
        attr("stroke-width", "$sSize")
      })
    }
  }
}

@MDCDsl
@Composable
private fun MDCCircularProgressIndeterminateContainer(size: Int, fourColor: Boolean) {
  Div({ classes("mdc-circular-progress__indeterminate-container") }) {
    if (fourColor) {
      repeat(4) {
        MDCCircularProgressSpinnerLayer(size, it + 1)
      }
    } else {
      MDCCircularProgressSpinnerLayer(size, null)
    }
  }
}

@MDCDsl
@Composable
private fun MDCCircularProgressSpinnerLayer(size: Int, color: Int?) {
  val sSize1 = size / 12.0
  val sSize2 = size / 15.0
  Div({
    classes("mdc-circular-progress__spinner-layer")
    color?.let { classes("mdc-circular-progress__color-$color") }
  }) {
    Div({ classes("mdc-circular-progress__circle-clipper", "mdc-circular-progress__circle-left") }) {
      MDCCircularProgressIndeterminateGraphic(size, sSize1)
    }
    Div({ classes("mdc-circular-progress__gap-patch") }) {
      MDCCircularProgressIndeterminateGraphic(size, sSize2)
    }
    Div({ classes("mdc-circular-progress__circle-clipper", "mdc-circular-progress__circle-right") }) {
      MDCCircularProgressIndeterminateGraphic(size, sSize1)
    }
  }
}

@MDCDsl
@Composable
@OptIn(ExperimentalComposeWebSvgApi::class)
private fun MDCCircularProgressIndeterminateGraphic(size: Int, sSize: Number) {
  val cSize = size / 2.0
  val rSize = size / 2.666666667
  val daSize = size * 2.3561875
  val doSize = size * 1.178104167
  Svg(
    viewBox = "0 0 $size $size",
    attrs = { classes("mdc-circular-progress__indeterminate-circle-graphic") }
  ) {
    Circle(cx = cSize, cy = cSize, r = rSize, attrs = {
      attr("stroke-dasharray", "$daSize")
      attr("stroke-dashoffset", "$doSize")
      attr("stroke-width", "$sSize")
    })
  }
}
