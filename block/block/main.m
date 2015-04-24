//
//  main.m
//  block
//
//  Created by 陶照平 on 15-4-23.
//  Copyright (c) 2015年 陶照平. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "button.h"

@protocol Linster;

typedef int (^MySum) (int,int);

typedef int (*Sumq) (int,int);

int test ()
{
    //报错block不允许修改,需要加入__block才允许修改
    
    __block int x = 10;
    //block
    int (^xsum)(int,int) = ^(int a,int b)
    {
        
        x++;
        return a+b;
    };
    
    printf("%i \n",xsum(3,4));
    
    int (^test)() = ^()
    {
        return 0;
    };
    
    
    
    //*sumx(2,5);
    
    
    
    
    MySum mySum = ^(int a,int b)
    {
        return a*b;
    };
    printf("a*b = %i\n",mySum(5,4));
    
    //®int (*sump) (int,int) = mySum;
    return 0;
}


int main(int argc, const char * argv[]) {
    @autoreleasepool {
        test();
        button* butt = [[button alloc] init];
        butt.block = ^(button* bu)
        {
            printf("@调用了 =%p\n",bu);
        };
        
        //判断是否准守了linster2协议。不判断是否实现协议方法
        if ([butt conformsToProtocol:@protocol(Linster)])
        {
            [butt click];
        }
        
        if ([butt respondsToSelector:@selector(click)]) {
            printf("@判断是否实现了click这个方法");
        }
    }
    return 0;
}
