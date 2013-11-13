//
//  AppDelegate.m
//  CraftsBeerApp
//
//  Created by Mandeep Singh on 10/06/13.
//  Copyright (c) 2013 Mandeep Singh. All rights reserved.
//

#import "AppDelegate.h"
#import "LoginViewController.h"
#import "HomeViewController.h"
#import "MBProgressHUD.h"
#import "Reachability.h"
#import <FacebookSDK/FacebookSDK.h>
#import "Flurry.h"
#import <Crashlytics/Crashlytics.h>

// for flurry analytics
#define YOUR_API_KEY @"3DBRP8MY7DG2Y37SF38Y"

@implementation AppDelegate

@synthesize wifiReach,window,internetReach,internetWorking;
- (BOOL)application:(UIApplication *)application
            openURL:(NSURL *)url
  sourceApplication:(NSString *)sourceApplication
         annotation:(id)annotation {
    // attempt to extract a token from the url
    return [FBAppCall handleOpenURL:url
                  sourceApplication:sourceApplication
                    fallbackHandler:^(FBAppCall *call) {
                        NSLog(@"In fallback handler");
                    }];
}

- (void)applicationWillTerminate:(UIApplication *)application {
    // FBSample logic
    // if the app is going away, we close the session object
    [FBSession.activeSession close];
}

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
    [Crashlytics startWithAPIKey:@"b53fcf08df9b183b382153735d57a10862fc5348"];
    
    [[UIApplication sharedApplication] setStatusBarHidden:YES];
    self.window = [[UIWindow alloc] initWithFrame:[[UIScreen mainScreen] bounds]];
    // Override point for customization after application launch.
    loginView=[[LoginViewController alloc]init];
    home=[[HomeViewController alloc]init];
    if ([[NSUserDefaults standardUserDefaults]valueForKey:@"user_id"] != nil ) {
        nav=[[UINavigationController alloc]initWithRootViewController:home];
    }
    else
    {
        nav=[[UINavigationController alloc]initWithRootViewController:loginView];
    }
    nav.navigationBarHidden=YES;
    self.window.rootViewController=nav;
    self.window.backgroundColor = [UIColor whiteColor];
    [self.window makeKeyAndVisible];
    [Flurry setCrashReportingEnabled:YES];
   // [Flurry setEventLoggingEnabled:YES];
    //note: iOS only allows one crash reporting tool per app; if using another, set to: NO
    [Flurry startSession:YOUR_API_KEY];
    //your code

/*
    NSString* myLogEvent = @"Most people clicked here";
    [FlurryAnalytics startSession:YOUR_API_KEY];
    [FlurryAnalytics logEvent:myLogEvent];
    
    NSDictionary *dictionary = [NSDictionary dictionaryWithObjectsAndKeys:@"your dynamic parameter value",@"your dynamic parameter name", nil];
    [FlurryAnalytics logEvent:myLogEvent withParameters:dictionary];
    [FlurryAnalytics logEvent:myLogEvent timed:YES];
    [FlurryAnalytics logEvent:myLogEvent withParameters:dictionary timed:YES];
    [FlurryAnalytics endTimedEvent:myLogEvent withParameters:dictionary];
    [FlurryAnalytics logAllPageViews:nav];
    [FlurryAnalytics logPageView];
    [FlurryAnalytics setSessionReportsOnPauseEnabled:YES];
    [FlurryAnalytics setSessionReportsOnCloseEnabled:YES];
    [FlurryAnalytics setSecureTransportEnabled:YES];
    [FlurryAnalytics setUserID:@"USER_ID"];
    [FlurryAnalytics setAge:18];
    [FlurryAnalytics setGender:@"m"];
    [FlurryAnalytics setGender:@"f"];
    */
    return YES;
}

- (void)applicationWillResignActive:(UIApplication *)application
{
    // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
    // Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
}

- (void)applicationDidEnterBackground:(UIApplication *)application
{
    // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later. 
    // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
}

- (void)applicationWillEnterForeground:(UIApplication *)application
{
    // Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
}

- (void)applicationDidBecomeActive:(UIApplication *)application
{
    // FBSample logic
    // Call the 'activateApp' method to log an app event for use in analytics and advertising reporting.
    [FBAppEvents activateApp];
    
    // FBSample logic
    // We need to properly handle activation of the application with regards to SSO
    //  (e.g., returning from iOS 6.0 authorization dialog or from fast app switching).
    [FBAppCall handleDidBecomeActive];
}


#pragma mark Progress HUD
/**
 This method start/show process indicator whenever application is fetching/getting data from any API.
 **/
-(void)show_LoadingIndicator {
    if(!HUD){
        HUD = [[MBProgressHUD alloc] initWithView:self.window];
        [self.window addSubview:HUD];
        HUD.delegate = self;
        HUD.labelText = @"Loading...";
    }
	[HUD show:YES];
}
/**
 This method hides/remove the data processing indicator.
 **/
-(void)hide_LoadingIndicator {
    if(HUD){
        
        [HUD hide:YES];
    }
}
#pragma mark-for internet connection
- (void) updateInterfaceWithReachability: (Reachability*) curReach
{
	if(curReach == internetReach)
	{
		NetworkStatus netStatus = [curReach currentReachabilityStatus];
		switch (netStatus)
        {
            case NotReachable:
            {
                self.internetWorking = -1;
                NSLog(@"INTERNET NOT WORKING");
                break;
            }
            case ReachableViaWiFi:
            {
                self.internetWorking = 0;
                break;
            }
            case ReachableViaWWAN:
            {
				self.internetWorking = 0;
				break;
            }
        }
	}
}
-(void)checkInternetConnection
{
	internetReach = [Reachability reachabilityForInternetConnection] ;
	[internetReach startNotifer];
	[self updateInterfaceWithReachability: internetReach];
}


@end
