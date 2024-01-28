/*
 * Copyright (c) 2024 Kaiserpfalz EDV-Service, Roland T. Lichti
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package de.lichti.klenkes74.starter.logging;



import java.io.IOException;

import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;


/**
 * 
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2024-01-28
 */
@Component
@Order(1)
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true, includeFieldNames = true)
@Slf4j
public class OriginalHostFilter implements Filter {@Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {

        final HttpServletRequest req = (HttpServletRequest) request;

        MDC.put("host", this.resolveHost(req));
        MDC.put("forwardedPath", req.getHeader("X-Fordearded-For"));
        MDC.put("hostProtocol", req.getHeader("X-Forwarded-Proto"));

        log.trace("Added X-Forward info to MDC.");

        chain.doFilter(request, response);

        MDC.remove("host");
        MDC.remove("forwardedPath");
        MDC.remove("hostProtocol");
    }
    
    private String resolveHost(final HttpServletRequest req) {
        String result = req.getHeader("X-Forwarded-Host");

        if (result == null || result.isBlank()) {
            result = req.getRemoteHost();
        }

        return result;
    }
}
