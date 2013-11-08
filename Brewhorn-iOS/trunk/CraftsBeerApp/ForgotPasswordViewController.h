//
//  ForgotPasswordViewController.h
//  CraftBeer
//
//  Created by Mandeep Singh on 07/06/13.
//  Copyright (c) 2013 Mandeep Singh. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ForgotPasswordViewController : UIViewController
{
    NSMutableData *receiveData;
    NSURLConnection *theConnection;
}


@property (strong, nonatomic) IBOutlet UILabel *lblHeader;
@property (retain, nonatomic) IBOutlet UITextField *txtUserName;

- (IBAction)btn_Submit:(id)sender;
- (IBAction)btnBack:(id)sender;

@end
