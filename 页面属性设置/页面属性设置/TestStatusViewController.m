//
//  TestStatusViewController.m
//  页面属性设置
//
//  Created by 尹亚涛 on 15/5/5.
//  Copyright (c) 2015年 taozhaoping. All rights reserved.
//

#import "TestStatusViewController.h"

@interface TestStatusViewController ()

@property (weak, nonatomic) IBOutlet UILabel *charateWidthStatus;
@property (weak, nonatomic) IBOutlet UILabel *charateColorStatus;
@end

@implementation TestStatusViewController

-(void)setTestToAnalyze:(NSAttributedString *)testToAnalyze
{
    _testToAnalyze = testToAnalyze;
    //不在当前视图更新文本的时候交由viewWillAPPEART进行gengxin
    if(self.view.window )[self updateUI];
}

-(void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    [self updateUI];
}

-(NSAttributedString *)charaterWithAttributed:(NSString *)attributedName
{
    NSMutableAttributedString* charater = [[NSMutableAttributedString alloc] init];
    NSUInteger index = 0;
    while (index < [self.testToAnalyze length]) {
        NSRange range;
        id value = [self.testToAnalyze attribute:attributedName atIndex:index effectiveRange:&range];
        if (value) {
            [charater appendAttributedString:[self.testToAnalyze attributedSubstringFromRange:range]];
            index = range.location + range.length;
        }else
        {
            index++;
        }
    }
    return charater;
}

-(void)updateUI
{
    NSAttributedString* charateWidth = [self charaterWithAttributed:NSStrokeWidthAttributeName];
    NSAttributedString* charateColor = [self charaterWithAttributed:NSForegroundColorAttributeName];
    
     self.charateWidthStatus.text = [NSString stringWithFormat:@"%zi:描边的字符串",[charateWidth length]];
    self.charateColorStatus.text = [NSString stringWithFormat:@"%zi:颜色的字符串", [charateColor length]];
                                    
}

@end
