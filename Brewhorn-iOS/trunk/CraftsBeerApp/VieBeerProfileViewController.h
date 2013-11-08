//
//  VieBeerProfileViewController.h
//  CraftsBeerApp
//
//  Created by Mandeep Singh on 07/08/13.
//  Copyright (c) 2013 Mandeep Singh. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface VieBeerProfileViewController : UIViewController

@property (nonatomic,retain) NSMutableArray *array;

@property (strong, nonatomic) IBOutlet UILabel *txtBrewery;
@property (strong, nonatomic) IBOutlet UILabel *txtBeerName;
@property (strong, nonatomic) IBOutlet UILabel *txtStyle;
@property (strong, nonatomic) IBOutlet UILabel *txtABV;
@property (strong, nonatomic) IBOutlet UILabel *txtIBU;
@property (strong, nonatomic) IBOutlet UILabel *txtMood;
@property (strong, nonatomic) IBOutlet UILabel *txtVenue;
@property (strong, nonatomic) IBOutlet UILabel *txtEvent;
@property (strong, nonatomic) IBOutlet UILabel *txtHipe;
@property (strong, nonatomic) IBOutlet UIScrollView *scrlView;
@property (strong, nonatomic) IBOutlet UILabel *lblHeader;

- (IBAction)btn_Update:(id)sender;
- (IBAction)btnCancel:(id)sender;

@end
