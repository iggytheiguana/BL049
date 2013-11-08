//
//  RegisterViewController.h
//  CraftBeer
//
//  Created by Mandeep Singh on 07/06/13.
//  Copyright (c) 2013 Mandeep Singh. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface RegisterViewController : UIViewController
{
    NSMutableArray *arraySelectBeer;
    // for parsing
    NSMutableData *recieveData;
    NSURLConnection *theConnection;
    
    NSString *strPickerOldVal;
    int btnTerms;

}
// to make the background dull when the picker comes
@property (strong, nonatomic) IBOutlet UIView *viewBackground;

@property (strong, nonatomic) IBOutlet UITextField *txtFirstName;
@property (strong, nonatomic) IBOutlet UITextField *tztLastName;
@property (strong, nonatomic) IBOutlet UITextField *txtUserName;
@property (strong, nonatomic) IBOutlet UITextField *txtPassword;
@property (strong, nonatomic) IBOutlet UITextField *txtConfirmPassword;
@property (strong, nonatomic) IBOutlet UITextField *txtEmail;
@property (strong, nonatomic) IBOutlet UITextField *txtZip;
@property (strong, nonatomic) IBOutlet UIPickerView *PickerSelectBeer;
@property (strong, nonatomic) NSString *txt;
@property (strong, nonatomic) IBOutlet UIView *viewTextFields;
@property (strong, nonatomic) IBOutlet UIButton *btnSelectBeer;
@property (strong, nonatomic) IBOutlet UIToolbar *toolBar;
@property (strong, nonatomic) IBOutlet UIPickerView *pickerStyle;
@property (strong, nonatomic) IBOutlet UILabel *lblHeader;
@property (strong, nonatomic) IBOutlet UIButton *btnChkMark;

#pragma mark button methods
- (IBAction)btnBack:(id)sender;
- (IBAction)btnSelectBeer:(id)sender;
- (IBAction)btnSubmit:(id)sender;
- (IBAction)btnResign:(id)sender;
- (IBAction)CancelBtn:(id)sender;
- (IBAction)btnDone:(id)sender;
- (IBAction)btnInfo:(id)sender;
- (IBAction)btnTermsAndConditions:(id)sender;
- (IBAction)BtnCheck:(id)sender;


@end
