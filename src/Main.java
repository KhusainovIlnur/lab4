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
        CompletableFuture<?> v1;
        CompletableFuture<?> v2;
        CompletableFuture<?> v3;

        Visitor vis1 = new Visitor(businessCenter);
        Visitor vis2 = new Visitor(businessCenter);
        Visitor vis3 = new Visitor(businessCenter);

        v1 = CompletableFuture.runAsync(vis1::enterBuilding).thenRun(vis1::goUp).thenRun(vis1::doSomeWork).thenRun(vis1::goDown);
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
        v2 = CompletableFuture.runAsync(vis2::enterBuilding).thenRun(vis2::goUp).thenRun(vis2::doSomeWork).thenRun(vis2::goDown);
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
        v3 = CompletableFuture.runAsync(vis3::enterBuilding).thenRun(vis3::goUp).thenRun(vis3::doSomeWork).thenRun(vis3::goDown);

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
