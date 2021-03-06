package com.cognifide.sling.query.iterator;

import java.util.Iterator;

import com.cognifide.sling.query.api.Predicate;
import com.cognifide.sling.query.api.function.Option;

public class FilteringIterator<T> extends AbstractIterator<Option<T>> {

	private final Iterator<Option<T>> iterator;

	private final Predicate<T> predicate;

	public FilteringIterator(Iterator<Option<T>> iterator, Predicate<T> predicate) {
		this.iterator = iterator;
		this.predicate = predicate;
	}

	@Override
	protected Option<T> getElement() {
		while (iterator.hasNext()) {
			Option<T> element = iterator.next();
			if (element.isEmpty() || predicate.accepts(element.getElement())) {
				return element;
			} else {
				return Option.empty(element.getArgumentId());
			}
		}
		return null;
	}
}
