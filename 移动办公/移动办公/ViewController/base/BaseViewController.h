//
//  BaseViewController.h
//  移动办公
//
//  Created by 尹亚涛 on 15/5/6.
//  Copyright (c) 2015年 陶照平. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "SVProgressHUD.h"
#import "ServiceUtil.h"
#import "PushViewDelegate.h"

@interface BaseViewController :UIViewController<PushViewDelegate>

#pragma mark - 变量声明
@property (nonatomic,strong,readonly) NSString* userId;
@property (nonatomic,strong,readonly) NSString* userName;
@property (nonatomic,strong,readonly) NSString* userPassword;
@property (nonatomic,readonly,getter=isSaveUser) BOOL saveUser;
@property (nonatomic,readonly,getter=isGesture) BOOL gesture;

#pragma mark 网络请求接口
@property(nonatomic,strong)ServiceUtil* serviceUtil;

@property (nonatomic,strong) NSUserDefaults* userDef;



#pragma mark - 公共方法
#pragma mark 添加全局配置
-(void)NSUserDefAddObject:(NSString *) obj key:(NSString *) key;
-(void)NSUserDefAddDictionary:(NSDictionary *) dictionary;

#pragma mark 获取指定KEY的配置信息
-(NSString *)QueryNSUserDefToKey:(NSString *)key;


#pragma mark - 提示框HUD
- (IBAction)show:(id)sender ;

- (IBAction)showText:(id)sender;

- (IBAction)showprogress:(id)sender;

- (void)increateProgress;

- (IBAction)dismiss:(id)sender;

- (IBAction)showSuccess:(id)sender;

- (IBAction)showError:(NSString* )errorMessage;
@end
