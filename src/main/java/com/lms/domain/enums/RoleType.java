package com.lms.domain.enums;


public enum RoleType {

    ROLE_CHAIRMAN("Chairman"),
    ROLE_DEAN("Dean"),
    ROLE_ADMIN("Administrator"),
    ROLE_TEACHER("Teacher"),
    ROLE_STUDENT("Student");
    private String name;

    private RoleType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
