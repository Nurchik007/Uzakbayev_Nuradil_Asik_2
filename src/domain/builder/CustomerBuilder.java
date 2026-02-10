package domain.builder;

import domain.accounts.Customer;

public class CustomerBuilder {
    private String id;
    private String name;

    public CustomerBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public CustomerBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public Customer build() {
        return new Customer(id, name);
    }
}
