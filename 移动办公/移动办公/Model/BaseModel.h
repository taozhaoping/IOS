//
//  BaseModel.h
//  移动办公
//
//  Created by 尹亚涛 on 15/5/15.
//  Copyright (c) 2015年 陶照平. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "AnalyticalDelegate.h"
#import "AFHTTPRequestOperation.h"
#import "ServiceUtil.h"
#import "AppUrl.h"

#pragma mark model基础类，所有model必须继承该方法
@interface BaseModel : NSObject<AnalyticalDelegate>

#pragma mark -  参数
#pragma mark 是否完成
@property (nonatomic,readonly,getter=isComplete)BOOL complete;

@property(nonatomic,getter=isSuccess) BOOL success;

@property (nonatomic)__block NSError* err;

#pragma mark 是否报错
@property (nonatomic,readonly,getter=isError) BOOL error;

#pragma mark 错误编码
@property (nonatomic,readonly)NSString* errorCode;

#pragma mark 错误信息
@property (nonatomic,readonly) NSString* errorMessage;

#pragma mark 返回json字符串
@property (nonatomic,readonly) NSString* json;


@property(nonatomic)__block NSDictionary* dictionary;

#pragma mark 是否同步
@property(nonatomic,getter=isSendSynchronous)BOOL sendSynchronous;

#pragma mark - http接口
@property (nonatomic,readonly) NSMutableString* http;
@property (nonatomic,readonly) NSString* serviceMethod;
@property (nonatomic,strong,readonly) NSURL* url;
@property (nonatomic,strong,readonly) NSMutableURLRequest* request;
@property (nonatomic,strong,readonly) AFHTTPRequestOperation* afHttp;
@property(nonatomic,readonly) NSString* httpMethod;

#pragma mark - 公共方法
#pragma mark 查询信息
-(NSDictionary*)queryService;
#pragma mark 修改信息
-(BOOL)updateService;

#pragma mark 下载文件
-(NSString*)downLoadFile;

@end
