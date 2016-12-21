/*******************************************************************************
 *
 *==============================================================================
 *
 * Copyright (c) 2001-2016 Primeton Technologies, Ltd.
 * All rights reserved.
 * 
 * Created on 2016年12月20日 下午2:18:27
 *******************************************************************************/


package com.primeton.zipkin;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.kristofa.brave.Brave;
import com.github.kristofa.brave.Brave.Builder;
import com.github.kristofa.brave.EmptySpanCollectorMetricsHandler;
import com.github.kristofa.brave.Sampler;
import com.github.kristofa.brave.SpanCollector;
import com.github.kristofa.brave.http.DefaultSpanNameProvider;
import com.github.kristofa.brave.http.HttpSpanCollector;
import com.github.kristofa.brave.http.HttpSpanCollector.Config;
import com.github.kristofa.brave.okhttp.BraveOkHttpRequestResponseInterceptor;
import com.github.kristofa.brave.servlet.BraveServletFilter;

import okhttp3.OkHttpClient;

/**
 * 
 * TODO zipkin配置
 *
 * @author wangzhao (mailto:wangzhao@primeton.com)
 */
@Configuration
public class ZipkinConfig {
    
    //span（一次请求信息或者一次链路调用）信息收集器
    @Bean
    public SpanCollector spanCollector() {
        Config config = HttpSpanCollector.Config.builder()
                .compressionEnabled(false)// 默认false，span在transport之前是否会被gzipped
                .connectTimeout(5000)
                .flushInterval(1)
                .readTimeout(6000)
                .build();
        return HttpSpanCollector.create("http://localhost:9411", config, new EmptySpanCollectorMetricsHandler());
    }
    
    //作为各调用链路，只需要负责将指定格式的数据发送给zipkin
    @Bean
    public Brave brave(SpanCollector spanCollector){
        Builder builder = new Builder("service1");//指定serviceName
        builder.spanCollector(spanCollector);
        builder.traceSampler(Sampler.create(1));//采集率
        return builder.build();
    }

    //设置server的（服务端收到请求和服务端完成处理，并将结果发送给客户端）过滤器
    @Bean
    public BraveServletFilter braveServletFilter(Brave brave) {
        BraveServletFilter filter = new BraveServletFilter(brave.serverRequestInterceptor(),
                brave.serverResponseInterceptor(), new DefaultSpanNameProvider());
        return filter;
    }
    
    //设置client的（发起请求和获取到服务端返回信息）拦截器
    @Bean
    public OkHttpClient okHttpClient(Brave brave){
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new BraveOkHttpRequestResponseInterceptor(
                        brave.clientRequestInterceptor(),
                        brave.clientResponseInterceptor(), 
                        new DefaultSpanNameProvider())).build();
        return httpClient;
    }
}
