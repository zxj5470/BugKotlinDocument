package com.github.zxj5470.bugktdoc.options;

import com.intellij.openapi.options.Configurable;

import javax.swing.*;

public abstract class BugKtDocConfigureForm implements Configurable {
	protected JPanel mainPanel;
	protected JCheckBox useBugKtDoc;
	protected JCheckBox showUnitTypeDefault;
}
