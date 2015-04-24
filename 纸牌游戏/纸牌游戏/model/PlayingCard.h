//
//  PlayingCard.h
//  纸牌游戏
//
//  Created by 陶照平 on 15-4-22.
//  Copyright (c) 2015年 陶照平. All rights reserved.
//

#import "Crad.h"
#import <GameKit/GameKit.h>

@interface PlayingCard : Crad

@property (nonatomic,strong) NSString *suit;

@property (nonatomic) NSUInteger rank;

+(NSArray *)colorCrad;

+(NSArray *)rankStrings;

+(NSUInteger)maxRank;


@end
