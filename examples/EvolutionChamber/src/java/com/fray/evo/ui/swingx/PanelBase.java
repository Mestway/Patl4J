package com.fray.evo.ui.swingx;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
public abstract class PanelBase extends JPanel {
  private static final long serialVersionUID=4799832117789414417L;
  private final EcSwingX parent;
  /** 
 * Constructor.
 * @param parent the window that holds this panel.
 * @param layout
 */
  protected PanelBase(  final EcSwingX parent,  LayoutManager layout){
    super(layout);
    this.parent=parent;
  }
  protected JPanel addRadioButtonBox(  String title,  String[] captions,  int defaultSelected,  final CustomActionListener a){
    GridBagConstraints gridBagConstraints;
    gridBagConstraints=new GridBagConstraints();
    gridBagConstraints.fill=GridBagConstraints.HORIZONTAL;
    gridBagConstraints.gridy=parent.gridy;
    gridBagConstraints.gridwidth=2;
    int genVar4167;
    genVar4167=1;
    int genVar4168;
    genVar4168=1;
    int genVar4169;
    genVar4169=1;
    int genVar4170;
    genVar4170=1;
    gridBagConstraints.insets=new Insets(genVar4167,genVar4168,genVar4169,genVar4170);
    JRadioButton[] buttons;
    buttons=new JRadioButton[captions.length];
    ButtonGroup group;
    group=new ButtonGroup();
    JPanel radioButtonBox;
    radioButtonBox=new JPanel();
    javax.swing.border.TitledBorder genVar4171;
    genVar4171=BorderFactory.createTitledBorder(title);
    radioButtonBox.setBorder(genVar4171);
    int i=0;
    for (; i < buttons.length; i++) {
      java.lang.String genVar4172;
      genVar4172=captions[i];
      buttons[i]=new JRadioButton(genVar4172);
      javax.swing.JRadioButton genVar4173;
      genVar4173=buttons[i];
      genVar4173.addActionListener(a);
      javax.swing.JRadioButton genVar4174;
      genVar4174=buttons[i];
      parent.inputControls.add(genVar4174);
      javax.swing.JRadioButton genVar4175;
      genVar4175=buttons[i];
      group.add(genVar4175);
      boolean genVar4176;
      genVar4176=i == defaultSelected;
      if (genVar4176) {
        javax.swing.JRadioButton genVar4177;
        genVar4177=buttons[i];
        boolean genVar4178;
        genVar4178=true;
        genVar4177.setSelected(genVar4178);
      }
 else {
        ;
      }
      javax.swing.JRadioButton genVar4179;
      genVar4179=buttons[i];
      radioButtonBox.add(genVar4179);
    }
    PanelBase genVar4180;
    genVar4180=this;
    genVar4180.add(radioButtonBox,gridBagConstraints);
    return radioButtonBox;
  }
  protected JCheckBox addCheck(  String name,  final CustomActionListener a){
    GridBagConstraints gridBagConstraints;
    final JCheckBox checkBox;
    checkBox=new JCheckBox();
    checkBox.setText(name);
    gridBagConstraints=new GridBagConstraints();
    gridBagConstraints.anchor=GridBagConstraints.WEST;
    gridBagConstraints.fill=GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx=.5;
    int genVar4181;
    genVar4181=name.length();
    int genVar4182;
    genVar4182=2;
    boolean genVar4183;
    genVar4183=genVar4181 == genVar4182;
    if (genVar4183) {
      gridBagConstraints.gridwidth=1;
    }
 else {
      gridBagConstraints.gridwidth=2;
    }
    gridBagConstraints.gridy=parent.gridy;
    int genVar4184;
    genVar4184=1;
    int genVar4185;
    genVar4185=1;
    int genVar4186;
    genVar4186=1;
    int genVar4187;
    genVar4187=1;
    gridBagConstraints.insets=new Insets(genVar4184,genVar4185,genVar4186,genVar4187);
    PanelBase genVar4188;
    genVar4188=this;
    genVar4188.add(checkBox,gridBagConstraints);
    checkBox.addActionListener(a);
    ChangeListener genVar4189;
    genVar4189=new ChangeListener(){
      @Override public void stateChanged(      ChangeEvent arg0){
        a.actionPerformed(new ActionEvent(checkBox,0,"changed"));
      }
    }
;
    checkBox.addChangeListener(genVar4189);
    parent.inputControls.add(checkBox);
    return checkBox;
  }
}
