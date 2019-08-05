import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        BusinessCenter businessCenter =  new BusinessCenter();

        List<Thread> visitors = new ArrayList<>();

        Thread visitorThread;
        String threadName;

        ExecutorService exec = Executors.newFixedThreadPool(3);
        Future<?> v1;
        Future<?> v2;
        Future<?> v3;
/*        for (int i = 0; i < 3; i++) {
            threadName = "Visitor " + (i + 1);
            visitorThread = new Thread(new Visitor(businessCenter), threadName);
            visitors.add(visitorThread);
            visitors.get(i).start();*

            future = exec.submit(new Visitor(businessCenter));
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {}
        }*/
        v1 = exec.submit(new Visitor(businessCenter));
        v2 = exec.submit(new Visitor(businessCenter));
        v3 = exec.submit(new Visitor(businessCenter));

        try {
            v1.get();
            v2.get();
            v3.get();
        }
        catch (InterruptedException e) {}
        catch (ExecutionException e) {}



        exec.shutdown();

    }
}
