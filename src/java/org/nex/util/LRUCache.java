package org.nex.util;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class LRUCache implements IRemovableCache {
	private Map<Object, Object> cache;
	private int MAX_SIZE = 100;

	public LRUCache(int maxSize) {
		this.MAX_SIZE = maxSize;
		this.cache = new LinkedHashMap<Object, Object>(maxSize + 1, 0.75F, true) {
			private static final long serialVersionUID = 1L;

			protected boolean removeEldestEntry(Entry eldest) {
				return this.size() > LRUCache.this.MAX_SIZE;
			}
		};
	}

	public void add(Object key, Object value) {
		Map var3 = this.cache;
		synchronized (this.cache) {
			this.cache.put(key, value);
		}
	}

	public Object get(Object key) {
		Map var2 = this.cache;
		synchronized (this.cache) {
			return this.cache.containsKey(key) ? this.cache.get(key) : null;
		}
	}

	public void remove(Object key) {
		Map var2 = this.cache;
		synchronized (this.cache) {
			if (this.cache.containsKey(key)) {
				this.cache.remove(key);
			}

		}
	}

	public void clear() {
		Map var1 = this.cache;
		synchronized (this.cache) {
			this.cache.clear();
		}
	}

	public int size() {
		Map var1 = this.cache;
		synchronized (this.cache) {
			return this.cache.size();
		}
	}
}
