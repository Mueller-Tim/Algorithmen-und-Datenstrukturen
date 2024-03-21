package ch.zhaw.ads;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ADS12_1_test {
    SortServer sortServer = new SortServer();
    final int DATAELEMS = 10000;

    @Test
    public void testRandomData() {
        sortServer.dataElems = DATAELEMS;
        sortServer.insertion_threshold = 50;
        int[] data = sortServer.randomData();
        sortServer.quickerSort(data);
        for (int i = 0; i< sortServer.dataElems - 1; i++) {
            assertTrue(data[i] <= data[i + 1], "Random Data");
        }
    }

    @Test
    public void testAscendingData() {
        sortServer.dataElems = DATAELEMS;
        sortServer.insertion_threshold = 50;
        int[] data = sortServer.ascendingData();
        sortServer.quickerSort(data);
        for (int i = 0; i< sortServer.dataElems - 1; i++) {
            assertTrue(data[i] <= data[i + 1], "ASC Data");
        }
    }

    @Test
    public void testDecendingData() {
        sortServer.dataElems = DATAELEMS;
        sortServer.insertion_threshold = 50;
        int[] data = sortServer.descendingData();
        sortServer.quickerSort(data);
        for (int i = 0; i< sortServer.dataElems - 1; i++) {
            assertTrue(data[i] <= data[i + 1], "DESC Data");
        }
    }

    @Test
    public void testCompleteData() {
        boolean found;
        sortServer.dataElems = DATAELEMS;
        sortServer.insertion_threshold = 50;
        int[] data = sortServer.randomData();
        int[] original = new int[data.length];
        System.arraycopy(data, 0, original, 0, data.length);
        sortServer.quickerSort(data);
        for (int i = 0; i <= sortServer.dataElems - 1; i++) {
            found = false;
            for (int j = 0; j <= sortServer.dataElems - 1; j++) {
                if (data[i] == original[j]) {
                    found = true;
                    break;
                }
            }
            assertTrue(found, "Data not found after sort");
        }
    }
}
