package com.gls.search.util


import org.json.JSONObject
import java.net.URLEncoder
import java.util.*


/**
 *
 * @author willian
 * @created 2017-07-03 17:35
 * @email 18702515157@163.com
 **/

fun getStats(q: HashMap<String,String>?,facet:String,pageNo:Int=1,pageSize:Int=20): JSONObject {

    val start = (pageNo - 1) * pageSize

    var url = "http://120.77.64.98:8002/solr/frank/select?wt=json&indent=true&stats=true&stats.field=orderWeight&stats.field=orderVolume&start=$start&rows=$pageSize"

    q?.forEach { t, u ->
        url = "$url&q=$t:$u"
    }
    url = url +"&stats.facet="+facet
//    val hashMap = HashMap<String, String>()
//    hashMap["wt"] = "json"
//    hashMap["q"] = q
//    hashMap["indent"] = "true"
//    hashMap["stats"] = "true"
//    hashMap["stats.field"] = "orderWeight"
//    hashMap["stats.field"] = "orderVolume"
//    hashMap["rows"] = "1"
//    hashMap["stats.facet"] = facet
    val response = khttp.get(url)
    return response.jsonObject
}

fun getGroup(groupBy:Array<String>,rows:Int,sort:String = "",q: HashMap<String,String>?,pageNo:Int=1,pageSize:Int=20):JSONObject{
    var url = "http://120.77.64.98:8002/solr/frank/select?wt=json&indent=true&group=true&rows=${rows}"
    for (field in groupBy) {
        url = url + "&group.field=" + field
    }
    q?.forEach { t, u ->
        url = "$url&q=$t:$u"
    }
    if (sort.isNotBlank()) url = url + "&group.sort=" + URLEncoder.encode(sort,"utf-8")
    return khttp.get(url).jsonObject
}

fun getSolr(q: HashMap<String,String>?,pageNo:Int=1,pageSize:Int=20,solrLibrary:String):JSONObject{
    val start = (pageNo - 1) * pageSize
    var url = "http://120.77.64.98:8002/solr/$solrLibrary/select?wt=json&indent=true&start=$start&rows=$pageSize"
    q?.forEach { t, u ->
        url = "$url&q=$t:$u"
    }
    return khttp.get(url).jsonObject
}