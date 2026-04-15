package com.qma_microservices.api_gateway.config;

/**
 * Gateway routes are configured declaratively in application.yml under:
 *   spring.cloud.gateway.routes
 *
 * Routes defined:
 *   - auth-service  → /api/v1/auth/**       (public, no JWT filter)
 *   - quantity-service → /api/v1/quantities/** (protected, JwtAuthenticationFilter applied)
 *
 * To add new routes, update application.yml instead of using Java DSL here.
 */
public class GatewayConfig {
    // Routes managed via application.yml — no Java DSL needed.
}