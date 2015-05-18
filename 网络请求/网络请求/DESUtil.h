//
//  DESUtil.h
//  移动办公
//
//  Created by 尹亚涛 on 15/5/7.
//  Copyright (c) 2015年 陶照平. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <CommonCrypto/CommonDigest.h>
#import <CommonCrypto/CommonCryptor.h>

#define _DES_DEFAULT_KEY_ "02adfd5a"

@interface DESUtil : NSObject

+ (NSString *) encode:(NSString *)str;
+ (NSString *) decode:(NSString *)str;

@end
