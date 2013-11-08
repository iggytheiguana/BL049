//
//  userProfileViewController.h
//  CraftsBeerApp
//
//  Created by Mandeep Singh on 01/08/13.
//  Copyright (c) 2013 Mandeep Singh. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface userProfileViewController : UIViewController

@property (strong, nonatomic) IBOutlet UITextField *txtFirstName;
@property (strong, nonatomic) IBOutlet UITextField *txtLastName;
@property (strong, nonatomic) IBOutlet UITextField *txtUserName;
@property (strong, nonatomic) IBOutlet UITextField *txtPassword;
@property (strong, nonatomic) IBOutlet UILabel *txtEmail;
@property (strong, nonatomic) IBOutlet UITextField *txtZip;
@property (strong, nonatomic) IBOutlet UITextField *txtBeerVal;
@property (strong, nonatomic) IBOutlet UILabel *lblHeader;
@property (strong, nonatomic) IBOutlet UISwitch *swchSharing;

- (IBAction)btnInfo:(id)sender;

- (IBAction)btn_Back:(id)sender;
- (IBAction)btnEdit:(id)sender;
- (IBAction)btnPreviewTaste:(id)sender;
- (IBAction)swchSharing:(id)sender;
- (IBAction)ViewHistory:(id)sender;

@end
