//
//  Deck.h
//  纸牌游戏
//
//  Created by 陶照平 on 15-4-22.
//  Copyright (c) 2015年 陶照平. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Crad.h"

@interface Deck : NSObject

#pragma mark - 公共方法
#pragma mark 在头部或者底部添加纸牌
-(void) addCrad :(Crad *) crad atTop : (BOOL) atTop;

#pragma mark 在底部添加纸牌
-(void) addCrad : (Crad *) crad;

#pragma mark 获取纸牌
-(Crad *) drawRandomCard;
@end
