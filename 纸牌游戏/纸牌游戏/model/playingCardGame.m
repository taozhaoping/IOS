//
//  playingCardGame.m
//  纸牌游戏
//
//  Created by 尹亚涛 on 15-4-29.
//  Copyright (c) 2015年 陶照平. All rights reserved.
//

#import "playingCardGame.h"
#import "PlayingCardDeck.h"
@interface playingCardGame ()
@property (nonatomic,readwrite) NSInteger sorce;
@property (nonatomic,strong) NSMutableArray* cradArray;
@end
@implementation playingCardGame

-(NSMutableArray *)cradArray
{
    if(!_cradArray) _cradArray = [[NSMutableArray alloc] init];
    return _cradArray;
}

-(instancetype)initWithPlayingCard:(NSUInteger)count
                              Deck:(Deck *) userDeck{
    self = [super init];
    if(self)
    {
        for (int i = 0; i < count; i++) {
            Crad* card = [userDeck drawRandomCard];
            if (card) {
                [self.cradArray addObject:card];
            }else{
                self = nil;
                break;
            }
        }
    }
    return self;
}

-(void)chooseCardIndex:(NSUInteger)index
{
    Crad* crad = [self.cradArray objectAtIndex:index];
    if (crad.isChosen)
    {
        [crad setChosen:false];
    }else{
        [crad setChosen:true];
    }
    
}

@end
