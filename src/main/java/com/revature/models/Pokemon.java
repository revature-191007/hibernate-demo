package com.revature.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * JPA annotations - Annotations which define the entity relationship
 * between the Java class definition and the database table. Much of it
 * can be inferred, but we can optionally set other values and define
 * relationships.
 */

@Entity // Defines that this class can be managed by an ORM
//@Table  OPTIONAL: configuration details for the table 
public class Pokemon {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
//	@Column // OPTIONAL: Configuration details for the column
	private int id;
	
	@Column(nullable=false, length=20)
	private String name;
	
	private double weight;
	private double height;
	
	@Column(columnDefinition = "TEXT")
	private String description;

	@Column(name="ratio_male")
	private double ratioMale = 0.5;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		long temp;
		temp = Double.doubleToLongBits(height);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		if (Double.doubleToLongBits(ratioMale) != Double.doubleToLongBits(other.ratioMale))
			return false;
		if (Double.doubleToLongBits(weight) != Double.doubleToLongBits(other.weight))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Pokemon [id=" + id + ", name=" + name + ", weight=" + weight + ", height=" + height + ", description="
				+ description + ", ratioMale=" + ratioMale + "]";
	}

	public Pokemon(int id, String name, double weight, double height, String description, double ratioMale) {
		super();
		this.id = id;
		this.name = name;
		this.weight = weight;
		this.height = height;
		this.description = description;
		this.ratioMale = ratioMale;
	}

	public Pokemon() {
		super();
		// TODO Auto-generated constructor stub
	}
}
