package com.example.managementpharmacy.shared.page;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.function.Function;

public  abstract  class PagingAndSortingBuilder {

    /*
    T dato de ingreso
    U dato de salida
    converter va ser el mapper que usemos
     */

    public <T, U>  PageResponse<U> buildPageResponse(Page<T> page, Function<T, U> converter){

        List<U> content = page.getContent()
                .stream()
                .map( converter)
                .toList();

    return PageResponse.<U> builder()
            .content(content)
            .number(page.getNumber())
            .numberOfElementos(page.getNumberOfElements())
            .size(page.getSize())
            .totalElementos(page.getTotalElements())
            .totalPages(page.getTotalPages())
            .build();

    }


    public Pageable  buildPageable(PageableRequest request,  String sqlColumn){

        Sort.Direction direction = Sort.Direction
                .fromOptionalString(request.getSortOrder())
                .orElse(Sort.Direction.DESC);

        Sort sort = Sort.by(direction, sqlColumn);

        return PageRequest.of(
                request.getPage() - 1,
                request.getSize(),
                sort
                 );
    }

}
