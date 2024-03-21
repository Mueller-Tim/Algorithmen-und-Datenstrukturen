package ch.zhaw.ads;

public class TestCObjectServer implements CommandExecutor  {
   private static CObject new_CObject(Object s) {
      return (CObject) Storage._new("CObject", s);
   }

   static CObject a;
   static CObject e;

   public String execute (String input) {
     run();
     return Storage.getLog();
   }

  private void run() {
     a = new_CObject("A");
     CObject b = new_CObject("B");
     CObject c = new_CObject("C");
     CObject d = new_CObject("D");
     e = new_CObject("E");
     CObject f = new_CObject("F");
     CObject g = new_CObject("G");
     Storage.addRoot(a);
     Storage.addRoot(e);
     a.next = b; b.next = c; b.down = a; c.down = d;
     e.next = f; f.next = g; g.next = e;
     Storage.dump("\nRoots:", Storage.getRoot());
     if (Storage.generationalGCActive) {
        Storage.dump("\nYoung heap 1:", Storage.getYoungHeap());
        Storage.dump("Old heap 1:", Storage.getOldHeap());
     } else {
        Storage.dump("Heap 1:", Storage.getYoungHeap());
     }
     Storage.gc();
     if (Storage.generationalGCActive) {
        Storage.dump("Young heap 2:", Storage.getYoungHeap());
        Storage.dump("Old heap 2:", Storage.getOldHeap());
     } else {
        Storage.dump("Heap 2:", Storage.getYoungHeap());
     }
     b.next = f;
     Storage.gc();
     if (Storage.generationalGCActive) {
        Storage.dump("Young heap 3:", Storage.getYoungHeap());
        Storage.dump("Old heap 3:", Storage.getOldHeap());
     } else {
        Storage.dump("Heap 3:", Storage.getYoungHeap());
     }
     f.next = null;
     Storage.gc();
     if (Storage.generationalGCActive) {
        Storage.dump("Young heap 4:", Storage.getYoungHeap());
        Storage.dump("Old heap 4:", Storage.getOldHeap());
     } else {
        Storage.dump("Heap 4:", Storage.getYoungHeap());
     }
     Storage.gc();
     if (Storage.generationalGCActive) {
        Storage.dump("Young heap 5:", Storage.getYoungHeap());
        Storage.dump("Old heap 5:", Storage.getOldHeap());
     } else {
        Storage.dump("Heap 5:", Storage.getYoungHeap());
     }
  }
}
