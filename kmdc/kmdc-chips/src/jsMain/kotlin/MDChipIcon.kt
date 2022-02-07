import androidx.compose.runtime.Composable
import dev.petuska.kmdc.core.MDCDsl
import org.jetbrains.compose.web.dom.AttrBuilderContext
import org.jetbrains.compose.web.dom.ContentBuilder
import org.jetbrains.compose.web.dom.ElementScope
import org.jetbrains.compose.web.dom.Span
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLSpanElement

public class MDCChipLeadingActionScope(scope: ElementScope<HTMLButtonElement>) : ElementScope<HTMLButtonElement> by scope
public class MDCChipTrailingActionScope(scope: ElementScope<HTMLButtonElement>) : ElementScope<HTMLButtonElement> by scope

/**
 * [JS API](https://github.com/material-components/material-components-web/tree/v13.0.0/packages/mdc-chips/action)
 */
@MDCDsl
@Composable
public fun MDCChipLeadingActionScope.MDCChipActionLeadingIcon(
  attrs: AttrBuilderContext<HTMLSpanElement>? = null,
  content: ContentBuilder<HTMLSpanElement>? = null,
) {
  Span(attrs = {
    classes("mdc-evolution-chip__graphic")
  }) {
    Span(attrs = {
      classes(
        "mdc-evolution-chip__icon",
        "mdc-evolution-chip__icon--primary",
        "mdc-chip__icon",
        "mdc-chip__icon--leading"
      )
      attrs?.invoke(this)
    }, content = content)
  }
}

@MDCDsl
@Composable
public fun MDCChipTrailingActionScope.MDCChipActionTrailingIcon(
  attrs: AttrBuilderContext<HTMLSpanElement>? = null,
  content: ContentBuilder<HTMLSpanElement>? = null,
) {
  Span(attrs = {
    classes(
      "mdc-evolution-chip__icon",
      "mdc-evolution-chip__icon--trailing",
      // "mdc-chip__icon",
      // "mdc-chip__icon--trailing"
    )
    attrs?.invoke(this)
  }, content = content)
}
