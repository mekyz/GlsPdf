/**
 * Copyright 2016 ECCloud Corporation. All rights reserved.
 *
 * --------------------------------------------------------
 * 此技术信息为本公司机密信息，未经本公司书面同意禁止向第三方披露.
 * 版权所有：深圳市慧众云商科技有限公司
 * --------------------------------------------------------
 */

package com.gls.search.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * 文  件  名:SolrField<br/>  
 * 文件描述:<br/>  
 * 修  改  人: 詹昌高<br/>
 * 修改日期:2016年12月6日<br/>
 * 修改内容:<br/>
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SolrField {
    /**
     * 主键
     * 功能描述：<br/>
     * 创建人: 詹昌高<br/>
     * 创建日期:2016年12月6日<br/>
     * @return
     */
    public SolrEnum key() default SolrEnum.NO_KEY;
    /**
     * solr设置字段
     * 功能描述：<br/>
     * 创建人: 詹昌高<br/>
     * 创建日期:2016年12月6日<br/>
     * @return
     */
    public SolrEnum field()  default SolrEnum.NO_FIELD;
     
}
