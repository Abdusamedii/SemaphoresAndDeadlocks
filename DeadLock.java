import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
    public class DeadLock {
        public int S = 1;
        public int Q = 1;
        public static void main(String[] args) throws IOException,InterruptedException{

            DeadLock m = new DeadLock();
        }
        public DeadLock(){
            testThread();
        }
        public void method1() throws InterruptedException{
            waitMedi();
            Thread.sleep(100);
            waitAlbi();

            System.out.println("Metoda 1 ne funksion");// pjesa kritike
            SignalA();
            Signal();
        }
        public void method2()throws InterruptedException{
            waitAlbi();
            Thread.sleep(100);
            waitMedi();
            System.out.println("Metoda 2 ne funksion"); // pjesa kritike
            Signal();
            SignalA();
        }
        public void waitMedi(){
            while (this.S<=0){

            }
            this.S = this.S - 1;
        }
        public void Signal(){
            this.S = this.S + 1;
        }
        public void waitAlbi(){
            while (this.Q<=0){

            }
            this.Q = this.Q - 1;
        }
        public void SignalA(){
            this.Q = this.Q + 1;
        }
        public void testThread()
        {

            //create a callable for each method
            Callable<Void> callable1 = new Callable<Void>()
            {
                @Override
                public Void call() throws Exception
                {
                    method1();
                    return null;
                }
            };

            Callable<Void> callable2 = new Callable<Void>()
            {
                @Override
                public Void call() throws Exception
                {
                    method2();
                    return null;
                }
            };


            //add to a list
            List<Callable<Void>> taskList = new ArrayList<Callable<Void>>();
            taskList.add(callable1);
            taskList.add(callable2);

            //create a pool executor with 3 threads
            ExecutorService executor = Executors.newFixedThreadPool(3);

            try
            {
                //start the threads and wait for them to finish
                executor.invokeAll(taskList);
            }
            catch (InterruptedException ie)
            {
                //do something if you care about interruption;
            }

        }


}
