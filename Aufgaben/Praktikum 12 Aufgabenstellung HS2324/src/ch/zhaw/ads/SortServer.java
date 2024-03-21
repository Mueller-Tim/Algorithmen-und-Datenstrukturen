package ch.zhaw.ads;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SortServer implements CommandExecutor {
    private final int DATARANGE = 10000000;
    public int dataElems; // number of data
    public int insertion_threshold = 50;

    private void bubbleSort(int[] a) {
        for (int k = a.length - 1; k > 0; k--) {
            boolean noSwap = true;
            for (int i = 0; i < k; i++) {
                if (a[i] > a[i + 1]) {
                    swap(a, i, i + 1);
                    noSwap = false;
                }
            }
            if (noSwap) {
                break;
            }
        }
    }

    private void insertionSort(int[] a) {
        for (int k = 1; k < a.length; k++) {
            if (a[k] < a[k - 1]) {
                int x = a[k];
                int i;
                for (i = k; ((i > 0) && (a[i - 1] > x)); i--) {
                    a[i] = a[i - 1];
                }
                a[i] = x;
            }
        }
    }

    private void insertionSort(int[] a, int l, int r) {
        for (int k = l + 1; k < r + 1; k++) {
            if (a[k] < a[k - 1]) {
                int x = a[k];
                int i;
                for (i = k; ((i > 0) && (a[i - 1] > x)); i--) {
                    a[i] = a[i - 1];
                }
                a[i] = x;
            }
        }
    }

    private void selectionSort(int[] a) {
        for (int k = 0; k < a.length; k++) {
            int min = k;
            for (int i = k + 1; i < a.length; i++) {
                if (a[i] < a[min]) {
                    min = i;
                }
            }
            if (min != k) {
                swap(a, min, k);
            }
        }
    }

    private void streamSort(int[] a) {
        int[] b = Arrays.stream(a).sorted().toArray();
        System.arraycopy(b, 0, a, 0, a.length);
    }

    void quickerSort(int[] a){
        quickerSort(a, 0, a.length-1);
    }

    private void quickerSort(int[] arr, int left, int right) {
        // Todo Aufgabe 12.1
        if(left < right) {
            moveMedianValueToTheRight(arr, left, right);
            int indexPivot = partition(arr, left, right);
            quickerSort(arr, left, indexPivot - 1);
            quickerSort(arr, indexPivot + 1, right);
        }
    }

    private int partition (int[] arr, int left, int right) {
        // Todo Aufgabe 12.1
        int pivotValue = arr[right];
        int i = left;
        for(int j = left; j < right; j++) {
            if(arr[j] < pivotValue) {
                swap(arr, i, j);
                i++;
            }
        }
        swap(arr, i, right);
        return i;
    }

    private void moveMedianValueToTheRight(int[] arr, int left, int right)
    {
        if (right - left < 5) return;
        int mid = (right + left) / 2;
        if (arr[left] <= arr[mid] && arr[mid] <= arr[right] || arr[right] <= arr[mid] && arr[mid] <= arr[left]) {
            swap(arr, mid, right);
            return;
        } else if (arr[mid] <= arr[left] && arr[left] <= arr[right] || arr[right] <= arr[left] && arr[left] <= arr[mid]) {
            swap(arr, left, right);
            return;
        } else {
            return;
        }
    }

    private boolean isSorted(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            if (a[i] > a[i + 1]) {
                return false;
            }
        }
        return true;
    }

    public int[] randomData() {
        int[] a = new int[dataElems];
        for (int i = 0; i < a.length; i++) {
            a[i] = (int) (Math.random() * DATARANGE);
        }
        return a;
    }

    public int[] ascendingData() {
        int[] a = new int[dataElems];
        for (int i = 0; i < a.length; i++) {
            a[i] = i;
        }
        return a;
    }

    public int[] descendingData() {
        int[] a = new int[dataElems];
        for (int i = 0; i < a.length; i++) {
            a[i] = a.length - i;
        }
        return a;
    }

    private void swap(int[] a, int i, int j) {
        int h = a[i];
        a[i] = a[j];
        a[j] = h;
    }

    // measure time of sorting algorithm
    // generator to generate the data
    // consumer sorts the data
    // return elapsed time in ms
    // if data is not sorted an exception is thrown
    private double measureTime(Supplier<int[]> generator, Consumer<int[]> sorter) throws Exception {
        double elapsed = 0;

        int[] a = generator.get();
        int[] b = new int[dataElems];

        long startTime = System.currentTimeMillis();
        long endTime = startTime;
        int count = 0;
        while (endTime < startTime + 10000) {
            System.arraycopy(a, 0, b, 0, a.length);
            sorter.accept(b);
            count++;
            endTime = System.currentTimeMillis();
        }
        elapsed = (double)(endTime - startTime) / count;
        if (!isSorted(b)) throw new Exception ("ERROR not sorted");
        return elapsed;
    }

    public String execute(String arg)  {
        Map<String, Consumer<int[]>> sorter = Map.of(
                "BUBBLE", this::bubbleSort,
                "INSERTION", this::insertionSort,
                "SELECTION", this::selectionSort,
                "STREAM", this::streamSort,
                "QUICKERSORT", this::quickerSort
        );
        Map<String, Supplier<int[]>> generator =  Map.of(
                "RANDOM", this::randomData,
                "ASC", this::ascendingData,
                "DESC", this::descendingData
        );

        String[] args = arg.toUpperCase().split(" ");
        dataElems = Integer.parseInt(args[2]);
        if (args.length >= 4) insertion_threshold = Integer.parseInt(args[3]);
        try {
            double time = measureTime(generator.get(args[1]), sorter.get(args[0]));
            return arg + " " + time + " ms\n";
        } catch (Exception ex) {
            return arg + " " + ex.getMessage();
        }
    }

    public static void main(String[] args) {
        SortServer sorter = new SortServer();
        String sort;
        sort = "BUBBLE RANDOM 10000"; System.out.println(sorter.execute(sort));
        sort = "SELECTION RANDOM 10000"; System.out.println(sorter.execute(sort));
        sort = "INSERTION RANDOM 10000"; System.out.println(sorter.execute(sort));
        sort = "QUICKERSORT RANDOM 10000 100"; System.out.println(sorter.execute(sort));
        sort = "STREAM RANDOM 10000"; System.out.println(sorter.execute(sort));

        sort = "BUBBLE ASC 10000"; System.out.println(sorter.execute(sort));
        sort = "SELECTION ASC 10000"; System.out.println(sorter.execute(sort));
        sort = "INSERTION ASC 10000"; System.out.println(sorter.execute(sort));
        sort = "QUICKERSORT ASC 10000 100"; System.out.println(sorter.execute(sort));
        sort = "STREAM ASC 10000"; System.out.println(sorter.execute(sort));
    }
}
