package org.jfbarahonag.model;

public class Employee {
    private Integer id;
    private String first_name;
    private String last_name;
    private String document;

    public Employee() {
    }

    public Employee(String first_name, String last_name, String document) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.document = document;
    }

    public Employee(Integer id, String first_name, String last_name, String document) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.document = document;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", document='" + document + '\'' +
                '}';
    }
}
