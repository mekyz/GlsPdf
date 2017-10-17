package com.gls.api.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gls.pojo.BusinessProportion;
import com.gls.pojo.ProductParam;
import com.gls.pojo.RelationGraph;
import com.gls.search.core.ServerTypeEnum;
import com.gls.search.core.SolrCloudServer;
import com.gls.search.entity.SupplierEntity;
import com.gls.search.util.Page;
import com.gls.search.util.ResponseListResult;
import com.gls.search.util.ResponseResult;
import com.gls.search.util.SolrUtils;
import com.gls.util.Constants;
import com.gls.util.Levenshtein;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.Group;
import org.apache.solr.client.solrj.response.GroupCommand;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SearchNewService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static String queryCompanyByProKey(String param) {
        DecimalFormat df = new DecimalFormat("######0.000");
        JSONObject object = JSONObject.parseObject(param);
        Page<SupplierEntity> page = new Page<>();
        page.setCurPage(object.getInteger("pageNo") == null ? 1 : object.getInteger("pageNo"));
        page.setPageSize(object.getInteger("pageSize") == null ? 20 : object.getInteger("pageSize") > 20 ? 20 : object.getInteger("pageSize"));
        DefaultHttpClient httpClient = new DefaultHttpClient();
//        SolrServer solrServer = new HttpSolrServer("http://120.77.64.98:8002/solr/frank");
        SolrServer solrServer = SolrCloudServer.getSolrServer(ServerTypeEnum.FRANK_ORDER);
        SolrQuery solrQuery = new SolrQuery();
        String face = "";
        String serchStr = "";
        String company = "";
        if (object.getInteger("type") == 0) {
            face = "buniessId";
            company = "by_buniess";
        } else if (object.getInteger("type") == 1) {
            face = "supplierId";
            company = "by_supplier";
        }

        if (!"".equals(object.get("search_keywords")) && object.get("search_keywords") != null && !"".equals(object.get("search_pro")) && object.get("search_pro") != null) {
            serchStr = "by_keyWordss:" + object.get("search_pro") + "&q=" + company + ":" + object.get("search_keywords");
            solrQuery.setQuery("by_keyWordss:" + object.get("search_pro") + " AND " + company + ":" + object.get("search_keywords"));
        } else {
            if (!"".equals(object.get("search_keywords")) && object.get("search_keywords") != null) {
                solrQuery.setQuery(serchStr);
                serchStr = company + ":" + object.get("search_keywords");
                solrQuery.setQuery(serchStr);
            }

            if (!"".equals(object.get("search_pro")) && object.get("search_pro") != null) {
                serchStr = "by_keyWordss:" + object.get("search_pro");
                solrQuery.setQuery(serchStr);
            }
        }
        serchStr = serchStr.replace(" ", "+");
        String url = "http://120.77.64.98:8002/solr/frank/select?q=" + serchStr + "&wt=json&indent=true&stats=true&stats.field=orderWeight&stats.field=orderVolume&stats.facet=" + face + "&rows=1";
        //URLEncoder.
        //System.out.println("aaa"+url);
        HttpGet method = new HttpGet(url);
        List<SupplierEntity> list = new ArrayList();

        try {
            HttpResponse result = httpClient.execute(method);

            JSONObject jsonData = (JSONObject) JSON.parse(EntityUtils.toString(result.getEntity()));

            //result出错
            JSONObject statsFields = jsonData.getJSONObject("stats").getJSONObject("stats_fields");
            JSONObject orderVolume = statsFields.getJSONObject("orderVolume");
            JSONObject orderWeight = statsFields.getJSONObject("orderWeight");

            //出错
            orderVolume = (JSONObject) orderVolume.getJSONObject("facets");
            orderWeight = (JSONObject) orderWeight.getJSONObject("facets");
            //orderWeight =(JSONObject) orderWeight.getJSONObject("orderWeight");
            //System.out.println("tttttttt"+orderVolume.toString());
            orderVolume = orderVolume.getJSONObject(face);
            System.out.println("tttttttt" + object.getInteger("sort"));
            orderWeight = orderWeight.getJSONObject(face);
            //排序
            if (object != null && object.getInteger("sort") != null) {
                if (object.getInteger("sort") == 1) {
                    //solrQuery.addSort(new SortClause("totalCount", ORDER.desc)); // 排序
                    Iterator<String> it = orderVolume.keySet().iterator();
                    while (it.hasNext()) {
                        String s = it.next();
                        JSONObject o = orderVolume.getJSONObject(s);
                        // System.out.println(o.get("count"));
                        SupplierEntity se = new SupplierEntity();
                        se.setTotalCount(Integer.parseInt(o.get("count").toString()));
                        se.setTotalVolume(Double.parseDouble(df.format(orderVolume.getJSONObject(s).getDouble("sum"))));
                        se.setTotalWeight(Double.parseDouble(df.format(orderWeight.getJSONObject(s).getDouble("sum"))));
                        se.setSid(Integer.parseInt(s));
                        se.setSortStr(Double.parseDouble(o.get("count").toString()));
                        list.add(se);

                    }
                    Collections.sort(list);
                    Collections.reverse(list);
                } else if (object.getInteger("sort") == -1) {
                    Iterator<String> it = orderVolume.keySet().iterator();
                    while (it.hasNext()) {
                        String s = it.next();
                        JSONObject o = orderVolume.getJSONObject(s);
                        //System.out.println(o.get("count"));
                        SupplierEntity se = new SupplierEntity();
                        se.setTotalCount(Integer.parseInt(o.get("count").toString()));
                        se.setTotalVolume(Double.parseDouble(df.format(orderVolume.getJSONObject(s).getDouble("sum"))));
                        se.setTotalWeight(Double.parseDouble(df.format(orderWeight.getJSONObject(s).getDouble("sum"))));
                        se.setSid(Integer.parseInt(s));
                        se.setSortStr(Double.parseDouble(o.get("count").toString()));
                        list.add(se);

                    }
                    Collections.sort(list);

                    //solrQuery.addSort(new SortClause("totalCount", ORDER.asc)); // 排序
                } else if (object.getInteger("sort") == 2) {
                    Iterator<String> it = orderVolume.keySet().iterator();
                    while (it.hasNext()) {
                        String s = it.next();
                        JSONObject o = orderWeight.getJSONObject(s);
                        // System.out.println(o.get("count"));
                        SupplierEntity se = new SupplierEntity();
                        se.setTotalCount(Integer.parseInt(o.get("count").toString()));
                        se.setTotalVolume(Double.parseDouble(df.format(orderVolume.getJSONObject(s).getDouble("sum"))));
                        se.setTotalWeight(Double.parseDouble(df.format(orderWeight.getJSONObject(s).getDouble("sum"))));
                        se.setSid(Integer.parseInt(s));
                        se.setSortStr(orderWeight.getJSONObject(s).getDouble("sum"));
                        list.add(se);


                    }
                    Collections.sort(list);
                    Collections.reverse(list);
                    //solrQuery.addSort(new SortClause("totalWeight", ORDER.desc)); // 排序
                } else if (object.getInteger("sort") == -2) {
                    //solrQuery.addSort(new SortClause("totalWeight", ORDER.asc)); // 排序
                    Iterator<String> it = orderVolume.keySet().iterator();
                    while (it.hasNext()) {
                        String s = it.next();
                        JSONObject o = orderWeight.getJSONObject(s);
                        //System.out.println(o.get("count"));
                        SupplierEntity se = new SupplierEntity();
                        se.setTotalCount(Integer.parseInt(o.get("count").toString()));
                        se.setTotalVolume(orderVolume.getJSONObject(s).getDouble("sum"));
                        se.setTotalWeight(orderWeight.getJSONObject(s).getDouble("sum"));
                        se.setSid(Integer.parseInt(s));
                        se.setSortStr(orderWeight.getJSONObject(s).getDouble("sum"));
                        list.add(se);

                    }

                    Collections.sort(list);

                } else if (object.getInteger("sort") == 3) {
                    Iterator<String> it = orderVolume.keySet().iterator();
                    while (it.hasNext()) {
                        String s = it.next();
                        JSONObject o = orderVolume.getJSONObject(s);

                        SupplierEntity se = new SupplierEntity();
                        se.setTotalCount(Integer.parseInt(o.get("count").toString()));
                        se.setTotalVolume(orderVolume.getJSONObject(s).getDouble("sum"));
                        se.setTotalWeight(orderWeight.getJSONObject(s).getDouble("sum"));
                        se.setSid(Integer.parseInt(s));
                        se.setSortStr(orderVolume.getJSONObject(s).getDouble("sum"));
                        list.add(se);

                    }

                    Collections.sort(list);
                    Collections.reverse(list);
                    //solrQuery.addSort(new SortClause("totalVolume", ORDER.desc)); // 排序
                } else if (object.getInteger("sort") == -3) {
                    //solrQuery.addSort(new SortClause("totalVolume", ORDER.asc)); // 排序
                    Iterator<String> it = orderVolume.keySet().iterator();
                    while (it.hasNext()) {
                        String s = it.next();
                        JSONObject o = orderVolume.getJSONObject(s);

                        SupplierEntity se = new SupplierEntity();
                        se.setTotalCount(Integer.parseInt(o.get("count").toString()));
                        se.setTotalVolume(Double.parseDouble(df.format(orderVolume.getJSONObject(s).getDouble("sum"))));
                        se.setTotalWeight(Double.parseDouble(df.format(orderWeight.getJSONObject(s).getDouble("sum"))));
                        se.setSid(Integer.parseInt(s));
                        se.setSortStr(orderVolume.getJSONObject(s).getDouble("sum"));
                        list.add(se);

                    }
                    Collections.sort(list);

                }
            } 

            //分组查询

            //solrQuery.setParam("wt", "json");
            solrQuery.setParam("group", true);//是否分组
            solrQuery.setParam("group.field", face);
            solrQuery.setParam("group.sort", "arrideDate asc");
            solrQuery.setParam("rows", "10000");
            //System.out.println("dddddd"+solrQuery);
            Map<String, SolrDocument> maps = new HashMap();
            try {
                QueryResponse resp = solrServer.query(solrQuery);

                //System.out.println(resp.getGroupResponse().getValues().toString());
                List<GroupCommand> listgroup = resp.getGroupResponse().getValues();
                //System.out.println(resp.getResponse().get("stats").toString().replaceAll("=", ":"));
                //System.out.println(JSON.parse(resp.getResponse().get("stats").toString().replaceAll("=", ":")).toString());

                //System.out.println("aaaaaabbbbbaaaa"+resp.getGroupResponse().toString());
                //System.out.println("aaaaaaaaaa"+listgroup.size());
                for (int i = 0; i < listgroup.size(); i++) {

                    List<Group> list2 = listgroup.get(i).getValues();
                    for (int j = 0; j < list2.size(); j++) {
                        SolrDocumentList list3 = list2.get(j).getResult();

                        maps.put(list3.get(0).getFieldValue(face).toString(), list3.get(0));
                    }
                }

            } catch (SolrServerException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            int p = 0;
            int start = (page.getCurPage() - 1) * page.getPageSize();
            List<SupplierEntity> slist = new ArrayList<>();

            for (int j = 0; j < list.size(); j++) {
                p++;
                SupplierEntity se = list.get(j);

                SolrDocument s = maps.get(se.getSid() + "");
                //null
                String f = s.get("proDesc").toString();
                f = f.replace(object.get("search_pro").toString().toUpperCase(), "<font color='red'>" + object.get("search_pro").toString().toUpperCase() + "</font>");
                //System.out.println(object.get("search_pro")+"aaaaaa"+f );
                //System.out.println("bbbbbbb"+s.get("proDesc").toString());
                //se.setLastProductDesc(s.get("proDesc").toString()); 
                se.setLastProductDesc(f);
                if (face.equals("buniessId")) {
                    se.setSname(s.get("buniess") + "");
                } else {
                    se.setSname(s.get("supplier") + "");
                }
               // System.out.println((se.getSname().equals("null")||se.getSname().equals("UNAVAILABLE"))+"aaaaaa"+se.getSname());
                if(se.getSname().equals("null")||se.getSname().equals("UNAVAILABLE")){
                	continue;
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat sdf1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
               
                    //Date date = sdf1.parse(s.get("arrideDate") + "");
                    //se.setLastDate(sdf.format(date));
                    se.setLastDate(s.get("arrideDate") + "");
             
                //System.out.println(se.getSname()+"---"+se.getLastProductDesc());
                if (p > start) {
                    slist.add(se);

                }

                if (slist.size() >= page.getPageSize()) {
                    break;
                }
            }
            //System.out.println(orderVolume.get("MR ZHOU WENZHAN").toString()+orderVolume.size());

            page.setCounts(list.size());
            page.setObjects(slist);
            page.setPageCount(list.size() / page.getPageSize() + 1);
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ResponseListResult createSuccess = ResponseListResult.createSuccess(page.getObjects());
        createSuccess.setTotal(list.size());
        return createSuccess.toJson();

    }

    public static List queryProportionByProKey(ProductParam param, int type) {
    	RelationGraph g= new RelationGraph();
    	List<RelationGraph> graph = new ArrayList();
    	 g.setId(param.getShangJiaId());
    	Map datamap = new HashMap();
    	Map gmap = new HashMap();
        SolrServer solrServer = new HttpSolrServer("http://120.77.64.98:8002/solr/frank");
        //关键词
        SolrQuery solrQuery = new SolrQuery();
        if (!"".equals(param.getProKey()) && (param.getProKey() != null)) {
        	
        }else{
        	solrQuery =new SolrQuery("by_keyWordss:" + param.getProKey());
        }
        
        solrQuery.setParam("rows", "10000");
        //企业ID
        //供应商或采购商
        if (param.getIetype() == 0) {
            solrQuery.setParam("fq", "buniessId:" + param.getShangJiaId());
           
        } else {
            solrQuery.setParam("fq", "supplierId:" + param.getShangJiaId());
        }
        try {
            QueryResponse resp = solrServer.query(solrQuery);
            SolrDocumentList list = resp.getResults();

            double total = 0.00;
            List<Map<String, Object>> listTransactionInfo = new ArrayList();
            List<BusinessProportion> listBusinessProportion1 = new ArrayList();
            List<Map<String, Object>> listRecentTransaction = new ArrayList();
            Map<String, Map<String, Object>> mapr = new HashMap();
            for (int i = 0; i < list.size(); i++) {
                Map map = new HashMap();
                SolrDocument s = list.get(i);
                //System.out.println(s.get("id")+s.toString());
                //System.out.println("tttttt"+s.get("orderWeight")+s.toString());
                //System.out.println("cccccccccc"+s.get("orderVolume")+s.toString());
                total = total + Double.parseDouble(s.get("orderWeight") + "");

                map.put("frankly_code", s.get("orderNo"));
                map.put("total_volume", s.get("orderVolume"));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat sdf1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
             
                    //Date date = sdf1.parse(s.get("arrideDate") + "");
                    map.put("yearMonth", s.get("arrideDate") + "");
               
                map.put("id", s.get("orderId"));
                map.put("product_description", s.get("proDesc"));
                map.put("qiyun_prot_name", s.get("qiyunPort"));
                map.put("mudi_prot_name", s.get("mudiPort"));
                listTransactionInfo.add(map);
                if (param.getIetype() == 0) {
                    if (mapr.get(s.get("buniessId") + "") == null) {
                        Map m = new HashMap();
                        m.put("volume", Double.parseDouble(s.get("orderVolume") + ""));
                        m.put("address", s.get("buniessAddress"));
                        m.put("pname", s.get("buniess"));
                        m.put("name", s.get("buniess"));
                        
                       
                        if(gmap.get(s.get("supplierId")+"")==null){
                        	RelationGraph g1= new RelationGraph();
                        	gmap.put(s.get("supplierId")+"", s);
                        	 g1.setId(Integer.parseInt(s.get("supplierId")+""));
                             g1.setName(s.get("supplier")+"");
                             graph.add(g1);
                        }
                            //Date date = sdf1.parse(s.get("arrideDate") + "");
                            map.put("yearMonth", s.get("arrideDate") + "");
                       
                        m.put("count", 1);
                        m.put("weight", Double.parseDouble(s.get("orderWeight") + ""));
                        mapr.put(s.get("buniessId") + "", m);
                        
                    } else {
                        Map m = mapr.get(s.get("buniessId") + "");
                        m.put("name", s.get("buniess"));
                        m.put("volume", Double.parseDouble(m.get("volume") + "") + Double.parseDouble(s.get("orderVolume") + ""));
                        m.put("address", s.get("buniessAddress"));
                        m.put("pname", s.get("buniess"));
                        m.put("yearMonth", s.get("arrideDate"));
                        m.put("count", Integer.parseInt(m.get("count") + "") + 1);
                        m.put("weight", Double.parseDouble(m.get("weight") + "") + Double.parseDouble(s.get("orderWeight") + ""));
                        mapr.put(s.get("buniessId") + "", m);

                        if(gmap.get(s.get("supplierId")+"")==null){
                        	RelationGraph g1= new RelationGraph();
                        	gmap.put(s.get("supplierId")+"", s);
                        	 g1.setId(Integer.parseInt(s.get("supplierId")+""));
                             g1.setName(s.get("supplier")+"");
                             graph.add(g1);
                        }
                       
                    }
                } else {
                    if (mapr.get(s.get("supplierId") + "") == null) {
                        Map m = new HashMap();
                        m.put("volume", Double.parseDouble(s.get("orderVolume") + ""));
                        m.put("address", s.get("supplierAddress"));
                        m.put("pname", s.get("supplier"));
                        m.put("name", s.get("supplier"));
                        if(gmap.get(s.get("buniessId")+"")==null){
                        	RelationGraph g1= new RelationGraph();
                        	gmap.put(s.get("buniessId")+"", s);
                        	 g1.setId(Integer.parseInt(s.get("buniessId")+""));
                             g1.setName(s.get("buniess")+"");
                             graph.add(g1);
                        }                                        
                            //Date date = sdf1.parse(s.get("arrideDate") + "");
                            m.put("yearMonth", s.get("arrideDate") + "");
                       
                        m.put("count", 1);
                        m.put("weight", Double.parseDouble(s.get("orderWeight") + ""));
                        mapr.put(s.get("supplierId") + "", m);
                    } else {
                        Map m = mapr.get(s.get("supplierId") + "");
                        if(gmap.get(s.get("buniessId")+"")==null){
                        	RelationGraph g1= new RelationGraph();
                        	gmap.put(s.get("buniessId")+"", s);
                        	 g1.setId(Integer.parseInt(s.get("buniessId")+""));
                             g1.setName(s.get("buniess")+"");
                             graph.add(g1);
                        }     
                      
                        try {
                            Date date = sdf1.parse(s.get("arrideDate") + "");
                            map.put("yearMonth", sdf.format(date));
                        } catch (ParseException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        m.put("volume", Double.parseDouble(m.get("volume") + "") + Double.parseDouble(s.get("orderVolume") + ""));
                        m.put("address", s.get("supplierAddress"));
                        m.put("pname", s.get("supplier"));
                        m.put("name", s.get("supplier"));
                        m.put("count", Integer.parseInt(m.get("count") + "") + 1);
                        m.put("weight", Double.parseDouble(m.get("weight") + "") + Double.parseDouble(s.get("orderWeight") + ""));
                        mapr.put(s.get("supplierId") + "", m);

                    }

                }

            }
            java.text.DecimalFormat df = new java.text.DecimalFormat("#.000");
            for (String s : mapr.keySet()) {
                Map m = mapr.get(s);
                m.get("weight");

                m.put("weight", df.format(Double.parseDouble(m.get("weight") + "")));
                listRecentTransaction.add(m);
                //System.out.println(m);
            }
            Map<String, BusinessProportion> mapb = new HashMap();
            for (int i = 0; i < listTransactionInfo.size(); i++) {

                SolrDocument s = list.get(i);

                double d = Double.parseDouble(s.get("orderWeight") + "") / total;

                //System.out.println(total+"aaa"+b.getBaifenbi());
                if (mapb.get(s.get("productName") + "") == null) {
                    Map<String, Object> m = new HashMap();
                    BusinessProportion b = new BusinessProportion();

                    b.setName(s.get("productName") + "");
                    b.setPid(1);
                    b.setValue(Double.parseDouble(s.get("orderWeight") + ""));
                    mapb.put(s.get("productName") + "", b);
                } else {
                    BusinessProportion b = mapb.get(s.get("productName") + "");
                    b.setName(s.get("productName") + "");
                    b.setPid(1);
                    b.setValue(Double.parseDouble(s.get("orderWeight") + "") + b.getValue());
                }

            }

            for (String s : mapb.keySet()) {
                BusinessProportion m = mapb.get(s);
                m.setBaifenbi(m.getValue() / total * 100 + "%");
                listBusinessProportion1.add(m);
                //System.out.println(m.getName()+"---"+m.getBaifenbi());
                //System.out.println(m);
            }
            g.setGraph(graph);
            datamap.put("graph", g);
            datamap.put("listTransactionInfo", listTransactionInfo.subList(0, 10));
            datamap.put("listBusinessProportion1", listBusinessProportion1);
            datamap.put("listRecentTransaction", listRecentTransaction);
            System.out.print(listTransactionInfo.size()+"---"+listBusinessProportion1.size()+"---"+listRecentTransaction.size());
            System.out.print(JSON.toJSONString(datamap));
            if (type == 1) {
                return listTransactionInfo;
            }

            if (type == 2) {
                return listBusinessProportion1;
            }

            if (type == 3) {
                return listRecentTransaction;
            }
           
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
             
        return null;

    }

    public static RelationGraph findRelationGraph(@RequestBody ProductParam productParam) {
        RelationGraph r = new RelationGraph();
        SolrServer solrServer = new HttpSolrServer("http://120.77.64.98:8002/solr/frank");
        SolrQuery solrQuery = new SolrQuery("by_keyWordss:" + productParam.getProKey());
        solrQuery.setParam("rows", "10000");
        if (productParam.getIetype() == 0) {
            solrQuery.setParam("fq", "buniessId:" + productParam.getShangJiaId());

        } else {
            solrQuery.setParam("fq", "supplierId:" + productParam.getShangJiaId());
        }
        QueryResponse resp;
        List<RelationGraph> list1 = new ArrayList();
        try {
            resp = solrServer.query(solrQuery);
            SolrDocumentList list = resp.getResults();
            //System.out.println(list.size());

            List lq = new ArrayList();
            for (int i = 0; i < list.size(); i++) {
                SolrDocument s = list.get(i);
                RelationGraph r1 = new RelationGraph();
                if (productParam.getIetype() == 0) {
                    r.setId(Integer.parseInt(s.get("supplierId") + ""));
                    r.setName(s.get("supplier") + "");
                    r1.setId(Integer.parseInt(s.get("buniessId") + ""));
                    r1.setName(s.get("buniess") + "");
                    //System.out.println(s.get("buniessId")+s.toString());
                    if (!lq.contains(s.get("buniessId"))) {
                        list1.add(r1);
                        lq.add(s.get("buniessId"));
                    }

                } else {
                    r.setId(Integer.parseInt(s.get("buniessId") + ""));
                    r.setName(s.get("buniess") + "");
                    System.out.println(s.get("supplierId") + s.toString());
                    r1.setId(Integer.parseInt(s.get("supplierId") + ""));
                    r1.setName(s.get("supplier") + "");
                    if (!lq.contains(s.get("supplierId"))) {
                        list1.add(r1);
                        lq.add(s.get("supplierId"));
                    }

                }
                //System.out.print(list1.size());
            }
        } catch (SolrServerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        r.setGraph(list1);
        return r;

    }

  
    public String getBuyerList(String param) {

        SolrServer solrServer = null;
        JSONObject object = JSONObject.parseObject(param);
        if (object.getInteger("type") == 0) {
            solrServer = SolrCloudServer.getSolrServer(ServerTypeEnum.BUSINIESS);
        } else if (object.getInteger("type") == 1) {
            solrServer = SolrCloudServer.getSolrServer(ServerTypeEnum.SUPPLIER);
        } else {
            //其他。。。
        }
        // 获取分页信息
        Page<SupplierEntity> page = new Page<SupplierEntity>();
        page.setCurPage(object.getInteger("pageNo") == null ? 1 : object.getInteger("pageNo"));
        page.setPageSize(object.getInteger("pageSize") == null ? 20 : object.getInteger("pageSize") > 20 ? 20 : object.getInteger("pageSize"));
        // 创建组合条件串
        StringBuilder params = new StringBuilder();
        // 组合商品条件

        if (!"".equals(object.get("search_keywords")) && object.get("search_keywords") != null && !"".equals(object.get("search_pro")) && object.get("search_pro") != null) {
            params.append("search_pro:\"" + object.get("search_pro") + "\" OR \"" + object.get("search_keywords").toString().replace(" ", "") + "\"" +
                    " AND search_keywords:\"" + object.get("search_keywords") + "\"");
        } else {
            if (!"".equals(object.get("search_keywords")) && object.get("search_keywords") != null) {
                params.append("search_keywords:\"" + object.get("search_keywords") + "\" OR search_keywords:\"" + object.get("search_keywords").toString().replace(" ", "") + "\"");
            }
            if (!"".equals(object.get("search_pro")) && object.get("search_pro") != null) {
                params.append("search_pro:\"" + object.get("search_pro") + "\" OR search_pro:\"" + object.get("search_pro").toString().replace(" ", "") + "\"");
            }
        }
        try {
            // 获取solr查询到的数据
            Page<SupplierEntity> goodsList = SolrUtils.queryByPage(page, params.toString(), solrServer, SupplierEntity.class, param);
            ResponseListResult createSuccess = ResponseListResult.createSuccess(goodsList.getObjects());
            createSuccess.setTotal(goodsList.getCounts());
            return createSuccess.toJson();

        } catch (Exception e) {
            logger.error("Exception: SearchService getLucenGoodsList  " + e.getMessage());
            return ResponseResult.createFalied(Constants.IO_EXCEPTION_CODE).toJson();
        }
    }


    
    public static String queryCompanyByProKey2(String param) {
    	DecimalFormat df = new DecimalFormat("######0.000");
        JSONObject object = JSONObject.parseObject(param);
        Page<SupplierEntity> page = new Page<>();
        page.setCurPage(object.getInteger("pageNo") == null ? 1 : object.getInteger("pageNo"));
        page.setPageSize(object.getInteger("pageSize") == null ? 20 : object.getInteger("pageSize") > 20 ? 20 : object.getInteger("pageSize"));
        DefaultHttpClient httpClient = new DefaultHttpClient();
        SolrServer solrServer = new HttpSolrServer("http://120.77.64.98:8002/solr/frank");
        SolrQuery solrQuery = new SolrQuery();
        String face = "";
        String serchStr = "";
        String company = "";
        if (object.getInteger("type") == 0) {
            face = "buniessId";
            company = "by_buniess";
        } else if (object.getInteger("type") == 1) {
            face = "supplierId";
            company = "by_supplier";
        }

        if (!"".equals(object.get("search_keywords")) && object.get("search_keywords") != null && !"".equals(object.get("search_pro")) && object.get("search_pro") != null) {
            serchStr = "by_keyWordss:" + object.get("search_pro") + "&q=" + company + ":" + object.get("search_keywords");
            solrQuery.setQuery("by_keyWordss:" + object.get("search_pro") + " AND " + company + ":" + object.get("search_keywords"));
        } else {
            if (!"".equals(object.get("search_keywords")) && object.get("search_keywords") != null) {
                solrQuery.setQuery(serchStr);
                serchStr = company + ":" + object.get("search_keywords");
                solrQuery.setQuery(serchStr);
            }

            if (!"".equals(object.get("search_pro")) && object.get("search_pro") != null) {
                serchStr = "by_keyWordss:" + object.get("search_pro");
                solrQuery.setQuery(serchStr);
            }
        }
        serchStr = serchStr.replace(" ", "+");
        String url = "http://120.77.64.98:8002/solr/frank/select?q=" + serchStr + "&wt=json&indent=true&stats=true&stats.field=orderWeight&stats.field=orderVolume&stats.facet=" + face + "&rows=1";
        //URLEncoder.
        System.out.println(new Date()+"aaa"+url);
        HttpGet method = new HttpGet(url);
        List<SupplierEntity> list = new ArrayList();

        try {
            HttpResponse result = httpClient.execute(method);

            JSONObject jsonData = (JSONObject) JSON.parse(EntityUtils.toString(result.getEntity()));

            //result出错
            JSONObject statsFields = jsonData.getJSONObject("stats").getJSONObject("stats_fields");
            JSONObject orderVolume = statsFields.getJSONObject("orderVolume");
            JSONObject orderWeight = statsFields.getJSONObject("orderWeight");

            //出错
            orderVolume = (JSONObject) orderVolume.getJSONObject("facets");
            orderWeight = (JSONObject) orderWeight.getJSONObject("facets");
            //orderWeight =(JSONObject) orderWeight.getJSONObject("orderWeight");
            //System.out.println("tttttttt"+orderVolume.toString());
            orderVolume = orderVolume.getJSONObject(face);
            System.out.println("tttttttt" + new Date());
            orderWeight = orderWeight.getJSONObject(face);
            //排序                         
                    //solrQuery.addSort(new SortClause("totalCount", ORDER.desc)); // 排序
                    Iterator<String> it = orderVolume.keySet().iterator();
                  /*  while (it.hasNext()) {
                        String s = it.next();
                        JSONObject o = orderVolume.getJSONObject(s);
                        // System.out.println(o.get("count"));
                        SupplierEntity se = new SupplierEntity();
                        se.setTotalCount(Integer.parseInt(o.get("count").toString()));
                        se.setTotalVolume(Double.parseDouble(df.format(orderVolume.getJSONObject(s).getDouble("sum"))));
                        se.setTotalWeight(Double.parseDouble(df.format(orderWeight.getJSONObject(s).getDouble("sum"))));
                        se.setSid(Integer.parseInt(s));
                        se.setSortStr(Double.parseDouble(o.get("count").toString()));
                        //list.add(se);

                    }
                    Collections.sort(list);
                    Collections.reverse(list);*/
                              	         
             
            //分组查询

            //solrQuery.setParam("wt", "json");
            solrQuery.setParam("group", true);//是否分组
            solrQuery.setParam("group.field", face);
            solrQuery.setParam("group.sort", "arrideDate asc");
            solrQuery.setParam("rows", "10000");
            //System.out.println("dddddd"+solrQuery);
            Map<String, SolrDocument> maps = new HashMap();
            try {
            	 System.out.println("aaaaaaaa"+ new Date());
                QueryResponse resp = solrServer.query(solrQuery);

                //System.out.println(resp.getGroupResponse().getValues().toString());
               
                List<GroupCommand> listgroup = resp.getGroupResponse().getValues();
                System.out.println("aaaaaaaa"+ new Date());
                //System.out.println(resp.getResponse().get("stats").toString().replaceAll("=", ":"));
                //System.out.println(JSON.parse(resp.getResponse().get("stats").toString().replaceAll("=", ":")).toString());

                //System.out.println("aaaaaabbbbbaaaa"+resp.getGroupResponse().toString());
                //System.out.println("aaaaaaaaaa"+listgroup.size());
                String proDesc="";
                String spro = object.get("search_pro").toString().toUpperCase();
                for (int i = 0; i < listgroup.size(); i++) {

                    List<Group> list2 = listgroup.get(i).getValues();
                    for (int j = 0; j < list2.size(); j++) {
                        SolrDocumentList list3 = list2.get(j).getResult();
                        String s = list3.get(0).getFieldValue(face).toString();
                        SolrDocument d = list3.get(0);
                        //System.out.println(d.toString()+"ttttttttt");
                        //System.out.println(d.get("proDesc")+"dddddd");
                        maps.put(s, list3.get(0));
                        JSONObject o = orderVolume.getJSONObject(s);
                        SupplierEntity se = new SupplierEntity();
                        se.setTotalCount(Integer.parseInt(o.get("count").toString()));
                        se.setTotalVolume(Double.parseDouble(df.format(orderVolume.getJSONObject(s).getDouble("sum"))));
                        se.setTotalWeight(Double.parseDouble(df.format(orderWeight.getJSONObject(s).getDouble("sum"))));
                        se.setSid(Integer.parseInt(s));
                        proDesc =d.get("proDesc").toString();
                       
                        if(proDesc.contains(spro)){
                        	
                        	se.setSortStr(1+se.getTotalCount().doubleValue()/300);
                        	 proDesc = proDesc.replace(spro, "<font color='red'>" + spro + "</font>");
                        }else{
                        	TreeMap m = Levenshtein.getMax(proDesc, spro);
                        	
                        	se.setSortStr(Double.parseDouble(m.lastKey()+"")+se.getTotalCount().doubleValue()/300);
                        	
                        	proDesc = proDesc.replace(m.get(m.lastKey()).toString(), "<font color='red'>" + m.get(m.lastKey()) + "</font>");
                        }
                       // se.setSortStr(Double.parseDouble(o.get("count").toString()));
                       se.setLastProductDesc(proDesc);
                     
                        if (face.equals("buniessId")) {
                            se.setSname(d.get("buniess") + "");
                           
                        } else {
                            se.setSname(d.get("supplier") + "");
                           
                        }
                        se.setLastDate(d.get("arrideDate") + "");
                        list.add(se);
                    }
                }

            } catch (SolrServerException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //System.out.println(list.size()+"aaaaaaaaaaa");
            int p = 0;
            int start = (page.getCurPage() - 1) * page.getPageSize();
            List<SupplierEntity> slist = new ArrayList<>();
            Collections.sort(list);
            Collections.reverse(list);
            for (int j = 0; j < list.size(); j++) {
                p++;
                SupplierEntity se = list.get(j);
                SolrDocument s = maps.get(se.getSid() + "");
                            
                if("null".equals(se.getSname())||"UNAVAILABLE".equals(se.getSname())){
                	continue;
                }
  
                if (p > start) {
                    slist.add(se);

                }

                if (slist.size() >= page.getPageSize()) {
                    break;
                }
            }
            //System.out.println(orderVolume.get("MR ZHOU WENZHAN").toString()+orderVolume.size());

            page.setCounts(list.size());
            page.setObjects(slist);
            page.setPageCount(list.size() / page.getPageSize() + 1);
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("ccccccccc"+ new Date());
        ResponseListResult createSuccess = ResponseListResult.createSuccess(page.getObjects());
        createSuccess.setTotal(list.size());
        return createSuccess.toJson();
    	
    }
    
    public static String queryAllDetail(ProductParam param) {
    	RelationGraph g= new RelationGraph();
    	List<RelationGraph> graph = new ArrayList();
    	 g.setId(param.getShangJiaId());
    	Map datamap = new HashMap();
    	Map gmap = new HashMap();
        SolrServer solrServer = new HttpSolrServer("http://120.77.64.98:8002/solr/frank");
        //关键词
        SolrQuery solrQuery = new SolrQuery();
        if (!"".equals(param.getProKey()) && (param.getProKey() != null)) {
        	solrQuery =new SolrQuery("by_keyWordss:" + param.getProKey());
        }else{
        	solrQuery =new SolrQuery("*:*");
        }
        
        solrQuery.setParam("rows", "10000");
        //企业ID
        //供应商或采购商
        //System.out.println("nnnnnnnnnnn"+param.getIetype()+"nnnnnnnnnn"+param.getShangJiaId());
        if (param.getIetype() == 0) {
            solrQuery.setParam("fq", "buniessId:" + param.getShangJiaId());
           
        } else {
            solrQuery.setParam("fq", "supplierId:" + param.getShangJiaId());
        }
        try {
        	//System.out.println(solrQuery.toString()+"aaaaaaaaaa");
            QueryResponse resp = solrServer.query(solrQuery);
            SolrDocumentList list = resp.getResults();

            double total = 0.00;
            List<Map<String, Object>> listTransactionInfo = new ArrayList();
            List<BusinessProportion> listBusinessProportion1 = new ArrayList();
            List<Map<String, Object>> listRecentTransaction = new ArrayList();
            Map<String, Map<String, Object>> mapr = new HashMap();
            for (int i = 0; i < list.size(); i++) {
                Map map = new HashMap();
                SolrDocument s = list.get(i);
                //System.out.println(s.get("id")+s.toString());
                //System.out.println("tttttt"+s.get("orderWeight")+s.toString());
                //System.out.println("cccccccccc"+s.get("orderVolume")+s.toString());
                total = total + Double.parseDouble(s.get("orderWeight") + "");

                map.put("frankly_code", s.get("orderNo"));
                map.put("total_volume", s.get("orderVolume"));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat sdf1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
             
                    //Date date = sdf1.parse(s.get("arrideDate") + "");
                    map.put("yearMonth", s.get("arrideDate") + "");
               
                map.put("id", s.get("orderId"));
                map.put("product_description", s.get("proDesc"));
                map.put("qiyun_prot_name", s.get("qiyunPort"));
                map.put("mudi_prot_name", s.get("mudiPort"));
                listTransactionInfo.add(map);
                if (param.getIetype() == 1) {
                	g.setName(s.get("supplier")+"");
                    if (mapr.get(s.get("buniessId") + "") == null) {
                        Map m = new HashMap();
                        m.put("volume", Double.parseDouble(s.get("orderVolume") + ""));
                        m.put("address", s.get("buniessAddress"));
                        m.put("pname", s.get("buniess"));
                        m.put("name", s.get("buniess"));
                        
                       
                        if(gmap.get(s.get("buniessId")+"")==null){
                        	RelationGraph g1= new RelationGraph();
                        	gmap.put(s.get("buniessId")+"", s);
                        	 g1.setId(Integer.parseInt(s.get("buniessId")+""));
                             g1.setName(s.get("buniess")+"");
                             graph.add(g1);
                        }
                            //Date date = sdf1.parse(s.get("arrideDate") + "");
                            map.put("yearMonth", s.get("arrideDate") + "");
                       
                        m.put("count", 1);
                        m.put("weight", Double.parseDouble(s.get("orderWeight") + ""));
                        mapr.put(s.get("buniessId") + "", m);
                        
                    } else {
                        Map m = mapr.get(s.get("buniessId") + "");
                        m.put("name", s.get("buniess"));
                        m.put("volume", Double.parseDouble(m.get("volume") + "") + Double.parseDouble(s.get("orderVolume") + ""));
                        m.put("address", s.get("buniessAddress"));
                        m.put("pname", s.get("buniess"));
                        m.put("yearMonth", s.get("arrideDate"));
                        m.put("count", Integer.parseInt(m.get("count") + "") + 1);
                        m.put("weight", Double.parseDouble(m.get("weight") + "") + Double.parseDouble(s.get("orderWeight") + ""));
                        mapr.put(s.get("buniessId") + "", m);

                        if(gmap.get(s.get("buniessId")+"")==null){
                        	RelationGraph g1= new RelationGraph();
                        	gmap.put(s.get("buniessId")+"", s);
                        	 g1.setId(Integer.parseInt(s.get("buniessId")+""));
                             g1.setName(s.get("buniess")+"");
                             graph.add(g1);
                        }
                       
                    }
                } else {
                	g.setName(s.get("buniess")+"");
                    if (mapr.get(s.get("supplierId") + "") == null) {
                        Map m = new HashMap();
                        m.put("volume", Double.parseDouble(s.get("orderVolume") + ""));
                        m.put("address", s.get("supplierAddress"));
                        m.put("pname", s.get("supplier"));
                        m.put("name", s.get("supplier"));
                        if(gmap.get(s.get("supplierId")+"")==null){
                        	RelationGraph g1= new RelationGraph();
                        	gmap.put(s.get("supplierId")+"", s);
                        	 g1.setId(Integer.parseInt(s.get("supplierId")+""));
                             g1.setName(s.get("supplier")+"");
                             graph.add(g1);
                        }                                        
                            //Date date = sdf1.parse(s.get("arrideDate") + "");
                            m.put("yearMonth", s.get("arrideDate") + "");
                       
                        m.put("count", 1);
                        m.put("weight", Double.parseDouble(s.get("orderWeight") + ""));
                        mapr.put(s.get("supplierId") + "", m);
                    } else {
                        Map m = mapr.get(s.get("supplierId") + "");
                        if(gmap.get(s.get("supplierId")+"")==null){
                        	RelationGraph g1= new RelationGraph();
                        	gmap.put(s.get("supplierId")+"", s);
                        	 g1.setId(Integer.parseInt(s.get("supplierId")+""));
                             g1.setName(s.get("supplier")+"");
                             graph.add(g1);
                        }     
                                           
                            //Date date = sdf1.parse(s.get("arrideDate") + "");
                            map.put("yearMonth", s.get("arrideDate") + "");
                        
                        m.put("volume", Double.parseDouble(m.get("volume") + "") + Double.parseDouble(s.get("orderVolume") + ""));
                        m.put("address", s.get("supplierAddress"));
                        m.put("pname", s.get("supplier"));
                        m.put("name", s.get("supplier"));
                        m.put("count", Integer.parseInt(m.get("count") + "") + 1);
                        m.put("weight", Double.parseDouble(m.get("weight") + "") + Double.parseDouble(s.get("orderWeight") + ""));
                        mapr.put(s.get("supplierId") + "", m);

                    }

                }

            }
           // System.out.println("bbb"+mapr.size());
            java.text.DecimalFormat df = new java.text.DecimalFormat("#.000");
            for (String s : mapr.keySet()) {
                Map m = mapr.get(s);
                m.get("weight");

                m.put("weight", df.format(Double.parseDouble(m.get("weight") + "")));
                listRecentTransaction.add(m);
                //System.out.println("aaa"+m);
            }
            Map<String, BusinessProportion> mapb = new HashMap();
            for (int i = 0; i < listTransactionInfo.size(); i++) {

                SolrDocument s = list.get(i);

                double d = Double.parseDouble(s.get("orderWeight") + "") / total;

                //System.out.println(total+"aaa"+b.getBaifenbi());
                if (mapb.get(s.get("productName") + "") == null) {
                    Map<String, Object> m = new HashMap();
                    BusinessProportion b = new BusinessProportion();

                    b.setName(s.get("productName") + "");
                    b.setPid(1);
                    b.setValue(Double.parseDouble(s.get("orderWeight") + ""));
                    mapb.put(s.get("productName") + "", b);
                } else {
                    BusinessProportion b = mapb.get(s.get("productName") + "");
                    b.setName(s.get("productName") + "");
                    b.setPid(1);
                    b.setValue(Double.parseDouble(s.get("orderWeight") + "") + b.getValue());
                }

            }

            for (String s : mapb.keySet()) {
                BusinessProportion m = mapb.get(s);
                m.setBaifenbi(m.getValue() / total * 100 + "%");
                listBusinessProportion1.add(m);
                //System.out.println(m.getName()+"---"+m.getBaifenbi());
                //System.out.println(m);
            }
          
            List<RelationGraph> graph1 = new ArrayList();
            if(graph.size()>20){
            	graph=graph.subList(0, 20);
            }
            List flist = new ArrayList();
            List flist2 = new ArrayList();
            flist.add(g.getId());
            for(int j=0;j<graph.size();j++){
            	RelationGraph r = graph.get(j);
            	flist2.add(r.getId());
            }
            for(int j=0;j<graph.size();j++){
            	RelationGraph r = graph.get(j);    
            	List<RelationGraph> glist =getRelationGraph(param.getProKey(),r.getId()+"",param.getIetype(),flist);
            	
            	List<RelationGraph> graph2 = new ArrayList();
            	for(int k=0;k<glist.size();k++){
            		RelationGraph r1 = glist.get(k);
            		if(param.getIetype()==0){
            			List<RelationGraph> glist2 =getRelationGraph(param.getProKey(),r1.getId()+"",1,flist2);
            			r1.setGraph(glist2);
            		}else{
            			List<RelationGraph> glist2 =getRelationGraph(param.getProKey(),r1.getId()+"",0,flist2);
            			r1.setGraph(glist2);
            		}
            		graph2.add(r1);
            		
            	}
            	//graph2.add();
            	r.setGraph(graph2);
            	graph1.add(r);
            }
            g.setGraph(graph1);
            datamap.put("graph", g);
            Map transactionInfo = new HashMap();
            Map recentTransaction= new HashMap();
            
            Collections.sort(listTransactionInfo, new Comparator() {
                @Override
                public int compare(Object o1, Object o2) {
                	return (int)((Double.parseDouble(((Map)o2).get("total_volume")+"") -Double.parseDouble(((Map)o1).get("total_volume")+""))*100);
                	
                }
              });
            if(listTransactionInfo.size()<10){
            	 transactionInfo.put("listTransactionInfo", listTransactionInfo.subList(0, listTransactionInfo.size()));
            	 transactionInfo.put("total", listBusinessProportion1.size());
            	datamap.put("transactionInfo",transactionInfo );
            }else{
            	 transactionInfo.put("listTransactionInfo", listTransactionInfo.subList(0, 10));
            	 transactionInfo.put("total", listTransactionInfo.size());
            	datamap.put("transactionInfo",transactionInfo );
            	
            }           
            datamap.put("listBusinessProportion1", listBusinessProportion1);
           Collections.sort(listRecentTransaction, new Comparator() {
                @Override
                public int compare(Object o1, Object o2) {
                	return Integer.parseInt(((Map)o2).get("count")+"") -Integer.parseInt(((Map)o1).get("count")+"");
                	
                }
              });
            if(listRecentTransaction.size()<10){
            	recentTransaction.put("listRecentTransaction", listRecentTransaction);
            }else{
            	recentTransaction.put("listRecentTransaction", listRecentTransaction.subList(0, 10));
            }
            
            recentTransaction.put("total", listRecentTransaction.size());
            datamap.put("recentTransaction", recentTransaction);
            
           // System.out.print(listTransactionInfo.size()+"---"+listBusinessProportion1.size()+"---"+listRecentTransaction.size());
            //System.out.print(JSON.toJSONString(datamap));
           
           
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
             
        return JSON.toJSONString(datamap);

    }
    
    static List<RelationGraph> getRelationGraph(String proKey,String pid,int type,List flist){
    	 SolrServer solrServer = new HttpSolrServer("http://120.77.64.98:8002/solr/frank");
    	 SolrQuery solrQuery = new SolrQuery("*:*");
    	 if(!proKey.equals("")){
    		 solrQuery = new SolrQuery("by_keyWordss:"+proKey);
    	 }
    	 List<RelationGraph> list = new ArrayList();
    	
    	  solrQuery.setParam("group", true);//是否分组
    	  if(type==0){
    		  solrQuery.setParam("group.field", "buniessId");
    		  solrQuery.setParam("fq", "supplierId:" + pid);
    		 
    	  }else{
    		  solrQuery.setParam("group.field", "supplierId");
    		  solrQuery.setParam("fq", "buniessId:" + pid);
    	  }
                   
          solrQuery.setParam("rows", "20");
          try {
        	  System.out.println("aaaaaaaaaa"+solrQuery);
			QueryResponse resp = solrServer.query(solrQuery);
			 List<GroupCommand> listgroup = resp.getGroupResponse().getValues();
			 
			 for (int i = 0; i < listgroup.size(); i++) {

                 List<Group> list2 = listgroup.get(i).getValues();
                 //System.out.println("aaaaaaaaaa"+list2.size());
                 for (int j = 0; j < list2.size(); j++) {
                     SolrDocumentList list3 = list2.get(j).getResult();
                   
                     //String s = list3.get(0).getFieldValue(face).toString();
                     RelationGraph g = new RelationGraph();
                     if(type==0){
                    	                    	 
                    	 g.setId(Integer.parseInt(list3.get(0).getFieldValue("buniessId").toString()));
                    	 if(list3.get(0).getFieldValue("buniess")!=null){
                    		 g.setName(list3.get(0).getFieldValue("buniess").toString());
                    	 }
                    	
                    	
                     }else{
                    	 g.setId(Integer.parseInt(list3.get(0).getFieldValue("supplierId").toString()));
                    	// System.out.println("bbbbb"+list3.get(0).toString());
                    	 if(list3.get(0).getFieldValue("supplier")!=null){
                    		 g.setName(list3.get(0).getFieldValue("supplier").toString()); 
                    	 }
                    	
                    	
                     }
                     if(!flist.contains(g.getId())){
                    	 list.add(g);
                     }
                     
                 }
			 }
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
    	
    }
    
    public static void main(String[] args) {
        ProductParam param = new ProductParam();
        param.setIetype(1);
        param.setProkey("bike");
        param.setShangJiaId(436660);
        //System.out.println(queryProportionByProKey(param, 2).size());
        //queryCompanyByProKey("{'type':0,'search_keywords':'bike','sort':1}");
        //System.out.println(getRelationGraph("vacuum cleaner","2084037",1));
    }

}
