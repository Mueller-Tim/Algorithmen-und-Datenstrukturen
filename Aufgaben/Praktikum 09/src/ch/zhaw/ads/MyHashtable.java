package ch.zhaw.ads;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("unchecked")
public class MyHashtable<K, V> implements Map<K, V> {
    private K[] keys = (K[]) new Object[10];
    private int currentSize;
    private V[] values = (V[]) new Object[10];
    private int maxSize;
    private final K DELETED = (K) new Object();

    private int hash(Object k) {
        int h = Math.abs(k.hashCode());
        return h % keys.length;
    }

    public MyHashtable(int size) {
        // to TODO be done
        this.maxSize = size;
        clear();
    }

    // Removes all mappings from this map (optional operation).
    public void clear() {
        // TODO to be done
        keys = (K[]) new Object[maxSize];
        values = (V[]) new Object[maxSize];
        currentSize = 0;
    }

    // Associates the specified value with the specified key in this map (optional operation).
    public V put(K key, V value) {
        int h = getIdx(key);
        if (h < 0 || values[h] == null) {
            if (currentSize > 0.8*maxSize) {
                rehash();
            }
            h = hash(key);

            // cells with DELETED keys are regarded as free
            while (keys[h] != null && !key.equals(keys[h]) && keys[h] != DELETED) {
                h = (h + 1) % keys.length;
            }
            currentSize++;
            keys[h] = key;
        }
        values[h] = value;
        return value;
    }

    // Returns the value to which this map maps the specified key.
    public V get(Object key) {
        // TODO to be done
        int h = getIdx(key);
        return (h < 0) ? null : values[h];
    }

    private int getIdx(Object key) {
        // TODO neu von tim
        int h = hash(key);
        int c = 0;
        while (keys[h] != null && !keys[h].equals(key) && c < maxSize) {
            h = (h + 1) % keys.length; c++;
        }
        return (c == maxSize) ? -1 : h;
    }

    // Removes the mapping for this key from this map if present (optional operation).
    public V remove(Object key) {
        // TODO to be done (Aufgabe 3)
        int h = getIdx(key);
        if (h < 0 || values[h] == null) {
            return null;
        }
        V oldValue = values[h];
        keys[h] = DELETED;
        values[h] = null;
        currentSize--;
        return oldValue;
    }

    // Returns the number of key-value mappings in this map.
    public int size() {
        // TODO to be done
        return currentSize;
    }

    // UnsupportedOperationException ===================================================================
    // Returns a collection view of the values contained in this map.
    public Collection<V> values() {
        throw new UnsupportedOperationException();
    }

    // Returns true if this map contains a mapping for the specified key.
    public boolean containsKey(Object key) {
        throw new UnsupportedOperationException();
    }

    // Returns true if this map maps one or more keys to the specified value.
    public boolean containsValue(Object value) {
        throw new UnsupportedOperationException();
    }

    // Returns a set view of the mappings contained in this map.
    public Set<Map.Entry<K, V>> entrySet() {
        throw new UnsupportedOperationException();
    }

    // Compares the specified object with this map for equality.
    public boolean equals(Object o) {
        throw new UnsupportedOperationException();
    }

    // Copies all of the mappings from the specified map to this map (optional operation).
    public void putAll(Map<? extends K, ? extends V> t) {
        throw new UnsupportedOperationException();
    }

    // Returns the hash code value for this map.
    public int hashCode() {
        throw new UnsupportedOperationException();
    }

    // Returns true if this map contains no key-value mappings.
    public boolean isEmpty() {
        throw new UnsupportedOperationException();
    }

    // Returns a set view of the keys contained in this map.
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    private void rehash() {
        // TODO neu von tim
        K[] oldKeys = keys;
        V[] oldValues = values;
        maxSize *= 2;
        clear();
        for (int i = 0;i < oldKeys.length; i++) {
            if (oldKeys[i] != null && oldKeys[i] != DELETED) {
                put(oldKeys[i], oldValues[i]);
            }
        }
    }
}
