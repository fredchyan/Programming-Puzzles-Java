package com.company;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/*
Recursion
        Keep Distance For Identical Elements(C++ not ready)
        Given an integer k, arrange the sequence of integers [1, 1, 2, 2, 3, 3, ...., k - 1, k - 1, k, k], such that the output integer array satisfy this condition:

        Between each two i's, they are exactly i integers (for example: between the two 1s, there is one number, between the two 2's there are two numbers).

        If there does not exist such sequence, return null.

        Assumptions:

        k is guaranteed to be > 0
        Examples:

        k = 3, The output = { 2, 3, 1, 2, 1, 3 }.

        Approach:
        Generate the sequence 112233... and then permute it.
        1. Take care of deduplication when permuting sequence with duplicate elements.
        2. Eliminate invalid branch. When first time adding a number to the current search,
           put its index to a hash map. When the same number is encountered later on, make sure
           only recurse to the next level if the right index (current index) - left index - 1
           contains the same number(the number itself) it's supposed to contain.
*/

class SolutionKeepDistanceForIdenticalElements {
    private void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
    private int[] helper(int[] seq, int idx, HashMap<Integer, Integer> leftMap) {
        if (idx == seq.length) {
            return seq;
        }
        HashSet<Integer> dedup = new HashSet<Integer>();
        for (int i = idx; i < seq.length; i++) {
            if (!dedup.contains(seq[i])) {
                dedup.add(seq[i]);
                if (leftMap.containsKey(seq[i])) {
                    int leftIndex = leftMap.get(seq[i]);
                    int rightIndex = idx;
                    int numElems = rightIndex - leftIndex - 1;
                    swap(seq, idx, i);
                    if (numElems == seq[idx]) {
                        int[] res = helper(seq, idx + 1, leftMap);
                        if (res != null) {
                            return res;
                        }
                    }
                    swap(seq, idx, i);
                } else {
                    leftMap.put(seq[i], idx);
                    swap(seq, i, idx);
                    int[] res = helper(seq, idx + 1, leftMap);
                    if (res != null) {
                        return res;
                    }
                    swap(seq, i, idx);
                    leftMap.remove(seq[i]);
                }
            }
        }
        return null;
    }
    public int[] keepDistance(int k) {
        int[] seq = new int[2*k];
        for (int i = 0; i < seq.length; i++) {
            seq[i] = (i / 2) + 1;
        }
        HashMap<Integer, Integer> leftMap = new HashMap<Integer, Integer>();
        int[] res = helper(seq, 0, leftMap);
        return res;
    }
}

public class Main {
    public static void main(String[] args) {
        SolutionKeepDistanceForIdenticalElements sol1 = new SolutionKeepDistanceForIdenticalElements();
        int[] ans1 = sol1.keepDistance(3);
        System.out.println(Arrays.toString(ans1));
    }
}
