package ch.zhaw.ads;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ADS13_1_test {
    private CObject new_CObject(Object s) {
        return (CObject) Storage._new("CObject", s);
    }

    static CObject a, d;
    CObject b, c, e, f, g;

    @BeforeEach
    public void setUp() {
        Storage.generationalGCActive = false;
        Storage.clear();
        newObjects();
    }

    private void testContent(String message, Iterable<Collectable> content, String expected) {
        StringBuilder b = new StringBuilder();
        content.forEach(b::append);
        assertEquals(expected, b.toString(), message);
    }

    private void newObjects() {
        a = new_CObject("A");
        b = new_CObject("B");
        c = new_CObject("C");
        d = new_CObject("D");
        e = new_CObject("E");
        f = new_CObject("F");
        g = new_CObject("G");
        Storage.addRoot(a);
        Storage.addRoot(d);
        a.next = b;
        b.next = c;
        b.down = a;
        c.down = d;
        d.next = e;
        e.next = f;
        f.next = g;
    }

    @Test
    public void testRoot() {
        testContent("ROOT", Storage.getRoot(), "AD");
    }

    @Test
    public void testInitialHeap() {
        testContent("HEAP1", Storage.getHeap(), "ABCDEFG");
    }

    @Test
    public void testAfterFirstGC() {
        Storage.gc();
        testContent("HEAP2", Storage.getHeap(), "ABCDEFG");
    }

    @Test
    public void testAfterFirstChangeGC() {
        Storage.gc();
        e.next = d;
        Storage.gc();
        testContent("HEAP3", Storage.getHeap(), "ABCDE");
    }

    @Test
    public void testAfterSecondChangeGC() {
        Storage.gc();
        e.next = d;
        Storage.gc();
        a.next = null;
        Storage.gc();
        testContent("HEAP4", Storage.getHeap(), "ADE");
    }

    @Test
    public void testFinalHeap() {
        Storage.gc();
        e.next = d;
        Storage.gc();
        a.next = null;
        Storage.gc();
        Storage.gc();
        testContent("HEAP5", Storage.getHeap(), "ADE");
    }
}
