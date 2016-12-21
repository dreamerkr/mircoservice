/*******************************************************************************
 *
 *==============================================================================
 *
 * Copyright (c) 2001-2016 Primeton Technologies, Ltd.
 * All rights reserved.
 * 
 * Created on 2016年12月20日 下午3:44:09
 *******************************************************************************/


package com.primeton.zipkin.service3;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("service的API接口")
@RestController
@RequestMapping("/service3")
public class ZipkinBraveController {
    
    @ApiOperation("trace第三步")
    @RequestMapping("/test")
    public String service1() throws Exception {
        Thread.sleep(300);
        return "service3";
    }
    
}
