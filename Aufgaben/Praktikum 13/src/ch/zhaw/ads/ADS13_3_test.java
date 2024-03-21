package ch.zhaw.ads;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ADS13_3_test {
    private CObject new_CObject(Object s) {
        return (CObject) Storage._new("CObject", s);
    }

    static CObject a, d;
    CObject b, c, e, f, g, h;

    @BeforeEach
    public void setUp() {
        Storage.generationalGCActive = true;
        Storage.youngGenerationOnly = true;
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
        testContent("YOUNG HEAP1", Storage.getYoungHeap(), "ABCDEFG");
        testContent("OLD HEAP1", Storage.getOldHeap(), "");    }

    @Test
    public void testAfterFirstGC() {
        Storage.gc();
        Storage.getYoungHeap();
        Storage.getOldHeap();
        testContent("YOUNG HEAP2", Storage.getYoungHeap(), "");
        testContent("OLD HEAP2", Storage.getOldHeap(), "ABCDEFG");
    }

    @Test
    public void testAfterFirstChangeGC() {
        Storage.gc();
        e.next = d;
        Storage.gc();
        testContent("YOUNG HEAP3", Storage.getYoungHeap(), "");
        testContent("OLD HEAP3", Storage.getOldHeap(), "ABCDE");
    }

    @Test
    public void testAfterSecondChangeGC() {
        Storage.gc();
        e.next = d;
        Storage.gc();
        a.next = null;
        Storage.gc();
        testContent("YOUNG HEAP4", Storage.getYoungHeap(), "");
        testContent("OLD HEAP4", Storage.getOldHeap(), "ABCDE");
    }

    @Test
    public void testAfterSecondCallGC() {
        Storage.gc();
        e.next = d;
        Storage.gc();
        a.next = null;
        Storage.gc();
        Storage.gc();
        testContent("YOUNG HEAP5", Storage.getYoungHeap(), "");
        testContent("OLD HEAP5", Storage.getOldHeap(), "ADE");
    }

    @Test
    public void testAfterAddObjecthGC() {
        Storage.gc();
        e.next = d;
        Storage.gc();
        a.next = null;
        Storage.gc();
        Storage.gc();
        h = new_CObject("H");
        a.next = h;
        testContent("YOUNG HEAP5", Storage.getYoungHeap(), "H");
        testContent("OLD HEAP5", Storage.getOldHeap(), "ADE");
    }

    @Test
    public void testFinalHeap() {
        Storage.gc();
        e.next = d;
        Storage.gc();
        a.next = null;
        Storage.gc();
        Storage.gc();
        h = new_CObject("H");
        a.next = h;
        testContent("YOUNG HEAP5", Storage.getYoungHeap(), "H");
        testContent("OLD HEAP5", Storage.getOldHeap(), "ADE");
        Storage.gc();
        testContent("YOUNG HEAP5", Storage.getYoungHeap(), "");
        testContent("OLD HEAP5", Storage.getOldHeap(), "ADEH");
    }
}
