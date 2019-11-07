package com.revature.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Type {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable=false, length=8)
	private String name;
	
	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name="type_id")
	List<Pokemon> pokemonWithType;

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

	public List<Pokemon> getPokemonWithType() {
		return pokemonWithType;
	}

	public void setPokemonWithType(List<Pokemon> pokemonWithType) {
		this.pokemonWithType = pokemonWithType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((pokemonWithType == null) ? 0 : pokemonWithType.hashCode());
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
		Type other = (Type) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (pokemonWithType == null) {
			if (other.pokemonWithType != null)
				return false;
		} else if (!pokemonWithType.equals(other.pokemonWithType))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Type [id=" + id + ", name=" + name + ", pokemonWithType=" + pokemonWithType + "]";
	}

	public Type(int id, String name, List<Pokemon> pokemonWithType) {
		super();
		this.id = id;
		this.name = name;
		this.pokemonWithType = pokemonWithType;
	}

	public Type() {
		super();
		// TODO Auto-generated constructor stub
	}

}
