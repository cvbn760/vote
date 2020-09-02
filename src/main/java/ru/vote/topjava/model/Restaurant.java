package ru.vote.topjava.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "restaurants")
public class Restaurant {
    @Id
    @SequenceGenerator(name = "restaurants_seq", sequenceName = "restaurants_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurants_seq")
    @Column(name = "id_rest")
    private Integer idRest;

    @Column(name = "id_owner_rest", nullable = false)
    private Integer idOwnerRest;

    @Column(name = "name_rest", nullable = false)
    private String nameRest;

    @Column(name = "address", nullable = false)
    private String address;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_rest", referencedColumnName = "id_rest", insertable = false, updatable = false)
    @OrderBy("date DESC")
    private Set<Menu> menus;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_owner_rest", referencedColumnName = "id", insertable = false, updatable = false)
    private User adminRest;

    public User getAdminRest() {
        return adminRest;
    }

    public void setAdminRest(User adminRest) {
        this.adminRest = adminRest;
    }

    public Set<Menu> getMenus() {
        return menus;
    }

    public void setMenus(Set<Menu> menus) {
        this.menus = menus;
    }

    public Integer getIdRest() {
        return idRest;
    }

    public Integer getIdOwnerRest() {
        return idOwnerRest;
    }

    public String getNameRest() {
        return nameRest;
    }

    public String getAddress() {
        return address;
    }

    public void setNameRest(String name_rest) {
        this.nameRest = name_rest;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Restaurant(){}

    public Restaurant(Integer idOwnerRest, String nameRest, String address) {
        this.nameRest = nameRest;
        this.address = address;
        this.menus = new HashSet<>();
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "idRest=" + idRest +
                ", idOwnerRest=" + idOwnerRest +
                ", nameRest='" + nameRest + '\'' +
                ", address='" + address + '\'' +
                ", menus size=" + menus.size() +
                '}';
    }
}
