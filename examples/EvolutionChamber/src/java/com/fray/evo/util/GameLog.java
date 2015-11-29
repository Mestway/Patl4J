package com.fray.evo.util;
import static com.fray.evo.ui.swingx.EcSwingXMain.messages;
import java.io.PrintStream;
import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;
public final class GameLog {
  public enum FailReason {  OutOfActions,   OverDrone,   Waypoint}
  public enum MessageType {  Evolved,   Mining,   Scout,   Obtained}
  private boolean on;
  private PrintStream log;
  public GameLog(){
    on=false;
    log=System.out;
  }
  public void setEnabled(  boolean on){
    com.fray.evo.util.GameLog genVar4296;
    genVar4296=this;
    genVar4296.on=on;
  }
  public void setPrintStream(  PrintStream stream){
    log=stream;
  }
  public boolean isEnabled(){
    return on;
  }
  public void printFailure(  FailReason r,  EcState des,  EcState curr){
    if (on) {
      java.lang.String genVar4297;
      genVar4297="Goal";
      String goal;
      goal=messages.getString(genVar4297);
      java.lang.String genVar4298;
      genVar4298="-------";
      java.lang.String genVar4299;
      genVar4299="-------";
      java.lang.String genVar4300;
      genVar4300=genVar4298 + goal + genVar4299;
      log.print(genVar4300);
      java.lang.String genVar4301;
      genVar4301=des.toUnitsOnlyString();
      log.println(genVar4301);
switch (r) {
case OutOfActions:
        log.println(messages.getString("OutOfActions"));
      break;
case OverDrone:
    log.println(messages.getString("FailedToHaveRequired") + " " + curr.getOverDrones(curr)+ " "+ messages.getString("waypoint.drones"));
  break;
case Waypoint:
log.println(messages.getString("FailedToMeetWaypoint"));
break;
}
boolean genVar4302;
genVar4302=curr != null;
if (genVar4302) {
java.lang.String genVar4303;
genVar4303=curr.toCompleteString();
log.println(genVar4303);
}
 else {
;
}
}
 else {
;
}
}
public void printWaypoint(int waypointIndex,EcState waypoint){
if (on) {
java.lang.String genVar4304;
genVar4304="---";
java.lang.String genVar4305;
genVar4305="Waypoint";
java.lang.String genVar4306;
genVar4306=messages.getString(genVar4305);
java.lang.String genVar4307;
genVar4307=" ";
java.lang.String genVar4308;
genVar4308="---";
java.lang.String genVar4309;
genVar4309=genVar4304 + genVar4306 + genVar4307+ waypointIndex+ genVar4308;
log.println(genVar4309);
java.lang.String genVar4310;
genVar4310=waypoint.toCompleteString();
log.println(genVar4310);
java.lang.String genVar4311;
genVar4311="----------------";
log.println(genVar4311);
}
 else {
;
}
}
public void printSatisfied(int actions,EcState curr,EcState des){
if (on) {
java.lang.String genVar4312;
genVar4312="Satisfied";
java.lang.String genVar4313;
genVar4313=messages.getString(genVar4312);
log.println(genVar4313);
java.lang.String genVar4314;
genVar4314="NumberOfActions";
java.lang.String genVar4315;
genVar4315=messages.getString(genVar4314);
java.lang.String genVar4316;
genVar4316=" ";
int genVar4317;
genVar4317=(actions);
java.lang.String genVar4318;
genVar4318=genVar4315 + genVar4316 + genVar4317;
log.println(genVar4318);
java.lang.String genVar4319;
genVar4319="-------";
java.lang.String genVar4320;
genVar4320="Goal";
java.lang.String genVar4321;
genVar4321=messages.getString(genVar4320);
java.lang.String genVar4322;
genVar4322="-------";
java.lang.String genVar4323;
genVar4323=genVar4319 + genVar4321 + genVar4322;
log.print(genVar4323);
java.lang.String genVar4324;
genVar4324=des.toUnitsOnlyString();
log.println(genVar4324);
java.lang.String genVar4325;
genVar4325="---";
java.lang.String genVar4326;
genVar4326="FinalOutput";
java.lang.String genVar4327;
genVar4327=messages.getString(genVar4326);
java.lang.String genVar4328;
genVar4328="---";
java.lang.String genVar4329;
genVar4329=genVar4325 + genVar4327 + genVar4328;
log.println(genVar4329);
java.lang.String genVar4330;
genVar4330=curr.toCompleteString();
log.println(genVar4330);
java.lang.String genVar4331;
genVar4331="------------------";
log.println(genVar4331);
}
 else {
;
}
}
public void printAction(EcBuildOrder bo,EcAction a){
if (on) {
java.lang.String genVar4332;
genVar4332=bo.toShortString();
java.lang.String genVar4333;
genVar4333="\t";
java.lang.String genVar4334;
genVar4334=genVar4332 + genVar4333 + a;
log.println(genVar4334);
}
 else {
;
}
}
public void printMessage(EcBuildOrder s,MessageType message,String string){
if (on) {
String messageString;
messageString=null;
switch (message) {
case Evolved:
messageString=messages.getString("Evolved");
break;
case Mining:
messageString=messages.getString("Mining");
break;
case Scout:
messageString=messages.getString("Scout");
break;
case Obtained:
messageString=messages.getString("Spawned");
break;
}
java.lang.String genVar4335;
genVar4335="@";
java.lang.String genVar4336;
genVar4336=s.timestamp();
java.lang.String genVar4337;
genVar4337="\t";
java.lang.String genVar4338;
genVar4338=": \t";
java.lang.String genVar4339;
genVar4339=string.trim();
java.lang.String genVar4340;
genVar4340=genVar4335 + genVar4336 + genVar4337+ messageString+ genVar4338+ genVar4339;
log.println(genVar4340);
}
 else {
;
}
}
}
