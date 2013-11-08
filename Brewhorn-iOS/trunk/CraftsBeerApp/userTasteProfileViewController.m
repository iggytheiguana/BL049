//
//  userTasteProfileViewController.m
//  CraftsBeerApp
//
//  Created by Mandeep Singh on 01/08/13.
//  Copyright (c) 2013 Mandeep Singh. All rights reserved.
//

#import "userTasteProfileViewController.h"
#import "BeerDetails1ViewController.h"

@interface userTasteProfileViewController ()

@end

@implementation userTasteProfileViewController

@synthesize sliderAroma,sliderBitter,sliderMalt,sliderMouthFeel,sliderSweet,sliderYeast;
@synthesize lblAroma3,lblAroma4,lblAroma5,lblAroma6,lblAroma7,lblBitter3,lblBitter4,lblBitter5,lblBitter6,lblBitter7,lblMalt3,lblMalt4,lblMalt5,lblMalt6,lblMalt7,lblMouth3,lblMouth4,lblMouth5,lblMouth6,lblMouth7,lblSweet3,lblSweet4,lblSweet5,lblSweet6,lblSweet7,lblYeast3,lblYeast4,lblYeast5,lblYeast6,lblYeast7,lblHeader;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}
#pragma mark- Life cycle
- (void)viewDidLoad
{
    NSLog(@"the array val is %@",[[NSUserDefaults standardUserDefaults]objectForKey:@"arrayDetails"]);
    NSMutableArray *arrayDet=[[[NSUserDefaults standardUserDefaults]objectForKey:@"arrayDetails"]mutableCopy];
    
    sliderAroma.minimumTrackTintColor=[UIColor colorWithPatternImage:[UIImage imageNamed:@"slider_red.png"]];
    sliderBitter.minimumTrackTintColor=[UIColor colorWithPatternImage:[UIImage imageNamed:@"slider_red.png"]];
    sliderMalt.minimumTrackTintColor=[UIColor colorWithPatternImage:[UIImage imageNamed:@"slider_red.png"]];
    sliderMouthFeel.minimumTrackTintColor=[UIColor colorWithPatternImage:[UIImage imageNamed:@"slider_red.png"]];
    sliderSweet.minimumTrackTintColor=[UIColor colorWithPatternImage:[UIImage imageNamed:@"slider_red.png"]];
    sliderYeast.minimumTrackTintColor=[UIColor colorWithPatternImage:[UIImage imageNamed:@"slider_red.png"]];
    
    sliderAroma.maximumTrackTintColor=[UIColor colorWithPatternImage:[UIImage imageNamed:@"slider_yellow.png"]];
    sliderBitter.maximumTrackTintColor=[UIColor colorWithPatternImage:[UIImage imageNamed:@"slider_yellow.png"]];
    sliderMalt.maximumTrackTintColor=[UIColor colorWithPatternImage:[UIImage imageNamed:@"slider_yellow.png"]];
    sliderMouthFeel.maximumTrackTintColor=[UIColor colorWithPatternImage:[UIImage imageNamed:@"slider_yellow.png"]];
    sliderSweet.maximumTrackTintColor=[UIColor colorWithPatternImage:[UIImage imageNamed:@"slider_yellow.png"]];
    sliderYeast.maximumTrackTintColor=[UIColor colorWithPatternImage:[UIImage imageNamed:@"slider_yellow.png"]];
    
    sliderAroma.thumbTintColor=[UIColor colorWithPatternImage:[UIImage imageNamed:@"slider_handle.png"]];
    sliderBitter.thumbTintColor=[UIColor colorWithPatternImage:[UIImage imageNamed:@"slider_handle.png"]];
    sliderMalt.thumbTintColor=[UIColor colorWithPatternImage:[UIImage imageNamed:@"slider_handle.png"]];
    sliderMouthFeel.thumbTintColor=[UIColor colorWithPatternImage:[UIImage imageNamed:@"slider_handle.png"]];
    sliderSweet.thumbTintColor=[UIColor colorWithPatternImage:[UIImage imageNamed:@"slider_handle.png"]];
    sliderYeast.thumbTintColor=[UIColor colorWithPatternImage:[UIImage imageNamed:@"slider_handle.png"]];
    
    {
        sliderAroma.value=[[arrayDet objectAtIndex:0]floatValue];
        valAroma=[[arrayDet objectAtIndex:0]integerValue];
        sliderSweet.value=[[arrayDet objectAtIndex:1]floatValue];
        valSweet=[[arrayDet objectAtIndex:1]integerValue];
        sliderMalt.value=[[arrayDet objectAtIndex:3]floatValue];
        valMalt=[[arrayDet objectAtIndex:3]integerValue];
        sliderBitter.value=[[arrayDet objectAtIndex:2]floatValue];
        valBitter=[[arrayDet objectAtIndex:2]integerValue];
        sliderYeast.value=[[arrayDet objectAtIndex:4]floatValue];
        valYeast=[[arrayDet objectAtIndex:4]integerValue];
        sliderMouthFeel.value=[[arrayDet objectAtIndex:5]floatValue];
        valMouthFeel=[[arrayDet objectAtIndex:5]integerValue];
    }
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)viewDidUnload {
    [self setSliderSweet:nil];
    [self setSliderAroma:nil];
    [self setSliderBitter:nil];
    [self setSliderMalt:nil];
    [self setSliderYeast:nil];
    [self setSliderMouthFeel:nil];
    [super viewDidUnload];
}
#pragma mark- Button actions
- (IBAction)btn_Back:(id)sender
{
    [self.navigationController popViewControllerAnimated:YES];
}

- (IBAction)btnEdit:(id)sender
{
    NSString *strVal=[[NSUserDefaults standardUserDefaults]valueForKey:@"user_id"];
    
    BeerDetails1ViewController *beerDetails=[[BeerDetails1ViewController alloc]init];
    beerDetails.strChkUser=@"2";
    beerDetails.ChkView=@"2";
    beerDetails.striD=strVal;
    [self.navigationController pushViewController:beerDetails animated:YES];
}
@end
