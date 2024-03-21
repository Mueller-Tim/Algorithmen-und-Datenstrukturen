package ch.zhaw.ads;

/** base class of collectable objects */
public class CObject implements Collectable {
   private boolean mark;  // to mark object
   public CObject next, down;
   public String value;

   public CObject (String value) {
      this.value = value;
   }

   public void setMark(boolean mark) {
      this.mark = mark;
   }

   public boolean isMarked() {
      return mark;
   }

   public String toString() {
     return value;
   }

   @Override
   public boolean equals(Object o) {
      return this.getClass() == o.getClass() && this.value.equals(((CObject)o).value);
   }
}
