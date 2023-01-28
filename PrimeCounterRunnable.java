import java.util.*;

public class PrimeCounterRunnable implements Runnable
{
    static int N;
    volatile Counter counter;
    volatile int primesFound;
    volatile long primesTotal;
    volatile List<Integer> primesList;

    PrimeCounterRunnable(int n)
    {
        N = n;
        counter = new Counter(n - 1);
        primesFound = 1;
        primesTotal = 2;
        primesList = new ArrayList<Integer>();
    }

    // Return list of primes
    public List<Integer> getPrimesList()
    {
        return primesList;
    }

    // Returns sum of primes
    public long getPrimesTotal()
    {
        return primesTotal;
    }

    // Returns total number of primes
    public int getPrimesFound()
    {
        return primesFound;
    }

    // Checks if 'n' is a prime integer in O(sqrt(n)) time
    private boolean isPrime(int n)
    {
        if (n == 2)
            return true;

        if (n % 2 == 0 || n < 2)
            return false;

        for (int i = 3; i <= Math.sqrt(n); i += 2)
        {
            if (n % i == 0)
                return false;
        }

        return true;
    }

    public void run () {
        int j;

        do
        {
            j = counter.getAndDecrement();

            // Check if prime and is valid number
            if (j >= 3 && isPrime(j))
            {
                // Thread safe prime accounting
                synchronized (this)
                {
                    primesTotal += j;
                    primesFound++;

                    if (primesList.size() < 10)
                        primesList.add(j);
                }
            }
        } while (j >= 3);
    }
}
