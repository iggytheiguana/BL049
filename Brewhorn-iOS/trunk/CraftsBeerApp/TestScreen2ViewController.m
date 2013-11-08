//
//  TestScreen2ViewController.m
//  CraftsBeerApp
//
//  Created by Mandeep Singh on 03/08/13.
//  Copyright (c) 2013 Mandeep Singh. All rights reserved.
//

#import "TestScreen2ViewController.h"
#import "JSON.h"
#import "HomeViewController.h"
#import "BeerDetails2ViewController.h"
#import "ShareScreenViewController.h"
#import "iToast.h"
#import "InfoViewController.h"
#import "ProfileBeerInfoViewController.h"
#define FbClientID @"366509033392814"

@interface TestScreen2ViewController ()

@end

@implementation TestScreen2ViewController

@synthesize tblArray,viewArray,txtAroma,txtBitter,txtSweet;
@synthesize lblAroma3,lblAroma4,lblAroma5,lblAroma6,lblAroma7;
@synthesize sliderAroma,sliderBitter,sliderSweet;
@synthesize lblBitter3,lblBitter4,lblBitter5,lblBitter6,lblBitter7,lblSweet3,lblSweet4,lblSweet5,lblSweet6,lblSweet7,chkUser,array;
@synthesize strAr,strBi,strSw,strid,btnBitter,btnSweet,btnAroma,txtAr,txtBi,txtSw,txtTexture,btnTexture,scrlView,switchYeast,lblHeader,lblHeader1;
@synthesize fbGraph;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}
- (void)viewDidLoad
{
    
    FBLoginView *loginview = [[FBLoginView alloc] init];
    
    loginview.frame = CGRectOffset(loginview.frame, 5, 5);
    loginview.delegate = self;
    
    //  [self.view addSubview:loginview];
    
    [loginview sizeToFit];
    
    [[[[iToast makeText:@"Tap the parameter for a description."] setGravity:iToastGravityTop offsetLeft:0 offsetTop:[[UIScreen mainScreen]bounds].size.height-140] setDuration:2000] show:iToastTypeNotice];
    
    swch=@"1";
    if ([[UIScreen mainScreen]bounds].size.height==568) {
        scrlView.contentSize=CGSizeMake(260, 480);
    }
    else
    {
        scrlView.contentSize=CGSizeMake(260, 380);
    }    arrayAromaTable=[[NSMutableArray alloc]initWithObjects:@"nutty",@"biscuit",@"toast",@"lighttoast",@"burnttoast",@"caramel",@"molasses",@"darkfruits",@"prune",@"darkraisin",@"smoky",@"chocolaty",@"coffee",@"burnt",@"bitterchocolate",@"strongcoffee",@"licorice",@"oaky",@"plum",@"toffee",@"oats",@"bready",@"bandaid",@"medicinal", nil];
    
    arrayBitterTable=[[NSMutableArray alloc]initWithObjects:@"spicy",@"fruity",@"banana",@"clove",@"goldenraisin",@"whitefleshfruit",@"meaty",@"peppery",@"bubblegum",@"horseblanket",@"barnyard",@"bandaid",nil];
    
    arraySweetTable=[[NSMutableArray alloc]initWithObjects:@"light",@"moderate",@"heavy",@"flat",@"crisp",@"prickly",@"sharp",@"stinging",@"zesty",@"sprintzy",@"bubbly",@"soft",@"effervescent", nil];
    
    arrayTextureTable=[[NSMutableArray alloc]initWithObjects:@"lightbody",@"mediumbody",@"fullbody",@"viscous",@"syrupy",@"creamy",@"metallic",@"silky",@"gritty",@"acidic",@"dry",@"chewy",@"luscious",@"tannic",@"watery",@"rich",@"velvety", nil];
    
    arrayAromaValue=[[NSMutableArray alloc]init];
    arrayBitterValue=[[NSMutableArray alloc]init];
    arraySweetValue=[[NSMutableArray alloc]init];
    arrayTextureValue=[[NSMutableArray alloc]init];
    
    
    
    sliderAroma.minimumTrackTintColor=[UIColor colorWithPatternImage:[UIImage imageNamed:@"slider_red.png"]];
    sliderBitter.minimumTrackTintColor=[UIColor colorWithPatternImage:[UIImage imageNamed:@"slider_red.png"]];
    sliderSweet.minimumTrackTintColor=[UIColor colorWithPatternImage:[UIImage imageNamed:@"slider_red.png"]];
    sliderAroma.maximumTrackTintColor=[UIColor colorWithPatternImage:[UIImage imageNamed:@"slider_yellow.png"]];
    sliderBitter.maximumTrackTintColor=[UIColor colorWithPatternImage:[UIImage imageNamed:@"slider_yellow.png"]];
    sliderSweet.maximumTrackTintColor=[UIColor colorWithPatternImage:[UIImage imageNamed:@"slider_yellow.png"]];
    sliderAroma.thumbTintColor=[UIColor colorWithPatternImage:[UIImage imageNamed:@"slider_handle.png"]];
    sliderBitter.thumbTintColor=[UIColor colorWithPatternImage:[UIImage imageNamed:@"slider_handle.png"]];
    sliderSweet.thumbTintColor=[UIColor colorWithPatternImage:[UIImage imageNamed:@"slider_handle.png"]];
    [self.sliderSweet addTarget:self action:@selector(dragEndedForSlider)forControlEvents:UIControlEventTouchUpInside];
    [self.sliderBitter addTarget:self action:@selector(dragEndedForSlider)forControlEvents:UIControlEventTouchUpInside];
    [self.sliderAroma addTarget:self action:@selector(dragEndedForSlider)forControlEvents:UIControlEventTouchUpInside];
    NSLog(@"the array val is %@",[[NSUserDefaults standardUserDefaults]objectForKey:@"arrayDetails"]);
    NSMutableArray *arrayDet=[[[NSUserDefaults standardUserDefaults]objectForKey:@"arrayDetails"]mutableCopy];
    
    
    {
        aromaMin=[[arrayDet objectAtIndex:3]integerValue]-2;
        aromaMax=[[arrayDet objectAtIndex:3]integerValue]+2;
        sliderAroma.minimumValue=aromaMin;
        sliderAroma.maximumValue=aromaMax;
        sliderAroma.value=[[arrayDet objectAtIndex:3]floatValue];
        valAroma=[[arrayDet objectAtIndex:3]integerValue];
        strMalt=[arrayDet objectAtIndex:3];
        lblAroma3.text=[NSString stringWithFormat:@"%i",aromaMin];
        lblAroma4.text=[NSString stringWithFormat:@"%i",aromaMin+1];
        lblAroma5.text=[NSString stringWithFormat:@"%i",aromaMin+2];
        lblAroma6.text=[NSString stringWithFormat:@"%i",aromaMin+3];
        lblAroma7.text=[NSString stringWithFormat:@"%i",aromaMax];
        
        
        
        SweetMin=[[arrayDet objectAtIndex:5]integerValue]-2;
        SweetMax=[[arrayDet objectAtIndex:5]integerValue]+2;
        sliderSweet.minimumValue=SweetMin;
        sliderSweet.maximumValue=SweetMax;
        sliderSweet.value=[[arrayDet objectAtIndex:5]floatValue];
        valSweet=[[arrayDet objectAtIndex:5]integerValue];
        strMouthfeel=[arrayDet objectAtIndex:5];
        
        lblSweet3.text=[NSString stringWithFormat:@"%i",SweetMin];
        lblSweet4.text=[NSString stringWithFormat:@"%i",SweetMin+1];
        lblSweet5.text=[NSString stringWithFormat:@"%i",SweetMin+2];
        lblSweet6.text=[NSString stringWithFormat:@"%i",SweetMin+3];
        lblSweet7.text=[NSString stringWithFormat:@"%i",SweetMax];
        
        
        
        
        bitterMin=[[arrayDet objectAtIndex:4]integerValue]-2;
        bitterMax=[[arrayDet objectAtIndex:4]integerValue]+2;
        sliderBitter.minimumValue=bitterMin;
        sliderBitter.maximumValue=bitterMax;
        sliderBitter.value=[[arrayDet objectAtIndex:4]floatValue];
        valBitter=[[arrayDet objectAtIndex:4]integerValue];
        strYeast=[arrayDet objectAtIndex:4];
        
        lblBitter3.text=[NSString stringWithFormat:@"%i",bitterMin];
        lblBitter4.text=[NSString stringWithFormat:@"%i",bitterMin+1];
        lblBitter5.text=[NSString stringWithFormat:@"%i",bitterMin+2];
        lblBitter6.text=[NSString stringWithFormat:@"%i",bitterMin+3];
        lblBitter7.text=[NSString stringWithFormat:@"%i",bitterMax];
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
- (IBAction)btnAroma:(id)sender
{
    chkArray=1;
    viewArray.hidden=NO;
    [tblArray reloadData];
}
- (IBAction)btnDone:(id)sender
{
    int i;
    NSString *strAroma=@"";
    NSString *strBitter=@"";
    NSString *strSweet=@"";
    if (chkArray==1) {
        NSLog(@"arraycount=%i",[arrayAromaValue count]);
        for (i=0; i<[arrayAromaValue count]; i++)
        {
            if (i==0) {
                strAroma=[arrayAromaValue objectAtIndex:i];
            }
            else
            {
                strAroma=[NSString stringWithFormat:@"%@,%@",strAroma,[arrayAromaValue objectAtIndex:i]];
            }
        }
        txtAroma.text=strAroma;
    }
    else  if (chkArray==2) {
        for (i=0; i<[arrayBitterValue count]; i++)
        {
            if (i==0) {
                strBitter=[arrayBitterValue objectAtIndex:i];
            }
            else
            {
                strBitter=[NSString stringWithFormat:@"%@,%@",strBitter,[arrayBitterValue objectAtIndex:i]];
            }
        }
        txtBitter.text=strBitter;
    }
    else  if (chkArray==3) {
        for (i=0; i<[arraySweetValue count]; i++)
        {
            if (i==0) {
                strSweet=[arraySweetValue objectAtIndex:i];
            }
            else
            {
                strSweet=[NSString stringWithFormat:@"%@,%@",strSweet,[arraySweetValue objectAtIndex:i]];
            }
        }
        txtSweet.text=strSweet;
    }
    else  {
        for (i=0; i<[arrayTextureValue count]; i++)
        {
            if (i==0) {
                strTexture=[arrayTextureValue objectAtIndex:i];
            }
            else
            {
                strTexture=[NSString stringWithFormat:@"%@,%@",strTexture,[arrayTextureValue objectAtIndex:i]];
            }
        }
        txtTexture.text=strTexture;
    }
    if (txtAroma.text.length>2) {
        [btnAroma setTitle:@"" forState:UIControlStateNormal];
    }
    else
    {
        [btnAroma setTitle:@"Comments" forState:UIControlStateNormal];
    }
    if (txtBitter.text.length>2) {
        [btnBitter setTitle:@"" forState:UIControlStateNormal];
    }
    else
    {
        [btnBitter setTitle:@"Comments" forState:UIControlStateNormal];
    }
    if (txtSweet.text.length>2) {
        [btnSweet setTitle:@"" forState:UIControlStateNormal];
    }
    else
    {
        [btnSweet setTitle:@"Carbonation" forState:UIControlStateNormal];
    }
    if (txtTexture.text.length>2) {
        [btnTexture setTitle:@"" forState:UIControlStateNormal];
    }
    else
    {
        [btnTexture setTitle:@"Texture" forState:UIControlStateNormal];
    }
    
    viewArray.hidden=YES;
}
- (IBAction)btnBitter:(id)sender
{
    chkArray=2;
    viewArray.hidden=NO;
    [tblArray reloadData];
}
- (IBAction)btnSweet:(id)sender
{
    chkArray=3;
    viewArray.hidden=NO;
    [tblArray reloadData];
}
- (IBAction)btnTexture:(id)sender
{
    chkArray=4;
    viewArray.hidden=NO;
    [tblArray reloadData];
}

- (IBAction)switch_Yeast:(id)sender
{
    if (switchYeast.isOn==NO) {
        swch=@"0";
        sliderBitter.enabled=NO;
        [sliderBitter setValue:3.0];
        btnBitter.hidden=YES;
        txtBitter.editable=NO;
        txtBitter.backgroundColor=[UIColor lightGrayColor];
        txtBitter.text=@"";
    }
    else
    {
        NSMutableArray *arrayDet=[[[NSUserDefaults standardUserDefaults]objectForKey:@"arrayDetails"]mutableCopy];
        swch=@"1";
        sliderBitter.enabled=YES;
        txtBitter.editable=YES;
        [sliderBitter setValue:[[arrayDet objectAtIndex:4]floatValue]];
        btnBitter.hidden=NO;
        txtBitter.backgroundColor=[UIColor whiteColor];
    }
}

- (IBAction)btn_info:(id)sender
{
    ProfileBeerInfoViewController *tasteInfo=[[ProfileBeerInfoViewController alloc]init];
    [self.navigationController presentViewController:tasteInfo animated:YES completion:nil];
}

- (IBAction)malt:(id)sender
{
    UIAlertView *alert=[[UIAlertView alloc]initWithTitle:@"Help" message:@"What grain, dark candy or fruit flavor(s) did you pick up and how strong are they?" delegate:nil cancelButtonTitle:@"Thanks" otherButtonTitles:nil];
    [alert show];
}

- (IBAction)yeast:(id)sender
{
    UIAlertView *alert=[[UIAlertView alloc]initWithTitle:@"Help" message:@"In a wheat beer, do you taste bubblegum or banana-clove? In a Belgian, do you taste spice or fruit? In a sour or lambic, do you pick up any funk-like taste?" delegate:nil cancelButtonTitle:@"Thanks" otherButtonTitles:nil];
    [alert show];
}

- (IBAction)mouth:(id)sender
{
    UIAlertView *alert=[[UIAlertView alloc]initWithTitle:@"Help" message:@"Is the carbonation crisp, prickly, or stinging? Does the body of the beer feel light, moderate or heavy?" delegate:nil cancelButtonTitle:@"Thanks" otherButtonTitles:nil];
    [alert show];
}
- (IBAction)slider_Bitter:(id)sender {
    chkButton=4;
    valBitter=sliderBitter.value;
    valBitter = lroundf(sliderBitter.value);
    [sliderBitter setValue:valBitter animated:YES];
    strYeast=[NSString stringWithFormat:@"%i",(int)valBitter];
    NSLog(@"the value is %d",(int)sliderBitter.value);
}

- (IBAction)slider_Sweet:(id)sender {
    chkButton=2;
    valSweet=sliderSweet.value;
    valSweet = lroundf(sliderSweet.value);
    [sliderSweet setValue:valSweet animated:YES];
    
    strMouthfeel=[NSString stringWithFormat:@"%i",(int)valSweet];
    NSLog(@"the strMouthfeel is %d",(int)sliderSweet.value);
}

- (IBAction)slider_Aroma:(id)sender {
    chkButton=1;
    valAroma=sliderAroma.value;
    valAroma = lroundf(sliderAroma.value);
    [sliderAroma setValue:valAroma animated:YES];
    
    strMalt=[NSString stringWithFormat:@"%i",(int)valAroma];
    NSLog(@"the value is %d",(int)sliderAroma.value);
    
}
-(void)dragEndedForSlider
{
    int strChkVal;
    if (chkButton==4)
    {
        strChkVal=[[NSString stringWithFormat:@"%f",valBitter]integerValue];
        strCompare=[lblBitter5.text integerValue];
        
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
        [[[[iToast makeText:@"The beer's taste was somewhat less than you prefer."] setGravity:iToastGravityTop offsetLeft:0 offsetTop:40] setDuration:500] show:iToastTypeNotice];
    }
    else if (strChkVal==strCompare)
    {
        [[[[iToast makeText:@"The beer's taste met your baseline taste preferences."] setGravity:iToastGravityTop offsetLeft:0 offsetTop:40] setDuration:500] show:iToastTypeNotice];
    }
    else if (strChkVal==strCompare-2)
    {
        [[[[iToast makeText:@"The beer's taste was much less than you prefer."] setGravity:iToastGravityTop offsetLeft:0 offsetTop:40] setDuration:500] show:iToastTypeNotice];
    }
    else if (strChkVal==strCompare+1)
    {
        [[[[iToast makeText:@"The beer's taste was somewhat more than you prefer."] setGravity:iToastGravityTop offsetLeft:0 offsetTop:40] setDuration:500] show:iToastTypeNotice];
    }
    else if (strChkVal==strCompare+2)
    {
        [[[[iToast makeText:@"The beer's taste was much more than you prefer."] setGravity:iToastGravityTop offsetLeft:0 offsetTop:40] setDuration:500] show:iToastTypeNotice];
    }
}

- (IBAction)btnSave:(id)sender
{
    UIAlertView *alert=[[UIAlertView alloc]initWithTitle:@"Would you like to add optional parameters." message:@"" delegate:self cancelButtonTitle:@"Save" otherButtonTitles:@"Proceed", nil];
    [alert show];
    return;
}
-(void)saveData
{
    
    txtSweet.text=@"";
    txtBitter.text=@"";
    txtAroma.text=@"";
    txtTexture.text=@"";
    [arrayAromaValue removeAllObjects];
    [arrayBitterValue removeAllObjects];
    [arraySweetValue removeAllObjects];
    [arrayTextureValue removeAllObjects];
    {
        [applicationDelegate show_LoadingIndicator];
        NSString *strSour;
        NSString *strAddictive;
        NSString *strBooziness;
        strSour=@"0";
        strAddictive=@"0";
        strBooziness=@"0";
        /*
         NSMutableArray *arrayDet=[[[NSUserDefaults standardUserDefaults]objectForKey:@"arrayDetails"]mutableCopy];
         if ([[arrayDet objectAtIndex:9]integerValue]==0) {
         strSour=@"0";
         }
         if ([[arrayDet objectAtIndex:10]integerValue]==0) {
         strAddictive=@"0";
         }
         if ([[arrayDet objectAtIndex:11]integerValue]==0) {
         strBooziness=@"0";
         }
         */
        
        if ([swch integerValue]==0) {
            strYeast=@"0";
        }
        NSString *strUseriD= [[NSUserDefaults standardUserDefaults]valueForKey:@"user_id"];
        NSURL * serviceUrl;
        NSString * xmlString;
        if ([chkUser integerValue]==2) {
            xmlString =
            [NSString stringWithFormat:@"<?xml version=\"1.0\" encoding=\"UTF-8\" ?><editBeerProfile><beerId><![CDATA[%@]]></beerId><userId><![CDATA[%@]]></userId><aroma><![CDATA[%@]]></aroma><sweet><![CDATA[%@]]></sweet><bitter><![CDATA[%@]]></bitter><malt><![CDATA[%@]]></malt><yeast><![CDATA[%@]]></yeast><mouthFeel><![CDATA[%@]]></mouthFeel><sour><![CDATA[%@]]></sour><additive><![CDATA[%@]]></additive><booziness><![CDATA[%@]]></booziness><aroma_cmt><![CDATA[%@]]></aroma_cmt><sweet_cmt><![CDATA[%@]]></sweet_cmt><bitter_cmt><![CDATA[%@]]></bitter_cmt><malt_cmt><![CDATA[%@]]></malt_cmt><yeast_cmt><![CDATA[%@]]></yeast_cmt><mouthFeel_cmt><![CDATA[%@]]></mouthFeel_cmt><sour_cmt><![CDATA[%@]]></sour_cmt><additive_cmt><![CDATA[%@]]></additive_cmt><booziness_cmt><![CDATA[%@]]></booziness_cmt></editBeerProfile>",strid,strUseriD,strAr,strSw,strBi,strMalt,strYeast,strMouthfeel,strSour,strAddictive,strBooziness,txtAr,txtSw,txtBi,txtAroma.text,txtBitter.text,txtSweet.text,@"",@"",@""];
            
            NSLog(@"the xmlstring is =%@",xmlString);
            serviceUrl = [NSURL URLWithString:@"http://brewhorn.com/app_data/webserviceController/editBeerProfile"];
        }
        else{
            /*           NSMutableArray *arrayDet=[[[NSUserDefaults standardUserDefaults]objectForKey:@"arrayDetails"]mutableCopy];
             if ([[arrayDet objectAtIndex:9]integerValue]==0) {
             strSour=@"0";
             }
             else
             {
             strSour=@"3";
             }
             if ([[arrayDet objectAtIndex:10]integerValue]==0) {
             strAddictive=@"0";
             }
             else
             {
             strAddictive=@"3";
             }
             
             if ([[arrayDet objectAtIndex:11]integerValue]==0) {
             strBooziness=@"0";
             }
             else
             {
             strBooziness=@"3";
             }
             */
            xmlString =
            [NSString stringWithFormat:@"<?xml version=\"1.0\" encoding=\"UTF-8\" ?><addBeerProfile><beerId><![CDATA[%@]]></beerId><userId><![CDATA[%@]]></userId><aroma><![CDATA[%@]]></aroma><sweet><![CDATA[%@]]></sweet><bitter><![CDATA[%@]]></bitter><malt><![CDATA[%@]]></malt><yeast><![CDATA[%@]]></yeast><mouthFeel><![CDATA[%@]]></mouthFeel><sour><![CDATA[%@]]></sour><additive><![CDATA[%@]]></additive><booziness><![CDATA[%@]]></booziness></addBeerProfile>",strid,strUseriD,strAr,strSw,strBi,[NSString stringWithFormat:@"%i",(int)valAroma],[NSString stringWithFormat:@"%i",(int)valBitter],[NSString stringWithFormat:@"%i",(int)valSweet],strSour,strAddictive,strBooziness];
            NSLog(@"the xmlstring is =%@",xmlString);
            serviceUrl = [NSURL URLWithString:kNewBeerTaste];
        }
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
        NSString *serRslt;
        if ([chkUser integerValue]==2) {
            serRslt=[NSString stringWithFormat:@"%@",[dictReg objectForKey:@"editBeerProfile"]];
        }
        else
        {
            serRslt=[NSString stringWithFormat:@"%@",[dictReg objectForKey:@"addBeerProfile"]];
        }
        if ([serRslt integerValue]>0)
        {
            if ([[NSUserDefaults standardUserDefaults]integerForKey:@"AutomateSharing"]==1) {
                [self btnFb];
            }
            else
            {
                HomeViewController *home=[[HomeViewController alloc]init];
                [self.navigationController pushViewController:home animated:NO];
            }
            //  [self.navigationController popViewControllerAnimated:YES];
            [applicationDelegate hide_LoadingIndicator];
        }
        else if([serRslt integerValue]== -1)
        {
            [applicationDelegate hide_LoadingIndicator];
            UIAlertView *alert=[[UIAlertView alloc] initWithTitle:@"Alert!" message:@"In-valid Beer." delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
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
}
- (IBAction)btnCancel:(id)sender
{
    [self.navigationController popViewControllerAnimated:YES];
}

- (void)viewDidUnload {
    [self setTblArray:nil];
    [self setViewArray:nil];
    [self setTxtBitter:nil];
    [self setTxtSweet:nil];
    [self setTxtAroma:nil];
    [self setSliderBitter:nil];
    [self setSliderSweet:nil];
    [self setSliderAroma:nil];
    [self setBtnAroma:nil];
    [self setBtnBitter:nil];
    [self setBtnSweet:nil];
    [super viewDidUnload];
}
#pragma mark- AlertView delegate methods
- (void)alertView:(UIAlertView *)alertView didDismissWithButtonIndex:(NSInteger)buttonIndex
{
    if (buttonIndex==1) {
        if ([swch integerValue]==0) {
            strYeast=@"0";
        }
        
        BeerDetails2ViewController *beerDetails=[[BeerDetails2ViewController alloc]init];
        NSLog(@"srtar %@   strbi   %@    strmalt %@",strAr,strBi,strMalt);
        beerDetails.valAroma=strAr;
        beerDetails.valBitter=strBi;
        beerDetails.valMalt=strMalt;
        beerDetails.valMouthFeel=strMouthfeel;
        NSLog(@"THE VAL IS %@",strMouthfeel);
        beerDetails.valSweet=strSw;
        beerDetails.valYeast=strYeast;
        beerDetails.strChkUser=@"1";
        beerDetails.striD=strid;
        beerDetails.chkUser=chkUser;
        if ([chkUser integerValue]==2)
        {
            {
                beerDetails.valMalt=strMalt;
                beerDetails.valYeast=strYeast;
                beerDetails.valMouthFeel=strMouthfeel;
            }
        }
        
        beerDetails.strSw=txtSweet.text;
        beerDetails.strAr=txtAroma.text;
        beerDetails.strBi=txtBitter.text;
        beerDetails.txtAr=txtAr;
        beerDetails.txtBi=txtBi;
        beerDetails.txtSw=txtSw;
        beerDetails.array=array;
        
        [self.navigationController pushViewController:beerDetails animated:YES];
    }
    else
    {
        if ([alertView.message isEqualToString:@"Share your BrewHorn moment now!"])
        {
            ShareScreenViewController *home=[[ShareScreenViewController alloc]init];
            home.chkSkip=1;
            [self.navigationController pushViewController:home animated:NO];
        }
        else
        {
            [self saveData];
        }
    }
}

#pragma mark - View Methods of tableview delegate
// Customize the number of sections in the table view.

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    if (chkArray==1) {
        return [arrayAromaTable count];
        
    }
    else     if (chkArray==2) {
        return [arrayBitterTable count];
        
    }
    else     if (chkArray==3) {
        return [arraySweetTable count];
        
    }
    else  {
        return [arrayTextureTable count];
        
    }
}
// Customize the appearance of table view cells.
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    NSString * cellIdentifier =[NSString stringWithFormat:@"Cell %d",indexPath.row];
    
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:cellIdentifier];
    if (cell == nil) {
        
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:cellIdentifier];
    }
    cell.accessoryType = UITableViewCellAccessoryNone ;
    
    cell.selectionStyle=UITableViewCellSelectionStyleNone;
    int i;
    if (chkArray==1) {
        {
            if ([arrayAromaValue containsObject:[arrayAromaTable objectAtIndex:indexPath.row]])
            {
                
                cell.accessoryType = UITableViewCellAccessoryCheckmark ;
            }
            else
            {
                cell.accessoryType = UITableViewCellAccessoryNone ;
            }
        }
        
        cell.textLabel.text=[arrayAromaTable objectAtIndex:indexPath.row];
        
    }
    else     if (chkArray==2) {
        {
            if ([arrayBitterValue containsObject:[arrayBitterTable objectAtIndex:indexPath.row]])
            {
                
                cell.accessoryType = UITableViewCellAccessoryCheckmark ;
            }
            else
            {
                cell.accessoryType = UITableViewCellAccessoryNone ;
            }
        }
        cell.textLabel.text=[arrayBitterTable objectAtIndex:indexPath.row];
        
    }
    else     if (chkArray==3) {
        {
            if ([arraySweetValue containsObject:[arraySweetTable objectAtIndex:indexPath.row]])
            {
                
                cell.accessoryType = UITableViewCellAccessoryCheckmark ;
            }
            else
            {
                cell.accessoryType = UITableViewCellAccessoryNone ;
            }
        }
        cell.textLabel.text=[arraySweetTable objectAtIndex:indexPath.row];
        
    }
    
    else  {
        {
            if ([arrayTextureValue containsObject:[arrayTextureTable objectAtIndex:indexPath.row]])
            {
                
                cell.accessoryType = UITableViewCellAccessoryCheckmark ;
            }
            else
            {
                cell.accessoryType = UITableViewCellAccessoryNone ;
            }
        }
        cell.textLabel.text=[arrayTextureTable objectAtIndex:indexPath.row];
        
    }
    cell.backgroundColor=[UIColor clearColor];
    return cell;
}
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    int i;
    UITableViewCell *cell = [tableView cellForRowAtIndexPath:indexPath];
    if (cell.accessoryType == UITableViewCellAccessoryCheckmark)
    {
        cell.accessoryType = UITableViewCellAccessoryNone;
        if (chkArray==1) {
            NSLog(@"arraycount=%i",[arrayAromaValue count]);
            for (i=0; i<[arrayAromaValue count]; i++)
            {
                if ([[arrayAromaValue objectAtIndex:i]isEqualToString:[arrayAromaTable objectAtIndex:indexPath.row]])
                {
                    [arrayAromaValue removeObjectAtIndex:i];
                }
            }
        }
        else     if (chkArray==2) {
            for (i=0; i<[arrayBitterValue count]; i++)
            {
                if ([[arrayBitterValue objectAtIndex:i]isEqualToString:[arrayBitterTable objectAtIndex:indexPath.row]])
                {
                    [arrayBitterValue removeObjectAtIndex:i];
                }
            }
        }
        else     if (chkArray==3) {
            for (i=0; i<[arraySweetValue count]; i++)
            {
                if ([[arraySweetValue objectAtIndex:i]isEqualToString:[arraySweetTable objectAtIndex:indexPath.row]])
                {
                    [arraySweetValue removeObjectAtIndex:i];
                }
            }
        }
        
        else  {
            for (i=0; i<[arrayTextureValue count]; i++)
            {
                if ([[arrayTextureValue objectAtIndex:i]isEqualToString:[arrayTextureTable objectAtIndex:indexPath.row]])
                {
                    [arrayTextureValue removeObjectAtIndex:i];
                }
            }
        }
    }
    else
    {
        cell.accessoryType = UITableViewCellAccessoryCheckmark;
        if (chkArray==1) {
            [arrayAromaValue addObject:[arrayAromaTable objectAtIndex:indexPath.row]];
        }
        else     if (chkArray==2) {
            [arrayBitterValue addObject:[arrayBitterTable objectAtIndex:indexPath.row]];
        }
        else     if (chkArray==3) {
            [arraySweetValue addObject:[arraySweetTable objectAtIndex:indexPath.row]];
        }
        
        else  {
            [arrayTextureValue addObject:[arrayTextureTable objectAtIndex:indexPath.row]];
        }
    }
    NSLog(@"the array val is %@",arrayAromaValue);
}
#pragma mark share with facebook
- (void)btnFb
{
    NSString *strBrName=[NSString stringWithFormat:@"#%@",[[NSUserDefaults standardUserDefaults]valueForKey:@"Beername"]];
    NSString *strBrwName=[NSString stringWithFormat:@"#%@",[[NSUserDefaults standardUserDefaults]valueForKey:@"Breweryname"]];
    if (strBrwName.length<2 || [strBrwName isEqualToString:@"#(null)"]) {
        strBrwName=@"";
    }
    if (strBrName.length<2 || [strBrName isEqualToString:@"#(null)"]) {
        strBrName=@"";
    }
    
    NSString *strText=[NSString stringWithFormat:@"I just profiled %@ %@ with @brewhornbeerapp.#brewhorn#craftbeer",strBrwName,strBrName];
    if (strBrName.length==0 && strBrwName.length==0) {
        strText=[NSString stringWithFormat:@"@brewhornbeerapp.#brewhorn#craftbeer"];
    }
    NSString *someTweet = strText;
    if (floor(NSFoundationVersionNumber) > NSFoundationVersionNumber_iOS_6_1)
    {
        SLComposeViewController *tweetSheet = [SLComposeViewController composeViewControllerForServiceType:SLServiceTypeTwitter];
        tweetSheet.completionHandler = ^(SLComposeViewControllerResult result)
        {
            [self dismissModalViewControllerAnimated:YES];
            HomeViewController *home=[[HomeViewController alloc]init];
            [self.navigationController pushViewController:home animated:NO];
            
        };
        [tweetSheet setInitialText:someTweet];
        [self presentViewController:tweetSheet animated:YES completion:NULL];
    }
    else
    {
        // running on iOS5
        if (NSClassFromString(@"TWTweetComposeViewController") && [TWTweetComposeViewController canSendTweet]) {
            
            TWTweetComposeViewController *tweetSheet = [[TWTweetComposeViewController alloc] init];
            tweetSheet.completionHandler = ^(TWTweetComposeViewControllerResult result){
                [self dismissModalViewControllerAnimated:YES];
                HomeViewController *home=[[HomeViewController alloc]init];
                [self.navigationController pushViewController:home animated:NO];
            };
            [tweetSheet setInitialText:someTweet];
            [self presentModalViewController:tweetSheet animated:YES];
        }
        else if ( NSClassFromString(@"SLComposeViewController") && [SLComposeViewController isAvailableForServiceType:SLServiceTypeTwitter] ) {
            
            SLComposeViewController *tweetSheet = [SLComposeViewController composeViewControllerForServiceType:SLServiceTypeTwitter];
            
            [tweetSheet setInitialText:someTweet];
            tweetSheet.completionHandler = ^(SLComposeViewControllerResult result)
            {
                [self dismissModalViewControllerAnimated:YES];
                HomeViewController *home=[[HomeViewController alloc]init];
                [self.navigationController pushViewController:home animated:NO];
            };
            [self presentViewController:tweetSheet animated:YES completion:NULL];
        }
        else
        {
            UIAlertView *alert=[[UIAlertView alloc]initWithTitle:@"Twitter" message:@"Please configure twitter in your phone settings." delegate:nil cancelButtonTitle:@"Ok" otherButtonTitles:nil];
            [alert show];
        }
        
    }
}


@end
