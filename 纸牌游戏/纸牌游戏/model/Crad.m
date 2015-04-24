//
//  Crad.m
//  纸牌游戏
//
//  Created by 陶照平 on 15-4-22.
//  Copyright (c) 2015年 陶照平. All rights reserved.
//

#import "Crad.h"

@interface Crad ()

@end

@implementation Crad


-(int)match:(NSArray *)otherCards
{
    int score = 0;
    
    for (Crad* crad in otherCards){
        if ([crad.contents isEqualToString:self.contents]) {
            score = 1;
        }
    }
    return score;
}

@end
