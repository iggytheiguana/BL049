//
//  ViewImageViewController.m
//  CraftsBeerApp
//
//  Created by Mandeep Singh on 01/08/13.
//  Copyright (c) 2013 Mandeep Singh. All rights reserved.
//

#import "ViewImageViewController.h"
#import "VieBeerProfileViewController.h"
#import "addBeerViewController.h"
#import "MatchInfoViewController.h"
#import "iToast.h"

@interface ViewImageViewController ()

@end
@implementation ViewImageViewController
@synthesize strimg,imgView,lblBeer,array,strChkBeer;
- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}
#pragma mark - View lifecycle
- (void)viewDidLoad
{
    
    if ([strChkBeer integerValue]==0) {
        lblBeer.text=[NSString stringWithFormat:@"You can be the first to profile this beer"];
    }
    else
    {
        lblBeer.text=[NSString stringWithFormat:@"This beer matches %@ percent with your taste profile",strimg];
    }
    strimg=[NSString stringWithFormat:@"%@.png",strimg];
    imgView.image=[UIImage imageNamed:strimg];
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
}
- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}
- (void)viewDidUnload {
    [self setImgView:nil];
    [self setLblBeer:nil];
    [super viewDidUnload];
}
#pragma mark - Button action
- (IBAction)btnBack:(id)sender
{
    [self.navigationController popViewControllerAnimated:YES];
}

- (IBAction)btnViewProfile:(id)sender
{
    addBeerViewController *addBeer=[[addBeerViewController alloc]init];
    addBeer.strEditMode=@"2";
    addBeer.array=array;
    
    if ([strChkBeer integerValue]==0){
        addBeer.chkHeadder=2;
    }
    else
    {
        addBeer.chkHeadder=1;
    }
    addBeer.strBeerid=[array valueForKey:@"beerId"];
    [self.navigationController pushViewController:addBeer animated:YES];
}

- (IBAction)btnInfo:(id)sender
{
    MatchInfoViewController *info=[[MatchInfoViewController alloc]init];
    [self.navigationController presentViewController:info animated:YES completion:nil];
}


@end
