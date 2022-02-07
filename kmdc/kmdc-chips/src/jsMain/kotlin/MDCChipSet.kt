package dev.petuska.kmdc.chips

import androidx.compose.runtime.Composable
import dev.petuska.kmdc.core.*
import org.jetbrains.compose.web.attributes.AttrsBuilder
import org.jetbrains.compose.web.dom.ElementScope
import org.jetbrains.compose.web.dom.Span
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLSpanElement

@JsModule("@material/chips/dist/mdc.chips.css")
private external val MDCChipStyle: dynamic

public data class MDCChipSetOpts(
  var interactionPattern: InteractionPattern = InteractionPattern.Grid,
  var multiSelectable: Boolean = true,
  var overflow: Boolean = false
) {
  public enum class InteractionPattern(public val role: String) {
    Grid("grid"),
    ListBox("listbox")
  }
}

public class MDCChipSetAttrsScope private constructor() : AttrsBuilder<HTMLSpanElement>()

public class MDCChipSetScope(scope: ElementScope<HTMLElement>) : ElementScope<HTMLElement> by scope

/**
 * [JS API](https://github.com/material-components/material-components-web/tree/v13.0.0/packages/mdc-chips/chip-set)
 */
@MDCDsl
@Composable
public fun MDCChipSet(
  opts: Builder<MDCChipSetOpts>? = null,
  attrs: Builder<MDCChipSetAttrsScope>? = null,
  content: ComposableBuilder<MDCChipSetScope>? = null,
) {
  MDCChipStyle
  val options = MDCChipSetOpts().apply { opts?.invoke(this) }
  Span(attrs = {
    classes(
      "mdc-evolution-chip-set",
      // "mdc-chip-set"
    )
    role(options.interactionPattern.role)
    if (options.interactionPattern == MDCChipSetOpts.InteractionPattern.ListBox) {
      attr("aria-orientation", "horizontal")
      attr("aria-multiselectable", if (options.multiSelectable) "true" else "false")
    }
    initialiseMDC(MDCChipSetModule.MDCChipSet::attachTo)
    attrs?.invoke(this.unsafeCast<MDCChipSetAttrsScope>())
  }) {
    Span(attrs = {
      classes("mdc-evolution-chip-set__chips")
      if (options.overflow) classes("mdc-evolution-chip-set--overflow")
      role("presentation")
    }, content = content?.let { { MDCChipSetScope(this).it() } })
  }
}
