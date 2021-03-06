package com.cognifide.sling.query.function;

import java.util.Iterator;

import com.cognifide.sling.query.api.Predicate;
import com.cognifide.sling.query.api.function.Option;
import com.cognifide.sling.query.api.function.IteratorToIteratorFunction;
import com.cognifide.sling.query.iterator.FilteringIterator;

public class FilterFunction<T> implements IteratorToIteratorFunction<T> {

	private final Predicate<T> predicate;

	public FilterFunction(Predicate<T> predicate) {
		this.predicate = predicate;
	}

	@Override
	public Iterator<Option<T>> apply(Iterator<Option<T>> input) {
		return new FilteringIterator<T>(input, predicate);
	}

}
