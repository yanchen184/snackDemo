package com.yc.snackoverflow.handler;

import com.yc.snackoverflow.constant.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Standard API response format
 *
 * @param <T> Type of the response data
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultData<T> {
    private int code;
    private String message;
    private T data;
    private long timestamp;

    /**
     * Constructor with code, message and data
     *
     * @param code Error code
     * @param message Error message
     * @param data Response data
     */
    public ResultData(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * Create a success response
     *
     * @param <T> Type of the response data
     * @return Success response with no data
     */
    public static <T> ResultData<T> success() {
        return new ResultData<>(ErrorCode.SUCCESS, "Success", null);
    }

    /**
     * Create a success response with data
     *
     * @param data Response data
     * @param <T> Type of the response data
     * @return Success response with data
     */
    public static <T> ResultData<T> success(T data) {
        return new ResultData<>(ErrorCode.SUCCESS, "Success", data);
    }

    /**
     * Create a success response with message and data
     *
     * @param message Success message
     * @param data Response data
     * @param <T> Type of the response data
     * @return Success response with message and data
     */
    public static <T> ResultData<T> success(String message, T data) {
        return new ResultData<>(ErrorCode.SUCCESS, message, data);
    }

    /**
     * Create a failure response with error code and message
     *
     * @param code Error code
     * @param message Error message
     * @param <T> Type of the response data
     * @return Failure response with error code and message
     */
    public static <T> ResultData<T> fail(int code, String message) {
        return new ResultData<>(code, message, null);
    }

    /**
     * Create a failure response with error code, message and data
     *
     * @param code Error code
     * @param message Error message
     * @param data Error data
     * @param <T> Type of the response data
     * @return Failure response with error code, message and data
     */
    public static <T> ResultData<T> fail(int code, String message, T data) {
        return new ResultData<>(code, message, data);
    }
}
