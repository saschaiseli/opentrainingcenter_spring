package ch.opentrainingcenter.business.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Pattern;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@NamedQueries({ //
		@NamedQuery(name = "Athlete.findByEmail", query = "SELECT a FROM ATHLETE a where a.email=?1"),
		@NamedQuery(name = "Athlete.findByEmailAndPassword", query = "SELECT a FROM ATHLETE a where a.email=?1 and a.password=?2") })

@Entity(name = "ATHLETE")
public class Athlete implements Serializable, UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "ATHLETE_ID_SEQUENCE", sequenceName = "ATHLETE_ID_SEQUENCE")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ATHLETE_ID_SEQUENCE")
	private long id;

	@Column(nullable = false)
	private String firstName;
	@Column(nullable = false)
	private String lastName;
	@Pattern(regexp = ".+@.+", message = "{user.email.pattern}")
	@Column(unique = true, nullable = false)
	private String email;
	@Column(nullable = false)
	private String password;

	@Temporal(TemporalType.DATE)
	private Date birthday;

	@Column(nullable = true)
	private int maxheartrate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLogin;

	@OneToMany(mappedBy = "athlete", cascade = CascadeType.REMOVE)
	private Set<Route> routes = new HashSet<>();

	@OneToMany(mappedBy = "athlete", cascade = CascadeType.ALL)
	private Set<Health> healths = new HashSet<>();

	@OneToMany(mappedBy = "athlete", cascade = CascadeType.REMOVE)
	private Set<Training> trainings = new HashSet<>();

	@OneToMany(mappedBy = "athlete", cascade = CascadeType.REMOVE)
	private Set<Planungwoche> planungwoches = new HashSet<>();

	@OneToMany(mappedBy = "athlete", cascade = CascadeType.REMOVE)
	private Set<Shoe> shoes = new HashSet<>();

	public Athlete() {
	}

	public Athlete(final String firstName, final String lastName, final String email, final String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

	public long getId() {
		return id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(final Date birthday) {
		this.birthday = birthday;
	}

	public Integer getMaxheartrate() {
		return maxheartrate;
	}

	public void setMaxheartrate(final Integer maxheartrate) {
		this.maxheartrate = maxheartrate;
	}

	public Set<Route> getRoutes() {
		return routes;
	}

	public void setRoutes(final Set<Route> routes) {
		this.routes = routes;
	}

	public Set<Planungwoche> getPlanungwoches() {
		return planungwoches;
	}

	public void setPlanungwoches(final Set<Planungwoche> planungwoches) {
		this.planungwoches = planungwoches;
	}

	public void addTraining(final Training record) {
		trainings.add(record);
	}

	public int getMaxHeartRate() {
		return maxheartrate;
	}

	public void setMaxHeartRate(final int maxHeartRate) {
		maxheartrate = maxHeartRate;

	}

	public Set<Health> getHealths() {
		return healths;
	}

	public void setHealths(final Set<Health> healths) {
		this.healths = healths;
	}

	public Set<Training> getTrainings() {
		return trainings;
	}

	public void setTrainings(final Set<Training> trainings) {
		this.trainings = trainings;

	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		final String hashedPassowrd = passwordEncoder.encode(password.trim());
		this.password = hashedPassowrd;
	}

	public Set<Shoe> getShoes() {
		return shoes;
	}

	public void setShoes(final Set<Shoe> shoes) {
		this.shoes = shoes;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(final Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	@Override
	public String toString() {
		return "Athlete [id=" + id + ", name=" + firstName + ", birthday=" + birthday + ", maxheartrate=" + maxheartrate
				+ "]";
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		final List<GrantedAuthority> roles = new ArrayList<>();
		roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		return roles;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
