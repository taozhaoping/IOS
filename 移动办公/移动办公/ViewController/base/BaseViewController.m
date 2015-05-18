//
//  BaseViewController.m
//  移动办公
//
//  Created by 尹亚涛 on 15/5/6.
//  Copyright (c) 2015年 陶照平. All rights reserved.
//

#import "BaseViewController.h"

@interface BaseViewController ()

@end

@implementation BaseViewController

-(NSUserDefaults *)userDef
{
    if(!_userDef)
    {
        _userDef = [NSUserDefaults standardUserDefaults];
    }
    return _userDef;
}

-(void)NSUserDefAddObject:(NSString *)obj key:(NSString *)key
{
    [self.userDef setObject:obj forKey:key];
    [self.userDef synchronize];
}

-(void)NSUserDefAddDictionary:(NSDictionary *) dictionary
{
    for (id key in dictionary) {
        if ([key isKindOfClass:[NSString class]]) {
            [self.userDef setObject:[dictionary objectForKey:(NSString *) key] forKey:key];
        }
    }
}

-(NSString *) QueryNSUserDefToKey:(NSString *)key
{
    return [self.userDef objectForKey:key];
}

-(void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)show:(id)sender {
    //  [SVProgressHUD show];
    //SVProgressHUDMaskType 设置显示的样式
    [SVProgressHUD showWithMaskType:SVProgressHUDMaskTypeBlack];
    [self performSelector:@selector(dismiss:) withObject:nil afterDelay:3];
}

- (IBAction)showText:(id)sender {
    [SVProgressHUD showWithStatus:@"加载中，请稍后。。。"];
    [self performSelector:@selector(dismiss:) withObject:nil afterDelay:3];
}

- (IBAction)showprogress:(id)sender {
    [SVProgressHUD showProgress:0 status:@"加载中"];
    [self performSelector:@selector(increateProgress) withObject:nil afterDelay:0.3];
}


static float progressValue = 0.0f;
- (void)increateProgress
{
    progressValue += 0.1;
    [SVProgressHUD showProgress:progressValue status:@"加载中"];
    if (progressValue < 1) {
        [self performSelector:@selector(increateProgress) withObject:nil afterDelay:0.3];
    }else{
        [self performSelector:@selector(dismiss:) withObject:nil afterDelay:0.4];
    }
    
}

- (IBAction)dismiss:(id)sender {
    [SVProgressHUD dismiss];
}

- (IBAction)showSuccess:(id)sender {
    [SVProgressHUD showSuccessWithStatus:@"success"];
    [self performSelector:@selector(dismiss:) withObject:nil afterDelay:3];
}

- (IBAction)showError:(NSString* )errorMessage {
    [SVProgressHUD showErrorWithStatus:errorMessage];
    [self performSelector:@selector(dismiss:) withObject:nil afterDelay:3];
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
