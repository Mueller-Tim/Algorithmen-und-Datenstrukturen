package ch.zhaw.ads;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ADS12_3_test {
    ParallelQuickerSortServer sortServer = new ParallelQuickerSortServer();
    final int DATAELEMS = 10000;

    @Test
    public void testRandomData() {
        ParallelQuickerSortServer.dataElems = DATAELEMS;
        ParallelQuickerSortServer.insertion_threshold = 50;
        int[] data = sortServer.randomData();
        Thread rootThread = new ParallelQuickerSortServer(data, 0, data.length - 1);
        rootThread.start();
        try {
            rootThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i< ParallelQuickerSortServer.dataElems - 1; i++) {
            assertTrue(data[i] <= data[i + 1], "Sorted");
        }
    }

    @Test
    public void testAscendingData() {
        ParallelQuickerSortServer.dataElems = DATAELEMS;
        ParallelQuickerSortServer.insertion_threshold = 50;
        int[] data = sortServer.ascendingData();
        Thread rootThread = new ParallelQuickerSortServer(data, 0, data.length - 1);
        rootThread.start();
        try {
            rootThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i< ParallelQuickerSortServer.dataElems - 1; i++) {
            assertTrue(data[i] <= data[i + 1], "Sorted");
        }
    }

    @Test
    public void testDescendingData() {
        ParallelQuickerSortServer.dataElems = DATAELEMS;
        ParallelQuickerSortServer.insertion_threshold = 50;
        int[] data = sortServer.descendingData();
        Thread rootThread = new ParallelQuickerSortServer(data, 0, data.length - 1);
        rootThread.start();
        try {
            rootThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i< ParallelQuickerSortServer.dataElems - 1; i++) {
            assertTrue(data[i] <= data[i + 1], "Sorted");
        }
    }

    @Test
    public void testCompleteData() {
        boolean found;
        ParallelQuickerSortServer.dataElems = DATAELEMS;
        ParallelQuickerSortServer.insertion_threshold = 50;
        int[] data = sortServer.ascendingData();
        int[] original = new int[data.length];
        System.arraycopy(data, 0, original, 0, data.length);
        Thread rootThread = new ParallelQuickerSortServer(data, 0, data.length - 1);
        rootThread.start();
        try {
            rootThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i <= ParallelQuickerSortServer.dataElems - 1; i++) {
            found = false;
            for (int j = 0; j <= ParallelQuickerSortServer.dataElems - 1; j++) {
                if (data[i] == original[j]) {
                    found = true;
                    break;
                }
            }
            assertTrue(found, "Data not found after sort");
        }
    }
}
