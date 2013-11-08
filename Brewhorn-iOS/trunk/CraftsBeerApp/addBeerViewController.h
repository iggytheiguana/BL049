//
//  addBeerViewController.h
//  CraftsBeerApp
//
//  Created by Mandeep Singh on 11/06/13.
//  Copyright (c) 2013 Mandeep Singh. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface addBeerViewController : UIViewController<UIActionSheetDelegate,UIPickerViewDelegate,UIPickerViewDataSource>
{
    float valAroma;
    float valSweet;
    float valBitter;
    float valMalt;
    float valYeast;
    float valMouthFeel;
    float valSour;
    float valAdditive;
    float valBooziness;
    
    UIActionSheet *datePickerViewPopup;
    UIActionSheet *pickerViewPopup;
    UIActionSheet *actionSheet;
    UIPickerView *pickerView1;
    UIPickerView *pickerViewStyle;

    NSMutableArray *arrayMood;
    NSMutableArray *arrayVenue;
    NSMutableArray *arrayEvent;
    NSMutableArray *arrayHype;
    NSMutableArray *arrayPicker;
    NSMutableArray *memoryArray1;
    NSMutableArray *memoryArray2;
    NSMutableArray *memoryArray3;
    
    NSMutableArray *likeArray1;
    NSMutableArray *likeArray2;
    NSMutableArray *likeArray3;
    NSMutableArray *arrayFruitBeer;


    
    int componentValue;

    int selectPicker;
    
    NSString *strStyleOldValue;
    NSString *strMoodOldValue;
    NSString *strVenueOldValue;
    NSString *strEventOldValue;
    NSString *strHipeOldValue;
}

@property (strong, nonatomic) IBOutlet UITextField *txtBrewery;
@property (strong, nonatomic) IBOutlet UITextField *txtBeerName;
@property (strong, nonatomic) IBOutlet UITextField *txtStyle;
@property (strong, nonatomic) IBOutlet UITextField *txtABV;
@property (strong, nonatomic) IBOutlet UITextField *txtIBU;
@property (strong, nonatomic) IBOutlet UITextField *txtMood;
@property (strong, nonatomic) IBOutlet UITextField *txtVenue;
@property (strong, nonatomic) IBOutlet UITextField *txtEvent;
@property (strong, nonatomic) IBOutlet UITextField *txtHipe;
@property (strong, nonatomic) NSString *strEditMode;
@property (strong, nonatomic) NSString *strBeerid;
@property (assign, nonatomic) int chkHeadder;
@property (nonatomic,retain) NSMutableArray *array;
@property (strong, nonatomic) IBOutlet UIScrollView *scrlView;
@property (strong, nonatomic) IBOutlet UILabel *lblHeader;
@property (strong, nonatomic) IBOutlet UIButton *btnStyle;
@property (strong, nonatomic) IBOutlet UIImageView *imgBrName;
@property (strong, nonatomic) IBOutlet UIImageView *imgBeerName;
@property (strong, nonatomic) IBOutlet UIImageView *imgStyle;


- (IBAction)btn_Save:(id)sender;
- (IBAction)btn_Style:(id)sender;
- (IBAction)btn_Mood:(id)sender;
- (IBAction)btn_Venue:(id)sender;
- (IBAction)btn_Event:(id)sender;
- (IBAction)btn_Hipe:(id)sender;
- (IBAction)btn_Back:(id)sender;
- (IBAction)btnResignView:(id)sender;
- (IBAction)info:(id)sender;

@end
