/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.cognifide.sling.query.mock;

import java.util.Map;

import org.apache.sling.api.resource.AbstractResource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.wrappers.ValueMapDecorator;

public class MockResource extends AbstractResource {

    private final String path;

    private final Map<String, Object> props;

    private final ResourceMetadata rm = new ResourceMetadata();

    private final ResourceResolver resolver;

    public MockResource(final String path,
            final Map<String, Object> props,
            final ResourceResolver resolver) {
        this.path = path;
        this.props = props;
        this.resolver = resolver;
    }

    @Override
    public String getPath() {
        return this.path;
    }

    @Override
    public String getResourceType() {
	    if (props.containsKey("sling:resourceType")) {
		    return (String) props.get("sling:resourceType");
	    } else {
		    return (String) props.get("jcr:primaryType");
	    }
    }

    @Override
    public String getResourceSuperType() {
        return (String)this.props.get("sling:resourceSuperType");
    }

    @Override
    public ResourceMetadata getResourceMetadata() {
        return rm;
    }

    @Override
    public ResourceResolver getResourceResolver() {
        return this.resolver;
    }

    @Override
    public <AdapterType> AdapterType adaptTo(final Class<AdapterType> type) {
        if ( type == ValueMap.class ) {
            return (AdapterType)new ValueMapDecorator(this.props);
        }
        return super.adaptTo(type);
    }

    @Override
    public String toString() {
        return "MockResource [path=" + path + ", props=" + props + "]";
    }

}
