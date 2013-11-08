//
//  ShareScreenViewController.m
//  CraftsBeerApp
//
//  Created by Mandeep Singh on 23/07/13.
//  Copyright (c) 2013 Mandeep Singh. All rights reserved.
//

#import "ShareScreenViewController.h"
#import "addBeerViewController.h"
#import "HomeViewController.h"
#import "iToast.h"

@interface ShareScreenViewController ()

@end

@implementation ShareScreenViewController
@synthesize lblHeader;
@synthesize fbGraph,btnBack,btnSkip,chkSkip;
#define FbClientID @"366509033392814"

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    if (chkSkip==1) {
        [[[[iToast makeText:@"Skip Sharing and return to the Search Screen."] setGravity:iToastGravityTop offsetLeft:0 offsetTop:[[UIScreen mainScreen]bounds] .size.height-120] setDuration:2000] show:iToastTypeNotice];
        btnSkip.hidden=NO;
        btnBack.hidden=YES;
        
    }
    else
    {
        btnSkip.hidden=YES;
        btnBack.hidden=NO;
        
    }
    FBLoginView *loginview = [[FBLoginView alloc] init];
    
    loginview.frame = CGRectOffset(loginview.frame, 5, 5);
    loginview.delegate = self;
    
    //  [self.view addSubview:loginview];
    
    [loginview sizeToFit];
    
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)btnSkip:(id)sender
{
    for (UIViewController* viewController in self.navigationController.viewControllers) {
        
        if ([viewController isKindOfClass:[HomeViewController class]] ) {
            HomeViewController *home = (HomeViewController*)viewController;
            [self.navigationController popToViewController:home animated:YES];
        }
    }
}

- (IBAction)btnEdit:(id)sender
{
    UIAlertView *alert=[[UIAlertView alloc]initWithTitle:@"" message:@"Functionality pending" delegate:nil cancelButtonTitle:@"Ok" otherButtonTitles:nil];
    [alert show];
}
- (IBAction)btnFb:(id)sender
{
    NSLog(@"you are here");
    
    if ([[[UIDevice currentDevice] systemVersion] floatValue] >= 6.0)
    {
        NSLog(@"you are here3");
        
        //    if ([SLComposeViewController isAvailableForServiceType:SLServiceTypeFacebook])
        {
            SLComposeViewController *objvc = [SLComposeViewController composeViewControllerForServiceType:SLServiceTypeFacebook];
            NSLog(@"you are here1");
            //setting the text to post
            NSString *strBrName=[NSString stringWithFormat:@"#%@",[[NSUserDefaults standardUserDefaults]valueForKey:@"Beername"]];
            NSString *strBrwName=[NSString stringWithFormat:@"#%@",[[NSUserDefaults standardUserDefaults]valueForKey:@"Breweryname"]];
            if (strBrwName.length<2 || [strBrwName isEqualToString:@"#(null)"]) {
                strBrwName=@"";
            }
            if (strBrName.length<2 || [strBrName isEqualToString:@"#(null)"]) {
                strBrName=@"";
            }
            NSString *strText=[NSString stringWithFormat:@"I just profiled %@ %@ with @brewhornbeerapp.#brewhorn#craftbeer",strBrwName,strBrName];
            if (strBrName.length==0 && strBrwName.length==0) {
                strText=[NSString stringWithFormat:@"@brewhornbeerapp.#brewhorn#craftbeer"];
            }
            
            [objvc setInitialText:strText];
            
            //adding the image to FB post
            //  [objvc addImage:[UIImage imageNamed:@"SurajMirajkar.jpg"]];
            
            //adding the URL to FB post
            
            //display the twitter composer modal view controller
            if ([[[UIDevice currentDevice] systemVersion] floatValue] >= 6.0)
                [self presentViewController:objvc animated:YES completion:nil];
            else
                [self presentModalViewController:objvc animated:YES];
            
            objvc.completionHandler = ^(SLComposeViewControllerResult result)
            {
                NSLog(@"you are here2");
                
                if ([[[UIDevice currentDevice] systemVersion] floatValue] >= 6.0)
                    [self dismissViewControllerAnimated:YES completion:nil];
                else
                    [self dismissModalViewControllerAnimated:YES];
                
            };
        }
        return;
        
        
        
        
        
        
        
        
        
        
        
        
        {
            // Post a status update to the user's feed via the Graph API, and display an alert view
            // with the results or an error.
            
            NSURL *urlToShare = [NSURL URLWithString:@"http://developers.facebook.com/ios"];
            
            // This code demonstrates 3 different ways of sharing using the Facebook SDK.
            // The first method tries to share via the Facebook app. This allows sharing without
            // the user having to authorize your app, and is available as long as the user has the
            // correct Facebook app installed. This publish will result in a fast-app-switch to the
            // Facebook app.
            // The second method tries to share via Facebook's iOS6 integration, which also
            // allows sharing without the user having to authorize your app, and is available as
            // long as the user has linked their Facebook account with iOS6. This publish will
            // result in a popup iOS6 dialog.
            // The third method tries to share via a Graph API request. This does require the user
            // to authorize your app. They must also grant your app publish permissions. This
            // allows the app to publish without any user interaction.
            
            // If it is available, we will first try to post using the share dialog in the Facebook app
            FBAppCall *appCall = [FBDialogs presentShareDialogWithLink:urlToShare
                                                                  name:nil
                                                               caption:nil
                                                           description:@"Welcome to CraftsBeer."
                                                               picture:nil
                                                           clientState:nil
                                                               handler:^(FBAppCall *call, NSDictionary *results, NSError *error) {
                                                                   if (error) {
                                                                       NSLog(@"Error: %@", error.description);
                                                                   } else {
                                                                       NSLog(@"Success!");
                                                                   }
                                                               }];
            
            if (!appCall) {
                // Next try to post using Facebook's iOS6 integration
                BOOL displayedNativeDialog = [FBDialogs presentOSIntegratedShareDialogModallyFrom:self
                                                                                      initialText:nil
                                                                                            image:nil
                                                                                              url:urlToShare
                                                                                          handler:nil];
                
                if (!displayedNativeDialog) {
                    // Lastly, fall back on a request for permissions and a direct post using the Graph API
                    [self performPublishAction:^{
                        NSString *message = [NSString stringWithFormat:@"Welcome to CraftsBeer"];
                        
                        [FBRequestConnection startForPostStatusUpdate:message
                                                    completionHandler:^(FBRequestConnection *connection, id result, NSError *error) {
                                                    }];
                    }];
                }
            }
        }
    }
    else
    {
        //create the instance of graph api
        fbGraph = [[FbGraph alloc]initWithFbClientID:FbClientID];
        //mark some permissions for your access token so that it knows what permissions it has
        [fbGraph authenticateUserWithCallbackObject:self andSelector:@selector(FBGraphResponse) andExtendedPermissions:@"user_photos,user_videos,publish_stream,offline_access,user_checkins,friends_checkins,publish_checkins,email"];
    }
    
}
- (void)FBGraphResponse
{
    @try
    {
        if (fbGraph.accessToken)
        {
            SBJSON *jsonparser = [[SBJSON alloc]init];
            
            FbGraphResponse *fb_graph_response = [fbGraph doGraphGet:@"me" withGetVars:nil];
            
            NSString *resultString = [NSString stringWithString:fb_graph_response.htmlResponse];
            NSDictionary *dict =  [jsonparser objectWithString:resultString];
            NSLog(@"Dict = %@",dict);
            
            NSMutableDictionary *variable = [[NSMutableDictionary alloc]initWithCapacity:1];
            NSString *strBrName=[NSString stringWithFormat:@"#%@",[[NSUserDefaults standardUserDefaults]valueForKey:@"Beername"]];
            NSString *strBrwName=[NSString stringWithFormat:@"#%@",[[NSUserDefaults standardUserDefaults]valueForKey:@"Breweryname"]];
            if (strBrwName.length<2 || [strBrwName isEqualToString:@"#(null)"]) {
                strBrwName=@"";
            }
            if (strBrName.length<2 || [strBrName isEqualToString:@"#(null)"]) {
                strBrName=@"";
            }
            NSString *strText=[NSString stringWithFormat:@"I just profiled %@ %@ with @brewhornbeerapp.#brewhorn#craftbeer",strBrwName,strBrName];
            if (strBrName.length==0 && strBrwName.length==0) {
                strText=[NSString stringWithFormat:@"@brewhornbeerapp.#brewhorn#craftbeer"];
            }
            [variable setObject:strText forKey:@"description"];
            [fbGraph doGraphPost:@"me/feed" withPostVars:variable];
            
        }
    }
    @catch (NSException *exception) {
    }
    
}

- (IBAction)btnTwitter:(id)sender
{
    NSString *strBrName=[NSString stringWithFormat:@"#%@",[[NSUserDefaults standardUserDefaults]valueForKey:@"Beername"]];
    NSString *strBrwName=[NSString stringWithFormat:@"#%@",[[NSUserDefaults standardUserDefaults]valueForKey:@"Breweryname"]];
    if (strBrwName.length<2 || [strBrwName isEqualToString:@"#(null)"]) {
        strBrwName=@"";
    }
    if (strBrName.length<2 || [strBrName isEqualToString:@"#(null)"]) {
        strBrName=@"";
    }
    
    NSString *strText=[NSString stringWithFormat:@"I just profiled %@ %@ with @brewhornbeerapp.#brewhorn#craftbeer",strBrwName,strBrName];
    if (strBrName.length==0 && strBrwName.length==0) {
        strText=[NSString stringWithFormat:@"@brewhornbeerapp.#brewhorn#craftbeer"];
    }
    NSString *someTweet = strText;
    if (floor(NSFoundationVersionNumber) > NSFoundationVersionNumber_iOS_6_1)
    {
        SLComposeViewController *tweetSheet = [SLComposeViewController composeViewControllerForServiceType:SLServiceTypeTwitter];
        
        [tweetSheet setInitialText:someTweet];
        [self presentViewController:tweetSheet animated:YES completion:NULL];
    }
    else
    {
        // running on iOS5
        if (NSClassFromString(@"TWTweetComposeViewController") && [TWTweetComposeViewController canSendTweet]) {
            
            TWTweetComposeViewController *tweetSheet = [[TWTweetComposeViewController alloc] init];
            
            [tweetSheet setInitialText:someTweet];
            [self presentModalViewController:tweetSheet animated:YES];
        }
        else if ( NSClassFromString(@"SLComposeViewController") && [SLComposeViewController isAvailableForServiceType:SLServiceTypeTwitter] ) {
            
            SLComposeViewController *tweetSheet = [SLComposeViewController composeViewControllerForServiceType:SLServiceTypeTwitter];
            
            [tweetSheet setInitialText:someTweet];
            [self presentViewController:tweetSheet animated:YES completion:NULL];
            
        }
        else
        {
            UIAlertView *alert=[[UIAlertView alloc]initWithTitle:@"Twitter" message:@"Please configure twitter in your phone settings." delegate:nil cancelButtonTitle:@"Ok" otherButtonTitles:nil];
            [alert show];
        }
        
    }
}

- (IBAction)btnCancel:(id)sender
{
    for (UIViewController* viewController in self.navigationController.viewControllers) {
        
        if ([viewController isKindOfClass:[HomeViewController class]] ) {
            HomeViewController *home = (HomeViewController*)viewController;
            [self.navigationController popToViewController:home animated:YES];
        }
    }
}

#pragma mark - FBLoginViewDelegate

- (void)loginViewShowingLoggedInUser:(FBLoginView *)loginView {
    // first get the buttons set for login mode
    //    self.buttonPostPhoto.enabled = YES;
    //    self.buttonPostStatus.enabled = YES;
    //    self.buttonPickFriends.enabled = YES;
    //    self.buttonPickPlace.enabled = YES;
    
    // "Post Status" available when logged on and potentially when logged off.  Differentiate in the label.
    // [self.btn setTitle:@"Post Status Update (Logged On)" forState:self.buttonPostStatus.state];
}

- (void)loginViewFetchedUserInfo:(FBLoginView *)loginView
                            user:(id<FBGraphUser>)user {
    // here we use helper properties of FBGraphUser to dot-through to first_name and
    // id properties of the json response from the server; alternatively we could use
    // NSDictionary methods such as objectForKey to get values from the my json object
    //  self.labelFirstName.text = [NSString stringWithFormat:@"Hello %@!", user.first_name];
    // setting the profileID property of the FBProfilePictureView instance
    // causes the control to fetch and display the profile picture for the user
    //  self.profilePic.profileID = user.id;
    // self.loggedInUser = user;
}

- (void)loginViewShowingLoggedOutUser:(FBLoginView *)loginView {
    // test to see if we can use the share dialog built into the Facebook application
    FBShareDialogParams *p = [[FBShareDialogParams alloc] init];
    p.link = [NSURL URLWithString:@"http://developers.facebook.com/ios"];
#ifdef DEBUG
    [FBSettings enableBetaFeatures:FBBetaFeaturesShareDialog];
#endif
    BOOL canShareFB = [FBDialogs canPresentShareDialogWithParams:p];
    BOOL canShareiOS6 = [FBDialogs canPresentOSIntegratedShareDialogWithSession:nil];
    
}

- (void)loginView:(FBLoginView *)loginView handleError:(NSError *)error {
    // see https://developers.facebook.com/docs/reference/api/errors/ for general guidance on error handling for Facebook API
    // our policy here is to let the login view handle errors, but to log the results
    NSLog(@"FBLoginView encountered an error=%@", error);
}

#pragma mark -

// Convenience method to perform some action that requires the "publish_actions" permissions.
- (void) performPublishAction:(void (^)(void)) action {
    // we defer request for permission to post to the moment of post, then we check for the permission
    if ([FBSession.activeSession.permissions indexOfObject:@"publish_actions"] == NSNotFound) {
        // if we don't already have the permission, then we request it now
        [FBSession.activeSession requestNewPublishPermissions:@[@"publish_actions"]
                                              defaultAudience:FBSessionDefaultAudienceFriends
                                            completionHandler:^(FBSession *session, NSError *error) {
                                                if (!error) {
                                                    action();
                                                }
                                                //For this example, ignore errors (such as if user cancels).
                                            }];
    } else {
        action();
    }
    
}
- (void)viewDidUnload {
    [self setBtnSkip:nil];
    [self setBtnBack:nil];
    [super viewDidUnload];
}
@end
