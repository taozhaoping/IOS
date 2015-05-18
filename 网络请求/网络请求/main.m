//
//  main.m
//  网络请求
//
//  Created by 尹亚涛 on 15/5/8.
//  Copyright (c) 2015年 陶照平. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "AppDelegate.h"
#import "Nework.h"

int main(int argc, char * argv[]) {
    @autoreleasepool {
        
        
        Nework* nework = [[Nework alloc] init];
        [nework test];
        
//        AFHTTPRequestOperationManager *manager = [AFHTTPRequestOperationManager manager];
//        NSDictionary *parameters = @{@"jsonData": data};
//        [manager POST:kVideoURL parameters:parameters success:^(AFHTTPRequestOperation *operation, id responseObject) {
//            NSLog(@"JSON: %@", responseObject);
//        } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
//            NSLog(@"Error: %@", error);
//        }];
        

        
        return UIApplicationMain(argc, argv, nil, NSStringFromClass([AppDelegate class]));
    }
}
