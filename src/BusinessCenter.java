import javax.sound.midi.Soundbank;
import java.util.Random;

public class BusinessCenter {
    private int liftFloor;
    private Visitor visitorAtControl;
    private Visitor visitorInLift;
    boolean liftFree;
    private static long startTime = System.currentTimeMillis();

    public BusinessCenter() {

    }

    public boolean enterControl(Visitor v) {
        if (visitorAtControl == null) {
            visitorAtControl = v;
            System.out.println(v.getTimer() + v.toString() + "проходную занял");
            return true;
        }
        else {
            System.out.println("проходная занята");
            return false;
        }
//        visitorAtControl = v;
//              return true;
//          }
//          else {
//              while (visitorAtControl != null) {
//                System.out.println(v + "wait");
//                    try {
//                        wait();
//                    }
//                    catch (InterruptedException e) {}
//              }
//
//              visitorAtControl = v;
//              System.out.println("Проходную занял " + visitorAtControl);
//              return true;
//          }
    }

    public void passControl(Visitor v) {
        if (visitorAtControl != v) {
            System.out.println("Проходная не была освобождена! ЗАЛЕЗ В НЕОЧЕРЕДИ!");
        }
        System.out.println(v.getTimer() + v.toString() + "показывает документы");
        try {
            Thread.sleep(v.randSleepTime);
        } catch (InterruptedException e) {}
        System.out.println(v.getTimer() + v.toString() + "документы проверены, вошел");
        visitorAtControl = null;
        System.out.println(v.getTimer() + v.toString() + "проходную освободил");
    }

    public boolean callLift(Visitor v) {

        // пока заглушка
        return true;
    }

    public void moveLift(Visitor v, int targetFloor) {

    }

    public void enterLift(Visitor v) {

    }

    public void exitLift(Visitor v) {

    }

    public long getTimeDiffSec() {
        return Math.abs(startTime - System.currentTimeMillis());
    }

}
