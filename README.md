# Priority Number Tracker

## Overview
This project implements a **Producer-Consumer Algorithm with Priority Tracking**. The system consists of:

- **A Producer** – Generates a random number between **0 and 5** every second.
- **A Consumer** – Collects generated numbers within a specified time interval **T** and determines the most frequently occurring number within that interval.

## Mechanism
1. The **most frequent number** in each time interval is identified.
2. If the number has **not appeared in the previous consecutive interval**, it is pushed to a result list with an initial count of **1**.
3. If the number appears in a **non-consecutive interval**, its count is incremented (`count + 1`).
4. If the number appears **consecutively**, it is ignored.

## Example Execution
For an interval of **T = 1s**, given the following series of numbers:

1️⃣ **[5, 5, 5, 5, 5, 3]** → Most frequent: **5** → Pushed to list with `count = 1`  
2️⃣ **[5, 5, 4, 4, 5, 3]** → Most frequent: **5** → **Ignored (consecutive)**  
3️⃣ **[5, 5, 0, 3, 3, 3]** → Most frequent: **3** → Pushed to list with `count = 1`  
4️⃣ **[5, 5, 5, 5, 5, 3]** → Most frequent: **5** → **Count incremented (`count = 2`)**  
5️⃣ **[5, 5, 4, 4, 5, 3]** → Most frequent: **5** → **Ignored (consecutive)**

## Final Output
```json
[
  {
    "classIndex": 5,
    "count": 2
  },
  {
    "classIndex": 3,
    "count": 1
  }
]
```
## Explanation of Output
Each entry in the output represents a **most frequent number (classIndex)** along with its **occurrence count** in **non-consecutive intervals**.

- `"classIndex"` → The number that appeared most frequently in at least one time interval.
- `"count"` → The number of times this value was the most frequent in a **non-consecutive series**.

## Use Cases
This algorithm can be applied in various real-world scenarios, such as:
- **Real-time event prioritization** (e.g., tracking the most occurring error codes in logs).
- **Signal processing** (e.g., analyzing sensor data for dominant patterns).
- **Game mechanics** (e.g., detecting frequently pressed buttons in gameplay sessions).  
## Performance Considerations
- The algorithm processes data **in real time**, making it suitable for **low-latency applications**.
- It operates in **O(n) complexity** per interval, ensuring scalability for large datasets.

## Future Improvements
Potential enhancements for this system include:
- **Customizable time intervals** instead of a fixed 1-second window.
- **Support for different data sources**, such as streaming APIs.
- **Parallel processing** for handling higher frequency data streams.  
