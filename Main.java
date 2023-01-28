import java.util.*;
import java.io.*;

public class Main {
    double execTime;
    int primesFound;
    long primesTotal;
    List<Integer> primesList;
    Thread [] threads;

    Main()
    {
        execTime = 0;
        primesFound = 0;
        primesTotal = 0;
        primesList = new ArrayList<Integer>();
        threads = new Thread [8];
    }

    // Begins the prime accounting process
    public void countPrimes(int n)
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

        // Get accounted values
        primesFound = runnable.getPrimesFound();
        primesTotal = runnable.getPrimesTotal();

        var list = runnable.getPrimesList();
        list.add(2);
        Collections.sort(list);

        if (list.size() >= 10)
        {
            primesList = list.subList(list.size() - 10, list.size());
        }
        else
        {
            primesList = list;
        }
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
            for (long prime : primesList)
            {
                writer.write(prime + " ");
            }
            writer.write("]");

            System.out.println("----> Prime Counting for 10^8 Done in " + execTime + " milliseconds");
            System.out.println("----> See primes.txt");

            writer.close();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void printResultTest(FileWriter writer, int exp)
    {
        try
        {
            writer.write("10^" + exp + ": ");
            writer.write(execTime + "ms " + primesFound + " " + primesTotal);
            writer.write(" [ ");
            for (long prime : primesList)
            {
                writer.write(prime + " ");
            }
            writer.write("]\n");
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    // Will test primes from 10^1 to 10^8
    public void testSuite()
    {
        long startSuiteTime = System.nanoTime();

        FileWriter writer = null;

        try
        {
            File file = new File("primes_test.txt");
            
            if (file.exists())
            {
                file.delete();
            }
            
            file.createNewFile();

            writer = new FileWriter("primes_test.txt");
            
            for (int i = 1; i <= 8; i++)
            {
                Main temp = new Main();

                temp.countPrimes((int)Math.pow(10,i));
                temp.printResultTest(writer, i);
            }

            double endSuiteTime = (System.nanoTime() - startSuiteTime) / 1000000.0;

            System.out.println("----> Testing Suite Done in " + endSuiteTime + " milliseconds");
            System.out.println("----> See primes_test.txt");

            writer.close();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            writer = null;
            System.out.println("ERROR - Suite could not run");
        }

    }

    public static void main(String[] args) throws Exception {
        Main assignment1 = new Main();

        if (args == null || args.length == 0 || !args[0].equals("test"))
        {
            assignment1.countPrimes((int)Math.pow(10,8));
            assignment1.printResult();
        }
        else
        {
            assignment1.testSuite();
        }
    }
}
