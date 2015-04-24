//
//  main.m
//  字典
//
//  Created by 陶照平 on 15-4-23.
//  Copyright (c) 2015年 陶照平. All rights reserved.
//

#import <Foundation/Foundation.h>

int main(int argc, const char * argv[]) {
    @autoreleasepool {
        NSDictionary* dic = [NSDictionary dictionaryWithObject:@"A" forKey:@"1"];
        NSString* str = [dic objectForKey:@"1"];
        NSLog(@"%@\n",str);
        
        NSArray* keyArray = @[@"A",@"B",@"C",@"D",@"E",@"F",@"G",@"H"];
        NSArray* valueArray = @[@"1",@"2",@"3",@"4",@"5",@"6",@"7",@"8"];
        
        dic = [NSDictionary dictionaryWithObjects:valueArray forKeys:keyArray];
        NSEnumerator* itArray = [dic keyEnumerator];
        NSString* key;
        while (key =[itArray nextObject]) {
            NSLog(@"key  %@\n",[dic objectForKey:key]);
        }
    }
    return 0;
}
