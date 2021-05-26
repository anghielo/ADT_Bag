public class Node<T> {		// Non-inner class, therefore we must specify generic data type <T> 
	private T data;			// Entry in bag
	private Node<T> next;	// Link to the next node
	
	// The constructor's name is Node, not Node<T>
	public Node(T dataPortion) {
		this(dataPortion, null);
	}
	
	public Node(T dataPortion, Node<T> nextNode) {
		data = dataPortion;
		next = nextNode;
	}
	
	public T getData() {
		return data;
	}
	
	public void setData(T newData) {
		data = newData;
	}
	
	public Node<T> getNextNode() {
		return next;
	}
	
	public void setNextNode(Node<T> nextNode) {
		next = nextNode;
	}
}
