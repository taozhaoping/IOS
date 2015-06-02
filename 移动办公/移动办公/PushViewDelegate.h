//
//  PushViewDelegate.h
//  移动办公
//
//  Created by 陶照平 on 15/6/2.
//  Copyright (c) 2015年 陶照平. All rights reserved.
//

#import <Foundation/Foundation.h>
@class BaseModel;

@protocol PushViewDelegate

#pragma mark 开始加载
-(void)startLoad;

#pragma mark 开始刷新刷新
-(void)pushView;

#pragma mark 返回错误
-(void)reultError:(BaseModel*)baseModel;

#pragma mark 结束加载
-(void)endLoad;
@end
