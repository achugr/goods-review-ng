package ru.goodsreview.api.provider;

/**
 * achugr, achugr@yandex-team.ru
 * 13.07.12
 */
public enum HttpStatus {

    OK("ok", 200),
    BAD_REQUEST("Bad request", 400),
    AUTHORIZATION_FAILED("Authorization failed", 401),
    RATE_LIMIT_EXCEEDED("Rate limit exceeded", 403),
    NOT_FOUND("not found", 404),
    METHOD_NOT_SUPPORTED("not supported", 405),
    REQUIRED_PARAMETER_IS_MISSING("is missing", 422),
    INCORRECT_PARAMETER_FORMAT("format is incorrect", 422),
    PARAMETER_VALUE_IS_NOT_SUPPORTED("is not supported", 422);


    private final String error;
    private final int code;

    HttpStatus(String error, int code) {
        this.error = error;
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}
