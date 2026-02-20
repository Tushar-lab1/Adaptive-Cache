package cache;

public interface EvictionStrategy {
    void keyAccessed(int key);
    int evictKey();
    void removeKey(int key);
    void rebuild(Iterable<Integer> keys);
}