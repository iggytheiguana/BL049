//
//  userTasteProfileViewController.h
//  CraftsBeerApp
//
//  Created by Mandeep Singh on 01/08/13.
//  Copyright (c) 2013 Mandeep Singh. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface userTasteProfileViewController : UIViewController
{
    float valAroma;
    float valSweet;
    float valBitter;
    float valMalt;
    float valYeast;
    float valMouthFeel;
}

@property (strong, nonatomic) IBOutlet UISlider *sliderSweet;
@property (strong, nonatomic) IBOutlet UISlider *sliderAroma;
@property (strong, nonatomic) IBOutlet UISlider *sliderBitter;
@property (strong, nonatomic) IBOutlet UISlider *sliderMalt;
@property (strong, nonatomic) IBOutlet UISlider *sliderYeast;
@property (strong, nonatomic) IBOutlet UISlider *sliderMouthFeel;

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

@property (strong, nonatomic) IBOutlet UILabel *lblMalt3;
@property (strong, nonatomic) IBOutlet UILabel *lblMalt4;
@property (strong, nonatomic) IBOutlet UILabel *lblMalt5;
@property (strong, nonatomic) IBOutlet UILabel *lblMalt6;
@property (strong, nonatomic) IBOutlet UILabel *lblMalt7;


@property (strong, nonatomic) IBOutlet UILabel *lblYeast3;
@property (strong, nonatomic) IBOutlet UILabel *lblYeast4;
@property (strong, nonatomic) IBOutlet UILabel *lblYeast5;
@property (strong, nonatomic) IBOutlet UILabel *lblYeast6;
@property (strong, nonatomic) IBOutlet UILabel *lblYeast7;

@property (strong, nonatomic) IBOutlet UILabel *lblMouth3;
@property (strong, nonatomic) IBOutlet UILabel *lblMouth4;
@property (strong, nonatomic) IBOutlet UILabel *lblMouth5;
@property (strong, nonatomic) IBOutlet UILabel *lblMouth6;
@property (strong, nonatomic) IBOutlet UILabel *lblMouth7;

@property (strong, nonatomic) IBOutlet UILabel *lblHeader;

- (IBAction)btn_Back:(id)sender;
- (IBAction)btnEdit:(id)sender;

@end
