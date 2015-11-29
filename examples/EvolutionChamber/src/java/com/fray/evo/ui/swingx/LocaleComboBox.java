package com.fray.evo.ui.swingx;
import java.awt.Component;
import java.net.URL;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
/** 
 * A combobox that displays a list of languages.
 * @author mike.angstadt
 */
public class LocaleComboBox extends JComboBox {
  private static final long serialVersionUID=784618264004326168L;
  /** 
 * Constructor.
 * @param locales the Locales to be shown in the combobox
 * @param currentLocale the current Locale of the application
 */
  public LocaleComboBox(  Locale[] locales,  Locale currentLocale){
    for (    Locale locale : locales) {
      Item item;
      item=new Item();
      item.locale=locale;
      item.text=locale.getDisplayLanguage(currentLocale);
      java.lang.Class<com.fray.evo.ui.swingx.LocaleComboBox> genVar4020;
      genVar4020=LocaleComboBox.class;
      java.lang.String genVar4021;
      genVar4021=locale.getLanguage();
      java.lang.String genVar4022;
      genVar4022=".png";
      java.lang.String genVar4023;
      genVar4023=genVar4021 + genVar4022;
      URL url;
      url=genVar4020.getResource(genVar4023);
      boolean genVar4024;
      genVar4024=url != null;
      if (genVar4024) {
        item.image=new ImageIcon(url);
      }
 else {
        ;
      }
      LocaleComboBox genVar4025;
      genVar4025=this;
      genVar4025.addItem(item);
    }
    LocaleComboBox genVar4026;
    genVar4026=this;
    com.fray.evo.ui.swingx.LocaleComboBox.ComboBoxRenderer genVar4027;
    genVar4027=new ComboBoxRenderer();
    genVar4026.setRenderer(genVar4027);
    LocaleComboBox genVar4028;
    genVar4028=this;
    genVar4028.setSelectedLocale(currentLocale);
  }
  /** 
 * Gets the selected Locale.
 * @return
 */
  public Locale getSelectedLocale(){
    LocaleComboBox genVar4029;
    genVar4029=this;
    java.lang.Object genVar4030;
    genVar4030=genVar4029.getSelectedItem();
    Item item;
    item=(Item)genVar4030;
    return item.locale;
  }
  /** 
 * Sets the selected Locale
 * @param locale
 */
  public void setSelectedLocale(  Locale locale){
    int i=0;
    for (; i < getItemCount(); i++) {
      LocaleComboBox genVar4031;
      genVar4031=this;
      java.lang.Object genVar4032;
      genVar4032=genVar4031.getItemAt(i);
      Item item;
      item=(Item)genVar4032;
      java.lang.String genVar4033;
      genVar4033=item.locale.getLanguage();
      java.lang.String genVar4034;
      genVar4034=locale.getLanguage();
      boolean genVar4035;
      genVar4035=genVar4033.equals(genVar4034);
      if (genVar4035) {
        LocaleComboBox genVar4036;
        genVar4036=this;
        genVar4036.setSelectedIndex(i);
        java.lang.String genVar4037;
        genVar4037=item.locale.getCountry();
        java.lang.String genVar4038;
        genVar4038=locale.getCountry();
        boolean genVar4039;
        genVar4039=genVar4037.equals(genVar4038);
        if (genVar4039) {
          break;
        }
 else {
          ;
        }
      }
 else {
        ;
      }
    }
  }
  /** 
 * Represents an item in the combobox
 * @author mike.angstadt
 */
private static class Item {
    public String text;
    public ImageIcon image;
    public Locale locale;
  }
  /** 
 * Allows for a flag icon to be shown alongside each language.
 * @see http://www.java2s.com/Code/Java/Swing-JFC/CustomComboBoxwithImage.htm
 * @author mike.angstadt
 */
private static class ComboBoxRenderer extends JLabel implements ListCellRenderer {
    private static final long serialVersionUID=-2560362531812495409L;
    public ComboBoxRenderer(){
      ComboBoxRenderer genVar4040;
      genVar4040=this;
      boolean genVar4041;
      genVar4041=true;
      genVar4040.setOpaque(genVar4041);
      ComboBoxRenderer genVar4042;
      genVar4042=this;
      genVar4042.setVerticalAlignment(CENTER);
    }
    public Component getListCellRendererComponent(    JList list,    Object value,    int index,    boolean isSelected,    boolean cellHasFocus){
      Item item;
      item=(Item)value;
      if (isSelected) {
        ComboBoxRenderer genVar4043;
        genVar4043=this;
        java.awt.Color genVar4044;
        genVar4044=list.getSelectionBackground();
        genVar4043.setBackground(genVar4044);
        ComboBoxRenderer genVar4045;
        genVar4045=this;
        java.awt.Color genVar4046;
        genVar4046=list.getSelectionForeground();
        genVar4045.setForeground(genVar4046);
      }
 else {
        ComboBoxRenderer genVar4047;
        genVar4047=this;
        java.awt.Color genVar4048;
        genVar4048=list.getBackground();
        genVar4047.setBackground(genVar4048);
        ComboBoxRenderer genVar4049;
        genVar4049=this;
        java.awt.Color genVar4050;
        genVar4050=list.getForeground();
        genVar4049.setForeground(genVar4050);
      }
      ComboBoxRenderer genVar4051;
      genVar4051=this;
      genVar4051.setIcon(item.image);
      ComboBoxRenderer genVar4052;
      genVar4052=this;
      genVar4052.setText(item.text);
      ComboBoxRenderer genVar4053;
      genVar4053=this;
      java.awt.Font genVar4054;
      genVar4054=list.getFont();
      genVar4053.setFont(genVar4054);
      com.fray.evo.ui.swingx.LocaleComboBox.ComboBoxRenderer genVar4055;
      genVar4055=this;
      return genVar4055;
    }
  }
}
