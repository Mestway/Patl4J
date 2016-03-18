package com.fray.evo.ui.swingx;

import static com.fray.evo.ui.swingx.EcSwingXMain.messages;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.Text;

public class PanelSettings extends PanelBase {
	private static final long serialVersionUID = 331188014877319644L;

	public PanelSettings(TabFolder parent, final EcSwingX swingX) {
		super(parent, swingX, SWT.NONE, new FormLayout());
		final String[] radioButtonCaptions = { messages.getString("settings.workerParity.none"),
				messages.getString("settings.workerParity.untilSaturation"),
				messages.getString("settings.workerParity.allowOverdroning") };
		final int defaultSelected;
		if (swingX.destination.get(swingX.destination.size() - 1).settings.overDrone) {
			defaultSelected = 1;
		} else if (swingX.destination.get(swingX.destination.size() - 1).settings.workerParity) {
			defaultSelected = 2;
		} else {
			defaultSelected = 0;
		}
		swingX.margintop = 200;
		addRadioButtonBox(messages.getString("settings.workerParity"), radioButtonCaptions, defaultSelected,
				new CustomActionListener() {

					@Override
					void reverse(Object o) {
					}

					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {

					}

					@Override
					public void widgetSelected(SelectionEvent e) {
						if (swingX.getSelected(e).equals(radioButtonCaptions[1])) {
							swingX.destination.get(swingX.destination.size() - 1).settings.workerParity = true;
							swingX.destination.get(swingX.destination.size() - 1).settings.overDrone = false;
						} else if (swingX.getSelected(e).equals(radioButtonCaptions[2])) {
							swingX.destination.get(swingX.destination.size() - 1).settings.workerParity = false;
							swingX.destination.get(swingX.destination.size() - 1).settings.overDrone = true;
						} else {
							swingX.destination.get(swingX.destination.size() - 1).settings.workerParity = false;
							swingX.destination.get(swingX.destination.size() - 1).settings.overDrone = false;
						}

					}
				});
		swingX.gridx = 0;
		swingX.margintop += 60;

		addCheck(messages.getString("settings.useExtractorTrick"), new CustomActionListener() {

			@Override
			void reverse(Object o) {
				Button b = (Button) o;
				b.setSelection(swingX.destination.get(swingX.destination.size() - 1).settings.useExtractorTrick);
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				swingX.destination.get(swingX.destination.size() - 1).settings.useExtractorTrick = swingX.getTrue(e);

			}
		}).setSelection(swingX.destination.get(swingX.destination.size() - 1).settings.useExtractorTrick);
		swingX.margintop += swingX.topIncreament;
		swingX.gridx = 0;
		addCheck(messages.getString("settings.pullWorkersFromGas"), new CustomActionListener() {

			@Override
			void reverse(Object o) {
				Button b = (Button) o;
				b.setSelection(swingX.destination.get(swingX.destination.size() - 1).settings.pullWorkersFromGas);
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				swingX.destination.get(swingX.destination.size() - 1).settings.pullWorkersFromGas = swingX.getTrue(e);

			}
		}).setSelection(swingX.destination.get(swingX.destination.size() - 1).settings.useExtractorTrick);
		swingX.gridx = 0;
		swingX.margintop += swingX.topIncreament;
		addCheck(messages.getString("settings.pullThreeWorkersTogether"), new CustomActionListener() {

			@Override
			void reverse(Object o) {
				Button b = (Button) o;
				b.setSelection(swingX.destination.get(swingX.destination.size() - 1).settings.pullThreeWorkersOnly);
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				swingX.destination.get(swingX.destination.size() - 1).settings.pullThreeWorkersOnly = swingX.getTrue(e);

			}
		}).setSelection(swingX.destination.get(swingX.destination.size() - 1).settings.pullThreeWorkersOnly);
		swingX.gridx = 0;
		swingX.margintop += swingX.topIncreament;
		addCheck(messages.getString("settings.avoidMiningGas"), new CustomActionListener() {

			@Override
			void reverse(Object o) {
				Button b = (Button) o;
				b.setSelection(swingX.destination.get(swingX.destination.size() - 1).settings.avoidMiningGas);
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				swingX.destination.get(swingX.destination.size() - 1).settings.avoidMiningGas = swingX.getTrue(e);

			}
		}).setSelection(swingX.destination.get(swingX.destination.size() - 1).settings.avoidMiningGas);
		swingX.gridx = 0;
		swingX.margintop += swingX.topIncreament;
		swingX.addInput(this, messages.getString("settings.maxExtractorTrickSupply"), NumberTextField.class,
				new CustomActionListener() {

					@Override
					void reverse(Object o) {
						Text c = (Text) o;
						c.setText(Integer.toString(swingX.destination
								.get(swingX.destination.size() - 1).settings.maximumExtractorTrickSupply));
					}

					@Override
					public void widgetSelected(SelectionEvent e) {
						swingX.destination
								.get(swingX.destination.size() - 1).settings.maximumExtractorTrickSupply = swingX
										.getDigit(e);
					}
				}).setText("200");
		swingX.gridx = 0;
		swingX.margintop += swingX.topIncreament;
		swingX.xIncreament = 49;
		swingX.addInput(this, messages.getString("settings.minPoolSupply"), NumberTextField.class,
				new CustomActionListener() {
					@Override
					void reverse(Object o) {
						Text c = (Text) o;
						c.setText(Integer.toString(
								swingX.destination.get(swingX.destination.size() - 1).settings.minimumPoolSupply));
					}

					@Override
					public void widgetSelected(SelectionEvent e) {
						swingX.destination.get(swingX.destination.size() - 1).settings.minimumPoolSupply = swingX
								.getDigit(e);
					}
				}).setText("2");
		swingX.gridx = 0;
		swingX.margintop += swingX.topIncreament;
		swingX.addInput(this, messages.getString("settings.minExtractorSupply"), NumberTextField.class,
				new CustomActionListener() {
					@Override
					void reverse(Object o) {
						Text c = (Text) o;
						c.setText(Integer.toString(
								swingX.destination.get(swingX.destination.size() - 1).settings.minimumExtractorSupply));
					}

					@Override
					public void widgetSelected(SelectionEvent e) {
						swingX.destination.get(swingX.destination.size() - 1).settings.minimumExtractorSupply = swingX
								.getDigit(e);
					}
				}).setText("2");
		swingX.gridx = 0;
		swingX.margintop += swingX.topIncreament;
		swingX.addInput(this, messages.getString("settings.minHatcherySupply"), NumberTextField.class,
				new CustomActionListener() {
					@Override
					void reverse(Object o) {
						Text c = (Text) o;
						c.setText(Integer.toString(
								swingX.destination.get(swingX.destination.size() - 1).settings.minimumHatcherySupply));
					}

					@Override
					public void widgetSelected(SelectionEvent e) {
						swingX.destination.get(swingX.destination.size() - 1).settings.minimumHatcherySupply = swingX
								.getDigit(e);
					}
				}).setText("2");
	}
}
