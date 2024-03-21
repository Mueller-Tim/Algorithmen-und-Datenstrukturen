package ch.zhaw.ads;

import java.util.List;
import java.util.Objects;

public class MySortedList extends MyList{



	@Override
	public boolean add(Object o) {
		ListNode nextNode = new ListNode();
		nextNode.data = o;

		if(Objects.isNull(super.head.data)){
			head = nextNode;
			nextNode.prev = nextNode;
			nextNode.next = nextNode;
		} else if(head.data.toString().charAt(0) > o.toString().charAt(0)){
			nextNode.next = head;
			nextNode.prev = head.prev;
			head.prev.next = nextNode;
			head.prev = nextNode;
			head = nextNode;

		} else{
			ListNode currentNode = super.head;
			while(currentNode.next != head && currentNode.next.data.toString().charAt(0) <= o.toString().charAt(0)){
				currentNode = currentNode.next;
			}
			nextNode.next = currentNode.next;
			nextNode.prev = currentNode;
			currentNode.next.prev = nextNode;
			currentNode.next = nextNode;
		}
		super.size++;
		return true;
	}

}
