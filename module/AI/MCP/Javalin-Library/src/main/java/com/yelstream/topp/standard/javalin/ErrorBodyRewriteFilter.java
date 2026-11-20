/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024-2026 Morten Sabroe Mortensen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yelstream.topp.standard.javalin;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Servlet filter rewriting the body of error responses, i.e. responses with status 400 or higher.
 * <p>
 * Only bodies written through {@link HttpServletResponse#getWriter()} are rewritten.
 * Successful responses pass through unbuffered, so streaming responses (e.g. SSE) are unaffected.
 * </p>
 * <p>
 * Whether a body is captured is decided by the status at the time the writer is obtained:
 * </p>
 * <ul>
 *     <li>Status 400 or higher: the body is captured in memory,
 *         and after the filter chain returns, the rewritten body is written to the actual response.</li>
 *     <li>Otherwise: the actual writer is returned, and the body is not rewritten,
 *         even if the status is set to an error afterwards.</li>
 * </ul>
 * <p>
 * When a body is rewritten, any {@code Content-Length} set by the filtered servlet is reset,
 * leaving the length of the rewritten body to the container.
 * Other headers set by the filtered servlet are kept as-is.
 * </p>
 * <p>
 * Created with the rewriter as the only argument, by a Lombok-generated constructor;
 * {@link NullPointerException} is thrown if the rewriter is {@code null}.
 * </p>
 * <p>
 * Usage, e.g. via {@link Javalins#create(jakarta.servlet.http.HttpServlet, String, ErrorBodyRewriter)}:
 * </p>
 * <pre>{@code
 * handler.addFilter(new FilterHolder(new ErrorBodyRewriteFilter((status, body) -> "{\"error\":" + status + "}")),
 *                   "/mcp",
 *                   EnumSet.of(DispatcherType.REQUEST));
 * }</pre>
 *
 * @author Morten Sabroe Mortensen
 * @since 2026-11-19
 */
@AllArgsConstructor
public final class ErrorBodyRewriteFilter extends HttpFilter {
    /**
     * Rewrites the body of an error response.
     */
    @FunctionalInterface
    public interface ErrorBodyRewriter {
        /**
         * Rewrites an error body.
         * <p>
         * Called once per error response with a captured body, after the filtered servlet has finished.
         * </p>
         * @param status HTTP status code; 400 or higher.
         * @param body Original body, as written through the writer; possibly empty.
         * @return Rewritten body; written to the actual response in place of the original body.
         */
        String rewrite(int status, String body);
    }

    /**
     * Rewriter of error bodies.
     */
    @NonNull
    private final transient ErrorBodyRewriter rewriter;

    /**
     * Passes the request on with a wrapped response, and writes the rewritten error body, if any, afterwards.
     * @param request HTTP request.
     * @param response HTTP response.
     * @param chain Filter chain.
     * @throws IOException Thrown in case of I/O error.
     * @throws ServletException Thrown in case of servlet error.
     */
    @Override
    protected void doFilter(HttpServletRequest request,
                            HttpServletResponse response,
                            FilterChain chain) throws IOException, ServletException {
        ErrorCapturingResponse wrapped = new ErrorCapturingResponse(response);
        chain.doFilter(request, wrapped);
        wrapped.flushRewritten(rewriter);
    }

    /**
     * Response wrapper capturing the writer output only once the status signals an error.
     */
    private static final class ErrorCapturingResponse extends HttpServletResponseWrapper {
        /**
         * Captured error body; {@code null} until an error body is written.
         */
        private StringWriter captured;

        /**
         * Writer into {@link #captured}; {@code null} until an error body is written.
         */
        private PrintWriter capturingWriter;

        /**
         * Constructor.
         * @param response Actual response.
         */
        ErrorCapturingResponse(HttpServletResponse response) {
            super(response);
        }

        /**
         * Gets the writer of the response body.
         * <p>
         * If the status signals an error, returns a writer capturing the body in memory;
         * the same writer is returned on repeated calls.
         * Otherwise, returns the writer of the actual response.
         * </p>
         * @return Writer.
         * @throws IOException Thrown in case of I/O error.
         */
        @Override
        public PrintWriter getWriter() throws IOException {
            if (getStatus() < HttpServletResponse.SC_BAD_REQUEST) {
                return super.getWriter();
            }
            if (capturingWriter == null) {
                captured = new StringWriter();
                capturingWriter = new PrintWriter(captured);
            }
            return capturingWriter;
        }

        /**
         * Writes the rewritten error body to the actual response.
         * <p>
         * Does nothing if no error body has been captured.
         * Any {@code Content-Length} set by the filtered servlet is reset first,
         * since it applies to the original body;
         * this is done by resetting the buffer and removing the header, unless the response is already committed.
         * </p>
         * @param rewriter Rewriter of error bodies.
         * @throws IOException Thrown in case of I/O error.
         */
        void flushRewritten(ErrorBodyRewriter rewriter) throws IOException {
            if (captured == null) {
                return;
            }
            capturingWriter.flush();
            if (!isCommitted()) {
                // Nothing has reached the actual output yet; the original body was captured.
                // Resetting the buffer also clears a declared content length (e.g. in Jetty),
                // and removing the header leaves the length of the rewritten body to the container.
                resetBuffer();
                setContentLengthLong(-1);
            }
            PrintWriter out = super.getWriter();
            out.write(rewriter.rewrite(getStatus(), captured.toString()));
            out.flush();
        }
    }
}
