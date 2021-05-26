/** A bag interface describing the operations of a bag of objects.
    The interface provides a well-regulated communication between
    its hidden implementation and the client program. */
public interface BagInterface<T> {
	
	/** Gets the current number of entries in this bag.
	   @return The integer number of entries currently in the bag. */
	public int getCurrentSize();
	
	/** Sees whether this bag is empty.
	   @return True if the bag is empty, or false if not. */
	public boolean isEmpty();
	
	/** Adds a new entry to this bag.
	   @param newEntry The object to be added as a new entry.
	   @return True if the addition is successful, or false if not. */
	public boolean add(T newEntry);
	
	/** Removes one unspecified entry from this bag, if possible.
	   @return Either the removed entry, if the removal was successful, or null. */
	public T remove();
	
	/** Removes one occurrence of a given entry from this bag, if possible.
	   @param anEntry The entry to be removed.
	   @return True if the removal was successful, or false if not. */
	public boolean remove(T anEntry);
	
	/** Removes all entries from this bag. */
	public void clear();
	
	/** Counts the number of times a given entry appears in this bag.
	   @param anEntry The entry to be counted.
	   @return The number of times anEntry appears in the bag. */
	public int getFrequencyOf(T anEntry);
	
	/** Tests whether this bag contains a given entry.
	   @param anEntry The entry to find.
	   @return True if the bag contains anEntry, or false if not. */
	public boolean contains(T anEntry);
	
	/** Retrieves all entries that are in this bag.
	   @return A newly allocated array of all the entries in the bag. Note: If the bag is empty, the returned array is empty. */
	public T[] toArray();
	
	/** A new collection of entries created in a new bag after the contents of the first and second bag are combined.
	    Note: The union does not affect the contents of the first and second bag.
	   @param otherBag The other second bag
	   @return A new bag collection that contains all the items from the first and second bag. */
	public BagInterface<T> union(BagInterface<T> otherBag);
	
	/** A new collection of entries created in a new bag when ONLY the same items are from the first and second bag.
	    Note: The intersection does not affect the contents of the first and second bag.
	   @param otherBag The other second bag
	   @return A new bag collection that contains all the items that are ONLY in the first and second bag. */
	public BagInterface<T> intersection(BagInterface<T> otherBag);
	
	/** A new collection of entries created in a new bag that is left over after removing those that also occur in the second bag.
	    Note: The difference does not affect the contents of the first and second bag.
	   @param otherBag The other second bag
	   @return A new bag collection that contains all items that are left over after removing those that appear in the second bag. */
	public BagInterface<T> difference(BagInterface<T> otherBag);
}
