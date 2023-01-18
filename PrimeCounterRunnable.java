import java.util.*;

public class PrimeCounterRunnable implements Runnable
{
    static long N;
    volatile Counter counter;
    volatile long primesFound;
    volatile long primesTotal;
    volatile List<Long> primesList;

    PrimeCounterRunnable(long n)
    {
        N = n;
        counter = new Counter(n - 1);
        primesFound = 1;
        primesTotal = 2;
        primesList = new ArrayList<Long>();
    }

    // Return list of primes
    public List<Long> getPrimesList()
    {
        return primesList;
    }

    // Returns sum of primes
    public long getPrimesTotal()
    {
        return primesTotal;
    }

    // Returns total number of primes
    public long getPrimesFound()
    {
        return primesFound;
    }

    // Checks if 'n' is a prime integer in O(sqrt(n)) time
    private boolean isPrime(long n)
    {
        if (n % 2 == 0)
            return false;

        for (long i = 3; i <= Math.sqrt(n); i += 2)
        {
            if (n % i == 0)
                return false;
        }

        return true;
    }

    public void run () {
        long j;

        do
        {
            j = counter.getAndDecrement();

            // Check if prime and is valid number
            if (isPrime(j) && j >= 3)
            {
                // Thread safe prime accounting
                synchronized (this)
                {
                    primesTotal += j;
                    primesFound++;
                    primesList.add(j);
                }
            }
        } while (j >= 3);
    }
}