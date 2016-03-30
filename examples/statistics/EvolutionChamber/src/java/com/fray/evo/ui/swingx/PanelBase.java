package com.fray.evo.ui.swingx;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;


public abstract class PanelBase extends JPanel
{
	private static final long serialVersionUID = 4799832117789414417L;
	private final EcSwingX parent;

    /**
	 * Constructor.
	 * @param parent the window that holds this panel.
     * @param layout
	 */
	protected PanelBase(final EcSwingX parent, LayoutManager layout)
	{
        super(layout);//Y
        this.parent = parent;
    }

    protected JPanel addRadioButtonBox(String title, String[] captions, int defaultSelected, final CustomActionListener a)
    {
        GridBagConstraints gridBagConstraints = new GridBagConstraints();//Ym
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;//Ym
        gridBagConstraints.gridy = parent.gridy;//Ym
        gridBagConstraints.gridwidth = 2;//Ym
        gridBagConstraints.insets = new Insets(1, 1, 1, 1);//Ym

        JRadioButton[] buttons = new JRadioButton[captions.length];//Ym
        ButtonGroup group = new ButtonGroup();//Ymm
        JPanel radioButtonBox = new JPanel();//Ymm
        radioButtonBox.setBorder(BorderFactory.createTitledBorder(title));//Y

        for (int i = 0; i < buttons.length; i++)
        {
            buttons[i] = new JRadioButton(captions[i]);//Ymm
            buttons[i].addActionListener(a);//Y
            parent.inputControls.add(buttons[i]);
            group.add(buttons[i]);//Ymm
            if (i == defaultSelected)
                buttons[i].setSelected(true);//Y
            radioButtonBox.add(buttons[i]);//Ymm
        }
        add(radioButtonBox, gridBagConstraints);//Ymm
        return radioButtonBox;
    }

    protected JCheckBox addCheck(String name, final CustomActionListener a)
    {
        GridBagConstraints gridBagConstraints;//Ym

        final JCheckBox checkBox = new JCheckBox();//Ymm
        checkBox.setText(name);//Y
        gridBagConstraints = new GridBagConstraints();//Ymm
        gridBagConstraints.anchor = GridBagConstraints.WEST;//Ymm
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;//Ymm
        gridBagConstraints.weightx = .5;//Ymm
        if (name.length() == 2)
            gridBagConstraints.gridwidth = 1;//Ymm
        else
            gridBagConstraints.gridwidth = 2;//Ymm
        gridBagConstraints.gridy = parent.gridy;//Ymm
        gridBagConstraints.insets = new Insets(1, 1, 1, 1);//Ymm
        add(checkBox, gridBagConstraints);//Ymm
        checkBox.addActionListener(a);//Y
        checkBox.addChangeListener(new ChangeListener()//Ym
        {
            @Override
            public void stateChanged(ChangeEvent arg0)//Ym
            {
                a.actionPerformed(new ActionEvent(checkBox, 0, "changed"));
            }
        });
        parent.inputControls.add(checkBox);
        return checkBox;
    }
}
