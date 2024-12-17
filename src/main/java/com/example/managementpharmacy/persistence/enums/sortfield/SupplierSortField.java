package com.example.managementpharmacy.persistence.enums.sortfield;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;


@Getter
@AllArgsConstructor
public enum SupplierSortField {

    ID("id", "id_supplier"),
    COMPANY_NAME("companyName", "company_name"),
    CONTACT("contact", "contact"),
    PHONE("phone", "phone"),
    STATE("state", "state");

    private final String fieldName;
    private final String columnName;


    public static String getSqlColumn(String value) {
        return Arrays.stream(SupplierSortField.values())
                .filter(sortFiel -> sortFiel.getFieldName().equals(value))
                .findFirst()
                .map(SupplierSortField::getColumnName)
                .orElseGet(ID::getColumnName);
    }


}
