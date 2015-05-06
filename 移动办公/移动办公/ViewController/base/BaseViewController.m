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

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
