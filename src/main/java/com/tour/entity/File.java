/**
 * 
 */
package com.tour.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Ramanand
 *
 */
@Entity
@Table(name = "file")
public class File {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "file")
	private String path;

	/**
	 * @param id
	 * @param name
	 * @param path
	 */
	public File(Long id, String name, String path) {
		this.id = id;
		this.name = name;
		this.path = path;
	}

	/**
	 * 
	 */
	public File() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return "File [id=" + id + ", name=" + name + ", path=" + path + "]";
	}

}
