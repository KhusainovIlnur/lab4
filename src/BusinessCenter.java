import javax.sound.midi.Soundbank;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BusinessCenter {
    private int liftFloor = 1;
    private Visitor visitorAtControl;
    private Visitor visitorInLift;
    boolean liftFree;
    private static long startTime = System.currentTimeMillis();

    private final Object lockP = new Object(); // для синхронизации проходной
    private final Object lockL = new Object(); // для синхронизации лифта

    ReentrantLock lockerP, lockerL;
    Condition conditionP, conditionL;

    public BusinessCenter() {
        liftFree = true;

        lockerP = new ReentrantLock(); // создаем блокировку
        conditionP = lockerP.newCondition(); // получаем условие, связанное с блокировкой

        lockerL = new ReentrantLock(); // создаем блокировку
        conditionL = lockerL.newCondition(); // получаем условие, связанное с блокировкой
    }

    public boolean enterControl(Visitor v) {
        lockerP.lock();
        if (visitorAtControl != null) {
            try {
                conditionP.await();
            } catch (InterruptedException e) { }
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
        lockerP.unlock();
    }

    public boolean  callLift(Visitor v) {
        lockerL.lock();
        if (!liftFree) {
            try {
                conditionL.await();
            } catch (InterruptedException e) { }
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
        lockerL.unlock();
    }

    public long getTimeDiffSec() {
        return Math.abs(startTime - System.currentTimeMillis());
    }

}
