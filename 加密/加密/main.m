//
//  main.m
//  加密
//
//  Created by 尹亚涛 on 15/5/8.
//  Copyright (c) 2015年 陶照平. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "AppDelegate.h"
#import "DESUtil.h"

int main(int argc, char * argv[]) {
    @autoreleasepool {
        
        NSString* st = @"{\"FItemNumber\":\"25153\",\"FPassword\":\"ABC@330326!\"}";
        st =@"{\"IsSuccess\":true,\"Result\":\"[{\"FModel\":\"DH-DSS-T8100-PRO/2000\",\"FName\":\"大华交通综合管控平台软件（2000路）DH-DSS-T8100-PRO/2000\",\"FFirstLine\":\"软件\",\"FSecLine\":\"软件产品\",\"FStandPrice\":\"价格权限规则制定中，暂无法查询，请您谅解！\"}]\"}";
        NSString* str = [DESUtil encode: st key:@"02adfd5a"];
        
        NSLog(@"加密: %@ \n", str);
        
        NSString* str1 = [DESUtil decode:str key:@"02adfd5a"];
        NSLog(@"解密: %@ \n", str1);
        
        NSString* strJava = @"73e980ed734ee24d4054c9993b2a58d2e3723ccd2ac7916facbc09537744aedfac3a45e8e97d3015a04bb32ec41cfff10772165bb018dbc4479ec97b6911ef833ff9a697853ae2c4b6f2d0d49cc0a782172ea80e7a3cb2210c21caff4e39c3b654d47c313e40f205e988fac7fa54597ee2d3717e17bdbbe55b7a4695fc655b69980e469a303082be35b6fb0be760f69fc69a8b2be4000853066643f877c912e2e15a8fabc26b0d231563b72fda75b48527bc459900aadd9394674412e4443aacca2f8f5d95849ba1b588862a94fa66695d5724df2ca3632020475fc1cb8f795127cfc26bb6d38e843c929f3a7a46f0ba49f557d7c45f45edb59db8d3b7a141de3f32c2f128954b437f9b64fa7e665739a2fe0a47dc056baa3f5bf57b21ef1abdae4c6a83a170414ddf71e0cdc305aef2a3caef615c3f79759f51ab8dd7bad743749f249cbae23c2bcdb8cc5a2e6898c21b380b336cbbecf8cb29b04e2492e5ac38eae5f49e39d48c38a065e2949bb4e726232a28b8e0ac0b750a84565a67af4e741912e3097c0a1991d50427e64c6e8228f549afa7a3fc958bdbbbd29efaecadceaa2c24204e05739dd2df8a4ed897160d40a9a579507de62e6b28188afb5d7111be0d22b781c694aa043ffaf57a82c5e2401b1407d0bdbdcf1f3cb90276df4875debc3e9645fd334c7337ad57d6f24189c01c0141abaea2445666ac723967000425548333450666ed57a0b7fd49385e2dc8498fed4602abe7626d1bd9af3cf0cfea34aef8c8e5cf04344893d1f973fbe956cb5a2d340fdc5e112a18cd7210e80847d236799e2a1c";
//        
        NSString* str3 = [DESUtil decode:strJava key:@"02adfd5a"];
       NSLog(@"java解密: %@ \n", str3);

        
        return UIApplicationMain(argc, argv, nil, NSStringFromClass([AppDelegate class]));
    }
}
