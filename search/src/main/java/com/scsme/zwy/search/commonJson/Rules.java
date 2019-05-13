package com.scsme.zwy.search.commonJson;


import lombok.Data;

import java.io.Serializable;

@Data
public class Rules<T> implements Serializable {
	/**
	 * 查询的数据字段
	 */
	private String field;
	
	/**
	 * 查询符号(
	 * Like:模糊;(1参数)
	 * NotLike:;(1参数)
	 * GreaterThan:>  大于，(1参数)
	 * GreaterThanOrEqualTo:>= 不小于 (1参数)
	 * LessThan:<，(1参数)
	 * LessThanOrEqualTo:<=  不大于(1参数)
	 * NotEqualTo:不等于 (1参数)
	 * In:在数列范围中 (不定参数)
	 * NotIn:在数列范围中 (不定参数)
	 * Between:两者间 (2参数)
	 * NotBetween:不在两者中 (2参数)
	 * IsNotNull (0参数)
	 * IsNull  (0参数)）
	 */
	private String op;
	
	/**
	 * 查询条件数据
	 */
	private T data;


}
