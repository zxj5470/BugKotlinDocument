package com.github.zxj5470.bugktdoc.options;

import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public abstract class BugKtDocConfigureForm implements Configurable {
	protected @NotNull
	JPanel mainPanel;
	protected @NotNull
	JCheckBox useBugKtDoc;
	protected @NotNull
	JCheckBox showUnitTypeDefault;
	protected @NotNull
	JCheckBox showClassFieldProperty;
	protected @NotNull
	JCheckBox showConstructor;
}
