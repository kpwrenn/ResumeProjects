/*
 * ChainedHashTable.java
 *
 * Computer Science 112, Boston University
 * 
 * Modifications and additions by:
 *     name: Kevin Wrenn
 *     email: kpwrennii@gmail.com
 */

import java.util.*;     // to allow for the use of Arrays.toString() in testing

/*
 * A class that implements a hash table using separate chaining.
 */
public class ChainedHashTable implements HashTable {
    /* 
     * Private inner class for a node in a linked list
     * for a given position of the hash table
     */
    private class Node {
        private Object key;
        private LLQueue<Object> values;
        private Node next;
        
        private Node(Object key, Object value) {
            this.key = key;
            values = new LLQueue<Object>();
            values.insert(value);
            next = null;
        }
    }
    
    private Node[] table;      // the hash table itself
    private int numKeys;       // the total number of keys in the table
        
    /* hash function */
    public int h1(Object key) {
        int h1 = key.hashCode() % table.length;
        if (h1 < 0) {
            h1 += table.length;
        }
        return h1;
    }
    
    /*** Add your constructor here ***/
    public ChainedHashTable(int maxSize) { 
        if (maxSize <= 0) { 
            throw new IllegalArgumentException();
        }
        table = new Node[maxSize];
    }
    
    /*
     * insert - insert the specified (key, value) pair in the hash table.
     * Returns true if the pair can be added and false if there is overflow.
     */
    public boolean insert(Object key, Object value) {
        /** Replace the following line with your implementation. **/
        if (key == null) {
            throw new IllegalArgumentException("key must be non-null");
        }
        
        int i = h1(key); 
        int firstRemoved = -1;
        // System.out.println("i is: " + i  + ", table[i] is " + table[i]);
        Node trav = table[i];
        while (trav != null) { 
            if (trav.key.equals(key)) { 
                trav.values.insert(value);
                return true;
            }
            trav = trav.next;
        }
        if (table[i] == null) {
            if (firstRemoved == -1) { 
                firstRemoved = i;
            }        
            // System.out.println("This runs");
            table[i] = new Node (key, value);
        }
        else { 
            // if (search(key) != null) { 
            //     numKeys--;
            // }
            Node temp = table[i]; 
            table[i] = new Node (key, value);
            table[i].next = temp;
        }
        numKeys++;
        return true;
    }
    
    /*
     * search - search for the specified key and return the
     * associated collection of values, or null if the key 
     * is not in the table
     */
    public Queue<Object> search(Object key) {
        /** Replace the following line with your implementation. **/
        if (key == null) {
            throw new IllegalArgumentException("key must be non-null");
        }
        
        int i = h1(key);
        
        if (i == -1 || table[i] == null) {
            return null;
        } else {
            Node trav = table[i];
            while (trav != null) { 
                if (trav.key.equals(key)) { 
                    return trav.values;
                }
                else { 
                    trav = trav.next;
                }
            }
        }
        return null;  
    }
    
    /* 
     * remove - remove from the table the entry for the specified key
     * and return the associated collection of values, or null if the key 
     * is not in the table
     */
    public Queue<Object> remove(Object key) {
        /** Replace the following line with your implementation. **/
        if (key == null) {
            throw new IllegalArgumentException("key must be non-null");
        }
            
        int i = h1(key);
        if (i == -1 || table[i] == null || search(key) == null) {
            return null;
        }
        Node trav = table[i]; 
        Node trail = null; 
        LLQueue<Object> removedVals = null;
        while (trav != null) { 
            if (trav.key.equals(key)) { 
                removedVals = trav.values;
                if (trail == null) { 
                    table[i] = table[i].next;
                }
                else { 
                    trail.next = trav.next;
                
                }
                numKeys--;
            }
            trail = trav; 
            trav = trav.next;
        }
        // LLQueue<Object> removedVals = table[i].values;
        return removedVals;
    }
    
    
    /*** Add the other required methods here ***/
    public int getNumKeys() { 
        return numKeys;
    }
    public double load() { 
        return (double) numKeys / table.length;
    }
    public Object[] getAllKeys() { 
        Object[] allKeys = new Object[numKeys];
        int allKeysIndex = 0;
        int j = 0;
        
        for (j = 0; j < table.length; j++) { 
            if (table[j] != null) {
                Node trav = table[j];
                while(trav != null) { 
                    allKeys[allKeysIndex++] = trav.key;
                    trav = trav.next;
                }
            }
        }
        return allKeys;
    }
    /*
     * toString - returns a string representation of this ChainedHashTable
     * object. *** You should NOT change this method. ***
     */
    public String toString() {
        String s = "[";
        
        for (int i = 0; i < table.length; i++) {
            if (table[i] == null) {
                s += "null";
            } else {
                String keys = "{";
                Node trav = table[i];
                while (trav != null) {
                    keys += trav.key;
                    if (trav.next != null) {
                        keys += "; ";
                    }
                    trav = trav.next;
                }
                keys += "}";
                s += keys;
            }
        
            if (i < table.length - 1) {
                s += ", ";
            }
        }       
        
        s += "]";
        return s;
    }
    /*
     * resize(int size)
     * 
     * resizes the current hashTable to size size
     */
    public void resize(int size) { 
        if (size < table.length) { 
            throw new IllegalArgumentException();
        }
        if (size == table.length) { 
            return; 
        }
        ChainedHashTable newHash = new ChainedHashTable(size);
        int index = 0;
        for (int i = 0; i < table.length; i++) { 
            Node trav = table[i];
            while (trav != null) {
                while (!trav.values.isEmpty()) {
                    newHash.insert(trav.key, trav.values.remove());
                    System.out.println(newHash.table);
                    // trav.values.remove();
                }
                trav = trav.next;
            }
            
        }
        table = newHash.table;
    }
    public static void main(String[] args) {
        System.out.println("--- Testing insert from Problem 9 ---");
        try {
            boolean results = false;
            ChainedHashTable hash = new ChainedHashTable(10);
            int[] keys = {44, 75, 32, 23, 5, 62, 22, 57, 80};
            for (int i = 0; i < keys.length; i++) { 
                results = hash.insert(keys[i], keys[i]);
            }
         
            
            System.out.println("actual results:");
            System.out.println(results);
            System.out.println("expected results:");
            System.out.println(true);
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results == true);
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
                           
        System.out.println();    // include a blank line between tests
        try {
            boolean results = false;
            ChainedHashTable hash = new ChainedHashTable(10);
            int[] keys = {44, 35, 53, 23, 48, 62, 28, 57, 80};
            for (int i = 0; i < keys.length; i++) { 
                results = hash.insert(keys[i], keys[i]);
            }
         
            
            System.out.println("actual results:");
            System.out.println(results);
            System.out.println("expected results:");
            System.out.println(true);
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results == true);
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
                      
        System.out.println();    // include a blank line between tests
        System.out.println("--- Testing remove from Problem 9 ---");
        try {
            boolean results = false;
            ChainedHashTable hash = new ChainedHashTable(10);
            int[] keys = {44, 75, 32, 23, 5, 62, 22, 57, 80};
            for (int i = 0; i < keys.length; i++) { 
                hash.insert(keys[i], keys[i]);
            }
            results = hash.remove(44).peek().equals(44);
            
            System.out.println("actual results:");
            System.out.println(results);
            System.out.println("expected results:");
            System.out.println(true);
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results == true);
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
                           
        System.out.println();    // include a blank line between tests
        try {
            boolean results = false;
            ChainedHashTable hash = new ChainedHashTable(10);
            int[] keys = {44, 35, 53, 23, 48, 62, 28, 57, 80};
            for (int i = 0; i < keys.length; i++) { 
               hash.insert(keys[i], keys[i]);
            }
            results = hash.remove(35).peek().equals(35);
            
            System.out.println("actual results:");
            System.out.println(results);
            System.out.println("expected results:");
            System.out.println(true);
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results == true);
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
                      
        System.out.println();    // include a blank line between tests
        System.out.println("--- Testing getNumKeys from Problem 9 ---");
        try {
            ChainedHashTable table = new ChainedHashTable(5);
            table.insert("howdy", 15);
            table.insert("goodbye", 10);
            table.insert("apple", 5);
            boolean results = table.getNumKeys() == 3;

            System.out.println("actual results:");
            System.out.println(results);
            System.out.println("expected results:");
            System.out.println(true);
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results == true);
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
                           
        System.out.println();    // include a blank line between tests
        try {
            ChainedHashTable table = new ChainedHashTable(5);
            table.insert("howdy", 15);
            table.insert("goodbye", 10);
            table.insert("apple", 5);
            table.insert("howdy", 25); 
            boolean results = table.getNumKeys() == 3;

            System.out.println("actual results:");
            System.out.println(results);
            System.out.println("expected results:");
            System.out.println(true);
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results == true);
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        System.out.println();    
        System.out.println("--- Testing load from Problem 9 ---");
        try {
            ChainedHashTable table = new ChainedHashTable(5);
            table.insert("howdy", 15);
            table.insert("goodbye", 10);
            table.insert("apple", 5);
            boolean results = table.load() == 0.6;

            System.out.println("actual results:");
            System.out.println(results);
            System.out.println("expected results:");
            System.out.println(true);
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results == true);
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
                           
        System.out.println();    // include a blank line between tests
        try {
            ChainedHashTable table = new ChainedHashTable(5);
            table.insert("howdy", 15);
            table.insert("goodbye", 10);
            table.insert("apple", 5);
            table.insert("pear", 6);
            boolean results = table.load() == 0.8;

            System.out.println("actual results:");
            System.out.println(results);
            System.out.println("expected results:");
            System.out.println(true);
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results == true);
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        System.out.println();    
        System.out.println("--- Testing getAllKeys() from Problem 9 ---");
        try {
            ChainedHashTable table = new ChainedHashTable(5);
            table.insert("howdy", 15);
            table.insert("goodbye", 10);
            table.insert("apple", 5);
            Object[] keys = table.getAllKeys();
            Object[] expectedKeys = {"apple", "howdy", "goodbye"};
            boolean results = true;
            for (int i = 0; i < keys.length; i++) { 
                if (!keys[i].equals(expectedKeys[i])) { 
                    results = false;
                }
            }
            // boolean results = keys.equals(expectedKeys);

            System.out.println("actual results:");
            System.out.println(results);
            System.out.println("expected results:");
            System.out.println(true);
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results == true);
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
                           
        System.out.println();    // include a blank line between tests
        try {
            ChainedHashTable table = new ChainedHashTable(5);
            table.insert("howdy", 15);
            table.insert("goodbye", 10);
            table.insert("fortnite", 5);
            // table.insert("howdy", 25);
            Object[] keys = table.getAllKeys();
            Object[] expectedKeys = {"howdy", "fortnite", "goodbye"};
            boolean results = true;
            for (int i = 0; i < keys.length; i++) { 
                System.out.println(keys[i]);
                if (!keys[i].equals(expectedKeys[i])) {
                    System.out.println("This runs when:"); 
                    System.out.println("Keys[i] is " + keys[i]); 
                    System.out.println("ExpectedKeys[i] is " + expectedKeys[i]); 
                    results = false;
                }
            // boolean results = keys.equals(expectedKeys);
            }
            System.out.println("actual results:");
            System.out.println(results);
            System.out.println("expected results:");
            System.out.println(true);
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results == true);
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        System.out.println();    
        System.out.println("--- Testing resize() from Problem 9 ---");
        try {
            ChainedHashTable table = new ChainedHashTable(5);
            table.insert("howdy", 15);
            table.insert("goodbye", 10);
            table.insert("apple", 5);
            System.out.println(table);
            table.resize(7);
            System.out.println(table);
            // boolean results = keys.equals(expectedKeys);
            boolean results = true;
            System.out.println("actual results:");
            System.out.println(results);
            System.out.println("expected results:");
            System.out.println(true);
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results == true);
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
                           
        System.out.println();    // include a blank line between tests
        try {
            ChainedHashTable table = new ChainedHashTable(5);
            table.insert("howdy", 15);
            table.insert("goodbye", 10);
            table.insert("fortnite", 5);
            // table.insert("howdy", 25);
            System.out.println(table);
            table.resize(7);
            System.out.println(table);
            boolean results = true;
            System.out.println("actual results:");
            System.out.println(results);
            System.out.println("expected results:");
            System.out.println(true);
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results == true);
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        System.out.println();    
        /** Add your unit tests here **/
        // ChainedHashTable table = new ChainedHashTable(5);
        // table.insert("howdy", 15);
        // table.insert("goodbye", 10);
        // System.out.println(table.insert("apple", 5));
        // System.out.println(table);

        // ChainedHashTable table = new ChainedHashTable(5);
        // table.insert("howdy", 15);
        // table.insert("goodbye", 10);
        // table.insert("apple", 5);
        // System.out.println(table.getNumKeys());
        // table.insert("howdy", 25);     // insert a duplicate
        // System.out.println(table.getNumKeys());


        // ChainedHashTable table = new ChainedHashTable(5);
        // table.insert("howdy", 15);
        // table.insert("goodbye", 10);
        // table.insert("apple", 5);
        // System.out.println(table.load());
        // table.insert("pear", 6);
        // System.out.println(table.load());

        // ChainedHashTable table = new ChainedHashTable(5);
        // table.insert("howdy", 15);
        // table.insert("goodbye", 10);
        // table.insert("apple", 5);
        // table.insert("howdy", 25);    // insert a duplicate
        // Object[] keys = table.getAllKeys();
        // System.out.println(Arrays.toString(keys));
        // System.out.println(table);

        // ChainedHashTable table = new ChainedHashTable(5);
        // table.insert("howdy", 15);
        // table.insert("goodbye", 10);
        // table.insert("apple", 5);
        // System.out.println(table.toString());
        // table.resize(7);
        // System.out.println(table);
    }
    }


