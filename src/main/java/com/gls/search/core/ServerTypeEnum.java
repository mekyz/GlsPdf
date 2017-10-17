package com.gls.search.core;
/**
 * 
 * 文  件  名:ReflectUtil<br/>  
 * 文件描述:<br/>  
 * 修  改  人: 詹昌高 <br/>
 * 修改日期:2016年12月6日<br/>
 * 修改内容:<br/>
 */
public enum ServerTypeEnum {
	
    SUPPLIER((byte) 0), GOODS((byte) 1), WM_PEODUCT((byte) 2)
	,BUSINIESS((byte) 3),FRANK_ORDER((byte) 4);

	private byte value;

	ServerTypeEnum(byte value) {
		this.value = value;
	}

	public byte getValue() {
		return value;
	}

	public static ServerTypeEnum parse(byte b) {
		if (b == SUPPLIER.getValue()) {
			return SUPPLIER;
		} else if (b == GOODS.getValue()) {
			return GOODS;
		} else if (b == WM_PEODUCT.getValue()) {
			return WM_PEODUCT;
		} else if(b == BUSINIESS.getValue()){
		    return BUSINIESS;
		} else if(b == FRANK_ORDER.getValue()){
            return FRANK_ORDER;
        }
		return ServerTypeEnum.SUPPLIER;
	}
}
