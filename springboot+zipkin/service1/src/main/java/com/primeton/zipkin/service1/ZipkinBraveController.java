/*******************************************************************************
 *
 *==============================================================================
 *
 * Copyright (c) 2001-2016 Primeton Technologies, Ltd.
 * All rights reserved.
 * 
 * Created on 2016年12月20日 下午3:44:09
 *******************************************************************************/


package com.primeton.zipkin.service1;

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
@RequestMapping("/service1")
public class ZipkinBraveController {

    @Autowired
    private OkHttpClient client;
    
    @ApiOperation("trace第一步")
    @RequestMapping("/test")
    public String service1() throws Exception {
        Thread.sleep(100);
        Request request = new Request.Builder().url("http://localhost:8082/service2/test").build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
    
}
