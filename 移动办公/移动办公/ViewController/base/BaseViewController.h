//
//  BaseViewController.h
//  移动办公
//
//  Created by 尹亚涛 on 15/5/6.
//  Copyright (c) 2015年 陶照平. All rights reserved.
//

#import <UIKit/UIKit.h>


@interface BaseViewController : UIViewController

#pragma mark - 变量声明
@property (nonatomic,strong,readonly) NSString* userId;
@property (nonatomic,strong,readonly) NSString* userName;
@property (nonatomic,strong,readonly) NSString* userPassword;
@property (nonatomic,readonly,getter=isSaveUser) BOOL saveUser;
@property (nonatomic,readonly,getter=isGesture) BOOL gesture;


@property (nonatomic,strong) NSUserDefaults* userDef;



#pragma mark - 公共方法
#pragma mark 添加全局配置
-(void)NSUserDefAddObject:(NSString *) obj key:(NSString *) key;
-(void)NSUserDefAddDictionary:(NSDictionary *) dictionary;

#pragma mark 获取指定KEY的配置信息
-(NSString *)QueryNSUserDefToKey:(NSString *)key;
@end
