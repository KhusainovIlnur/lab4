import java.sql.Time;
import java.util.Random;

public class Visitor implements Runnable{
    private BusinessCenter place;
    private static int totalCount = 0; // счетчик посетителей
    private int num;
    private int floor = new Random().nextInt(10 - 1 + 1) + 1;

    public int randSleepTime = (new Random().nextInt(10 - 1 + 1) + 1) * 1000*2;

    public Visitor(BusinessCenter place) {
        this.place = place;
        num = ++totalCount;
    }

    @Override
    public void run() {
        enterBuilding();
        goUp();
        doSomeWork();
        goDown();
    }

    private synchronized void enterBuilding() {
        System.out.println(getTimer() + toString() + "входит");
        if (place.enterControl(this)) {
            place.passControl(this);
        }
    }

    private void goUp() {
        System.out.println(getTimer() + toString()  + "поднимается наверх");
        try {
            Thread.sleep(randSleepTime);
        }
        catch (InterruptedException e) {}
        System.out.println(getTimer() + toString()  + "поднялся наверх");
    }

    private void doSomeWork() {
        System.out.println(getTimer() + toString()  + "начинает что то делать");
        try {
            Thread.sleep(randSleepTime);
        }
        catch (InterruptedException e) {}
        System.out.println(getTimer() + toString()  + "закончил");
    }

    private void goDown() {
        System.out.println(getTimer() + toString()  + "спускается");
        try {
            Thread.sleep(randSleepTime);
        }
        catch (InterruptedException e) {}
        System.out.println(getTimer() + toString()  + "спустился");
    }

    public String getTimer() {
       return String.format("%6d ms:  ", place.getTimeDiffSec());
    }

    @Override
    public String toString() {
        return num + " посетитель ";
    }

}
