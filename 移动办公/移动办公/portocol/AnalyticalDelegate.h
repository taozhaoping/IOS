//
//  AnalyticalDelegate.h
//  移动办公
//
//  Created by 尹亚涛 on 15/5/15.
//  Copyright (c) 2015年 陶照平. All rights reserved.
//
#import <Foundation/Foundation.h>

#pragma mark 解析协议
@protocol AnalyticalDelegate <NSObject>

//@required
#pragma mark json字符串转换成字典
-(NSDictionary*)DictionaryForJson:(NSString *) json;

#pragma mark 字典转换成JSON字符串
-(NSString*)JsonForDictionary:(NSDictionary*) dictionary;

#pragma mark 当前对象转换成字典
-(NSDictionary*)DictionaryForBean;

#pragma mark 字典转换成当前对象
-(void)beanForDictionary:(NSDictionary *)dict;

#pragma mark 当前对象转换成JSON字符串
-(NSString*)JsonForDictionary;

-(NSDictionary*)dictionaryForSearchBean;

@end
