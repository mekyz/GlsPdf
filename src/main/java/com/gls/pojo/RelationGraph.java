/**
 * Copyright 2016 ECCloud Corporation. All rights reserved.
 *
 * --------------------------------------------------------
 * 此技术信息为本公司机密信息，未经本公司书面同意禁止向第三方披露.
 * 版权所有：贝恩国际
 * --------------------------------------------------------
 */

package com.gls.pojo;

import java.util.List;

/**
 * 
 * 文  件  名:RelationGraph<br/>  
 * 文件描述:合作伙伴关系图 <br/>  
 * 修  改  人: 陈取名 <br/>
 * 修改日期:2017年1月19日<br/>
 * 修改内容:<br/>
 */
public class RelationGraph {
    
    private Integer id;
    private String name;
    
    private List<RelationGraph> graph;
    
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    public List<RelationGraph> getGraph() {
        return graph;
    }
    public void setGraph(List<RelationGraph> graph) {
        this.graph = graph;
    }
    
    
    public RelationGraph(Integer id, String name, List<RelationGraph> graph) {
        super();
        this.id = id;
        this.name = name;
        this.graph = graph;
    }
    public RelationGraph() {
        super();
    }
    
    
}
