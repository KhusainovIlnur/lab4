import java.sql.Time;
import java.util.Random;

public class Visitor {
    private BusinessCenter place;
    private static int totalCount = 0; // счетчик посетителей
    private int num;
    private int floor;

    private int currentFloor;

    public int randSleepTime = (new Random().nextInt(10 - 1 + 1) + 1) * 1000;

    public Visitor(BusinessCenter place) {
        this.place = place;
        num = ++totalCount;
        floor = new Random().nextInt(10 - 1 + 1) + 1;
        currentFloor = 1;
    }

    public void run() {
//        enterBuilding();
//        goUp();
//        doSomeWork();
//        goDown();
    }

    public synchronized void enterBuilding() {
        System.out.println(getTimer() + toString() + "входит");
        if (place.enterControl(this)) {
            place.passControl(this);
        }
    }

    public void goUp() {
        if (place.callLift(this)) {
            place.moveLift(this, currentFloor);
            place.enterLift(this);
            place.moveLift(this, floor);
            place.exitLift(this);
        }
    }

    public void doSomeWork() {
        System.out.println(getTimer() + toString()  + "начинает что то делать");
        try {
            Thread.sleep(randSleepTime);
        }
        catch (InterruptedException e) {}
        System.out.println(getTimer() + toString()  + "закончил");
    }

    public void goDown() {
        if (place.callLift(this)) {
            place.moveLift(this, floor);
            place.enterLift(this);
            place.moveLift(this, 1);
            place.exitLift(this);
        }
    }

    public String getTimer() {
       return String.format("%6d ms:  ", place.getTimeDiffSec());
    }

    @Override
    public String toString() {
        return num + " посетитель ";
    }

}
