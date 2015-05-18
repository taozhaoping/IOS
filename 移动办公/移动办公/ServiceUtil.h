//
//  ServiceUtil.h
//  移动办公
//
//  Created by 尹亚涛 on 15/5/14.
//  Copyright (c) 2015年 陶照平. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "AFHTTPRequestOperation.h"
#import "AppUrl.h"
#import "DESUtil.h"
#import "BaseModel.h"

typedef void(^RequestSuccess)(AFHTTPRequestOperation*, id);

@interface ServiceUtil : NSObject

#pragma mark - 参数
@property (nonatomic,strong,readonly) NSString* http;
@property (nonatomic,strong,readonly) NSMutableURLRequest* request;
@property (nonatomic,strong,readonly) AFHTTPRequestOperation* afHttp;

@property (nonatomic)__block NSError* err;

#pragma mark 是否报错
@property (nonatomic,readonly,getter=isError) BOOL error;

#pragma mark 错误编码
@property (nonatomic,readonly)NSString* errorCode;

#pragma mark 错误信息
@property (nonatomic,readonly) NSString* errorMessage;

#pragma mark 返回json字符串
@property (nonatomic,readonly) NSString* json;

#pragma mark 返回结果的block代码块
@property(nonatomic,copy) RequestSuccess requestReturn;

@property(nonatomic)__block NSDictionary* dictionary;

@property(nonatomic,readonly) NSString* httpMethod;

#pragma mark - 公共方法
#pragma mark 查询信息
-(NSDictionary*)queryService:(NSString*)serviceMethod;
#pragma mark 修改信息
-(BOOL)updateService;

#pragma mark 下载文件
-(NSString*)downLoadFile;


@end
