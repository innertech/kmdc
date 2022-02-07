package dev.petuska.kmdc.menu

import dev.petuska.kmdc.core.MDCAttrsDsl

/**
 * [JS API](https://github.com/material-components/material-components-web/tree/v13.0.0/packages/mdc-menu)
 */
@MDCAttrsDsl
public fun MDCMenuAttrsScope.onSelected(listener: (event: MDCMenuModule.MDCMenuSelectedEvent) -> Unit) {
  addEventListener(MDCMenuModule.strings.SELECTED_EVENT) {
    listener(it.nativeEvent.unsafeCast<MDCMenuModule.MDCMenuSelectedEvent>())
  }
}
