package com.example.managementpharmacy.persistence.enums.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public enum RolePharmacy {
    ADMIN_SOFTWARE,
    ADMINISTRATOR,
    HUMAN_RESOURCES,
    INTERVENTION,
    INSPECTOR,
    DIAN,
    SUPERVISOR_LEVEL_1,
    SUPERVISOR_LEVEL_2,
    SUPERVISOR_LEVEL_3,
    HEAD_PHARMACIST,
    CUSTOMER_SERVICE,
    WAREHOUSE_AIDE,
    SALES_AGENT,
    ACCOUNTANT,
    GUEST_LEVEL_1,
    GUEST_LEVEL_2,
    GUEST_LEVEL_3,
    INACTIVE;

}


