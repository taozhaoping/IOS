//
//  main.m
//  string
//
//  Created by 陶照平 on 15-4-23.
//  Copyright (c) 2015年 陶照平. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "linsert.h"

void CreateString()
{
    NSString* str = @"第一种方式";
    NSString* str1 = [NSString stringWithFormat:@"第二中方式 %@",str ];
    char* chr = "这是个char";
    NSString* strforchar = [NSString stringWithUTF8String:chr];
    NSLog(@"%@\n",strforchar);
    NSLog(@"%@",str1);
    
    //NSString* str2 = [NSString stringWithString:@"==="];
    NSString* path = @"/Users/yoon/Documents/test.ini1";
    NSError* err ;
    NSString* str3 = [NSString stringWithContentsOfFile:path encoding:NSUTF8StringEncoding error:&err];
    NSLog(@"path = %@ \n",str3);
    NSLog(@"error = %@\n",err);
    
    @try {
       NSException *exc = [[NSException alloc]initWithName:@"错误" reason:@"错误编码" userInfo:nil];
        @throw exc;
    }
    @catch (NSException *exception) {
        
        NSLog(@"错误信息%@",exception);
    }
    @finally {
        NSLog(@"=======================\n");
    }
    
    //直接读取url地址内容
    NSURL *url = [NSURL URLWithString:@"http:www.baidu.com"];
    NSString* urlForStr = [NSString stringWithContentsOfURL:url encoding:NSUTF8StringEncoding error:&err];
    NSLog(@"urlForStr = %@\n",urlForStr);
    NSLog(@"==========================\n");
    NSLog(@"methodForSelector = %@\n",str3);
}

void StringExport()
{
    NSString* str = @"字符串导出";
    NSError *error;
    //第一个参数：文件名 .第二个参数为原子性（true的时候会先写到临时文件，在写到目标文件）,第三个参数：错误信息
    //当文件不存在的时候，自动创建
    [str writeToFile:@"/Users/yoon/Documents/test.ini124" atomically:false encoding:NSUTF8StringEncoding error:&error];
    
    //NSURL *url = [NSURL URLWithString:@"file:\\Win260781517\测试"];
    //[str writeToURL:url atomically:true encoding:NSUTF8StringEncoding error:&error];
    
    NSArray* arry = @[@"属性1",@"属性2",@"属性3",@"属性4"];
    [arry writeToFile:@"/Users/yoon/Documents/config.ini" atomically:true];
    //NSURL* fileUrl = [NSURL URLWithString:@"/Users/yoon/Documents/config.ini"];
    NSArray *reultArrly = [NSArray arrayWithContentsOfFile:@"/Users/yoon/Documents/config.ini"];
    for (NSString *fileStr in reultArrly) {
        NSLog(@"fileStr = %@\n",fileStr);
    }
    NSComparisonResult str5 = [@"abc" compare: @"Abc"];
    if (str5 == NSOrderedSame) {
        NSLog(@"相等");
    }else if (str5 == NSOrderedAscending)
    {
        NSLog(@"降序");
        
    }else if (str5 == NSOrderedDescending)
    {
        NSLog(@"升序");
    }
    
//    NSString* st = @"ABCDEF";
//    [st insertValue:st atIndex:5 inPropertyWithKey:@"123"];
//    NSLog(@"insertValue = %@\n",st);
    
    NSMutableString *strmu = [NSMutableString stringWithFormat:@"可以变更的字符串 %@ !",@"（测试下）"];
    NSLog(@"NSMutableString = %@\n",strmu);
    //更改字符串
    [strmu setString:@"setString"];
    NSLog(@"NSMutableString = %@\n",strmu);
    
    [strmu insertString:@"insertString" atIndex:2];
     NSLog(@"insertString = %@\n",strmu);
    
    NSString* sub = [strmu substringToIndex:5];
    
    NSLog(@"substringToIndex = %@\n",sub);
    if (error)
    {
        NSLog(@"失败");
    }else
    {
        NSLog(@"成功");
    }
    
    //分割字符串，返回数组
    NSString* str6 = @"1/2/3/4/5/6";
    NSArray* list = [str6 componentsSeparatedByString:@"/"];
    for (NSString* listStr in list) {
        NSLog(@"componentsSeparatedByString = %@",listStr);
    }
    NSArray* pathArray = @[@"c:",@"我的文档",@"文件下载",@"谷歌"];
    NSString* pathComponents = [NSString pathWithComponents:pathArray];
    NSLog(@"pathWithComponents = %@",pathComponents);
    NSLog(@"判断是否为绝对路径 = %i",[pathComponents isAbsolutePath]);
    
    NSRange range;
    range.length=4;
    range.location=1;
    
    
    
    NSArray *aObject = [NSArray arrayWithObject:@"asd"];
    NSLog(@"aObject = %@\n",aObject[0]);
    
    aObject = [NSArray arrayWithObjects:@"1",@"2",@"4",@"5",@"6",@"7", nil];
    NSLog(@"aObjects = %lu\n",[aObject count]);
    
    //NSUInteger* index = [aObject indexOfObject:@"4"];
    NSUInteger index = [aObject indexOfObject:@"11" inRange:range];
    NSLog(@"index = %zi",index);
    
    aObject = [NSArray arrayWithObjects:[[linsert alloc] init],[[linsert alloc] init], nil];
    [aObject makeObjectsPerformSelector:@selector(click)];
    
    //调用前需要先判断对象是否包含了需要调用的方法
    [aObject makeObjectsPerformSelector:@selector(click1:) withObject:@"123"];
    
    [aObject enumerateObjectsUsingBlock:^(id obj, NSUInteger idx, BOOL *stop) {
        NSLog(@"%@ = %zi\n",obj,idx);
        
        if (idx == 0)
        {
            //控制是否停止遍历
            *stop = true;
        }
    }];
    //下面的遍历的时候，会调用上面enumerateObjectsUsingBlock 设置的 block
    for (linsert *xv in aObject) {
        NSLog(@"fileStr = %@\n",xv);
    }
    linsert* objNext;
    NSEnumerator* it = [aObject objectEnumerator];
    //反序迭代起;
    it = [aObject reverseObjectEnumerator];
    while (objNext = [it nextObject]) {
        NSLog(@"objectEnumerator = %@\n",objNext);
    }
}
                      
void test(NSString **str)
{
    *str = @"test";
}

int main(int argc, const char * argv[]) {
    @autoreleasepool {
        // insert code here...
        CreateString();
        NSString* s = @"111";
        test(&s);
        NSLog(@"test 是否改变了内容：%@",s);
        
        StringExport();
    }
    return 0;
}
