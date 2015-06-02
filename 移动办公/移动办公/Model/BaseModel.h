//
//  BaseModel.h
//  移动办公
//
//  Created by 尹亚涛 on 15/5/15.
//  Copyright (c) 2015年 陶照平. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "AnalyticalDelegate.h"
#import "PushViewDelegate.h"
#import "AppUrl.h"

#pragma mark model基础类，所有model必须继承该方法
@interface BaseModel : NSObject<AnalyticalDelegate>

#pragma mark -  参数
#pragma mark 是否完成
@property (nonatomic,readonly,getter=IsComplete)BOOL complete;

@property(nonatomic) BOOL IsSuccess;

@property(nonatomic,copy)NSString* Result;

@property (nonatomic,strong) NSError* err;

#pragma mark 错误编码
@property (nonatomic,copy,readonly)NSString* errorCode;

#pragma mark 错误信息
@property (nonatomic,copy,readonly) NSString* errorMessage;

#pragma mark 委托（更新页面）
@property(nonatomic,strong)id<PushViewDelegate> pushViewDelegate;

#pragma mark 返回json字符串
@property (nonatomic,readonly) NSString* json;


@property(nonatomic) NSDictionary* dictionary;

#pragma mark 搜索字典
@property(nonatomic,strong) NSMutableDictionary* searchDictionary;

#pragma mark 搜索文本
@property(nonatomic,strong) NSString* searchText;

#pragma mark 服务器URL方法
@property(nonatomic,strong) NSString* urlMethod;

#pragma mark 是否同步
@property(nonatomic,getter=isSendSynchronous)BOOL sendSynchronous;

#pragma mark 返回的列表数据
@property(nonatomic,strong)NSMutableArray* reultArray;

-(instancetype)initWithObject;

+(instancetype)getInstance;

#pragma mark 初始化一些参数
-(void)initParame;

@end
