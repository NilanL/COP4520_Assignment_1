import java.util.*;
import java.io.*;

public class Main {
    double execTime;
    long primesFound;
    long primesTotal;
    List<Long> topTenList;
    Thread [] threads;

    Main()
    {
        execTime = 0;
        primesFound = 0;
        primesTotal = 0;
        topTenList = new ArrayList<Long>();
        threads = new Thread [8];
    }

    // Begins the prime accounting process
    public void count(long n)
    {
        long startTime = System.nanoTime();

        PrimeCounterRunnable runnable = new PrimeCounterRunnable(n);

        // Generate threads
        for (int i = 0; i < threads.length; i++)
        {
            threads[i] = new Thread(runnable);
        }

        // Start threads
        for (int i = 0; i < threads.length; i++)
        {
            threads[i].start();
        }

        // Join threads
        for (int i = 0; i < threads.length; i++)
        {
            try
            {
                threads[i].join();
            }
            catch (InterruptedException e)
            {
                System.out.println(e.getMessage());
                return;
            }
        }

        execTime = (System.nanoTime() - startTime) / 1000000.0;

        primesFound = runnable.getPrimesFound();
        primesTotal = runnable.getPrimesTotal();

        // Get list of 10 largest primes
        var list = runnable.getPrimesList();
        Collections.sort(list);
        topTenList = list.subList(list.size() - 10, list.size());
    }

    // Writes results into primes.txt
    public void printResult()
    {
        try
        {
            File file = new File("primes.txt");
            
            if (file.exists())
            {
                file.delete();
            }

            file.createNewFile();

            FileWriter writer = new FileWriter("primes.txt");

            writer.write(execTime + "ms " + primesFound + " " + primesTotal);
            writer.write(" [ ");
            for (long prime : topTenList)
            {
                writer.write(prime + " ");
            }
            writer.write("]");

            writer.close();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) throws Exception {
        Main assignment1 = new Main();
        assignment1.count((long)Math.pow(10, 8));
        assignment1.printResult();
    }
}