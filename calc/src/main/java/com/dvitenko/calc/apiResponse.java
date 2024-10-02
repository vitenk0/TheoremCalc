package com.dvitenko.calc;

public class apiResponse<T> {
    public T data;
    public String error;

    public apiResponse(T data, String error) {
        this.data = data;
        this.error = error;
    }

}
