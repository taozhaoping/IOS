//
//  MainViewController.h
//  移动办公
//
//  Created by 尹亚涛 on 15/5/6.
//  Copyright (c) 2015年 陶照平. All rights reserved.
//

#import "BaseViewController.h"
#import "ContactModel.h"

@interface MainViewController : BaseViewController

@property (weak, nonatomic) IBOutlet UITextView *textView;
@property(nonatomic,strong)ContactModel* contactInfo;
@end
