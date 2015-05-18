//
//  Nework.m
//  网络请求
//
//  Created by 尹亚涛 on 15/5/13.
//  Copyright (c) 2015年 陶照平. All rights reserved.
//

#import "Nework.h"
#import "AFURLSessionManager.h"

@implementation Nework

//网络请求
//NSString* kVideoURL = @"http://10.18.106.216:9090/GetAndroidDataService.svc/SoftCheckUpdate";
NSString* kVideoURL = @"http://10.18.106.216:9090/GetAndroidDataService.svc/LoginVerify";

//加密字符串
NSString* data;
NSDictionary* dic;

//下载地址
NSString* DownloadUrl = @"http://m.dahuatech.com:8010/dahuaMobile.apk";

-(void)test
{
    [self enctry];
    //[self content];
    //[self content1];
    [self content2];
    //[self checkVersion];
    //[self checkVersion1];
    //[self downLoad];
    
}

-(void)downLoad
{
    NSURL *url = [NSURL URLWithString:DownloadUrl];
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:url];
    
        NSData *data1 = [NSURLConnection sendSynchronousRequest:request returningResponse:nil error:nil];
    NSURL *filePath = [NSURL fileURLWithPath:@"file:/Users/yoon/Downloads/test.apk"];
    [data1 writeToFile:@"file:/Users/yoon/Downloads/test.apk" atomically:true];
    
    NSData* fileData = [NSURLConnection sendSynchronousRequest:request returningResponse:nil error:nil];
    
    [fileData writeToFile:@"file:/Users/yoon/Downloads/test.apk" atomically:YES];
    
    //    NSString* reult = [[NSString alloc] initWithData:data1 encoding:NSUTF8StringEncoding];
    //    NSLog(@"%@\n",reult);
    
    NSURLSessionConfiguration *configuration = [NSURLSessionConfiguration defaultSessionConfiguration];
    AFURLSessionManager *manager = [[AFURLSessionManager alloc] initWithSessionConfiguration:configuration];
    

    NSURLSessionUploadTask *uploadTask = [manager uploadTaskWithRequest:request fromFile:filePath progress:nil completionHandler:^(NSURLResponse *response, id responseObject, NSError *error) {
        if (error) {
            NSLog(@"Error: %@", error);
        } else {
            NSLog(@"Success: %@ %@", response, responseObject);
        }
    }];
    [uploadTask resume];
}

-(void)enctry
{
    // 4.创建参数字符串对象
    
     dic = [[NSDictionary alloc] initWithObjects:@[@"Taojian_9999",@"26078"] forKeys:@[@"FPassword",@"FItemNumber"]];
    NSData *postDatas = nil;
    postDatas = [NSJSONSerialization dataWithJSONObject:dic options:NSJSONWritingPrettyPrinted error:nil];
    NSString *str = [[NSString alloc] initWithData:postDatas encoding:NSUTF8StringEncoding];
    //加密
    data = [DESUtil encode:str];
    
}

-(void)checkVersion
{
    NSURL *url = [NSURL URLWithString:kVideoURL];
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:url];
    [request setHTTPMethod:@"GET"];
    
//    NSData *data1 = [NSURLConnection sendSynchronousRequest:request returningResponse:nil error:nil];
//    NSString* reult = [[NSString alloc] initWithData:data1 encoding:NSUTF8StringEncoding];
//    NSLog(@"%@\n",reult);
    
    NSURLSessionConfiguration *configuration = [NSURLSessionConfiguration defaultSessionConfiguration];
    AFURLSessionManager *manager = [[AFURLSessionManager alloc] initWithSessionConfiguration:configuration];
    

    
    NSURL *filePath = [NSURL fileURLWithPath:@"file://Users/yoon/Documents/test"];
    NSURLSessionUploadTask *uploadTask = [manager uploadTaskWithRequest:request fromFile:filePath progress:nil completionHandler:^(NSURLResponse *response, id responseObject, NSError *error) {
        if (error) {
            NSLog(@"Error: %@", error);
        } else {
            NSLog(@"Success: %@ %@", response, responseObject);
        }
    }];
    [uploadTask resume];
}

-(void)checkVersion1
{
    // 2.创建NSURL对象
    NSURL *url = [NSURL URLWithString:kVideoURL];
    // 3.创建请求
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:url];
    
    NSString* len = [NSString stringWithFormat:@"%lu",[data length]];
    
//    [request addValue:@"application/json"  forHTTPHeaderField:@"Accept"];
//    [request addValue:@"Mozilla/5.0"  forHTTPHeaderField:@"User-agent"];
//    [request addValue:@"ZH-CN,zh;q=0.8,en-US;q=0.6,en;q=0.4"  forHTTPHeaderField:@"Accept-Language"];
//    [request addValue:len  forHTTPHeaderField:@"Content-Length"];
    //[request addValue:@"application/json"  forHTTPHeaderField:@"Content-Type"];
    
    // 5.将字符串转为NSData对象
    
    
    // 6.设置请求体
    //[request setHTTPBody:json];
    
    // 7.设置请求方式
    [request setHTTPMethod:@"GET"];
    
    
    AFHTTPRequestOperation *operation = [[AFHTTPRequestOperation alloc] initWithRequest:request];
    
    [operation setCompletionBlockWithSuccess:^(AFHTTPRequestOperation *operation, id responseObject) {
        
        NSString *html = operation.responseString;
        
        NSData* data = [html dataUsingEncoding:NSUTF8StringEncoding];
        
        NSJSONSerialization* dict=[NSJSONSerialization  JSONObjectWithData:data options:NSJSONReadingMutableLeaves error:nil];
        
        NSDictionary *weatherDic = [NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableLeaves error:nil];
        id weatherInfo = [weatherDic objectForKey:@"Result"];
        
        if([weatherInfo isKindOfClass:[NSString class]])
        {
            NSLog(@"获取到的数据(NSString)Result为：%@",weatherInfo);
        }
        
        
        
        
    }failure:^(AFHTTPRequestOperation *operation, NSError *error) {
        
        NSLog(@"发生错误！%@",error);
        
    }];
    
    NSOperationQueue *queue = [[NSOperationQueue alloc] init];
    
    [queue addOperation:operation];
}

-(void)content2
{
    NSURL *url = [NSURL URLWithString:kVideoURL];
     NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:url];
    [request setHTTPMethod:@"POST"];
    
    NSMutableDictionary* mudic = [[NSMutableDictionary alloc] init];
    [mudic setValue:data forKey:@"jsonData"];
    
    NSData* json = [NSJSONSerialization dataWithJSONObject:mudic options:NSJSONWritingPrettyPrinted error:nil];

    NSString* len = [NSString stringWithFormat:@"%lu",[json length]];
    
    [request addValue:@"application/json"  forHTTPHeaderField:@"Accept"];
    [request addValue:@"Mozilla/5.0"  forHTTPHeaderField:@"User-agent"];
    [request addValue:@"ZH-CN,zh;q=0.8,en-US;q=0.6,en;q=0.4"  forHTTPHeaderField:@"Accept-Language"];
    [request addValue: len forHTTPHeaderField:@"Content-Length"];
    [request addValue:@"application/json"  forHTTPHeaderField:@"Content-Type"];

    
   
    
    [request setHTTPBody:json];
    // 创建同步链接
    NSData *data1 = [NSURLConnection sendSynchronousRequest:request returningResponse:nil error:nil];
    
    NSString* reult = [[NSString alloc] initWithData:data1 encoding:NSUTF8StringEncoding];
    NSLog(@"%@\n",reult);
    
}

-(void)content1
{
    
    // 2.创建NSURL对象
    NSURL *url = [NSURL URLWithString:kVideoURL];
    // 3.创建请求
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:url];
    
    NSString* len = [NSString stringWithFormat:@"%lu",[data length]];
    
    [request addValue:@"application/json"  forHTTPHeaderField:@"Accept"];
    [request addValue:@"Mozilla/5.0"  forHTTPHeaderField:@"User-agent"];
    [request addValue:@"ZH-CN,zh;q=0.8,en-US;q=0.6,en;q=0.4"  forHTTPHeaderField:@"Accept-Language"];
    [request addValue:len  forHTTPHeaderField:@"Content-Length"];
    [request addValue:@"application/json"  forHTTPHeaderField:@"Content-Type"];
    
    // 5.将字符串转为NSData对象
    NSMutableDictionary* mudic = [[NSMutableDictionary alloc] init];
    [mudic setValue:data forKey:@"jsonData"];
    NSData* json = [NSJSONSerialization dataWithJSONObject:mudic options:NSJSONWritingPrettyPrinted error:nil];

    // 6.设置请求体
    [request setHTTPBody:json];
    
    // 7.设置请求方式
    [request setHTTPMethod:@"POST"];
    
    // 创建同步链接
    //NSData *data1 = [NSURLConnection sendSynchronousRequest:request returningResponse:nil error:nil];
    
//    NSString* reult = [[NSString alloc] initWithData:data1 encoding:NSUTF8StringEncoding];
//    NSLog(@"%@\n",reult);

    AFHTTPRequestOperation *operation = [[AFHTTPRequestOperation alloc] initWithRequest:request];
    
    [operation setCompletionBlockWithSuccess:^(AFHTTPRequestOperation *operation, id responseObject) {
        
    NSString *html = operation.responseString;

        NSData* data = [html dataUsingEncoding:NSUTF8StringEncoding];
        
        NSJSONSerialization* dict=[NSJSONSerialization  JSONObjectWithData:data options:NSJSONReadingMutableLeaves error:nil];
        
        NSDictionary *weatherDic = [NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableLeaves error:nil];
        id weatherInfo = [weatherDic objectForKey:@"Result"];
        
        if([weatherInfo isKindOfClass:[NSString class]])
        {
             NSLog(@"获取到的数据(NSString)Result为：%@",weatherInfo);
        }
        
       

        
    }failure:^(AFHTTPRequestOperation *operation, NSError *error) {
            
    NSLog(@"发生错误！%@",error);
            
    }];
    
    NSOperationQueue *queue = [[NSOperationQueue alloc] init];
    
    [queue addOperation:operation];
    
}

-(void)content
{

    
    // 2.创建NSURL对象
    NSURL *url = [NSURL URLWithString:kVideoURL];
    // 3.创建请求
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:url];
    
    NSString* len = [NSString stringWithFormat:@"%lu",[data length]];
    
    [request addValue:@"application/json"  forHTTPHeaderField:@"Accept"];
    [request addValue:@"Mozilla/5.0"  forHTTPHeaderField:@"User-agent"];
    [request addValue:@"ZH-CN,zh;q=0.8,en-US;q=0.6,en;q=0.4"  forHTTPHeaderField:@"Accept-Language"];
    [request addValue:len  forHTTPHeaderField:@"Content-Length"];
    [request addValue:@"application/json"  forHTTPHeaderField:@"Content-Type"];
    
    // 5.将字符串转为NSData对象
    NSMutableDictionary* mudic = [[NSMutableDictionary alloc] init];
    [mudic setValue:data forKey:@"jsonData"];
    NSData* json = [NSJSONSerialization dataWithJSONObject:mudic options:NSJSONWritingPrettyPrinted error:nil];
    
    // 6.设置请求体
    //[request setHTTPBody:json];
    
    // 7.设置请求方式
    [request setHTTPMethod:@"POST"];
    
    // 创建同步链接
    //NSData *data1 = [NSURLConnection sendSynchronousRequest:request returningResponse:nil error:nil];
    
    //    NSString* reult = [[NSString alloc] initWithData:data1 encoding:NSUTF8StringEncoding];
    //    NSLog(@"%@\n",reult);
    
    AFHTTPRequestOperation *operation = [[AFHTTPRequestOperation alloc] initWithRequest:request];
    
    [operation setCompletionBlockWithSuccess:^(AFHTTPRequestOperation *operation, id responseObject) {
        
        NSString *html = operation.responseString;
        
        NSData* data = [html dataUsingEncoding:NSUTF8StringEncoding];
        
        NSJSONSerialization* dict=[NSJSONSerialization  JSONObjectWithData:data options:NSJSONReadingMutableLeaves error:nil];
        
        NSDictionary *weatherDic = [NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableLeaves error:nil];
        id weatherInfo = [weatherDic objectForKey:@"Result"];
        
        if([weatherInfo isKindOfClass:[NSString class]])
        {
            NSLog(@"获取到的数据(NSString)Result为：%@",weatherInfo);
        }
        
        
        
        
    }failure:^(AFHTTPRequestOperation *operation, NSError *error) {
        
        NSLog(@"发生错误！%@",error);
        
    }];
    
    NSOperationQueue *queue = [[NSOperationQueue alloc] init];
    
    [queue addOperation:operation];
}

@end

