//
//  PlayingCard.m
//  纸牌游戏
//
//  Created by 陶照平 on 15-4-22.
//  Copyright (c) 2015年 陶照平. All rights reserved.
//

#import "PlayingCard.h"

@implementation PlayingCard

+(NSArray *) colorCrad
{
    NSArray *stringArry= @[@"♥️",@"♣️",@"♠️",@"♦️"];
    return stringArry;
}

+(NSArray *)rankStrings
{
    return @[@"?",@"A",@"2",@"3",@"4",@"5",@"6",@"7",@"8",@"9",@"10",@"J",@"Q",@"K"];
}

@synthesize suit = _suit;
@synthesize rank = _rank;

-(void)setSuit:(NSString *)suit
{
    if ([[PlayingCard colorCrad] containsObject:suit]) {
        _suit = suit;
    }
}

-(NSString *)suit
{
    
    return _suit ? _suit : @"?";
}

+(NSUInteger )maxRank
{
    return [[PlayingCard rankStrings] count];
}

-(void)setRank:(NSUInteger)rank
{
    if ([PlayingCard maxRank] >= rank) {
        _rank =rank;
    }
    
}

-(NSString *) contents
{
    
    return [self.suit stringByAppendingFormat:@"%@",[PlayingCard rankStrings][self.rank]];
}

@end
