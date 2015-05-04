//
//  playingCardGame.m
//  纸牌游戏
//
//  Created by 尹亚涛 on 15-4-29.
//  Copyright (c) 2015年 陶照平. All rights reserved.
//

#import "playingCardGame.h"
#import "PlayingCardDeck.h"
@interface PlayingCardGame ()
@property (nonatomic,readwrite) NSInteger sorce;
@property (nonatomic,strong) NSMutableArray* cradArray;
@end
@implementation PlayingCardGame

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
    if (!crad.isMatched) {
        if (crad.isChosen) {
            [crad setChosen:false];
        }else{
            for (Crad* otherCrad in self.cradArray) {
                if (otherCrad.isChosen && !otherCrad.isMatched) {
                    
                    int matchSource = [crad match:@[otherCrad]];
                    if (matchSource)
                    {
                        self.sorce +=matchSource*MATCH_BOUNS;
                        [otherCrad setMatched:true];
                        [crad setMatched:true];
                    }else{
                        self.sorce-=MISMATCH_PENALTY;
                        [otherCrad setChosen:false];
                    }
                    break;
                }
            }
            self.sorce -=COST_TO_CHOOSE;
            [crad setChosen:true];
        }
    }
    
}
static const int MISMATCH_PENALTY = 2;
static const int MATCH_BOUNS = 4;
static const int COST_TO_CHOOSE = 1;

-(Crad *)CardAtIndex:(NSUInteger)index
{
    return index < [self.cradArray count] ? [self.cradArray objectAtIndex:index] : nil;
}

@end
