package ch.zhaw.ads;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

public class Storage {
    public static boolean generationalGCActive = false; // in Aufgabe 2 verwendet
    public static StringBuffer log = new StringBuffer();
    private static List<Collectable> root;
    private static List<Collectable> youngHeap;
    private static List<Collectable> oldHeap;
    public static boolean youngGenerationOnly = true;

    static {
        clear();
    }

    public static void clear() {
        root = new LinkedList<>();
        youngHeap = new LinkedList<>();
        // oldHeap erst in Aufgabe 2 verwenden!
        oldHeap = new LinkedList<>();
    }

    /* add  root object */
    public static void addRoot(Collectable obj) {
        root.add(obj);
    }

    // create a collectable object of class cls
    public static Collectable _new(String cls, Object arg) {
        Collectable obj = null;
        try {
            // create an object and call constructor
            Constructor<?> cst = Class.forName(getPackage() + cls).getConstructor(arg.getClass());
            obj = (Collectable) cst.newInstance(new Object[] {arg});
            // add object to heap
            youngHeap.add(obj);
            log.append("New: ").append(obj).append("\n");
        } catch (Exception ex) {
            log.append("error creating object ").append(cls).append("\n");
        }
        return obj;
    }

    private static String getPackage() {
        Package pack = Storage.class.getPackage();
        if (pack != null && !pack.getName().equals("")) {
            return pack.getName() + ".";
        } else {
            return "";
        }
    }

    /* remove object from heap */
    public static void delete(Collectable obj) {
        if (youngHeap.remove(obj)) {
            if (generationalGCActive) {
                log.append("Delete young heap: ").append(obj).append("\n");
            } else {
                log.append("Delete heap: ").append(obj).append("\n");
            }
        } else if (oldHeap.remove(obj)) {
            log.append("Delete old heap: ").append(obj).append("\n");
        } else {
            log.append("error trying to delete not existing object ").append(obj).append("\n");
        }
    }

    /* get all root objects */
    public static Iterable<Collectable> getRoot() {
        return new LinkedList<>(root);
    }

    /* get young heap */
    public static Iterable<Collectable> getYoungHeap() {
        return new LinkedList<>(youngHeap);
    }

    /* get old heap */
    public static Iterable<Collectable> getOldHeap() {
        return new LinkedList<>(oldHeap);
    }

    /* get heap */
    public static Iterable<Collectable> getHeap() {
        return new LinkedList<>(youngHeap);
    }

    /* get references to collectables of an object */
    public static Iterable<Collectable> getRefs(Collectable obj) {
        // get all fields of an object
        List<Collectable> fieldList = new LinkedList<>();
        for (Field field : obj.getClass().getFields()) {
            try {
                Object o = field.get(obj);
                if (o instanceof Collectable) {
                    fieldList.add((Collectable) o);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return fieldList;
    }

    /* dump an iterator */
    public static void dump(String s, Iterable<Collectable> itr) {
        log.append(s);
        for (Collectable o: itr) {
            log.append(" ").append(o);
        }
        log.append("\n");
    }

    public static String getLog() {
        return log.toString();
    }

    private static void mark(Collectable cObject) {
        // TODO Aufgabe 13.1
        if(!cObject.isMarked()){
            cObject.setMark(true);
            Iterable<Collectable> refs = getRefs(cObject);
            refs.forEach(Storage::mark);
        }
    }

    private static void sweep() {
        // TODO Aufgabe 13.1 und Aufgabe 13.2
        if(generationalGCActive && !youngGenerationOnly){
            getOldHeap().forEach(n ->{
                if(!n.isMarked()){
                    delete(n);
                } else{
                    n.setMark(false);
                }
            });
        }
        getYoungHeap().forEach(n ->{
            if(!n.isMarked()){
                delete(n);
            } else{
                n.setMark(false);
                if(generationalGCActive){
                    oldHeap.add(n);
                    youngHeap.remove(n);
                }
            }
        });
    }

    public static void gc() {
        if (generationalGCActive) {
            if (youngGenerationOnly) {
                log.append("\nCollector start young generation only\n");
            } else {
                log.append("\nCollector start young and old generation\n");
            }
        } else {
            log.append("\nCollector start\n");
        }

        // TODO Aufgabe 13.1 und Aufgabe 13.2
        getRoot().forEach(Storage::mark);
        sweep();
        youngGenerationOnly = !youngGenerationOnly;
        log.append("Collector end\n");
    }
}
