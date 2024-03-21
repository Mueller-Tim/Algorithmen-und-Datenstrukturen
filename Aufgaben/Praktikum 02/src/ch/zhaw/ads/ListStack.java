package ch.zhaw.ads;

import java.util.ArrayList;
import java.util.List;

public class ListStack implements Stack{

	private List<Object> list = new ArrayList<>();

	@Override
	public void push(Object pushedObject) throws StackOverflowError {
		list.add(0, pushedObject);
	}

	@Override
	public Object pop() {
		if(list.isEmpty()){
			return null;
		} else{
			return list.remove(0);
		}
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public Object peek() {
		if(list.isEmpty()){
			return null;
		} else{
			return list.get(0);
		}
	}

	@Override
	public void removeAll() {
		list = new ArrayList<>();
	}

	@Override
	public boolean isFull() {
		return false;
	}
}
