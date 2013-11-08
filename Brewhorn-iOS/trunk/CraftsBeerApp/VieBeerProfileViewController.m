//
//  VieBeerProfileViewController.m
//  CraftsBeerApp
//
//  Created by Mandeep Singh on 07/08/13.
//  Copyright (c) 2013 Mandeep Singh. All rights reserved.
//

#import "VieBeerProfileViewController.h"
#import "addBeerViewController.h"

@interface VieBeerProfileViewController ()

@end

@implementation VieBeerProfileViewController
@synthesize txtABV,txtBeerName,txtBrewery,txtEvent,txtHipe,txtIBU,txtMood,txtStyle,txtVenue,array,scrlView,lblHeader;
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
    if ([[UIScreen mainScreen]bounds].size.height==568) {
        scrlView.contentSize=CGSizeMake(320, 480);
    }
    else
    {
        scrlView.contentSize=CGSizeMake(320, 380);
    }
    txtABV.text=[array valueForKey:@"abv"];
    txtBeerName.text=[array valueForKey:@"beerName"];
    txtBrewery.text=[array valueForKey:@"brewery"];
    txtEvent.text=[array valueForKey:@"event"];
    txtHipe.text=[array valueForKey:@"hype"];
    txtIBU.text=[array valueForKey:@"ibu"];
    txtMood.text=[array valueForKey:@"mood"];
    txtStyle.text=[array valueForKey:@"beerStyle"];
    txtVenue.text=[array valueForKey:@"venue"];
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
}
- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}
#pragma mark - Button actions
- (IBAction)btn_Update:(id)sender
{
    addBeerViewController *addBeer=[[addBeerViewController alloc]init];
    addBeer.strEditMode=@"2";
    addBeer.strBeerid=[array valueForKey:@"beerId"];
    [self.navigationController pushViewController:addBeer animated:YES];
}
- (IBAction)btnCancel:(id)sender
{
    [self.navigationController popViewControllerAnimated:YES];
}
- (void)viewDidUnload {
    [self setScrlView:nil];
    [super viewDidUnload];
}
@end
