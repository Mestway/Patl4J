package panels;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import borderlayout.BorderData;
import borderlayout.BorderLayout;


public class AuthorPanel extends Composite {
	
	private String CREATED_BY="Created by : Momoh Ozavoshon Jamiu";
	private String TITLE="Analysis of Heat Transfer \n Through Fins";
	
	protected void checkSubclass(){
		
	}
	
	public AuthorPanel(Composite parent, int style){
		super(parent, style);
		this.setLayout(new BorderLayout());
		Text titleTextArea = new Text(this, SWT.MULTI | SWT.CENTER);
		titleTextArea.setText(TITLE);
		titleTextArea.setEditable(false);
		
		Text createdByTextArea = new Text(this, SWT.MULTI | SWT.CENTER);
		createdByTextArea.setText(CREATED_BY);
		createdByTextArea.setEditable(false);
		
		Button analyzeButton = new Button(this, SWT.PUSH);
		analyzeButton.setText("Analyze");
		analyzeButton.setAlignment(SWT.CENTER);
		
		titleTextArea.setLayoutData(BorderData.NORTH);
		createdByTextArea.setLayoutData(BorderData.CENTER);
		analyzeButton.setLayoutData(BorderData.SOUTH);
		
	}
	
	public Button getButton(){
		return (Button)this.getChildren()[2];
	}
	

}
