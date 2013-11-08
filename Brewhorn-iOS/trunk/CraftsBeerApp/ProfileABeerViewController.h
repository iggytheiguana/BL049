//
//  ProfileABeerViewController.h
//  CraftsBeerApp
//
//  Created by Mandeep Singh on 30/07/13.
//  Copyright (c) 2013 Mandeep Singh. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ProfileABeerViewController : UIViewController
{
    float valSour;
    float valAdditive;
    float valBooziness;
    
    int   chkButton;
    
    int aromaMin;
    int SweetMin;
    int maltMin;
    
    int aromaMax;
    int SweetMax;
    int maltMax;
    int strCompare;
    
    
}
@property (strong, nonatomic) IBOutlet UILabel *lblHeader;

@property (strong, nonatomic) IBOutlet UILabel *lblTop;
@property (strong, nonatomic) IBOutlet UISlider *sliderSour;
@property (strong, nonatomic) IBOutlet UISlider *sliderAddictive;
@property (strong, nonatomic) IBOutlet UISlider *sliderBooziness;
@property (strong, nonatomic) NSString *valAroma;
@property (strong, nonatomic) NSString * valSweet;
@property (strong, nonatomic) NSString * valBitter;
@property (strong, nonatomic) NSString * valMalt;
@property (strong, nonatomic) NSString * valYeast;
@property (strong, nonatomic) NSString * valMouthFeel;
@property (strong, nonatomic) NSString *strChkUser;
@property (strong, nonatomic) NSString *striD;

@property (strong, nonatomic) IBOutlet UILabel *lblAroma3;
@property (strong, nonatomic) IBOutlet UILabel *lblAroma4;
@property (strong, nonatomic) IBOutlet UILabel *lblAroma5;
@property (strong, nonatomic) IBOutlet UILabel *lblAroma6;
@property (strong, nonatomic) IBOutlet UILabel *lblAroma7;

@property (strong, nonatomic) IBOutlet UILabel *lblSweet3;
@property (strong, nonatomic) IBOutlet UILabel *lblSweet4;
@property (strong, nonatomic) IBOutlet UILabel *lblSweet5;
@property (strong, nonatomic) IBOutlet UILabel *lblSweet6;
@property (strong, nonatomic) IBOutlet UILabel *lblSweet7;

@property (strong, nonatomic) IBOutlet UILabel *lblBitter3;
@property (strong, nonatomic) IBOutlet UILabel *lblBitter4;
@property (strong, nonatomic) IBOutlet UILabel *lblBitter5;
@property (strong, nonatomic) IBOutlet UILabel *lblBitter6;
@property (strong, nonatomic) IBOutlet UILabel *lblBitter7;


- (IBAction)btnBack:(id)sender;
- (IBAction)btnSave:(id)sender;
- (IBAction)slider_Sour:(id)sender;
- (IBAction)slider_Addictive:(id)sender;
- (IBAction)slider_Booziness:(id)sender;

@end
