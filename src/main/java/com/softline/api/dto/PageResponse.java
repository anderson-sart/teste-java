package com.softline.api.dto;

import java.util.List;

public record PageResponse<T>(
        List<T> data,
        long total,
        int page,
        int perPage,
        int lastPage
) {}
