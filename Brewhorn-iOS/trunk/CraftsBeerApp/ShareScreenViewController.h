//
//  ShareScreenViewController.h
//  CraftsBeerApp
//
//  Created by Mandeep Singh on 23/07/13.
//  Copyright (c) 2013 Mandeep Singh. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <Social/Social.h>
#import <Twitter/Twitter.h>
#import <FacebookSDK/FacebookSDK.h>
#import "FbGraph.h"
#import "SBJSON.h"

@interface ShareScreenViewController : UIViewController<FBLoginViewDelegate>
@property (strong, nonatomic) IBOutlet UILabel *lblHeader;
@property (retain, nonatomic) FbGraph *fbGraph;
@property (strong, nonatomic) IBOutlet UIButton *btnSkip;
@property (strong, nonatomic) IBOutlet UIButton *btnBack;
@property (assign, nonatomic) int chkSkip;

- (IBAction)btnSkip:(id)sender;
- (IBAction)btnEdit:(id)sender;
- (IBAction)btnFb:(id)sender;
- (IBAction)btnTwitter:(id)sender;
- (IBAction)btnCancel:(id)sender;

@end
