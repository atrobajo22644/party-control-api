package com.leveltwo.partycontrol.utils;

import org.springframework.data.domain.Sort;

public class SortUtils {
    public static Sort.Direction getSortDirection(String direction) {
        return direction.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
    }
}
