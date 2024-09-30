//TC: O(1)
//SC: O(n)
class PhoneDirectory {
    private Queue<Integer> availableNumbers;  // Queue to track available numbers
    private boolean[] used;                   // Boolean array to track used numbers
    private int maxNumbers;                   // Maximum number of numbers allowed

    // Constructor to initialize the phone directory
    public PhoneDirectory(int maxNumbers) {
        this.maxNumbers = maxNumbers;
        this.availableNumbers = new LinkedList<>();
        this.used = new boolean[maxNumbers];
        
        // Add all numbers from 0 to maxNumbers-1 to the available pool
        for (int i = 0; i < maxNumbers; i++) {
            availableNumbers.offer(i);
        }
    }

    // Provides a number that is not assigned to anyone. Returns -1 if no number is available.
    public int get() {
        if (availableNumbers.isEmpty()) {
            return -1;  // No numbers available
        }
        int number = availableNumbers.poll();
        used[number] = true;  // Mark the number as used
        return number;
    }

    // Checks if the number is available
    public boolean check(int number) {
        return number >= 0 && number < maxNumbers && !used[number];
    }

    // Releases a number back to the pool of available numbers
    public void release(int number) {
        if (number >= 0 && number < maxNumbers && used[number]) {
            used[number] = false;  // Mark the number as available
            availableNumbers.offer(number);  // Add it back to the available pool
        }
    }
}

/**
 * Your PhoneDirectory object will be instantiated and called as such:
 * PhoneDirectory obj = new PhoneDirectory(maxNumbers);
 * int param_1 = obj.get();
 * boolean param_2 = obj.check(number);
 * obj.release(number);
 */
