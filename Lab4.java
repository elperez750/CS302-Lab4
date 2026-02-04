import java.util.*;

/**
 * CS 302 -- Lab 4
 * Last Stone Weight (MUST use a heap in the student solution)
 *
 * This file includes:
 *  - 100,000 randomized test cases
 *  - A reference oracle using TreeMap (multiset) for validation
 *
 * Students should ONLY edit the "STUDENT CODE" section.
 */
public class Lab4 {
    // ---------------------------------------------------------------------
    // STUDENT CODE (edit below)
    // Requirement: MUST use a heap (PriorityQueue) to solve the problem.
    // Do NOT modify method signature.

    public static int lastStoneWeight(int[] stones) {
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

    // ---------------------------------------------------------------------
    // Do not change any of the code below!

    private static final int LabNo = 4;
    private static final Random rng = new Random(190718);
    private static final int NO_OF_LINES = 100000;

    /**
     * Runs one test case:
     * - computes expected answer using oracle (TreeMap multiset)
     * - compares to student solution
     */
    private static boolean testProblem(int[] stones) {
        int expected = oracleLastStoneWeight(stones);
        int actual = lastStoneWeight(stones); // student solution

        return expected == actual;
    }

    public static void main(String[] args) {
        System.out.println("CS 302 -- Lab " + LabNo);
        testProblems();
    }

    private static void testProblems() {
        System.out.println("-- -- -- -- --");
        System.out.println(NO_OF_LINES + " test cases for Lab " + LabNo + ".");

        boolean passedAll = true;

        long start = System.currentTimeMillis();
        for (int i = 1; i <= NO_OF_LINES; i++) {
            boolean passed = false;
            boolean exce = false;

            try {
                int[] testCase = createProblem(i);
                passed = testProblem(testCase);
            } catch (Exception ex) {
                passed = false;
                exce = true;
            }

            if (!passed) {
                System.out.println("Test " + i + " failed!" + (exce ? " (Exception)" : ""));
                // Print a small reproduction hint (not too big)
                int[] repro = createProblemRepro(i);
                System.out.println("Repro input (size=" + repro.length + "): " + Arrays.toString(repro));
                System.out.println("Expected: " + oracleLastStoneWeight(repro));
                System.out.println("Actual  : " + lastStoneWeight(repro));
                passedAll = false;
                break;
            }
        }

        System.out.println((System.currentTimeMillis() - start) + " ms");

        if (passedAll) {
            System.out.println("All tests passed.");
        }
    }

    /**
     * Oracle solution using TreeMap as a multiset.
     * This avoids using a heap, so it can validate a heap-based student solution independently.
     */
    private static int oracleLastStoneWeight(int[] stones) {
        TreeMap<Integer, Integer> multiset = new TreeMap<>();

        for (int w : stones) {
            if (w < 0) throw new IllegalArgumentException("Stone weights must be non-negative.");
            if (w == 0) continue; // optional: keep or ignore zeros; both are fine if consistent
            multiset.put(w, multiset.getOrDefault(w, 0) + 1);
        }

        while (countElements(multiset) > 1) {
            int y = pollMax(multiset);
            int x = pollMax(multiset);

            if (y != x) {
                int diff = y - x;
                if (diff != 0) {
                    multiset.put(diff, multiset.getOrDefault(diff, 0) + 1);
                }
            }
        }

        if (multiset.isEmpty()) return 0;
        return multiset.lastKey();
    }

    private static int countElements(TreeMap<Integer, Integer> ms) {
        // Since sizes are small (<= 200), this is fast enough and very clear.
        int total = 0;
        for (int c : ms.values()) total += c;
        return total;
    }

    private static int pollMax(TreeMap<Integer, Integer> ms) {
        int key = ms.lastKey();
        int count = ms.get(key);
        if (count == 1) ms.remove(key);
        else ms.put(key, count - 1);
        return key;
    }

    /**
     * Create one random test case.
     * - size grows with testNo, capped at 200
     * - weights are randomized but controlled (deterministic due to fixed seed)
     */
    private static int[] createProblem(int testNo) {
        int size = rng.nextInt(Math.min(200, testNo)) + 1; // 1..min(200,testNo)

        int[] stones = new int[size];

        // Weight range: tweak as you like. This range creates lots of duplicates (good for testing).
        // You can increase this to test larger numbers too.
        int maxWeight = 1000; // 0..999

        for (int i = 0; i < size; i++) {
            stones[i] = rng.nextInt(maxWeight);
        }

        return stones;
    }

    /**
     * Recreates the same test case for printing after a failure.
     * Because createProblem() consumes RNG state, we need a deterministic replay.
     */
    private static int[] createProblemRepro(int testNo) {
        Random replay = new Random(190718);

        int[] last = null;
        for (int i = 1; i <= testNo; i++) {
            int size = replay.nextInt(Math.min(200, i)) + 1;
            int[] stones = new int[size];
            int maxWeight = 1000;
            for (int j = 0; j < size; j++) stones[j] = replay.nextInt(maxWeight);
            last = stones;
        }
        return last;
    }

    
}
