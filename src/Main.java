import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        BusinessCenter businessCenter =  new BusinessCenter();

        List<Thread> visitors = new ArrayList<>();

        Thread visitorThread;
        String threadName;

        for (int i = 0; i < 3; i++) {
            threadName = "Visitor " + (i + 1);
            visitorThread = new Thread(new Visitor(businessCenter), threadName);
            visitors.add(visitorThread);
            visitors.get(i).start();

            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {}
        }

    }
}
