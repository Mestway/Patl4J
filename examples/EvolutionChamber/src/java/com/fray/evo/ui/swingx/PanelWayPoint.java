package com.fray.evo.ui.swingx;
import com.fray.evo.EcState;
import com.fray.evo.util.ZergBuildingLibrary;
import com.fray.evo.util.ZergUnitLibrary;
import com.fray.evo.util.ZergUpgradeLibrary;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static com.fray.evo.ui.swingx.EcSwingXMain.messages;
public class PanelWayPoint extends PanelBase {
  private static final long serialVersionUID=-3838081126063166169L;
  private final EcState state;
  /** 
 * Constructor.
 * @param parent the window that holds this panel.
 */
  public PanelWayPoint(  final EcSwingX parent,  final EcState state){
    super(parent,new GridBagLayout());
    this.state=state;
    com.fray.evo.ui.swingx.PanelWayPoint genVar3713;
    genVar3713=this;
    java.lang.String genVar3714;
    genVar3714="waypoint.drones";
    java.lang.String genVar3715;
    genVar3715=messages.getString(genVar3714);
    java.lang.Class<com.fray.evo.ui.swingx.NumberTextField> genVar3716;
    genVar3716=NumberTextField.class;
    CustomActionListener genVar3717;
    genVar3717=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        state.setUnits(ZergUnitLibrary.Drone,EcSwingX.getDigit(e));
      }
      @Override void reverse(      Object o){
        JTextField c=(JTextField)o;
        c.setText(Integer.toString(state.getDrones()));
      }
    }
;
    parent.addInput(genVar3713,genVar3715,genVar3716,genVar3717);
    com.fray.evo.ui.swingx.PanelWayPoint genVar3718;
    genVar3718=this;
    java.lang.String genVar3719;
    genVar3719="waypoint.deadline";
    java.lang.String genVar3720;
    genVar3720=messages.getString(genVar3719);
    java.lang.Class<com.fray.evo.ui.swingx.TimeTextField> genVar3721;
    genVar3721=TimeTextField.class;
    CustomActionListener genVar3722;
    genVar3722=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        state.targetSeconds=EcSwingX.getDigit(e);
        ((JTextField)e.getSource()).setText(formatAsTime(state.targetSeconds));
      }
      void reverse(      Object o){
        JTextField c=(JTextField)o;
        c.setText(formatAsTime(state.targetSeconds));
      }
    }
;
    javax.swing.JTextField genVar3723;
    genVar3723=parent.addInput(genVar3718,genVar3720,genVar3721,genVar3722);
    PanelWayPoint genVar3724;
    genVar3724=this;
    java.lang.String genVar3725;
    genVar3725=genVar3724.formatAsTime(state.targetSeconds);
    genVar3723.setText(genVar3725);
    parent.gridy++;
    com.fray.evo.ui.swingx.PanelWayPoint genVar3726;
    genVar3726=this;
    java.lang.String genVar3727;
    genVar3727="waypoint.overlords";
    java.lang.String genVar3728;
    genVar3728=messages.getString(genVar3727);
    java.lang.Class<com.fray.evo.ui.swingx.NumberTextField> genVar3729;
    genVar3729=NumberTextField.class;
    CustomActionListener genVar3730;
    genVar3730=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        state.setUnits(ZergUnitLibrary.Overlord,EcSwingX.getDigit(e));
      }
      void reverse(      Object o){
        JTextField c=(JTextField)o;
        c.setText(Integer.toString(state.getOverlords()));
      }
    }
;
    parent.addInput(genVar3726,genVar3728,genVar3729,genVar3730);
    com.fray.evo.ui.swingx.PanelWayPoint genVar3731;
    genVar3731=this;
    java.lang.String genVar3732;
    genVar3732="waypoint.overseers";
    java.lang.String genVar3733;
    genVar3733=messages.getString(genVar3732);
    java.lang.Class<com.fray.evo.ui.swingx.NumberTextField> genVar3734;
    genVar3734=NumberTextField.class;
    CustomActionListener genVar3735;
    genVar3735=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        state.setUnits(ZergUnitLibrary.Overseer,EcSwingX.getDigit(e));
      }
      void reverse(      Object o){
        JTextField c=(JTextField)o;
        c.setText(Integer.toString(state.getOverseers()));
      }
    }
;
    parent.addInput(genVar3731,genVar3733,genVar3734,genVar3735);
    parent.gridy++;
    int genVar3736;
    genVar3736=parent.destination.size();
    int genVar3737;
    genVar3737=1;
    int genVar3738;
    genVar3738=genVar3736 - genVar3737;
    com.fray.evo.EcState genVar3739;
    genVar3739=parent.destination.get(genVar3738);
    boolean genVar3740;
    genVar3740=state == genVar3739;
    if (genVar3740) {
      com.fray.evo.ui.swingx.PanelWayPoint genVar3741;
      genVar3741=this;
      java.lang.String genVar3742;
      genVar3742="waypoint.scoutTiming";
      java.lang.String genVar3743;
      genVar3743=messages.getString(genVar3742);
      java.lang.Class<com.fray.evo.ui.swingx.TimeTextField> genVar3744;
      genVar3744=TimeTextField.class;
      CustomActionListener genVar3745;
      genVar3745=new CustomActionListener(){
        public void actionPerformed(        ActionEvent e){
          parent.destination.get(parent.destination.size() - 1).scoutDrone=EcSwingX.getDigit(e);
          ((JTextField)e.getSource()).setText(formatAsTime(state.scoutDrone));
        }
        void reverse(        Object o){
          JTextField c=(JTextField)o;
          c.setText(formatAsTime((parent.destination.get(parent.destination.size() - 1).scoutDrone)));
        }
      }
;
      javax.swing.JTextField genVar3746;
      genVar3746=parent.addInput(genVar3741,genVar3743,genVar3744,genVar3745);
      PanelWayPoint genVar3747;
      genVar3747=this;
      java.lang.String genVar3748;
      genVar3748=genVar3747.formatAsTime(state.scoutDrone);
      genVar3746.setText(genVar3748);
    }
 else {
      ;
    }
    PanelWayPoint genVar3749;
    genVar3749=this;
    java.lang.String genVar3750;
    genVar3750="waypoint.burrow";
    java.lang.String genVar3751;
    genVar3751=messages.getString(genVar3750);
    CustomActionListener genVar3752;
    genVar3752=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        if (parent.getTrue(e))         state.addUpgrade(ZergUpgradeLibrary.Burrow);
 else         state.removeUpgrade(ZergUpgradeLibrary.Burrow);
      }
      void reverse(      Object o){
        JCheckBox c=(JCheckBox)o;
        c.setSelected(state.isBurrow());
      }
    }
;
    genVar3749.addCheck(genVar3751,genVar3752);
    parent.gridy++;
    com.fray.evo.ui.swingx.PanelWayPoint genVar3753;
    genVar3753=this;
    java.lang.String genVar3754;
    genVar3754="waypoint.queens";
    java.lang.String genVar3755;
    genVar3755=messages.getString(genVar3754);
    java.lang.Class<com.fray.evo.ui.swingx.NumberTextField> genVar3756;
    genVar3756=NumberTextField.class;
    CustomActionListener genVar3757;
    genVar3757=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        state.setUnits(ZergUnitLibrary.Queen,EcSwingX.getDigit(e));
      }
      void reverse(      Object o){
        JTextField c=(JTextField)o;
        c.setText(Integer.toString(state.getQueens()));
      }
    }
;
    parent.addInput(genVar3753,genVar3755,genVar3756,genVar3757);
    PanelWayPoint genVar3758;
    genVar3758=this;
    java.lang.String genVar3759;
    genVar3759="waypoint.pneumatizedCarapace";
    java.lang.String genVar3760;
    genVar3760=messages.getString(genVar3759);
    CustomActionListener genVar3761;
    genVar3761=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        if (parent.getTrue(e))         state.addUpgrade(ZergUpgradeLibrary.PneumatizedCarapace);
 else         state.removeUpgrade(ZergUpgradeLibrary.PneumatizedCarapace);
      }
      void reverse(      Object o){
        JCheckBox c=(JCheckBox)o;
        c.setSelected(state.isPneumatizedCarapace());
      }
    }
;
    genVar3758.addCheck(genVar3760,genVar3761);
    parent.gridy++;
    com.fray.evo.ui.swingx.PanelWayPoint genVar3762;
    genVar3762=this;
    java.lang.String genVar3763;
    genVar3763="waypoint.zerglings";
    java.lang.String genVar3764;
    genVar3764=messages.getString(genVar3763);
    java.lang.Class<com.fray.evo.ui.swingx.NumberTextField> genVar3765;
    genVar3765=NumberTextField.class;
    CustomActionListener genVar3766;
    genVar3766=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        state.setUnits(ZergUnitLibrary.Zergling,EcSwingX.getDigit(e));
      }
      void reverse(      Object o){
        JTextField c=(JTextField)o;
        c.setText(Integer.toString(state.getZerglings()));
      }
    }
;
    parent.addInput(genVar3762,genVar3764,genVar3765,genVar3766);
    PanelWayPoint genVar3767;
    genVar3767=this;
    java.lang.String genVar3768;
    genVar3768="waypoint.ventralSacs";
    java.lang.String genVar3769;
    genVar3769=messages.getString(genVar3768);
    CustomActionListener genVar3770;
    genVar3770=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        if (parent.getTrue(e))         state.addUpgrade(ZergUpgradeLibrary.VentralSacs);
 else         state.removeUpgrade(ZergUpgradeLibrary.VentralSacs);
      }
      void reverse(      Object o){
        JCheckBox c=(JCheckBox)o;
        c.setSelected(state.isVentralSacs());
      }
    }
;
    genVar3767.addCheck(genVar3769,genVar3770);
    parent.gridy++;
    PanelWayPoint genVar3771;
    genVar3771=this;
    java.lang.String genVar3772;
    genVar3772="waypoint.metabolicBoost";
    java.lang.String genVar3773;
    genVar3773=messages.getString(genVar3772);
    CustomActionListener genVar3774;
    genVar3774=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        if (parent.getTrue(e))         state.addUpgrade(ZergUpgradeLibrary.MetabolicBoost);
 else         state.removeUpgrade(ZergUpgradeLibrary.MetabolicBoost);
      }
      void reverse(      Object o){
        JCheckBox c=(JCheckBox)o;
        c.setSelected(state.isMetabolicBoost());
      }
    }
;
    genVar3771.addCheck(genVar3773,genVar3774);
    PanelWayPoint genVar3775;
    genVar3775=this;
    java.lang.String genVar3776;
    genVar3776="waypoint.adrenalGlands";
    java.lang.String genVar3777;
    genVar3777=messages.getString(genVar3776);
    CustomActionListener genVar3778;
    genVar3778=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        if (parent.getTrue(e))         state.addUpgrade(ZergUpgradeLibrary.AdrenalGlands);
 else         state.removeUpgrade(ZergUpgradeLibrary.AdrenalGlands);
      }
      void reverse(      Object o){
        JCheckBox c=(JCheckBox)o;
        c.setSelected(state.isAdrenalGlands());
      }
    }
;
    genVar3775.addCheck(genVar3777,genVar3778);
    parent.gridy++;
    com.fray.evo.ui.swingx.PanelWayPoint genVar3779;
    genVar3779=this;
    java.lang.String genVar3780;
    genVar3780="waypoint.banelings";
    java.lang.String genVar3781;
    genVar3781=messages.getString(genVar3780);
    java.lang.Class<com.fray.evo.ui.swingx.NumberTextField> genVar3782;
    genVar3782=NumberTextField.class;
    CustomActionListener genVar3783;
    genVar3783=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        state.setUnits(ZergUnitLibrary.Baneling,EcSwingX.getDigit(e));
      }
      void reverse(      Object o){
        JTextField c=(JTextField)o;
        c.setText(Integer.toString(state.getBanelings()));
      }
    }
;
    parent.addInput(genVar3779,genVar3781,genVar3782,genVar3783);
    PanelWayPoint genVar3784;
    genVar3784=this;
    java.lang.String genVar3785;
    genVar3785="waypoint.centrifugalHooks";
    java.lang.String genVar3786;
    genVar3786=messages.getString(genVar3785);
    CustomActionListener genVar3787;
    genVar3787=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        if (parent.getTrue(e))         state.addUpgrade(ZergUpgradeLibrary.CentrifugalHooks);
 else         state.removeUpgrade(ZergUpgradeLibrary.CentrifugalHooks);
      }
      void reverse(      Object o){
        JCheckBox c=(JCheckBox)o;
        c.setSelected(state.isCentrifugalHooks());
      }
    }
;
    genVar3784.addCheck(genVar3786,genVar3787);
    parent.gridy++;
    com.fray.evo.ui.swingx.PanelWayPoint genVar3788;
    genVar3788=this;
    java.lang.String genVar3789;
    genVar3789="waypoint.roaches";
    java.lang.String genVar3790;
    genVar3790=messages.getString(genVar3789);
    java.lang.Class<com.fray.evo.ui.swingx.NumberTextField> genVar3791;
    genVar3791=NumberTextField.class;
    CustomActionListener genVar3792;
    genVar3792=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        state.setUnits(ZergUnitLibrary.Roach,EcSwingX.getDigit(e));
      }
      void reverse(      Object o){
        JTextField c=(JTextField)o;
        c.setText(Integer.toString(state.getRoaches()));
      }
    }
;
    parent.addInput(genVar3788,genVar3790,genVar3791,genVar3792);
    parent.gridy++;
    PanelWayPoint genVar3793;
    genVar3793=this;
    java.lang.String genVar3794;
    genVar3794="waypoint.glialReconstitution";
    java.lang.String genVar3795;
    genVar3795=messages.getString(genVar3794);
    CustomActionListener genVar3796;
    genVar3796=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        if (parent.getTrue(e))         state.addUpgrade(ZergUpgradeLibrary.GlialReconstitution);
 else         state.removeUpgrade(ZergUpgradeLibrary.GlialReconstitution);
      }
      void reverse(      Object o){
        JCheckBox c=(JCheckBox)o;
        c.setSelected(state.isGlialReconstitution());
      }
    }
;
    genVar3793.addCheck(genVar3795,genVar3796);
    PanelWayPoint genVar3797;
    genVar3797=this;
    java.lang.String genVar3798;
    genVar3798="waypoint.tunnelingClaws";
    java.lang.String genVar3799;
    genVar3799=messages.getString(genVar3798);
    CustomActionListener genVar3800;
    genVar3800=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        if (parent.getTrue(e))         state.addUpgrade(ZergUpgradeLibrary.TunnelingClaws);
 else         state.removeUpgrade(ZergUpgradeLibrary.TunnelingClaws);
      }
      void reverse(      Object o){
        JCheckBox c=(JCheckBox)o;
        c.setSelected(state.isTunnelingClaws());
      }
    }
;
    genVar3797.addCheck(genVar3799,genVar3800);
    parent.gridy++;
    com.fray.evo.ui.swingx.PanelWayPoint genVar3801;
    genVar3801=this;
    java.lang.String genVar3802;
    genVar3802="waypoint.hydralisks";
    java.lang.String genVar3803;
    genVar3803=messages.getString(genVar3802);
    java.lang.Class<com.fray.evo.ui.swingx.NumberTextField> genVar3804;
    genVar3804=NumberTextField.class;
    CustomActionListener genVar3805;
    genVar3805=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        state.setUnits(ZergUnitLibrary.Hydralisk,EcSwingX.getDigit(e));
      }
      void reverse(      Object o){
        JTextField c=(JTextField)o;
        c.setText(Integer.toString(state.getHydralisks()));
      }
    }
;
    parent.addInput(genVar3801,genVar3803,genVar3804,genVar3805);
    PanelWayPoint genVar3806;
    genVar3806=this;
    java.lang.String genVar3807;
    genVar3807="waypoint.groovedSpines";
    java.lang.String genVar3808;
    genVar3808=messages.getString(genVar3807);
    CustomActionListener genVar3809;
    genVar3809=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        if (parent.getTrue(e))         state.addUpgrade(ZergUpgradeLibrary.GroovedSpines);
 else         state.removeUpgrade(ZergUpgradeLibrary.GroovedSpines);
      }
      void reverse(      Object o){
        JCheckBox c=(JCheckBox)o;
        c.setSelected(state.isGroovedSpines());
      }
    }
;
    genVar3806.addCheck(genVar3808,genVar3809);
    parent.gridy++;
    com.fray.evo.ui.swingx.PanelWayPoint genVar3810;
    genVar3810=this;
    java.lang.String genVar3811;
    genVar3811="waypoint.infestors";
    java.lang.String genVar3812;
    genVar3812=messages.getString(genVar3811);
    java.lang.Class<com.fray.evo.ui.swingx.NumberTextField> genVar3813;
    genVar3813=NumberTextField.class;
    CustomActionListener genVar3814;
    genVar3814=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        state.setUnits(ZergUnitLibrary.Infestor,EcSwingX.getDigit(e));
      }
      void reverse(      Object o){
        JTextField c=(JTextField)o;
        c.setText(Integer.toString(state.getInfestors()));
      }
    }
;
    parent.addInput(genVar3810,genVar3812,genVar3813,genVar3814);
    parent.gridy++;
    PanelWayPoint genVar3815;
    genVar3815=this;
    java.lang.String genVar3816;
    genVar3816="waypoint.neuralParasite";
    java.lang.String genVar3817;
    genVar3817=messages.getString(genVar3816);
    CustomActionListener genVar3818;
    genVar3818=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        if (parent.getTrue(e))         state.addUpgrade(ZergUpgradeLibrary.NeuralParasite);
 else         state.removeUpgrade(ZergUpgradeLibrary.NeuralParasite);
      }
      void reverse(      Object o){
        JCheckBox c=(JCheckBox)o;
        c.setSelected(state.isNeuralParasite());
      }
    }
;
    genVar3815.addCheck(genVar3817,genVar3818);
    PanelWayPoint genVar3819;
    genVar3819=this;
    java.lang.String genVar3820;
    genVar3820="waypoint.pathogenGlands";
    java.lang.String genVar3821;
    genVar3821=messages.getString(genVar3820);
    CustomActionListener genVar3822;
    genVar3822=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        if (parent.getTrue(e))         state.addUpgrade(ZergUpgradeLibrary.PathogenGlands);
 else         state.removeUpgrade(ZergUpgradeLibrary.PathogenGlands);
      }
      void reverse(      Object o){
        JCheckBox c=(JCheckBox)o;
        c.setSelected(state.isPathogenGlands());
      }
    }
;
    genVar3819.addCheck(genVar3821,genVar3822);
    parent.gridy++;
    com.fray.evo.ui.swingx.PanelWayPoint genVar3823;
    genVar3823=this;
    java.lang.String genVar3824;
    genVar3824="waypoint.mutalisks";
    java.lang.String genVar3825;
    genVar3825=messages.getString(genVar3824);
    java.lang.Class<com.fray.evo.ui.swingx.NumberTextField> genVar3826;
    genVar3826=NumberTextField.class;
    CustomActionListener genVar3827;
    genVar3827=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        state.setUnits(ZergUnitLibrary.Mutalisk,EcSwingX.getDigit(e));
      }
      void reverse(      Object o){
        JTextField c=(JTextField)o;
        c.setText(Integer.toString(state.getMutalisks()));
      }
    }
;
    parent.addInput(genVar3823,genVar3825,genVar3826,genVar3827);
    parent.gridy++;
    com.fray.evo.ui.swingx.PanelWayPoint genVar3828;
    genVar3828=this;
    java.lang.String genVar3829;
    genVar3829="waypoint.ultralisks";
    java.lang.String genVar3830;
    genVar3830=messages.getString(genVar3829);
    java.lang.Class<com.fray.evo.ui.swingx.NumberTextField> genVar3831;
    genVar3831=NumberTextField.class;
    CustomActionListener genVar3832;
    genVar3832=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        state.setUnits(ZergUnitLibrary.Ultralisk,EcSwingX.getDigit(e));
      }
      void reverse(      Object o){
        JTextField c=(JTextField)o;
        c.setText(Integer.toString(state.getUltralisks()));
      }
    }
;
    parent.addInput(genVar3828,genVar3830,genVar3831,genVar3832);
    PanelWayPoint genVar3833;
    genVar3833=this;
    java.lang.String genVar3834;
    genVar3834="waypoint.chitinousPlating";
    java.lang.String genVar3835;
    genVar3835=messages.getString(genVar3834);
    CustomActionListener genVar3836;
    genVar3836=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        if (parent.getTrue(e))         state.addUpgrade(ZergUpgradeLibrary.ChitinousPlating);
 else         state.removeUpgrade(ZergUpgradeLibrary.ChitinousPlating);
      }
      void reverse(      Object o){
        JCheckBox c=(JCheckBox)o;
        c.setSelected(state.isChitinousPlating());
      }
    }
;
    genVar3833.addCheck(genVar3835,genVar3836);
    parent.gridy++;
    com.fray.evo.ui.swingx.PanelWayPoint genVar3837;
    genVar3837=this;
    java.lang.String genVar3838;
    genVar3838="waypoint.corruptors";
    java.lang.String genVar3839;
    genVar3839=messages.getString(genVar3838);
    java.lang.Class<com.fray.evo.ui.swingx.NumberTextField> genVar3840;
    genVar3840=NumberTextField.class;
    CustomActionListener genVar3841;
    genVar3841=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        state.setUnits(ZergUnitLibrary.Corruptor,EcSwingX.getDigit(e));
      }
      void reverse(      Object o){
        JTextField c=(JTextField)o;
        c.setText(Integer.toString(state.getCorruptors()));
      }
    }
;
    parent.addInput(genVar3837,genVar3839,genVar3840,genVar3841);
    com.fray.evo.ui.swingx.PanelWayPoint genVar3842;
    genVar3842=this;
    java.lang.String genVar3843;
    genVar3843="waypoint.broodlords";
    java.lang.String genVar3844;
    genVar3844=messages.getString(genVar3843);
    java.lang.Class<com.fray.evo.ui.swingx.NumberTextField> genVar3845;
    genVar3845=NumberTextField.class;
    CustomActionListener genVar3846;
    genVar3846=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        state.setUnits(ZergUnitLibrary.Broodlord,EcSwingX.getDigit(e));
      }
      void reverse(      Object o){
        JTextField c=(JTextField)o;
        c.setText(Integer.toString(state.getBroodlords()));
      }
    }
;
    parent.addInput(genVar3842,genVar3844,genVar3845,genVar3846);
    parent.gridy++;
    PanelWayPoint genVar3847;
    genVar3847=this;
    java.lang.String genVar3848;
    genVar3848="waypoint.melee";
    java.lang.String genVar3849;
    genVar3849=messages.getString(genVar3848);
    java.lang.String genVar3850;
    genVar3850=" +1";
    java.lang.String genVar3851;
    genVar3851=genVar3849 + genVar3850;
    CustomActionListener genVar3852;
    genVar3852=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        if (parent.getTrue(e))         state.addUpgrade(ZergUpgradeLibrary.Melee1);
 else         state.removeUpgrade(ZergUpgradeLibrary.Melee1);
      }
      void reverse(      Object o){
        JCheckBox c=(JCheckBox)o;
        c.setSelected(state.isMelee1());
      }
    }
;
    genVar3847.addCheck(genVar3851,genVar3852);
    PanelWayPoint genVar3853;
    genVar3853=this;
    java.lang.String genVar3854;
    genVar3854="+2";
    CustomActionListener genVar3855;
    genVar3855=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        if (parent.getTrue(e))         state.addUpgrade(ZergUpgradeLibrary.Melee2);
 else         state.removeUpgrade(ZergUpgradeLibrary.Melee2);
      }
      void reverse(      Object o){
        JCheckBox c=(JCheckBox)o;
        c.setSelected(state.isMelee2());
      }
    }
;
    genVar3853.addCheck(genVar3854,genVar3855);
    PanelWayPoint genVar3856;
    genVar3856=this;
    java.lang.String genVar3857;
    genVar3857="+3";
    CustomActionListener genVar3858;
    genVar3858=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        if (parent.getTrue(e))         state.addUpgrade(ZergUpgradeLibrary.Melee3);
 else         state.removeUpgrade(ZergUpgradeLibrary.Melee3);
      }
      void reverse(      Object o){
        JCheckBox c=(JCheckBox)o;
        c.setSelected(state.isMelee3());
      }
    }
;
    genVar3856.addCheck(genVar3857,genVar3858);
    parent.gridy++;
    PanelWayPoint genVar3859;
    genVar3859=this;
    java.lang.String genVar3860;
    genVar3860="waypoint.missile";
    java.lang.String genVar3861;
    genVar3861=messages.getString(genVar3860);
    java.lang.String genVar3862;
    genVar3862=" +1";
    java.lang.String genVar3863;
    genVar3863=genVar3861 + genVar3862;
    CustomActionListener genVar3864;
    genVar3864=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        if (parent.getTrue(e))         state.addUpgrade(ZergUpgradeLibrary.Missile1);
 else         state.removeUpgrade(ZergUpgradeLibrary.Missile1);
      }
      void reverse(      Object o){
        JCheckBox c=(JCheckBox)o;
        c.setSelected(state.isMissile1());
      }
    }
;
    genVar3859.addCheck(genVar3863,genVar3864);
    PanelWayPoint genVar3865;
    genVar3865=this;
    java.lang.String genVar3866;
    genVar3866="+2";
    CustomActionListener genVar3867;
    genVar3867=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        if (parent.getTrue(e))         state.addUpgrade(ZergUpgradeLibrary.Missile2);
 else         state.removeUpgrade(ZergUpgradeLibrary.Missile2);
      }
      void reverse(      Object o){
        JCheckBox c=(JCheckBox)o;
        c.setSelected(state.isMissile2());
      }
    }
;
    genVar3865.addCheck(genVar3866,genVar3867);
    PanelWayPoint genVar3868;
    genVar3868=this;
    java.lang.String genVar3869;
    genVar3869="+3";
    CustomActionListener genVar3870;
    genVar3870=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        if (parent.getTrue(e))         state.addUpgrade(ZergUpgradeLibrary.Missile3);
 else         state.removeUpgrade(ZergUpgradeLibrary.Missile3);
      }
      void reverse(      Object o){
        JCheckBox c=(JCheckBox)o;
        c.setSelected(state.isMissile3());
      }
    }
;
    genVar3868.addCheck(genVar3869,genVar3870);
    parent.gridy++;
    PanelWayPoint genVar3871;
    genVar3871=this;
    java.lang.String genVar3872;
    genVar3872="waypoint.carapace";
    java.lang.String genVar3873;
    genVar3873=messages.getString(genVar3872);
    java.lang.String genVar3874;
    genVar3874=" +1";
    java.lang.String genVar3875;
    genVar3875=genVar3873 + genVar3874;
    CustomActionListener genVar3876;
    genVar3876=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        if (parent.getTrue(e))         state.addUpgrade(ZergUpgradeLibrary.Armor1);
 else         state.removeUpgrade(ZergUpgradeLibrary.Armor1);
      }
      void reverse(      Object o){
        JCheckBox c=(JCheckBox)o;
        c.setSelected(state.isArmor1());
      }
    }
;
    genVar3871.addCheck(genVar3875,genVar3876);
    PanelWayPoint genVar3877;
    genVar3877=this;
    java.lang.String genVar3878;
    genVar3878="+2";
    CustomActionListener genVar3879;
    genVar3879=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        if (parent.getTrue(e))         state.addUpgrade(ZergUpgradeLibrary.Armor2);
 else         state.removeUpgrade(ZergUpgradeLibrary.Armor2);
      }
      void reverse(      Object o){
        JCheckBox c=(JCheckBox)o;
        c.setSelected(state.isArmor2());
      }
    }
;
    genVar3877.addCheck(genVar3878,genVar3879);
    PanelWayPoint genVar3880;
    genVar3880=this;
    java.lang.String genVar3881;
    genVar3881="+3";
    CustomActionListener genVar3882;
    genVar3882=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        if (parent.getTrue(e))         state.addUpgrade(ZergUpgradeLibrary.Armor3);
 else         state.removeUpgrade(ZergUpgradeLibrary.Armor3);
      }
      void reverse(      Object o){
        JCheckBox c=(JCheckBox)o;
        c.setSelected(state.isArmor3());
      }
    }
;
    genVar3880.addCheck(genVar3881,genVar3882);
    parent.gridy++;
    PanelWayPoint genVar3883;
    genVar3883=this;
    java.lang.String genVar3884;
    genVar3884="waypoint.flyerAttack";
    java.lang.String genVar3885;
    genVar3885=messages.getString(genVar3884);
    java.lang.String genVar3886;
    genVar3886=" +1";
    java.lang.String genVar3887;
    genVar3887=genVar3885 + genVar3886;
    CustomActionListener genVar3888;
    genVar3888=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        if (parent.getTrue(e))         state.addUpgrade(ZergUpgradeLibrary.FlyerAttacks1);
 else         state.removeUpgrade(ZergUpgradeLibrary.FlyerAttacks1);
      }
      void reverse(      Object o){
        JCheckBox c=(JCheckBox)o;
        c.setSelected(state.isFlyerAttack1());
      }
    }
;
    genVar3883.addCheck(genVar3887,genVar3888);
    PanelWayPoint genVar3889;
    genVar3889=this;
    java.lang.String genVar3890;
    genVar3890="+2";
    CustomActionListener genVar3891;
    genVar3891=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        if (parent.getTrue(e))         state.addUpgrade(ZergUpgradeLibrary.FlyerAttacks2);
 else         state.removeUpgrade(ZergUpgradeLibrary.FlyerAttacks2);
      }
      void reverse(      Object o){
        JCheckBox c=(JCheckBox)o;
        c.setSelected(state.isFlyerAttack2());
      }
    }
;
    genVar3889.addCheck(genVar3890,genVar3891);
    PanelWayPoint genVar3892;
    genVar3892=this;
    java.lang.String genVar3893;
    genVar3893="+3";
    CustomActionListener genVar3894;
    genVar3894=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        if (parent.getTrue(e))         state.addUpgrade(ZergUpgradeLibrary.FlyerAttacks3);
 else         state.removeUpgrade(ZergUpgradeLibrary.FlyerAttacks3);
      }
      void reverse(      Object o){
        JCheckBox c=(JCheckBox)o;
        c.setSelected(state.isFlyerAttack3());
      }
    }
;
    genVar3892.addCheck(genVar3893,genVar3894);
    parent.gridy++;
    PanelWayPoint genVar3895;
    genVar3895=this;
    java.lang.String genVar3896;
    genVar3896="waypoint.flyerArmor";
    java.lang.String genVar3897;
    genVar3897=messages.getString(genVar3896);
    java.lang.String genVar3898;
    genVar3898=" +1";
    java.lang.String genVar3899;
    genVar3899=genVar3897 + genVar3898;
    CustomActionListener genVar3900;
    genVar3900=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        if (parent.getTrue(e))         state.addUpgrade(ZergUpgradeLibrary.FlyerArmor1);
 else         state.removeUpgrade(ZergUpgradeLibrary.FlyerArmor1);
      }
      void reverse(      Object o){
        JCheckBox c=(JCheckBox)o;
        c.setSelected(state.isFlyerArmor1());
      }
    }
;
    genVar3895.addCheck(genVar3899,genVar3900);
    PanelWayPoint genVar3901;
    genVar3901=this;
    java.lang.String genVar3902;
    genVar3902="+2";
    CustomActionListener genVar3903;
    genVar3903=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        if (parent.getTrue(e))         state.addUpgrade(ZergUpgradeLibrary.FlyerArmor2);
 else         state.removeUpgrade(ZergUpgradeLibrary.FlyerArmor2);
      }
      void reverse(      Object o){
        JCheckBox c=(JCheckBox)o;
        c.setSelected(state.isFlyerArmor2());
      }
    }
;
    genVar3901.addCheck(genVar3902,genVar3903);
    PanelWayPoint genVar3904;
    genVar3904=this;
    java.lang.String genVar3905;
    genVar3905="+3";
    CustomActionListener genVar3906;
    genVar3906=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        if (parent.getTrue(e))         state.addUpgrade(ZergUpgradeLibrary.FlyerArmor3);
 else         state.removeUpgrade(ZergUpgradeLibrary.FlyerArmor3);
      }
      void reverse(      Object o){
        JCheckBox c=(JCheckBox)o;
        c.setSelected(state.isFlyerArmor3());
      }
    }
;
    genVar3904.addCheck(genVar3905,genVar3906);
    parent.gridy++;
    com.fray.evo.ui.swingx.PanelWayPoint genVar3907;
    genVar3907=this;
    java.lang.String genVar3908;
    genVar3908="waypoint.bases";
    java.lang.String genVar3909;
    genVar3909=messages.getString(genVar3908);
    java.lang.Class<com.fray.evo.ui.swingx.NumberTextField> genVar3910;
    genVar3910=NumberTextField.class;
    CustomActionListener genVar3911;
    genVar3911=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        state.requiredBases=EcSwingX.getDigit(e);
      }
      void reverse(      Object o){
        JTextField c=(JTextField)o;
        c.setText(Integer.toString(state.requiredBases));
      }
    }
;
    parent.addInput(genVar3907,genVar3909,genVar3910,genVar3911);
    com.fray.evo.ui.swingx.PanelWayPoint genVar3912;
    genVar3912=this;
    java.lang.String genVar3913;
    genVar3913="waypoint.lairs";
    java.lang.String genVar3914;
    genVar3914=messages.getString(genVar3913);
    java.lang.Class<com.fray.evo.ui.swingx.NumberTextField> genVar3915;
    genVar3915=NumberTextField.class;
    CustomActionListener genVar3916;
    genVar3916=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        state.setBuilding(ZergBuildingLibrary.Lair,EcSwingX.getDigit(e));
      }
      void reverse(      Object o){
        JTextField c=(JTextField)o;
        c.setText(Integer.toString(state.getLairs()));
      }
    }
;
    parent.addInput(genVar3912,genVar3914,genVar3915,genVar3916);
    parent.gridy++;
    com.fray.evo.ui.swingx.PanelWayPoint genVar3917;
    genVar3917=this;
    java.lang.String genVar3918;
    genVar3918="waypoint.hives";
    java.lang.String genVar3919;
    genVar3919=messages.getString(genVar3918);
    java.lang.Class<com.fray.evo.ui.swingx.NumberTextField> genVar3920;
    genVar3920=NumberTextField.class;
    CustomActionListener genVar3921;
    genVar3921=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        state.setBuilding(ZergBuildingLibrary.Hive,EcSwingX.getDigit(e));
      }
      void reverse(      Object o){
        JTextField c=(JTextField)o;
        c.setText(Integer.toString(state.getHives()));
      }
    }
;
    parent.addInput(genVar3917,genVar3919,genVar3920,genVar3921);
    com.fray.evo.ui.swingx.PanelWayPoint genVar3922;
    genVar3922=this;
    java.lang.String genVar3923;
    genVar3923="waypoint.gasExtractors";
    java.lang.String genVar3924;
    genVar3924=messages.getString(genVar3923);
    java.lang.Class<com.fray.evo.ui.swingx.NumberTextField> genVar3925;
    genVar3925=NumberTextField.class;
    CustomActionListener genVar3926;
    genVar3926=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        state.setBuilding(ZergBuildingLibrary.Extractor,EcSwingX.getDigit(e));
      }
      void reverse(      Object o){
        JTextField c=(JTextField)o;
        c.setText(Integer.toString(state.getGasExtractors()));
      }
    }
;
    parent.addInput(genVar3922,genVar3924,genVar3925,genVar3926);
    parent.gridy++;
    com.fray.evo.ui.swingx.PanelWayPoint genVar3927;
    genVar3927=this;
    java.lang.String genVar3928;
    genVar3928="waypoint.evolutionChambers";
    java.lang.String genVar3929;
    genVar3929=messages.getString(genVar3928);
    java.lang.Class<com.fray.evo.ui.swingx.NumberTextField> genVar3930;
    genVar3930=NumberTextField.class;
    CustomActionListener genVar3931;
    genVar3931=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        state.setBuilding(ZergBuildingLibrary.EvolutionChamber,EcSwingX.getDigit(e));
      }
      void reverse(      Object o){
        JTextField c=(JTextField)o;
        c.setText(Integer.toString(state.getEvolutionChambers()));
      }
    }
;
    parent.addInput(genVar3927,genVar3929,genVar3930,genVar3931);
    parent.gridy++;
    com.fray.evo.ui.swingx.PanelWayPoint genVar3932;
    genVar3932=this;
    java.lang.String genVar3933;
    genVar3933="waypoint.spineCrawlers";
    java.lang.String genVar3934;
    genVar3934=messages.getString(genVar3933);
    java.lang.Class<com.fray.evo.ui.swingx.NumberTextField> genVar3935;
    genVar3935=NumberTextField.class;
    CustomActionListener genVar3936;
    genVar3936=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        state.setBuilding(ZergBuildingLibrary.SpineCrawler,EcSwingX.getDigit(e));
      }
      void reverse(      Object o){
        JTextField c=(JTextField)o;
        c.setText(Integer.toString(state.getSpineCrawlers()));
      }
    }
;
    parent.addInput(genVar3932,genVar3934,genVar3935,genVar3936);
    com.fray.evo.ui.swingx.PanelWayPoint genVar3937;
    genVar3937=this;
    java.lang.String genVar3938;
    genVar3938="waypoint.sporeCrawlers";
    java.lang.String genVar3939;
    genVar3939=messages.getString(genVar3938);
    java.lang.Class<com.fray.evo.ui.swingx.NumberTextField> genVar3940;
    genVar3940=NumberTextField.class;
    CustomActionListener genVar3941;
    genVar3941=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        state.setBuilding(ZergBuildingLibrary.SporeCrawler,EcSwingX.getDigit(e));
      }
      void reverse(      Object o){
        JTextField c=(JTextField)o;
        c.setText(Integer.toString(state.getSporeCrawlers()));
      }
    }
;
    parent.addInput(genVar3937,genVar3939,genVar3940,genVar3941);
    parent.gridy++;
    com.fray.evo.ui.swingx.PanelWayPoint genVar3942;
    genVar3942=this;
    java.lang.String genVar3943;
    genVar3943="waypoint.spawningPools";
    java.lang.String genVar3944;
    genVar3944=messages.getString(genVar3943);
    java.lang.Class<com.fray.evo.ui.swingx.NumberTextField> genVar3945;
    genVar3945=NumberTextField.class;
    CustomActionListener genVar3946;
    genVar3946=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        state.setBuilding(ZergBuildingLibrary.SpawningPool,EcSwingX.getDigit(e));
      }
      void reverse(      Object o){
        JTextField c=(JTextField)o;
        c.setText(Integer.toString(state.getSpawningPools()));
      }
    }
;
    parent.addInput(genVar3942,genVar3944,genVar3945,genVar3946);
    com.fray.evo.ui.swingx.PanelWayPoint genVar3947;
    genVar3947=this;
    java.lang.String genVar3948;
    genVar3948="waypoint.banelingNests";
    java.lang.String genVar3949;
    genVar3949=messages.getString(genVar3948);
    java.lang.Class<com.fray.evo.ui.swingx.NumberTextField> genVar3950;
    genVar3950=NumberTextField.class;
    CustomActionListener genVar3951;
    genVar3951=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        state.setBuilding(ZergBuildingLibrary.BanelingNest,EcSwingX.getDigit(e));
      }
      void reverse(      Object o){
        JTextField c=(JTextField)o;
        c.setText(Integer.toString(state.getBanelingNest()));
      }
    }
;
    parent.addInput(genVar3947,genVar3949,genVar3950,genVar3951);
    parent.gridy++;
    com.fray.evo.ui.swingx.PanelWayPoint genVar3952;
    genVar3952=this;
    java.lang.String genVar3953;
    genVar3953="waypoint.roachWarrens";
    java.lang.String genVar3954;
    genVar3954=messages.getString(genVar3953);
    java.lang.Class<com.fray.evo.ui.swingx.NumberTextField> genVar3955;
    genVar3955=NumberTextField.class;
    CustomActionListener genVar3956;
    genVar3956=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        state.setBuilding(ZergBuildingLibrary.RoachWarren,EcSwingX.getDigit(e));
      }
      void reverse(      Object o){
        JTextField c=(JTextField)o;
        c.setText(Integer.toString(state.getRoachWarrens()));
      }
    }
;
    parent.addInput(genVar3952,genVar3954,genVar3955,genVar3956);
    com.fray.evo.ui.swingx.PanelWayPoint genVar3957;
    genVar3957=this;
    java.lang.String genVar3958;
    genVar3958="waypoint.hydraliskDens";
    java.lang.String genVar3959;
    genVar3959=messages.getString(genVar3958);
    java.lang.Class<com.fray.evo.ui.swingx.NumberTextField> genVar3960;
    genVar3960=NumberTextField.class;
    CustomActionListener genVar3961;
    genVar3961=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        state.setBuilding(ZergBuildingLibrary.HydraliskDen,EcSwingX.getDigit(e));
      }
      void reverse(      Object o){
        JTextField c=(JTextField)o;
        c.setText(Integer.toString(state.getHydraliskDen()));
      }
    }
;
    parent.addInput(genVar3957,genVar3959,genVar3960,genVar3961);
    parent.gridy++;
    com.fray.evo.ui.swingx.PanelWayPoint genVar3962;
    genVar3962=this;
    java.lang.String genVar3963;
    genVar3963="waypoint.infestationPits";
    java.lang.String genVar3964;
    genVar3964=messages.getString(genVar3963);
    java.lang.Class<com.fray.evo.ui.swingx.NumberTextField> genVar3965;
    genVar3965=NumberTextField.class;
    CustomActionListener genVar3966;
    genVar3966=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        state.setBuilding(ZergBuildingLibrary.InfestationPit,EcSwingX.getDigit(e));
      }
      void reverse(      Object o){
        JTextField c=(JTextField)o;
        c.setText(Integer.toString(state.getInfestationPit()));
      }
    }
;
    parent.addInput(genVar3962,genVar3964,genVar3965,genVar3966);
    com.fray.evo.ui.swingx.PanelWayPoint genVar3967;
    genVar3967=this;
    java.lang.String genVar3968;
    genVar3968="waypoint.spires";
    java.lang.String genVar3969;
    genVar3969=messages.getString(genVar3968);
    java.lang.Class<com.fray.evo.ui.swingx.NumberTextField> genVar3970;
    genVar3970=NumberTextField.class;
    CustomActionListener genVar3971;
    genVar3971=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        state.setBuilding(ZergBuildingLibrary.Spire,EcSwingX.getDigit(e));
      }
      void reverse(      Object o){
        JTextField c=(JTextField)o;
        c.setText(Integer.toString(state.getSpire()));
      }
    }
;
    parent.addInput(genVar3967,genVar3969,genVar3970,genVar3971);
    parent.gridy++;
    com.fray.evo.ui.swingx.PanelWayPoint genVar3972;
    genVar3972=this;
    java.lang.String genVar3973;
    genVar3973="waypoint.nydusNetworks";
    java.lang.String genVar3974;
    genVar3974=messages.getString(genVar3973);
    java.lang.Class<com.fray.evo.ui.swingx.NumberTextField> genVar3975;
    genVar3975=NumberTextField.class;
    CustomActionListener genVar3976;
    genVar3976=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        state.setBuilding(ZergBuildingLibrary.NydusNetwork,EcSwingX.getDigit(e));
      }
      void reverse(      Object o){
        JTextField c=(JTextField)o;
        c.setText(Integer.toString(state.getNydusNetwork()));
      }
    }
;
    parent.addInput(genVar3972,genVar3974,genVar3975,genVar3976);
    com.fray.evo.ui.swingx.PanelWayPoint genVar3977;
    genVar3977=this;
    java.lang.String genVar3978;
    genVar3978="waypoint.nydusWorms";
    java.lang.String genVar3979;
    genVar3979=messages.getString(genVar3978);
    java.lang.Class<com.fray.evo.ui.swingx.NumberTextField> genVar3980;
    genVar3980=NumberTextField.class;
    CustomActionListener genVar3981;
    genVar3981=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        state.setBuilding(ZergBuildingLibrary.NydusWorm,EcSwingX.getDigit(e));
      }
      void reverse(      Object o){
        JTextField c=(JTextField)o;
        c.setText(Integer.toString(state.getNydusWorm()));
      }
    }
;
    parent.addInput(genVar3977,genVar3979,genVar3980,genVar3981);
    parent.gridy++;
    com.fray.evo.ui.swingx.PanelWayPoint genVar3982;
    genVar3982=this;
    java.lang.String genVar3983;
    genVar3983="waypoint.ultraliskCaverns";
    java.lang.String genVar3984;
    genVar3984=messages.getString(genVar3983);
    java.lang.Class<com.fray.evo.ui.swingx.NumberTextField> genVar3985;
    genVar3985=NumberTextField.class;
    CustomActionListener genVar3986;
    genVar3986=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        state.setBuilding(ZergBuildingLibrary.UltraliskCavern,EcSwingX.getDigit(e));
      }
      void reverse(      Object o){
        JTextField c=(JTextField)o;
        c.setText(Integer.toString(state.getUltraliskCavern()));
      }
    }
;
    parent.addInput(genVar3982,genVar3984,genVar3985,genVar3986);
    com.fray.evo.ui.swingx.PanelWayPoint genVar3987;
    genVar3987=this;
    java.lang.String genVar3988;
    genVar3988="waypoint.greaterSpires";
    java.lang.String genVar3989;
    genVar3989=messages.getString(genVar3988);
    java.lang.Class<com.fray.evo.ui.swingx.NumberTextField> genVar3990;
    genVar3990=NumberTextField.class;
    CustomActionListener genVar3991;
    genVar3991=new CustomActionListener(){
      public void actionPerformed(      ActionEvent e){
        state.setBuilding(ZergBuildingLibrary.GreaterSpire,EcSwingX.getDigit(e));
      }
      void reverse(      Object o){
        JTextField c=(JTextField)o;
        c.setText(Integer.toString(state.getGreaterSpire()));
      }
    }
;
    parent.addInput(genVar3987,genVar3989,genVar3990,genVar3991);
    parent.gridy++;
    int width;
    width=4;
    final PanelWayPoint tmp;
    tmp=this;
    int genVar3992;
    genVar3992=parent.destination.size();
    int genVar3993;
    genVar3993=1;
    int genVar3994;
    genVar3994=genVar3992 - genVar3993;
    com.fray.evo.EcState genVar3995;
    genVar3995=parent.destination.get(genVar3994);
    boolean genVar3996;
    genVar3996=state != genVar3995;
    if (genVar3996) {
      com.fray.evo.ui.swingx.PanelWayPoint genVar3997;
      genVar3997=this;
      java.lang.String genVar3998;
      genVar3998="waypoint.remove";
      java.lang.String genVar3999;
      genVar3999=messages.getString(genVar3998);
      int genVar4000;
      genVar4000=1;
      ActionListener genVar4001;
      genVar4001=new ActionListener(){
        public void actionPerformed(        ActionEvent e){
          parent.removeTab(tmp);
        }
      }
;
      javax.swing.JButton genVar4002;
      genVar4002=parent.addButton(genVar3997,genVar3999,genVar4000,genVar4001);
      parent.inputControls.add(genVar4002);
      width=3;
    }
 else {
      ;
    }
    com.fray.evo.ui.swingx.PanelWayPoint genVar4003;
    genVar4003=this;
    java.lang.String genVar4004;
    genVar4004="waypoint.reset";
    java.lang.String genVar4005;
    genVar4005=messages.getString(genVar4004);
    ActionListener genVar4006;
    genVar4006=new ActionListener(){
      public void actionPerformed(      ActionEvent e){
        for (int i=0; i < getComponentCount(); i++) {
          Component component=getComponent(i);
          if (component instanceof JTextField) {
            JTextField textField=(JTextField)component;
            if (textField.getText().indexOf(":") == -1) {
              textField.setText("0");
              textField.getActionListeners()[0].actionPerformed(new ActionEvent(textField,0,""));
            }
          }
 else           if (component instanceof JCheckBox) {
            JCheckBox checkBox=(JCheckBox)component;
            checkBox.setSelected(false);
            checkBox.getActionListeners()[0].actionPerformed(new ActionEvent(checkBox,0,""));
          }
        }
      }
    }
;
    javax.swing.JButton genVar4007;
    genVar4007=parent.addButton(genVar4003,genVar4005,width,genVar4006);
    parent.inputControls.add(genVar4007);
  }
  String formatAsTime(  int time){
    int genVar4008;
    genVar4008=60;
    int minutes;
    minutes=time / genVar4008;
    int genVar4009;
    genVar4009=60;
    int seconds;
    seconds=time % genVar4009;
    java.lang.String genVar4010;
    genVar4010=Integer.toString(minutes);
    java.lang.String genVar4011;
    genVar4011=":";
    int genVar4012;
    genVar4012=10;
    boolean genVar4013;
    genVar4013=seconds < genVar4012;
    java.lang.String genVar4014;
    genVar4014="0";
    java.lang.String genVar4015;
    genVar4015="";
    java.lang.String genVar4016;
    genVar4016=genVar4013 ? genVar4014 : genVar4015;
    String genVar4017;
    genVar4017=(genVar4016);
    java.lang.String genVar4018;
    genVar4018=Integer.toString(seconds);
    java.lang.String genVar4019;
    genVar4019=genVar4010 + genVar4011 + genVar4017+ genVar4018;
    return genVar4019;
  }
  public EcState getState(){
    return state;
  }
}
