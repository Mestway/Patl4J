package panels;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import borderlayout.BorderData;
import borderlayout.BorderLayout;
import utility.Utilities;

public class RectangleMeasurePanel extends Composite{

	private Text lengthText;
	private Text widthText;
	private Text thicknessText;
	private Text ambientTempText;
	private Text baseTempText;

	protected void checkSubclass(){
		
	}
	
	public RectangleMeasurePanel(Composite parent, int style){
		super(parent, style);
		this.setLayout(new BorderLayout());

		Composite panelLeft = new Composite(this, SWT.BORDER);
		panelLeft.setLayout(new BorderLayout());
		panelLeft.setLayoutData(BorderData.WEST);
		Composite panelRight = new Composite(this, SWT.BORDER);
		panelRight.setLayout(new BorderLayout());
		panelRight.setLayoutData(BorderData.EAST);
		
		Composite leftLabels = new Composite(panelLeft, SWT.BORDER);
		GridLayout gridLayout0 = new GridLayout(1, true);
		gridLayout0.horizontalSpacing = 1;
		gridLayout0.verticalSpacing = 1;
		leftLabels.setLayout(gridLayout0);
		leftLabels.setLayoutData(BorderData.WEST);
		
		Composite leftFields = new Composite(panelLeft, SWT.NULL);
		GridLayout gridLayout1 = new GridLayout(1, true);
		gridLayout1.horizontalSpacing = 1;
		gridLayout1.verticalSpacing = 1;
		leftFields.setLayout(gridLayout1);
		leftFields.setLayoutData(BorderData.EAST);
		
		Label lengthLabel = new Label(leftLabels, SWT.NULL);
		lengthLabel.setText("Length");
		lengthText = new Text(leftFields, SWT.NULL);
		lengthText.setTextLimit(10);
		lengthText.addPaintListener(Utilities.normalBorder);
		lengthText.addFocusListener(Utilities.focusListener);
		
		Label widthLabel = new Label(leftLabels, SWT.NULL);
		widthLabel.setText("Width");
		widthText = new Text(leftFields, SWT.NULL);
		widthText.setTextLimit(10);
		widthText.addPaintListener(Utilities.normalBorder);
		widthText.addFocusListener(Utilities.focusListener);

		Label thicknessLabel = new Label(leftLabels, SWT.NULL);
		thicknessLabel.setText("Length");
		thicknessText = new Text(leftFields, SWT.NULL);
		thicknessText.setTextLimit(10);
		thicknessText.addPaintListener(Utilities.normalBorder);
		thicknessText.addFocusListener(Utilities.focusListener);

		Composite rightLabels = new Composite(panelRight, SWT.BORDER);
		GridLayout gridLayout2 = new GridLayout(1, true);
		gridLayout2.horizontalSpacing = 1;
		gridLayout2.verticalSpacing = 1;
		rightLabels.setLayout(gridLayout2);
		rightLabels.setLayoutData(BorderData.WEST);
		
		Composite rightFields = new Composite(panelRight, SWT.BORDER);
		GridLayout gridLayout3 = new GridLayout(1, true);
		gridLayout3.horizontalSpacing = 1;
		gridLayout3.verticalSpacing = 1;
		rightFields.setLayout(gridLayout3);
		rightFields.setLayoutData(BorderData.EAST);

		Label ambientTempLabel = new Label(rightLabels, SWT.NULL);
		ambientTempLabel.setText("Ambient Temperature");
		
		ambientTempText = new Text(rightFields, SWT.NULL);
		ambientTempText.setTextLimit(10);
		ambientTempText.addPaintListener(Utilities.normalBorder);
		ambientTempText.addFocusListener(Utilities.focusListener);

		Label baseTempLabel = new Label(rightLabels, SWT.NULL);
		baseTempLabel.setText("Base Temperature");
		baseTempText = new Text(rightFields, SWT.NULL);
		baseTempText.setTextLimit(10);
		baseTempText.addPaintListener(Utilities.normalBorder);
		baseTempText.addFocusListener(Utilities.focusListener);

	}
	
	public double getLength(){
		return Double.parseDouble(lengthText.getText());
	}
	
	public double getWidthF(){
		return Double.parseDouble(widthText.getText());
	}
	
	public double getThickness(){
		return Double.parseDouble(thicknessText.getText());
	}
	
	public double getAmbientTemp(){
		return Double.parseDouble(ambientTempText.getText());
	}
	
	public double getBaseTemp(){
		return Double.parseDouble(baseTempText.getText());
	};
	
	public void setLengthFocus(FocusListener focusListener){
		lengthText.addFocusListener(focusListener);
	}
	
	public void setWidthFocus(FocusListener focusListener){
		widthText.addFocusListener(focusListener);
	}
	
	public void setThicknessFocus(FocusListener focusListener){
		thicknessText.addFocusListener(focusListener);
	}
}
