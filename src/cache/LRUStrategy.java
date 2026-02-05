package cache;
public class LRUStrategy implements EvictionStrategy {

    @Override
    public void keyAccessed(int key) {

    }

    @Override
    public int evictKey() {
        return -1;
    }

    @Override
    public void removeKey(int key) {

    }
}
