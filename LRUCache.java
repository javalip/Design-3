import java.util.HashMap;
import java.util.Map;

public class LRUCache {
    /**
     Reason for choosing hasmap and dll.
     1. stack approach get = 0(n)
     LRU will be at bottom access is expensive


     - stored the key value pair in node and key and the address to node in hashmap.
     - linked list contained the order in which any operation
     is conducted maintaining LRU at the end of doubly linkedl ist.
     - doubly linked is used to perfoem constant time opeation for look
     up and deletion ( to remove LRU ) or update MRU

     time complexity
     get - o(1)
     put - o(1)
     Space complexity
     o(n)


     */
    // need an empty hshmap , Node and DLL
    // Node
    class Node {
        int key, val;
        Node next, prev;

        public Node(int key, int val) {
            this.key = key;
            this.val = val;
        }
    }
    // I may have to remove first or last node. May have to be handled differently.
    // Or add dummy node head and tail and in between we have linkedlist
    // insertion, deletion operations required in doubly linkedlist

    Node head, tail;
    Map<Integer, Node> map;
    int capacity;

    public LRUCache(int capacity) {
        map = new HashMap<>();
        this.capacity = capacity;
        head = new Node(-1, -1);
        tail = new Node(-1, -1);
        head.next = tail;
        tail.prev = head;
    }

    private void addToHead(Node node) {
        node.next = head.next;
        node.prev = head;
        node.next.prev = node;
        head.next = node;
    }

    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    public int get(int key) {
        // if key is not preset return -1
        if (!map.containsKey(key)) {
            return -1;
        }
        // if key is present, get it.
        // remove it from linked list using the value and add to the head of the LL
        // as its not LRU anymore as we just did get
        Node node = map.get(key);
        removeNode(node);
        //
        addToHead(node);
        return node.val;
    }

    public void put(int key, int value) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            // since key already exists, we update its value.
            // its MRU now so remove from existing postion and add to the head
            removeNode(node);
            addToHead(node);
            node.val = value;
            return;
        }
        // if new node is coming in for put request, capacity needs ot be checked.
        // if the capacity has reached LRU should be removed
        // LRU is the one prev to the tail. get that node and call delete.
        if (capacity == map.size()) {
            // get LRU
            Node tailPrev = tail.prev;
            // remove from linkedlist
            removeNode(tailPrev);
            // also remove from map
            map.remove(tailPrev.key);
        }
        Node newNode = new Node(key, value);
        // add new node ot the head.
        addToHead(newNode);
        // alsop pot it in the map.
        map.put(key, newNode);
    }

}
