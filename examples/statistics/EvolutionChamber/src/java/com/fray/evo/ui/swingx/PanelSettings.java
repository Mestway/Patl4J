package com.fray.evo.ui.swingx;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static com.fray.evo.ui.swingx.EcSwingXMain.messages;


public class PanelSettings extends PanelBase
{
	private static final long serialVersionUID = 331188014877319644L;

	/**
	 * Constructor.
	 * @param parent the window that holds this panel.
	 */
	public PanelSettings(final EcSwingX parent)
	{
        super(parent, new GridBagLayout());

        {
            // somebody enlighten me please how this could be done easier... but
            // it works :)
            final String[] radioButtonCaptions = {messages.getString("settings.workerParity.none"), messages.getString("settings.workerParity.untilSaturation"), messages.getString("settings.workerParity.allowOverdroning")};
            final int defaultSelected;
            if (parent.destination.get(parent.destination.size()-1).settings.overDrone)
            {
                defaultSelected = 1;
            }
            else if (parent.destination.get(parent.destination.size()-1).settings.workerParity)
            {
                defaultSelected = 2;
            }
            else
            {
                defaultSelected = 0;
            }
            addRadioButtonBox(messages.getString("settings.workerParity"), radioButtonCaptions, defaultSelected,
                    new CustomActionListener()
                    {
                        public void actionPerformed(ActionEvent e)
                        {
                            if (parent.getSelected(e).equals(radioButtonCaptions[1]))
                            {
                                parent.destination.get(parent.destination.size()-1).settings.workerParity = true;
                                parent.destination.get(parent.destination.size()-1).settings.overDrone = false;
                            }
                            else if (parent.getSelected(e).equals(radioButtonCaptions[2]))
                            {
                                parent.destination.get(parent.destination.size()-1).settings.workerParity = false;
                                parent.destination.get(parent.destination.size()-1).settings.overDrone = true;
                            }
                            else
                            {
                                parent.destination.get(parent.destination.size()-1).settings.workerParity = false;
                                parent.destination.get(parent.destination.size()-1).settings.overDrone = false;
                            }
                        }

                        @Override
                        void reverse(Object o)
                        {
                            //TODO: Code this up
                        }
                    });
            parent.gridy++;
        }
        addCheck(messages.getString("settings.useExtractorTrick"), new CustomActionListener() {//Ym
            public void actionPerformed(ActionEvent e) {//Ym
                parent.destination.get(parent.destination.size() - 1).settings.useExtractorTrick = parent.getTrue(e);
            }

            @Override
            void reverse(Object o) {
                JCheckBox c = (JCheckBox) o;//Y
                c.setSelected(parent.destination.get(parent.destination.size() - 1).settings.useExtractorTrick);//Y
            }
        }).setSelected(parent.destination.get(parent.destination.size()-1).settings.useExtractorTrick);//Y
        parent.gridy++;
        addCheck(messages.getString("settings.pullWorkersFromGas"), new CustomActionListener() {//Ym
            public void actionPerformed(ActionEvent e) {//Ym
                parent.destination.get(parent.destination.size() - 1).settings.pullWorkersFromGas = parent.getTrue(e);
            }

            @Override
            void reverse(Object o) {
                JCheckBox c = (JCheckBox) o;//Y
                c.setSelected(parent.destination.get(parent.destination.size() - 1).settings.pullWorkersFromGas);//Y
            }
        }).setSelected(parent.destination.get(parent.destination.size()-1).settings.useExtractorTrick);//Y
        parent.gridy++;
        addCheck(messages.getString("settings.pullThreeWorkersTogether"), new CustomActionListener() {//Ym
            public void actionPerformed(ActionEvent e) {//Ym
                parent.destination.get(parent.destination.size() - 1).settings.pullThreeWorkersOnly = parent.getTrue(e);
            }

            @Override
            void reverse(Object o) {
                JCheckBox c = (JCheckBox) o;//Y
                c.setSelected(parent.destination.get(parent.destination.size() - 1).settings.pullThreeWorkersOnly);//Y
            }
        }).setSelected(parent.destination.get(parent.destination.size()-1).settings.pullThreeWorkersOnly);//Y
        parent.gridy++;
        addCheck(messages.getString("settings.avoidMiningGas"), new CustomActionListener() {//Ym
            public void actionPerformed(ActionEvent e) {//Ym
                parent.destination.get(parent.destination.size() - 1).settings.avoidMiningGas = parent.getTrue(e);
            }

            @Override
            void reverse(Object o) {
                JCheckBox c = (JCheckBox) o;//Y
                c.setSelected(parent.destination.get(parent.destination.size() - 1).settings.avoidMiningGas);//Y
            }
        }).setSelected(parent.destination.get(parent.destination.size()-1).settings.avoidMiningGas);//Y
        parent.gridy++;
        parent.addInput(this, messages.getString("settings.maxExtractorTrickSupply"), NumberTextField.class, new CustomActionListener()//Ym
        {
            @Override
            public void actionPerformed(ActionEvent e)//Ym
            {
                parent.destination.get(parent.destination.size()-1).settings.maximumExtractorTrickSupply = parent.getDigit(e);
            }
            @Override
            void reverse(Object o)
            {
                JTextField c = (JTextField) o;//Y
                c.setText(Integer.toString(parent.destination.get(parent.destination.size()-1).settings.maximumExtractorTrickSupply));//Y
            }
        }).setText("200");//Y
        parent.gridy++;
        parent.addInput(this, messages.getString("settings.minPoolSupply"), NumberTextField.class, new CustomActionListener()//Ym
        {
            @Override
            public void actionPerformed(ActionEvent e)//Ym
            {
                parent.destination.get(parent.destination.size()-1).settings.minimumPoolSupply = parent.getDigit(e);
            }
            @Override
            void reverse(Object o)
            {
                JTextField c = (JTextField) o;//Y
                c.setText(Integer.toString(parent.destination.get(parent.destination.size()-1).settings.minimumPoolSupply));//Y
            }
        }).setText("2");//Y
        parent.gridy++;
        parent.addInput(this, messages.getString("settings.minExtractorSupply"), NumberTextField.class, new CustomActionListener()//Ym
        {
            @Override
            public void actionPerformed(ActionEvent e)//Ym
            {
                parent.destination.get(parent.destination.size()-1).settings.minimumExtractorSupply = parent.getDigit(e);
            }
            @Override
            void reverse(Object o)
            {
                JTextField c = (JTextField) o;//Y
                c.setText(Integer.toString(parent.destination.get(parent.destination.size()-1).settings.minimumExtractorSupply));//Y
            }
        }).setText("2");//Y
        parent.gridy++;
        parent.addInput(this, messages.getString("settings.minHatcherySupply"), NumberTextField.class, new CustomActionListener()//Ym
        {
            @Override
            public void actionPerformed(ActionEvent e)//Ym
            {
                parent.destination.get(parent.destination.size()-1).settings.minimumHatcherySupply = parent.getDigit(e);

            }
            @Override
            void reverse(Object o)
            {
                JTextField c = (JTextField) o;//Y
                c.setText(Integer.toString(parent.destination.get(parent.destination.size()-1).settings.minimumHatcherySupply));//Y
            }
        }).setText("2");//Y
    }
}
