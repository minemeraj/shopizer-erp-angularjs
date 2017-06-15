package com.shopizer.business.entity.order;

import java.math.BigDecimal;

import com.shopizer.business.entity.common.EntityId;

public class OrderTotal extends EntityId {
	
	private OrderTotalVariationEnum variation;
	
	private OrderTotalTypeEnum type;
	
	private String name;
	
	private BigDecimal value;
	
	private int order;

	public OrderTotalVariationEnum getVariation() {
		return variation;
	}

	public void setVariation(OrderTotalVariationEnum variation) {
		this.variation = variation;
	}

	public OrderTotalTypeEnum getType() {
		return type;
	}

	public void setType(OrderTotalTypeEnum type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

}
