import javax.sound.midi.Soundbank;
import java.util.Random;

public class BusinessCenter {
    private int liftFloor = 1;
    private Visitor visitorAtControl;
    private Visitor visitorInLift;
    boolean liftFree;
    private static long startTime = System.currentTimeMillis();

    private final Object lockP = new Object(); // для синхронизации проходной
    private final Object lockL = new Object(); // для синхронизации лифта


    public BusinessCenter() {
        liftFree = true;
    }

    public boolean enterControl(Visitor v) {
        if (visitorAtControl != null) {
            synchronized (lockP) {
                try {
                    lockP.wait();
                } catch (InterruptedException e) {
                }
            }
        }

        visitorAtControl = v;
        System.out.println(v.getTimer() + v.toString() + "проходную занял");
        return true;
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
        System.out.println(v.getTimer() + v.toString() + "проходную освободил");

        visitorAtControl = null;
        synchronized (lockP) {
            lockP.notify();
        }
    }

    public boolean  callLift(Visitor v) {
        if (!liftFree) {
            synchronized (lockL) {
                try {
                    lockL.wait();
                } catch (InterruptedException e) {
                }
            }
        }

        liftFree = false;
        System.out.println(v.getTimer() + v.toString() + "вызвал лифт - свободен");
        return true;
    }

    public void moveLift(Visitor v, int targetFloor) {
        System.out.print(v.getTimer() + v.toString() + "лифт едет  ");

        if (liftFloor < targetFloor)
            for (int i = liftFloor; i <= targetFloor; i++) {
                System.out.print(i + " ");
                try {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e) {}
            }
        else
            for (int i = liftFloor; i >= targetFloor; i--) {
                System.out.print(i + " ");
                try {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e) {}
            }
        System.out.println("этаж");
        liftFloor = targetFloor;

    }

    public void enterLift(Visitor v) {
        System.out.println(v.getTimer() + v.toString() + "вошел в лифт");

    }

    public void exitLift(Visitor v) {
        System.out.println(v.getTimer() + v.toString() + "вышел из лифта");
        liftFree = true;
        synchronized (lockL) {
            lockL.notify();
        }
    }

    public long getTimeDiffSec() {
        return Math.abs(startTime - System.currentTimeMillis());
    }

}
