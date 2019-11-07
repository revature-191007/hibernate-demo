package com.revature.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 * JPA annotations - Annotations which define the entity relationship between
 * the Java class definition and the database table. Much of it can be inferred,
 * but we can optionally set other values and define relationships.
 */

@Entity // Defines that this class can be managed by an ORM
//@Table  OPTIONAL: configuration details for the table 
public class Pokemon {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column // OPTIONAL: Configuration details for the column
	private int id;

	@Column(nullable = false, length = 20)
	private String name;

	private double weight;
	private double height;

	@Column(columnDefinition = "TEXT")
	private String description;

	@Column(name = "ratio_male")
	private double ratioMale = 0.5;

	@ManyToOne
	@JoinColumn(name = "type_id")
	private Type primaryType;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "location_pokemon", joinColumns = { @JoinColumn(name = "pokemon_id") }, inverseJoinColumns = {
			@JoinColumn(name = "location_id") })
	private List<Location> appearingLocations;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getRatioMale() {
		return ratioMale;
	}

	public void setRatioMale(double ratioMale) {
		this.ratioMale = ratioMale;
	}

	public Type getPrimaryType() {
		return primaryType;
	}

	public void setPrimaryType(Type primaryType) {
		this.primaryType = primaryType;
	}

	public List<Location> getAppearingLocations() {
		return appearingLocations;
	}

	public void setAppearingLocations(List<Location> appearingLocations) {
		this.appearingLocations = appearingLocations;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((appearingLocations == null) ? 0 : appearingLocations.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		long temp;
		temp = Double.doubleToLongBits(height);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((primaryType == null) ? 0 : primaryType.hashCode());
		temp = Double.doubleToLongBits(ratioMale);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(weight);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pokemon other = (Pokemon) obj;
		if (appearingLocations == null) {
			if (other.appearingLocations != null)
				return false;
		} else if (!appearingLocations.equals(other.appearingLocations))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (Double.doubleToLongBits(height) != Double.doubleToLongBits(other.height))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (primaryType == null) {
			if (other.primaryType != null)
				return false;
		} else if (!primaryType.equals(other.primaryType))
			return false;
		if (Double.doubleToLongBits(ratioMale) != Double.doubleToLongBits(other.ratioMale))
			return false;
		if (Double.doubleToLongBits(weight) != Double.doubleToLongBits(other.weight))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Pokemon [id=" + id + ", name=" + name + ", weight=" + weight + ", height=" + height + ", description="
				+ description + ", ratioMale=" + ratioMale + ", primaryType=" + primaryType + ", appearingLocations="
				+ appearingLocations + "]";
	}

	public Pokemon(int id, String name, double weight, double height, String description, double ratioMale,
			Type primaryType, List<Location> appearingLocations) {
		super();
		this.id = id;
		this.name = name;
		this.weight = weight;
		this.height = height;
		this.description = description;
		this.ratioMale = ratioMale;
		this.primaryType = primaryType;
		this.appearingLocations = appearingLocations;
	}

	public Pokemon() {
		super();
		// TODO Auto-generated constructor stub
	}

}
