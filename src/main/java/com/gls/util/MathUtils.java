package com.gls.util;

import java.math.BigDecimal;

public class MathUtils {

	/**
	 * 
	 * residueTwoBehindPoint 小数点后剩2位
	 * @param total 总数
	 * @return BigDecimal
	 * @throw
	 */
	public static Double residueTwoBehindPoint(Object total){
		BigDecimal bdPrice = new BigDecimal(String.valueOf(total));
		return bdPrice.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * 
	 * multiplyTwoBigDecimal 两数相乘
	 * @param first 第一个数
	 * @param second 第二个数
	 * @return BigDecimal
	 * @throw
	 */
	public static Double multiplyTwoBigDecimal(Object first, Object second){
		/*BigDecimal bdFirst = new BigDecimal(String.valueOf(first));
		BigDecimal bdSecond = new BigDecimal(String.valueOf(second));
		bdFirst.multiply(bdSecond).setScale(2, BigDecimal.ROUND_HALF_UP);
		return bdFirst.doubleValue();*/
		
		BigDecimal bdFirst = new BigDecimal(String.valueOf(first));
		BigDecimal bdSecond = new BigDecimal(String.valueOf(second));
		BigDecimal bdResult;
		bdResult = bdFirst.multiply(bdSecond).setScale(2, BigDecimal.ROUND_HALF_UP);
		return bdResult.doubleValue();
	}
	
	/**
	 * 
	 * multiplyTwoBigDecimal 两数相加
	 * @param first 第一个数
	 * @param second 第二个数
	 * @return BigDecimal
	 * @throw
	 */
	public static Double addTwoBigDecimal(Object first, Object second){
		
		BigDecimal bdFirst = new BigDecimal(String.valueOf(first));
		BigDecimal bdSecond = new BigDecimal(String.valueOf(second));
		BigDecimal bdResult;
		bdResult = bdFirst.add(bdSecond).setScale(2, BigDecimal.ROUND_HALF_UP);
		return bdResult.doubleValue();

	}
	public static int addTwoBigDecimal2Int(Object first, Object second){
		
		BigDecimal bdFirst = new BigDecimal(String.valueOf(first));
		BigDecimal bdSecond = new BigDecimal(String.valueOf(second));
		BigDecimal bdResult;
		bdResult = bdFirst.add(bdSecond).setScale(2, BigDecimal.ROUND_HALF_UP);
		return bdResult.intValue();
		
	}
	
	/**
	 * 
	 * multiplyTwoBigDecimal 两数相减
	 * @param first 第一个数
	 * @param second 第二个数
	 * @return BigDecimal
	 * @throw
	 */
	public static double minusTwoBigDecimal(Object first, Object second){
		BigDecimal bdFirst = new BigDecimal(String.valueOf(first));
		BigDecimal bdSecond = new BigDecimal(String.valueOf(second));
		return bdFirst.subtract(bdSecond).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 
	 * multiplyTwoBigDecimal 两数相减
	 * @param first 第一个数
	 * @param second 第二个数
	 * @return BigDecimal
	 * @throw
	 */
	public static int minusTwoBigDecimal2Int(Object first, Object second){
		BigDecimal bdFirst = new BigDecimal(String.valueOf(first));
		BigDecimal bdSecond = new BigDecimal(String.valueOf(second));
		return bdFirst.subtract(bdSecond).setScale(2, BigDecimal.ROUND_HALF_UP).intValue();
	}
	
	
	public static void main(String[] args) {
		System.out.println(minusTwoBigDecimal(32, 20));
	}

	
}
