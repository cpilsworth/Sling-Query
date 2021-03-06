package com.cognifide.sling.query;

import static com.cognifide.sling.query.TestUtils.assertEmptyIterator;
import static com.cognifide.sling.query.TestUtils.assertResourceSetEquals;
import static com.cognifide.sling.query.api.SlingQuery.$;

import org.apache.sling.api.resource.Resource;
import org.junit.Test;

import com.cognifide.sling.query.api.SlingQuery;

public class ClosestTest {

	private static final String PATH = "application/configuration/labels/jcr:content/configParsys/tab_0/items/text";

	private Resource tree = TestUtils.getTree();

	@Test
	public void testClosest() {
		SlingQuery query = $(tree.getChild(PATH)).closest("cq:Page");
		assertResourceSetEquals(query.iterator(), "labels");
	}

	@Test
	public void testNoClosest() {
		SlingQuery query = $(tree.getChild(PATH)).closest("cq:Undefined");
		assertEmptyIterator(query.iterator());
	}

	@Test
	public void testClosestOnRoot() {
		SlingQuery query = $(tree).closest("cq:Page");
		assertResourceSetEquals(query.iterator(), "/");
	}
}
