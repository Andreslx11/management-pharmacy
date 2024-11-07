package com.example.managementpharmacy.persistence.enums.sortfield;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

// Lombok annotations
@Getter
@AllArgsConstructor
public enum ProductSortField {

    ID("id", "id_product"),
    TRADE_NAME("tradeName", "trade_name"),
    SALE_PRICE("salePrice", "sale_price"),
    EXPIRATION_DATE("expirationDate", "expiration_date"),
    CREATION_DATE("creationDate", "creation_date"),
    STATE("state", "state");


    private final String fieldName;
    private final String columnName;

    public static  String getSqlColumn(String value){
        return Arrays.stream(ProductSortField.values())
                .filter(sortField -> sortField.getFieldName().equals(value))
                .findFirst()
                .map(ProductSortField::getColumnName)
                .orElseGet(ID::getColumnName);

    }

}
