package com.gls.api.web;

import com.alibaba.fastjson.JSON;
import com.gls.api.service.ProductService;
import com.gls.api.service.SearchNewService;
import com.gls.api.service.SearchService;
import com.gls.api.service.UserService;
import com.gls.pojo.UserSearchLog;
import com.gls.pojo.request.QueryParam;
import com.gls.search.entity.FrankOrderEntity;
import com.gls.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;


/**
 * 
 * 文  件  名:SearchController<br/>  
 * 文件描述:通过该控制层获取solr库的商品数据<br/>  
 * 修  改  人: 詹昌高 <br/>
 * 修改日期:2016年12月6日<br/>
 * 修改内容:<br/>
 */
@Controller
public class SearchController {
	
	
	@Autowired
	private UserService userService;
	@Autowired
	private ProductService productService;
	@Autowired
	private SearchService searchService;
	



    /**
     * 通过查询条件，使用solr查询出所有的商品(通过关键词模糊查询)<br/>
     * 功能描述：<br/>
     * 创建人: 詹昌高<br/>
     * 创建日期:2016年12月6日<br/>
     * @param param 关键词search_keywords，pageNo页数，pageSize记录数
     * @return
     */
    @RequestMapping(value = "/getSolrGoodsList",produces = Constants.JSON)
    @ResponseBody
    public String getSolrGoodsList(String search_keywords,Integer pageNo,Integer pageSize){

        Map<String,Object> map = new HashMap<String, Object>();
        map.put("search_keywords", search_keywords);
        map.put("pageNo", pageNo);
        map.put("pageSize", pageSize);
        String result = searchService.getSolrGoodsList(map);

        return result;

    }
    /**
     *
     * 功能描述：根据搜索条件查找相应的提单<br/>
     * 创建人: willian<br/>
     * 创建日期:2017年6月23日<br/>
     * @param country 作为采购商的国家
     * @param product_desc 产品描述
     * @param start_data
     * @param end_data
     * @param buyer
     * @param supplier
     * @param original_country
     * @param mudi_port
     * @return
     */
    @RequestMapping(value = "/getFrankOrderList",produces = Constants.JSON,method = RequestMethod.POST)
    @ResponseBody
    public String getFrankOrderList(@RequestBody QueryParam param) throws ParseException {
        //是否需要记录
        return searchService.getFrankOrderList(param);
    }
	


    /**
     * @param type
     * @param pageNo
     * @param pageSize
     * @param search_keywords
     * @param search_pro
     * @param sort
     * @param token
     * @param userId
     * @return
     */
    @RequestMapping(value = "/getBuyerList",produces = Constants.JSON,method = RequestMethod.GET)
    @ResponseBody
    public String getBuyerList(@RequestParam("type") int type,@RequestParam("pageNo") int pageNo,
                               @RequestParam("pageSize") int pageSize,@RequestParam("search_keywords") String search_keywords,
                               @RequestParam("search_pro") String search_pro,@RequestParam("sort") int sort,@RequestParam("token") String token,
                               @RequestParam("userId") Integer userId){
                Map<String,Object> map = new HashMap<>();
                map.put("search_keywords", search_keywords);//供应商或者采购商关键词
                map.put("search_pro", search_pro);//产品名称关键词
                map.put("pageNo", pageNo);
                map.put("pageSize", pageSize);
                map.put("type", type); //type=0 供应商，type=1 采购商
                map.put("sort", sort);
                if(search_keywords!=null   &&   !search_keywords.equals("")){
                     UserSearchLog userSearchLog_word=new UserSearchLog(userId,search_keywords,1);//搜索company
                     userService.insertUserSearchLog(userSearchLog_word);
                }
                if(search_pro!=null   &&   !search_pro.equals("")){
                     UserSearchLog userSearchLog_pro=new UserSearchLog(userId,search_pro,0);//搜索产品
                     userService.insertUserSearchLog(userSearchLog_pro);
                }
                if(sort==0){
                	return SearchNewService.queryCompanyByProKey2(JSON.toJSONString(map));
                }else{
                	return SearchNewService.queryCompanyByProKey(JSON.toJSONString(map));
                }

    }
	
	
/*功能描述：根据对应条件获取提单列表<br/>
  	创建人: 詹昌高<br/>
  创建日期:2016年12月30日<br/>
 @param pageNo
 @param pageSize
 @return*/
     
    @RequestMapping(value = "/getSolrFranklyById",produces = Constants.JSON)
    @ResponseBody
    public String getSolrFranklyById(Integer orderId,long userId,String token){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("orderId", orderId);
        String result = searchService.getSolrFranklyById(JSON.toJSONString(map));

        return result;
    }
	/**
	 *
	 * 功能描述：根据地区查询商家<br/>
	 * 创建人: 詹昌高<br/>
	 * 创建日期:2017年3月4日<br/>
	 * @param name
	 * @param pageNo
	 * @param pageSize
	 * @param ietype
	 * @param did
	 * @return
	 */
    @RequestMapping(value = "/getBuyerListTest",produces = Constants.JSON)
    @ResponseBody
    public String getBuyerListTest(Integer pageNo,Integer pageSize,Integer ietype,Integer did,long userId,String token){

        Map<String,Object> map = new HashMap<String, Object>();
        map.put("ietype", ietype);//0=进口，1=出口
        if(pageNo!=null && pageSize !=null){
            map.put("pageNo", (pageNo-1)*pageSize);
            map.put("pageSize", pageSize);
        }else{
            map.put("pageNo", 0);
            map.put("pageSize", 10);
        }
        map.put("did", did);
        String str = searchService.getBuyerListTest(map);

        return str;
    }
	
    
}
