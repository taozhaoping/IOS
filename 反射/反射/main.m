//
//  main.m
//  反射
//
//  Created by 陶照平 on 15-4-24.
//  Copyright (c) 2015年 陶照平. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "User.h"

int main(int argc, const char * argv[]) {
    @autoreleasepool {
        
        Class cla = NSClassFromString(@"User");
        
        User* user = [[cla alloc] init];
        
        user.user=@"陶照平";
        
        
        NSString* str = NSStringFromClass([user class]);
        NSLog(@"类名:%@",str);
        NSString* motch = NSStringFromSelector(@selector(cilck:));
        NSLog(@"方法反射:%@\n",motch);
        SEL cilck = NSSelectorFromString(motch);
        if ([user respondsToSelector:cilck])
        {
            [user performSelector:@selector(cilck:) withObject:@"反射！"];
            NSLog(@"dfds");
        }
        NSString* str1 = [NSString stringWithFormat:@"123456789"];
        
        NSRange range = [str1 rangeOfString:@"23"];
        NSLog(@"%zi\n",range.length);
        
        NSString* str2 = [NSString stringWithFormat:@"1"];
        bool isContain = [str1 containsString:str2];
        
        NSLog(@"%i\n",isContain);
        
    }
    return 0;
}
