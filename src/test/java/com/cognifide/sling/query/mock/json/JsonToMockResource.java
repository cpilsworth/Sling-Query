package com.cognifide.sling.query.mock.json;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;

import com.cognifide.sling.query.mock.MockResourceResolver;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public final class JsonToMockResource {


	private JsonToMockResource() {
	}

	public static Resource parse(InputStream inputStream) throws PersistenceException {
		JsonElement element = new JsonParser().parse(new InputStreamReader(inputStream));
		return parseResource(element.getAsJsonObject(), "/", null, null);
	}

	private static MockResourceResolver getResourceResolver(String rootPath, Map<String, Object> properties) {
		Map<String, Map<String, Object>> resources = new HashMap<String, Map<String, Object>>();
		resources.put(rootPath, properties);
		return new MockResourceResolver(new EventAdmin() {
			@Override
			public void postEvent(Event event) {
			}

			@Override
			public void sendEvent(Event event) {
			}
		}, resources);
	}

	private static Resource parseResource(JsonObject object, String name, Resource parent, MockResourceResolver resourceResolver) throws PersistenceException {
		Map<String, Object> properties = new LinkedHashMap<String, Object>();
		Map<String, JsonObject> children = new LinkedHashMap<String, JsonObject>();
		for (Entry<String, JsonElement> entry : object.entrySet()) {
			JsonElement value = entry.getValue();
			if (value.isJsonPrimitive()) {
				properties.put(entry.getKey(), value.getAsString());
			} else if (value.isJsonObject()) {
				children.put(entry.getKey(), value.getAsJsonObject());
			}
		}

		Resource resource;
		if (resourceResolver == null) {
			resourceResolver =  getResourceResolver(name, properties);
			resource = resourceResolver.getResource("/");
		} else {
		    resource = resourceResolver.create(parent, name, properties);
		}

		for (Entry<String, JsonObject> entry : children.entrySet()) {
			parseResource(entry.getValue(), entry.getKey(), resource, resourceResolver);
		}
		resourceResolver.commit();
		return resource;
	}
}
