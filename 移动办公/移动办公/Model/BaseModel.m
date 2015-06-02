//
//  BaseModel.m
//  移动办公
//
//  Created by 尹亚涛 on 15/5/15.
//  Copyright (c) 2015年 陶照平. All rights reserved.
//

#import "BaseModel.h"

@interface BaseModel ()
@property (nonatomic,readwrite,getter=IsComplete)BOOL complete;
@property (nonatomic,copy,readwrite)NSString* errorCode;
@property (nonatomic,copy,readwrite) NSString* errorMessage;
@end

@implementation BaseModel

@synthesize urlMethod = _urlMethod;

-(instancetype)initWithObject
{
    self = [super init];
    if (self) {
        [self initParame];
    }
    return self;
}

+(instancetype)getInstance
{
    return [[self alloc]initWithObject];
}

-(void)initParame
{
    
}

-(NSString *)errorCode
{
    if (!_errorCode) {
        _errorCode = [NSString stringWithFormat:@"%li",self.err.code ];
    }
    return _errorCode;
}

-(NSString*)errorMessage
{
    if (!_errorMessage) {
        _errorMessage = [NSString stringWithFormat:@"%@",[self.err.userInfo objectForKey:@"NSLocalizedDescription"]];
    }
    return _errorMessage;
}


-(NSDictionary*)DictionaryForJson:(NSString *) json
{
    
    return self.dictionary;
}

-(NSString*)JsonForDictionary:(NSDictionary*) dictionary
{
    
    return self.json;
}

-(NSDictionary*)DictionaryForBean
{
    
    return self.dictionary;
}

-(NSString*)JsonForDictionary
{
    
    return self.json;
}

-(NSString*)jsonForSearchBean
{
    
    return self.json;
}

-(void)beanForDictionary:(NSDictionary *)dict
{
    self.complete = true;
    [self setValuesForKeysWithDictionary:dict];
}

-(NSDictionary*)dictionaryForSearchBean
{
    return self.searchDictionary;
}

-(NSMutableDictionary*)searchDictionary
{
    if(!_searchDictionary)
    {
        _searchDictionary = [[NSMutableDictionary alloc] init];
    }
    
    return _searchDictionary;
}

@end
