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
#import "PlayingCardGame.h"
#import "PlayingCardDeck.h"

@interface ViewController ()

@property (nonatomic,strong) UIButton* button;

@property (strong, nonatomic) IBOutletCollection(UIButton) NSArray *cradButtonArry;

@property (nonatomic,strong) PlayingCardGame* game;

@property (nonatomic,strong) PlayingCardDeck* cardDeck;

@property (weak, nonatomic) IBOutlet UILabel *sourceLable;

@end

@implementation ViewController



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

-(void)viewWillLayoutSubviews
{
    NSLog(@"旋转之前");
}

-(void)viewDidLayoutSubviews
{
    NSLog(@"旋转之后");
}

-(BOOL)shouldAutorotate
{
    return [super shouldAutorotate];
    //NSLog(@"不允许旋转");
    //return false;
}

-(void)awakeFromNib
{
    NSLog(@"======");
}

-(PlayingCardGame*)game
{
    if(!_game)
    {
        _game = [[PlayingCardGame alloc] initWithPlayingCard:[self.cradButtonArry count] Deck:[self cardDeck]];
    }
    return _game;
}

-(Deck *)cardDeck
{
    if (!_cardDeck) {
        _cardDeck = [[PlayingCardDeck alloc] init];
    }
    return _cardDeck;
}


-(NSString *) titleForCrad :(Crad *) card
{
    return card.isChosen ? card.contents :@"";
}

-(UIImage *) backgroundForCard : (Crad *) card
{
    return [UIImage imageNamed:card.isChosen ? @"image" : @"60-90" ];
}

- (IBAction)touchCradButton:(UIButton *)sender {
    NSUInteger touchCrad = [self.cradButtonArry indexOfObject:sender];
    [self.game chooseCardIndex:touchCrad];
    [self updateUI];
    self.sourceLable.text = [NSString stringWithFormat:@"分数:%ld",(long)self.game.sorce];
//    if([sender.currentTitle length])
//    {
//        [ViewController flop:sender];
//    }else{
//       Crad* crad = [self.cardDeck drawRandomCard];
//        if (crad) {
//            
//        UIImage* ui= [UIImage imageNamed:@"image"];
//        NSLog(@"%@",crad.contents);
//        [sender setBackgroundImage:ui forState:UIControlStateNormal];
//        [sender setTitle: crad.contents
//                forState:UIControlStateNormal];
//           
//            if (_button && [_button.currentTitle isEqualToString:crad.contents] ) {
//                
//                [ViewController flop:_button];
//            }else{
//                [ViewController flop:_button];
//                _button = sender;
//            }
//        
//        
//        }else
//        {
//            [sender setHidden:true];
//        }
//    }
}

-(void) updateUI
{
    for (UIButton* otherButton in self.cradButtonArry) {
        NSUInteger index = [self.cradButtonArry indexOfObject:otherButton];
        Crad * card = [self.game CardAtIndex:index];
        [otherButton setBackgroundImage:[self backgroundForCard:card] forState:UIControlStateNormal];
        [otherButton setTitle:[self titleForCrad:card] forState:UIControlStateNormal];
        otherButton.enabled = !card.isMatched;
    }
}

//重新开始游戏
- (IBAction)clearGame:(UIButton *)sender {
    _game = nil;
    _cardDeck = nil;
    [self updateUI];
    self.sourceLable.text = [NSString stringWithFormat:@"分数:%ld",(long)self.game.sorce];
}
@end
