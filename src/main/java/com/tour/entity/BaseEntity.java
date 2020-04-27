package com.tour.entity;

import java.time.LocalDate;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity {

	private LocalDate createDate;

	private LocalDate modifiedDate;

	private LocalDate deleteDate;

	public LocalDate getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}

	public LocalDate getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(LocalDate modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public LocalDate getDeleteDate() {
		return deleteDate;
	}

	public void setDeleteDate(LocalDate deleteDate) {
		this.deleteDate = deleteDate;
	}

}
