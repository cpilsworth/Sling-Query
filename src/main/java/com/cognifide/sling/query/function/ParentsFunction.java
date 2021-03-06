package com.cognifide.sling.query.function;

import java.util.Iterator;

import com.cognifide.sling.query.api.Predicate;
import com.cognifide.sling.query.api.TreeProvider;
import com.cognifide.sling.query.api.function.ElementToIteratorFunction;
import com.cognifide.sling.query.iterator.ParentsIterator;

public class ParentsFunction<T> implements ElementToIteratorFunction<T> {

	private final Predicate<T> until;

	private final TreeProvider<T> provider;

	public ParentsFunction(Predicate<T> until, TreeProvider<T> provider) {
		this.until = until;
		this.provider = provider;
	}

	@Override
	public Iterator<T> apply(T resource) {
		return new ParentsIterator<T>(until, resource, provider);
	}

}
