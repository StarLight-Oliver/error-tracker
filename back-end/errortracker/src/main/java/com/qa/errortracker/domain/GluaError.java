package com.qa.errortracker.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class GluaError {
	@Id
	private String hash;
	
	private String shortErr;
	private String stack;
	private String name;
	
	private Integer count = 1;
	
	private Short state;
	
	@ManyToMany(targetEntity = Developer.class, cascade = {CascadeType.PERSIST, CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH} )
	private Set<Developer> developers = new HashSet<>();
	public GluaError() {
		// Empty because its spring
		
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getShortErr() {
		return shortErr;
	}

	public void setShortErr(String shortErr) {
		this.shortErr = shortErr;
	}

	public String getStack() {
		return stack;
	}

	public void setStack(String stack) {
		this.stack = stack;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Set<Developer> getDevelopers() {
		return developers;
	}

	public void setDevelopers(Set<Developer> developers) {
		this.developers = developers;
	}

	public Short getState() {
		return state;
	}

	public void setState(Short state) {
		this.state = state;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		var prime = 31;
		var result = 1;
		result = prime * result + ((count == null) ? 0 : count.hashCode());
		result = prime * result + ((developers == null) ? 0 : developers.hashCode());
		result = prime * result + ((hash == null) ? 0 : hash.hashCode());
		result = prime * result + ((shortErr == null) ? 0 : shortErr.hashCode());
		result = prime * result + ((stack == null) ? 0 : stack.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
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
		GluaError other = (GluaError) obj;
		if (count == null) {
			if (other.count != null)
				return false;
		} else if (!count.equals(other.count))
			return false;
		if (developers == null) {
			if (other.developers != null)
				return false;
		} else if (!developers.equals(other.developers))
			return false;
		if (hash == null) {
			if (other.hash != null)
				return false;
		} else if (!hash.equals(other.hash))
			return false;
		if (shortErr == null) {
			if (other.shortErr != null)
				return false;
		} else if (!shortErr.equals(other.shortErr))
			return false;
		if (stack == null) {
			if (other.stack != null)
				return false;
		} else if (!stack.equals(other.stack))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		return true;
	}
}
