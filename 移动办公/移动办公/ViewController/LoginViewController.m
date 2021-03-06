//
//  LoginViewController.m
//  移动办公
//
//  Created by 尹亚涛 on 15/5/6.
//  Copyright (c) 2015年 陶照平. All rights reserved.
//

#import "LoginViewController.h"
#import "MainViewController.h"
#import "UserInfoModel.h"

@interface LoginViewController ()

#pragma  mark - 变量声明
@property (nonatomic,strong,readwrite) NSString* userId;
@property (nonatomic,strong,readwrite) NSString* userName;
@property (nonatomic,strong,readwrite) NSString* userPassword;
@property (nonatomic,readwrite,getter=isSaveUser) BOOL saveUser;
@property (nonatomic,readwrite,getter=isGesture) BOOL gesture;
@property (weak, nonatomic) IBOutlet UITextField *userNameLable;
@property (weak, nonatomic) IBOutlet UITextField *userPasswordLable;

#pragma mark - outlet
@property (weak, nonatomic) IBOutlet UIButton *checkBoxButton;

@end

@implementation LoginViewController

@synthesize userId;
@synthesize userName;
@synthesize userPassword;
@synthesize saveUser;
@synthesize gesture;

#pragma mark - matchs
- (void)viewDidLoad {
    [super viewDidLoad];
    
}

-(void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:animated];
    [self initUser];
    [_userPasswordLable setSecureTextEntry:YES];
    //[self NSUserDefAddObject:@"26078" key:@"userId"];
}


-(BOOL)shouldPerformSegueWithIdentifier:(NSString *)identifier sender:(id)sender
{
    BOOL reult = false;
    userId = _userNameLable.text;
    userPassword = _userPasswordLable.text;
    self.userInfo.fItemNumber = userId;
    self.userInfo.fPassword = userPassword;

    [self.serviceUtil queryService:self.userInfo];
    //验证登陆信息
    if (self.userInfo.IsComplete&&self.userInfo.IsSuccess) {
        [self dismiss:sender];
            reult = true;
    }else{
        [self showError:self.userInfo.Result];
    }
    
    return reult;
}

-(void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
   
        NSLog(@"登陆\n");
        if([segue.identifier isEqualToString:@"main"])
        {
            if([segue.destinationViewController isKindOfClass:[MainViewController class]])
            {
                //MainViewController* mainViewController = (MainViewController *)segue.destinationViewController;
                //设置参数
            }
        }

    
}


-(void)initUser
{
    self.userNameLable.text = [self QueryNSUserDefToKey:@"userId"];
    NSString* imageName;
    if (self.isSaveUser)
    {
        imageName = [[NSString alloc] initWithFormat:@"checkbox_active"];
    }else
    {
        imageName = [[NSString alloc] initWithFormat:@"checkbox_back"];
    }
    UIImage* currentImage = [UIImage imageNamed:imageName];
    [currentImage setAccessibilityIdentifier:imageName];
    [_checkBoxButton setBackgroundImage:currentImage forState:UIControlStateNormal];
}

- (IBAction)login:(UIButton *)sender {
    [self.view endEditing:YES];
    [self showText:sender];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)changeSaveUser:(UIButton *)sender {
   [self setSaveUser: saveUser ? false:true];
    [self initUser];
}

-(UserInfoModel*)userInfo
{
    if (!_userInfo) {
        _userInfo = [UserInfoModel getInstance];
    }
    return _userInfo;
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
