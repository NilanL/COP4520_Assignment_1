# COP4520 Assignment 1

## Methodology

The approach for isPrime() was similar to the approach found here: https://www.geeksforgeeks.org/prime-numbers/.
The parallelization was based on the textbook's approach, as well the approach shown in the slides/class.
</br></br>

## Testing

To verify the total number of primes, values from 10^1 to 10^8 were fed into the program through a test suite and compared with the list of prime totals from Wolfram Mathworld: https://mathworld.wolfram.com/PrimeCountingFunction.html.</br>
The list of largest primes as well as prime sums were verified for values 10^1 and 10^2, which were hand checked and verified.
Each value was also tested a minimum of 5 times to ensure that the values did not change among multiple runs. If they had changed, then that would mean the program had a mutual exclusion issue, with threads read/writing over each other causing the occasional result shifts.
</br></br>
The effective use of multiple threads was verified with modification of thread configurations, with ~6.5 seconds being clocked with 8 threads, 4 threads resulting in ~7 seconds, 2 threads resulting in ~13 seconds, and 1 thread resulting in ~20 seconds. This showed an obvious decrease in runtime efficiency in-line with decreased parallelization. I also checked my windows task manager to ensure that 8 cores were being utilized by the program. This was confirmed with 8 physical cores being used at around 80-100%, in line with the 8 generated threads of the assignment.
</br></br>

## Running

### Normal
1. Pull repo
2. Navigate to local repo directory
3. Run command "javac Main.java"
4. Run command "java Main"
5. Should take between 6 to 22 seconds for N = 10^8 (Depends on processor)
6. Find results in "primes.txt" that will be created in the repo directory

### Test Suite
1. Pull repo
2. Navigate to local repo directory
3. Run command "javac Main.java"
4. To run test suite for primes of 10^1 to 10^8, run command "java Main test"
5. Find results in "primes_test.txt" that will be created in the repo directory

