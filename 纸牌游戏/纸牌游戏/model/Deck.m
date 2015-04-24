//
//  Deck.m
//  纸牌游戏
//
//  Created by 陶照平 on 15-4-22.
//  Copyright (c) 2015年 陶照平. All rights reserved.
//

#import "Deck.h"
#import "PlayingCard.h"
@interface Deck ()
@property (nonatomic,strong) NSMutableArray * cradArray;
@end

@implementation Deck

-(NSMutableArray *) cradArray
{
    //获取纸牌堆，当为null的时候，创建一个新的
    if (!_cradArray)
    {
        _cradArray = [[NSMutableArray alloc] init];
    }
    return _cradArray;
}

-(void)addCrad:(Crad *)crad
        atTop:(BOOL)atTop
{
    if (atTop) {
        //替换原来位置的对象
        //[self.cradArray setObject:crad atIndexedSubscript:0];
        [self.cradArray insertObject:crad atIndex:0];
    }else{
        [self.cradArray addObject:crad];
    }
    
}

-(void)addCrad:(Crad *)crad
{
    [self addCrad:crad atTop:NO];
}

-(Crad *)drawRandomCard
{
    //随机抽取纸牌，
    Crad *crad = nil;
    NSMutableArray *crads = self.cradArray;
    
    if ([crads count])
    {
        NSUInteger count = arc4random() % [crads count];
        crad = crads[count];
        if([crad isKindOfClass:[PlayingCard class]])
        //if ([PlayingCard class] == [crad class])
        {
            [crads removeObjectAtIndex:count];
        }
    }
    return crad;
}

@end
