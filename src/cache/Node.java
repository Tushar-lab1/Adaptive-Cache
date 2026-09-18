package cache;

public class Node {
    String key;
    Node prev;
    Node next;

    Node(String key) {
        this.key = key;
    }
}