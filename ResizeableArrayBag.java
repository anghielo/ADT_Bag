import java.util.Arrays;

/** A class of bags whose entries are stored in a resizeable array.
    The bag has a max capacity. */
public class ResizeableArrayBag<T> implements BagInterface<T> {
	private T[] bag;
	private static final int DEFAULT_CAPACITY = 25;
	private int numberOfEntries;
	private boolean integrityOK = false;
	private static final int MAX_CAPACITY = 10000; 
	
	/** No-argument constructor sets the DEFAULT_CAPACITY for the array bag. */
	public ResizeableArrayBag() {
		this(DEFAULT_CAPACITY);
	}
	
	/** One-argument constructor must specify the array's length, which is the bag's capacity.
	    @param desiredCapacity The initial capacity of the bag. */
	public ResizeableArrayBag(int desiredCapacity) {
		// Avoid user or hacker from potentially crashing system. Enhances robustness of code
		if(desiredCapacity <= MAX_CAPACITY)
		{
			@SuppressWarnings("unchecked")
			T[] tempBag = (T[]) new Object[desiredCapacity]; 				// Unchecked type cast
			bag = tempBag;
			numberOfEntries = 0;
			integrityOK = true;
		}
		else
			throw new IllegalStateException("Attempt to create a bag whose capacity exceeds allowed maximum.");
	}
	
	/** Checks the integrity of the object and throws an exception if this object is not initialized. */
	private void checkIntegrity() {
		if(!integrityOK)
			throw new SecurityException("ArrayBag object is corrupt.");
	}
	
	/** Gets the current number of entries in this bag.
	   @return The integer number of entries currently in this bag. */
	@Override
	public int getCurrentSize() {
		return numberOfEntries;
	}
	
	/** Sees whether this bag is full.
	   @return True if this bag is full, or false if not. */
	public boolean isFull() {
		return numberOfEntries == bag.length;
	}
	
	/** Sees whether this bag is empty.
	   @return True if this bag is empty, or false if not. */
	@Override
	public boolean isEmpty() {
		return numberOfEntries == 0;
	}

	/** Adds a new entry to this bag.
	   @param newEntry The object to be added as a new entry.
	   @return True. */
	@Override
	public boolean add(T newEntry) {
		checkIntegrity();
		boolean result = true;
		if(isFull())
			doubleCapacity();
		
		bag[numberOfEntries] = newEntry;
		numberOfEntries++;
		
		return result;
	}
	
	/** Doubles the size of the array bag.
	    Precondition: checkIntegrity() has been called. */
	private void doubleCapacity() {
		int newLength = 2 * bag.length;
		checkCapacity(newLength);											// Check that MAX_CAPACITY has not been reached.
		bag = Arrays.copyOf(bag, newLength);								// O(n) because array elements have to be copied.
	}
	
	/** Throws an exception if the client requests a capacity that is too large. */
	private void checkCapacity(int capacity) {
		if(capacity > MAX_CAPACITY) {
			throw new IllegalStateException("Attempted to create a bag whose capacity exceeds " +
											"allowed maximum of" + MAX_CAPACITY);
		}
	}

	/** Removes one unspecified entry from this bag, if possible.
	   @return Either the removed entry, if the removal was successful, or null. */
	@Override
	public T remove() {														// User might want to know what value is deleted so we return the removed value
		checkIntegrity();
		T result = removeEntry(numberOfEntries - 1);
		
		return result;
	}

	/** Removes one occurrence of a given entry from this bag, if possible.
	   @param anEntry The entry to be removed.
	   @return True if the removal was successful, or false if not. */
	@Override
	public boolean remove(T anEntry) {
		checkIntegrity();
		int index = getIndexOf(anEntry);
		T result = removeEntry(index);										// Swapping the index with the last entry for efficiency
		
		return anEntry.equals(result);
	}

	/** Removes and returns the entry at a given index within the array bag.
	//  If no such entry exists, returns null. */
	// Preconditions: 0 <= givenIndex < numberOfEntries; checkIntegrity() has been called
	private T removeEntry(int givenIndex) {
		T result = null;
		if(!isEmpty() && (givenIndex >= 0)){
			result = bag[givenIndex];										// Copy the data from givenIndex to the temporary "result" variable
			bag[givenIndex] = bag[numberOfEntries - 1];						// Overwrite the data in givenIndex position with the last item
			bag[numberOfEntries - 1] = null;								// For security reasons, nullify data in the last item's position
			numberOfEntries--;
		}
		return result;
	}
	
	/** Locates a given entry within this bag. Returns an index to the array
        containing the entry, if located, or -1 otherwise. */
	// Precondition: checkIntegrity() has been called.
	private int getIndexOf(T anEntry) {
		int where = -1;
		boolean found = false;	// Create flag to break from while loop
		int index = 0;
		
		while(!found && (index < numberOfEntries)) {
			if(anEntry.equals(bag[index])) {
				found = true;
				where = index;
			}
			index++;
		}
		
		// Assertion: If where > -1, anEntry is in the array bag, and it equals bag[where]; otherwise, anEntry is not in the array.
		return where;
	}
	
	/** Removes all entries from this bag. */
	@Override
	public void clear() {
		while(!isEmpty())
			remove();			// Better to use for security reasons
	}
	
	/** Counts the number of times a given entry appears in this bag.
	   @param anEntry The entry to be counted.
	   @return The number of times anEntry appears in this bag. */
	@Override
	public int getFrequencyOf(T anEntry) {
		checkIntegrity();
		int counter = 0;
		
		for(int i = 0; i < numberOfEntries; i++)
			if(anEntry.equals(bag[i]))
				counter++;
		
		return counter;
	}

	/** Tests whether this bag contains a given entry.
	   @param anEntry The entry to locate.
	   @return True if this bag contains anEntry, or false otherwise. */
	@Override
	public boolean contains(T anEntry) {
		checkIntegrity();
		
		return getIndexOf(anEntry) > -1;
	}

	@Override
	public T[] toArray() {
		// The cast is safe because the new array contains null entries
		@SuppressWarnings("unchecked")
		T[] result = (T[]) new Object[numberOfEntries]; 					// Unchecked type casting
		
		for(int i = 0; i < numberOfEntries; i++)
			result[i] = bag[i];
		
		return result;														// returns to the client the newly allocated array
	}
	
	// Method to add the contents of "this.bag" to the calling bag
	private void fillBag(BagInterface<T> otherBag) {
		int i = 0;
		while(i < this.getCurrentSize())
			otherBag.add(this.bag[i++]);
	}
	
	/** A new collection of entries created in a new bag after the contents of the first and second bag are combined.
        Note: The union does not affect the contents of the first and second bag.
        @param otherBag The other second bag
        @return A new bag collection that contains all the items from the first and second bag. */
	@Override
	public BagInterface<T> union(BagInterface<T> otherBag) {
		// Bag to hold final contents of the first and second bag
		BagInterface<T> unionBag = new ResizeableArrayBag<T>();
		
		// Add the contents from the first bag to the unionBag
		this.fillBag(unionBag);											// Function will add item to the end of the bag as long as the array is not full O(1) (Best), otherwise a new doubled-sized array will need to be created and items copied over O(n) (Worst)
		
		// Bag to hold the contents from the second bag
		T[] otherTempBag = otherBag.toArray();							// Function will always have to copy the items to a new array O(n)
		
		int i = 0;
		while(i < otherTempBag.length)
			// Add the contents from the second bag to the union bag
			unionBag.add(otherTempBag[i++]);							// Function will add item to the end of the bag as long as the array is not full O(1) (Best), otherwise a new doubled-sized array will need to be created and items copied over O(n) (Worst)
		
		return unionBag;
	}

	/** A new collection of entries created in a new bag when ONLY the same items are from the first and second bag.
        Note: The intersection does not affect the contents of the first and second bag.
        @param otherBag The other second bag
        @return A new bag collection that contains all the items that are ONLY in the first and second bag. */
	@Override
	public BagInterface<T> intersection(BagInterface<T> otherBag) {
		// Bag to hold the contents that are ONLY in the first and second bag
		BagInterface<T> intersectionBag = new ResizeableArrayBag<T>();
		
		// Bag to work with and hold contents from the bags
		BagInterface<T> tempHoldingBag = new ResizeableArrayBag<T>();
		
		// Add the contents from the first bag to the tempHoldingBag
		this.fillBag(tempHoldingBag);									// Function will add item to the end of the bag as long as the array is not full O(1) (Best), otherwise a new doubled-sized array will need to be created and items copied over O(n) (Worst)
		
		// Bag to hold the contents from the second bag		
		T[] otherTempBag = otherBag.toArray();							// Function will always have to copy the items to a new array O(n)
		
		int i = 0;
		while(i < otherTempBag.length) {
			// Check if the items from the second bag are in the first bag
			if(tempHoldingBag.contains(otherTempBag[i])) {				// If function finds item at beginning of the bag it is O(1) (Best), otherwise the item will be found at the end O(n) (Worst)
				// Add contents from the second bag to the intersectionBag only if the item is also in the first bag
				intersectionBag.add(otherTempBag[i]);					// Function will add item to the end of the bag as long as the array is not full O(1) (Best), otherwise a new doubled-sized array will need to be created and items copied over O(n) (Worst)
				// Remove the item from tempHoldingBag once it has been added so it reiterates the loop with remaining items
				tempHoldingBag.remove(otherTempBag[i]);					// If function finds item at beginning of the bag it is O(1) (Best), otherwise the item will be removed at the end O(n) (Worst)
			}
			i++;
		}
		
		return intersectionBag;
	}
	
	/** A new collection of entries created in a new bag that is left over after removing those that also occur in the second bag.
        Note: The difference does not affect the contents of the first and second bag.
        @param otherBag The other second bag.
        @return A new bag collection that contains all items that are left over after removing those that appear in the second bag. */
	@Override
	public BagInterface<T> difference(BagInterface<T> otherBag) {		
		// Bag to hold the contents that are left over in the first bag after removing those that appear in the second bag
		BagInterface<T> differenceBag = new ResizeableArrayBag<T>();
		
		// Add the contents from the first bag to the differenceBag
		this.fillBag(differenceBag);									// Function will add item to the end of the bag as long as the array is not full O(1) (Best), otherwise a new doubled-sized array will need to be created and items copied over O(n) (Worst)
		
		// Bag to hold the contents from the second bag
		T[] otherTempBag = otherBag.toArray();							// Function will always have to copy the items to a new array O(n)
		
		int i = 0;
		while(i < otherTempBag.length) {
			// Check if the items from the second bag are in the first bag
			if(differenceBag.contains(otherTempBag[i])) {				// If function finds item at beginning of the bag it is O(1) (Best), otherwise the item will be found at the end O(n) (Worst)
				// Remove the item from the first bag if it is found in the second bag
				differenceBag.remove(otherTempBag[i]);					// If function finds item at beginning of the bag it is O(1) (Best), otherwise the item will be removed at the end O(n) (Worst)
			}
			i++;
		}
		
		return differenceBag;
	}
	
	/** The toString method overrides the superclass toString method. This version includes the contents of the bag.
	    @return Literal concatenated string. */
	public String toString() {
		String myString;
		myString = ("bag contains " + this.getCurrentSize() + " items(s):\n");
		
		Object[] bagArray = this.toArray();
		
		for(int i = 0; i < bagArray.length; i++)
			myString += (bagArray[i] + " ");
		
		myString += "\n";
		return myString;
	}
}
