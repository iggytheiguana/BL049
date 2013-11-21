
#import "userProfileViewController.h"
#import "ASyncURLConnection.h"
#import "EditUserProfileViewController.h"
#import "LoginViewController.h"
#import "userTasteProfileViewController.h"
#import "InfoViewController.h"
#import "HistoryViewController.h"
#import "JSONKit.h"

@interface userProfileViewController ()

@end

@implementation userProfileViewController

@synthesize txtBeerVal,txtEmail,txtFirstName,txtLastName,txtUserName,txtZip,lblHeader,swchSharingFb,swchSharingTwitter;
- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}
#pragma mark view life cycle

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
}

-(void)viewWillAppear:(BOOL)animated
{
    if ([[NSUserDefaults standardUserDefaults]boolForKey:@"AutomateSharingFb"])
        [swchSharingFb setOn:YES];
    else
        [swchSharingFb setOn:NO];
    
    if ([[NSUserDefaults standardUserDefaults]boolForKey:@"AutomateSharingTwitter"])
        [swchSharingTwitter setOn:YES];
    else
        [swchSharingTwitter setOn:NO];
    
    [applicationDelegate show_LoadingIndicator];
    [self getUserDetails];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark get user details

- (void)getUserDetails
{
    NSString *strid=[[NSUserDefaults standardUserDefaults]valueForKey:@"user_id"];
    NSString *xmlString=[NSString stringWithFormat:UserProfile_XML,strid];
    
    NSMutableURLRequest *theRequest = [NSMutableURLRequest requestWithURL:[NSURL URLWithString:kUserProfile]
                                                              cachePolicy:NSURLRequestUseProtocolCachePolicy timeoutInterval:120.0];
    
    NSDictionary *headerFieldsDict = [NSDictionary dictionaryWithObjectsAndKeys:@"text/xml; charset=utf-8", @"Content-Type", nil];
    
    [theRequest setAllHTTPHeaderFields:headerFieldsDict];
    [theRequest setHTTPMethod:@"POST"];
    [theRequest setHTTPBody:[xmlString dataUsingEncoding:NSUTF8StringEncoding]];
    
    [AsyncURLConnection request:theRequest completeBlock:^(NSData *jsonData) {
        NSMutableDictionary *dictUserImgsLst = [jsonData objectFromJSONData];
        NSMutableArray *array=[dictUserImgsLst valueForKey:@"userProfile"];
        txtLastName.text=[array valueForKey:@"lastName"];
        txtUserName.text=[array valueForKey:@"username"];
        txtZip.text=[array valueForKey:@"zipcode"];
        txtFirstName.text=[array valueForKey:@"firstName"];
        txtEmail.text=[array valueForKey:@"email"];
        txtBeerVal.text=[array valueForKey:@"drinkerLevel"];
        [applicationDelegate hide_LoadingIndicator];
    } errorBlock:^(NSError *error)
     {
         [applicationDelegate hide_LoadingIndicator];
     }];
    
}
#pragma mark Button actions

- (IBAction)btnInfo:(id)sender
{
    InfoViewController *info=[[InfoViewController alloc]init];
    [self presentViewController:info animated:YES completion:nil];
}

- (IBAction)btn_Back:(id)sender
{
    [self.navigationController popViewControllerAnimated:YES];
}

- (IBAction)btnEdit:(id)sender
{
    EditUserProfileViewController *editView=[[EditUserProfileViewController alloc]init];
    editView.strBeerExperience=txtBeerVal.text;
    editView.strEmail=txtEmail.text;
    editView.strFirstName=txtFirstName.text;
    editView.strLastName=txtLastName.text;
    editView.strUserName=txtUserName.text;
    editView.strZip=txtZip.text;
    
    if([[NSUserDefaults standardUserDefaults]boolForKey:@"AutomateSharingFb"])
        [editView.swchSharingFb setOn:YES];
    else
        [editView.swchSharingFb setOn:NO];
    
    if([[NSUserDefaults standardUserDefaults]boolForKey:@"AutomateSharingTwitter"])
        [editView.swchSharingTwitter setOn:YES];
    else
        [editView.swchSharingTwitter setOn:NO];
    
    [self.navigationController pushViewController:editView animated:YES];
}

- (IBAction)btnPreviewTaste:(id)sender
{
    userTasteProfileViewController *taste=[[userTasteProfileViewController alloc]init];
    [self.navigationController pushViewController:taste animated:YES];
    return;
}

- (IBAction)swchSharingFb:(id)sender
{
    NSLog(@"fb");
}

- (IBAction)swchSharingTwitter:(id)sender
{
    NSLog(@"twitter");
}

- (IBAction)ViewHistory:(id)sender
{
    HistoryViewController *history=[[HistoryViewController alloc]init];
    [self.navigationController pushViewController:history animated:YES];
}

#pragma mark Memory management

- (void)viewDidUnload
{
    [self setTxtFirstName:nil];
    [self setTxtLastName:nil];
    [self setTxtUserName:nil];
    [self setTxtPassword:nil];
    [self setTxtEmail:nil];
    [self setTxtZip:nil];
    [self setTxtBeerVal:nil];
//    [self setSwchSharing:nil];
    [self setSwchSharingFb:nil];
    [self setSwchSharingTwitter:nil];
    [super viewDidUnload];
}
@end
