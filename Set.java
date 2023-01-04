/*
 * Student First Name:
 * Student Last Name:
 * Student BU Number:
 * Purpose: 
 * 
 * NEED TO COMMENT CODE
 */

// import java.util.Arrays;
public class Set  {
    private static final int SIZE = 10; // default size of initial set
                                
    private int[] set;      // array referece to the set, representing a set of integers
    private int size;       // current physical size of the set (equivalent to set.length)
    private int next;       // index to next available slot in the set array (equivalent to the number of elements in the set, like numElements in myArray)
    
     /*
     * Default, no-argument constructor -- creates an empty set 
     */
    public Set() {
        // your code here
        set = new int[SIZE];
        size = SIZE;
        next = 0;
    }
     /*
     * Set(int[] arr)
     * 
     * Creates a set based off the array passed in 
     * 
     * returns: the this reference, a reference to the object
     */
    public Set(int[] arr) {
        this();
        for (int i = 0; i < arr.length; i++) { 
            // System.out.println("i: " + i + " - This is printed, 1");
            insert(arr[i]);
        }
        
    }
    

    /* 
     * clone()
     * 
     * Creates a deep copy of this's set array
     * 
     * returns: Set
     */
    public Set clone() {
        // your code here
        if (next == 0) { 
            return new Set();
        }
        int[] copyArr = new int[size];
        for (int i = 0; i < next; i++) { 
            // System.out.println("clone: copyArr[i] = set[i] is happening right here");
            copyArr[i] = set[i];

        }
        // System.out.println("Next right before return of clone: " + next);
        return new Set(copyArr);
    }
  

    /** 
     * This method reallocates the array set to twice as 
     * big and copies all the elements over.
     * This method is called only by the insert method
     * when it cannot insert another element to the set.
     *
     * Note that this is the reason that in this class
     * the member size is not a class variable (i.e. static)
     * and it is not final, because the set can grow and size
     * will be modified accordingly.
     */
    private void resize() {
        size *= 2;

	// Create a new array double the size
        int[] temp = new int[size];

	// Copy all the elements from the current set
	// to the new set
        for(int i = 0; i < set.length; ++i) {
            temp[i] = set[i];
        }

	// Assign to the set reference the newly
	// resized array that represents the set.
        set = temp;
    }
 
    public String toString()  {
        String str = "[";
        if (next == 0) { 
            return "[]";
        }
        if (next == 1) { 
            return "[" + set[0] + "]";
        }
        for (int i = 0; i < next; i++) { 
            if (i == next - 1) { 
                str += set[i] + "]";
            }
            else { 
                str += set[i] + ",";
            }
        }   
        return str;
    } 
    /* 
     * cardinality()
     * 
     * 
     * returns: next, the number of elements we care for in the set array
     */
    public int cardinality() {
        // your code here
        return next;
    }
    /* 
     * isEmpty()
     * 
     * Checks if the set array is empty, or has no elements we care for
     * 
     * returns: boolean
     */
    public  boolean isEmpty() {
        // your code here
        return next == 0;
    }
    /* 
     * member(int k)
     * 
     * Checks k is in the set
     * 
     * returns: boolean
     */
    public boolean member(int k) {
        // your code here
        for (int i = 0; i < next; i++) { 
            if (set[i] == k) { 
                return true;
            }
        }
        return false;
    }    
    /* 
     * subset(Set S)
     * 
     * checks if this is a subset of S
     * 
     * returns: boolean
     */
    public boolean subset(Set S) {
        // your code here
        boolean isSubset = true;
        if (next == 0) { 
            return isSubset;
        }
        for (int i = 0; i < next; i++) { 
             if (!S.member(set[i])) { 
                isSubset = false; 
             }
        }
        return isSubset;
    }
    /* 
     * equal(Set S)
     * 
     * Checks if this and S are equal, or have the same exact elements 
     * 
     * returns: boolean
     */
    public boolean equal(Set S) {
        // your code here 
        //Need subset method
        return this.subset(S) && S.subset(this);
    }
   
    /* 
     * insert(int k)
     * 
     * inserts k at next available position in set if it is not in set
     * 
     * returns: boolean
     */
    public void insert(int k) {
        // your code here
        if (k == 0 && !isEmpty()) { 
            return;
        }
        if (member(k)) { 
            // System.out.println("This is also printed, 2");
            return;
        }
        if (next == size) { 
            // System.out.println("This is also printed, 3");
            resize();
        }
        // System.out.println("This is also printed, 4");
        set[next] = k;
        // System.out.println("This is also printed, 5");
        // System.out.println("set looks as so: " + Arrays.toString(set));
        next++;
        // System.out.println("next looks as so: " + getNext());
        // System.out.println(Arrays.toString(set));
        // System.out.println();
    }
   //question xfortine
   /* 
     * delete(int k)
     * 
     * deletes k from set if in set, otherwise does nothing
     * 
     * returns: void
     */
    public void delete(int k) {
        // your code here

        if (!member(k)) { 
            // System.out.println("This ran!");
            return;
        }    
        for (int i = 0; i < next; i++) { 
            if (set[i] == k) { 
                if (k == set[next-1]) { 
                    next--; 
                    return;
                }
                int indexOfK = i;
                for (int j = indexOfK; j < next - 1; j++) { 
                    set[j] = set[j + 1];
                    // next--;
                }
            }
        }
        next--;
    }
    /* 
     * union(Set S)
     * 
     * unions this Set and S
     * 
     * returns: Set
     */
    public Set union(Set S) {
        Set clonedSet = S.clone();
       
       for (int i = 0; i < next; i++) { 
            clonedSet.insert(set[i]);
        }
       
        return clonedSet;
    }
   /* 
     * intersection(Set S)
     * 
     * creates an intersected Set with this and S
     * 
     * returns: Set
     */
    public Set intersection(Set S) {
        // your code here
        int larger; 
        Set intersection = new Set();
        if (S.next > next) { 
            larger = S.next;
            for (int i = 0; i < larger; i++) { 
                if (member(S.set[i])) { 
                    intersection.insert(set[i]);
                }
            }
        }
        else { 
            larger = next;
            for (int i = 0; i < larger; i++) { 
                if (S.member(set[i])) { 
                    intersection.insert(set[i]);
                }
            }
        }
        // for (int i = 0; i < smaller; i++) { 
        //     if (member(S.set[i]) && S.member(set[i])) { 
        //         intersection.insert(set[i]);
        //     }
        // }
        return intersection;
    }
    /* 
     * setdifference(Set S)
     * 
     * creates a set of the set difference of this and S
     * 
     * returns: Set
     */
    public Set setdifference(Set S) {
        // your code here
        Set setDifference = new Set();
        for (int i = 0; i < next; i++) { 
            if (!S.member(set[i])) { 
                setDifference.insert(set[i]);
            }
        }
        return setDifference;
    }   
    /* 
     * getSet()
     * 
     *
     * 
     * returns: the set integer arr
     */
    public int[] getSet() {
        return set;
    }
     /* 
     * getNext()
     * 
     *
     * 
     * returns: int, next
     */
    public int getNext() { 
        return next;
    }
}