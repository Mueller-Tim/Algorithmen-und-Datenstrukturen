package ch.zhaw.ads;

import java.util.AbstractList;
import java.util.List;
import java.util.Objects;

public class MyList extends AbstractList {

	protected ListNode head = new ListNode();;
	protected int size = 0;

	class ListNode{
		Object data;
		ListNode next, prev;
	}

	public boolean add(Object o){

		ListNode nextNode = new ListNode();

		if(Objects.nonNull(head.data)){
			nextNode.next = head;
			nextNode.prev = head.prev;
			head.prev.next = nextNode;
			head.prev = nextNode;
			nextNode.data = o;
		} else{
			head.data = o;
			head.prev = head;
			head.next = head;
		}
		size++;
		return true;
	}

	public boolean remove(Object obj){
		if(size == 0){
			return false;
		}
		ListNode pointer = head;
		int index = 0;

		while(size != 0 && !pointer.data.equals(obj) && index < size){
			index++;
			pointer = pointer.next;
		}
		if(index < size){
			if(head.data.equals(obj)){
				if(size == 1){
					head.data = null;
					head.next = null;
					head.prev = null;
				} else{
					head.data = head.next.data;
					head.next = head.next.next;
					head.next.prev = head;
				}
			} else{
				pointer.prev.next = pointer.next;
				pointer.next.prev = pointer.prev;
			}
			size--;
			return true;
		}
		return true;
	}

	@Override
	public Object get(int index) {
		ListNode nextNode = head;
		if(Objects.nonNull(nextNode.data)){
			for(int i = 0; i < index; i++){
				nextNode = nextNode.next;
			}
		}
		return nextNode.data;
	}

	public boolean isEmpty(){
		return size == 0;
	}

	@Override
	public int size() {
		return size;
	}

	public void clear(){
		head.data = null;
		head.next = null;
		head.prev = null;
		size = 0;
	}

}
