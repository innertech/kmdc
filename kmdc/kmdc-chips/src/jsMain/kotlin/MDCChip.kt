package dev.petuska.kmdc.chips

import MDCChipLeadingActionScope
import MDCChipTrailingActionScope
import androidx.compose.runtime.Composable
import dev.petuska.kmdc.core.*
import org.jetbrains.compose.web.attributes.AttrsBuilder
import org.jetbrains.compose.web.attributes.ButtonType
import org.jetbrains.compose.web.attributes.disabled
import org.jetbrains.compose.web.attributes.type
import org.jetbrains.compose.web.dom.*
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLSpanElement

public sealed class MDCChipOpts {
  public abstract val type: Type
  public abstract var disabled: Boolean

  public enum class Type {
    Action,
    Input
  }

  public data class Action(
    public override var disabled: Boolean = false,
  ) : MDCChipOpts() {
    public override val type: Type = Type.Action
    public var leadingIcon: String? = null
  }

  public data class Input(
    public override var disabled: Boolean = false,
    public var trailingActionNavigable: Boolean = false,
    public var trailingAriaLabel: String = ""
  ) : MDCChipOpts() {
    public override val type: Type = Type.Action
  }
}

public class MDCChipScope(scope: ElementScope<HTMLSpanElement>) : ElementScope<HTMLSpanElement> by scope
public class MDCChipActionScope(scope: ElementScope<HTMLSpanElement>) : ElementScope<HTMLSpanElement> by scope

/**
 * [JS API](https://github.com/material-components/material-components-web/tree/v13.0.0/packages/mdc-chips/chip)
 */
@MDCDsl
@Composable
public fun MDCChipSetScope.MDCActionChip(
  id: String,
  opts: Builder<MDCChipOpts.Action>? = null,
  attrs: Builder<AttrsBuilder<HTMLSpanElement>>? = null,
  leadingIcon: ComposableBuilder<MDCChipLeadingActionScope>? = null,
  content: ContentBuilder<HTMLSpanElement>? = null,
) {
  val options = MDCChipOpts.Action().apply { opts?.invoke(this) }
  MDCChip(
    id = id,
    disabled = options.disabled,
    attrs = attrs
  ) {
    MDCChipCell(
      actionOrder = ActionOrder.Primary
    ) {
      MDCChipPrimaryAction(
        disabled = options.disabled,
        leadingIcon = leadingIcon,
        content = content
      )
    }
  }
}

/**
 * [JS API](https://github.com/material-components/material-components-web/tree/v13.0.0/packages/mdc-chips/chip#input-chips)
 */
@MDCDsl
@Composable
public fun MDCChipSetScope.MDCInputChip(
  id: String,
  opts: Builder<MDCChipOpts.Input>? = null,
  attrs: Builder<AttrsBuilder<HTMLSpanElement>>? = null,
  trailingIcon: ComposableBuilder<MDCChipTrailingActionScope>? = null,
  content: ContentBuilder<HTMLSpanElement>? = null
) {
  val options = MDCChipOpts.Input().apply { opts?.invoke(this) }
  MDCChip(
    id = id,
    disabled = options.disabled,
    attrs = attrs
  ) {
    MDCChipCell(
      actionOrder = ActionOrder.Primary
    ) {
      MDCChipPrimaryAction(
        disabled = options.disabled,
        content = content
      )
      trailingIcon?.let {
        MDCChipCell(
          actionOrder = ActionOrder.Trailing
        ) {
          MDCChipTrailingAction { it.invoke(this) }
        }
      }
    }
  }
}

// Internal components

private enum class ActionOrder(val classSuffix: String) {
  Primary("primary"),
  Trailing("trailing")
}

@Composable
private fun MDCChipSetScope.MDCChip(
  id: String,
  disabled: Boolean,
  attrs: Builder<AttrsBuilder<HTMLSpanElement>>? = null,
  content: ComposableBuilder<MDCChipScope>? = null,
) {
  Span(attrs = {
    id(id)
    // NB: The SaSS and JS framework use mdc-evolution-chip, but the mdc.chips.css uses mdc-chip
    // We'll define both to make sure both events and styling works, but we shuold monitor
    // the material.io project and clean-up once they fix this discrepancy
    classes(
      "mdc-evolution-chip",
      // "mdc-chip"
    )
    if (disabled) classes("mdc-evolution-chip--disabled")
    role("row")
    attrs?.invoke(this)
  }, content = { content?.let { MDCChipScope(this).it() } })
}

@Composable
private fun MDCChipScope.MDCChipCell(
  actionOrder: ActionOrder,
  attrs: Builder<AttrsBuilder<HTMLSpanElement>>? = null,
  content: ComposableBuilder<MDCChipScope>? = null,
) {
  Span(attrs = {
    classes("mdc-evolution-chip__cell", "mdc-evolution-chip__cell--${actionOrder.classSuffix}")
    role("gridcell")
    attrs?.invoke(this)
  }, content = { content?.let { MDCChipScope(this).it() } })
}

@Composable
private fun MDCChipScope.MDCChipPrimaryAction(
  deletable: Boolean = false,
  disabled: Boolean = false,
  attrs: Builder<AttrsBuilder<HTMLButtonElement>>? = null,
  leadingIcon: ComposableBuilder<MDCChipLeadingActionScope>? = null,
  content: ContentBuilder<HTMLSpanElement>? = null
) {
  Button(attrs = {
    classes(
      "mdc-evolution-chip__action",
      "mdc-evolution-chip__action--primary",
      // "mdc-chip__primary-action"
    )
    type(ButtonType.Button)
    tabIndex(if (disabled) -1 else 0)
    if (disabled) disabled()
    if (deletable) attr("data-mdc-deletable", "true")
    attrs?.invoke(this)
  }) {
    Span(attrs = {
      classes(
        "mdc-evolution-chip__ripple",
        "mdc-evolution-chip__ripple--primary",
        // "mdc-chip__ripple"
      )
    })
    leadingIcon?.let { MDCChipLeadingActionScope(this).it() }
    Span(
      attrs = {
        classes(
          "mdc-evolution-chip__text-label",
          // "mdc-chip__text"
        )
      },
      content = content
    )
  }
}

@Composable
private fun MDCChipScope.MDCChipTrailingAction(
  trailingIcon: ComposableBuilder<MDCChipTrailingActionScope>
) {
  Button(attrs = {
    classes("mdc-evolution-chip__action", "mdc-evolution-chip__action--trailing")
    type(ButtonType.Button)
    tabIndex(-1)
    attr("data-mdc-deletable", "true")
  }) {
    Span(attrs = {
      classes("mdc-evolution-chip__ripple", "mdc-evolution-chip__ripple--trailing")
    })
    trailingIcon.let { MDCChipTrailingActionScope(this).it() }
  }
}
