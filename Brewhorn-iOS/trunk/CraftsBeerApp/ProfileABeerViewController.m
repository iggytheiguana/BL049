//
//  ProfileABeerViewController.m
//  CraftsBeerApp
//
//  Created by Mandeep Singh on 30/07/13.
//  Copyright (c) 2013 Mandeep Singh. All rights reserved.
//

#import "ProfileABeerViewController.h"
#import "JSON.h"
#import "HomeViewController.h"
#import "iToast.h"

@interface ProfileABeerViewController ()

@end

@implementation ProfileABeerViewController

@synthesize sliderAddictive,sliderBooziness,sliderSour;
@synthesize valMalt,valMouthFeel,valBitter,valYeast,valAroma,valSweet,striD,strChkUser;
@synthesize lblAroma3,lblAroma4,lblAroma5,lblAroma6,lblAroma7;
@synthesize lblBitter3,lblSweet7,lblBitter4,lblBitter5,lblBitter6,lblBitter7,lblSweet3,lblSweet4,lblSweet5,lblSweet6,lblTop,lblHeader;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}
#pragma mark view life cycle actions
- (void)viewDidLoad
{
    [[[[iToast makeText:@"Tap the parameter for a description."] setGravity:iToastGravityTop offsetLeft:0 offsetTop:[[UIScreen mainScreen]bounds].size.height-140] setDuration:2000] show:iToastTypeNotice];
    
    
    if (strChkUser==@"2") {
        lblTop.text=@"Create Your Beer Profile";
    }
    sliderAddictive.minimumTrackTintColor=[UIColor colorWithPatternImage:[UIImage imageNamed:@"slider_red.png"]];
    sliderBooziness.minimumTrackTintColor=[UIColor colorWithPatternImage:[UIImage imageNamed:@"slider_red.png"]];
    sliderSour.minimumTrackTintColor=[UIColor colorWithPatternImage:[UIImage imageNamed:@"slider_red.png"]];
    
    
    sliderAddictive.maximumTrackTintColor=[UIColor colorWithPatternImage:[UIImage imageNamed:@"slider_yellow.png"]];
    sliderBooziness.maximumTrackTintColor=[UIColor colorWithPatternImage:[UIImage imageNamed:@"slider_yellow.png"]];
    sliderSour.maximumTrackTintColor=[UIColor colorWithPatternImage:[UIImage imageNamed:@"slider_yellow.png"]];
    
    
    sliderAddictive.thumbTintColor=[UIColor colorWithPatternImage:[UIImage imageNamed:@"slider_handle.png"]];
    sliderBooziness.thumbTintColor=[UIColor colorWithPatternImage:[UIImage imageNamed:@"slider_handle.png"]];
    sliderSour.thumbTintColor=[UIColor colorWithPatternImage:[UIImage imageNamed:@"slider_handle.png"]];
    
    [self.sliderAddictive addTarget:self action:@selector(dragEndedForSlider)forControlEvents:UIControlEventTouchUpInside];
    [self.sliderBooziness addTarget:self action:@selector(dragEndedForSlider)forControlEvents:UIControlEventTouchUpInside];
    [self.sliderSour addTarget:self action:@selector(dragEndedForSlider)forControlEvents:UIControlEventTouchUpInside];
    
    NSLog(@"the array val is %@",[[NSUserDefaults standardUserDefaults]objectForKey:@"arrayDetails"]);
    NSMutableArray *arrayDet=[[[NSUserDefaults standardUserDefaults]objectForKey:@"arrayDetails"]mutableCopy];
    if ([strChkUser isEqualToString:@"2"])
    {
        lblAroma7.hidden=NO;
        lblAroma6.hidden=NO;
        lblAroma5.hidden=NO;
        lblAroma4.hidden=NO;
        lblAroma3.hidden=NO;
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
        
        
        aromaMin=[[arrayDet objectAtIndex:7]integerValue]-2;
        aromaMax=[[arrayDet objectAtIndex:7]integerValue]+2;
        sliderAddictive.minimumValue=aromaMin;
        sliderAddictive.maximumValue=aromaMax;
        sliderAddictive.value=[[arrayDet objectAtIndex:7]floatValue];
        valAdditive=[[arrayDet objectAtIndex:7]integerValue];
        lblAroma3.text=[NSString stringWithFormat:@"%i",aromaMin];
        lblAroma4.text=[NSString stringWithFormat:@"%i",aromaMin+1];
        lblAroma5.text=[NSString stringWithFormat:@"%i",aromaMin+2];
        lblAroma6.text=[NSString stringWithFormat:@"%i",aromaMin+3];
        lblAroma7.text=[NSString stringWithFormat:@"%i",aromaMax];
        
        
        
        SweetMin=[[arrayDet objectAtIndex:6]integerValue]-2;
        SweetMax=[[arrayDet objectAtIndex:6]integerValue]+2;
        sliderSour.minimumValue=SweetMin;
        sliderSour.maximumValue=SweetMax;
        sliderSour.value=[[arrayDet objectAtIndex:6]floatValue];
        valSour=[[arrayDet objectAtIndex:6]integerValue];
        lblSweet3.text=[NSString stringWithFormat:@"%i",SweetMin];
        lblSweet4.text=[NSString stringWithFormat:@"%i",SweetMin+1];
        lblSweet5.text=[NSString stringWithFormat:@"%i",SweetMin+2];
        lblSweet6.text=[NSString stringWithFormat:@"%i",SweetMin+3];
        lblSweet7.text=[NSString stringWithFormat:@"%i",SweetMax];
        
        
        
        maltMin=[[arrayDet objectAtIndex:8]integerValue]-2;
        maltMax=[[arrayDet objectAtIndex:8]integerValue]+2;
        sliderBooziness.minimumValue=maltMin;
        sliderBooziness.maximumValue=maltMax;
        sliderBooziness.value=[[arrayDet objectAtIndex:8]floatValue];
        valBooziness=[[arrayDet objectAtIndex:8]integerValue];
        lblBitter3.text=[NSString stringWithFormat:@"%i",maltMin];
        lblBitter4.text=[NSString stringWithFormat:@"%i",maltMin+1];
        lblBitter5.text=[NSString stringWithFormat:@"%i",maltMin+2];
        lblBitter6.text=[NSString stringWithFormat:@"%i",maltMin+3];
        lblBitter7.text=[NSString stringWithFormat:@"%i",maltMax];
        
    }
    
    
    
    
    
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}
#pragma mark Button actions
- (IBAction)btnBack:(id)sender
{
    {
        [applicationDelegate checkInternetConnection];
        if([applicationDelegate internetWorking] ==0)
        {
            if ([strChkUser isEqualToString:@"2"]) {
                
                [applicationDelegate show_LoadingIndicator];
                NSString *strUseriD= [[NSUserDefaults standardUserDefaults]valueForKey:@"user_id"];
                NSString * xmlString =
                [NSString stringWithFormat:@"<?xml version=\"1.0\" encoding=\"UTF-8\" ?><userTasteProfile><userId><![CDATA[%@]]></userId><aroma><![CDATA[%@]]></aroma><sweet><![CDATA[%@]]></sweet><bitter><![CDATA[%@]]></bitter><malt><![CDATA[%@]]></malt><yeast><![CDATA[%@]]></yeast><mouthFeel><![CDATA[%@]]></mouthFeel><sour><![CDATA[%@]]></sour><additive><![CDATA[%@]]></additive><booziness><![CDATA[%@]]></booziness></userTasteProfile>",strUseriD,valAroma,valSweet,valBitter,valMalt,valYeast,valMouthFeel,@"3",@"3",@"3"];
                
                NSLog(@"the xmlstring is =%@",xmlString);
                NSURL * serviceUrl = [NSURL URLWithString:kUserTaste];
                NSMutableURLRequest *theRequest = [NSMutableURLRequest requestWithURL:serviceUrl
                                                                          cachePolicy:NSURLRequestUseProtocolCachePolicy timeoutInterval:120.0];
                NSDictionary *headerFieldsDict = [NSDictionary dictionaryWithObjectsAndKeys:@"text/xml; charset=utf-8", @"Content-Type", nil];
                [theRequest setAllHTTPHeaderFields:headerFieldsDict];
                [theRequest setHTTPMethod:@"POST"];
                [theRequest setHTTPBody:[xmlString dataUsingEncoding:NSUTF8StringEncoding]];
                
                NSHTTPURLResponse* urlResponse = nil;
                NSError *error = [[NSError alloc] init];
                NSData *responseData1 = [NSURLConnection sendSynchronousRequest:theRequest returningResponse:&urlResponse error:&error];
                NSString* strVal= [[NSString alloc] initWithData:responseData1 encoding:NSUTF8StringEncoding];
                NSLog(@"The result is = %@",strVal);
                NSMutableDictionary*dictReg = [strVal JSONValue];
                NSLog(@"the result in dict = %@",dictReg);
                
                NSString *serRslt=[NSString stringWithFormat:@"%@",[dictReg objectForKey:@"userTasteProfile"]];
                
                if ([serRslt integerValue]>0)
                {
                  
                    UIAlertView *alert=[[UIAlertView alloc] initWithTitle:@"Alert!" message:@"Profile Saved." delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil];
                    [alert show];
                }
                else if([serRslt integerValue]== -1)
                {
                
                    UIAlertView *alert=[[UIAlertView alloc] initWithTitle:@"Alert!" message:@"User has already added his taste." delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
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
            else
            {
                [applicationDelegate show_LoadingIndicator];
                NSString *strUseriD= [[NSUserDefaults standardUserDefaults]valueForKey:@"user_id"];
                
                NSString * xmlString =
                [NSString stringWithFormat:@"<?xml version=\"1.0\" encoding=\"UTF-8\" ?><addBeerProfile><beerId><![CDATA[%@]]></beerId><userId><![CDATA[%@]]></userId><aroma><![CDATA[%@]]></aroma><sweet><![CDATA[%@]]></sweet><bitter><![CDATA[%@]]></bitter><malt><![CDATA[%@]]></malt><yeast><![CDATA[%@]]></yeast><mouthFeel><![CDATA[%@]]></mouthFeel><sour><![CDATA[%@]]></sour><additive><![CDATA[%@]]></additive><booziness><![CDATA[%@]]></booziness></addBeerProfile>",striD,strUseriD,valAroma,valSweet,valBitter,valMalt,valYeast,valMouthFeel,@"3",@"3",@"3"];
                
                NSLog(@"the xmlstring is =%@",xmlString);
                NSURL * serviceUrl = [NSURL URLWithString:kNewBeerTaste];
                NSMutableURLRequest *theRequest = [NSMutableURLRequest requestWithURL:serviceUrl
                                                                          cachePolicy:NSURLRequestUseProtocolCachePolicy timeoutInterval:120.0];
                NSDictionary *headerFieldsDict = [NSDictionary dictionaryWithObjectsAndKeys:@"text/xml; charset=utf-8", @"Content-Type", nil];
                [theRequest setAllHTTPHeaderFields:headerFieldsDict];
                [theRequest setHTTPMethod:@"POST"];
                [theRequest setHTTPBody:[xmlString dataUsingEncoding:NSUTF8StringEncoding]];
                
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
                  
                    UIAlertView *alert=[[UIAlertView alloc] initWithTitle:@"Alert!" message:@"Profile Saved." delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
                    [alert show];
                    [self.navigationController popToViewController:[self.navigationController.viewControllers objectAtIndex:1] animated:YES];
                    
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
    
}

- (IBAction)btnSave:(id)sender {
    [applicationDelegate checkInternetConnection];
    if([applicationDelegate internetWorking] ==0)
    {
        if ([strChkUser isEqualToString:@"2"]) {
            
            [applicationDelegate show_LoadingIndicator];
            NSString *strUseriD= [[NSUserDefaults standardUserDefaults]valueForKey:@"user_id"];
            NSString * xmlString =
            [NSString stringWithFormat:@"<?xml version=\"1.0\" encoding=\"UTF-8\" ?><userTasteProfile><userId><![CDATA[%@]]></userId><aroma><![CDATA[%@]]></aroma><sweet><![CDATA[%@]]></sweet><bitter><![CDATA[%@]]></bitter><malt><![CDATA[%@]]></malt><yeast><![CDATA[%@]]></yeast><mouthFeel><![CDATA[%@]]></mouthFeel><sour><![CDATA[%@]]></sour><additive><![CDATA[%@]]></additive><booziness><![CDATA[%@]]></booziness></userTasteProfile>",strUseriD,valAroma,valSweet,valBitter,valMalt,valYeast,valMouthFeel,[NSString stringWithFormat:@"%i",(int)valSour],[NSString stringWithFormat:@"%i",(int)valAdditive],[NSString stringWithFormat:@"%i",(int)valBooziness]];
            
            NSLog(@"the xmlstring is =%@",xmlString);
            NSURL * serviceUrl = [NSURL URLWithString:kUserTaste];
            NSMutableURLRequest *theRequest = [NSMutableURLRequest requestWithURL:serviceUrl
                                                                      cachePolicy:NSURLRequestUseProtocolCachePolicy timeoutInterval:120.0];
            NSDictionary *headerFieldsDict = [NSDictionary dictionaryWithObjectsAndKeys:@"text/xml; charset=utf-8", @"Content-Type", nil];
            [theRequest setAllHTTPHeaderFields:headerFieldsDict];
            [theRequest setHTTPMethod:@"POST"];
            [theRequest setHTTPBody:[xmlString dataUsingEncoding:NSUTF8StringEncoding]];
            
            NSHTTPURLResponse* urlResponse = nil;
            NSError *error = [[NSError alloc] init];
            NSData *responseData1 = [NSURLConnection sendSynchronousRequest:theRequest returningResponse:&urlResponse error:&error];
            NSString* strVal= [[NSString alloc] initWithData:responseData1 encoding:NSUTF8StringEncoding];
            NSLog(@"The result is = %@",strVal);
            NSMutableDictionary*dictReg = [strVal JSONValue];
            NSLog(@"the result in dict = %@",dictReg);
            
            NSString *serRslt=[NSString stringWithFormat:@"%@",[dictReg objectForKey:@"userTasteProfile"]];
            
            if ([serRslt integerValue]>0)
            {
                
                NSArray *arrayBeerDet2=[[NSArray alloc]initWithObjects:valAroma,valSweet,valBitter,valMalt,valYeast,valMouthFeel,[NSString stringWithFormat:@"%i",(int)valSour],[NSString stringWithFormat:@"%i",(int)valAdditive],[NSString stringWithFormat:@"%i",(int)valBooziness], nil];
                
                // [[NSUserDefaults standardUserDefaults]setObject:arrayBeerDet2 forKey:@"arrayDetails"];
                [[NSUserDefaults standardUserDefaults]synchronize];
                UIAlertView *alert=[[UIAlertView alloc] initWithTitle:@"Alert!" message:@"Profile Saved." delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil];
                [alert show];
            }
            else if([serRslt integerValue]== -1)
            {
               
                UIAlertView *alert=[[UIAlertView alloc] initWithTitle:@"Alert!" message:@"User has already added his taste." delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil];
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
        else
        {
            [applicationDelegate show_LoadingIndicator];
            NSString *strUseriD= [[NSUserDefaults standardUserDefaults]valueForKey:@"user_id"];
            
            NSString * xmlString =
            [NSString stringWithFormat:@"<?xml version=\"1.0\" encoding=\"UTF-8\" ?><addBeerProfile><beerId><![CDATA[%@]]></beerId><userId><![CDATA[%@]]></userId><aroma><![CDATA[%@]]></aroma><sweet><![CDATA[%@]]></sweet><bitter><![CDATA[%@]]></bitter><malt><![CDATA[%@]]></malt><yeast><![CDATA[%@]]></yeast><mouthFeel><![CDATA[%@]]></mouthFeel><sour><![CDATA[%@]]></sour><additive><![CDATA[%@]]></additive><booziness><![CDATA[%@]]></booziness></addBeerProfile>",striD,strUseriD,valAroma,valSweet,valBitter,valMalt,valYeast,valMouthFeel,[NSString stringWithFormat:@"%i",(int)valSour],[NSString stringWithFormat:@"%i",(int)valAdditive],[NSString stringWithFormat:@"%i",(int)valBooziness]];
            
            NSLog(@"the xmlstring is =%@",xmlString);
            NSURL * serviceUrl = [NSURL URLWithString:kNewBeerTaste];
            NSMutableURLRequest *theRequest = [NSMutableURLRequest requestWithURL:serviceUrl
                                                                      cachePolicy:NSURLRequestUseProtocolCachePolicy timeoutInterval:120.0];
            NSDictionary *headerFieldsDict = [NSDictionary dictionaryWithObjectsAndKeys:@"text/xml; charset=utf-8", @"Content-Type", nil];
            [theRequest setAllHTTPHeaderFields:headerFieldsDict];
            [theRequest setHTTPMethod:@"POST"];
            [theRequest setHTTPBody:[xmlString dataUsingEncoding:NSUTF8StringEncoding]];
            
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
              
                UIAlertView *alert=[[UIAlertView alloc] initWithTitle:@"Alert!" message:@"Profile Saved." delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil];
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
- (IBAction)slider_Sour:(id)sender
{
    chkButton=1;
    valSour=sliderSour.value;
    valSour = lroundf(sliderSour.value);
    [sliderSour setValue:valSour animated:YES];
    
}

- (IBAction)slider_Addictive:(id)sender
{
    chkButton=2;
    valAdditive=sliderAddictive.value;
    valAdditive = lroundf(sliderAddictive.value);
    [sliderAddictive setValue:valAdditive animated:YES];
    
}

- (IBAction)slider_Booziness:(id)sender
{
    chkButton=3;
    valBooziness=sliderBooziness.value;
    valBooziness = lroundf(sliderBooziness.value);
    [sliderBooziness setValue:valBooziness animated:YES];
    
}

-(void)dragEndedForSlider
{
    int strChkVal;
    if (chkButton==3)
    {
        strChkVal=[[NSString stringWithFormat:@"%f",valBooziness]integerValue];
        strCompare=[lblBitter5.text integerValue];
        
    }
    else if (chkButton==2)
    {
        strChkVal=[[NSString stringWithFormat:@"%f",valAdditive]integerValue];
        strCompare=[lblAroma5.text integerValue];
        
    }
    else
    {
        strChkVal=[[NSString stringWithFormat:@"%f",valSour]integerValue];
        strCompare=[lblSweet5.text integerValue];
        
    }
    if (strChkVal==4)
    {
        [[[[iToast makeText:@"The beer's taste was somewhat less than you prefer"] setGravity:iToastGravityTop offsetLeft:0 offsetTop:40] setDuration:500] show:iToastTypeNotice];
    }
    else if (strChkVal==5)
    {
        [[[[iToast makeText:@"The beer's taste met your baseline taste preferences"] setGravity:iToastGravityTop offsetLeft:0 offsetTop:40] setDuration:500] show:iToastTypeNotice];
    }
    else if (strChkVal==3)
    {
        [[[[iToast makeText:@"The beer's taste was much less than you prefer"] setGravity:iToastGravityTop offsetLeft:0 offsetTop:40] setDuration:500] show:iToastTypeNotice];
    }
    else if (strChkVal==6)
    {
        [[[[iToast makeText:@"The beer's taste was somewhat more than you prefer"] setGravity:iToastGravityTop offsetLeft:0 offsetTop:40] setDuration:500] show:iToastTypeNotice];
    }
    else if (strChkVal==7)
    {
        [[[[iToast makeText:@"The beer's taste was much more than you prefer"] setGravity:iToastGravityTop offsetLeft:0 offsetTop:40] setDuration:500] show:iToastTypeNotice];
    }
}

#pragma mark- AlertView delegate methods
- (void)alertView:(UIAlertView *)alertView didDismissWithButtonIndex:(NSInteger)buttonIndex
{
    if ([alertView.message isEqualToString:@"Profile Saved."]) {
        HomeViewController *homeView=[[HomeViewController alloc]init];
        [self.navigationController pushViewController:homeView animated:YES];
    }
}

- (void)viewDidUnload {
    [self setLblTop:nil];
    [super viewDidUnload];
}
@end

