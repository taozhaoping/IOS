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
@end

@implementation ViewController


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
