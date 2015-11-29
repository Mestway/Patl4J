package ui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import models.FinInterface;
import panels.AuthorPanel;
import panels.PickFinPanel;
import panels.ResultsPanel;
import ui.BaseInterface.ResultsReadyListener;
public class MainWindow extends JFrame implements ResultsReadyListener {
  private String CREATED_BY="Created by : Momoh Ozavoshon Jamiu";
  private String TITLE="Analysis of Heat Transfer \n Through Fins";
  private PickFinInterface pickFinInterface;
  private CylinderPanelInitializer cylinderInitializer;
  private ParabolicPanelInitializer parabolicInitializer;
  private RectanglePanelInitializer rectangleInitializer;
  private TrapezoidalPanelInitializer trapezoidalInitializer;
  private TrianglePanelInitializer triangleInitializer;
  public MainWindow(){
    super("Heat Analysis");
    ui.MainWindow genVar1056;
    genVar1056=this;
    genVar1056.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    MainWindow genVar1057;
    genVar1057=this;
    genVar1057.setMainFrame();
    ui.MainWindow genVar1058;
    genVar1058=this;
    ui.MainWindow genVar1059;
    genVar1059=this;
    ui.MainWindow genVar1060;
    genVar1060=this;
    genVar1058.cylinderInitializer=new CylinderPanelInitializer(genVar1059,genVar1060);
    ui.MainWindow genVar1061;
    genVar1061=this;
    ui.MainWindow genVar1062;
    genVar1062=this;
    ui.MainWindow genVar1063;
    genVar1063=this;
    genVar1061.parabolicInitializer=new ParabolicPanelInitializer(genVar1062,genVar1063);
    ui.MainWindow genVar1064;
    genVar1064=this;
    ui.MainWindow genVar1065;
    genVar1065=this;
    ui.MainWindow genVar1066;
    genVar1066=this;
    genVar1064.rectangleInitializer=new RectanglePanelInitializer(genVar1065,genVar1066);
    ui.MainWindow genVar1067;
    genVar1067=this;
    ui.MainWindow genVar1068;
    genVar1068=this;
    ui.MainWindow genVar1069;
    genVar1069=this;
    genVar1067.trapezoidalInitializer=new TrapezoidalPanelInitializer(genVar1068,genVar1069);
    ui.MainWindow genVar1070;
    genVar1070=this;
    ui.MainWindow genVar1071;
    genVar1071=this;
    ui.MainWindow genVar1072;
    genVar1072=this;
    genVar1070.triangleInitializer=new TrianglePanelInitializer(genVar1071,genVar1072);
  }
  public void setMainFrame(){
    AuthorPanel authorPanel;
    authorPanel=new AuthorPanel();
    javax.swing.JButton genVar1073;
    genVar1073=authorPanel.getButton();
    ActionListener genVar1074;
    genVar1074=new ActionListener(){
      @Override public void actionPerformed(      ActionEvent arg0){
        goToSelectPage();
      }
    }
;
    genVar1073.addActionListener(genVar1074);
    ui.MainWindow genVar1075;
    genVar1075=this;
    boolean genVar1076;
    genVar1076=false;
    genVar1075.setVisible(genVar1076);
    ui.MainWindow genVar1077;
    genVar1077=this;
    java.awt.Container genVar1078;
    genVar1078=genVar1077.getContentPane();
    genVar1078.add(authorPanel);
    ui.MainWindow genVar1079;
    genVar1079=this;
    genVar1079.pack();
    ui.MainWindow genVar1080;
    genVar1080=this;
    boolean genVar1081;
    genVar1081=true;
    genVar1080.setVisible(genVar1081);
  }
  public void goToSelectPage(){
    PickFinInterface pickFinInterface;
    pickFinInterface=new PickFinInterface(){
      @Override public void finPiked(      String item){
        if (item == "Cylindrical Fin")         cylinderInitializer.initializePanel();
        if (item == "Parabolic Fin")         parabolicInitializer.initializePanel();
        if (item == "Rectangular Fin")         rectangleInitializer.initializePanel();
        if (item == "Trapezoidal Fin")         trapezoidalInitializer.initializePanel();
        if (item == "Triangular Fin")         triangleInitializer.initializePanel();
      }
    }
;
    ui.MainWindow genVar1082;
    genVar1082=this;
    boolean genVar1083;
    genVar1083=false;
    genVar1082.setVisible(genVar1083);
    PickFinPanel pickFin;
    pickFin=new PickFinPanel(pickFinInterface);
    ui.MainWindow genVar1084;
    genVar1084=this;
    java.awt.Container genVar1085;
    genVar1085=genVar1084.getContentPane();
    genVar1085.removeAll();
    ui.MainWindow genVar1086;
    genVar1086=this;
    java.awt.Container genVar1087;
    genVar1087=genVar1086.getContentPane();
    genVar1087.add(pickFin);
    ui.MainWindow genVar1088;
    genVar1088=this;
    genVar1088.pack();
    ui.MainWindow genVar1089;
    genVar1089=this;
    boolean genVar1090;
    genVar1090=true;
    genVar1089.setVisible(genVar1090);
  }
  @Override public void goToResults(  FinInterface object){
    ui.MainWindow genVar1091;
    genVar1091=this;
    boolean genVar1092;
    genVar1092=false;
    genVar1091.setVisible(genVar1092);
    ui.MainWindow genVar1093;
    genVar1093=this;
    java.awt.Container genVar1094;
    genVar1094=genVar1093.getContentPane();
    genVar1094.removeAll();
    ui.MainWindow genVar1095;
    genVar1095=this;
    java.awt.Container genVar1096;
    genVar1096=genVar1095.getContentPane();
    panels.ResultsPanel genVar1097;
    genVar1097=new ResultsPanel(object);
    genVar1096.add(genVar1097);
    ui.MainWindow genVar1098;
    genVar1098=this;
    genVar1098.revalidate();
    ui.MainWindow genVar1099;
    genVar1099=this;
    genVar1099.repaint();
    ui.MainWindow genVar1100;
    genVar1100=this;
    genVar1100.pack();
    ui.MainWindow genVar1101;
    genVar1101=this;
    boolean genVar1102;
    genVar1102=true;
    genVar1101.setVisible(genVar1102);
  }
  public void refresh(){
    ui.MainWindow genVar1103;
    genVar1103=this;
    genVar1103.revalidate();
    ui.MainWindow genVar1104;
    genVar1104=this;
    genVar1104.repaint();
  }
public interface PickFinInterface {
    public void finPiked(    String item);
  }
}
