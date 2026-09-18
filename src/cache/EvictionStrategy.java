package cache;

public interface EvictionStrategy {
    void keyAccessed(String key);
    String evictKey();
    void removeKey(String key);
    void rebuild(Iterable<String> keys);
}