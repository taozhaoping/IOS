//
//  ViewController.m
//  页面属性设置
//
//  Created by taozhaoping on 15/4/26.
//  Copyright (c) 2015年 taozhaoping. All rights reserved.
//

#import "ViewController.h"

@interface ViewController ()

@property (weak, nonatomic) IBOutlet UILabel *HeardTital;
@property (weak, nonatomic) IBOutlet UITextView *lineBody;
@property (weak, nonatomic) IBOutlet UIButton *lableButton;
@end

@implementation ViewController

-(void)viewDidLoad
{
    [super viewDidLoad];
    NSMutableAttributedString * title = [[NSMutableAttributedString alloc] initWithString:self.lableButton.currentTitle];
    [title addAttributes:@{NSStrokeWidthAttributeName:@3,
                          NSStrokeColorAttributeName:self.lableButton.tintColor} range:NSMakeRange(0, [title length])];
    [self.lableButton setAttributedTitle:title forState:UIControlStateNormal];
    
    
}

-(void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    //同步系统字体大小
    [self userPreefderChagnes];
    //注册广播（广播名称为 字体大小改变）
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(preefderFontChanges:) name:UIContentSizeCategoryDidChangeNotification object:nil];
}

-(void)viewWillDisappear:(BOOL)animated
{
    [[NSNotificationCenter defaultCenter] removeObserver:self name:UIContentSizeCategoryDidChangeNotification object:nil];
}

-(void)preefderFontChanges:(NSNotificationCenter *)nsNotificationCenter
{
    [self userPreefderChagnes];
}

-(void)userPreefderChagnes
{
    self.lineBody.font = [UIFont preferredFontForTextStyle:UIFontTextStyleBody];
    self.HeardTital.font = [UIFont preferredFontForTextStyle:UIFontTextStyleHeadline];
    
}

- (IBAction)changBody:(UIButton *)sender {
    
    [self updateUI];
}

-(void) updateUI
{
    NSDictionary* dic = @{NSStrokeWidthAttributeName:@-3,
                                 NSStrokeColorAttributeName:[UIColor blackColor]};
    [self.lineBody.textStorage addAttributes:dic range:self.lineBody.selectedRange];
    
}
- (IBAction)UnUpdateUI {
    [self noUpdateUI];
}

-(void) noUpdateUI
{
    [self.lineBody.textStorage removeAttribute:NSStrokeWidthAttributeName range:self.lineBody.selectedRange];
}

- (IBAction)changebodyselectincolormatchBackgroundofButton:(UIButton *)sender {
    NSRange range = [self.lineBody selectedRange];
    [self.lineBody.textStorage addAttribute:NSForegroundColorAttributeName value: sender.backgroundColor range:range ];
}

@end
