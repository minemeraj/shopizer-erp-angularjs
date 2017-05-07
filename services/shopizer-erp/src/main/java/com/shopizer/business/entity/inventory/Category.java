package com.shopizer.business.entity.inventory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.shopizer.business.entity.common.Description;
import com.shopizer.business.entity.common.Entity;

/**
 * https://docs.mongodb.com/manual/tutorial/model-tree-structures/
 * https://docs.mongodb.com/ecosystem/use-cases/category-hierarchy/
 * @author c.samson
 *
 */
@Document
public class Category extends Entity {
	
	
	private List<Description> descriptions;
	
	@DBRef(db="parent")
	private Category parent;
	


	//path of code id0/id1/id2/id3
	//determines the ancestors
	private String path;
	
	@DBRef(db="children")
	private List<Category> children = new ArrayList<Category>();
	

	
	
	public List<Description> getDescriptions() {
		return descriptions;
	}
	public void setDescriptions(List<Description> descriptions) {
		this.descriptions = descriptions;
	}
	public Category getParent() {
		return parent;
	}
	public void setParent(Category parent) {
		this.parent = parent;
	}

	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public List<Category> getChildren() {
		return children;
	}
	public void setChildren(List<Category> children) {
		this.children = children;
	}



}
