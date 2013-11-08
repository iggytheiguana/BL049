//
//  ViewImageViewController.h
//  CraftsBeerApp
//
//  Created by Mandeep Singh on 01/08/13.
//  Copyright (c) 2013 Mandeep Singh. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ViewImageViewController : UIViewController

@property (nonatomic,retain) NSString *strimg;
@property (nonatomic,retain) NSMutableArray *array;
@property (nonatomic,retain) NSString *strChkBeer;


@property (strong, nonatomic) IBOutlet UIImageView *imgView;
@property (strong, nonatomic) IBOutlet UILabel *lblBeer;

- (IBAction)btnBack:(id)sender;
- (IBAction)btnViewProfile:(id)sender;
- (IBAction)btnInfo:(id)sender;

@end
