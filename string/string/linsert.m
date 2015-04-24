//
//  linsert.m
//  string
//
//  Created by 陶照平 on 15-4-23.
//  Copyright (c) 2015年 陶照平. All rights reserved.
//

#import "linsert.h"
#import <Foundation/Foundation.h>

@implementation linsert

-(void)click
{
    NSLog(@"%@->调用了click",self);
}

-(void)click1:(NSString*)a
{
    NSLog(@"%@->调用了click1 = %@",self,a);
}
@end
