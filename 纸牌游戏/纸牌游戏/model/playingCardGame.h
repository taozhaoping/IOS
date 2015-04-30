//
//  playingCardGame.h
//  纸牌游戏
//
//  Created by 尹亚涛 on 15-4-29.
//  Copyright (c) 2015年 陶照平. All rights reserved.
//

#import "PlayingCard.h"
#import "Deck.h"

@interface playingCardGame : PlayingCard

@property (nonatomic,readonly) NSInteger sorce;

-(instancetype)initWithPlayingCard :(NSUInteger) count Deck:(Deck *) userDeck;

#pragma mark 选择一张牌
-(void)chooseCardIndex:(NSUInteger) index;

#pragma mark 获取一张指定下标的牌
-(Crad *) CardAtIndex:(NSUInteger) index;


@end
