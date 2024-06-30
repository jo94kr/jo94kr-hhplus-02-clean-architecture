package io.hhplus.clean_architecture.common.handler;

public record ErrorResponse(
        String code,
        String message
) {
}
