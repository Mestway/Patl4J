package game;
import jFrame.UI;
import java.awt.Point;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import chat.RoomList;
public class Game {
  public static Ground[] ground=new Ground[26];
  public static void init(){
    int i=0;
    for (; i < 26; i++) {
      ground[i]=new Ground();
    }
    int genVar348;
    genVar348=0;
    game.Ground genVar349;
    genVar349=ground[genVar348];
    genVar349.fee=0;
    int genVar350;
    genVar350=0;
    game.Ground genVar351;
    genVar351=ground[genVar350];
    int genVar352;
    genVar352=1;
    genVar351.owner=-genVar352;
    int genVar353;
    genVar353=0;
    game.Ground genVar354;
    genVar354=ground[genVar353];
    genVar354.price=0;
    int genVar355;
    genVar355=1;
    game.Ground genVar356;
    genVar356=ground[genVar355];
    genVar356.fee=20000;
    int genVar357;
    genVar357=1;
    game.Ground genVar358;
    genVar358=ground[genVar357];
    int genVar359;
    genVar359=1;
    genVar358.owner=-genVar359;
    int genVar360;
    genVar360=1;
    game.Ground genVar361;
    genVar361=ground[genVar360];
    genVar361.price=10000;
    int genVar362;
    genVar362=2;
    game.Ground genVar363;
    genVar363=ground[genVar362];
    genVar363.fee=25000;
    int genVar364;
    genVar364=2;
    game.Ground genVar365;
    genVar365=ground[genVar364];
    int genVar366;
    genVar366=1;
    genVar365.owner=-genVar366;
    int genVar367;
    genVar367=2;
    game.Ground genVar368;
    genVar368=ground[genVar367];
    genVar368.price=15000;
    int genVar369;
    genVar369=3;
    game.Ground genVar370;
    genVar370=ground[genVar369];
    genVar370.fee=50000;
    int genVar371;
    genVar371=3;
    game.Ground genVar372;
    genVar372=ground[genVar371];
    int genVar373;
    genVar373=1;
    genVar372.owner=-genVar373;
    int genVar374;
    genVar374=3;
    game.Ground genVar375;
    genVar375=ground[genVar374];
    genVar375.price=20000;
    int genVar376;
    genVar376=4;
    game.Ground genVar377;
    genVar377=ground[genVar376];
    genVar377.fee=30000;
    int genVar378;
    genVar378=4;
    game.Ground genVar379;
    genVar379=ground[genVar378];
    int genVar380;
    genVar380=1;
    genVar379.owner=-genVar380;
    int genVar381;
    genVar381=4;
    game.Ground genVar382;
    genVar382=ground[genVar381];
    genVar382.price=20000;
    int genVar383;
    genVar383=5;
    game.Ground genVar384;
    genVar384=ground[genVar383];
    genVar384.fee=0;
    int genVar385;
    genVar385=5;
    game.Ground genVar386;
    genVar386=ground[genVar385];
    int genVar387;
    genVar387=1;
    genVar386.owner=-genVar387;
    int genVar388;
    genVar388=5;
    game.Ground genVar389;
    genVar389=ground[genVar388];
    genVar389.price=0;
    int genVar390;
    genVar390=6;
    game.Ground genVar391;
    genVar391=ground[genVar390];
    genVar391.fee=40000;
    int genVar392;
    genVar392=6;
    game.Ground genVar393;
    genVar393=ground[genVar392];
    int genVar394;
    genVar394=1;
    genVar393.owner=-genVar394;
    int genVar395;
    genVar395=6;
    game.Ground genVar396;
    genVar396=ground[genVar395];
    genVar396.price=25000;
    int genVar397;
    genVar397=7;
    game.Ground genVar398;
    genVar398=ground[genVar397];
    genVar398.fee=0;
    int genVar399;
    genVar399=7;
    game.Ground genVar400;
    genVar400=ground[genVar399];
    int genVar401;
    genVar401=1;
    genVar400.owner=-genVar401;
    int genVar402;
    genVar402=7;
    game.Ground genVar403;
    genVar403=ground[genVar402];
    genVar403.price=0;
    int genVar404;
    genVar404=8;
    game.Ground genVar405;
    genVar405=ground[genVar404];
    genVar405.fee=60000;
    int genVar406;
    genVar406=8;
    game.Ground genVar407;
    genVar407=ground[genVar406];
    int genVar408;
    genVar408=1;
    genVar407.owner=-genVar408;
    int genVar409;
    genVar409=8;
    game.Ground genVar410;
    genVar410=ground[genVar409];
    genVar410.price=30000;
    int genVar411;
    genVar411=9;
    game.Ground genVar412;
    genVar412=ground[genVar411];
    genVar412.fee=80000;
    int genVar413;
    genVar413=9;
    game.Ground genVar414;
    genVar414=ground[genVar413];
    int genVar415;
    genVar415=1;
    genVar414.owner=-genVar415;
    int genVar416;
    genVar416=9;
    game.Ground genVar417;
    genVar417=ground[genVar416];
    genVar417.price=45000;
    int genVar418;
    genVar418=10;
    game.Ground genVar419;
    genVar419=ground[genVar418];
    genVar419.fee=70000;
    int genVar420;
    genVar420=10;
    game.Ground genVar421;
    genVar421=ground[genVar420];
    int genVar422;
    genVar422=1;
    genVar421.owner=-genVar422;
    int genVar423;
    genVar423=10;
    game.Ground genVar424;
    genVar424=ground[genVar423];
    genVar424.price=35000;
    int genVar425;
    genVar425=11;
    game.Ground genVar426;
    genVar426=ground[genVar425];
    genVar426.fee=0;
    int genVar427;
    genVar427=11;
    game.Ground genVar428;
    genVar428=ground[genVar427];
    int genVar429;
    genVar429=1;
    genVar428.owner=-genVar429;
    int genVar430;
    genVar430=11;
    game.Ground genVar431;
    genVar431=ground[genVar430];
    genVar431.price=0;
    int genVar432;
    genVar432=12;
    game.Ground genVar433;
    genVar433=ground[genVar432];
    genVar433.fee=80000;
    int genVar434;
    genVar434=12;
    game.Ground genVar435;
    genVar435=ground[genVar434];
    int genVar436;
    genVar436=1;
    genVar435.owner=-genVar436;
    int genVar437;
    genVar437=12;
    game.Ground genVar438;
    genVar438=ground[genVar437];
    genVar438.price=48000;
    int genVar439;
    genVar439=13;
    game.Ground genVar440;
    genVar440=ground[genVar439];
    genVar440.fee=0;
    int genVar441;
    genVar441=13;
    game.Ground genVar442;
    genVar442=ground[genVar441];
    int genVar443;
    genVar443=1;
    genVar442.owner=-genVar443;
    int genVar444;
    genVar444=13;
    game.Ground genVar445;
    genVar445=ground[genVar444];
    genVar445.price=0;
    int genVar446;
    genVar446=14;
    game.Ground genVar447;
    genVar447=ground[genVar446];
    genVar447.fee=100000;
    int genVar448;
    genVar448=14;
    game.Ground genVar449;
    genVar449=ground[genVar448];
    int genVar450;
    genVar450=1;
    genVar449.owner=-genVar450;
    int genVar451;
    genVar451=14;
    game.Ground genVar452;
    genVar452=ground[genVar451];
    genVar452.price=50000;
    int genVar453;
    genVar453=15;
    game.Ground genVar454;
    genVar454=ground[genVar453];
    genVar454.fee=150000;
    int genVar455;
    genVar455=15;
    game.Ground genVar456;
    genVar456=ground[genVar455];
    int genVar457;
    genVar457=1;
    genVar456.owner=-genVar457;
    int genVar458;
    genVar458=15;
    game.Ground genVar459;
    genVar459=ground[genVar458];
    genVar459.price=80000;
    int genVar460;
    genVar460=16;
    game.Ground genVar461;
    genVar461=ground[genVar460];
    genVar461.fee=130000;
    int genVar462;
    genVar462=16;
    game.Ground genVar463;
    genVar463=ground[genVar462];
    int genVar464;
    genVar464=1;
    genVar463.owner=-genVar464;
    int genVar465;
    genVar465=16;
    game.Ground genVar466;
    genVar466=ground[genVar465];
    genVar466.price=70000;
    int genVar467;
    genVar467=17;
    game.Ground genVar468;
    genVar468=ground[genVar467];
    genVar468.fee=0;
    int genVar469;
    genVar469=17;
    game.Ground genVar470;
    genVar470=ground[genVar469];
    int genVar471;
    genVar471=1;
    genVar470.owner=-genVar471;
    int genVar472;
    genVar472=17;
    game.Ground genVar473;
    genVar473=ground[genVar472];
    genVar473.price=0;
    int genVar474;
    genVar474=18;
    game.Ground genVar475;
    genVar475=ground[genVar474];
    genVar475.fee=160000;
    int genVar476;
    genVar476=18;
    game.Ground genVar477;
    genVar477=ground[genVar476];
    int genVar478;
    genVar478=1;
    genVar477.owner=-genVar478;
    int genVar479;
    genVar479=18;
    game.Ground genVar480;
    genVar480=ground[genVar479];
    genVar480.price=75000;
    int genVar481;
    genVar481=19;
    game.Ground genVar482;
    genVar482=ground[genVar481];
    genVar482.fee=190000;
    int genVar483;
    genVar483=19;
    game.Ground genVar484;
    genVar484=ground[genVar483];
    int genVar485;
    genVar485=1;
    genVar484.owner=-genVar485;
    int genVar486;
    genVar486=19;
    game.Ground genVar487;
    genVar487=ground[genVar486];
    genVar487.price=90000;
    int genVar488;
    genVar488=20;
    game.Ground genVar489;
    genVar489=ground[genVar488];
    genVar489.fee=0;
    int genVar490;
    genVar490=20;
    game.Ground genVar491;
    genVar491=ground[genVar490];
    int genVar492;
    genVar492=1;
    genVar491.owner=-genVar492;
    int genVar493;
    genVar493=20;
    game.Ground genVar494;
    genVar494=ground[genVar493];
    genVar494.price=0;
    int genVar495;
    genVar495=21;
    game.Ground genVar496;
    genVar496=ground[genVar495];
    genVar496.fee=230000;
    int genVar497;
    genVar497=21;
    game.Ground genVar498;
    genVar498=ground[genVar497];
    int genVar499;
    genVar499=1;
    genVar498.owner=-genVar499;
    int genVar500;
    genVar500=21;
    game.Ground genVar501;
    genVar501=ground[genVar500];
    genVar501.price=100000;
    int genVar502;
    genVar502=22;
    game.Ground genVar503;
    genVar503=ground[genVar502];
    genVar503.fee=190000;
    int genVar504;
    genVar504=22;
    game.Ground genVar505;
    genVar505=ground[genVar504];
    int genVar506;
    genVar506=1;
    genVar505.owner=-genVar506;
    int genVar507;
    genVar507=22;
    game.Ground genVar508;
    genVar508=ground[genVar507];
    genVar508.price=90000;
    int genVar509;
    genVar509=23;
    game.Ground genVar510;
    genVar510=ground[genVar509];
    genVar510.fee=260000;
    int genVar511;
    genVar511=23;
    game.Ground genVar512;
    genVar512=ground[genVar511];
    int genVar513;
    genVar513=1;
    genVar512.owner=-genVar513;
    int genVar514;
    genVar514=23;
    game.Ground genVar515;
    genVar515=ground[genVar514];
    genVar515.price=120000;
    int genVar516;
    genVar516=24;
    game.Ground genVar517;
    genVar517=ground[genVar516];
    genVar517.fee=0;
    int genVar518;
    genVar518=24;
    game.Ground genVar519;
    genVar519=ground[genVar518];
    int genVar520;
    genVar520=1;
    genVar519.owner=-genVar520;
    int genVar521;
    genVar521=24;
    game.Ground genVar522;
    genVar522=ground[genVar521];
    genVar522.price=0;
    int genVar523;
    genVar523=25;
    game.Ground genVar524;
    genVar524=ground[genVar523];
    genVar524.fee=350000;
    int genVar525;
    genVar525=25;
    game.Ground genVar526;
    genVar526=ground[genVar525];
    int genVar527;
    genVar527=1;
    genVar526.owner=-genVar527;
    int genVar528;
    genVar528=25;
    game.Ground genVar529;
    genVar529=ground[genVar528];
    genVar529.price=150000;
  }
  public static int dice(){
    double genVar530;
    genVar530=Math.random();
    int genVar531;
    genVar531=6;
    double genVar532;
    genVar532=genVar530 * genVar531;
    int genVar533;
    genVar533=1;
    double genVar534;
    genVar534=genVar532 + genVar533;
    double genVar535;
    genVar535=(genVar534);
    int num;
    num=(int)genVar535;
    return num;
  }
  public static Point moving(  int num,  Point p){
    boolean genVar536;
    genVar536=0 <= num && num < 6;
    if (genVar536) {
      p.x-=80;
    }
 else {
      int genVar537;
      genVar537=6;
      boolean genVar538;
      genVar538=num == genVar537;
      if (genVar538) {
        p.x-=100;
      }
 else {
        int genVar539;
        genVar539=7;
        boolean genVar540;
        genVar540=num == genVar539;
        if (genVar540) {
          p.y-=100;
        }
 else {
          boolean genVar541;
          genVar541=8 <= num && num <= 12;
          if (genVar541) {
            p.y-=80;
          }
 else {
            int genVar542;
            genVar542=13;
            boolean genVar543;
            genVar543=num == genVar542;
            if (genVar543) {
              p.x+=100;
            }
 else {
              boolean genVar544;
              genVar544=14 <= num && num <= 19;
              if (genVar544) {
                p.x+=80;
              }
 else {
                boolean genVar545;
                genVar545=20 <= num && num < 25;
                if (genVar545) {
                  p.y+=80;
                }
 else {
                  int genVar546;
                  genVar546=25;
                  boolean genVar547;
                  genVar547=num == genVar546;
                  if (genVar547) {
                    p.y+=100;
                  }
 else {
                    java.lang.String genVar548;
                    genVar548="Game.moving() ERROR!";
                    System.out.println(genVar548);
                  }
                }
              }
            }
          }
        }
      }
    }
    return p;
  }
  public static Point movingBack(  int num,  Point p){
    boolean genVar549;
    genVar549=0 < num && num <= 6;
    if (genVar549) {
      p.x+=80;
    }
 else {
      int genVar550;
      genVar550=7;
      boolean genVar551;
      genVar551=num == genVar550;
      if (genVar551) {
        p.x+=100;
      }
 else {
        int genVar552;
        genVar552=8;
        boolean genVar553;
        genVar553=num == genVar552;
        if (genVar553) {
          p.y+=100;
        }
 else {
          boolean genVar554;
          genVar554=8 < num && num <= 13;
          if (genVar554) {
            p.y+=80;
          }
 else {
            int genVar555;
            genVar555=14;
            boolean genVar556;
            genVar556=num == genVar555;
            if (genVar556) {
              p.x-=100;
            }
 else {
              boolean genVar557;
              genVar557=14 < num && num <= 20;
              if (genVar557) {
                p.x-=80;
              }
 else {
                boolean genVar558;
                genVar558=21 <= num && num <= 25;
                if (genVar558) {
                  p.y-=80;
                }
 else {
                  int genVar559;
                  genVar559=0;
                  boolean genVar560;
                  genVar560=num == genVar559;
                  if (genVar560) {
                    p.y-=100;
                  }
 else {
                    java.lang.String genVar561;
                    genVar561="Game.movingBack() ERROR!";
                    System.out.println(genVar561);
                  }
                }
              }
            }
          }
        }
      }
    }
    return p;
  }
  public static int positionFormat(  int num){
    int genVar562;
    genVar562=26;
    boolean genVar563;
    genVar563=genVar562 <= num;
    if (genVar563) {
      num-=26;
    }
 else {
      int genVar564;
      genVar564=0;
      boolean genVar565;
      genVar565=num < genVar564;
      if (genVar565) {
        num+=26;
      }
 else {
        ;
      }
    }
    return num;
  }
  public static void goldmission(  User[] user,  int index,  int num,  JLabel[] horse,  JTextField[] moneyTextField,  RoomList rl){
switch (num) {
case 1:
      rl.sendMsg("move/" + rl.roomtitle + "/"+ rl.id+ "/-3");
    break;
case 2:
  int tmp=Game.positionFormat(7 - user[index].position);
rl.sendMsg("move/" + rl.roomtitle + "/"+ rl.id+ "/"+ tmp);
break;
case 5:
rl.sendMsg("move/" + rl.roomtitle + "/"+ rl.id+ "/4");
break;
case 6:
rl.sendMsg("move/" + rl.roomtitle + "/"+ rl.id+ "/6");
break;
case 7:
rl.sendMsg("move/" + rl.roomtitle + "/"+ rl.id+ "/2");
break;
case 8:
case 9:
break;
case 10:
if (user[index].money < 1000000) JOptionPane.showMessageDialog(null,"���� �����ϴ�!");
 else {
user[index].money-=1000000;
rl.sendMsg("money/" + rl.roomtitle + "/"+ index+ "/"+ user[index].money);
}
break;
case 3:
case 11:
tmp=26 - user[index].position;
rl.sendMsg("move/" + rl.roomtitle + "/"+ rl.id+ "/"+ tmp);
break;
case 4:
case 12:
user[index].money+=1000000;
rl.sendMsg("money/" + rl.roomtitle + "/"+ index+ "/"+ user[index].money);
break;
case 13:
if (user[index].money < 500000) JOptionPane.showMessageDialog(null,"���� �����ϴ�!");
 else {
user[index].money-=500000;
rl.sendMsg("money/" + rl.roomtitle + "/"+ index+ "/"+ user[index].money);
}
break;
case 14:
case 19:
if (user[index].money < 300000) JOptionPane.showMessageDialog(null,"���� �����ϴ�!");
 else {
user[index].money-=300000;
rl.sendMsg("money/" + rl.roomtitle + "/"+ index+ "/"+ user[index].money);
}
break;
case 15:
case 17:
user[index].money+=500000;
rl.sendMsg("money/" + rl.roomtitle + "/"+ index+ "/"+ user[index].money);
break;
case 16:
tmp=Game.positionFormat(20 - user[index].position);
rl.sendMsg("move/" + rl.roomtitle + "/"+ rl.id+ "/"+ tmp);
break;
case 18:
tmp=Game.positionFormat(25 - user[index].position);
rl.sendMsg("move/" + rl.roomtitle + "/"+ rl.id+ "/"+ tmp);
break;
}
}
public static void mission(User[] user,int index,JLabel[] horse,JTextField[] moneyTextField,RoomList rl){
switch (user[index].position) {
case 0:
break;
case 7:
break;
case 13:
break;
case 20:
break;
case 5:
case 11:
case 17:
case 24:
int num=(int)(Math.random() * 19 + 1);
UI.lblGoldkey.setIcon(new ImageIcon(UI.class.getResource("/image/gold_" + num + ".jpg")));
try {
Thread.sleep(2000);
}
 catch (InterruptedException e) {
e.printStackTrace();
}
UI.lblGoldkey.setIcon(new ImageIcon(UI.class.getResource("/image/gold.jpg")));
goldmission(user,index,num,horse,moneyTextField,rl);
break;
case 1:
case 2:
case 3:
case 4:
case 6:
case 8:
case 9:
case 10:
case 12:
case 14:
case 15:
case 16:
case 18:
case 19:
case 21:
case 22:
case 23:
case 25:
System.out.println("��ġ: " + user[index].position);
System.out.println("����: " + ground[user[index].position].owner);
if (ground[user[index].position].owner != index) {
if (ground[user[index].position].owner == -1) {
int result=0;
result=JOptionPane.showConfirmDialog(null,"�� ���԰����� " + ground[user[index].position].price + "�Դϴ�. �����Ͻðڽ��ϱ�?");
if (result == JOptionPane.YES_OPTION) {
if (ground[user[index].position].price > user[index].money) {
JOptionPane.showMessageDialog(null,"������ ���� �����ϴ�!");
}
 else {
user[index].money-=ground[user[index].position].price;
ground[user[index].position].owner=index;
rl.sendMsg("money/" + rl.roomtitle + "/"+ index+ "/"+ user[index].money);
}
}
 else if (result == JOptionPane.NO_OPTION) {
}
}
 else {
if (ground[user[index].position].fee > user[index].money) {
JOptionPane.showMessageDialog(null,"������ ���� �����ϴ�! �����? �Ļ공���ϴ�^^");
}
 else {
JOptionPane.showMessageDialog(null,"���� ������ " + ground[user[index].position].fee + " �Դϴ�");
user[index].money-=ground[user[index].position].fee;
user[ground[user[index].position].owner].money+=ground[user[index].position].fee;
rl.sendMsg("money/" + rl.roomtitle + "/"+ index+ "/"+ user[index].money);
rl.sendMsg("money/" + rl.roomtitle + "/"+ ground[user[index].position].owner+ "/"+ user[ground[user[index].position].owner].money);
}
}
}
break;
}
}
}
