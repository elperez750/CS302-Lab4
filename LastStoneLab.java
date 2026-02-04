import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

public class LastStoneLab {

    /**
     * Returns the weight of the last remaining stone (or 0 if none remain).
     * REQUIRED: must use a heap.
     *
     * Time complexity: O(n log n) due to heap operations.
     */
    public static int lastStoneWeight(int[] stones) {
        // Copy your solution to here for Unit Test
        //heap - PriorityQueue maxHeap
        // insert element to heap (build heap)
        //poll max 2 elements, difference
        // add difference
        //return maxHeap.isEmpty()? 0 : maxHeap.peek();

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

        for (int stone : stones) {
            maxHeap.add(stone);
        }

        while (maxHeap.size() > 1) {
            int bigStone = maxHeap.poll();
            int smallStone = maxHeap.poll();
            if (bigStone != smallStone) {
                maxHeap.add(bigStone - smallStone);
            }
        }

        if (maxHeap.isEmpty()) {
            return 0;
        }

        return maxHeap.peek();


    }

    // ---------- Testing Utilities ----------

    private static void assertEquals(String testName, int expected, int actual) {
        if (expected == actual) {
            System.out.println("[PASS] " + testName + " -> " + actual);
        } else {
            System.out.println("[FAIL] " + testName + " -> expected " + expected + ", got " + actual);
        }
    }

    private static void runTests() {
        // 1) Example from prompt
        assertEquals("Example 1",
                1,
                lastStoneWeight(new int[]{2, 7, 4, 1, 8, 1})
        );
        // Explanation:
        // Start heap contains {8,7,4,2,1,1}
        // Poll 8 and 7 → add 1
        // Heap {4,2,1,1,1}
        // Poll 4 and 2 → add 2
        // Heap {2,1,1,1}
        // Poll 2 and 1 → add 1
        // Heap {1,1,1}
        // Poll 1 and 1 → add nothing
        // Heap {1}
        // Return 1.


        // 2) Single element
        assertEquals("Example 2",
                1,
                lastStoneWeight(new int[]{1})
        );
        //Explanation:
        // Start heap contains {1}
        // Since heap has one item, it will not enter the while loop
        // It will return 1

        // 3) Empty
        assertEquals("Empty array",
                0,
                lastStoneWeight(new int[]{})
        );
        //Explanation:
        // Start heap contains no elements
        // Since size is less than 1, we don't enter the while loop
        // We return 0 since maxHeap.isEmpty() is true


        // 4) All equal (all cancel out)
        assertEquals("All equal even count",
                0,
                lastStoneWeight(new int[]{5, 5, 5, 5})
        );
        //Explanation:
        // Start heap contains {5, 5, 5, 5}
        // Poll 5 and 5 → destroy both.
        // Heap is {5, 5}
        // Poll 5 and 5 → destroy both
        // Heap is {}
        // Since our size is less than one on next iteration, we exit while loop
        // We return 0 since maxHeap.isEmpty() is true.


        // 5) All equal (odd count leaves one)
        assertEquals("All equal odd count",
                5,
                lastStoneWeight(new int[]{5, 5, 5})
        );
        //Explanation:
        // Start heap contains {5, 5, 5}
        // Poll 5 and 5 → destroy both
        // Heap is {5}
        // Since our size is 1, we exit the while loop
        // Return the only element in the heap, which is 5


        // 6) Already sorted
        assertEquals("Already sorted",
                0,
                lastStoneWeight(new int[]{1, 2, 3, 6})
        );
        // Explanation:
        // Start heap contains {1, 2, 3, 6}
        // Poll 6 and 3 → add 3
        // Heap is {1, 2, 3}
        // Poll 3 and 2 → add 1
        // Heap is {1, 1}
        // Poll 1 and 1 → destroy both
        // We exit the while loop since our size is less than 1
        // we return 0 since the maxHeap is empty
        

        // 7) Many duplicates + cancellations
        assertEquals("Duplicates with cancellations",
                2,
                lastStoneWeight(new int[]{10, 10, 8, 8, 6, 6, 2})
        );
        //Explanation:
        // Start heap contains {10, 10, 8, 8, 6, 6, 2}
        // Poll 10 and 10 → destroy both
        // Heap is {8, 8, 6, 6, 2}
        // Poll 8 and 8 → destroy both
        // Heap is {6, 6, 2}
        // Poll 6 and 6 → destroy both
        // Since size is 1, we exit the while loop
        // We return the only element remaining, which is 2

        // 8) Large values
        assertEquals("Large values",
                1,
                lastStoneWeight(new int[]{1_000_000_000, 999_999_999})
        );
        //Explanation:
        // Start heap is {1000000000, 999999999}
        // Poll 1000000000 and 999999999 → add 1
        // Heap is {1}
        // Since the size is 1, we exit the while loop
        // We return the only item in heap, which is 1


        // 9) Random small
        assertEquals("Random small",
                1,
                lastStoneWeight(new int[]{3, 3, 2, 1})
        );
        //Explanation:
        // Start heap is {3, 3, 2, 1}
        // Poll 3 and 3 → destroy both
        // Heap is {2, 1}
        // Poll 2 and 1 → add 1
        // Heap is {1}
        // Since the size of heap is 1, we exit while loop
        // We return the only item in the heap, which is 1
        

        // 10) Contains zeros (allowed if you want; keep consistent with your rules)
        assertEquals("Zeros included",
                0,
                lastStoneWeight(new int[]{0, 0, 0})
        );
        //Explanation:
        // Start heap is {0, 0, 0}
        // Poll 0 and 0 → destroy both
        // Heap is {0}
        // Since the size of the heap is 1, we exit the while loop
        // We return the only item in the heap, which is 0
    }

    public static void main(String[] args) {
        System.out.println("Running LastStoneLab tests...\n");
        runTests();

        // Optional: quick demo
        int[] demo = {2, 7, 4, 1, 8, 1};
        System.out.println("\nDemo input: " + Arrays.toString(demo));
        System.out.println("Demo output: " + lastStoneWeight(demo));
    }
}
