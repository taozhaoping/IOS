//
//  ServiceUtil.m
//  移动办公
//
//  Created by 尹亚涛 on 15/5/14.
//  Copyright (c) 2015年 陶照平. All rights reserved.
//

#import "ServiceUtil.h"

@interface ServiceUtil ()
@property (nonatomic,strong,readwrite) NSMutableString* http;
@property (nonatomic,strong,readwrite) NSMutableURLRequest* request;
@property (nonatomic,strong,readwrite) AFHTTPRequestOperation* afHttp;
@end

@implementation ServiceUtil

@synthesize http = _http;
@synthesize request = _request;
@synthesize afHttp = _afHttp;

-(instancetype)initWithService
{
    self = [super init];
    
    return self;
}

+(instancetype)getInstance
{
    return [[self alloc] initWithService];
}

/**服务器地址*/
-(NSMutableString*)http
{
    if(!_http)
    {
        _http = [NSMutableString stringWithString:_URL_API_HOST_ANDROID_ ];
    }
    return _http;
}

-(NSString*)httpMethod
{
    if (!_httpMethod) {
        _httpMethod = _POST_;
    }
    return _httpMethod;
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

-(void)queryService:(BaseModel<AnalyticalDelegate>*)baseModel
{

    NSURL *url = [NSURL URLWithString:[self.http stringByAppendingString:baseModel.urlMethod]];
    
    [self.request setURL:url];
    [self.request setHTTPMethod:self.httpMethod];
    
    NSData* json = [self ObjectForDesAndReturnData:[baseModel dictionaryForSearchBean]];
    
    NSString* len = [NSString stringWithFormat:@"%lu",[json length]];
    
    [self setRequestHead:self.request len:len];
    
    [self.request setHTTPBody:json];
    // 创建同步链接
    NSData *reultData = [NSURLConnection sendSynchronousRequest:self.request returningResponse:nil error:nil];
   NSDictionary* dict = [NSJSONSerialization JSONObjectWithData:reultData options:NSJSONReadingMutableLeaves error:nil];
    [baseModel beanForDictionary:dict];
}

-(void)queryListService:(BaseModel *)baseModel
{
    NSURL *url = [NSURL URLWithString:[self.http stringByAppendingString:baseModel.urlMethod]];
    
    [self.request setURL:url];
    [self.request setHTTPMethod:self.httpMethod];
    
    NSData* json = nil;
    //判断是传输对象字典还是传输查询文本
    if([baseModel.searchText isEqualToString:@""])
    {
        json = [self ObjectForDesAndReturnData:[baseModel dictionaryForSearchBean]];
    }else{
        json = [self stringForDesAndReturnData:baseModel.searchText];
    }
    
    NSString* len = [NSString stringWithFormat:@"%lu",[json length]];
    
    [self setRequestHead:self.request len:len];
    
    [self.request setHTTPBody:json];

    // 创建不同步链接
    AFHTTPRequestOperation *operation = [[AFHTTPRequestOperation alloc] initWithRequest:self.request];
    [operation setCompletionBlockWithSuccess:^(AFHTTPRequestOperation *operation, id responseObject) {
        NSString *html = operation.responseString;
        NSData* data = [html dataUsingEncoding:NSUTF8StringEncoding];
        [baseModel beanForDictionary:[NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableLeaves error:nil]];
        [baseModel.pushViewDelegate pushView];
    } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
        baseModel.err = error;
        [baseModel.pushViewDelegate reultError:baseModel];
    }];
    
    NSOperationQueue *queue = [[NSOperationQueue alloc] init];
    
    [queue addOperation:operation];

    
}

#pragma mark 转换字典为字符串，并加密
-(NSData*)ObjectForDesAndReturnData:(NSDictionary*) param
{
   
    //字典转换JSON字符串
    NSData* postDatas = [NSJSONSerialization dataWithJSONObject:param options:NSJSONWritingPrettyPrinted error:nil];
    NSString *str = [[NSString alloc] initWithData:postDatas encoding:NSUTF8StringEncoding];
    
    return [self stringForDesAndReturnData:str];
}

-(NSData*)stringForDesAndReturnData:(NSString*)str
{
    //加密
    NSString* encodeData = [DESUtil encode:str];
    
    //转换成指定格式的DATA数据
    NSMutableDictionary* mudic = [[NSMutableDictionary alloc] init];
    [mudic setValue:encodeData forKey:@"jsonData"];
    
    return [NSJSONSerialization dataWithJSONObject:mudic options:NSJSONWritingPrettyPrinted error:nil];
    
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

@end
