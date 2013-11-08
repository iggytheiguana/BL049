//
//  HistoryViewController.h
//  CraftsBeerApp
//
//  Created by Mandeep Singh on 15/10/13.
//  Copyright (c) 2013 Mandeep Singh. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface HistoryViewController : UIViewController
{
    NSMutableArray *arrayHistory;
}

@property (strong, nonatomic) IBOutlet UITableView *tblHistory;

- (IBAction)btnBack:(id)sender;

@end
