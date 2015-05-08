//
//  LoginViewController.m
//  移动办公
//
//  Created by 尹亚涛 on 15/5/6.
//  Copyright (c) 2015年 陶照平. All rights reserved.
//

#import "LoginViewController.h"
#import "MainViewController.h"
#import "DESUtil.h"

@interface LoginViewController ()

#pragma  mark - 变量声明
@property (nonatomic,strong,readwrite) NSString* userId;
@property (nonatomic,strong,readwrite) NSString* userName;
@property (nonatomic,strong,readwrite) NSString* userPassword;
@property (nonatomic,readwrite,getter=isSaveUser) BOOL saveUser;
@property (nonatomic,readwrite,getter=isGesture) BOOL gesture;
@property (weak, nonatomic) IBOutlet UITextField *userNameLable;

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
    // Do any additional setup after loading the view.
    NSString* st = @"{\"FPassword\":\"Taojian_9999\",\"FItemNumber\":\"26078\"}";
    st =@"{'IsSuccess':true,'Result':'[{'FModel':'DH-DSS-T8100-PRO/2000','FName':'大华交通综合管控平台软件（2000路）DH-DSS-T8100-PRO/2000','FFirstLine':'软件','FSecLine':'软件产品','FStandPrice':'价格权限规则制定中，暂无法查询，请您谅解！'}]'}";
    NSString* str = [DESUtil encode: st key:@"02adfd5a"];
     NSLog(@"加密1: %@ \n", str);
    NSString* str1 = [DESUtil decode:@"73e980ed734ee24d4054c9993b2a58d2e3723ccd2ac7916facbc09537744aedfac3a45e8e97d3015a04bb32ec41cfff10772165bb018dbc4479ec97b6911ef833ff9a697853ae2c4b6f2d0d49cc0a782172ea80e7a3cb2210c21caff4e39c3b654d47c313e40f205b14f9c11b4de8bb68c76a2b942ec4460cd4bf69e873e52da3a51bcd6f64cdbd14326dd6af7403f1ab02a7687c5ae39066652508221faf417b154269e051ab3bd70fa0cc6aa969527bdd5baa598e4fa87ba65e90207a939d180226951fff7df1a53b3490f819d2dfb119d9f2d86be0b0595799e793564b35deb6ac7b9f1e8fdef73582c6e6cee2f5ff2ccb1b72697e5085b19e5738228a9a3a8b45821e7ea8eba90e083a40d8a295b3049e13be9de9950e5e0a54f1f1fe7bd103ff08730d4a546fb9d49ec6291b543edeb0d70dabf1b830f669d9fe43f816d33532869b401dddbeb0624440d3821e400194bd0dc03c32fa50627c0404f60b177948bcfb9e95182529a452e74ce8df48c700cf8cf27a84236bef4957d5542b9eb5a7ca5eb3c3a5347a2b1e599f9ae09525c799b9bbc35925e1577fae5095379d707c12a54d8409eb067a19857cea3a58373b2f707a94a83a81fbedc8790b8616bb830c953a1dd1c730a7ebe08ac40a9" key:@"02adfd5a"];
    NSLog(@"解密1: %@ \n", str1);
    
    NSString* str2 = [DESUtil decode:@"920867b735326b72acbbc88a73c500fc511e61bbc0642c86c7e98811a9e379c58576cf45335f20d9fa5ac93d092552483fdf744968843917" key:@"02adfd5a"];
     NSLog(@"解密2: %@ \n", str2);
    NSString *hexString = @"920867b735326b72acbbc88a73c500fc511e61bbc0642c86c7e98811a9e379c58576cf45335f20d9fa5ac93d092552483fdf744968843917"; //16进制字符串
//    int j=0;
//    Byte bytes[128]; ///3ds key的Byte 数组， 128位
//    for (int i = 0; i < [hexString length]; i++) {
//        int int_ch; /// 两位16进制数转化后的10进制数
//        unichar hex_char1 = [hexString characterAtIndex:i]; ////两位16进制数中的第一位(高位*16)
//        int int_ch1;
//        if (hex_char1 >= '0' && hex_char1 <='9')
//            int_ch1 = (hex_char1-48)*16; //// 0 的Ascll - 48
//        else if (hex_char1 >= 'A' && hex_char1 <='F')
//            int_ch1 = (hex_char1-55)*16; //// A 的Ascll - 65
//        else
//            int_ch1 = (hex_char1-87)*16; //// a 的Ascll - 97
//        i++;
//        unichar hex_char2 = [hexString characterAtIndex:i]; ///两位16进制数中的第二位(低位)
//        int int_ch2;
//        if (hex_char2 >= '0' && hex_char2 <='9')
//            int_ch2 = (hex_char2-48); //// 0 的Ascll - 48
//        else if (hex_char1 >= 'A' && hex_char1 <='F')
//            int_ch2 = hex_char2-55; //// A 的Ascll - 65
//        else
//            int_ch2 = hex_char2-87; //// a 的Ascll - 97
//        int_ch = int_ch1+int_ch2;
//        NSLog(@"int_ch=%d",int_ch);
//        bytes[j] = int_ch; ///将转化后的数放入Byte数组里
//        j++;
//    }
//    NSData *newData = [[NSData alloc] initWithBytes:bytes length:128];
//    NSLog(@"newData=%@",newData);
    NSLog(@"解密1: %@ \n", str2);
    
    NSString* str3 = [DESUtil decode:@"920867b735326b72acbbc88a73c500fc511e61bbc0642c86c7e98811a9e379c58576cf45335f20d9fa5ac93d092552483fdf744968843917" key:@"02adfd5a"];
    NSLog(@"解密1: %@ \n", str3);
    
}

-(void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:animated];
    [self initUser];
    //[self NSUserDefAddObject:@"26078" key:@"userId"];
}

-(BOOL)shouldPerformSegueWithIdentifier:(NSString *)identifier sender:(id)sender
{
    BOOL reult = false;
    //验证登陆信息
    if(self.isSaveUser)
    {
        reult = true;
    }else{
        NSLog(@"不允许跳转！\n");
       
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
                MainViewController* mainViewController = (MainViewController *)segue.destinationViewController;
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
       NSLog(@"验证\n");
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)changeSaveUser:(UIButton *)sender {
   [self setSaveUser: saveUser ? false:true];
    [self initUser];
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
