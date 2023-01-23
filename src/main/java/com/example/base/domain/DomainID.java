package com.example.base.domain;

import java.io.Serializable;
import java.util.Objects;

public abstract class DomainID implements Serializable {

    protected final String id;

    protected DomainID(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "DomainID{" +
                "id='" + id + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DomainID domainID = (DomainID) o;
        return Objects.equals(id, domainID.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
