//
//  AppDelegate.h
//  CraftsBeerApp
//
//  Created by Mandeep Singh on 10/06/13.
//  Copyright (c) 2013 Mandeep Singh. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "MBProgressHUD.h"
@class LoginViewController;
@class HomeViewController;
@class Reachability;

@interface AppDelegate : UIResponder <UIApplicationDelegate,MBProgressHUDDelegate>
{
    LoginViewController *loginView;
    HomeViewController *home;
    MBProgressHUD * HUD;// for activity indicator
    UINavigationController *nav;
    Reachability *internetReach;
    Reachability* wifiReach;
    
}

@property (nonatomic, retain) Reachability *internetReach;
@property (nonatomic, retain) Reachability* wifiReach;
@property (nonatomic) int internetWorking;

@property (strong, nonatomic) UIWindow *window;
// methods for showing and hidin indicators
-(void)show_LoadingIndicator;
-(void)hide_LoadingIndicator;
-(void)checkInternetConnection;

@end
