//
//  ChangePasswordViewController.m
//  CraftsBeerApp
//
//  Created by Mandeep Singh on 01/08/13.
//  Copyright (c) 2013 Mandeep Singh. All rights reserved.
//

#import "ChangePasswordViewController.h"
#import "JSON.h"

@interface ChangePasswordViewController ()

@end

@implementation ChangePasswordViewController

@synthesize txtConfirm,txtPassword,oldPassword,lblHeader;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}
#pragma mark- Life cycle
- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
}
- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}
#pragma mark- Button pressed actions
- (IBAction)btnCancel:(id)sender
{
    [self.navigationController popViewControllerAnimated:YES];
}
- (IBAction)btnChange:(id)sender
{
    if (oldPassword.text.length==0) {
        UIAlertView *alert=[[UIAlertView alloc]initWithTitle:@"" message:@"Enter old password." delegate:nil cancelButtonTitle:@"Ok" otherButtonTitles:nil];
        [alert show];
    }
    else if (oldPassword.text.length<8) {
        UIAlertView *alert=[[UIAlertView alloc]initWithTitle:@"" message:@"Please check your password." delegate:nil cancelButtonTitle:@"Ok" otherButtonTitles:nil];
        [alert show];
    }
    else if (txtPassword.text.length==0) {
        UIAlertView *alert=[[UIAlertView alloc]initWithTitle:@"" message:@"Enter new password." delegate:nil cancelButtonTitle:@"Ok" otherButtonTitles:nil];
        [alert show];
    }
    else if (txtPassword.text.length<8) {
        UIAlertView *alert=[[UIAlertView alloc]initWithTitle:@"" message:@"The password should be of minimum 8 characters." delegate:nil cancelButtonTitle:@"Ok" otherButtonTitles:nil];
        [alert show];
    }

    else if (txtConfirm.text.length==0) {
        UIAlertView *alert=[[UIAlertView alloc]initWithTitle:@"" message:@"Enter confirm password." delegate:nil cancelButtonTitle:@"Ok" otherButtonTitles:nil];
        [alert show];
    }
    else if (![txtConfirm.text isEqualToString:txtPassword.text] ) {
        UIAlertView *alert=[[UIAlertView alloc]initWithTitle:@"" message:@"Password and Confirm password does not match." delegate:nil cancelButtonTitle:@"Ok" otherButtonTitles:nil];
        [alert show];
    }
    else
    {
        [applicationDelegate checkInternetConnection];
        if([applicationDelegate internetWorking] ==0)
        {
            
            [applicationDelegate show_LoadingIndicator];
            NSString *strid=[[NSUserDefaults standardUserDefaults]valueForKey:@"user_id"];
            
            NSString * xmlString =[NSString stringWithFormat:ChangePassword_XML,strid,oldPassword.text,txtPassword.text];
            
            NSLog(@"the xmlstring is =%@",xmlString);
            
            
            
            NSURL * serviceUrl = [NSURL URLWithString:kChangePassword];
            
            
            NSMutableURLRequest *theRequest = [NSMutableURLRequest requestWithURL:serviceUrl
                                                                      cachePolicy:NSURLRequestUseProtocolCachePolicy timeoutInterval:120.0];
            
            
            NSDictionary *headerFieldsDict = [NSDictionary dictionaryWithObjectsAndKeys:@"text/xml; charset=utf-8", @"Content-Type", nil];
            
            [theRequest setAllHTTPHeaderFields:headerFieldsDict];
            [theRequest setHTTPMethod:@"POST"];
            [theRequest setHTTPBody:[xmlString dataUsingEncoding:NSUTF8StringEncoding]];
            NSHTTPURLResponse* urlResponse = nil;
            NSError *error = [[NSError alloc] init];
            NSData *responseData1 = [NSURLConnection sendSynchronousRequest:theRequest returningResponse:&urlResponse error:&error];
            NSString* serRslt= [[NSString alloc] initWithData:responseData1 encoding:NSUTF8StringEncoding];
            NSLog(@"The result is = %@",serRslt);
            NSMutableDictionary*dictReg = [serRslt JSONValue];
            NSLog(@"the result in dict = %@",dictReg);
            
            NSString *strVal=[NSString stringWithFormat:@"%@",[dictReg objectForKey:@"changePassword"]];
            
            if ([strVal integerValue]>0)
            {
                [applicationDelegate hide_LoadingIndicator];
                
                [[NSUserDefaults standardUserDefaults]setValue:strVal forKey:@"user_id"];
                [[NSUserDefaults standardUserDefaults]synchronize];
                [self.navigationController popViewControllerAnimated:YES];
                
            }
            else  if ([strVal integerValue]==-3)
            {
                [applicationDelegate hide_LoadingIndicator];
                UIAlertView *alert=[[UIAlertView alloc] initWithTitle:@"" message:@"Old Password doesn't match with your profile." delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
                [alert show];
                
            }
            else  if ([strVal integerValue]==-1)
            {
                [applicationDelegate hide_LoadingIndicator];
                UIAlertView *alert=[[UIAlertView alloc] initWithTitle:@"" message:@"User does not exists" delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
                [alert show];
                
            }
            else if ([strVal integerValue]==-4)
            {
                [applicationDelegate hide_LoadingIndicator];
                UIAlertView *alert=[[UIAlertView alloc] initWithTitle:@"" message:@"Old and New Password have same values." delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
                [alert show];
                
            }
            else if ([strVal integerValue]==-2)
            {
                [applicationDelegate hide_LoadingIndicator];
                UIAlertView *alert=[[UIAlertView alloc] initWithTitle:@"" message:@"Server error." delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
                [alert show];
            }
            serRslt = nil;
        }
        else
        {
            UIAlertView *alertViewNetwrk = [[UIAlertView alloc] initWithTitle:@"Alert!"
                                                                      message:@"Please connect to internet."
                                                                     delegate:nil
                                                            cancelButtonTitle:@"OK"
                                                            otherButtonTitles:nil, nil];
            
            [alertViewNetwrk show];
            alertViewNetwrk = nil;
        }
    }
}
#pragma mark- UItextField delegate methods
- (BOOL)textFieldShouldReturn:(UITextField *)textField
{
    NSInteger nextTag = textField.tag + 1;
    UIResponder* nextResponder = [textField.superview viewWithTag:nextTag];
    
    if (nextResponder)
    {
        [nextResponder becomeFirstResponder];
    }
    else
    {
        [textField resignFirstResponder];
    }
    return YES;
}
#pragma mark- Memory management
- (void)viewDidUnload {
    [self setTxtConfirm:nil];
    [self setTxtPassword:nil];
    [self setOldPassword:nil];
    [super viewDidUnload];
}
@end
