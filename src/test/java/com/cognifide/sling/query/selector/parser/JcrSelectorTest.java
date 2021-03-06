package com.cognifide.sling.query.selector.parser;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.cognifide.sling.query.mock.MockTypeResolver;
import com.cognifide.sling.query.resource.jcr.query.JcrQueryBuilder;

public class JcrSelectorTest {
	@Test
	public void parseResourceType() {
		final String selector = "foundation/components/parsys";
		final String jcrQuery = "SELECT * FROM [nt:base] AS s WHERE s.[sling:resourceType] = 'foundation/components/parsys'";
		Assert.assertEquals(jcrQuery, parse(selector, "/"));
	}

	@Test
	public void parsePrimaryType() {
		final String selector = "cq:Page";
		final String jcrQuery = "SELECT * FROM [cq:Page] AS s";
		Assert.assertEquals(jcrQuery, parse(selector, "/"));
	}

	@Test
	public void parsePath() {
		final String selector = "cq:Page";
		final String jcrQuery = "SELECT * FROM [cq:Page] AS s WHERE ISDESCENDANTNODE([/content])";
		Assert.assertEquals(jcrQuery, parse(selector, "/content"));
	}

	@Test
	public void parseEmptySelector() {
		final String selector = "";
		final String jcrQuery = "SELECT * FROM [nt:base] AS s";
		Assert.assertEquals(jcrQuery, parse(selector, "/"));
	}

	@Test
	public void parseEquals() {
		final String selector = "[key1=value]";
		final String jcrQuery = "SELECT * FROM [nt:base] AS s WHERE s.[key1] = 'value'";
		Assert.assertEquals(jcrQuery, parse(selector, "/"));
	}

	@Test
	public void parseNotEquals() {
		final String selector = "[key1!=value]";
		final String jcrQuery = "SELECT * FROM [nt:base] AS s WHERE s.[key1] != 'value'";
		Assert.assertEquals(jcrQuery, parse(selector, "/"));
	}

	@Test
	public void parseContains() {
		final String selector = "[key1*=value]";
		final String jcrQuery = "SELECT * FROM [nt:base] AS s WHERE s.[key1] LIKE '%value%'";
		Assert.assertEquals(jcrQuery, parse(selector, "/"));
	}

	@Test
	public void parseContainsWord() {
		final String selector = "[key1~=value]";
		final String jcrQuery = "SELECT * FROM [nt:base] AS s WHERE s.[key1] LIKE '%value%'";
		Assert.assertEquals(jcrQuery, parse(selector, "/"));
	}

	@Test
	public void parseStartsWith() {
		final String selector = "[key1^=value]";
		final String jcrQuery = "SELECT * FROM [nt:base] AS s WHERE s.[key1] LIKE 'value%'";
		Assert.assertEquals(jcrQuery, parse(selector, "/"));
	}

	@Test
	public void parseEndsWith() {
		final String selector = "[key1$=value]";
		final String jcrQuery = "SELECT * FROM [nt:base] AS s WHERE s.[key1] LIKE '%value'";
		Assert.assertEquals(jcrQuery, parse(selector, "/"));
	}

	@Test
	public void parseEscapedValue() {
		final String selector = "[key1=value'123]";
		final String jcrQuery = "SELECT * FROM [nt:base] AS s WHERE s.[key1] = 'value''123'";
		Assert.assertEquals(jcrQuery, parse(selector, "/"));
	}

	@Test
	public void parseMultipleConditions() {
		final String selector = "[key1=value1][key2=value2]";
		final String jcrQuery = "SELECT * FROM [nt:base] AS s WHERE (s.[key1] = 'value1' AND s.[key2] = 'value2')";
		Assert.assertEquals(jcrQuery, parse(selector, "/"));
	}

	@Test
	public void parseMultipleConditionsWithResourceTypeAndPath() {
		final String selector = "foundation/components/parsys[key1=value1][key2=value2]";
		final String jcrQuery = "SELECT * FROM [nt:base] AS s WHERE (ISDESCENDANTNODE([/content]) AND (s.[sling:resourceType] = 'foundation/components/parsys' AND s.[key1] = 'value1' AND s.[key2] = 'value2'))";
		Assert.assertEquals(jcrQuery, parse(selector, "/content"));
	}

	@Test
	public void parseAttributeWithSubresource() {
		final String selector = "cq:Page[jcr:content/cq:template=xyz]";
		final String jcrQuery = "SELECT * FROM [cq:Page] AS s WHERE ISDESCENDANTNODE([/content])";
		Assert.assertEquals(jcrQuery, parse(selector, "/content"));
	}

	static String parse(String selector, String root) {
		List<Selector> selectors = SelectorParser.parse(selector);
		List<SelectorSegment> segments = SelectorParser.getFirstSegmentFromEachSelector(selectors);
		return new JcrQueryBuilder(new MockTypeResolver()).buildQuery(segments, root);
	}
}
