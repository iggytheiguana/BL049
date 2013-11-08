//
//  LoginViewController.m
//  CraftBeer
//
//  Created by Mandeep Singh on 07/06/13.
//  Copyright (c) 2013 Mandeep Singh. All rights reserved.
//

#import "LoginViewController.h"
#import "ForgotPasswordViewController.h"
#import "RegisterViewController.h"
#import "addBeerViewController.h"
#import "HomeViewController.h"
#import "InfoViewController.h"
#import "JSON.h"

@interface LoginViewController ()

@end

@implementation LoginViewController
@synthesize txtPassword;
@synthesize txtUserName,lblHeader;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}
#pragma mark - View lifecycle
- (void)viewDidLoad
{
    // lblTop.textColor=[UIColor colorWithRed:143 green:29 blue:2 alpha:1];
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
}
-(void)viewWillAppear:(BOOL)animated
{
    txtPassword.text=@"";
    txtUserName.text=@"";
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}
#pragma mark - Button actions
- (IBAction)btnForgotPassword:(id)sender
{
    //  addBeerViewController *forgot1=[[addBeerViewController alloc]init];
    //  [self.navigationController pushViewController:forgot1 animated:YES];
    //  return;
    
    [txtPassword resignFirstResponder];
    [txtUserName resignFirstResponder];
    ForgotPasswordViewController *forgot=[[ForgotPasswordViewController alloc]init];
    [self.navigationController pushViewController:forgot animated:YES];
    // self.navi
}

- (IBAction)Login:(id)sender {
    
    //to check the range of emailid
    //  NSString *emailReg = @"[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";
	//NSPredicate *emailTest = [NSPredicate predicateWithFormat:@"SELF MATCHES %@", emailReg];
    
    // to remove the white space on left and right side
    //  NSString* result = [yourString stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceCharacterSet]];
    
    // To remove the white space from everywhere left , right and middle also
    [txtUserName.text stringByReplacingOccurrencesOfString:@" " withString:@""];
    [txtPassword resignFirstResponder];
    [txtUserName resignFirstResponder];
    
    
    if(txtUserName.text.length ==0)
    {
        if(txtPassword.text.length ==0)
        {
            
            UIAlertView *alert=[[UIAlertView alloc] initWithTitle:@"Login" message:@"Please enter username and password."  delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil];
            [alert show];
            
        }
        else
        {
            
            UIAlertView *alert=[[UIAlertView alloc] initWithTitle:@"Login" message:@"Please enter Email address or user name." delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil];
            [alert show];
        }
        
    }
    else if(txtPassword.text.length ==0)
    {
        
        UIAlertView *alert=[[UIAlertView alloc] initWithTitle:@"Login" message:@"Please enter password."  delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil];
        [alert show];
        
    }
    /*
     else if (txtUsername.text  != YES)
     {
     
     UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Login"
     message:@"Please enter valid emailid."
     delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil];
     [alert show];
     [alert release];
     }
     
     */
    else
    {
        [applicationDelegate checkInternetConnection];
        if([applicationDelegate internetWorking] ==0)
        {
            
            [applicationDelegate show_LoadingIndicator];
            
            NSString *username = txtUserName.text;
            NSString *password = txtPassword.text;
            
            
            
            
            //             NSString * xmlString =[NSString stringWithFormat:@"<?xml version=\"1.0\" encoding=\"UTF-8\" ?><userLogin><username><![CDATA[%@]]></username><password><![CDATA[%@]]></password></userLogin>",username,password];
            NSString * xmlString =[NSString stringWithFormat:LOGIN_XML,username,password];
            
            NSLog(@"the xmlstring is =%@",xmlString);
            
            
            NSURL * serviceUrl = [NSURL URLWithString:kLoginServerUrl];
            
            NSLog(@"the url is is =%@",serviceUrl);
            
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
            NSDictionary *dict = [serRslt JSONValue];
            NSLog(@"the dictionary = %@",dict);
            if ([[dict valueForKey:@"userLogin"] integerValue]>0)
            {
                NSArray *array=[dict objectForKey:@"beerProfile"];
                NSLog(@"the array is %@",array);
                [[NSUserDefaults standardUserDefaults]setObject:array forKey:@"arrayDetails"];
                [[NSUserDefaults standardUserDefaults]synchronize];
                [[NSUserDefaults standardUserDefaults]setValue:[dict valueForKey:@"userLogin"] forKey:@"user_id"];
                [[NSUserDefaults standardUserDefaults]synchronize];
                
                HomeViewController *homeView=[[HomeViewController alloc]init];
                [self.navigationController pushViewController:homeView animated:YES];
                UIAlertView *alert=[[UIAlertView alloc] initWithTitle:@"Functionality Pending" message:@"Sucessfully login." delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
                //  [alert show];
                [applicationDelegate hide_LoadingIndicator];
            }
            else if([[dict valueForKey:@"userLogin"] integerValue]== -1)
            {
                [applicationDelegate hide_LoadingIndicator];
                UIAlertView *alert=[[UIAlertView alloc] initWithTitle:@"Login" message:@"Failure" delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
                [alert show];
            }
            else if([[dict valueForKey:@"userLogin"] integerValue]== -2)
            {
                [applicationDelegate hide_LoadingIndicator];
                UIAlertView *alert=[[UIAlertView alloc] initWithTitle:@"Login" message:@"Server error." delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
                [alert show];
            }
            serRslt = nil;
            
        }
        else
        {
            
            UIAlertView *alertViewNetwrk = [[UIAlertView alloc] initWithTitle:@"Alert!"
                                                                      message:@"Please connect to internet."
                                                                     delegate:self
                                                            cancelButtonTitle:@"OK"
                                                            otherButtonTitles:nil, nil];
            
            [alertViewNetwrk show];
            alertViewNetwrk = nil;
        }
    }
}

- (IBAction)btnResign:(id)sender
{
    [txtPassword resignFirstResponder];
    [txtUserName resignFirstResponder];
}

- (IBAction)btnInfo:(id)sender
{
    InfoViewController *info=[[InfoViewController alloc]init];
    [self presentViewController:info animated:YES completion:nil];
}
- (IBAction)btnRegister:(id)sender
{
    [txtPassword resignFirstResponder];
    [txtUserName resignFirstResponder];
    RegisterViewController *registerView=[[RegisterViewController alloc]init];
    [self.navigationController pushViewController:registerView animated:YES];
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
- (void)viewDidUnload {
    [self setTxtPassword:nil];
    [self setTxtUserName:nil];
    lblTop = nil;
    [super viewDidUnload];
}
@end
