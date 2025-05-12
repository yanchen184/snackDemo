package com.yc.snackoverflow.mapper;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Context;
import org.mapstruct.Named;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Utility mapper for list and collection operations
 */
public interface ListMapper {

    /**
     * Split a string into a list using the specified delimiter
     * 
     * @param str The string to split
     * @param delimiter The delimiter to use
     * @return List of strings
     */
    @Named("splitToList")
    default List<String> splitToList(String str, @Context String delimiter) {
        return Optional.ofNullable(str)
                .filter(StringUtils::isNotEmpty)
                .map(s -> s.split(delimiter))
                .map(Arrays::asList)
                .orElseGet(Collections::emptyList);
    }
    
    /**
     * Join a list of strings into a single string using the specified delimiter
     * 
     * @param list The list to join
     * @param delimiter The delimiter to use
     * @return Joined string
     */
    @Named("joinList")
    default String joinList(List<String> list, @Context String delimiter) {
        return Optional.ofNullable(list)
                .filter(l -> !l.isEmpty())
                .map(l -> String.join(delimiter, l))
                .orElse("");
    }
    
    /**
     * Convert a collection of objects to a list of strings using their toString method
     * 
     * @param collection The collection to convert
     * @return List of strings
     */
    @Named("collectionToStringList")
    default <T> List<String> collectionToStringList(Collection<T> collection) {
        return Optional.ofNullable(collection)
                .map(c -> c.stream().map(Object::toString).collect(Collectors.toList()))
                .orElseGet(Collections::emptyList);
    }
    
    /**
     * Filter null values from a list
     * 
     * @param list The list to filter
     * @return Filtered list
     */
    @Named("filterNulls")
    default <T> List<T> filterNulls(List<T> list) {
        return Optional.ofNullable(list)
                .map(l -> l.stream().filter(item -> item != null).collect(Collectors.toList()))
                .orElseGet(Collections::emptyList);
    }
}
