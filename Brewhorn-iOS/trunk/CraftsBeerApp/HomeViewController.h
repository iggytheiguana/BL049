//
//  HomeViewController.h
//  CraftsBeerApp
//
//  Created by Mandeep Singh on 11/06/13.
//  Copyright (c) 2013 Mandeep Singh. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface HomeViewController : UIViewController
{
    NSMutableArray *arrayBeer;
    
}

@property (strong, nonatomic) IBOutlet UITextField *txtBeerName;
@property (strong, nonatomic) IBOutlet UITableView *tblBeer;
@property (strong, nonatomic) IBOutlet UILabel *createNewBeer;
@property (strong, nonatomic) IBOutlet UIButton *btnAddNew;
@property (strong, nonatomic) IBOutlet UIImageView *imgTblBeer;
@property (strong, nonatomic) IBOutlet UIImageView *imgSearch;
@property (strong, nonatomic) IBOutlet UIButton *btnSearch;
@property (strong, nonatomic) IBOutlet UILabel *lblHeader;
@property (strong, nonatomic) IBOutlet UILabel *lblHints;

- (IBAction)earchBeer:(id)sender;
- (IBAction)ddeeerw:(id)sender;
- (IBAction)btnShare:(id)sender;
- (IBAction)btnEditUserProfile:(id)sender;
- (IBAction)info:(id)sender;


@end
