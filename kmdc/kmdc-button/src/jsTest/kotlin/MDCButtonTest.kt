package dev.petuska.kmdc.button

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.jetbrains.compose.web.testutils.runTest
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLElement
import kotlin.test.Test
import kotlin.test.assertEquals

class MDCButtonTest {
  @Test
  fun render() = runTest {
    fun HTMLElement.assertHtml(count: Int, upgraded: Boolean) {
      assertEquals(
        expected = """<button class="mdc-button${
        if (upgraded) " mdc-ripple-upgraded" else ""
        }"><span class="mdc-button__ripple"></span>Clicked $count times</button>""",
        actual = innerHTML
      )
    }

    var count by mutableStateOf(0)
    composition {
      MDCButton("Clicked $count times", attrs = { onClick { count++ } })
    }
    root.assertHtml(0, false)
    root.firstElementChild.unsafeCast<HTMLButtonElement>().click()
    waitForRecompositionComplete()
    root.assertHtml(1, true)
    count = 10
    waitForRecompositionComplete()
    root.assertHtml(10, true)
  }
}
