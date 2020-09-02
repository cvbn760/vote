package ru.vote.topjava.model;

import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@SelectBeforeUpdate
@Table(name = "voters", uniqueConstraints = {@UniqueConstraint(columnNames = {"menu_id", "voter_id_pk", "date"}, name = "voters_idx")})
public class Voter {

    @Id
    @SequenceGenerator(name = "voters_seq", sequenceName = "voters_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "voters_seq")
    @Column(name = "v_id")
    private Integer vId;

    @Column(name = "menu_id")
    private Integer menuId;

    @Column(name = "voter_id_pk", nullable = false)
    private Integer voterIdPk;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "voice", nullable = false)
    private boolean voice;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "menu_id", referencedColumnName = "id_menu", insertable = false, updatable = false)
    private Menu menu;

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Voter(){}

    public Voter(Integer vId, Integer menuId, Integer voterIdPk, LocalDate date, boolean voice, Menu menu) {
        this.vId = vId;
        this.menuId = menuId;
        this.voterIdPk = voterIdPk;
        this.date = date;
        this.voice = voice;
        this.menu = menu;
    }

    public Voter(Integer menuId, Integer voterIdPk, LocalDate date, boolean voice, Menu menu) {
        this.voterIdPk = voterIdPk;
        this.date = date;
        this.voice = voice;
        this.menuId = menuId;
        this.menu = menu;
    }

    public Voter(Integer menuId, Integer voterIdPk, LocalDate date, boolean voice) {
        this.voterIdPk = voterIdPk;
        this.date = date;
        this.voice = voice;
        this.menuId = menuId;
    }

    public Voter(LocalDate date, boolean voice, int menuId, Menu menu) {
        this.date = date;
        this.voice = voice;
        this.menuId = menuId;
        this.menu = menu;
    }

    public void setvId(Integer vId) {
        this.vId = vId;
    }

    public Integer getVId() {
        return vId;
    }

    public Integer getVoterIdPk() {
        return voterIdPk;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setVoterIdPk(Integer voterIdPk) {
        this.voterIdPk = voterIdPk;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public boolean isVoice() {
        return voice;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setVoice(boolean voice) {
        this.voice = voice;
    }

    @Override
    public String toString() {
        return "Voter{" +
                "vId=" + vId +
                ", menuId=" + menuId +
                ", voterIdPk=" + voterIdPk +
                ", date=" + date +
                ", voice=" + voice +
                '}';
    }
}
