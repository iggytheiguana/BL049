//
//  LoginViewController.h
//  CraftBeer
//
//  Created by Mandeep Singh on 07/06/13.
//  Copyright (c) 2013 Mandeep Singh. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface LoginViewController : UIViewController
{
    // for parsing
    NSMutableData *receiveDataLogin;
    NSURLConnection *theConnection;
    IBOutlet UILabel *lblTop;
}

@property (retain, nonatomic) IBOutlet UITextField *txtPassword;
@property (retain, nonatomic) IBOutlet UITextField *txtUserName;
@property (strong, nonatomic) IBOutlet UILabel *lblHeader;


- (IBAction)btnForgotPassword:(id)sender;
- (IBAction)Login:(id)sender;
- (IBAction)btnResign:(id)sender;
- (IBAction)btnInfo:(id)sender;

- (IBAction)btnRegister:(id)sender;
@end
