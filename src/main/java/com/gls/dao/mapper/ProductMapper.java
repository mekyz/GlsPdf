/**
 * Copyright 2016 ECCloud Corporation. All rights reserved.
 *
 * --------------------------------------------------------
 * 此技术信息为本公司机密信息，未经本公司书面同意禁止向第三方披露.
 * 版权所有：贝恩国际
 * --------------------------------------------------------
 */

package com.gls.dao.mapper;

import java.util.List;
import java.util.Map;



import org.apache.ibatis.annotations.Param;

import com.gls.pojo.BusinessProportion;
import com.gls.pojo.CustomPaimingTop10;
import com.gls.pojo.District;
import com.gls.pojo.IndustrySupplierTop10;
import com.gls.pojo.MapClickInfo;
import com.gls.pojo.MapInfo;
import com.gls.pojo.ProductParam;
import com.gls.pojo.ProductSupplierTrend;
import com.gls.pojo.RelationGraph;
import com.gls.pojo.SupplierDistri;
import com.gls.pojo.TransactionDetailed;
import com.gls.pojo.Trend;


/**
 * 
 * 文  件  名:ProductMapper<br/>  
 * 文件描述:产品有关接口<br/>  
 * 修  改  人: 陈取名 <br/>
 * 修改日期:2016年12月24日<br/>
 * 修改内容:<br/>
 */
public interface ProductMapper {
     /**
      *  
      * 功能描述：全球出口/进口服装top10国家排名 柜量/重量<br/>
      * 创建人: 陈取名<br/>
      * 创建日期:2016年12月24日<br/>
      * @param productParam
      * @return
      */
    public List<CustomPaimingTop10> findProductCountryRanking(ProductParam productParam);
    
    /**
     * 
     * 功能描述：产品进口/出口国占比饼图 柜量/重量<br/>
     * URL ： <br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2016年12月26日<br/>
     * @param productParam
     * @return
     */
    public List<IndustrySupplierTop10> findProductProportion(ProductParam productParam);
    
    /**
     * 
     * 功能描述：出口/进口 产品top10 供应商/采购商 排名  柜量/重量<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2016年12月24日<br/>
     * @param productParam
     * @return
     */
    public List<Map<String , Object>> findSupplierTop10(ProductParam productParam);
    
    /**
     * 
     * 功能描述：产品供应商分布比重图<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2016年12月26日<br/>
     * @param productParam
     * @return
     */
    public List<SupplierDistri> findSupplierDistri(ProductParam productParam);
    
    /**
     * 
     * 功能描述： 供应商/采购商 总数<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2016年12月27日<br/>
     * @param productParam
     * @return
     */
    public Double findBusinessSum(ProductParam productParam);
    
    /**
     * 
     * 功能描述：跨境贸易 进口/出口  柜量/重量<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2016年12月27日<br/>
     * @param productParam
     * @return
     */
    public Double findProductSum(ProductParam productParam);
    
    /**
     * 
     * 功能描述：产品Top10供应商 进口/出口 趋势<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2016年12月26日<br/>
     * @param productParam
     * @return
     */
    public List<Trend> findProductSupplierTrend(ProductParam productParam);
    /**
     * 
     * 功能描述：辅助接口 产品Top10供应商 进口/出口 趋势(先获取供应商id)<br/>
     * URL ： <br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2017年1月12日<br/>
     * @param productParam
     * @return
     */
    public List<ProductSupplierTrend> findProductSupplierTrend2(ProductParam productParam);
    
    /**
     * 
     * 功能描述：产品 进口/出口 趋势<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2016年12月26日<br/>
     * @param productParam
     * @return
     */
    public List<Map<String , Object>> findProductTrend(ProductParam productParam);
    
    /**
     * 
     * 功能描述：进出口产品Top10 目的国/来源国排名<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2017年3月2日<br/>
     * @param productParam
     * @return
     */
    public List<CustomPaimingTop10> findProductTop10Destination(ProductParam productParam);
    
    /**
     * 
     * 功能描述：产品占比及世界排名<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2016年12月26日<br/>
     * @param productParam
     * @return
     */
    public List<Map<String , Object>> findProductProportionAndRanking(ProductParam productParam);
    
    /**
     * 
     * 功能描述：获取 进口/出口 总 柜量/重量 (辅助接口，帮助计算排名)<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2016年12月27日<br/>
     * @param productParam
     * @return
     */
    public List<Map<String , Object>> findProductCount(ProductParam productParam);
    
    /**
     * 
     * 功能描述：进口/出口 产品Top5省份排名 柜量/重量<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2016年12月26日<br/>
     * @param productParam
     * @return
     */
    public List<Map<String , Object>> findProductTop5(ProductParam productParam);
    
    /**
     * 
     * 功能描述：进口/出口 产品省份比重 柜量/重量<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2016年12月27日<br/>
     * @param productParam
     * @return
     */
    public List<Map<String , Object>> findProductProvince(ProductParam productParam);
    
    /**
     * 
     * 功能描述：Top5省份 进口/出口 产品趋势(获取id)<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2016年12月27日<br/>
     * @param productParam
     * @return
     */
    public List<ProductSupplierTrend> findTop5ProvinceTrend(ProductParam productParam);
    /**
     * 
     * 功能描述：辅助接口：Top5省份 进口/出口 产品趋势(获取相应的 柜重量和时间段)<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2016年12月27日<br/>
     * @param productParam
     * @return
     */
    public List<Trend> findTop5ProvinceTrend2(ProductParam productParam);
    
    /**
     * 
     * 功能描述：获取地区分类<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2016年12月28日<br/>
     * @return
     */
    public List<District> findDistrict1();
    /**
     * 
     * 功能描述：获取地区其他分类<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2016年12月28日<br/>
     * @param id
     * @return
     */
    public List<District> findDistrict2(Integer did);
    
    /**
     * 
     * 功能描述：地图信息<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2016年12月28日<br/>
     * @return
     */
    public List<MapInfo> findMapInfo(ProductParam productParam);
    
    /**
     * 
     * 功能描述：地图信息辅助接口获取相关联的国家<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2017年1月3日<br/>
     * @param productParam
     * @return
     */
    public List<MapInfo> findMapRelation(ProductParam productParam);
    
    /**
     * 
     * 功能描述：地图信息辅助接口，获取点击地图的具体国家信息<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2017年1月10日<br/>
     * @param did
     * @return
     */
    public MapClickInfo findMapClickInfo(Integer did);
    /**
     * 
     * 功能描述：采购商/供应商 信息<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2016年12月30日<br/>
     * @param productParam
     * @return
     */
    public List<Map<String, Object>> findBusinessInfo(ProductParam productParam);

    public int findBusinessInfoPid(ProductParam productParam);
    
    /**
     * 
     * 功能描述：采购产品比重<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2017年1月17日<br/>
     * @param productParam
     * @return
     */
    public List<BusinessProportion> findBusinessProportion1(ProductParam productParam);
    /**
     * 
     * 功能描述：辅助接口：采购产品比重（计算产品总量） <br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2017年1月17日<br/>
     * @param productParam
     * @return
     */
    public Double findBusinessProportion2(ProductParam productParam);
    
    /**
     * 
     * 功能描述：公司联系人列表<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2017年1月17日<br/>
     * @param productParam
     * @return
     */
    public List<Map<String, Object>> findContacts(ProductParam productParam);
    public ProductParam findContactsById(ProductParam productParam);//查询单条
    
    public int findContactsCount(ProductParam productParam);
   /**
    * 修改公司联系人列表的邮箱和手机号码
    * @param productParam
    * @return
    */
    public int updateContacts(ProductParam productParam);
    
    public int updateBusinessInfo(Map<String,Object> productParam);
    
    public int insertContact(Map<String,Object> productParam);
    
    /**
     * 
     * 功能描述：详情页 供应商/采购商趋势<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2017年1月18日<br/>
     * @return
     */
    public List<ProductSupplierTrend> findBusinessTrendInfo(ProductParam productParam);
    
    public ProductSupplierTrend findBusinessTrendName(ProductParam productParam);
    
    /**
     * 
     * 功能描述：（辅助接口）详情页 供应商/采购商趋势<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2017年1月18日<br/>
     * @return
     */
    public List<Trend> findBusinessTrendInfo2(ProductParam productParam);
    
    /**
     * 
     * 功能描述：合作伙伴关系图<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2017年1月19日<br/>
     * @param id
     * @return
     */
    public RelationGraph findRelationGraph(ProductParam productParam);
    /**
     * 
     * 功能描述：（辅助接口：一级）合作伙伴关系图<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2017年1月19日<br/>
     * @param id
     * @return
     */
    public List<RelationGraph> findRelationGraph1(ProductParam productParam);
    /**
     * 
     * 功能描述：（辅助接口：二级）合作伙伴关系图<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2017年1月19日<br/>
     * @param id
     * @return
     */
    public List<RelationGraph> findRelationGraph2(ProductParam productParam);
    /**
     * 
     * 功能描述：（辅助接口：三级）合作伙伴关系图<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2017年1月19日<br/>
     * @param id
     * @return
     */
    public List<RelationGraph> findRelationGraph3(ProductParam productParam);
    
    /**
     * 
     * 功能描述：近一年的交易历史<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2017年1月20日<br/>
     * @param productParam
     * @return
     */
    public List<Map<String, Object>> findRecentTransaction(ProductParam productParam);
    public Integer findRecentTransactionCount(ProductParam productParam);
    
    /**
     * 
     * 功能描述：与xxx供应商的交易情况<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2017年1月20日<br/>
     * @param productParam
     * @return
     */
    public List<Map<String, Object>> findTransactionInfo(ProductParam productParam);
    public Integer findTransactionInfoCount(ProductParam productParam);
    
    /**
     * 
     * 功能描述：竞争对手<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2017年1月22日<br/>
     * @param productParam
     * @return
     */
    public List<ProductSupplierTrend> findCompete(ProductParam productParam);
    
    /**
     * 
     * 功能描述：核心采购商/供应商地区分布图<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2017年1月23日<br/>
     * @param productParam
     * @return
     */
    public List<MapInfo> findbusinessDistribution(ProductParam productParam);
    public List<MapInfo> findbusinessDistributionByIds(ProductParam productParam);
    
    /**
     * 
     * 功能描述：交易清单<br/>
     * 创建人: 陈取名<br/>
     * 创建日期:2017年3月8日<br/>
     * @param productParam
     * @return
     */
    public List<TransactionDetailed> findTransactionDetailed(ProductParam productParam);
    
    public List<Map<String, Object>> findProByStr(String str);
}
