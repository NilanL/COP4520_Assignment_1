public class Counter {
  private long value;

  Counter(long value)
  {
    this.value = value;
  }

  // Threads-safe counter value getting and incrementing
  public long getAndIncrement()
  {
    synchronized (this) 
    {
      long temp = this.value;
      this.value = temp + 2;
      return temp;
    }
  }

  // Threads-safe counter value getting and decrementing
  public long getAndDecrement()
  {
    synchronized (this) 
    {
      long temp = this.value;
      this.value = temp - 2;
      return temp;
    }
  }
}
