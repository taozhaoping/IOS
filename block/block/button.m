//
//  button.m
//  block
//
//  Created by 陶照平 on 15-4-23.
//  Copyright (c) 2015年 陶照平. All rights reserved.
//

#import "button.h"

@implementation button



-(void)click
{
    _block(self);
}

-(void)click2
{
    printf("click\n");
}

@end
