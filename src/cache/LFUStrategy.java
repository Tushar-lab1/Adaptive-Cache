package cache;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class LFUStrategy implements EvictionStrategy {
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
