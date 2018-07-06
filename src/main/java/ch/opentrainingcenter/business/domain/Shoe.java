package ch.opentrainingcenter.business.domain;

import javax.persistence.*;
import java.util.Date;

@NamedQueries({ //
        @NamedQuery(name = "Shoe.getShoesByAthlete", query = "SELECT s FROM SHOES s where s.athlete=:athlete"),
        @NamedQuery(name = "Shoe.getKilometers", query = "SELECT SUM(t.laengeInMeter) FROM TRAINING t where t.shoe=:shoe")})

@Entity(name = "SHOES")
public class Shoe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false)
    private String schuhname;
    private String imageicon;
    private int preis;

    @Temporal(TemporalType.TIMESTAMP)
    private Date kaufdatum;

    @ManyToOne
    @JoinColumn(name = "ID_FK_ATHLETE", nullable = false)
    private Athlete athlete;

    public Shoe() {
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getSchuhname() {
        return schuhname;
    }

    public void setSchuhname(final String schuhname) {
        this.schuhname = schuhname;
    }

    public String getImageicon() {
        return imageicon;
    }

    public void setImageicon(final String imageicon) {
        this.imageicon = imageicon;
    }

    public int getPreis() {
        return preis;
    }

    public void setPreis(final int preis) {
        this.preis = preis;
    }

    public Date getKaufdatum() {
        return kaufdatum;
    }

    public void setKaufdatum(final Date kaufdatum) {
        this.kaufdatum = kaufdatum;
    }

    public Athlete getAthlete() {
        return athlete;
    }

    public void setAthlete(final Athlete athlete) {
        this.athlete = athlete;
    }

    @Override
    public String toString() {
        return "Shoe [id=" + id + ", schuhname=" + schuhname + ", imageicon=" + imageicon + ", preis=" + preis
                + ", kaufdatum=" + kaufdatum + ", athlete=" + athlete + "]";
    }
}
