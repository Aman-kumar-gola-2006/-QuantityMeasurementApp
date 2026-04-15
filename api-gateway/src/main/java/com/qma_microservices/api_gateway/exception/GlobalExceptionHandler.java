package com.qma_microservices.api_gateway.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
@Order(-1)
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {

        HttpStatus status;
        String message;

        if (ex instanceof ExpiredJwtException) {
            status = HttpStatus.UNAUTHORIZED;
            message = "Token has expired. Please login again.";
            log.warn("Expired JWT: {}", ex.getMessage());

        } else if (ex instanceof JwtException) {
            status = HttpStatus.UNAUTHORIZED;
            message = "Invalid token signature.";
            log.warn("Invalid JWT: {}", ex.getMessage());

        } else if (ex instanceof RuntimeException
                && ex.getMessage() != null
                && ex.getMessage().contains("Authorization header")) {
            status = HttpStatus.UNAUTHORIZED;
            message = ex.getMessage();
            log.warn("Authorization error: {}", ex.getMessage());

        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            message = ex.getMessage() != null ? ex.getMessage() : "Unexpected error occurred";
            log.error("Unhandled gateway error", ex);
        }

        exchange.getResponse().setStatusCode(status);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        String errorBody = String.format(
                "{\"status\": %d, \"error\": \"%s\", \"message\": \"%s\"}",
                status.value(),
                status.getReasonPhrase(),
                message.replace("\"", "'")
        );

        byte[] bytes = errorBody.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
        return exchange.getResponse().writeWith(Mono.just(buffer));
    }
}

