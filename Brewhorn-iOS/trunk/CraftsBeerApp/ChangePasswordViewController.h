//
//  ChangePasswordViewController.h
//  CraftsBeerApp
//
//  Created by Mandeep Singh on 01/08/13.
//  Copyright (c) 2013 Mandeep Singh. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ChangePasswordViewController : UIViewController

@property (strong, nonatomic) IBOutlet UITextField *oldPassword;
@property (strong, nonatomic) IBOutlet UITextField *txtConfirm;
@property (strong, nonatomic) IBOutlet UITextField *txtPassword;
@property (strong, nonatomic) IBOutlet UILabel *lblHeader;

- (IBAction)btnCancel:(id)sender;
- (IBAction)btnChange:(id)sender;

@end
