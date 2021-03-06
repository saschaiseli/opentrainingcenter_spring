package ch.opentrainingcenter.business.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Health {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "ID_FK_ATHLETE")
    private Athlete athlete;

    private Integer weight;
    private Integer cardio;

    @Temporal(TemporalType.DATE)
    private Date dateofmeasure;

    public Health() {
    }

    Health(final Athlete athlete, final Integer weight, final Integer cardio, final Date dateofmeasure) {
        this.athlete = athlete;
        this.weight = weight;
        this.cardio = cardio;
        this.dateofmeasure = dateofmeasure;
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public Athlete getAthlete() {
        return athlete;
    }

    public void setAthlete(final Athlete athlete) {
        this.athlete = athlete;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(final Integer weight) {
        this.weight = weight;
    }

    public Integer getCardio() {
        return cardio;
    }

    public void setCardio(final Integer cardio) {
        this.cardio = cardio;
    }

    public Date getDateofmeasure() {
        return dateofmeasure;
    }

    public void setDateofmeasure(final Date dateofmeasure) {
        this.dateofmeasure = dateofmeasure;
    }

    @Override
    public String toString() {
        return "Health [athlete=" + athlete + ", weight=" + weight + ", cardio=" + cardio + ", dateofmeasure="
                + dateofmeasure + "]";
    }

}
