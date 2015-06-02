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
@end

@implementation BaseModel

@synthesize urlMethod = _urlMethod;

-(instancetype)initWithObject
{
    self = [super init];
    return self;
}

+(instancetype)getInstance
{
    return [[self alloc]initWithObject];
}

-(NSString*)urlMethod
{
    if (!_urlMethod) {
        _urlMethod = _LOGINACTIVITY_;
    }
    return _urlMethod;
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
