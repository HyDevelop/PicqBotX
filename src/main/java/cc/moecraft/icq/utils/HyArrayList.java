package cc.moecraft.icq.utils;

import java.util.ArrayList;
import java.util.Collection;

public class HyArrayList<E> extends ArrayList<E> {

	public HyArrayList() {
		super();
	}

	public HyArrayList(int initialCapacity) {
		super(initialCapacity);
	}

	public HyArrayList(Collection<? extends E> coll) {
		super(coll);
	}

	@Override
	public E get(int index) {
		return index >= size() ? null : super.get(index);
	}

}
