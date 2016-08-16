package com.msu_software_factory.thesortingguide;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class Sorting {
    static Random randomgenerator = new Random();
    static SortSteps sortSteps;
    static int sortSize = 6;

    public static int[] randList(int size){
        int[] newlist = new int[sortSize];
        for (int i = 0; i < newlist.length; i++) {
            int randomInt = randomgenerator.nextInt(99);
            newlist[i] = randomInt;
        }
        return newlist;
    }

    public static String toString(int[] list){
        String out = "[ ";
        for (int i = 0; i < list.length; i++) {
            out += list[i] + " ";
        }
        return out + "]";
    }

    // BUBBLE
    public static int[] bubbleSort(int[] unsorted){
        sortSteps = new SortSteps();
        Step step;
        for (int i = 1; i < unsorted.length; i++) {
            for (int j = 0; j < unsorted.length -i; j++) {
                if (unsorted[j] > unsorted[j+1]){
                    step = new Step();
                    step.arrayBefore = unsorted.clone();
                    step.type = "swap";
                    step.start = j;
                    step.end = j+1;
                    int temp = unsorted[j];
                    unsorted[j] = unsorted[j+1];
                    unsorted[j+1] = temp;
                    step.arrayAfter = unsorted.clone();
                    sortSteps.addStep(step);
                } else {
                    step = new Step();
                    step.arrayBefore = step.arrayAfter = unsorted.clone();
                    step.type = "compare";
                    step.start = j;
                    step.end = j+1;
                    sortSteps.addStep(step);
                }
            }
        }
        return unsorted;
    }

    // SELECTION
    public static int[] selectionSort(int[] unsorted){
        sortSteps = new SortSteps();
        Step step;
        for (int i = 0; i < unsorted.length; i++) {
            int min = i;
            for (int j = i +1; j <= unsorted.length - 1; j++) {
                if (unsorted[j] < unsorted[min]){
                    min = j;
                }
                step = new Step();
                step.arrayBefore = step.arrayAfter = unsorted.clone();
                step.type = "compare";
                step.start = j;
                step.end = min;
                sortSteps.addStep(step);
            }
            if (min != i){
                step = new Step();
                step.arrayBefore = unsorted.clone();
                step.type = "swap";
                step.start = min;
                step.end = i;
                int temp = unsorted[min];
                unsorted[min] = unsorted[i];
                unsorted[i] = temp;
                step.arrayAfter = unsorted.clone();
                sortSteps.addStep(step);
            }
        }
        return unsorted;
    }

    // INSERTION
    public static int[] insertionSort(int[] unsorted){
        sortSteps = new SortSteps();
        Step step;
        for (int i = 1; i < unsorted.length; i++) {
            for(int j = i; j > 0 && unsorted[j] < unsorted[j-1]; j--) {
                step = new Step();
                step.arrayBefore = unsorted.clone();
                step.type = "swap";
                step.start = j;
                step.end = j-1;
                int temp = unsorted[j];
                unsorted[j] = unsorted[j-1];
                unsorted[j-1] = temp;
                step.arrayAfter = unsorted.clone();
                sortSteps.addStep(step);
            }
        }
        return unsorted;
    }
    public static int[] startQuickSort(int[] num) {
        sortSteps = new SortSteps();

        return quickSort(num, 0, num.length - 1);
    }

    public static int[] quickSort(int[] num, int low, int high) {
        int pivot_point;
        if (low < high) {
            pivot_point = partition(num, low, high);
            num = quickSort(num, low, pivot_point - 1);
            num = quickSort(num, pivot_point + 1, high);
        }
        return num;
    }

    public static int partition(int[] num, int low, int high) {
        int pivot = num[high];
        int leftwall = low;
        Step step;

        for (int i = low; i < high; i++) {
            if (num[i] <= pivot) {
                step = new Step();
                step.arrayBefore = num.clone();
                step.type = "swap";
                step.start = i;
                step.end = leftwall;

                swap(num, i, leftwall);
                step.arrayAfter = num.clone();
                    if (leftwall != i) {
                        sortSteps.addStep(step);
                    }
                    leftwall++;
            }
        }
        if (leftwall != high) {

            step = new Step();
            step.arrayBefore = num.clone();
            step.type = "swap";
            step.start = high;
            step.end = leftwall;

            swap(num, high, leftwall);
            step.arrayAfter = num.clone();
            sortSteps.addStep(step);
        }
        return leftwall;
    }

    public static void swap(int[] num, int idex1, int idex2) {
        int temp = num[idex1];
        num[idex1] = num[idex2];
        num[idex2] = temp;
    }
    /*
        QuickSort ends here
     */

    /*
     Start here to grab all methods used for the merge sort algorithm
     */
    private static Integer[] getleft(Integer[] num){
        Integer[]left=new Integer[num.length/2];
        for (int i = 0; i < left.length; i++) {
            left[i]=num[i];
        }
        return left;
    }
    private static boolean isOdd(Integer num){
        if (num %2==1)
            return true;
        else return false;

    }
    private static Integer[] getright(Integer[] num){
        Integer[]right;
        if (isOdd(num.length)){
            right=new Integer[num.length/2+1];
        } else {
            right = new Integer[num.length / 2];
        }
        for (int i = 0; i < right.length; i++) {
            right[i]=num[i + (num.length/2)];
        }
        return right;
    }

    public static Integer[] mergeSort(Integer[] num){
        sortSteps = new SortSteps();
        if ( num.length == 1 )
            return num;
        Integer[] left = mergeSort(getleft(num));
        Integer[] right = mergeSort(getright(num));
        return merge(left,right);
    }

    private static Integer[] merge(Integer[] first, Integer[] second){
        ArrayList<Integer>list=new ArrayList<>();
        ArrayList<Integer>l1=new ArrayList<Integer>(Arrays.asList((first)));
        ArrayList<Integer>l2=new ArrayList<Integer>(Arrays.asList((second)));
        while (!l1.isEmpty() && !l2.isEmpty()){
            if (l1.get(0)>l2.get(0)){
                list.add(l2.remove(0));
            }else {
                list.add(l1.remove(0));
            }
        }
        while (!l1.isEmpty()) {
            list.add(l1.remove(0));
        }
        while (!l2.isEmpty()) {
            list.add(l2.remove(0));
        }
        return list.toArray(new Integer[list.size()]);
    }
    /*
        The end of the merge sort methods
     */

}

class SortSteps {
    LinkedList<Step> steps;

    public SortSteps() {
        steps = new LinkedList<Step>();
    }

    public void addStep(Step next) {
        steps.add(next);
    }

    public String toString() {
        String out = "Steps:";
        for (Step step : steps) {
            out += "\n\t" + step;
        }
        return out;
    }
}

class Step {
    // array before and array after can be removed later, but for now they are helpful for debugging
    int[] arrayBefore;
    int[] arrayAfter;
    // start and end are the starting and ending indexes of the moving value
    int start;
    int end;

    String type;

    public Step() {}

    public String toString() {
        String out = "Step:";
        out += "\n\t " + Sorting.toString(arrayBefore);
        out += "\n\t " + Sorting.toString(arrayAfter);
        out += "\n\t " + type;
        out += "\n\t Start: " + start;
        out += "\n\t End:   " + end;

        return out;
    }
}