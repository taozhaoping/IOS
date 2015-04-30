//
//  ViewController.m
//  纸牌游戏
//
//  Created by 陶照平 on 15-4-22.
//  Copyright (c) 2015年 陶照平. All rights reserved.
//

#import "ViewController.h"
#import "Crad.h"
#import "PlayingCard.h"
#import "PlayingCardDeck.h"

@interface ViewController ()

@property (nonatomic,strong) PlayingCardDeck* cardDeck;

@property (nonatomic,strong) UIButton* button;

@end

@implementation ViewController

@synthesize cardDeck = _cardDeck;


//viewDidLoad－加载视图
//
//viewWillAppear－UIViewController对象的视图即将加入窗口时调用；
//
//viewDidApper－UIViewController对象的视图已经加入到窗口时调用；
//
//viewWillDisappear－UIViewController对象的视图即将消失、被覆盖或是隐藏时调用；
//
//viewDidDisappear－UIViewController对象的视图已经消失、被覆盖或是隐藏时调用；
//
//viewVillUnload－当内存过低时，需要释放一些不需要使用的视图时，即将释放时调用；
//
//viewDidUnload－当内存过低，释放一些不需要的视图时调用。

-(void)viewDidLoad
{
    NSLog(@"加载视图");
}

-(void)viewWillAppear:(BOOL)animated
{
    NSLog(@"对象的视图即将加入窗口时调用");
}

-(void)viewDidAppear:(BOOL)animated
{
    NSLog(@"对象的视图已经加入到窗口时调用");
}

-(void)viewWillDisappear:(BOOL)animated
{
    NSLog(@"对象的视图即将消失、被覆盖或是隐藏时调用");
}

-(void)viewDidDisappear:(BOOL)animated
{
    NSLog(@"对象的视图已经消失、被覆盖或是隐藏时调用");
}

-(PlayingCardDeck *)cardDeck
{
    if (!_cardDeck) {
        _cardDeck = [[PlayingCardDeck alloc] init];
    }
    return _cardDeck;
}

+(void)flop : (UIButton *) sender
{
    UIImage *ui= [UIImage imageNamed:@"60-90"];
    [sender setBackgroundImage:ui forState:UIControlStateNormal];
    [sender setTitle: @""
            forState:UIControlStateNormal];
}

- (IBAction)touchCradButton:(UIButton *)sender {
    if([sender.currentTitle length])
    {
        [ViewController flop:sender];
    }else{
       Crad* crad = [self.cardDeck drawRandomCard];
        if (crad) {
            
        UIImage* ui= [UIImage imageNamed:@"image"];
        NSLog(@"%@",crad.contents);
        [sender setBackgroundImage:ui forState:UIControlStateNormal];
        [sender setTitle: crad.contents
                forState:UIControlStateNormal];
           
            if (_button && [_button.currentTitle isEqualToString:crad.contents] ) {
                
                [ViewController flop:_button];
            }else{
                [ViewController flop:_button];
                _button = sender;
            }
        
        
        }else
        {
            [sender setHidden:true];
        }
    }
}

//重新开始游戏
- (IBAction)clearGame:(UIButton *)sender {
    _cardDeck = nil;
    UIImage *ui= [UIImage imageNamed:@"60-90"];
    [_button setBackgroundImage:ui forState:UIControlStateNormal];
    [_button setTitle: @""
            forState:UIControlStateNormal];
    [_button setEnabled:true];

}
@end
