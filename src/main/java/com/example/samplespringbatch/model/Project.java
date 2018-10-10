package com.example.samplespringbatch.model;

import java.util.Set;
import java.util.UUID;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import com.datastax.driver.core.DataType.Name;

@Table(value = "project")
public class Project {

	@PrimaryKeyColumn(name = "name", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
	private String name;

	@PrimaryKeyColumn(name = "type", ordinal = 2, type = PrimaryKeyType.PARTITIONED)
	private String type;

	@PrimaryKeyColumn(name = "status", ordinal = 3, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.ASCENDING)
	@CassandraType(type = Name.TEXT)
	private Status status;

	@PrimaryKeyColumn(name = "created", ordinal = 4, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.ASCENDING)
	private UUID created;

	@CassandraType(type = Name.SET, typeArguments = { Name.TEXT })
	private Set<Technology> technologies;

	public Project() {
		super();
	}

	public Project(String name, String type, Status status, UUID created, Set<Technology> technologies) {
		super();
		this.name = name;
		this.type = type;
		this.status = status;
		this.created = created;
		this.technologies = technologies;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Set<Technology> getTechnologies() {
		return technologies;
	}

	public void setTechnologies(Set<Technology> technologies) {
		this.technologies = technologies;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public UUID getCreated() {
		return created;
	}

	public void setCreated(UUID created) {
		this.created = created;
	}

	@Override
	public String toString() {
		return "Project [name=" + name + ", type=" + type + ", status=" + status + ", created=" + created
				+ ", technologies=" + technologies + "]";
	}

}
