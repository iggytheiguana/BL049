//
//  EditUserProfileViewController.h
//  CraftsBeerApp
//
//  Created by Mandeep Singh on 01/08/13.
//  Copyright (c) 2013 Mandeep Singh. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface EditUserProfileViewController : UIViewController
{
    NSMutableArray *arraySelectBeer;
    // for parsing
    NSMutableData *recieveData;
    NSURLConnection *theConnection;
    NSString *strPickerOldVal;
    
}
// to make the background dull when the picker comes
@property (strong, nonatomic) IBOutlet UIView *viewBackground;

@property (strong, nonatomic) IBOutlet UITextField *txtFirstName;
@property (strong, nonatomic) IBOutlet UITextField *tztLastName;
@property (strong, nonatomic) IBOutlet UITextField *txtUserName;
@property (strong, nonatomic) IBOutlet UITextField *txtEmail;
@property (strong, nonatomic) IBOutlet UITextField *txtZip;
@property (strong, nonatomic) IBOutlet UIPickerView *PickerSelectBeer;
@property (strong, nonatomic) NSString *txt;
@property (strong, nonatomic) IBOutlet UIView *viewTextFields;
@property (strong, nonatomic) IBOutlet UIButton *btnSelectBeer;
@property (strong, nonatomic) IBOutlet UIToolbar *toolBar;
@property (strong, nonatomic) IBOutlet UIPickerView *pickerStyle;
@property (strong, nonatomic) NSString *strFirstName;
@property (strong, nonatomic) NSString *strLastName;
@property (strong, nonatomic) NSString *strUserName;
@property (strong, nonatomic) NSString *strZip;
@property (strong, nonatomic) NSString *strBeerExperience;
@property (strong, nonatomic) NSString *strEmail;
@property (strong, nonatomic) IBOutlet UILabel *lblHeader;
@property (strong, nonatomic) IBOutlet UISwitch *swchSharing;


// button actions
- (IBAction)btnBack:(id)sender;
- (IBAction)btnSelectBeer:(id)sender;
- (IBAction)btnSubmit:(id)sender;
- (IBAction)btnResign:(id)sender;
- (IBAction)CancelBtn:(id)sender;
- (IBAction)btnDone:(id)sender;
- (IBAction)swtchSharing:(id)sender;
- (IBAction)btnChangePassword:(id)sender;

@end
