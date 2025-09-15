/*
Name: Rowan Noel-Rickert
The purpose of this program is to take in string inputs one at a time and then search for a specific string in sorted order without sorting the array of strings
*/
import java.io.*;
import java.util.*;
import java.math.*;

public class stringSearch {
    public static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {

		//Asks user for how many strings they want to enter
        System.out.print("How many strings are in your array?  ");
        int size = Integer.parseInt(scanner.nextLine());
        String[] arrayOfString = new String[size];

		//Takes in "size" many strings from the user
        for (int i = 0; i < size; i++) {
            System.out.print("Please enter string " + (i + 1) + ":  ");
            arrayOfString[i] = scanner.nextLine();
        }

		//Asls user for int input on what they want
        System.out.print("Which string do you want from this array?  ");
        int targetIndex = Integer.parseInt(scanner.nextLine());

		//Prints out answer
        String answer = quickSelect(arrayOfString, arrayOfString.length, targetIndex);
        System.out.println("The string you were looking for is:  " + answer);
    }

	//Looks for required string
    public static String quickSelect(String[] array, int size, int k) {
		//if only one element in the array
        if (size == 1) {
            return array[0];
        }

		//computes the medians of subgroups in the array
        String[] medians = computeGroupMedians(array, size);
        String medianOfMedians = quickSelect(medians, medians.length, (medians.length + 1) / 2);

        String[] before = new String[size];
        String[] equal = new String[size];
        String[] after = new String[size];
        int beforeCount = 0, equalCount = 0, afterCount = 0;

		//Partitions the array into before, equal and after groups based around pivot
        for (String s : array) {
            int comparison = s.compareTo(medianOfMedians);
            if (comparison < 0) {
                before[beforeCount++] = s;
            } else if (comparison == 0) {
                equal[equalCount++] = s;
            } else {
                after[afterCount++] = s;
            }
        }

		//Recursively search in the correct group
        if (k <= beforeCount) {
            return quickSelect(sliceArray(before, beforeCount), beforeCount, k);
        } else if (k <= beforeCount + equalCount) {
            return equal[k - beforeCount - 1];
        } else {
            return quickSelect(sliceArray(after, afterCount), afterCount, k - beforeCount - equalCount);
        }
    }

	//Divides the array into groups of 5 and finds the median of each group
    public static String[] computeGroupMedians(String[] array, int size) {
        int groupCount = (size + 4) / 5;
        String[] medians = new String[groupCount];

		//computes the medians for each group of up to 5 elements
        for (int i = 0; i < size; i += 5) {
            int end = Math.min(i + 5, size);
            medians[i / 5] = findMedian(array, i, end);
        }

        return medians;
    }

	//Sorts the subgroup of strings and returns the median value
    public static String findMedian(String[] array, int start, int end) {
        String[] group = new String[end - start];
        System.arraycopy(array, start, group, 0, end - start);

		//Sort the group to find its median
        for (int i = 0; i < group.length - 1; i++) {
            for (int j = i + 1; j < group.length; j++) {
                if (group[i].compareTo(group[j]) > 0) {
                    String temp = group[i];
                    group[i] = group[j];
                    group[j] = temp;
                }
            }
        }
        return group[(group.length - 1) / 2];
    }

	//Creates a new array containing the first newsize elements of the original array
    public static String[] sliceArray(String[] array, int newSize) {
        String[] result = new String[newSize];
        System.arraycopy(array, 0, result, 0, newSize);
        return result;
    }


}
