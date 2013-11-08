//
//  ForgotPasswordViewController.m
//  CraftBeer
//
//  Created by Mandeep Singh on 07/06/13.
//  Copyright (c) 2013 Mandeep Singh. All rights reserved.
//

#import "ForgotPasswordViewController.h"
#import "JSON.h"
#import "AppDelegate.h"

@interface ForgotPasswordViewController ()

@end

@implementation ForgotPasswordViewController
@synthesize txtUserName;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)didReceiveMemoryWarning
{
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc that aren't in use.
}

#pragma mark - View lifecycle

- (void)viewDidLoad
{
    [txtUserName becomeFirstResponder];
    receiveData=[[NSMutableData alloc]init ];
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
}
- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

#pragma mark- UItextField delegate methods
- (BOOL)textFieldShouldReturn:(UITextField *)textField
{
    return YES;
}

#pragma mark- UIButton Actions
- (IBAction)btnBack:(id)sender
{
    [self.navigationController popViewControllerAnimated:YES];
}
- (IBAction)btn_Submit:(id)sender
{
    if(txtUserName.text.length>0)
    {
        {
            [applicationDelegate checkInternetConnection];
            if([applicationDelegate internetWorking] ==0)
            {
                [applicationDelegate show_LoadingIndicator];
                NSString *username = txtUserName.text;
                NSString * xmlString =
                [NSString stringWithFormat:@"<?xml version=\"1.0\" encoding=\"UTF-8\" ?><user><username><![CDATA[%@]]></username></user>",username];
                
                NSLog(@"the xmlstring is =%@",xmlString);
                NSURL * serviceUrl = [NSURL URLWithString:kFrgtPswdServerUrl];
                NSMutableURLRequest *theRequest = [NSMutableURLRequest requestWithURL:serviceUrl
                                                                          cachePolicy:NSURLRequestUseProtocolCachePolicy timeoutInterval:120.0];
                NSDictionary *headerFieldsDict = [NSDictionary dictionaryWithObjectsAndKeys:@"text/xml; charset=utf-8", @"Content-Type", nil];
                [theRequest setAllHTTPHeaderFields:headerFieldsDict];
                [theRequest setHTTPMethod:@"POST"];
                [theRequest setHTTPBody:[xmlString dataUsingEncoding:NSUTF8StringEncoding]];
                
                NSHTTPURLResponse* urlResponse = nil;
                NSError *error = [[NSError alloc] init];
                NSData *responseData1 = [NSURLConnection sendSynchronousRequest:theRequest returningResponse:&urlResponse error:&error];
                NSString* serRslt1= [[NSString alloc] initWithData:responseData1 encoding:NSUTF8StringEncoding];
                NSLog(@"The result is = %@",serRslt1);
                NSDictionary *dict=[serRslt1 JSONValue];
                NSString *serRslt=[dict valueForKey:@"forgotPassword"];
                
                if ([serRslt integerValue]>0)
                {
                    [applicationDelegate hide_LoadingIndicator];
                    UIAlertView *alert=[[UIAlertView alloc] initWithTitle:@"Alert!" message:@"Password sent." delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil];
                    [alert show];
                }
                else if([serRslt integerValue]== -1)
                {
                    [applicationDelegate hide_LoadingIndicator];
                    UIAlertView *alert=[[UIAlertView alloc] initWithTitle:@"Alert!" message:@"Invalid Email /UserName." delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil];
                    [alert show];
                }
                else if([serRslt integerValue]== -2)
                {
                    [applicationDelegate hide_LoadingIndicator];
                    UIAlertView *alert=[[UIAlertView alloc] initWithTitle:@"Alert!" message:@"Server Issue." delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil];
                    [alert show];
                }
                
                serRslt = nil;
            }
            else
            {
                
                UIAlertView *alertViewNetwrk = [[UIAlertView alloc] initWithTitle:@"Alert!"
                                                                          message:kAlertInternet
                                                                         delegate:self
                                                                cancelButtonTitle:@"OK"
                                                                otherButtonTitles:nil, nil];
                
                [alertViewNetwrk show];
                alertViewNetwrk = nil;
            }
            
            [applicationDelegate hide_LoadingIndicator];
        }
    }
    else
    {
        UIAlertView *alert=[[UIAlertView alloc] initWithTitle:@"Alert!" message:@"Please enter user name." delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil];
        [alert show];
    }
}

#pragma mark- AlertView delegate methods
- (void)alertView:(UIAlertView *)alertView didDismissWithButtonIndex:(NSInteger)buttonIndex
{
    if([alertView.message isEqualToString:@"Password sent."])
	{
        [self.navigationController popViewControllerAnimated:YES];
	}
}

- (void)viewDidUnload {
    [self setTxtUserName:nil];
    [super viewDidUnload];
}
@end
