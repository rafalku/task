package com.ing.task;

import lombok.Getter;

@Getter
enum AtmRequestType {
    FAILURE_RESTART(0),
    PRIORITY(1),
    SIGNAL_LOW(2),
    STANDARD(3);

    private final int priority;

    AtmRequestType(int priority) {
        this.priority = priority;
    }
}

@Getter
public class AtmServiceOrder {
    private int region;
    private AtmRequestType requestType;
    private int atmId;
}
