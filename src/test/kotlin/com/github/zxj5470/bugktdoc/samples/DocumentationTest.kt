package com.github.zxj5470.bugktdoc.samples

import com.intellij.codeInsight.editorActions.smartEnter.SmartEnterAction
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase
import org.junit.Test


class DocumentationTest : LightCodeInsightFixtureTestCase() {

	@Test
	fun testDocument() {
		myFixture.testDataPath = "testData"
		myFixture.configureByFiles("constructors.before.kt")
		myFixture.editor.caretModel.moveToOffset(3)
		myFixture.testAction(SmartEnterAction())
		myFixture.checkResultByFile("constructors.txt")
	}
}