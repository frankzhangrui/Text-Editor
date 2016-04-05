package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		// TODO: Implement this method
		head = new LLNode<E>();
		tail = new LLNode<E>();
		head.next = tail;
		tail.prev = head;
		size = 0;
		
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element ) 
	{
		// TODO: Implement this method
		LLNode<E> node = new LLNode<E>(element);
		LLNode<E> prev = tail.prev ;
		prev.next = node;
		node.prev = prev;
		node.next = tail;
		tail.prev = node;
		size +=1;
		return true;
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) 
	{
		// TODO: Implement this method.
		return this.getNode(index).data;
	}

	private LLNode<E> getNode(int index) {
		if ((index < 0) || (index >= size)) {
			throw new IndexOutOfBoundsException("incorrect index");
		}
		LLNode<E> curr = head;
		for ( int i = 0 ; i<= index ; i++) {
			curr = curr.next;
		}
		return curr;
	}
	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) {
		if(element == null) {
			throw new NullPointerException("element must not be null");
		}
		if(size != 0) {
			if( (index <0) || (index >= size)) {
				throw new IndexOutOfBoundsException("out of bound");
			}
			LLNode<E> prev;
			LLNode<E> node = new LLNode<E>(element);
			if(index == 0) {
				prev = head;
			}
			else {
				prev = this.getNode(index-1);
			}
			node.next = prev.next;
			node.next.prev = node;
			prev.next = node;
			node.prev = prev;
			size += 1;
		}
		else {
			if(index != 0) {
				throw new IndexOutOfBoundsException("out of bound");
			}
			LLNode<E> node = new LLNode<E>(element);
			head.next = node;
			node.next = tail;
			tail.prev = node;
			node.prev = head;
			size +=1;
		}
	}
	


	/** Return the size of the list */
	public int size() 
	{
		// TODO: Implement this method
		return size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) 
	{
		// TODO: Implement this method
		if(size == 0) {
			throw new IndexOutOfBoundsException("cannot remove in empty list");
		}
		if((index < 0) || (index >=size)) {
			throw new IndexOutOfBoundsException("cannot remove in empty list");
		}
		LLNode<E> prev = head;
		for(int i = 0 ; i < index; i++) {
			prev = prev.next;
		}
		LLNode<E> curr = prev.next;
		prev.next = curr.next;
		curr.next.prev = prev;
		curr.prev = null;
		curr.next = null;
		size -=1;
		return curr.data;
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) 
	{
		if(element == null) {
			throw new NullPointerException("element must not be null");
		}
		// TODO: Implement this method
		LLNode<E> node = this.getNode(index);
		E temp = node.data;
		node.data = element;
		return temp;
	}   
}

class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	// TODO: Add any other methods you think are useful here
	// E.g. you might want to add another constructor

	public LLNode(E e) 
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}
	
	public LLNode() {
		this.data = null;
		this.prev = null;
		this.next = null;
	}

}
