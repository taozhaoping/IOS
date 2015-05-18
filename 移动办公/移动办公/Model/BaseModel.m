//
//  BaseModel.m
//  移动办公
//
//  Created by 尹亚涛 on 15/5/15.
//  Copyright (c) 2015年 陶照平. All rights reserved.
//

#import "BaseModel.h"

@interface BaseModel ()
@property (nonatomic,strong,readwrite) NSURL* url;
@property (nonatomic,strong,readwrite) NSMutableURLRequest* request;
@property (nonatomic,strong,readwrite) AFHTTPRequestOperation* afHttp;
@property(nonatomic,readwrite) NSString* httpMethod;
@property (nonatomic,readwrite) NSString* errorMessage;
@property (nonatomic,readwrite,getter=isComplete)BOOL complete;

@end

@implementation BaseModel

@synthesize http = _http;
@synthesize url = _url;
@synthesize request = _request;
@synthesize afHttp = _afHttp;
@synthesize errorMessage = _errorMessage;
@synthesize complete = _complete;


-(NSDictionary*)queryService
{
    
    [self.request setURL:self.url];
    [self.request setHTTPMethod:self.httpMethod];
    
    NSData* json = [self ObjectForDesAndReturnData:self.dictionary];
    
    NSString* len = [NSString stringWithFormat:@"%lu",[json length]];
    
    [self setRequestHead:self.request len:len];
    
    [self.request setHTTPBody:json];
    
    //判断是否需要同步
    if (self.isSendSynchronous) {
        // 创建同步链接
        NSData *reultData = [NSURLConnection sendSynchronousRequest:self.request returningResponse:nil error:nil];
        _dictionary = [NSJSONSerialization JSONObjectWithData:reultData options:NSJSONReadingMutableLeaves error:nil];
        [self AnalyticalResultsReturned];
    }else{
        // 创建不同步链接
        AFHTTPRequestOperation *operation = [[AFHTTPRequestOperation alloc] initWithRequest:self.request];
        [operation setCompletionBlockWithSuccess:^(AFHTTPRequestOperation *operation, id responseObject) {
            NSString *html = operation.responseString;
            NSData* data = [html dataUsingEncoding:NSUTF8StringEncoding];
            _dictionary = [NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableLeaves error:nil];
            
        } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
            
            NSLog(@"发生错误！%@",error);
            
        }];
        
        NSOperationQueue *queue = [[NSOperationQueue alloc] init];
        
        [queue addOperation:operation];
    }
    
    return _dictionary;
}

-(NSURL*)url
{
    if(!_url)
    {
        _url = [NSURL URLWithString: [self.http stringByAppendingString:self.serviceMethod]];
    }
    return _url;
}

-(NSMutableString*)http
{
    if(!_http)
    {
        _http = _URL_API_HOST_ANDROID_;
    }
    return _http;
}

-(NSMutableURLRequest*)request
{
    if(!_request)
    {
        _request = [[NSMutableURLRequest alloc] init];
    }
    return _request;
}

-(AFHTTPRequestOperation*)afHttp
{
    if(!_afHttp)
    {
        _afHttp = [[AFHTTPRequestOperation alloc] initWithRequest:self.request];
    }
    
    return _afHttp;
}

-(NSDictionary*)dictionary
{
    if (!_dictionary) {
        _dictionary = [self DictionaryForBean];
    }
    return _dictionary;
}

#pragma mark 解析返回结果
-(void)AnalyticalResultsReturned
{
    self.complete = true;
    NSString* success = [self.dictionary objectForKey:@"IsSuccess"];
    _success = [success integerValue]>0 ? true:false;
    if(!self.isSuccess)
    {
        self.errorMessage = [self.dictionary objectForKey:@"Result"];
    }
}

#pragma mark 转换字典为字符串，并加密
-(NSData*)ObjectForDesAndReturnData:(NSDictionary*) param
{
    NSData* data;
    
    NSData *postDatas = nil;
    
    //字典转换JSON字符串
    postDatas = [NSJSONSerialization dataWithJSONObject:param options:NSJSONWritingPrettyPrinted error:nil];
    NSString *str = [[NSString alloc] initWithData:postDatas encoding:NSUTF8StringEncoding];
    
    //加密
    NSString* encodeData = [DESUtil encode:str];
    
    //转换成指定格式的DATA数据
    NSMutableDictionary* mudic = [[NSMutableDictionary alloc] init];
    [mudic setValue:encodeData forKey:@"jsonData"];
    
    data = [NSJSONSerialization dataWithJSONObject:mudic options:NSJSONWritingPrettyPrinted error:nil];
    
    return data;
}

#pragma mark 这是头信息
-(void)setRequestHead:(NSMutableURLRequest *)request len : (NSString*) len
{
    [self.request addValue:@"application/json"  forHTTPHeaderField:@"Accept"];
    [self.request addValue:@"Mozilla/5.0"  forHTTPHeaderField:@"User-agent"];
    [self.request addValue:@"ZH-CN,zh;q=0.8,en-US;q=0.6,en;q=0.4"  forHTTPHeaderField:@"Accept-Language"];
    [self.request addValue:@"application/json"  forHTTPHeaderField:@"Content-Type"];
    [request addValue:len  forHTTPHeaderField:@"Content-Length"];
    
}

-(NSString*)httpMethod
{
    if (!_httpMethod) {
        _httpMethod = @"POST";
    }
    return _httpMethod;
}

@end
