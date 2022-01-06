package dev.petuska.kmdc.menu.surface

import androidx.compose.runtime.Composable
import dev.petuska.kmdc.core.Builder
import dev.petuska.kmdc.core.MDCDsl
import org.jetbrains.compose.web.attributes.AttrsBuilder
import org.jetbrains.compose.web.dom.ContentBuilder
import org.jetbrains.compose.web.dom.Div
import org.w3c.dom.HTMLDivElement

public class MDCMenuSurfaceAnchorAttrsScope private constructor() : AttrsBuilder<HTMLDivElement>()

@MDCDsl
@Composable
public fun MDCMenuSurfaceAnchor(
  attrs: Builder<MDCMenuSurfaceAnchorAttrsScope>? = null,
  content: ContentBuilder<HTMLDivElement>? = null,
) {
  Div(
    attrs = {
      classes("mdc-menu-surface--anchor")
      attrs?.invoke(this.unsafeCast<MDCMenuSurfaceAnchorAttrsScope>())
    },
    content = content
  )
}
