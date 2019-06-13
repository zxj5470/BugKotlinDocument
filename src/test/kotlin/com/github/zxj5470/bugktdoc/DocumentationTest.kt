package com.github.zxj5470.bugktdoc

import com.intellij.codeInsight.editorActions.smartEnter.SmartEnterAction
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase
import org.junit.Test


class DocumentationTest : LightCodeInsightFixtureTestCase() {
	override fun getTestDataPath(): String = "testData"

	private fun byName(sampleName: String) {
		myFixture.configureByFiles("$sampleName.kt")
		myFixture.editor.caretModel.moveToOffset(3)
		myFixture.testAction(SmartEnterAction())
		myFixture.checkResultByFile("$sampleName.kt.txt")
	}

	@Test fun testConstructors() = byName("constructors")
	@Test fun testMain1() = byName("main1")
}