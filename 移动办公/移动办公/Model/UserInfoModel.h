//
//  UserInfoModel.h
//  移动办公
//
//  Created by 尹亚涛 on 15/5/15.
//  Copyright (c) 2015年 陶照平. All rights reserved.
//

#import "BaseModel.h"

@interface UserInfoModel : BaseModel

@property (nonatomic,weak) NSString* fItemNumber;

@property (nonatomic,weak) NSString* fItemName;

@property (nonatomic,weak) NSString* fPassword;

@property (nonatomic) BOOL fIsRememberMe;

-(instancetype)initUserInfoModel:(NSString*) itemNumber password : (NSString*) password;

@end
