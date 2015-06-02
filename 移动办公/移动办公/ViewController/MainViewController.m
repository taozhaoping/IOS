//
//  MainViewController.m
//  移动办公
//
//  Created by 尹亚涛 on 15/5/6.
//  Copyright (c) 2015年 陶照平. All rights reserved.
//

#import "MainViewController.h"

@interface MainViewController ()

@end

@implementation MainViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(ContactModel *)contactInfo
{
    if (!_contactInfo) {
        _contactInfo = [ContactModel getInstance];
        _contactInfo.pushViewDelegate = self;
    }
    return _contactInfo;
}

- (IBAction)onClick:(UIButton *)sender {
    [self startLoad];
    self.contactInfo.searchText = @"李娟";
    [self.serviceUtil queryListService:self.contactInfo];

}

#pragma mark 开始刷新刷新
-(void)pushView
{
    NSLog(@"%@",self.contactInfo.Result);
    self.textView.text = self.contactInfo.Result;
    NSLog(@"%s",__FUNCTION__);
    [self endLoad];
}

@end
