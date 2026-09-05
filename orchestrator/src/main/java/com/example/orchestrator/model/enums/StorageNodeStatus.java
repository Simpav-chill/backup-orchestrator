package com.example.orchestrator.model.enums;

import lombok.Getter;

@Getter
public enum StorageNodeStatus {
    ACTIVE("ACTIVE"),
    UNAVAILABLE("UNAVAILABLE");

    private final String value;

    StorageNodeStatus(final String value) {
        this.value = value;
    }
}
