package cache;

import java.util.HashMap;
import java.util.Map;

public class LRUStrategy implements EvictionStrategy {

    private final Map<String, Node> nodeMap; // Key and Node are the Key value pairs
    private final Node head;
    private final Node tail;

    // LRU Constructor
    public LRUStrategy() {
        nodeMap = new HashMap<>();
        head = new Node("");
        tail = new Node("");

        head.next = tail;
        tail.prev = head;
    }

    // If the Node is not present inside the map then that node is added to front.
    private void addFront(Node node)  {
        node.next = head.next;
        node.prev = head;

        head.next.prev = node;
        head.next = node;
    }

    // If the cache is already full and a new value is put inside the cache then the Least recently used value is removed with the help of this method.
    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    // If a value is accessed which is already present inside the nodemap then the value is moved to the front. 
    private void moveToFront(Node node) {
        removeNode(node);
        addFront(node);
    }

    // Whenever the cache system access the value then this method is invoked. if the map contains the key then that node is moved to front else a new node is created and then added to the front of the map.
    @Override
    public void keyAccessed(String key) {
        if(nodeMap.containsKey(key)) {
            moveToFront(nodeMap.get(key));
        }

        else {
            Node node = new Node(key);
            nodeMap.put(key , node);
            addFront(node);
        }
    }

    // This method returns the key of the Node which is to be evicted.
    @Override
    public String evictKey() {
        Node lru = tail.prev;
        return lru.key;
    }

    // This method takes the key as the input and then removes the node from the linked list and the node map.
    @Override
    public void removeKey(String key) {
        Node node = nodeMap.get(key);
        if(node != null) {
            removeNode(node);
            nodeMap.remove(key);
        }
    }

    // This method is used when we change the eviction strategy from LFU to LRU. Whenever that happens we rebuild the Node map and linked list to contain all the keys that are already present inside the cache system.
    @Override
    public void rebuild(Iterable<String> keys) {

    nodeMap.clear();
    head.next = tail;
    tail.prev = head;

    for (String key : keys) {
        Node node = new Node(key);
        nodeMap.put(key, node);
        addFront(node);
    }
}
}