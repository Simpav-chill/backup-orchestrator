package com.example.orchestrator.model.enums;

import lombok.Getter;

@Getter
public enum FileVersionStatus {
    UPLOADING("UPLOADING"),
    AVAILABLE("AVAILABLE"),
    FAILED("FAILED"),;

    private final String value;

    FileVersionStatus(String value) {
        this.value = value;
    }
}
