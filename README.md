# Lab 4: Last Stone Weight - PriorityQueue Analysis
**Course:** CS 302 – Advanced Data Structures and File Processing  
**Student:** Elliott [Last Name]

---

## 1. Problem Description
The goal of this lab is to simulate a "stone smashing" game where the two heaviest stones are repeatedly combined until at most one remains. 

### Rules:
- Select the two heaviest stones: $x$ and $y$ (where $x \le y$).
- If $x == y$, both are destroyed.
- If $x < y$, the new stone weight becomes $y - x$.
- Return the weight of the last stone, or $0$ if none remain.

---

## 2. Heap Implementation Logic
To meet the $O(n \log n)$ requirement, I utilized a **Max-Heap** (via Java’s `PriorityQueue`). 

### Why a Heap?
Using a simple array and sorting it every turn would result in a $O(n^2 \log n)$ complexity, which is unacceptable for large datasets. A Max-Heap allows us to:
1. **Extract the Maximum:** Access the heaviest stone in $O(1)$ and remove it in $O(\log n)$.
2. **Efficient Insertion:** Add the resulting stone back into the structure in $O(\log n)$.



### Step-by-Step Approach:
1. **Initialization:** Instantiate a `PriorityQueue` with `Collections.reverseOrder()` to convert the default Min-Heap into a Max-Heap.
2. **Loading:** Offer all elements from the `int[] stones` array into the heap.
3. **Simulation:** - While `heap.size() > 1`:
        - `poll()` twice to get the two heaviest stones.
        - If they aren't equal, `offer()` the difference back into the heap.
4. **Final Return:** If the heap is empty, return 0; otherwise, return the result of the final `peek()`.

---

## 3. Complexity Analysis

| Operation | Time Complexity |
| :--- | :--- |
| **Building the Heap** | $O(n \log n)$ |
| **Extracting 2 Stones** | $O(\log n)$ |
| **Inserting Result** | $O(\log n)$ |
| **Total Runtime** | $O(n \log n)$ |



---

## 4. Test Case Explanations
In `LastStoneLab.java`, the deterministic random test generator creates various scenarios to verify the implementation:
- **Empty/Single Element:** Ensuring the code doesn't crash on `stones.length < 2`.
- **Equal Weights:** Verifying that the heap correctly empties when all stones cancel each other out.
- **Large Weights:** Testing that the integer logic handles high-value weights without overflow.
