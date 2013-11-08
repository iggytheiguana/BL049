//
//  addBeerViewController.m
//  CraftsBeerApp
//
//  Created by Mandeep Singh on 11/06/13.
//  Copyright (c) 2013 Mandeep Singh. All rights reserved.
//

#import "addBeerViewController.h"
#import "BeerDetails1ViewController.h"
#import "TestScreenViewController.h"
#import "AddBeerInfoViewController.h"
#import "EditBeerInfoViewController.h"
#import "JSON.h"
#import "iToast.h"
#import "Flurry.h"

@interface addBeerViewController ()

@end

@implementation addBeerViewController

@synthesize txtABV,txtBeerName,txtBrewery,txtEvent,txtHipe,txtIBU,txtMood,txtStyle,txtVenue,strEditMode,scrlView,array;
@synthesize strBeerid,lblHeader,btnStyle,imgBeerName,imgBrName,imgStyle,chkHeadder;
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
    arrayPicker=[[NSMutableArray alloc]initWithObjects:@"Ale Styles",@"Lager Style",@"Hybrid Styles",@"FruitBeer",nil];
    
    likeArray2 = [[NSMutableArray alloc]initWithObjects:@"American Adjunct Lager",@"Americen Amber/Red Lager",@"American Double / Imperial Pilsner",@"Bock",@"American Malt Liquor",@"American Pale Lager", @"California Common / Steam Beer",@"Czech Pilsener",@"Doppelbock",@"Dortmunder / Export Lager",@"Euro Dark Lager",@"Euro Pale Lager",@"Euro Strong Lager",@"Eisbock", @"German Pilsener", @"Happoshu",@"Japanese Rice Lager",@"Keller Bier / Zwickel Bier",@"Low Alcohol Beer",@"Light Lager",@"Maibock / Helles Bock",@"Märzen / Oktoberfest",@"Munich Dunkel Lager",@"Munich Helles Lager",@"Rauchbier",@"Schwarzbier",@"Vienna Lager", nil] ;
    
    likeArray1 = [[NSMutableArray alloc]initWithObjects:@"Ale",@"Altbier",@"American Amber / Red Ale",@"American Barleywine",@"American Black Ale",@"American Blonde Ale",@"American Brown Ale",@"American Dark Wheat Ale",@"American Double / Imperial IPA",@"American Double / Imperial Stout",@"American IPA",@"American Pale Ale (APA)",@"American Pale Wheat Ale",@"American Porter",@"American Stout",@"American Strong Ale",@"American Wild Ale",@"Black & Tan",@"Berliner Weissbier",@"Belgian IPA",@"Belgian Pale Ale",@"Belgian Strong Dark Ale",@"Belgian Dark Ale",@"Belgian Strong Pale Ale",@"Bière de Champagne / Bière Brut",@"Bière de Garde",@"Baltic Porter",@"Braggot",@"Black and Tan",@"Cream Ale",@"Chile Beer",@"Dubbel",@"Dunkelweizen",@"English Barleywine",@"English Bitter",@"English Brown Ale",@"English Dark Mild Ale",@"English India Pale Ale(IPA)",@"English Pale Ale",@"English Pale Mild Ale",@"English Porter",@"English Stout",@"English Strong Ale",@"Extra Special/Strong Bitter(ESB)",@"Foreign/Export Stout",@"Faro",@"Flanders Oud Bruin",@"Flanders Red Ale",@"Gueuze",@"Gose",@"Hefeweizen",@"Irish Dry Stout",@"Irish Red Ale",@"Kvass",@"Kolsch",@"Kristalweizen",@"Lambic-Fruit",@"Lambic-Unblended",@"Milk/Sweet Stout",@"Oatmeal Stout",@"Old Ale",@"Pumpkin Ale",@"Quadrupel(Quad)",@"Rye Beer",@"Russian Imperial Stout",@"Roggenbier",@"Saison/Farmhouse Ale",@"Sahti",@"Scotch Ale / Wee Heavy",@"Scottish Ale",@"Scottish Gruit / Ancient Herbed Ale",@"Tripel",@"Wheatwine",@"Witbier",@"Winter Warmer",@"Weizenbock", nil] ;
    
    likeArray3=[[NSMutableArray alloc]initWithObjects:@"Fruit/Vegetable Beer",@"Herbed/Spiced Beer",@"Smoked Beer", nil];
    
    arrayFruitBeer=[[NSMutableArray alloc]initWithObjects:@"Fruit Beer", nil];
    
    arrayEvent=[[NSMutableArray alloc]initWithObjects:@"None",@"SmallGathering",@"GetTogether",@"Dinner",@"BBQ",@"Festival",@"BeerFoodPairing",@"Party",@"HappyHour",@"BottleShare",@"BeerGeekMeetup",@"TastingEvent",@"Vacation",@"WorkTravel",nil];
    arrayHype=[[NSMutableArray alloc]initWithObjects:@"None",@"Recent",@"Longstanding",@"TopXList",@"HighScore",@"GottaHave",@"NeverHeardOf",@"Recommendation",@"JustHeardOf",@"HeardThruGrapevine",nil];
    arrayMood=[[NSMutableArray alloc]initWithObjects:@"PhysicallyTired",@"Longday",@"Happy",@"Frustrated",@"MentallyTired",@"BlowOffSteam",@"Excited",@"FeelingMeh",@"Exhausted",@"FeelingGood",@"FeelingGreat",@"FeelingJustOK",@"RoughDay",@"Celebratory",@"NeedADrink", nil];
    arrayVenue=[[NSMutableArray alloc]initWithObjects:@"Home",@"Patio",@"Porch",@"FriendsHouse",@"FavouriteBar",@"TastingRoom",@"Ballgame",@"NewBar",@"Restaurant",@"Airport",@"Plane",@"Train",@"Cruise",@"VacationSpot",@"Beach",nil];
    memoryArray1=[[NSMutableArray alloc]initWithObjects:@"None",nil];
    memoryArray2=[[NSMutableArray alloc]initWithObjects:@"None",nil];
    memoryArray3=[[NSMutableArray alloc]initWithObjects:@"None",nil];
    
    if ([[UIScreen mainScreen]bounds].size.height==568) {
        scrlView.contentSize=CGSizeMake(320, 420);
        scrlView.frame=CGRectMake(0, 120, 320, 350);
    }
    else
    {
        scrlView.contentSize=CGSizeMake(320, 340);
    }
    if ([strEditMode integerValue]==2)
    {
        [[[[iToast makeText:@"Hype,Mood,Venue and Event values will be reset."] setGravity:iToastGravityTop offsetLeft:0 offsetTop:200] setDuration:2000] show:iToastTypeNotice];
        
        txtABV.text=[array valueForKey:@"abv"];
        txtBeerName.text=[array valueForKey:@"beerName"];
        txtBrewery.text=[array valueForKey:@"brewery"];
        txtEvent.text=@"";
        txtHipe.text=@"";
        txtIBU.text=[array valueForKey:@"ibu"];
        txtMood.text=@"";
        txtStyle.text=[array valueForKey:@"beerStyle"];
        txtVenue.text=@"";
        
        if (txtABV.text.length>0) {
            txtABV.enabled=NO;
        }
        if (txtIBU.text.length>0) {
            txtIBU.enabled=NO;
        }
        if (txtStyle.text.length>0) {
            txtStyle.enabled=NO;
            imgStyle.image=[UIImage imageNamed:@"text_field.png"];
            btnStyle.hidden=YES;
        }
        if (txtBeerName.text.length>0) {
            txtBeerName.enabled=NO;
        }
        if (txtBrewery.text.length>0) {
            txtBrewery.enabled=NO;
        }
        if (chkHeadder==1) {
            lblHeader.text=@"Edit Taste Influencers";
        }
        else
        {
            lblHeader.text=@"Edit Beer and Taste Influencers";
        }
        //  txtBeerName.backgroundColor=[UIColor clearColor];
        //  txtBrewery.backgroundColor=[UIColor clearColor];
        //   txtBrewery.textColor=[UIColor whiteColor];
        //  txtBeerName.textColor=[UIColor whiteColor];
        //  imgBeerName.hidden=YES;
        //  imgBrName.hidden=YES;
        // edit mode
        //  [self getBeerData];
    }
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
}

-(void)viewWillAppear:(BOOL)animated
{
    if ([strEditMode integerValue]==2)
    {
        
    }
    else
    {
        txtABV.text=@"";
        txtBeerName.text=@"";
        txtBrewery.text=@"";
        txtEvent.text=@"";
        txtHipe.text=@"";
        txtIBU.text=@"";
        txtMood.text=@"";
        txtStyle.text=@"";
        txtVenue.text=@"";
        
    }
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}
//   to get beer data
-(void)getBeerData
{
    NSMutableArray *dataArray=[[NSMutableArray alloc]init];
    
    NSString *strUid = [[NSUserDefaults standardUserDefaults]valueForKey:@"user_id"];
    // strUid=@"1";
    NSString *strUrl=[NSString stringWithFormat:kGetBeerDetails,strUid];
    NSLog(@"strUrl: %@", strUrl);
    
    NSURL *jsonURL = [NSURL URLWithString:strUrl];
    
    NSString *responseString = [[NSString alloc]initWithContentsOfURL:jsonURL encoding:NSUTF8StringEncoding error:nil];
    NSLog(@"Response: %@", responseString);
    
    if ([dataArray count]>0)
    {
        [dataArray removeAllObjects];
    }
    
    NSMutableDictionary * root = [responseString JSONValue];
    
    // NSLog(@"the count is %d",[[root objectForKey:@"userClubs"] count]);
    NSArray *data = [root objectForKey:@"getBeerProfile"];
    NSLog(@"the data is %@",[root objectForKey:@"userClubs"]);
    
    if ([[root objectForKey:@"userClubs"]isKindOfClass:[NSNumber
                                                        class] ]) {
        NSLog(@"string class");
        if ([[root objectForKey:@"userClubs"] integerValue]==-1)
        {
            
        }
        
    }
    
    else if (data==NULL)
    {
        [dataArray addObject:@""];
    }
    else if([[root valueForKey:@"Error"]integerValue]==1)
    {
        NSLog(@"the value is -1");
        
    }
    else if ([data isKindOfClass:[NSNull class]])
    {
    }
    else
    {
        dataArray=[data mutableCopy];
        NSLog(@"plotCrimePositions date is %@",dataArray);
        NSLog(@"Result array count %@",[dataArray valueForKey:@"aroma"]);
        txtBrewery.text=[dataArray valueForKey:@"brewery"];
        txtBeerName.text=[dataArray valueForKey:@"beerName"];
        txtStyle.text=[dataArray valueForKey:@"beerStyle"];
        txtABV.text=[dataArray valueForKey:@"abv"];
        txtIBU.text=[dataArray valueForKey:@"ibu"];
        txtMood.text=[dataArray valueForKey:@"mood"];
        txtVenue.text=[dataArray valueForKey:@"venue"];
        txtEvent.text=[dataArray valueForKey:@"event"];
        txtHipe.text=[dataArray valueForKey:@"hype"];
    }
}
////    to save the beer data
- (IBAction)btn_Save:(id)sender
{
    
    if (txtBeerName.text.length==0) {
        UIAlertView *alert=[[UIAlertView alloc]initWithTitle:@"" message:@"Enter the Beer name." delegate:nil cancelButtonTitle:@"Ok" otherButtonTitles: nil];
        [alert show];
    }
    else
    {
        
        [self resignTextFields];
        [applicationDelegate checkInternetConnection];
        if([applicationDelegate internetWorking] ==0)
        {
            [applicationDelegate show_LoadingIndicator];
            NSString *strUseriD= [[NSUserDefaults standardUserDefaults]valueForKey:@"user_id"];
            NSString * xmlString;
            
            
            NSURL * serviceUrl;
            if ([strEditMode integerValue]==2)
            {
                xmlString =
                [NSString stringWithFormat:@"<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?><editUserBeer><beerId><![CDATA[%@]]></beerId><userId><![CDATA[%@]]></userId><brewery><![CDATA[%@]]></brewery><beerName><![CDATA[%@]]></beerName><beerStyle><![CDATA[%@]]></beerStyle><abv><![CDATA[%@]]></abv><ibu><![CDATA[%@]]></ibu><mood><![CDATA[%@]]></mood><venue><![CDATA[%@]]></venue><event><![CDATA[%@]]></event><hype><![CDATA[%@]]></hype></editUserBeer>",strBeerid,strUseriD,txtBrewery.text,txtBeerName.text,txtStyle.text,txtABV.text,txtIBU.text,txtMood.text,txtVenue.text,txtEvent.text,txtHipe.text];
                serviceUrl = [NSURL URLWithString:kEditBeer];
                
            }
            else
            {
                xmlString =
                [NSString stringWithFormat:@"<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?><addUserBeer><userId><![CDATA[%@]]></userId><brewery><![CDATA[%@]]></brewery><beerName><![CDATA[%@]]></beerName><beerStyle><![CDATA[%@]]></beerStyle><abv><![CDATA[%@]]></abv><ibu><![CDATA[%@]]></ibu><mood><![CDATA[%@]]></mood><venue><![CDATA[%@]]></venue><event><![CDATA[%@]]></event><hype><![CDATA[%@]]></hype></addUserBeer>",strUseriD,txtBrewery.text,txtBeerName.text,txtStyle.text,txtABV.text,txtIBU.text,txtMood.text,txtVenue.text,txtEvent.text,txtHipe.text];
                
                
                serviceUrl = [NSURL URLWithString:kAddBeer];
            }
            NSLog(@"the xmlstring is =%@  and the url is %@",xmlString,serviceUrl);
            
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
            if ([strEditMode integerValue]==2) {
                serRslt=[NSString stringWithFormat:@"%@",[dictReg objectForKey:@"editUserBeer"]];
            }
            else
            {
                serRslt=[NSString stringWithFormat:@"%@",[dictReg objectForKey:@"addUserBeer"]];
            }
            if ([serRslt integerValue]>0)
            {
                [[NSUserDefaults standardUserDefaults]setValue:txtBeerName.text forKey:@"Beername"];
                [[NSUserDefaults standardUserDefaults]setValue:txtBrewery.text forKey:@"Breweryname"];
                [[NSUserDefaults standardUserDefaults]synchronize];
                
                [applicationDelegate hide_LoadingIndicator];
                if ([strEditMode integerValue]==2) {
                    [Flurry logEvent:@"Edit_Beer"];
                    TestScreenViewController *beer=[[TestScreenViewController alloc]init];
                    beer.strid=strBeerid;
                    beer.array=array;
                    beer.chkUser=@"2";
                    [self.navigationController pushViewController:beer animated:YES];
                    
                }
                else
                {
                    [Flurry logEvent:@"Add_Beer"];
                    TestScreenViewController *beerDetails=[[TestScreenViewController alloc]init];
                    beerDetails.strid=serRslt;
                    [self.navigationController pushViewController:beerDetails animated:YES];
                }
                
                return;
                
                UIAlertView *alert=[[UIAlertView alloc] initWithTitle:@"Alert!" message:@"Beer Saved." delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil];
                [alert show];
            }
            else if([serRslt integerValue]== -4)
            {
                [applicationDelegate hide_LoadingIndicator];
                UIAlertView *alert=[[UIAlertView alloc] initWithTitle:@"Alert!" message:@"Beer name exists." delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil];
                [alert show];
            }
            else if([serRslt integerValue]== -2)
            {
                [applicationDelegate hide_LoadingIndicator];
                UIAlertView *alert=[[UIAlertView alloc] initWithTitle:@"Alert!" message:@"Server Issue." delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil];
                [alert show];
            }
            else if([serRslt integerValue]== -3)
            {
                [applicationDelegate hide_LoadingIndicator];
                UIAlertView *alert=[[UIAlertView alloc] initWithTitle:@"Alert!" message:@"In-valid User." delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil];
                [alert show];
            }
            serRslt = nil;
        }
        else
        {
            UIAlertView *alertViewNetwrk = [[UIAlertView alloc] initWithTitle:@"Alert!"
                                                                      message:kAlertInternet
                                                                     delegate:self
                                                            cancelButtonTitle:@"OK"
                                                            otherButtonTitles:nil, nil];
            [alertViewNetwrk show];
            alertViewNetwrk = nil;
        }
        [applicationDelegate hide_LoadingIndicator];
    }
}
#pragma mark - Button actions
- (IBAction)btn_Style:(id)sender
{
    [self resignTextFields];
    selectPicker=6;
    strStyleOldValue=txtStyle.text ;
    if (strStyleOldValue.length==0) {
        txtStyle.text=[likeArray1 objectAtIndex:0];
    }
    {
        [UIView beginAnimations:nil context:nil];
        [UIView setAnimationDuration:0.5];
        
        self.view.frame=CGRectMake(0, -50, 320, [[UIScreen mainScreen]bounds].size.height+50);
        [UIView commitAnimations];
        
        actionSheet = [[UIActionSheet alloc] initWithTitle:nil delegate:nil cancelButtonTitle:nil destructiveButtonTitle:nil otherButtonTitles:nil];
        
        [actionSheet setActionSheetStyle:UIActionSheetStyleBlackTranslucent];
        
        CGRect pickerFrame = CGRectMake(0, 40, 0, 0);
        
        pickerViewStyle = [[UIPickerView alloc] initWithFrame:pickerFrame];
        pickerViewStyle.showsSelectionIndicator = YES;
        
        
        pickerViewStyle.dataSource = self;
        pickerViewStyle.delegate = self;
        
        UIToolbar *pickerToolbar = [[UIToolbar alloc] initWithFrame:CGRectMake(0, 0, 320, 44)];
        pickerToolbar.barStyle = UIBarStyleBlackOpaque;
        [pickerToolbar sizeToFit];
        
        NSMutableArray *barItems = [[NSMutableArray alloc] init];
        
        UIBarButtonItem *flexSpace = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemFlexibleSpace target:self action:nil];
        [barItems addObject:flexSpace];
        
        UIBarButtonItem *doneBtn = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemDone target:self action:@selector(doneActionSheet:)];
        [barItems addObject:doneBtn];
        
        UIBarButtonItem *cancelBtn = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemCancel target:self action:@selector(dismissActionSheet:)];
        [barItems addObject:cancelBtn];
        
        [pickerToolbar setItems:barItems animated:YES];
        
        [actionSheet addSubview:pickerToolbar];
        [actionSheet addSubview:pickerViewStyle];
        [actionSheet showInView:self.view];
        [actionSheet setBounds:CGRectMake(0,0,320, 464)];
    }
    return;
}

- (IBAction)btn_Mood:(id)sender
{
    [self resignTextFields];
    
    selectPicker=2;
    strMoodOldValue=txtMood.text ;
    if (strMoodOldValue.length==0) {
        txtMood.text=[arrayMood objectAtIndex:0];
    }
    
    {
        [UIView beginAnimations:nil context:nil];
        [UIView setAnimationDuration:0.5];
        
        self.view.frame=CGRectMake(0, -100, 320, [[UIScreen mainScreen]bounds].size.height+100);
        [UIView commitAnimations];
        actionSheet = [[UIActionSheet alloc] initWithTitle:nil delegate:nil cancelButtonTitle:nil destructiveButtonTitle:nil otherButtonTitles:nil];
        
        [actionSheet setActionSheetStyle:UIActionSheetStyleBlackTranslucent];
        CGRect pickerFrame = CGRectMake(0, 40, 0, 0);
        pickerView1 = [[UIPickerView alloc] initWithFrame:pickerFrame];
        pickerView1.showsSelectionIndicator = YES;
        pickerView1.dataSource = self;
        pickerView1.delegate = self;
        
        UIToolbar *pickerToolbar = [[UIToolbar alloc] initWithFrame:CGRectMake(0, 0, 320, 44)];
        pickerToolbar.barStyle = UIBarStyleBlackOpaque;
        [pickerToolbar sizeToFit];
        NSMutableArray *barItems = [[NSMutableArray alloc] init];
        UIBarButtonItem *flexSpace = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemFlexibleSpace target:self action:nil];
        [barItems addObject:flexSpace];
        UIBarButtonItem *doneBtn = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemDone target:self action:@selector(doneActionSheet:)];
        [barItems addObject:doneBtn];
        UIBarButtonItem *cancelBtn = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemCancel target:self action:@selector(dismissActionSheet:)];
        [barItems addObject:cancelBtn];
        
        [pickerToolbar setItems:barItems animated:YES];
        
        [actionSheet addSubview:pickerToolbar];
        [actionSheet addSubview:pickerView1];
        [actionSheet showInView:self.view];
        [actionSheet setBounds:CGRectMake(0,0,320, 464)];
    }
    
    return;
}

- (IBAction)btn_Venue:(id)sender
{
    [self resignTextFields];
    
    selectPicker=3;
    strVenueOldValue=txtVenue.text ;
    if (strVenueOldValue.length==0) {
        txtVenue.text=[arrayVenue objectAtIndex:0];
    }
    {
        [UIView beginAnimations:nil context:nil];
        [UIView setAnimationDuration:0.5];
        self.view.frame=CGRectMake(0, -150, 320, [[UIScreen mainScreen]bounds].size.height+150);
        [UIView commitAnimations];
        actionSheet = [[UIActionSheet alloc] initWithTitle:nil delegate:nil cancelButtonTitle:nil destructiveButtonTitle:nil otherButtonTitles:nil];
        [actionSheet setActionSheetStyle:UIActionSheetStyleBlackTranslucent];
        CGRect pickerFrame = CGRectMake(0, 40, 0, 0);
        
        pickerView1 = [[UIPickerView alloc] initWithFrame:pickerFrame];
        pickerView1.showsSelectionIndicator = YES;
        pickerView1.dataSource = self;
        pickerView1.delegate = self;
        
        UIToolbar *pickerToolbar = [[UIToolbar alloc] initWithFrame:CGRectMake(0, 0, 320, 44)];
        pickerToolbar.barStyle = UIBarStyleBlackOpaque;
        [pickerToolbar sizeToFit];
        
        NSMutableArray *barItems = [[NSMutableArray alloc] init];
        
        UIBarButtonItem *flexSpace = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemFlexibleSpace target:self action:nil];
        [barItems addObject:flexSpace];
        
        UIBarButtonItem *doneBtn = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemDone target:self action:@selector(doneActionSheet:)];
        [barItems addObject:doneBtn];
        
        UIBarButtonItem *cancelBtn = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemCancel target:self action:@selector(dismissActionSheet:)];
        [barItems addObject:cancelBtn];
        
        [pickerToolbar setItems:barItems animated:YES];
        
        [actionSheet addSubview:pickerToolbar];
        [actionSheet addSubview:pickerView1];
        [actionSheet showInView:self.view];
        [actionSheet setBounds:CGRectMake(0,0,320, 464)];
        
    }
    
    return;
}

- (IBAction)btn_Event:(id)sender
{
    [self resignTextFields];
    
    selectPicker=4;
    strEventOldValue=txtEvent.text ;
    if (strEventOldValue.length==0) {
        txtEvent.text=[arrayEvent objectAtIndex:0];
    }
    {
        [UIView beginAnimations:nil context:nil];
        [UIView setAnimationDuration:0.5];
        
        self.view.frame=CGRectMake(0, -200, 320, [[UIScreen mainScreen]bounds].size.height+200);
        [UIView commitAnimations];
        actionSheet = [[UIActionSheet alloc] initWithTitle:nil delegate:nil cancelButtonTitle:nil destructiveButtonTitle:nil otherButtonTitles:nil];
        
        [actionSheet setActionSheetStyle:UIActionSheetStyleBlackTranslucent];
        
        CGRect pickerFrame = CGRectMake(0, 40, 0, 0);
        
        pickerView1 = [[UIPickerView alloc] initWithFrame:pickerFrame];
        pickerView1.showsSelectionIndicator = YES;
        pickerView1.dataSource = self;
        pickerView1.delegate = self;
        
        UIToolbar *pickerToolbar = [[UIToolbar alloc] initWithFrame:CGRectMake(0, 0, 320, 44)];
        pickerToolbar.barStyle = UIBarStyleBlackOpaque;
        [pickerToolbar sizeToFit];
        
        NSMutableArray *barItems = [[NSMutableArray alloc] init];
        
        UIBarButtonItem *flexSpace = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemFlexibleSpace target:self action:nil];
        [barItems addObject:flexSpace];
        
        UIBarButtonItem *doneBtn = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemDone target:self action:@selector(doneActionSheet:)];
        [barItems addObject:doneBtn];
        
        UIBarButtonItem *cancelBtn = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemCancel target:self action:@selector(dismissActionSheet:)];
        [barItems addObject:cancelBtn];
        
        [pickerToolbar setItems:barItems animated:YES];
        
        [actionSheet addSubview:pickerToolbar];
        [actionSheet addSubview:pickerView1];
        [actionSheet showInView:self.view];
        [actionSheet setBounds:CGRectMake(0,0,320, 464)];
    }
    
    return;
}
- (IBAction)btn_Hipe:(id)sender
{
    [self resignTextFields];
    selectPicker=5;
    strHipeOldValue=txtHipe.text ;
    if (strHipeOldValue.length==0) {
        txtHipe.text=[arrayHype objectAtIndex:0];
    }
    {
        [UIView beginAnimations:nil context:nil];
        [UIView setAnimationDuration:0.5];
        self.view.frame=CGRectMake(0, -200, 320, [[UIScreen mainScreen]bounds].size.height+200);
        [UIView commitAnimations];
        
        actionSheet = [[UIActionSheet alloc] initWithTitle:nil delegate:nil cancelButtonTitle:nil destructiveButtonTitle:nil otherButtonTitles:nil];
        [actionSheet setActionSheetStyle:UIActionSheetStyleBlackTranslucent];
        CGRect pickerFrame = CGRectMake(0, 40, 0, 0);
        pickerView1 = [[UIPickerView alloc] initWithFrame:pickerFrame];
        pickerView1.showsSelectionIndicator = YES;
        pickerView1.dataSource = self;
        pickerView1.delegate = self;
        
        UIToolbar *pickerToolbar = [[UIToolbar alloc] initWithFrame:CGRectMake(0, 0, 320, 44)];
        pickerToolbar.barStyle = UIBarStyleBlackOpaque;
        [pickerToolbar sizeToFit];
        NSMutableArray *barItems = [[NSMutableArray alloc] init];
        UIBarButtonItem *flexSpace = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemFlexibleSpace target:self action:nil];
        [barItems addObject:flexSpace];
        
        UIBarButtonItem *doneBtn = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemDone target:self action:@selector(doneActionSheet:)];
        [barItems addObject:doneBtn];
        
        UIBarButtonItem *cancelBtn = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemCancel target:self action:@selector(dismissActionSheet:)];
        [barItems addObject:cancelBtn];
        [pickerToolbar setItems:barItems animated:YES];
        [actionSheet addSubview:pickerToolbar];
        [actionSheet addSubview:pickerView1];
        [actionSheet showInView:self.view];
        [actionSheet setBounds:CGRectMake(0,0,320, 464)];
    }
    return;
}
- (IBAction)btn_Back:(id)sender
{
    [self resignTextFields];
    [self.navigationController popViewControllerAnimated:YES];
}

- (IBAction)btnResignView:(id)sender
{
    [self resignTextFields];
}

- (IBAction)info:(id)sender
{
    if (chkHeadder==1) {
        EditBeerInfoViewController *add=[[EditBeerInfoViewController alloc]init];
        [self.navigationController presentViewController:add animated:YES completion:nil];
    }
    else
    {
        AddBeerInfoViewController *add=[[AddBeerInfoViewController alloc]init];
        [self.navigationController presentViewController:add animated:YES completion:nil];
    }
}
-(void)resignTextFields
{
    [txtVenue resignFirstResponder];
    [txtStyle resignFirstResponder];
    [txtMood resignFirstResponder];
    [txtIBU resignFirstResponder];
    [txtHipe resignFirstResponder];
    [txtEvent resignFirstResponder];
    [txtBrewery resignFirstResponder];
    [txtBeerName resignFirstResponder];
    [txtABV resignFirstResponder];
    if ([[UIScreen mainScreen]bounds].size.height==568) {
        self.view.frame=CGRectMake(0, 0, 320, 568);
    }
    else
    {
        self.view.frame=CGRectMake(0, 0, 320, 480);
    }
}
#pragma mark- UItextField delegate methods
- (BOOL)textFieldShouldReturn:(UITextField *)textField
{
    NSInteger nextTag = textField.tag + 1;
    
    UIResponder* nextResponder = [textField.superview viewWithTag:nextTag];
    
    if (nextResponder)
    {
        [nextResponder becomeFirstResponder];
    }
    else
    {
        if ([[UIScreen mainScreen]bounds].size.height==568) {
            self.view.frame=CGRectMake(0, 0, 320, 568);
        }
        else
        {
            self.view.frame=CGRectMake(0, 0, 320, 480);
        }
        [textField resignFirstResponder];
        
    }
    return YES;
}
- (void)textFieldDidBeginEditing:(UITextField *)textField

{
    if (textField==txtIBU) {
        if ([[UIScreen mainScreen]bounds].size.height==568) {
            self.view.frame=CGRectMake(0, -50, 320, [[UIScreen mainScreen]bounds].size.height);
        }
        else
        {
            self.view.frame=CGRectMake(0, -100, 320, [[UIScreen mainScreen]bounds].size.height);
        }
        
    }
    if (textField==txtABV) {
        if ([[UIScreen mainScreen]bounds].size.height==568) {
        }
        else
        {
            self.view.frame=CGRectMake(0, -50, 320, [[UIScreen mainScreen]bounds].size.height);
        }
    }
}
#pragma mark - Pickerview delegate
- (NSInteger)numberOfComponentsInPickerView:(UIPickerView *)pickerView;
{
    
    if (pickerView==pickerViewStyle) {
        return 2;
    }
    else
    {
        return 1;
    }
}
- (NSInteger)pickerView:(UIPickerView *)pickerView numberOfRowsInComponent:(NSInteger)component;
{
    
    
    if (pickerView==pickerViewStyle) {
        if (component == 0) {
            selectPicker=6;
            return [arrayPicker count];
        }
        else
        {
            selectPicker=6;
            
            switch (componentValue) {
                    
                    
                case 0:
                {
                    return [likeArray1 count];
                }
                    
                    break;
                    
                case 1:
                {
                    return [likeArray2 count]
                    ;
                }
                    
                    break ;
                case 2:
                {
                    return [likeArray3 count]
                    ;
                }
                    break ;
                    
                case 3:
                {
                    return [arrayFruitBeer count]
                    ;
                }
                    
                    break ;
                    
                default:
                    break;
            }
        }
        
        
        
    }
    else{
        if (selectPicker==2) {
            return [arrayMood count];
        }
        else if (selectPicker==3) {
            return [arrayVenue count];
        }
        else if (selectPicker==4) {
            return [arrayEvent count];
        }
        else if (selectPicker==5) {
            return [arrayHype count];
        }
        
    }
    return YES;
}

- (UIView *)pickerView:(UIPickerView *)pickerView viewForRow:(NSInteger)row forComponent:(NSInteger)component reusingView:(UIView *)view
{
    if (pickerView == pickerViewStyle) {
        view = nil ;
        if(view == nil )
        {
            view = [[UIView alloc]initWithFrame:CGRectMake(0, 0, 150, 30)];
            view.backgroundColor = [UIColor clearColor];
        }
        
        UILabel *lbl = [[UILabel alloc]initWithFrame:CGRectMake(10, 0, 140, 28)];
        lbl.adjustsFontSizeToFitWidth = YES ;
        lbl.backgroundColor = [UIColor clearColor];
        
        if(selectPicker == 6)
        {
            if (component==0) {
                lbl.text =  [arrayPicker objectAtIndex:row];
            }
            else
            {
                switch (componentValue) {
                    case 0:
                    {
                        lbl.text =  [likeArray1 objectAtIndex:row];
                    }
                        break;
                        
                    case 1:
                    {
                        lbl.text =  [likeArray2 objectAtIndex:row];
                        ;
                    }
                        break ;
                    case 2:
                    {
                        lbl.text =  [likeArray3 objectAtIndex:row];
                        ;
                    }
                        break ;
                    case 3:
                    {
                        lbl.text =  [arrayFruitBeer objectAtIndex:0];
                        ;
                    }
                        break ;
                        
                    default:
                        break;
                }
            }
        }
        [view addSubview:lbl];
        return  view ;
    }
    view = nil ;
    if(view == nil )
    {
        view = [[UIView alloc]initWithFrame:CGRectMake(0, 0, 300, 30)];
        view.backgroundColor = [UIColor clearColor];
    }
    
    UILabel *lbl = [[UILabel alloc]initWithFrame:CGRectMake(10, 0, 280, 28)];
    lbl.adjustsFontSizeToFitWidth = YES ;
    lbl.backgroundColor = [UIColor clearColor];
    
    if (selectPicker==2) {
        lbl.text =  [arrayMood objectAtIndex:row];
    }
    else if (selectPicker==3) {
        lbl.text =  [arrayVenue objectAtIndex:row];
    }
    else if (selectPicker==4) {
        lbl.text =  [arrayEvent objectAtIndex:row];
    }
    else if (selectPicker==5) {
        lbl.text =  [arrayHype objectAtIndex:row];
    }
    else
    {
        lbl.text =  [arrayPicker objectAtIndex:row];
    }
    [view addSubview:lbl];
    
    return  view ;
}
-(void)pickerView:(UIPickerView *)pickerView didSelectRow:(NSInteger)row
      inComponent:(NSInteger)component
{
    NSString *text;
    
    if (selectPicker==6)
    {
        if(component ==0 )
        {
            switch (row) {
                case 0:
                {
                    componentValue = 0;
                    [pickerViewStyle reloadComponent:1];
                    text=[NSString stringWithFormat:@"%@",[likeArray1 objectAtIndex:row]];
                    
                }
                    break;
                    
                    
                case 1:
                {
                    componentValue = 1;
                    [pickerViewStyle reloadComponent:1];
                    text=[NSString stringWithFormat:@"%@",[likeArray2 objectAtIndex:row]];
                    
                }
                    break;
                case 2:
                {
                    componentValue = 2;
                    [pickerViewStyle reloadComponent:1];
                    text=[NSString stringWithFormat:@"%@",[likeArray3 objectAtIndex:row]];
                    
                }
                    break;
                case 3:
                {
                    componentValue = 3;
                    [pickerViewStyle reloadComponent:1];
                    text=[NSString stringWithFormat:@"%@",[arrayFruitBeer objectAtIndex:0]];
                    
                }
                    break;
                    
                    
                default:
                    break;
            }
        }
        
        else
        {
            switch (componentValue) {
                case 0:
                {
                    text=[NSString stringWithFormat:@"%@",[likeArray1 objectAtIndex:row]];
                    NSLog(@"the text is %@",text);
                    
                }
                    break;
                    
                    
                case 1:
                {
                    text=[NSString stringWithFormat:@"%@",[likeArray2 objectAtIndex:row]];
                    NSLog(@"the text is %@",text);
                    
                }
                    break;
                case 2:
                {
                    text=[NSString stringWithFormat:@"%@",[likeArray3 objectAtIndex:row]];
                    NSLog(@"the text is %@",text);
                    
                }
                    break;
                case 3:
                {
                    text=[NSString stringWithFormat:@"%@",[arrayFruitBeer objectAtIndex:0]];
                    NSLog(@"the text is %@",text);
                    
                }
                    break;
                    
                    
                default:
                    break;
            }
            
        }
        
        NSLog(@"the text is %@",text);
        
        txtStyle.text=text;
    }
    
    else if (selectPicker==2) {
        text=[arrayMood objectAtIndex:row];
        txtMood.text=text;
    }
    else if (selectPicker==3) {
        text=[arrayVenue objectAtIndex:row];
        txtVenue.text=text;
    }
    else if (selectPicker==4) {
        text=[arrayEvent objectAtIndex:row];
        txtEvent.text=text;
    }
    else
    {
        text=[arrayHype objectAtIndex:row];
        txtHipe.text=text;
    }
}
- (void)selectRow:(NSInteger)row inComponent:(NSInteger)component animated:(BOOL)animated
{
    [pickerView1 selectRow:2 inComponent:0 animated:YES];
}

- (CGFloat)pickerView:(UIPickerView *)pickerView widthForComponent:(NSInteger)component
{
    if (pickerView==pickerViewStyle) {
        return 150;
    }
    else{
        return 300;
    }
}
- (CGFloat)pickerView:(UIPickerView *)pickerView rowHeightForComponent:(NSInteger)component
{
    return 50;
}
-(void)doneActionSheet:(id)sender
{
    
    [UIView beginAnimations:nil context:nil];
    [UIView setAnimationDuration:0.2];
    self.view.frame=CGRectMake(0,0, 320, [[UIScreen mainScreen] bounds].size.height-20);
    [UIView commitAnimations];
    pickerView1.hidden=YES;
    [actionSheet dismissWithClickedButtonIndex:0 animated:YES];
}
-(void)dismissActionSheet:(id)sender
{
    
    if (selectPicker==6)
    {
        txtStyle.text=strStyleOldValue;
    }
    
    else if (selectPicker==2) {
        txtMood.text=strMoodOldValue;
    }
    else if (selectPicker==3) {
        txtVenue.text=strVenueOldValue;
    }
    else if (selectPicker==4) {
        txtEvent.text=strEventOldValue;
    }
    else
    {
        txtHipe.text=strHipeOldValue;
    }
    
    [UIView beginAnimations:nil context:nil];
    [UIView setAnimationDuration:0.2];
    self.view.frame=CGRectMake(0,0, 320, [[UIScreen mainScreen] bounds].size.height-20);
    [UIView commitAnimations];
    [actionSheet dismissWithClickedButtonIndex:0 animated:YES];
}

- (void)viewDidUnload {
    [self setScrlView:nil];
    [self setImgBrName:nil];
    [self setImgBeerName:nil];
    [self setImgStyle:nil];
    [super viewDidUnload];
}
@end
