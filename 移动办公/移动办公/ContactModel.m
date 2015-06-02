//
//  ContactModel.m
//  移动办公
//
//  Created by 陶照平 on 15/6/2.
//  Copyright (c) 2015年 陶照平. All rights reserved.
//

#import "ContactModel.h"

@implementation ContactModel

@synthesize Result = _Result;

-(void)initParame
{
    self.urlMethod = _CONTACTSDATA_;
    self.sendSynchronous = true;
}


-(NSDictionary*)DictionaryForBean
{
    NSMutableDictionary* muDictionary = [[NSMutableDictionary alloc] init];
    
    [muDictionary setValue:self.FItemNumber forKey:@"FItemNumber"];
    [muDictionary setValue:self.FItemName forKey:@"FItemName"];
    [muDictionary setValue:self.FEmail forKey:@"FPassword"];
    [muDictionary setValue:self.FDepartment forKey:@"FPassword"];
    [muDictionary setValue:self.FCornet forKey:@"FPassword"];
    return muDictionary;
}

-(NSDictionary*)dictionaryForSearchBean
{
    [self.searchDictionary setValue:self.FItemNumber forKey:@"FItemNumber"];
    [self.searchDictionary setValue:self.FItemName forKey:@"FItemName"];
    [self.searchDictionary setValue:self.FCornet forKey:@"FPassword"];
    return self.searchDictionary;
}

-(NSString *)Result
{
    if (_Result) {
        if ([_Result isEqualToString:@"1"]) {
            _Result = @"查询失败,至少输入两个字";
        }else if ([_Result isEqualToString:@"2"])
        {
            _Result = @"查询失败，未查到信息";
        }
    }
    return _Result;
}

@end
