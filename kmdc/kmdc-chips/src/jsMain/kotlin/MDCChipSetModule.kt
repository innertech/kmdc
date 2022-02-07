package dev.petuska.kmdc.chips

import dev.petuska.kmdc.core.MDCBaseModule
import dev.petuska.kmdc.core.MDCEvent
import org.w3c.dom.Element

@JsModule("@material/chips")
public external object MDCChipSetModule {
  public class MDCChipSet(element: Element) : MDCBaseModule.MDCComponent<dynamic> {
    public companion object {
      public fun attachTo(element: Element): MDCChipSet
    }
  }

  public object MDCChipSetEvents {
    public val INTERACTION: String
    public val REMOVAL: String
    public val SELECTION: String
  }

  public class MDCChipSetInteractionEventDetail {
    public val chipID: String
    public val chipIndex: Int
  }

  public class MDCChipSetSelectionEventDetail {
    public val chipID: String
    public val chipIndex: Int
    public val isSelected: Boolean
  }

  public class MDCShipSetRemovalEventDetail {
    public val chipID: String
    public val chipIndex: Int
    public val isComplete: Boolean
  }

  public class MDCChipSetInteractionEvent : MDCEvent<MDCChipSetInteractionEventDetail>
  public class MDCChipSetSelectionEvent : MDCEvent<MDCChipSetSelectionEventDetail>
  public class MDCChipSetRemovalEvent : MDCEvent<MDCShipSetRemovalEventDetail>
}
