package com.alliander.osgp.shared.filters;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

/**
 * Filter which allows all cross-origin requests (for testing purposes.
 */
@Provider
public class CorsFilter implements ContainerResponseFilter {

    private static final String ACCESS_CONTROL_ALLOW_HEADER = "Access-Control-Allow-Origin";

    @Override
    public void filter(final ContainerRequestContext requestContext, final ContainerResponseContext responseContext)
            throws IOException {
        responseContext.getHeaders().putSingle(ACCESS_CONTROL_ALLOW_HEADER, "*");
    }
}
