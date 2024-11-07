package com.example.managementpharmacy.shared.page;

import lombok.*;

import java.util.List;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> {

    private List<T> content;
    private Integer number;
    private Integer numberOfElementos;
    private Integer size;
    private Long totalElementos;
    private Integer totalPages;
















}
