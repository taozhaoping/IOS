//
//  PlayingCardDeck.m
//  纸牌游戏
//
//  Created by 陶照平 on 15-4-22.
//  Copyright (c) 2015年 陶照平. All rights reserved.
//

#import "PlayingCardDeck.h"
#import "PlayingCard.h"

@implementation PlayingCardDeck

-(id)init
{
    self = [super init];
    
    if(self)
    {
        for (NSString *sult in [PlayingCard colorCrad]  ) {
            for (NSUInteger rank =1 ; rank < [PlayingCard maxRank]; rank++) {
                PlayingCard* crad = [[PlayingCard alloc] init];
                crad.suit = sult;
                crad.rank = rank;
                [self addCrad:crad];
            }
        }
        
    }
    
    return self;
    
}
@end
