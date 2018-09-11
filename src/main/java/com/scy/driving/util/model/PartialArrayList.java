package com.scy.driving.util.model;

import java.util.ArrayList;
import java.util.List;

/*
 * 可序列化ArrayList
  */
public class PartialArrayList<T> extends ArrayList<T> {
	
	private static final long serialVersionUID = 1314040611633476482L;
	private int startIndex;
	private int totalSize;
	
	public int getStartIndex() {
		return startIndex;
	}
	
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
	
	public int getTotalSize() {
		return totalSize;
	}
	
	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}
	
	public void populateFromSource(List<T> sourceList) {
		this.clear();
		if (sourceList != null) {
			for (T item : sourceList) {
				this.add(item);
			}
		}
	}
	
}
