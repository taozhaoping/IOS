//
//  AppUrl.h
//  移动办公
//
//  Created by 尹亚涛 on 15/5/14.
//  Copyright (c) 2015年 陶照平. All rights reserved.
//

#import <Foundation/Foundation.h>

#define _POST_ @"POST";

#define _HOST_ @"10.18.106.84:8010"

#define _HTTP_ @"http://"

#define _HTTPS_ @"https://"

#define _URL_SPLITTER_ @"/"

#define _URL_API_HOST_ [NSString stringWithFormat:@"%@%@%@",_HTTP_,_HOST_,_URL_SPLITTER_]

#define _ANDROID_SERVICE_ @"GetAndroidDataService.svc"

#define _URL_API_HOST_ANDROID_ [NSMutableString stringWithFormat:@"%@%@%@",_URL_API_HOST_,_ANDROID_SERVICE_,_URL_SPLITTER_]

#pragma mark - 接口方法
#pragma mark Lync2010
#define _LYNC_DOWNLOAD_ @"Lync2010.apk"

#pragma mark 日记统计服务器方法
#define _LOGSRECORD_ @"NewSetLogsRecord"

#pragma mark 用户登录
#define _LOGINACTIVITY_ @"LoginVerify"

#pragma mark 通讯录
#define _CONTACTSDATA_ @"GetContactsData"

@interface AppUrl : NSObject

@end
