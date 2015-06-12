package com.example.konark.truecallerassignment.model.listeners;

/**
 * This is the callback listener
 */
public interface CallBackListener {
    /**
     * @param apiResponse
     */
        void onSuccess(String apiResponse);

    /**
     * if login is not successful
     * @param message from api
     */
        void onFailed(String message);
    }