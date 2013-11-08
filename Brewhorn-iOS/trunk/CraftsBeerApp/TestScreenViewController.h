//
//  TestScreenViewController.h
//  CraftsBeerApp
//
//  Created by Mandeep Singh on 02/08/13.
//  Copyright (c) 2013 Mandeep Singh. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface TestScreenViewController : UIViewController
{
    NSMutableArray *arrayAromaTable;
    NSMutableArray *arraySweetTable;
    NSMutableArray *arrayBitterTable;
    NSMutableArray *arrayAromaValue;
    NSMutableArray *arrayBitterValue;
    NSMutableArray *arraySweetValue;
    
    NSString *strAr;
    NSString *strBi;
    NSString *strSw;

    float valAroma;
    float valSweet;
    float valBitter;
    
    int aromaMin;
    int SweetMin;
    int bitterMin;
    
    int aromaMax;
    int SweetMax;
    int bitterMax;
    int strCompare;
    
    int chkButton;
    int chkArray;
}
@property (strong, nonatomic) IBOutlet UITableView *tblArray;
@property (strong, nonatomic) IBOutlet UIView *viewArray;
@property (strong, nonatomic) IBOutlet UITextView *txtBitter;
@property (strong, nonatomic) IBOutlet UITextView *txtSweet;
@property (strong, nonatomic) IBOutlet UITextView *txtAroma;
@property (strong, nonatomic) IBOutlet UISlider *sliderBitter;
@property (strong, nonatomic) IBOutlet UISlider *sliderSweet;
@property (strong, nonatomic) IBOutlet UISlider *sliderAroma;

@property (nonatomic,strong)NSString *strid;
@property (nonatomic,strong)NSString *chkUser;
@property (nonatomic,strong)NSMutableArray *array;
@property (strong, nonatomic) IBOutlet UIButton *btnAroma;
@property (strong, nonatomic) IBOutlet UIButton *btnSweet;
@property (strong, nonatomic) IBOutlet UIButton *btnBitter;
@property (strong, nonatomic) IBOutlet UIScrollView *scrlView;
@property (strong, nonatomic) IBOutlet UILabel *lblHeader;


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


- (IBAction)btnAroma:(id)sender;
- (IBAction)btnDone:(id)sender;
- (IBAction)btnBitter:(id)sender;
- (IBAction)btnSweet:(id)sender;
- (IBAction)slider_Bitter:(id)sender;
- (IBAction)slider_Sweet:(id)sender;
- (IBAction)slider_Aroma:(id)sender;
- (IBAction)btnSave:(id)sender;
- (IBAction)btnCancel:(id)sender;
- (IBAction)btn_info:(id)sender;
- (IBAction)aroma:(id)sender;
- (IBAction)sweet:(id)sender;
- (IBAction)bitter:(id)sender;

@end
