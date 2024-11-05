package com.ofg.hairdresser.model.request;

public record ReviewUpdateRequest(
        int rating,
        String comment
) {

}
