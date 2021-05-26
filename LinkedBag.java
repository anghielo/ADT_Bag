/** A class of bags whose entries are stored in a chain of linked nodes.
    The bag does not get full unless program uses all of the computer's memory. */
public class LinkedBag<T> implements BagInterface<T> {
	
	private Node<T> firstNode;										// Head reference of the chain of nodes
	private int numberOfEntries;									// Tracks the number of nodes in the chain. Also number of entries in the current bag
	
	/** No-argument constructor sets the first node to null and numberOfEntries to 0. */
	public LinkedBag() {
		// Initially, a bag is empty, so the default constructor simply initializes the data fields firstNode to null and numberOfEntries to zero
		firstNode = null;				
		numberOfEntries = 0;			
	}

	/** Gets the current number of entries in this bag.
	   @return The integer number of entries currently in this bag. */
	@Override
	public int getCurrentSize() {
		return numberOfEntries;
	}

	/** Sees whether this bag is empty.
	   @return True if this bag is empty, or false if not. */
	@Override
	public boolean isEmpty() {
		return firstNode == null;									// Can also use: return numberOfEntries == 0; 
	}

	/** Adds a new entry to this bag.
	   @param newEntry The object to be added as a new entry.
	   @return True if the addition is successful, or false if not. */
	@Override
	public boolean add(T newEntry) {
		// newNode references a new instance of Node to place data in
		Node<T> newNode = new Node<T>(newEntry);					// newNode will be added to beginning of chain
		
		if(!isEmpty()) {
			// First step: If the bag is not empty then set newNode to reference the rest of the chain
			newNode.setNextNode(firstNode);							// Make new node reference rest of chain
		}															// firstNode will be null if chain is empty
		// Second step: newNode will now be at the beginning of the chain 
		firstNode = newNode;										// New node is at beginning of chain
		numberOfEntries++;
		
		return true;
	}

	/** Removes one unspecified entry from this bag, if possible.
	   @return Either the removed entry, if the removal was successful, or null. */
	@Override
	public T remove() {												// User might want to know what value is deleted so we return the removed value
		T result = null;
		if(!isEmpty()) {											// Check if the chain is empty
			result = (T) firstNode.getData();						// We want to remove the firstNode because it is efficient O(1)
			firstNode = firstNode.getNextNode();
			numberOfEntries--;
			
		}
		return result;
	}
	
	/** Removes one occurrence of a given entry from this bag, if possible. 
	   @param anEntry The entry to be removed.
	   @return True if the removal was successful or false otherwise. */
	@Override
	public boolean remove(T anEntry) {
		boolean result = false;
		Node<T> nodeN = getReferenceTo(anEntry);
		
		if(nodeN != null) {
			// Replace located entry with entry in first node.
			nodeN.setData(firstNode.getData());						// Copy the data of firstNode to replace the data in the node found
			// Remove first node and let it point to the next one.
			firstNode = firstNode.getNextNode();
			numberOfEntries--;
			
			result = true;
		}
		return result;
	}
	
	/** Locates a given entry within this bag. Returns a reference to the node
	    containing the entry, if located, or null otherwise. */
	private Node<T> getReferenceTo(T anEntry) {
		boolean found = false;
		Node<T> currentNode = firstNode;
		
		while(!found && (currentNode != null)) {
			if(anEntry.equals(currentNode.getData())) {
				found = true;
			}
			else
				currentNode = currentNode.getNextNode();		
		}
		return currentNode;
	}
	
	/** Removes all entries from this bag. */
	@Override
	public void clear() {
		while(!isEmpty())											// Use for security enhancement, otherwise set firstNode == null; and numberOfEntries = 0;
			remove();

	}

	/** Counts the number of times a given entry appears in this bag.
	   @param anEntry The entry to be counted.
	   @return The number of times anEntry appears in this bag. */
	@Override
	public int getFrequencyOf(T anEntry) {
		int frequency = 0;
		int counter = 0;
		Node<T> currentNode = firstNode;
		
		while((counter < numberOfEntries) && (currentNode != null)) {
			if(anEntry.equals(currentNode.getData())) {
				frequency++;
			}
			counter++;
			currentNode = currentNode.getNextNode();
		}
		
		return frequency;
	}

	/** Tests whether this bag contains a given entry.
	   @param anEntry The entry to locate.
	   @return True if the bag contains anEntry, or false otherwise. */
	@Override
	public boolean contains(T anEntry) {
		boolean found = false;
		Node<T> currentNode = firstNode;
		
		while(!found && (currentNode != null)) {
			if(anEntry.equals(currentNode.getData())) {
				found = true;
			}
			else {
				currentNode = currentNode.getNextNode();
			}
		}
		return found;
	}

	/** Retrieves all entries that are in this bag.
	   @return A newly allocated array of all the entries in this bag.
	   Note: If the bag is empty, the returned array is empty. */
	@Override
	public T[] toArray() {
		// The cast is safe because the new array contains null entries
		@SuppressWarnings("unchecked")
		T[] result = (T[]) new Object[numberOfEntries];				// Unchecked type casting
		
		int index = 0;
		/* Local variable currentNode to reference each node in the chain. Initially,
		   we want currentNode to reference the first node in the chain, so we set it to firstNode */
		Node<T> currentNode = firstNode;							// Add <T> to denote type
		while((index < numberOfEntries) && (currentNode != null)) {
			result[index] = currentNode.getData();					// Access the data we want
			index++;
			currentNode = currentNode.getNextNode();				// After accessing the data at currentNode, we then move to the next node until it becomes null
		}
		return result;
	}
	
	// Method adds the contents of this.bag to the calling bag
	private void fillBag(BagInterface<T> otherBag) {
		Node<T> currentNode = firstNode;
		int i = 0;
		while((i < this.getCurrentSize()) && (currentNode != null)) {
			otherBag.add(currentNode.getData());
			i++;
			currentNode = currentNode.getNextNode();
		}
	}

	/** A new collection of entries created in a new bag after the contents of the first and second bag are combined.
    Note: The union does not affect the contents of the first and second bag.
    @param otherBag The other second bag
    @return A new bag collection that contains all the items from the first and second bag. */
	@Override
	public BagInterface<T> union(BagInterface<T> otherBag) {
		// Bag to hold final contents of the first and second bag
		BagInterface<T> unionBag = new LinkedBag<T>();
		
		// Add the contents from the first bag to the unionBag
		this.fillBag(unionBag);											// Function will add item to the beginning of the chain O(1)
		
		// Bag to hold the contents from the second bag
		LinkedBag<T> otherTempBag = (LinkedBag<T>) otherBag;
		
		// Create a currentNode to point to the second bag's firstNode for clarity of traversal
		Node<T> currentNode = otherTempBag.firstNode;
		
		int i = 0;
		while((i < unionBag.getCurrentSize()) && (currentNode != null)) {
			unionBag.add(currentNode.getData());						// Function will add item to the beginning of the chain O(1)
			i++;
			currentNode = currentNode.getNextNode();					// The currentNode will point to the next node of the chain
		}
		return unionBag;
	}

	/** A new collection of entries created in a new bag when ONLY the same items are from the first and second bag.
    Note: The intersection does not affect the contents of the first and second bag.
    @param otherBag The other second bag
    @return A new bag collection that contains all the items that are ONLY in the first and second bag. */
	@Override
	public BagInterface<T> intersection(BagInterface<T> otherBag) {
		// Bag to hold the contents that are ONLY in the first and second bag
		BagInterface<T> intersectionBag = new LinkedBag<T>();
		
		// Bag to work with and hold contents from the bags
		BagInterface<T> tempHoldingBag = new LinkedBag<T>();
		
		// Add the contents from the first bag to the tempHoldingBag
		this.fillBag(tempHoldingBag);									// Function will add item to the beginning of the chain O(1)
		
		// Bag to hold the contents from the second bag		
		LinkedBag<T> otherTempBag = (LinkedBag<T>) otherBag;
		
		// Create a currentNode to point to the second bag's firstNode for clarity of traversal
		Node<T> currentNode = otherTempBag.firstNode;
		
		int i = 0;
		while((i < otherTempBag.getCurrentSize()) && (currentNode != null)) {
			// Check if the items from the second bag are in the first bag
			if(tempHoldingBag.contains(currentNode.getData())) {		// If function finds item at beginning of the bag it is O(1) (Best), otherwise the item will be found at the end O(n) (Worst)
				// Add contents from the second bag to the intersectionBag only if the item is also in the first bag
				intersectionBag.add(currentNode.getData());				// Function will add item to the beginning of the chain O(1)
				// Remove the item from tempHoldingBag once it has been added so it reiterates the loop with remaining items
				tempHoldingBag.remove(currentNode.getData());			// If function finds item at beginning of the bag it is O(1) (Best), otherwise the item will be removed at the end O(n) (Worst)				
			}
			i++;
			currentNode = currentNode.getNextNode();					// The currentNode will point to the next node of the chain
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
		BagInterface<T> differenceBag = new LinkedBag<T>();

		// Add the contents from the first bag to the differenceBag
		this.fillBag(differenceBag);									// Function will add item to the beginning of the chain O(1)

		// Bag to hold the contents from the second bag
		LinkedBag<T> otherTempBag = (LinkedBag<T>) otherBag;
		
		// Create a currentNode to point to the second bag's firstNode for clarity of traversal
		Node<T> currentNode = otherTempBag.firstNode;
		
		int i = 0;
		while((i < otherTempBag.getCurrentSize()) && (currentNode != null)) {
			// Check if the items from the second bag are in the first bag
			if(differenceBag.contains(currentNode.getData())) {			// If function finds item at beginning of the bag it is O(1) (Best), otherwise the item will be found at the end O(n) (Worst)
				// Remove the item from tempHoldingBag once it has been added so it reiterates the loop with remaining items
				differenceBag.remove(currentNode.getData());
			}
			i++;
			currentNode = currentNode.getNextNode();					// The currentNode will point to the next node of the chain
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