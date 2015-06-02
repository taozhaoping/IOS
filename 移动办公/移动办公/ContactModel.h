//
//  ContactModel.h
//  移动办公
//
//  Created by 陶照平 on 15/6/2.
//  Copyright (c) 2015年 陶照平. All rights reserved.
//

#import "BaseModel.h"

@interface ContactModel : BaseModel

#pragma mark 工号
@property(nonatomic,strong)NSString* FItemNumber;

#pragma mark 姓名
@property(nonatomic,strong)NSString* FItemName;

#pragma mark 邮件
@property(nonatomic,strong)NSString* FEmail;

#pragma mark 短号
@property(nonatomic,strong)NSString* FCornet;

#pragma 部门
@property(nonatomic,strong)NSString* FDepartment;
@end
