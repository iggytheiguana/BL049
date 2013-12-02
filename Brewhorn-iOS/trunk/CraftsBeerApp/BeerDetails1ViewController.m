//
//  BeerDetails1ViewController.m
//  CraftsBeerApp
//
//  Created by Mandeep Singh on 11/06/13.
//  Copyright (c) 2013 Mandeep Singh. All rights reserved.
//

#import "BeerDetails1ViewController.h"
#import "BeerDetails2ViewController.h"
#import "ProfileABeerViewController.h"
#import "YourTasteInfoViewController.h"
#import "HomeViewController.h"
#import "JSON.h"
#import "iToast.h"
#import "Flurry.h"

@interface BeerDetails1ViewController ()

@end

@implementation BeerDetails1ViewController

@synthesize sliderAroma,sliderBitter,sliderMalt,sliderMouthFeel,sliderSweet,sliderYeast,strChkUser,striD;

@synthesize lblAroma3,lblAroma4,lblAroma5,lblAroma6,lblAroma7,lblBitter3,lblBitter4,lblBitter5,lblBitter6,lblBitter7,lblMalt3,lblMalt4,lblMalt5,lblMalt6,lblMalt7,lblMouth3,lblMouth4,lblMouth5,lblMouth6,lblMouth7,lblSweet3,lblSweet4,lblSweet5,lblSweet6,lblSweet7,lblYeast3,lblYeast4,lblYeast5,lblYeast6,lblYeast7,lblTop,ChkView,btnBck,lblHeader;
- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}
#pragma mark view life cycle
- (void)viewDidLoad
{
    if ([strChkUser isEqualToString: @"2"]) {
        lblTop.text=@"Create Your Beer Profile";
        
        [[[[iToast makeText:@"Tap the parameter for a description."] setGravity:iToastGravityTop offsetLeft:0 offsetTop:[[UIScreen mainScreen]bounds].size.height-140] setDuration:2000] show:iToastTypeNotice];
    }
    if ([ChkView isEqualToString:@"2"]) {
        lblTop.text=@"Edit Your Taste Profile";
        {
            [[[[[iToast makeText:@"Adjust each slider button according to how much you think you prefer that taste parameter.Tap the parameter for a description."] setGravity:iToastGravityTop offsetLeft:0 offsetTop:[[UIScreen mainScreen]bounds].size.height-140] setDuration:2000] setDuration:2500] show:iToastTypeNotice];
        }
        btnBck.hidden=NO;
    }
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
    
    
    [self.sliderMouthFeel addTarget:self action:@selector(dragEndedForSlider)forControlEvents:UIControlEventTouchUpInside];
    [self.sliderSweet addTarget:self action:@selector(dragEndedForSlider)forControlEvents:UIControlEventTouchUpInside];
    [self.sliderYeast addTarget:self action:@selector(dragEndedForSlider)forControlEvents:UIControlEventTouchUpInside];
    [self.sliderMalt addTarget:self action:@selector(dragEndedForSlider)forControlEvents:UIControlEventTouchUpInside];
    [self.sliderBitter addTarget:self action:@selector(dragEndedForSlider)forControlEvents:UIControlEventTouchUpInside];
    [self.sliderAroma addTarget:self action:@selector(dragEndedForSlider)forControlEvents:UIControlEventTouchUpInside];
    
    NSLog(@"the array val is %@",[[NSUserDefaults standardUserDefaults]objectForKey:@"arrayDetails"]);
    NSMutableArray *arrayDet=[[[NSUserDefaults standardUserDefaults]objectForKey:@"arrayDetails"]mutableCopy];
    if ([strChkUser isEqualToString:@"2"])
    {
        lblAroma7.hidden=NO;
        lblAroma6.hidden=NO;
        lblAroma5.hidden=NO;
        lblAroma4.hidden=NO;
        lblAroma3.hidden=NO;
        valSweet=3;
        valYeast=3;
        valMouthFeel=3;
        valMalt=3;
        valBitter=3;
        valAroma=3;
        if ([ChkView isEqualToString:@"2"])
        {
            
            lblAroma7.hidden=NO;
            lblAroma6.hidden=NO;
            lblAroma5.hidden=NO;
            lblAroma4.hidden=NO;
            lblAroma3.hidden=NO;
            
            lblBitter3.hidden=NO;
            lblBitter4.hidden=NO;
            lblBitter5.hidden=NO;
            lblBitter6.hidden=NO;
            lblBitter7.hidden=NO;
            
            lblSweet7.hidden=NO;
            lblSweet6.hidden=NO;
            lblSweet5.hidden=NO;
            lblSweet4.hidden=NO;
            lblSweet3.hidden=NO;
            
            lblMalt3.hidden=NO;
            lblMalt4.hidden=NO;
            lblMalt5.hidden=NO;
            lblMalt6.hidden=NO;
            lblMalt7.hidden=NO;
            
            lblYeast7.hidden=NO;
            lblYeast6.hidden=NO;
            lblYeast5.hidden=NO;
            lblYeast4.hidden=NO;
            lblYeast3.hidden=NO;
            
            lblMouth3.hidden=NO;
            lblMouth4.hidden=NO;
            lblMouth5.hidden=NO;
            lblMouth6.hidden=NO;
            lblMouth7.hidden=NO;
            
            
            
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
        
    }
    else
    {
        
        lblAroma7.hidden=NO;
        lblAroma6.hidden=NO;
        lblAroma5.hidden=NO;
        lblAroma4.hidden=NO;
        lblAroma3.hidden=NO;
        
        lblBitter3.hidden=NO;
        lblBitter4.hidden=NO;
        lblBitter5.hidden=NO;
        lblBitter6.hidden=NO;
        lblBitter7.hidden=NO;
        
        lblSweet7.hidden=NO;
        lblSweet6.hidden=NO;
        lblSweet5.hidden=NO;
        lblSweet4.hidden=NO;
        lblSweet3.hidden=NO;
        
        lblMalt3.hidden=NO;
        lblMalt4.hidden=NO;
        lblMalt5.hidden=NO;
        lblMalt6.hidden=NO;
        lblMalt7.hidden=NO;
        
        lblYeast7.hidden=NO;
        lblYeast6.hidden=NO;
        lblYeast5.hidden=NO;
        lblYeast4.hidden=NO;
        lblYeast3.hidden=NO;
        
        lblMouth3.hidden=NO;
        lblMouth4.hidden=NO;
        lblMouth5.hidden=NO;
        lblMouth6.hidden=NO;
        lblMouth7.hidden=NO;
        
        
        aromaMin=[[arrayDet objectAtIndex:0]integerValue]-2;
        aromaMax=[[arrayDet objectAtIndex:0]integerValue]+2;
        sliderAroma.minimumValue=aromaMin;
        sliderAroma.maximumValue=aromaMax;
        sliderAroma.value=[[arrayDet objectAtIndex:0]floatValue];
        valAroma=[[arrayDet objectAtIndex:0]integerValue];
        lblAroma3.text=[NSString stringWithFormat:@"%i",aromaMin];
        lblAroma4.text=[NSString stringWithFormat:@"%i",aromaMin+1];
        lblAroma5.text=[NSString stringWithFormat:@"%i",aromaMin+2];
        lblAroma6.text=[NSString stringWithFormat:@"%i",aromaMin+3];
        lblAroma7.text=[NSString stringWithFormat:@"%i",aromaMax];
        
        
        
        SweetMin=[[arrayDet objectAtIndex:1]integerValue]-2;
        SweetMax=[[arrayDet objectAtIndex:1]integerValue]+2;
        sliderSweet.minimumValue=SweetMin;
        sliderSweet.maximumValue=SweetMax;
        sliderSweet.value=[[arrayDet objectAtIndex:1]floatValue];
        valSweet=[[arrayDet objectAtIndex:1]integerValue];
        
        lblSweet3.text=[NSString stringWithFormat:@"%i",SweetMin];
        lblSweet4.text=[NSString stringWithFormat:@"%i",SweetMin+1];
        lblSweet5.text=[NSString stringWithFormat:@"%i",SweetMin+2];
        lblSweet6.text=[NSString stringWithFormat:@"%i",SweetMin+3];
        lblSweet7.text=[NSString stringWithFormat:@"%i",SweetMax];
        
        
        
        maltMin=[[arrayDet objectAtIndex:2]integerValue]-2;
        maltMax=[[arrayDet objectAtIndex:2]integerValue]+2;
        sliderMalt.minimumValue=maltMin;
        sliderMalt.maximumValue=maltMax;
        sliderMalt.value=[[arrayDet objectAtIndex:2]floatValue];
        valMalt=[[arrayDet objectAtIndex:2]integerValue];
        
        lblMalt3.text=[NSString stringWithFormat:@"%i",maltMin];
        lblMalt4.text=[NSString stringWithFormat:@"%i",maltMin+1];
        lblMalt5.text=[NSString stringWithFormat:@"%i",maltMin+2];
        lblMalt6.text=[NSString stringWithFormat:@"%i",maltMin+3];
        lblMalt7.text=[NSString stringWithFormat:@"%i",maltMax];
        
        
        bitterMin=[[arrayDet objectAtIndex:3]integerValue]-2;
        bitterMax=[[arrayDet objectAtIndex:3]integerValue]+2;
        sliderBitter.minimumValue=bitterMin;
        sliderBitter.maximumValue=bitterMax;
        sliderBitter.value=[[arrayDet objectAtIndex:3]floatValue];
        valBitter=[[arrayDet objectAtIndex:3]integerValue];
        
        lblBitter3.text=[NSString stringWithFormat:@"%i",bitterMin];
        lblBitter4.text=[NSString stringWithFormat:@"%i",bitterMin+1];
        lblBitter5.text=[NSString stringWithFormat:@"%i",bitterMin+2];
        lblBitter6.text=[NSString stringWithFormat:@"%i",bitterMin+3];
        lblBitter7.text=[NSString stringWithFormat:@"%i",bitterMax];
        
        
        
        yeastMin=[[arrayDet objectAtIndex:4]integerValue]-2;
        yeastMax=[[arrayDet objectAtIndex:4]integerValue]+2;
        sliderYeast.minimumValue=yeastMin;
        sliderYeast.maximumValue=yeastMax;
        sliderYeast.value=[[arrayDet objectAtIndex:4]floatValue];
        valYeast=[[arrayDet objectAtIndex:4]integerValue];
        
        lblYeast3.text=[NSString stringWithFormat:@"%i",yeastMin];
        lblYeast4.text=[NSString stringWithFormat:@"%i",yeastMin+1];
        lblYeast5.text=[NSString stringWithFormat:@"%i",yeastMin+2];
        lblYeast6.text=[NSString stringWithFormat:@"%i",yeastMin+3];
        lblYeast7.text=[NSString stringWithFormat:@"%i",yeastMax];
        
        
        
        mouthFeelMin=[[arrayDet objectAtIndex:5]integerValue]-2;
        mouthFeelMax=[[arrayDet objectAtIndex:5]integerValue]+2;
        sliderMouthFeel.minimumValue=mouthFeelMin;
        sliderMouthFeel.maximumValue=mouthFeelMax;
        sliderMouthFeel.value=[[arrayDet objectAtIndex:5]floatValue];
        valMouthFeel=[[arrayDet objectAtIndex:5]integerValue];
        
        
        
        lblMouth3.text=[NSString stringWithFormat:@"%i",mouthFeelMin];
        lblMouth4.text=[NSString stringWithFormat:@"%i",mouthFeelMin+1];
        lblMouth5.text=[NSString stringWithFormat:@"%i",mouthFeelMin+2];
        lblMouth6.text=[NSString stringWithFormat:@"%i",mouthFeelMin+3];
        lblMouth7.text=[NSString stringWithFormat:@"%i",mouthFeelMax];
        
        
        
        
    }
    
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}
#pragma mark Slider moved
-(void)dragEndedForSlider
{
    int strChkVal;
    if (chkButton==6) {
        strChkVal=[[NSString stringWithFormat:@"%f",valMouthFeel]integerValue];
        strCompare=[lblMouth5.text integerValue];
    }
    else if (chkButton==5)
    {
        strChkVal=[[NSString stringWithFormat:@"%f",valYeast]integerValue];
        strCompare=[lblYeast5.text integerValue];
    }
    else if (chkButton==4)
    {
        strChkVal=[[NSString stringWithFormat:@"%f",valBitter]integerValue];
        strCompare=[lblBitter5.text integerValue];
        
    }
    else if (chkButton==3)
    {
        strChkVal=[[NSString stringWithFormat:@"%f",valMalt]integerValue];
        strCompare=[lblMalt5.text integerValue];
        
    }
    else if (chkButton==2)
    {
        strChkVal=[[NSString stringWithFormat:@"%f",valSweet]integerValue];
        strCompare=[lblSweet5.text integerValue];
        
    }
    else
    {
        strChkVal=[[NSString stringWithFormat:@"%f",valAroma]integerValue];
        strCompare=[lblAroma5.text integerValue];
        
    }
    if (strChkVal==strCompare-1)
    {
        [[[[iToast makeText:@"You prefer somewhat less."] setGravity:iToastGravityTop offsetLeft:0 offsetTop:40] setDuration:500] show:iToastTypeNotice];
    }
    else if (strChkVal==strCompare)
    {
        [[[[iToast makeText:@"You prefer an average amount."] setGravity:iToastGravityTop offsetLeft:0 offsetTop:40] setDuration:500] show:iToastTypeNotice];
    }
    else if (strChkVal==strCompare-2)
    {
        [[[[iToast makeText:@"You prefer much less."] setGravity:iToastGravityTop offsetLeft:0 offsetTop:40] setDuration:500] show:iToastTypeNotice];
    }
    else if (strChkVal==strCompare+1)
    {
        [[[[iToast makeText:@"You prefer somewhat more."] setGravity:iToastGravityTop offsetLeft:0 offsetTop:40] setDuration:500] show:iToastTypeNotice];
    }
    else if (strChkVal==strCompare+2)
    {
        [[[[iToast makeText:@"You prefer much more."] setGravity:iToastGravityTop offsetLeft:0 offsetTop:40] setDuration:500] show:iToastTypeNotice];
    }
}
#pragma mark button actions
- (IBAction)btnAr:(id)sender
{
    UIAlertView *alert=[[UIAlertView alloc]initWithTitle:@"Help" message:@"How strong of an aroma do you prefer from food and drink?" delegate:nil cancelButtonTitle:@"Thanks" otherButtonTitles:nil];
    
    [alert show];
}

- (IBAction)btnSw:(id)sender
{
    UIAlertView *alert=[[UIAlertView alloc]initWithTitle:@"Help" message:@"Consider your preference for general sweetness in food and drink." delegate:nil cancelButtonTitle:@"Thanks" otherButtonTitles:nil];
    [alert show];
}

- (IBAction)btnBi:(id)sender
{
    UIAlertView *alert=[[UIAlertView alloc]initWithTitle:@"Help" message:@"Consider your preference for bitter flavors in food and drink." delegate:nil cancelButtonTitle:@"Thanks" otherButtonTitles:nil];
    [alert show];
}

- (IBAction)btnMa:(id)sender
{
    UIAlertView *alert=[[UIAlertView alloc]initWithTitle:@"Help" message:@"Consider how much you like;- Biscuits, bread- Oatmeal, toast- candies like chocolate- caramel or toffee- dark fruits like dates or prunes." delegate:nil cancelButtonTitle:@"Thanks" otherButtonTitles:nil];
    
    [alert show];
}

- (IBAction)btnYe:(id)sender
{
    UIAlertView *alert=[[UIAlertView alloc]initWithTitle:@"Help" message:@"Consider how much you like;- bubblegum or banana-clove flavors- a general spiciness (peppery flavor)- general fruitiness- white fruits such as grapes, apples, pears- Farm animal aromas (barnyard)" delegate:nil cancelButtonTitle:@"Thanks" otherButtonTitles:nil];
    [alert show];
}

- (IBAction)btnMo:(id)sender
{
    UIAlertView *alert=[[UIAlertView alloc]initWithTitle:@"Help" message:@"Do you prefer thin or thick food or drink? How much carbonation do you like?" delegate:nil cancelButtonTitle:@"Thanks" otherButtonTitles:nil];
    [alert show];
}
- (IBAction)btnNext:(id)sender
{
    [self.navigationController popViewControllerAnimated:YES];
}

- (IBAction)btnBack:(id)sender
{
    [self.navigationController popViewControllerAnimated:YES];
}
- (IBAction)btnSave:(id)sender
{
    UIAlertView *alert=[[UIAlertView alloc]initWithTitle:@"" message:@"" delegate:self cancelButtonTitle:@"Save" otherButtonTitles:@"Proceed", nil];
    if (lblTop.text=@"Edit Your Taste Profile") {
        alert.message=@"You must proceed to edit the optional taste parameters.";
        [Flurry logEvent:@"EditUserTasteProfile"];
    }
    else
    {
        alert.message=@"Would you like to add optional parameters.";
        [Flurry logEvent:@"UserTasteProfile"];
    }
    
    
    [alert show];
    return;
}

- (IBAction)btnInfo:(id)sender
{
    YourTasteInfoViewController *tasteInfo=[[YourTasteInfoViewController alloc]init];
    [self.navigationController presentViewController:tasteInfo animated:YES completion:nil];
}

#pragma mark slider value changed
- (IBAction)slder_MouthFeel:(id)sender
{
    chkButton=6;
    valMouthFeel=sliderMouthFeel.value;
    valMouthFeel = lroundf(sliderMouthFeel.value);
    [sliderMouthFeel setValue:valMouthFeel animated:YES];
    
}

- (IBAction)slider_Yeast:(id)sender
{
    chkButton=5;
    valYeast=sliderYeast.value;
    valYeast = lroundf(sliderYeast.value);
    [sliderYeast setValue:valYeast animated:YES];
    
}

- (IBAction)slider_Bitter:(id)sender
{
    chkButton=4;
    valBitter=sliderBitter.value;
    valBitter = lroundf(sliderBitter.value);
    [sliderBitter setValue:valBitter animated:YES];
    
}

- (IBAction)slider_Malt:(id)sender
{
    chkButton=3;
    valMalt=sliderMalt.value;
    valMalt = lroundf(sliderMalt.value);
    [sliderMalt setValue:valMalt animated:YES];
}

- (IBAction)slider_Sweet:(id)sender
{
    chkButton=2;
    valSweet=sliderSweet.value;
    valSweet = lroundf(sliderSweet.value);
    [sliderSweet setValue:valSweet animated:YES];
    
}

- (IBAction)slider_Aroma:(id)sender
{
    chkButton=1;
    valAroma=sliderAroma.value;
    valAroma = lroundf(sliderAroma.value);
    [sliderAroma setValue:valAroma animated:YES];
    
    NSLog(@"the value is %d",(int)sliderAroma.value);
    
}
#pragma mark Registeration data saved
-(void)saveBeerData
{
    [applicationDelegate checkInternetConnection];
    if([applicationDelegate internetWorking] ==0)
    {
        if ([strChkUser isEqualToString:@"2"]) {
            [applicationDelegate show_LoadingIndicator];
            NSString *strUseriD= [[NSUserDefaults standardUserDefaults]valueForKey:@"user_id"];
            NSURL * serviceUrl;
            NSString * xmlString;
            NSMutableArray *arrayDet=[[[NSUserDefaults standardUserDefaults]objectForKey:@"arrayDetails"]mutableCopy];
            
            if ([ChkView isEqualToString:@"2"])
            {
                xmlString =
                [NSString stringWithFormat:@"<?xml version=\"1.0\" encoding=\"UTF-8\" ?><editUserTasteProfile><beerId><![CDATA[%@]]></beerId><userId><![CDATA[%@]]></userId><aroma><![CDATA[%@]]></aroma><sweet><![CDATA[%@]]></sweet><bitter><![CDATA[%@]]></bitter><malt><![CDATA[%@]]></malt><yeast><![CDATA[%@]]></yeast><mouthFeel><![CDATA[%@]]></mouthFeel><sour><![CDATA[%@]]></sour><additive><![CDATA[%@]]></additive><booziness><![CDATA[%@]]></booziness><sour_status><![CDATA[1]]></sour_status><additive_status><![CDATA[1]]></additive_status><booziness_status><![CDATA[1]]></booziness_status><yeast_status><![CDATA[1]]></yeast_status></editUserTasteProfile>",striD,strUseriD,[NSString stringWithFormat:@"%i",(int)valAroma],[NSString stringWithFormat:@"%i",(int)valSweet],[NSString stringWithFormat:@"%i",(int)valBitter],[NSString stringWithFormat:@"%i",(int)valMalt],[NSString stringWithFormat:@"%i",(int)valYeast],[NSString stringWithFormat:@"%i",(int)valMouthFeel],[arrayDet objectAtIndex:6],[arrayDet objectAtIndex:7],[arrayDet objectAtIndex:8]];
                
                serviceUrl = [NSURL URLWithString:kEditUserBeerTaste];
            }
            else
            {
                xmlString =
                [NSString stringWithFormat:@"<?xml version=\"1.0\" encoding=\"UTF-8\" ?><editUserTasteProfile><userId><![CDATA[%@]]></userId><aroma><![CDATA[%@]]></aroma><sweet><![CDATA[%@]]></sweet><bitter><![CDATA[%@]]></bitter><malt><![CDATA[%@]]></malt><yeast><![CDATA[%@]]></yeast><mouthFeel><![CDATA[%@]]></mouthFeel><sour><![CDATA[%@]]></sour><additive><![CDATA[%@]]></additive><booziness><![CDATA[%@]]></booziness><sour_status><![CDATA[1]]></sour_status><additive_status><![CDATA[1]]></additive_status><booziness_status><![CDATA[1]]></booziness_status><yeast_status><![CDATA[1]]></yeast_status></editUserTasteProfile>",strUseriD,[NSString stringWithFormat:@"%i",(int)valAroma],[NSString stringWithFormat:@"%i",(int)valSweet],[NSString stringWithFormat:@"%i",(int)valBitter],[NSString stringWithFormat:@"%i",(int)valMalt],[NSString stringWithFormat:@"%i",(int)valYeast],[NSString stringWithFormat:@"%i",(int)valMouthFeel],@"3",@"3",@"3"];
                
                serviceUrl = [NSURL URLWithString:kEditUserBeerTaste];
                
            }
            NSLog(@"the xmlstring is =%@",xmlString);
            NSMutableURLRequest *theRequest = [NSMutableURLRequest requestWithURL:serviceUrl
                                                                      cachePolicy:NSURLRequestUseProtocolCachePolicy timeoutInterval:120.0];
            NSDictionary *headerFieldsDict = [NSDictionary dictionaryWithObjectsAndKeys:@"text/xml; charset=utf-8", @"Content-Type", nil];
            [theRequest setAllHTTPHeaderFields:headerFieldsDict];
            [theRequest setHTTPMethod:@"POST"];
            [theRequest setHTTPBody:[xmlString dataUsingEncoding:NSUTF8StringEncoding]];
            [theRequest setHTTPShouldHandleCookies:NO];
            NSHTTPURLResponse* urlResponse = nil;
            NSError *error = [[NSError alloc] init];
            NSData *responseData1 = [NSURLConnection sendSynchronousRequest:theRequest returningResponse:&urlResponse error:&error];
            NSString* strVal= [[NSString alloc] initWithData:responseData1 encoding:NSUTF8StringEncoding];
            NSLog(@"The result is = %@",strVal);
            NSMutableDictionary*dictReg = [strVal JSONValue];
            NSLog(@"the result in dict1 = %@",dictReg);
            
            NSString *serRslt;
            if ([ChkView isEqualToString:@"2"])
            {
                serRslt=[NSString stringWithFormat:@"%@",[dictReg objectForKey:@"editUserTasteProfile"]];
            }
            else
            {
                serRslt=[NSString stringWithFormat:@"%@",[dictReg objectForKey:@"editUserTasteProfile"]];
            }
            
            if ([serRslt integerValue]>0)
            {
                HomeViewController *homeView=[[HomeViewController alloc]init];
                
                NSArray *arrayBeerDet;
                if ([ChkView isEqualToString:@"2"])
                {
                    arrayBeerDet=[[NSArray alloc]initWithObjects:[NSString stringWithFormat:@"%i",(int)valAroma],[NSString stringWithFormat:@"%i",(int)valSweet],[NSString stringWithFormat:@"%i",(int)valBitter],[NSString stringWithFormat:@"%i",(int)valMalt],[NSString stringWithFormat:@"%i",(int)valYeast],[NSString stringWithFormat:@"%i",(int)valMouthFeel],[arrayDet objectAtIndex:6],[arrayDet objectAtIndex:7],[arrayDet objectAtIndex:8],@"1",@"1",@"1",nil];
                }
                else
                {
                    arrayBeerDet=[[NSArray alloc]initWithObjects:[NSString stringWithFormat:@"%i",(int)valAroma],[NSString stringWithFormat:@"%i",(int)valSweet],[NSString stringWithFormat:@"%i",(int)valBitter],[NSString stringWithFormat:@"%i",(int)valMalt],[NSString stringWithFormat:@"%i",(int)valYeast],[NSString stringWithFormat:@"%i",(int)valMouthFeel],@"3",@"3",@"3", @"1",@"1",@"1",nil];
                }
                [[NSUserDefaults standardUserDefaults]setObject:arrayBeerDet forKey:@"arrayDetails"];
                [[NSUserDefaults standardUserDefaults]synchronize];
                
                
                [self.navigationController pushViewController:homeView animated:NO];                [applicationDelegate hide_LoadingIndicator];
                UIAlertView *alert=[[UIAlertView alloc] initWithTitle:@"Alert!" message:@"Profile Saved." delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
                [alert show];
            }
            else if([serRslt integerValue]== -1)
            {
                [applicationDelegate hide_LoadingIndicator];
                UIAlertView *alert=[[UIAlertView alloc] initWithTitle:@"Alert!" message:@"User has already added his taste." delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
                [alert show];
            }
            else if([serRslt integerValue]== -2)
            {
                [applicationDelegate hide_LoadingIndicator];
                UIAlertView *alert=[[UIAlertView alloc] initWithTitle:@"Alert!" message:@"Server Issue." delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
                [alert show];
            }
            else if([serRslt integerValue]== -3)
            {
                [applicationDelegate hide_LoadingIndicator];
                UIAlertView *alert=[[UIAlertView alloc] initWithTitle:@"Alert!" message:@"In-valid User." delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
                [alert show];
            }
            
            serRslt = nil;
        }
        else
        {
            [applicationDelegate show_LoadingIndicator];
            NSString *strUseriD= [[NSUserDefaults standardUserDefaults]valueForKey:@"user_id"];
            
            NSString * xmlString =
            [NSString stringWithFormat:@"<?xml version=\"1.0\" encoding=\"UTF-8\" ?><addBeerProfile><beerId><![CDATA[%@]]></beerId><userId><![CDATA[%@]]></userId><aroma><![CDATA[%@]]></aroma><sweet><![CDATA[%@]]></sweet><bitter><![CDATA[%@]]></bitter><malt><![CDATA[%@]]></malt><yeast><![CDATA[%@]]></yeast><mouthFeel><![CDATA[%@]]></mouthFeel><sour><![CDATA[%@]]></sour><additive><![CDATA[%@]]></additive><booziness><![CDATA[%@]]></booziness></addBeerProfile>",striD,strUseriD,[NSString stringWithFormat:@"%i",(int)valAroma],[NSString stringWithFormat:@"%i",(int)valSweet],[NSString stringWithFormat:@"%i",(int)valBitter],[NSString stringWithFormat:@"%i",(int)valMalt],[NSString stringWithFormat:@"%i",(int)valYeast],[NSString stringWithFormat:@"%i",(int)valMouthFeel],@"3",@"3",@"3"];
            
            NSLog(@"the xmlstring is =%@",xmlString);
            NSURL * serviceUrl = [NSURL URLWithString:kNewBeerTaste];
            NSMutableURLRequest *theRequest = [NSMutableURLRequest requestWithURL:serviceUrl
                                                                      cachePolicy:NSURLRequestUseProtocolCachePolicy timeoutInterval:120.0];
            NSDictionary *headerFieldsDict = [NSDictionary dictionaryWithObjectsAndKeys:@"text/xml; charset=utf-8", @"Content-Type", nil];
            [theRequest setAllHTTPHeaderFields:headerFieldsDict];
            [theRequest setHTTPMethod:@"POST"];
            [theRequest setHTTPBody:[xmlString dataUsingEncoding:NSUTF8StringEncoding]];
            [theRequest setHTTPShouldHandleCookies:NO];
            NSHTTPURLResponse* urlResponse = nil;
            NSError *error = [[NSError alloc] init];
            NSData *responseData1 = [NSURLConnection sendSynchronousRequest:theRequest returningResponse:&urlResponse error:&error];
            NSString* strVal= [[NSString alloc] initWithData:responseData1 encoding:NSUTF8StringEncoding];
            NSLog(@"The result is = %@",strVal);
            NSMutableDictionary*dictReg = [strVal JSONValue];
            NSLog(@"the result in dict = %@",dictReg);
            
            NSString *serRslt=[NSString stringWithFormat:@"%@",[dictReg objectForKey:@"addBeerProfile"]];
            
            if ([serRslt integerValue]>0)
            {
                [self.navigationController popViewControllerAnimated:YES];
           
                UIAlertView *alert=[[UIAlertView alloc] initWithTitle:@"Alert!" message:@"Profile Saved." delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
                [alert show];
            }
            else if([serRslt integerValue]== -1)
            {
          
                UIAlertView *alert=[[UIAlertView alloc] initWithTitle:@"Alert!" message:@"In-valid Beer." delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
                [alert show];
            }
            else if([serRslt integerValue]== -2)
            {
              
                UIAlertView *alert=[[UIAlertView alloc] initWithTitle:@"Alert!" message:@"Server Issue." delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
                [alert show];
            }
            else if([serRslt integerValue]== -3)
            {
                
                UIAlertView *alert=[[UIAlertView alloc] initWithTitle:@"Alert!" message:@"In-valid User." delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
                [alert show];
            }
            [applicationDelegate hide_LoadingIndicator];
            serRslt = nil;
        }
    }
    
    else
    {
        
        UIAlertView *alertViewNetwrk = [[UIAlertView alloc] initWithTitle:@"Alert!"
                                                                  message:kAlertInternet
                                                                 delegate:nil
                                                        cancelButtonTitle:@"OK"
                                                        otherButtonTitles:nil, nil];
        
        [alertViewNetwrk show];
        alertViewNetwrk = nil;
    }
    
    [applicationDelegate hide_LoadingIndicator];
    
    
}
#pragma mark move to next view
-(void)GoNextView
{
    
    if ([strChkUser isEqualToString: @"2"]) {
        
        BeerDetails2ViewController *beerDetails=[[BeerDetails2ViewController alloc]init];
        
        beerDetails.valAroma= [NSString stringWithFormat:@"%i",(int)valAroma];
        beerDetails.valBitter=[NSString stringWithFormat:@"%i",(int)valBitter];
        beerDetails.valMalt=[NSString stringWithFormat:@"%i",(int)valMalt];
        beerDetails.valMouthFeel=[NSString stringWithFormat:@"%i",(int)valMouthFeel];
        beerDetails.valSweet=[NSString stringWithFormat:@"%i",(int)valSweet];
        beerDetails.valYeast=[NSString stringWithFormat:@"%i",(int)valYeast];
        beerDetails.strChkUser=strChkUser;
        beerDetails.striD=striD;
        if ([ChkView isEqualToString:@"2"]) {
            
            beerDetails.chkUser=@"4";
        }
        [self.navigationController pushViewController:beerDetails animated:YES];
    }
    else
    {
        
        ProfileABeerViewController *beerDetails=[[ProfileABeerViewController alloc]init];
        
        beerDetails.valAroma= [NSString stringWithFormat:@"%i",(int)valAroma];
        beerDetails.valBitter=[NSString stringWithFormat:@"%i",(int)valBitter];
        beerDetails.valMalt=[NSString stringWithFormat:@"%i",(int)valMalt];
        beerDetails.valMouthFeel=[NSString stringWithFormat:@"%i",(int)valMouthFeel];
        beerDetails.valSweet=[NSString stringWithFormat:@"%i",(int)valSweet];
        beerDetails.valYeast=[NSString stringWithFormat:@"%i",(int)valYeast];
        beerDetails.strChkUser=strChkUser;
        beerDetails.striD=striD;
        
        [self.navigationController pushViewController:beerDetails animated:YES];
    }
}

#pragma mark- AlertView delegate methods
- (void)alertView:(UIAlertView *)alertView didDismissWithButtonIndex:(NSInteger)buttonIndex
{
    if (buttonIndex==1) {
        [self GoNextView];
    }
    else
    {
        [self saveBeerData];
    }
}
- (void)viewDidUnload {
    [self setLblAroma3:nil];
    [self setLblAroma4:nil];
    [self setLblAroma5:nil];
    [self setLblAroma6:nil];
    [self setLblAroma7:nil];
    [self setLblSweet3:nil];
    [self setLblSweet4:nil];
    [self setLblSweet5:nil];
    [self setLblSweet6:nil];
    [self setLblSweet7:nil];
    [self setLblBitter3:nil];
    [self setLblBitter4:nil];
    [self setLblBitter5:nil];
    [self setLblBitter6:nil];
    [self setLblBitter7:nil];
    [self setLblMalt3:nil];
    [self setLblMalt4:nil];
    [self setLblMalt5:nil];
    [self setLblMalt6:nil];
    [self setLblMalt7:nil];
    [self setLblYeast3:nil];
    [self setLblYeast4:nil];
    [self setLblYeast5:nil];
    [self setLblYeast6:nil];
    [self setLblYeast7:nil];
    [self setLblMouth3:nil];
    [self setLblMouth4:nil];
    [self setLblMouth5:nil];
    [self setLblMouth6:nil];
    [self setLblMouth7:nil];
    [self setLblTop:nil];
    [super viewDidUnload];
}
@end
