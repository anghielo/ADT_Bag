/** Union, intersection and difference, as defined in the BagInterface.java and implemented
    in the class LinkedBag.java. */
public class LinkedBagTest {
	public static void main(String[] args) {
		System.out.print("======================== LINKEDBAG TEST ========================\n\n");
		
		// Creating string bags
		BagInterface<String> stringBag1 = new LinkedBag<String>();
		BagInterface<String> stringBag2 = new LinkedBag<String>();
		
		// Creating string arrays
		String[] contentsOfStringBag1 = {"a", "b", "c"};
		String[] contentsOfStringBag2 = {"b", "b", "d", "e"};
		
		// Display original items
		System.out.print("First ");
		showItems(contentsOfStringBag1);
		System.out.print("Second ");
		showItems(contentsOfStringBag2);
		
		// Add items inside the bags
		System.out.println("\n==> Placing items inside the bags ==>\n");
		addItems(stringBag1, contentsOfStringBag1);
		addItems(stringBag2, contentsOfStringBag2);
		
		// Pass the bags "through the wringer"
		throughWringer(stringBag1, stringBag2);
		
		///////////////////////////////////////////////////////////////////////////////////
		
		// Creating integer bags
		BagInterface<Integer> integerBag1 = new LinkedBag<Integer>();
		BagInterface<Integer> integerBag2 = new LinkedBag<Integer>();
		
		// Creating integer arrays		
		Integer[] contentsOfIntegerBag1 = {2, 2, 2, 1, 3, 4, 5, 6, 7};
		Integer[] contentsOfIntegerBag2 = {6, 6, 2, 2, 8, 4, 11, 22, 33, 9};
		
		// Display original items
		System.out.print("First ");
		showItems(contentsOfIntegerBag1);
		System.out.print("Second ");
		showItems(contentsOfIntegerBag2);
			
		// Add items inside the bags
		System.out.println("\n==> Placing items inside the bags ==>\n");
		addItems(integerBag1, contentsOfIntegerBag1);
		addItems(integerBag2, contentsOfIntegerBag2);
		
		throughWringer(integerBag1, integerBag2);
	}
	
	private static <T> void showItems(T[] content) {
		System.out.print("set of items: ");
		for(int i = 0; i < content.length; i++)
			System.out.print(content[i] + " ");
		System.out.println();
	}
	
	private static <T> void addItems(BagInterface<T> aBag, T[] content) {
		for(int i = 0; i < content.length; i++)
			aBag.add(content[i]);
	}
	
	private static <T> void throughWringer(BagInterface<T> bag1, BagInterface<T> bag2) {
		// Show the two bags separately
		System.out.print("First " + bag1);
		System.out.print("Second " + bag2);
		System.out.println("\n===============================================================");
		
		// Show the union of the two bags
		BagInterface<T> everything = bag1.union(bag2);
		System.out.print("UNION OF THE BAGS\nThe new " + everything);
		System.out.println("\n---------------------------------------------------------------");
		
		// Show the intersection of the two bags
		BagInterface<T> commonItems = bag1.intersection(bag2);
		System.out.print("INTERSECTION OF THE BAGS\nThe new " + commonItems);
		System.out.println("\n---------------------------------------------------------------");
		
		// Show the difference of the two bags
		BagInterface<T> leftOver1 = bag1.difference(bag2);
		System.out.print("DIFFERENCE OF FIRST BAG WITH SECOND BAG\nThe new " + leftOver1);
		System.out.println("\n---------------------------------------------------------------");
		
		BagInterface<T> leftOver2 = bag2.difference(bag1);
		System.out.print("DIFFERENCE OF SECOND BAG WITH FIRST BAG\nThe new " + leftOver2);
		System.out.println("\n//////////////////////////////////////////////////////////////\n");
	}
}