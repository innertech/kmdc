package dev.petuska.kmdc.checkbox

import androidx.compose.runtime.Composable
import dev.petuska.kmdc.core.AttrsBuilder
import dev.petuska.kmdc.core.Builder
import dev.petuska.kmdc.core.ComposableBuilder
import dev.petuska.kmdc.core.MDCDsl
import dev.petuska.kmdc.core.MDCSideEffect
import dev.petuska.kmdc.core.applyAttrs
import dev.petuska.kmdc.core.applyContent
import dev.petuska.kmdc.core.initialiseMDC
import dev.petuska.kmdc.core.mdc
import dev.petuska.kmdc.core.rememberUniqueDomElementId
import dev.petuska.kmdc.form.field.MDCFormFieldScope
import org.jetbrains.compose.web.ExperimentalComposeWebSvgApi
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.builders.InputAttrsScope
import org.jetbrains.compose.web.attributes.disabled
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.ElementScope
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.Label
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.svg.Path
import org.jetbrains.compose.web.svg.Svg
import org.w3c.dom.HTMLDivElement

@JsModule("@material/checkbox/dist/mdc.checkbox.css")
public external val MDCCheckboxStyle: dynamic

public sealed interface MDCCheckboxScope : ElementScope<HTMLDivElement>

@MDCDsl
@Composable
public fun MDCCheckbox(
  disabled: Boolean = false,
  indeterminate: Boolean = false,
  attrs: AttrsBuilder<HTMLDivElement>? = null,
  content: ComposableBuilder<MDCCheckboxScope>? = null,
) {
  MDCCheckboxStyle

  Div(attrs = {
    classes("mdc-checkbox")
    initialiseMDC(MDCCheckboxModule.MDCCheckbox::attachTo)
    applyAttrs(attrs)
  }) {
    MDCSideEffect(indeterminate, MDCCheckboxModule.MDCCheckbox::indeterminate)
    MDCSideEffect(disabled, MDCCheckboxModule.MDCCheckbox::disabled)
    applyContent(content)
  }
}

/**
 * [JS API](https://github.com/material-components/material-components-web/tree/v13.0.0/packages/mdc-checkbox)
 */
@MDCDsl
@Composable
public fun MDCCheckbox(
  checked: Boolean?,
  disabled: Boolean = false,
  label: String? = null,
  attrs: Builder<InputAttrsScope<Boolean>>? = null,
) {
  val checkboxId = rememberUniqueDomElementId()
  MDCCheckbox(
    disabled = disabled,
    indeterminate = checked == null,
  ) {
    MDCCheckboxInput(
      checked = checked,
      disabled = disabled,
      attrs = {
        id(checkboxId)
        applyAttrs(attrs)
      }
    )
    MDCCheckboxBackground()
    MDCCheckboxRipple()
  }
  label?.let {
    Label(forId = checkboxId, attrs = { id("$checkboxId-label") }) { Text(it) }
  }
}

/**
 * [JS API](https://github.com/material-components/material-components-web/tree/v13.0.0/packages/mdc-checkbox)
 */
@MDCDsl
@Composable
public fun MDCFormFieldScope.MDCCheckbox(
  checked: Boolean?,
  disabled: Boolean = false,
  label: String? = null,
  attrs: Builder<InputAttrsScope<Boolean>>? = null,
) {
  val checkboxId = rememberUniqueDomElementId()
  MDCCheckbox(
    disabled = disabled,
    indeterminate = checked == null,
  ) {
    MDCCheckboxInput(
      checked = checked,
      disabled = disabled,
      attrs = {
        ref {
          it.mdc<MDCCheckboxModule.MDCCheckbox> { setInput(it, this) }
          onDispose { }
        }
        id(checkboxId)
        applyAttrs(attrs)
      }
    )
    MDCCheckboxBackground()
    MDCCheckboxRipple()
  }
  label?.let {
    Label(forId = checkboxId, attrs = { id("$checkboxId-label") }) { Text(it) }
  }
}

@MDCDsl
@Composable
public fun MDCCheckboxScope.MDCCheckboxInput(
  checked: Boolean?,
  disabled: Boolean = false,
  attrs: Builder<InputAttrsScope<Boolean>>? = null,
) {
  // WORKAROUND https://github.com/JetBrains/compose-jb/issues/1528
  //     We cannot use the controlled CheckboxInput directly, but the workaround is functionally equivalent.
  Input(type = InputType.Checkbox, attrs = {
    classes("mdc-checkbox__native-control") // This must precede `checked()`
    checked(checked == true) // This must follow `classes(...)`
    if (disabled) disabled()
    if (checked == null) attr("data-indeterminate", "true")
    applyAttrs(attrs)
  })
}

@OptIn(ExperimentalComposeWebSvgApi::class)
@MDCDsl
@Composable
public fun MDCCheckboxScope.MDCCheckboxBackground(
  attrs: AttrsBuilder<HTMLDivElement>? = null,
) {
  Div(attrs = {
    classes("mdc-checkbox__background")
    applyAttrs(attrs)
  }) {
    Svg(attrs = {
      classes("mdc-checkbox__checkmark")
      attr("viewBox", "0 0 24 24")
    }) {
      Path(
        d = "M1.73,12.91 8.1,19.28 22.79,4.59",
        attrs = {
          classes("mdc-checkbox__checkmark-path")
          attr("fill", "none")
        }
      )
    }
    Div(attrs = { classes("mdc-checkbox__mixedmark") })
  }
}

@MDCDsl
@Composable
public fun MDCCheckboxScope.MDCCheckboxRipple(
  attrs: AttrsBuilder<HTMLDivElement>? = null
) {
  Div(attrs = {
    classes("mdc-checkbox__ripple")
    applyAttrs(attrs)
  })
}
