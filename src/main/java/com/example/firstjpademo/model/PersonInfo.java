package com.example.firstjpademo.model;

public class PersonInfo {
    private Long id;
    private String name;

    public PersonInfo(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "PersonInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
