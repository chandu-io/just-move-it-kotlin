package io.c6.justmoveitkotlin

import org.junit.Test
import kotlin.test.assertEquals

class HelloTest {
  @Test
  fun testAssert() {
    assertEquals("Hello, world!", getHelloString())
  }
}
