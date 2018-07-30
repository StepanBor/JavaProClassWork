package ua.kiev.prog.one2many;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clients2")
public class Client {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private Integer age;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Address> addresses = new ArrayList<>();

    public Client() {}

    public Client(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public void addAddress(Address address) {
        if ( ! addresses.contains(address)) {
            addresses.add(address);
            address.setClient(this);
        }
    }

    public Address getAddress(int index) {
        return addresses.get(index);
    }

    public void clearAddresses() {
        addresses.clear();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }
}
