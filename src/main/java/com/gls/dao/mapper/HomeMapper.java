package com.gls.dao.mapper;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.gls.pojo.District;
import com.gls.pojo.FrankOrderBeen;
import com.gls.pojo.IndustryAreaEntity;
import com.gls.pojo.IndustryAreaTop10Entity;
import com.gls.pojo.IndustryProductEntity;
import com.gls.pojo.IndustryStaticTimeTop10;
import com.gls.pojo.IndustryStatis;
import com.gls.pojo.IndustrySupplierTop10;
import com.gls.pojo.IndustryTop10Entity;
import com.gls.pojo.ProductCategory;
import com.gls.pojo.ProductParam;
import com.gls.pojo.TenantsAmount;
import com.gls.pojo.TransactionDetailed;
import com.gls.search.entity.ProductEntity;
import com.gls.search.entity.SupplierEntity;



/**
 * 
 * 文  件  名:GoodsMapper<br/>  
 * 文件描述:<br/>  
 * 修  改  人: 詹昌高 <br/>
 * 修改日期:2016年12月24日<br/>
 * 修改内容:<br/>
 */
public interface HomeMapper {
    
    /**
     * 
     * 功能描述：TOP10进（出）口行业排名<br/>
     * 创建人: 詹昌高<br/>
     * 创建日期:2016年12月24日<br/>
     * @return
     */
    public List<IndustryTop10Entity> getIndustryTop10(ProductParam param);
    
    /**
     * 
     * 功能描述：TOP10进（出）口行业产品排名<br/>
     * 创建人: 詹昌高<br/>
     * 创建日期:2016年12月24日<br/>
     * @return
     */
    public List<IndustryProductEntity> getIndustryProductTop10(ProductParam param);
    
    /**
     * 
     * 功能描述：行业出口国占比饼图 <br/>
     * 创建人: 詹昌高<br/>
     * 创建日期:2016年12月26日<br/>
     * @param map
     * @return
     */
    public List<IndustryAreaTop10Entity> getIndustryAreaTop10(ProductParam param);
    
    /**
     * 
     * 功能描述：产品出口国占饼图 <br/>
     * 创建人: 詹昌高<br/>
     * 创建日期:2016年12月26日<br/>
     * @param map
     * @return
     */
    public List<IndustryAreaTop10Entity> getIndustryProductAreaTop10(ProductParam param);
    
    
    
    /**
     * 
     * 功能描述：行业下供应商/采购商前十排名 <br/>
     * 创建人: 詹昌高<br/>
     * 创建日期:2016年12月26日<br/>
     * @param map
     * @return
     */
    public List<IndustrySupplierTop10> getIndustrySupplierTop10(ProductParam param);
    
    /**
     * 
     * 功能描述：获取进出口采购商/供应商数量<br/>
     * 创建人: 詹昌高<br/>
     * 创建日期:2016年12月27日<br/>
     * @param map
     * @return
     */
    public TenantsAmount getTenantsAmount(ProductParam param);
    
    /**
     * 
     * 功能描述：获取进出口/进口柜量重量<br/>
     * 创建人: 詹昌高<br/>
     * 创建日期:2016年12月27日<br/>
     * @param map
     * @return
     */
    public IndustryTop10Entity getValueAmount(ProductParam param);
    
    /**
     * 
     * 功能描述：行业产品采购商/供应商地区分布<br/>
     * 创建人: 詹昌高<br/>
     * 创建日期:2016年12月27日<br/>
     * @param map
     * @return
     */
    public List<IndustryAreaEntity> getTurnoverDistributed(ProductParam param);
    
    
    
    /**
     * 
     * 功能描述：前十个行业进出口<br/>
     * 创建人: 詹昌高<br/>
     * 创建日期:2016年12月27日<br/>
     * @param map
     * @return
     */
    public List<IndustryStaticTimeTop10> getIndustryStaticTimeTop10(ProductParam param);
    
    
    /**
     * 
     * 功能描述：前十个行业进出口趋势<br/>
     * 创建人: 詹昌高<br/>
     * 创建日期:2016年12月27日<br/>
     * @param map
     * @return
     */
    public List<IndustryStatis> getIndustryTrendTop10(ProductParam param);
    
    /**
     * 
     * 功能描述：地区进出口趋势<br/>
     * 创建人: 詹昌高<br/>
     * 创建日期:2016年12月27日<br/>
     * @param map
     * @return
     */
    public List<IndustryAreaEntity> getAreaTurnoverTrend(ProductParam param);
    
    
    /**
     * 
     * 功能描述：获取所有类目<br/>
     * 创建人: 詹昌高<br/>
     * 创建日期:2016年12月27日<br/>
     * @param category
     * @return
     */
    public List<ProductCategory> getAllCategoryLevel1();
    /**
     * 
     * 功能描述：获取其他级别的类别<br/>
     * 创建人: 詹昌高<br/>
     * 创建日期:2016年12月27日<br/>
     * @param cid
     * @return
     */
    public List<ProductCategory> getAllCategoryLevel2(Integer cid);
    
    /**
     * 
     * 功能描述：通过产品id获取第一级别行业id<br/>
     * 创建人: 詹昌高<br/>
     * 创建日期:2016年12月29日<br/>
     * @param pid
     * @return
     */
    
    public Integer getCategoryLevelId1(ProductParam param);
    
    /**
     * 
     * 功能描述：获取所有商品  针对创建solr索引库<br/>
     * 创建人: 詹昌高<br/>
     * 创建日期:2016年12月30日<br/>
     * @return
     */
    public List<ProductEntity> getProList();

    
    /**
     * 
     * 功能描述：获取商家  针对创建solr索引库<br/>
     * 创建人: 詹昌高<br/>
     * 创建日期:2016年12月30日<br/>
     * @return
     */
    public List<SupplierEntity> getBuyerList(Map<String,Object> map);
    
    /**
     * 
     * 功能描述：获取数量<br/>
     * 创建人: 詹昌高<br/>
     * 创建日期:2017年3月3日<br/>
     * @param map
     * @return
     */
    public Integer getBuyerListCount(Map<String,Object> map);
    
    public District getDistrictById(int did);
    
    public List<TransactionDetailed> getComListByDid(ProductParam productParam);
    
    public List<TransactionDetailed> getComDetailListById(ProductParam productParam);
    
    public FrankOrderBeen getFrankOrBeen(BigInteger orderId);
    
}
