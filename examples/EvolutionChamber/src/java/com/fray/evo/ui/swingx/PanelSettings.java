package com.fray.evo.ui.swingx;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import static com.fray.evo.ui.swingx.EcSwingXMain.messages;
public class PanelSettings extends PanelBase {
  private static final long serialVersionUID=331188014877319644L;
  /** 
 * Constructor.
 * @param parent the window that holds this panel.
 */
  public PanelSettings(  final EcSwingX parent){
    super(parent,new GridBagLayout());
{
      java.lang.String genVar4191;
      genVar4191="settings.workerParity.none";
      java.lang.String genVar4192;
      genVar4192=messages.getString(genVar4191);
      java.lang.String genVar4193;
      genVar4193="settings.workerParity.untilSaturation";
      java.lang.String genVar4194;
      genVar4194=messages.getString(genVar4193);
      java.lang.String genVar4195;
      genVar4195="settings.workerParity.allowOverdroning";
      java.lang.String genVar4196;
      genVar4196=messages.getString(genVar4195);
      final String[] radioButtonCaptions={genVar4192,genVar4194,genVar4196};
      int defaultSelected;
      int genVar4197;
      genVar4197=parent.destination.size();
      int genVar4198;
      genVar4198=1;
      int genVar4199;
      genVar4199=genVar4197 - genVar4198;
      com.fray.evo.EcState genVar4200;
      genVar4200=parent.destination.get(genVar4199);
      com.fray.evo.EcSettings genVar4201;
      genVar4201=genVar4200.settings;
      boolean genVar4202;
      genVar4202=genVar4201.overDrone;
      if (genVar4202) {
        defaultSelected=1;
      }
 else {
        int genVar4203;
        genVar4203=parent.destination.size();
        int genVar4204;
        genVar4204=1;
        int genVar4205;
        genVar4205=genVar4203 - genVar4204;
        com.fray.evo.EcState genVar4206;
        genVar4206=parent.destination.get(genVar4205);
        com.fray.evo.EcSettings genVar4207;
        genVar4207=genVar4206.settings;
        boolean genVar4208;
        genVar4208=genVar4207.workerParity;
        if (genVar4208) {
          defaultSelected=2;
        }
 else {
          defaultSelected=0;
        }
      }
      PanelSettings genVar4209;
      genVar4209=this;
      java.lang.String genVar4210;
      genVar4210="settings.workerParity";
      java.lang.String genVar4211;
      genVar4211=messages.getString(genVar4210);
      CustomActionListener genVar4212;
      genVar4212=new CustomActionListener(){
        public void actionPerformed(        ActionEvent e){
          if (parent.getSelected(e).equals(radioButtonCaptions[1])) {
            parent.destination.get(parent.destination.size() - 1).settings.workerParity=true;
            parent.destination.get(parent.destination.size() - 1).settings.overDrone=false;
          }
 else           if (parent.getSelected(e).equals(radioButtonCaptions[2])) {
            parent.destination.get(parent.destination.size() - 1).settings.workerParity=false;
            parent.destination.get(parent.destination.size() - 1).settings.overDrone=true;
          }
 else {
            parent.destination.get(parent.destination.size() - 1).settings.workerParity=false;
            parent.destination.get(parent.destination.size() - 1).settings.overDrone=false;
          }
        }
        @Override void reverse(        Object o){
        }
      }
;
      genVar4209.addRadioButtonBox(genVar4211,radioButtonCaptions,defaultSelected,genVar4212);
      parent.gridy++;
    }
    PanelSettings genVar4213;
    genVar4213=this;
    java.lang.String genVar4214;
    genVar4214="settings.useExtractorTrick";
    java.lang.String genVar4215;
    genVar4215=messages.getString(genVar4214);
    CustomActionListener genVar4216;
    genVar4216=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        parent.destination.get(parent.destination.size() - 1).settings.useExtractorTrick=parent.getTrue(e);
      }
      @Override void reverse(      Object o){
        JCheckBox c=(JCheckBox)o;
        c.setSelected(parent.destination.get(parent.destination.size() - 1).settings.useExtractorTrick);
      }
    }
;
    javax.swing.JCheckBox genVar4217;
    genVar4217=genVar4213.addCheck(genVar4215,genVar4216);
    int genVar4218;
    genVar4218=parent.destination.size();
    int genVar4219;
    genVar4219=1;
    int genVar4220;
    genVar4220=genVar4218 - genVar4219;
    com.fray.evo.EcState genVar4221;
    genVar4221=parent.destination.get(genVar4220);
    com.fray.evo.EcSettings genVar4222;
    genVar4222=genVar4221.settings;
    boolean genVar4223;
    genVar4223=genVar4222.useExtractorTrick;
    genVar4217.setSelected(genVar4223);
    parent.gridy++;
    PanelSettings genVar4224;
    genVar4224=this;
    java.lang.String genVar4225;
    genVar4225="settings.pullWorkersFromGas";
    java.lang.String genVar4226;
    genVar4226=messages.getString(genVar4225);
    CustomActionListener genVar4227;
    genVar4227=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        parent.destination.get(parent.destination.size() - 1).settings.pullWorkersFromGas=parent.getTrue(e);
      }
      @Override void reverse(      Object o){
        JCheckBox c=(JCheckBox)o;
        c.setSelected(parent.destination.get(parent.destination.size() - 1).settings.pullWorkersFromGas);
      }
    }
;
    javax.swing.JCheckBox genVar4228;
    genVar4228=genVar4224.addCheck(genVar4226,genVar4227);
    int genVar4229;
    genVar4229=parent.destination.size();
    int genVar4230;
    genVar4230=1;
    int genVar4231;
    genVar4231=genVar4229 - genVar4230;
    com.fray.evo.EcState genVar4232;
    genVar4232=parent.destination.get(genVar4231);
    com.fray.evo.EcSettings genVar4233;
    genVar4233=genVar4232.settings;
    boolean genVar4234;
    genVar4234=genVar4233.useExtractorTrick;
    genVar4228.setSelected(genVar4234);
    parent.gridy++;
    PanelSettings genVar4235;
    genVar4235=this;
    java.lang.String genVar4236;
    genVar4236="settings.pullThreeWorkersTogether";
    java.lang.String genVar4237;
    genVar4237=messages.getString(genVar4236);
    CustomActionListener genVar4238;
    genVar4238=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        parent.destination.get(parent.destination.size() - 1).settings.pullThreeWorkersOnly=parent.getTrue(e);
      }
      @Override void reverse(      Object o){
        JCheckBox c=(JCheckBox)o;
        c.setSelected(parent.destination.get(parent.destination.size() - 1).settings.pullThreeWorkersOnly);
      }
    }
;
    javax.swing.JCheckBox genVar4239;
    genVar4239=genVar4235.addCheck(genVar4237,genVar4238);
    int genVar4240;
    genVar4240=parent.destination.size();
    int genVar4241;
    genVar4241=1;
    int genVar4242;
    genVar4242=genVar4240 - genVar4241;
    com.fray.evo.EcState genVar4243;
    genVar4243=parent.destination.get(genVar4242);
    com.fray.evo.EcSettings genVar4244;
    genVar4244=genVar4243.settings;
    boolean genVar4245;
    genVar4245=genVar4244.pullThreeWorkersOnly;
    genVar4239.setSelected(genVar4245);
    parent.gridy++;
    PanelSettings genVar4246;
    genVar4246=this;
    java.lang.String genVar4247;
    genVar4247="settings.avoidMiningGas";
    java.lang.String genVar4248;
    genVar4248=messages.getString(genVar4247);
    CustomActionListener genVar4249;
    genVar4249=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        parent.destination.get(parent.destination.size() - 1).settings.avoidMiningGas=parent.getTrue(e);
      }
      @Override void reverse(      Object o){
        JCheckBox c=(JCheckBox)o;
        c.setSelected(parent.destination.get(parent.destination.size() - 1).settings.avoidMiningGas);
      }
    }
;
    javax.swing.JCheckBox genVar4250;
    genVar4250=genVar4246.addCheck(genVar4248,genVar4249);
    int genVar4251;
    genVar4251=parent.destination.size();
    int genVar4252;
    genVar4252=1;
    int genVar4253;
    genVar4253=genVar4251 - genVar4252;
    com.fray.evo.EcState genVar4254;
    genVar4254=parent.destination.get(genVar4253);
    com.fray.evo.EcSettings genVar4255;
    genVar4255=genVar4254.settings;
    boolean genVar4256;
    genVar4256=genVar4255.avoidMiningGas;
    genVar4250.setSelected(genVar4256);
    parent.gridy++;
    com.fray.evo.ui.swingx.PanelSettings genVar4257;
    genVar4257=this;
    java.lang.String genVar4258;
    genVar4258="settings.maxExtractorTrickSupply";
    java.lang.String genVar4259;
    genVar4259=messages.getString(genVar4258);
    java.lang.Class<com.fray.evo.ui.swingx.NumberTextField> genVar4260;
    genVar4260=NumberTextField.class;
    CustomActionListener genVar4261;
    genVar4261=new CustomActionListener(){
      @Override public void actionPerformed(      ActionEvent e){
        parent.destination.get(parent.destination.size() - 1).settings.maximumExtractorTrickSupply=parent.getDigit(e);
      }
      @Override void reverse(      Object o){
        JTextField c=(JTextField)o;
        c.setText(Integer.toString(parent.destination.get(parent.destination.size() - 1).settings.maximumExtractorTrickSupply));
      }
    }
;
    javax.swing.JTextField genVar4262;
    genVar4262=parent.addInput(genVar4257,genVar4259,genVar4260,genVar4261);
    java.lang.String genVar4263;
    genVar4263="200";
    genVar4262.setText(genVar4263);
    parent.gridy++;
    com.fray.evo.ui.swingx.PanelSettings genVar4264;
    genVar4264=this;
    java.lang.String genVar4265;
    genVar4265="settings.minPoolSupply";
    java.lang.String genVar4266;
    genVar4266=messages.getString(genVar4265);
    java.lang.Class<com.fray.evo.ui.swingx.NumberTextField> genVar4267;
    genVar4267=NumberTextField.class;
    CustomActionListener genVar4268;
    genVar4268=new CustomActionListener(){
      @Override public void actionPerformed(      ActionEvent e){
        parent.destination.get(parent.destination.size() - 1).settings.minimumPoolSupply=parent.getDigit(e);
      }
      @Override void reverse(      Object o){
        JTextField c=(JTextField)o;
        c.setText(Integer.toString(parent.destination.get(parent.destination.size() - 1).settings.minimumPoolSupply));
      }
    }
;
    javax.swing.JTextField genVar4269;
    genVar4269=parent.addInput(genVar4264,genVar4266,genVar4267,genVar4268);
    java.lang.String genVar4270;
    genVar4270="2";
    genVar4269.setText(genVar4270);
    parent.gridy++;
    com.fray.evo.ui.swingx.PanelSettings genVar4271;
    genVar4271=this;
    java.lang.String genVar4272;
    genVar4272="settings.minExtractorSupply";
    java.lang.String genVar4273;
    genVar4273=messages.getString(genVar4272);
    java.lang.Class<com.fray.evo.ui.swingx.NumberTextField> genVar4274;
    genVar4274=NumberTextField.class;
    CustomActionListener genVar4275;
    genVar4275=new CustomActionListener(){
      @Override public void actionPerformed(      ActionEvent e){
        parent.destination.get(parent.destination.size() - 1).settings.minimumExtractorSupply=parent.getDigit(e);
      }
      @Override void reverse(      Object o){
        JTextField c=(JTextField)o;
        c.setText(Integer.toString(parent.destination.get(parent.destination.size() - 1).settings.minimumExtractorSupply));
      }
    }
;
    javax.swing.JTextField genVar4276;
    genVar4276=parent.addInput(genVar4271,genVar4273,genVar4274,genVar4275);
    java.lang.String genVar4277;
    genVar4277="2";
    genVar4276.setText(genVar4277);
    parent.gridy++;
    com.fray.evo.ui.swingx.PanelSettings genVar4278;
    genVar4278=this;
    java.lang.String genVar4279;
    genVar4279="settings.minHatcherySupply";
    java.lang.String genVar4280;
    genVar4280=messages.getString(genVar4279);
    java.lang.Class<com.fray.evo.ui.swingx.NumberTextField> genVar4281;
    genVar4281=NumberTextField.class;
    CustomActionListener genVar4282;
    genVar4282=new CustomActionListener(){
      @Override public void actionPerformed(      ActionEvent e){
        parent.destination.get(parent.destination.size() - 1).settings.minimumHatcherySupply=parent.getDigit(e);
      }
      @Override void reverse(      Object o){
        JTextField c=(JTextField)o;
        c.setText(Integer.toString(parent.destination.get(parent.destination.size() - 1).settings.minimumHatcherySupply));
      }
    }
;
    javax.swing.JTextField genVar4283;
    genVar4283=parent.addInput(genVar4278,genVar4280,genVar4281,genVar4282);
    java.lang.String genVar4284;
    genVar4284="2";
    genVar4283.setText(genVar4284);
  }
}
