//
//  Crad.h
//  纸牌游戏
//
//  Created by 陶照平 on 15-4-22.
//  Copyright (c) 2015年 陶照平. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Crad : NSObject

#pragma mark - 属性设置
#pragma mark 内容
@property (nonatomic,retain) NSString* contents;

#pragma mark 是否选中
@property (nonatomic,assign,getter=isChosen) BOOL chosen;

#pragma mark 是否匹配
@property (nonatomic,assign,getter=isMatched) BOOL matched;

#pragma mark - 公共方法
#pragma mark 是否匹配
-(int)match :(NSArray *) otherCards;

@end
