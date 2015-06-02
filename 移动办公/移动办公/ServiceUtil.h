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

@interface ServiceUtil : NSObject

#pragma mark - 参数
@property (nonatomic,strong,readonly) NSMutableString* http;

#pragma mark 请求方式
@property(nonatomic,strong)NSString* httpMethod;
@property (nonatomic,strong,readonly) NSMutableURLRequest* request;
@property (nonatomic,strong,readonly) AFHTTPRequestOperation* afHttp;

-(instancetype)initWithService;

+(instancetype)getInstance;

#pragma mark - 公共方法

#pragma mark 查询信息
-(void)queryService:(BaseModel*)baseModel;


@end
