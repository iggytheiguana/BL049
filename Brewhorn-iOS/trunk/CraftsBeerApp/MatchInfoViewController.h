//
//  MatchInfoViewController.h
//  CraftsBeerApp
//
//  Created by Mandeep Singh on 20/09/13.
//  Copyright (c) 2013 Mandeep Singh. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface MatchInfoViewController : UIViewController
@property (strong, nonatomic) IBOutlet UIWebView *webView;
@property (strong, nonatomic) IBOutlet UIScrollView *scrlView;


- (IBAction)btnBack:(id)sender;

@end
