package com.ppvilla.spring.rest;

public class Item {
	private Long id;
	private String description;
	private boolean checked;

	public Item() {
	}

	public Item(Long id, String description, boolean checked) {
		super();
		this.id = id;
		this.description = description;
		this.checked = checked;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", description=" + description + ", checked=" + checked + "]";
	}

}
