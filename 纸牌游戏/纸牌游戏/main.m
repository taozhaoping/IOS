//
//  main.m
//  纸牌游戏
//
//  Created by 陶照平 on 15-4-22.
//  Copyright (c) 2015年 陶照平. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "AppDelegate.h"

int main(int argc, char * argv[]) {
    @autoreleasepool {
        NSString* str1 = [NSString stringWithFormat:@"123456789"];
        
        NSRange range = [str1 rangeOfString:@"23"];
        NSLog(@"%zi\n",range.length);
        
        NSString* str2 = [NSString stringWithFormat:@"1"];
        bool isContain = [str1 containsString:str2];
        
        NSLog(@"%i\n",isContain);

        //return 0;
        return UIApplicationMain(argc, argv, nil, NSStringFromClass([AppDelegate class]));
    }
}
