package dev.petuska.kmdc.chips

import dev.petuska.kmdc.core.MDCAttrsDsl

/**
 * [JS API](https://github.com/material-components/material-components-web/tree/v13.0.0/packages/mdc-chips/chip-set)
 */
@MDCAttrsDsl
public fun MDCChipSetAttrsScope.onInteraction(listener: (event: MDCChipSetModule.MDCChipSetInteractionEvent) -> Unit) {
  addEventListener(MDCChipSetModule.MDCChipSetEvents.INTERACTION) {
    listener(it.nativeEvent.unsafeCast<MDCChipSetModule.MDCChipSetInteractionEvent>())
  }
}

@MDCAttrsDsl
public fun MDCChipSetAttrsScope.onSelection(listener: (event: MDCChipSetModule.MDCChipSetSelectionEvent) -> Unit) {
  addEventListener(MDCChipSetModule.MDCChipSetEvents.SELECTION) {
    listener(it.nativeEvent.unsafeCast<MDCChipSetModule.MDCChipSetSelectionEvent>())
  }
}

@MDCAttrsDsl
public fun MDCChipSetAttrsScope.onRemoval(listener: (event: MDCChipSetModule.MDCChipSetRemovalEvent) -> Unit) {
  addEventListener(MDCChipSetModule.MDCChipSetEvents.REMOVAL) {
    listener(it.nativeEvent.unsafeCast<MDCChipSetModule.MDCChipSetRemovalEvent>())
  }
}
