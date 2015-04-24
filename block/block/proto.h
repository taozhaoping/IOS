//  proto.h
//  block
//
//  Created by 陶照平 on 15-4-23.
//  Copyright (c) 2015年 陶照平. All rights reserved.
//

@protocol Linster <NSObject>

@required; //必须实现
-(void)click;

@optional;
-(void)click1;

@end



@protocol Linster2 <NSObject>

@optional;  //可实现也可不实现
-(void)click;

@end