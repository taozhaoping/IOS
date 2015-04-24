//
//  button.h
//  block
//
//  Created by 陶照平 on 15-4-23.
//  Copyright (c) 2015年 陶照平. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "proto.h"
@class button;
typedef void (^MyButton) (button*);

@interface button : NSObject <Linster>

@property (nonatomic,assign) MyButton block;


@end
