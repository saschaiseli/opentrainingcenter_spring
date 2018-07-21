package ch.opentrainingcenter.business.domain;

import javax.persistence.*;

@Entity
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String beschreibung;

    @ManyToOne
    @JoinColumn(name = "ID_FK_ATHLETE", nullable = false)
    private Athlete athlete;

    @OneToOne
    @JoinColumn(name = "ID_FK_TRAINING", nullable = false)
    private Training referenzTrack;

    public Route() {
    }

    public Route(final String routenName, final String routenBeschreibung, final Training referenzTraining) {
        name = routenName;
        beschreibung = routenBeschreibung;
        athlete = referenzTraining.getAthlete();
        referenzTrack = referenzTraining;
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(final String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public void setAthlete(final Athlete athlete) {
        this.athlete = athlete;
    }

    public Athlete getAthlete() {
        return athlete;
    }

    public Training getReferenzTrack() {
        return referenzTrack;
    }

    public void setReferenzTrack(final Training referenzTrack) {
        this.referenzTrack = referenzTrack;
    }

    @Override
    public String toString() {
        return "Route [name=" + name + ", beschreibung=" + beschreibung + ", athlete=" + athlete + "]";
    }

}
