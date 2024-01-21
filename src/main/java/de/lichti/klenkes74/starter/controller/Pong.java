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
package de.lichti.klenkes74.starter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.ws.rs.core.MediaType;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;




/**
 * 
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2024-01-20
 */
@RestController
@RequestMapping(value = "/api", produces = MediaType.TEXT_PLAIN)
@ToString(onlyExplicitlyIncluded = true, includeFieldNames = true)
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Slf4j
public class Pong {
    @GetMapping("ping")
    public String ping(
        @RequestHeader(name = "X-Forwarded-Host", defaultValue = "none") String originalHost,
        @RequestHeader(name = "X-Forwarded-Proto", defaultValue = "http") String proto,
        @RequestParam(name = "nonce", defaultValue="pong") final String nonce
    ) {
        log.info("Server pinged. client='{}', protocol='{}', nonce='{}'", originalHost, proto, nonce);

        return nonce;
    }
    
    @GetMapping("ping/{nonce}")
    public String pingPath(
        @RequestHeader(name = "X-Forwarded-Host", defaultValue = "none") String originalHost,
        @RequestHeader(name = "X-Forwarded-Proto", defaultValue = "http") String proto,
        @PathVariable(name = "nonce", required = false) final String nonce
    ) {
        return ping(originalHost, proto, nonce != null ? nonce : "pong");
    }
}
