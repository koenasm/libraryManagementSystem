package com.lms.model;

public enum BookStatus {
    AVAILABLE, RESERVED, BORROWED;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.name().substring(0, 1));
        builder.append(this.name().substring(1).toLowerCase());
        return builder.toString();

    }
}
