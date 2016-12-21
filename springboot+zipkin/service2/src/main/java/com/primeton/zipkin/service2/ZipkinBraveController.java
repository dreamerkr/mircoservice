/*******************************************************************************
 *
 *==============================================================================
 *
 * Copyright (c) 2001-2016 Primeton Technologies, Ltd.
 * All rights reserved.
 * 
 * Created on 2016年12月20日 下午3:44:09
 *******************************************************************************/


package com.primeton.zipkin.service2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Api("service的API接口")
@RestController
@RequestMapping("/service2")
public class ZipkinBraveController {

    @Autowired
    private OkHttpClient client;
    
    @ApiOperation("trace第二步")
    @RequestMapping("/test")
    public String service1() throws Exception {
        Thread.sleep(200);
        Request request3 = new Request.Builder().url("http://localhost:8083/service3/test").build();
        Response response3 = client.newCall(request3).execute();
        
        Request request4 = new Request.Builder().url("http://localhost:8084/service4/test").build();
        Response response4 = client.newCall(request4).execute();
        return response3.toString()+":"+response4.toString();
    }
    
}
