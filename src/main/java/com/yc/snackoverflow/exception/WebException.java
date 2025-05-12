package com.yc.snackoverflow.exception;

import lombok.Getter;

/**
 * @author meow
 */
@Getter
public class WebException extends RuntimeException {

    private final WebError webError;

    private final Object[] args;

    private final String webErrorMsg;
    
    private int errorCode;

    public WebException(final WebError webError) {
        this.webError = webError;
        this.args = null;
        this.webErrorMsg = webError.getErrorLog();
        this.errorCode = -1; // Default value indicating webError should be used
    }

    public WebException(final WebError webError, final Object... args) {
        super(webError.getErrorCode());
        this.webError = webError;
        this.args = args;
        this.webErrorMsg = webError.getFormatAsString(args);
        this.errorCode = -1; // Default value indicating webError should be used
    }
    
    public WebException(final int errorCode, final String message) {
        super(message);
        this.errorCode = errorCode;
        this.webError = null;
        this.args = null;
        this.webErrorMsg = message;
    }
    
    /**
     * Get the error code
     * @return int error code
     */
    public int getCode() {
        if (errorCode != -1) {
            return errorCode;
        } else if (webError != null) {
            // Parse the error code string to an integer or return a default value
            try {
                // Remove 'ERR' prefix and convert to int
                String codeStr = webError.getErrorCode().substring(3);
                return Integer.parseInt(codeStr);
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                // Return a default error code if parsing fails
                return 10000; // Default system error code
            }
        } else {
            return 10000; // Default system error code
        }
    }
}