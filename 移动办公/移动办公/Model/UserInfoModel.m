//
//  UserInfoModel.m
//  移动办公
//
//  Created by 尹亚涛 on 15/5/15.
//  Copyright (c) 2015年 陶照平. All rights reserved.
//

#import "UserInfoModel.h"

@interface UserInfoModel ()


@end
@implementation UserInfoModel


-(void)initParame
{
     self.urlMethod = _LOGINACTIVITY_;
    self.sendSynchronous = true;
}

-(instancetype)initUserInfoModel:(NSString *)itemNumber password:(NSString *)password
{
    self = [super init];
    if (self) {
        self.fItemNumber = itemNumber;
        self.fPassword = password;
    }
    return self;
}

-(NSDictionary*)DictionaryForBean
{
    NSMutableDictionary* muDictionary = [[NSMutableDictionary alloc] init];
    
    [muDictionary setValue:self.fItemNumber forKey:@"FItemNumber"];
    [muDictionary setValue:self.fItemName forKey:@"FItemName"];
    [muDictionary setValue:self.fPassword forKey:@"FPassword"];
    
    return muDictionary;
}

-(NSDictionary*)dictionaryForSearchBean
{
    [self.searchDictionary setValue:self.fItemNumber forKey:@"FItemNumber"];
    [self.searchDictionary setValue:self.fPassword forKey:@"FPassword"];
    return self.searchDictionary;
}




@end
