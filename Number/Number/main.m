//
//  main.m
//  Number
//
//  Created by 陶照平 on 15-4-24.
//  Copyright (c) 2015年 陶照平. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "User.h"

typedef struct {
    NSUInteger year;
    NSUInteger motch;
    NSUInteger day;
} MyDate;
int main(int argc, const char * argv[]) {
    @autoreleasepool {
        NSNumber* number  = @(4);
        NSLog(@"number  = %@",number);
        int i = 3;
        NSNumber* number1 = [NSNumber numberWithInt:i];
        NSLog(@"number  = %@",number1);
        NSNumber* number2 = [NSNumber numberWithLong:4L];
        NSLog(@"number  = %@",number2);
        
        MyDate date = {2015,3,4};
        //根据结构体生成指定的字符串类型；
        char* type = @encode(MyDate);
        NSValue* value = [NSValue value:&date withObjCType:type];
        MyDate date1;
        [value getValue:&date1];
        NSLog(@"year =%zi , motch = %zi, day = %zi\n",date1.year,date1.motch,date1.day);
        
        User* user = [[User alloc] init];
        
        //不比较继承关系
        NSLog(@"比较=%i\n",[user isMemberOfClass:[NSObject class]]);
        
        //比较继承关系
        NSLog(@"比较=%i\n",[user isKindOfClass:[NSObject class]]);
        
        NSLog(@"%i",[NSString class] == [NSNumber class]);
        
        //NSString* str1 = [NSString stringWithFormat:@"123456789"];
        NSMutableString* str3 = [NSMutableString stringWithString:@"123456789"];
        NSMutableString* str2 = [NSMutableString stringWithFormat:@"1"];
        bool isContain = [str3 containsString: str2];
 NSLog(@"%i\n",isContain);
    }
    return 0;
}
