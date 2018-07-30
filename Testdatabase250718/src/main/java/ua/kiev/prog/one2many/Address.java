package ua.kiev.prog.one2many;

import javax.persistence.*;

@Entity
@Table(name = "address2")
public class Address {
    @Id
    @GeneratedValue
    private Long id;

    private String country;
    private String city;
    private String street;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    public Address() {}

    public Address(String country, String city, String street) {
        this.country = country;
        this.city = city;
        this.street = street;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
