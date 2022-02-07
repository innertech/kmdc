package local.sandbox.samples

import MDCChipActionLeadingIcon
import dev.petuska.kmdc.chips.MDCActionChip
import dev.petuska.kmdc.chips.MDCChipSet
import dev.petuska.kmdc.chips.onInteraction
import dev.petuska.kmdc.chips.onSelection
import dev.petuska.kmdcx.icons.MDCIconOpts
import local.sandbox.engine.Sample
import local.sandbox.engine.Samples
import org.jetbrains.compose.web.dom.Text

@Suppress("Unused")
private val MenuSamples = Samples("MDCChipSet") {
  Sample("Chipset Action") {
    MDCChipSet(attrs = {
      onInteraction { console.log("Chip ${it.detail.chipID} selected.") }
    }) {
      MDCActionChip(
        id = "HomeId",
        leadingIcon = {
          MDCChipActionLeadingIcon(attrs = {
            classes("material-icons")
          }) { Text(MDCIconOpts.MDCIconType.Home.iconType) } }
      ) {
        Text("Home")
      }
    }
  }
}
