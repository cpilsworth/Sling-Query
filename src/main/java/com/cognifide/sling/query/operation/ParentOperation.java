package com.cognifide.sling.query.operation;

import java.util.Iterator;

import org.apache.sling.api.resource.Resource;

import com.cognifide.sling.query.EmptyIterator;
import com.cognifide.sling.query.Operation;
import com.cognifide.sling.query.iterator.ArrayIterator;

public class ParentOperation implements Operation {

	@Override
	public Iterator<Resource> getResources(Resource resource) {
		Resource parent = resource.getParent();
		if (parent == null) {
			return EmptyIterator.INSTANCE;
		} else {
			return new ArrayIterator(parent);
		}
	}

}
