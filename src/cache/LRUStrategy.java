package cache;

import java.util.HashMap;
import java.util.Map;

public class LRUStrategy implements EvictionStrategy {

    private final Map<Integer , Node> nodeMap;
    private final Node head;
    private final Node tail;

    public LRUStrategy() {
        nodeMap = new HashMap<>();
        head = new Node(-1);
        tail = new Node(-1);

        head.next = tail;
        tail.prev = head;
    }

    private void addFront(Node node)  {
        node.next = head.next;
        node.prev = head;

        head.next.prev = node;
        head.next = node;
    }

    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void moveToFront(Node node) {
        removeNode(node);
        addFront(node);
    }

    @Override
    public void keyAccessed(int key) {
        if(nodeMap.containsKey(key)) {
            moveToFront(nodeMap.get(key));
        }

        else {
            Node node = new Node(key);
            nodeMap.put(key , node);
            addFront(node);
        }
    }

    @Override
    public int evictKey() {
        Node lru = tail.prev;
        return lru.key;
    }

    @Override
    public void removeKey(int key) {
        Node node = nodeMap.get(key);
        if(node != null) {
            removeNode(node);
            nodeMap.remove(key);
        }
    }

    @Override
    public void rebuild(Iterable<Integer> keys) {

    nodeMap.clear();
    head.next = tail;
    tail.prev = head;

    for (Integer key : keys) {
        Node node = new Node(key);
        nodeMap.put(key, node);
        addFront(node);
    }
}
}