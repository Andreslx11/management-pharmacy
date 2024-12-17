package com.example.managementpharmacy.persistence.enums.sortfield;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

// Lombok annotation
@Getter
@AllArgsConstructor
public enum EmployeeSortField {

    ID("id", "id_employee"),
    FIRST_NAME("firstName", "first_name"),
    LAST_NAME("lastname", "last_name"),
    DOCUMENT_NUMBER("documentNumber", "document_number"),
    ROLE("role", "role"),
    EMAIL("email", "email"),
    CONTRACT_START_DATE("contractStartDate", "contract_start_date"),
    CONTRACT_END_DATE("contractEndDate", "contract_end_date"),
    STATE("state", "state");


    private final String fieldName;
    private final String columnName;

    public static String getSqlColumn(String value) {
        return Arrays.stream(EmployeeSortField.values())
                .filter(sortField -> sortField.getFieldName().equals(value))
                .findFirst()
                .map(EmployeeSortField::getColumnName)
                .orElseGet(ID::getColumnName);
    }


}