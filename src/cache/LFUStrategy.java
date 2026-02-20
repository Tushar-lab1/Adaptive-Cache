package cache;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class LFUStrategy implements EvictionStrategy {

    private final Map<Integer , Integer> keyToFreq;
    private final Map<Integer , LinkedHashSet<Integer>> freqToKeys;
    private int minFreq;

    public LFUStrategy() {
        keyToFreq = new HashMap<>();
        freqToKeys = new HashMap<>();
        minFreq = 0;
    }

    @Override
    public void keyAccessed(int key) {
        if(!keyToFreq.containsKey(key)) {
            keyToFreq.put(key , 1);
            freqToKeys.computeIfAbsent(1, k -> new LinkedHashSet<>());
            minFreq = 1;
            return;
        }

        //Existing Key -- Increase Frequency
        int oldFreq = keyToFreq.get(key);
        int newFreq = oldFreq + 1;

        keyToFreq.put(key , newFreq);

        //Remove from old frequency set
        LinkedHashSet<Integer> oldSet = freqToKeys.get(oldFreq);
        oldSet.remove(oldFreq);

        if(oldSet.isEmpty()) {
            freqToKeys.remove(oldFreq);
            if(minFreq == oldFreq) {
                minFreq = oldFreq;
            }
        }

        freqToKeys.computeIfAbsent(newFreq, k -> new LinkedHashSet<>()).add(key);
    }

    @Override
    public int evictKey() {
    LinkedHashSet<Integer> keys = freqToKeys.get(minFreq);

    if (keys == null || keys.isEmpty()) {
        throw new IllegalStateException(
            "LFU internal state corrupted: no keys for minFreq=" + minFreq
        );
    }

    return keys.iterator().next();
}

    @Override
    public void removeKey(int key) {
        Integer freq = keyToFreq.get(key);
        if(freq==null) return;
        LinkedHashSet<Integer> keys = freqToKeys.get(freq);
        keys.remove(key);

        if(keys.isEmpty()) {
            freqToKeys.remove(freq);
            if(minFreq == freq) {
                minFreq++;
            }
        }

        keyToFreq.remove(key);
    }

    @Override
public void rebuild(Iterable<Integer> keys) {

    keyToFreq.clear();
    freqToKeys.clear();
    minFreq = 0;

    for (Integer key : keys) {
        keyToFreq.put(key, 1);
        freqToKeys
            .computeIfAbsent(1, k -> new LinkedHashSet<>())
            .add(key);
    }

    if (!keyToFreq.isEmpty()) {
        minFreq = 1;
    }
}
}
