package com.nordclan.application.exception;

import com.nordclan.application.constant.Errors;

public class LevelCardNotFoundException extends RuntimeException {

    public LevelCardNotFoundException(Long id) {
        super(String.format(Errors.Level.NOT_FOUND, id));
    }
}
