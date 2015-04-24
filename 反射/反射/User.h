//
//  User.h
//  反射
//
//  Created by 陶照平 on 15-4-24.
//  Copyright (c) 2015年 陶照平. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface User : NSObject

@property (nonatomic,strong) NSString* user;

@property (nonatomic,strong) NSString* password;


-(void) test;

-(void)cilck :(NSString*) str;
@end
