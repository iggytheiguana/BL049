
#import "BeerDetails2ViewController.h"
#import "JSON.h"
#import "HomeViewController.h"
#import "ShareScreenViewController.h"
#import "iToast.h"
#import <QuartzCore/QuartzCore.h>
#import "InfoViewController.h"
#import "YourTasteInfoViewController.h"
#import "ProfileBeerInfoViewController.h"
#define FbClientID @"366509033392814"

@interface BeerDetails2ViewController ()

@end
@implementation BeerDetails2ViewController
@synthesize sliderAddictive,sliderBooziness,sliderSour;
@synthesize valMalt,valMouthFeel,valBitter,valYeast,valAroma,valSweet,striD,strChkUser;
@synthesize lblAroma3,lblAroma4,lblAroma5,lblAroma6,lblAroma7;
@synthesize lblBitter3,lblSweet7,lblBitter4,lblBitter5,lblBitter6,lblBitter7,lblSweet3,lblSweet4,lblSweet5,lblSweet6,lblTop,scrlView,txtAddictive,txtFlavour,txtSour,txtWarmth,switchAddictive,switchBooziness,switchSour,viewTbl,tblSelectVal;
@synthesize chkUser,strAr,strBi,strSw,txtSw,txtBi,txtAr,array,btnFlavour,btnSour,btnWarmth,lblHeader,lblHeader1,lblAddictive,lblBooziness,btnAddi,btnInfo,additivebtn,boozinessbtn;
@synthesize fbGraph;
@synthesize accountStore;
@synthesize facebookAccount, twitterAccount;
@synthesize tblNewView,viewNew,likeArray4;

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
    chkTable=2;
    [[[[iToast makeText:@"Tap the parameter for a description."] setGravity:iToastGravityTop offsetLeft:0 offsetTop:[[UIScreen mainScreen]bounds].size.height-140] setDuration:2000] show:iToastTypeNotice];
    
    arrayPicker=[[NSMutableArray alloc]initWithObjects:@"Fruit/Vegetable",@"Spices/Herbs",@"Wood",@"Barrel Aging",@"Miscellaneous",nil];
    arrayDiscriptorDetails=[[NSMutableArray alloc]init];
    
    strSour=@"3";
    strAddictive=@"3";
    strBooziness=@"3";
    NSLog(@"VAL MOUTH FEEL IS %@",valMouthFeel);
    arrayAromaTable=[[NSMutableArray alloc]initWithObjects:@"light",@"moderate",@"heavy",@"residual",@"rubbingalcohol",@"spicy",nil];
    
    arrayBitterTable=[[NSMutableArray alloc]initWithObjects:@"slight",@"moderate",@"hot",@"residual",@"burning",@"rubbingalcohol",@"warm",nil];
    
    arraySweetTable=[[NSMutableArray alloc]initWithObjects:@"tart",@"vinegar",@"puckering",@"acidic",@"horseblanket",@"barnyard",nil];
    
    arrayAromaValue=[[NSMutableArray alloc]init];
    arrayBitterValue=[[NSMutableArray alloc]init];
    arraySweetValue=[[NSMutableArray alloc]init];
    arrayAdditiveValue=[[NSMutableArray alloc]init];
    
    if ([[UIScreen mainScreen]bounds].size.height==568) {
        scrlView.contentSize=CGSizeMake(260, 500);
    }
    else
    {
        scrlView.contentSize=CGSizeMake(260, 420);
    }
    if ([strChkUser isEqualToString:@"2"]) {
        lblTop.text=@"Optional Taste Parameters";
        btnInfo.hidden=YES;
    }
    if ([chkUser isEqualToString:@"4"]) {
        lblTop.text=@"Edit Optional Taste Parameters";
        btnInfo.hidden=YES;
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
        txtAddictive.hidden=YES;
        txtFlavour.hidden=YES;
        txtSour.hidden=YES;
        txtWarmth.hidden=YES;
        btnWarmth.hidden=YES;
        btnSour.hidden=YES;
        btnFlavour.hidden=YES;
        scrlView.contentSize=CGSizeMake(260, 300);
        lblAddictive.frame=CGRectMake(10, 110, 80, 21);
        additivebtn.frame=CGRectMake(10, 110, 80, 21);
        lblAroma3.frame=CGRectMake(13, 140, 50, 15);
        lblAroma4.frame=CGRectMake(69, 140, 50, 15);
        lblAroma5.frame=CGRectMake(125, 140, 50, 15);
        lblAroma6.frame=CGRectMake(181, 140, 50, 15);
        lblAroma7.frame=CGRectMake(237, 140, 50, 15);
        sliderAddictive.frame=CGRectMake(8, 160, 242, 23);
        
        lblBooziness.frame=CGRectMake(10, 200, 80, 21);
        boozinessbtn.frame=CGRectMake(10, 200, 80, 21);
        lblBitter3.frame=CGRectMake(13, 230, 50, 15);
        lblBitter4.frame=CGRectMake(69, 230, 50, 15);
        lblBitter5.frame=CGRectMake(125, 230, 50, 15);
        lblBitter6.frame=CGRectMake(181, 230, 50, 15);
        lblBitter7.frame=CGRectMake(237, 230, 50, 15);
        sliderBooziness.frame=CGRectMake(8, 250, 242, 23);
        
        if ([chkUser isEqualToString:@"4"]) {
            
            sliderAddictive.value=[[arrayDet objectAtIndex:7]floatValue];
            valAdditive=[[arrayDet objectAtIndex:7]integerValue];
            strAddictive=[arrayDet objectAtIndex:7];
            
            sliderSour.value=[[arrayDet objectAtIndex:6]floatValue];
            valSour=[[arrayDet objectAtIndex:6]integerValue];
            strSour=[arrayDet objectAtIndex:6];
            
            sliderBooziness.value=[[arrayDet objectAtIndex:8]floatValue];
            valBooziness=[[arrayDet objectAtIndex:8]integerValue];
            strBooziness=[arrayDet objectAtIndex:8];
        }
        
        switchAddictive.hidden=YES;
        switchBooziness.hidden=YES;
        switchSour.hidden=YES;
        
        
    }
    else
    {
        switchAddictive.hidden=NO;
        switchBooziness.hidden=NO;
        switchSour.hidden=NO;
        strSwchSour=@"1";
        strSwchAddictive=@"1";
        strSwchBooziness=@"1";
        
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
        
        {
            aromaMin=[[arrayDet objectAtIndex:7]integerValue]-2;
            aromaMax=[[arrayDet objectAtIndex:7]integerValue]+2;
            sliderAddictive.minimumValue=aromaMin;
            sliderAddictive.maximumValue=aromaMax;
            strAddictive=[arrayDet objectAtIndex:7];
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
            strSour=[arrayDet objectAtIndex:6];
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
            strBooziness=[arrayDet objectAtIndex:8];
            sliderBooziness.value=[[arrayDet objectAtIndex:8]floatValue];
            valBooziness=[[arrayDet objectAtIndex:8]integerValue];
            lblBitter3.text=[NSString stringWithFormat:@"%i",maltMin];
            lblBitter4.text=[NSString stringWithFormat:@"%i",maltMin+1];
            lblBitter5.text=[NSString stringWithFormat:@"%i",maltMin+2];
            lblBitter6.text=[NSString stringWithFormat:@"%i",maltMin+3];
            lblBitter7.text=[NSString stringWithFormat:@"%i",maltMax];
            
        }
        
    }
    
    
    
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    
    NSString *strBrName=[NSString stringWithFormat:@"#%@",[[NSUserDefaults standardUserDefaults]valueForKey:@"Beername"]];
    NSString *strBrwName=[NSString stringWithFormat:@"#%@",[[NSUserDefaults standardUserDefaults]valueForKey:@"Breweryname"]];
    strBrName=[strBrName stringByReplacingOccurrencesOfString:@" " withString:@""];
    strBrwName=[strBrwName stringByReplacingOccurrencesOfString:@" " withString:@""];
    
    if (strBrwName.length<2 || [strBrwName isEqualToString:@"#(null)"]) {
        strBrwName=@"";
    }
    if (strBrName.length<2 || [strBrName isEqualToString:@"#(null)"]) {
        strBrName=@"";
    }
    
    //    NSString *strText=[NSString stringWithFormat:@"I just profiled %@ %@ with @brewhornbeerapp.#brewhorn#craftbeer",strBrwName,strBrName];
    
    NSString *strText=[NSString stringWithFormat:@"I just profiled %@ %@ with @BrewHornBeerApp.#brewhorn",strBrwName,strBrName];
    if (strBrName.length==0 && strBrwName.length==0) {
        strText=[NSString stringWithFormat:@"@brewhornbeerapp.#brewhorn#craftbeer"];
    }
    
    someTweet = strText;
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (BOOL)text:(UITextView *)textField
{
    //[textField resignFirstResponder];
    return YES;
}

#pragma mark - TextView delegate

- (void)textFieldDidBeginEditing:(UITextField *)textField
{
    if (textField==txtFlavour || textField==txtWarmth ||textField==txtAddictive) {
        self.view.frame=CGRectMake(0, -130, 320, [[UIScreen mainScreen]bounds].size.height-20);
    }
}
- (BOOL)textFieldShouldReturn:(UITextField *)textField
{
    self.view.frame=CGRectMake(0,0, 320, [[UIScreen mainScreen]bounds].size.height-20);
    self.scrlView.scrollsToTop=YES;
    [textField resignFirstResponder];
    // [textField resignFirstResponder];
    
    return YES;
}

-(void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event
{
    [txtAddictive resignFirstResponder];
    [txtFlavour resignFirstResponder];
    [txtSour resignFirstResponder];
    [txtWarmth resignFirstResponder];
    self.view.frame=CGRectMake(0,0, 320, [[UIScreen mainScreen]bounds].size.height-20);
    self.scrlView.scrollsToTop=YES;
}
- (IBAction)switch_Booziness:(id)sender
{
    if (switchBooziness.isOn==NO) {
        strSwchBooziness=@"0";
        sliderBooziness.enabled=NO;
        [sliderBooziness setValue:3.0];
        btnFlavour.hidden=YES;
        btnWarmth.hidden=YES;
        txtFlavour.text=@"";
        txtWarmth.text=@"";
        txtFlavour.enabled=NO;
        txtWarmth.enabled=NO;
        // [arrayAromaValue removeAllObjects];
        [arrayBitterValue removeAllObjects];
        txtWarmth.backgroundColor=[UIColor lightGrayColor];
        txtFlavour.backgroundColor=[UIColor lightGrayColor];
    }
    else
    {
        NSMutableArray *arrayDet=[[[NSUserDefaults standardUserDefaults]objectForKey:@"arrayDetails"]mutableCopy];
        [sliderBooziness setValue:[[arrayDet objectAtIndex:8]floatValue]];
        btnFlavour.hidden=NO;
        btnWarmth.hidden=NO;
        txtWarmth.backgroundColor=[UIColor whiteColor];
        txtFlavour.backgroundColor=[UIColor whiteColor];
        txtFlavour.enabled=YES;
        txtWarmth.enabled=YES;
        strSwchBooziness=@"1";
        sliderBooziness.enabled=YES;
    }
}

- (IBAction)switch_sour:(id)sender
{
    if (switchSour.isOn==NO) {
        strSwchSour=@"0";
        sliderSour.enabled=NO;
        [sliderSour setValue:3.0];
        btnSour.hidden=YES;
        txtSour.text=@"";
        txtSour.enabled=NO;
        [arraySweetValue removeAllObjects];
        txtSour.backgroundColor=[UIColor lightGrayColor];
        
    }
    else
    {
        NSMutableArray *arrayDet=[[[NSUserDefaults standardUserDefaults]objectForKey:@"arrayDetails"]mutableCopy];
        [sliderSour setValue:[[arrayDet objectAtIndex:6]floatValue]];
        btnSour.hidden=NO;
        txtSour.enabled=YES;
        txtSour.backgroundColor=[UIColor whiteColor];
        strSwchSour=@"1";
        sliderSour.enabled=YES;
    }
}
- (IBAction)switch_Addictive:(id)sender
{
    if (switchAddictive.isOn==NO) {
        strSwchAddictive=@"0";
        sliderAddictive.enabled=NO;
        txtAddictive.text=@"";
        txtAddictive.enabled=NO;
        btnAddi.enabled=NO;
        [txtAddictive resignFirstResponder];
        self.view.frame=CGRectMake(0,0, 320, [[UIScreen mainScreen]bounds].size.height-20);
        txtAddictive.backgroundColor=[UIColor lightGrayColor];
        [sliderAddictive setValue:3.0];
    }
    else
    {
        NSMutableArray *arrayDet=[[[NSUserDefaults standardUserDefaults]objectForKey:@"arrayDetails"]mutableCopy];
        [sliderAddictive setValue:[[arrayDet objectAtIndex:7]floatValue]];
        txtAddictive.backgroundColor=[UIColor whiteColor];
        txtAddictive.enabled=YES;
        btnAddi.enabled=YES;
        strSwchAddictive=@"1";
        sliderAddictive.enabled=YES;
    }
}

#pragma mark Button actions

- (IBAction)btnBack:(id)sender
{
    [self.navigationController popViewControllerAnimated:YES];
    return;
}

- (IBAction)btnClose:(id)sender
{
    int i;
    NSString *strAroma=@"";
    NSString *strBitter=@"";
    NSString *strSweet=@"";
    NSString *strAdditive=@"";
    
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
        txtFlavour.text=strAroma;
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
        txtWarmth.text=strBitter;
    }
    else  if (chkArray==4) {
        for (i=0; i<[arrayAdditiveValue count]; i++)
        {
            if (i==0) {
                strAdditive=[arrayAdditiveValue objectAtIndex:i];
                
            }
            else
            {
                
                strAdditive=[NSString stringWithFormat:@"%@,%@",strAdditive,[arrayAdditiveValue objectAtIndex:i]];
            }
        }
        txtAddictive.text=strAdditive;
    }
    
    else  {
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
        txtSour.text=strSweet;
    }
    
    viewTbl.hidden=YES;
}

- (IBAction)btn_Warmth:(id)sender {
    chkArray=2;
    viewTbl.hidden=NO;
    [txtAddictive resignFirstResponder];
    self.view.frame=CGRectMake(0,0, 320, [[UIScreen mainScreen]bounds].size.height-20);
    [tblSelectVal reloadData];
    
}

- (IBAction)btn_Flavour:(id)sender {
    chkArray=1;
    viewTbl.hidden=NO;
    [txtAddictive resignFirstResponder];
    self.view.frame=CGRectMake(0,0, 320, [[UIScreen mainScreen]bounds].size.height-20);
    [tblSelectVal reloadData];
}

- (IBAction)btn_Sour:(id)sender
{
    chkArray=3;
    viewTbl.hidden=NO;
    [txtAddictive resignFirstResponder];
    self.view.frame=CGRectMake(0,0, 320, [[UIScreen mainScreen]bounds].size.height-20);
    [tblSelectVal reloadData];
}

- (IBAction)btn_Addictive:(id)sender
{
    {
        chkArray=4;
        viewTbl.hidden=NO;
        [txtAddictive resignFirstResponder];
        self.view.frame=CGRectMake(0,0, 320, [[UIScreen mainScreen]bounds].size.height-20);
        [tblSelectVal reloadData];
    }
    return;
    selectPicker=6;
    strStyleOldValue=txtAddictive.text ;
    if (strStyleOldValue.length==0) {
        txtAddictive.text=[likeArray1 objectAtIndex:0];
    }
    {
        [UIView beginAnimations:nil context:nil];
        [UIView setAnimationDuration:0.5];
        
        self.view.frame=CGRectMake(0, -150, 320, [[UIScreen mainScreen]bounds].size.height+150);
        if ([[UIScreen mainScreen]bounds].size.height==568) {
            scrlView.frame=CGRectMake(30, 83, 260, 400);
        }
        else
        {
            scrlView.frame=CGRectMake(30, 83, 260, 300);
        }
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

- (IBAction)btn_info:(id)sender
{
    if ([strChkUser isEqualToString:@"2"]) {
        YourTasteInfoViewController *tasteInfo=[[YourTasteInfoViewController alloc]init];
        [self.navigationController presentViewController:tasteInfo animated:YES completion:nil];
    }
    else
    {
        ProfileBeerInfoViewController *tasteInfo=[[ProfileBeerInfoViewController alloc]init];
        [self.navigationController presentViewController:tasteInfo animated:YES completion:nil];
    }
}

- (IBAction)sour:(id)sender
{
    if ([strChkUser isEqualToString:@"2"])
    {
        UIAlertView *alert=[[UIAlertView alloc]initWithTitle:@"Help" message:@"Consider how much you like sour or tart flavors?" delegate:nil cancelButtonTitle:@"Thanks" otherButtonTitles:nil];
        [alert show];
    }
    else
    {
        UIAlertView *alert=[[UIAlertView alloc]initWithTitle:@"Help" message:@"How puckering is the beer? Is there a specific flavor associated with the sour / tart experience?" delegate:nil cancelButtonTitle:@"Thanks" otherButtonTitles:nil];
        [alert show];
    }
}

- (IBAction)addictive:(id)sender
{
    if ([strChkUser isEqualToString:@"2"])
    {
        UIAlertView *alert=[[UIAlertView alloc]initWithTitle:@"Help" message:@"Consider how much you might like fruit, spices or herbs, vegetables, wood aging, or liquor barrel aging in a beer?" delegate:nil cancelButtonTitle:@"Thanks" otherButtonTitles:nil];
        [alert show];
    }
    else
    {
        UIAlertView *alert=[[UIAlertView alloc]initWithTitle:@"Help" message:@"How well did the additive come through? Did it overpower the base beer? Was the additive even detectable?" delegate:nil cancelButtonTitle:@"Thanks" otherButtonTitles:nil];
        [alert show];
    }
}

- (IBAction)booziness:(id)sender
{
    if ([strChkUser isEqualToString:@"2"])
    {
        UIAlertView *alert=[[UIAlertView alloc]initWithTitle:@"Help" message:@"Consider all alcohols you consume. How much do you prefer an alcohol flavor and sensation?" delegate:nil cancelButtonTitle:@"Thanks" otherButtonTitles:nil];
        [alert show];
    }
    else
    {
        UIAlertView *alert=[[UIAlertView alloc]initWithTitle:@"Help" message:@"Does the beer have an alcohol flavor of hard liquor? Does it have a warm, spicy, or burning sensation?" delegate:nil cancelButtonTitle:@"Thanks" otherButtonTitles:nil];
        [alert show];
    }
}

- (IBAction)btnCloseTblView:(id)sender
{
    NSString *strAdditive;
    int i;
    for (i=0; i<[arrayAdditiveValue count]; i++)
    {
        if (i==0) {
            strAdditive=[arrayAdditiveValue objectAtIndex:i];
            
        }
        else
        {
            
            strAdditive=[NSString stringWithFormat:@"%@,%@",strAdditive,[arrayAdditiveValue objectAtIndex:i]];
        }
    }
    txtAddictive.text=strAdditive;
    chkTable=2;
    viewNew.hidden=YES;
    viewTbl.hidden=YES;
}

- (IBAction)btnSave:(id)sender
{
    [applicationDelegate show_LoadingIndicator];
    [applicationDelegate checkInternetConnection];
    if([applicationDelegate internetWorking] ==0)
    {
        if ([strChkUser isEqualToString:@"2"]) {
            
            [applicationDelegate show_LoadingIndicator];
            NSString *strUseriD= [[NSUserDefaults standardUserDefaults]valueForKey:@"user_id"];
            NSURL * serviceUrl;
            NSString * xmlString;
            {
                xmlString =
                [NSString stringWithFormat:@"<?xml version=\"1.0\" encoding=\"UTF-8\" ?><editUserTasteProfile><beerId><![CDATA[%@]]></beerId><userId><![CDATA[%@]]></userId><aroma><![CDATA[%@]]></aroma><sweet><![CDATA[%@]]></sweet><bitter><![CDATA[%@]]></bitter><malt><![CDATA[%@]]></malt><yeast><![CDATA[%@]]></yeast><mouthFeel><![CDATA[%@]]></mouthFeel><sour><![CDATA[%@]]></sour><additive><![CDATA[%@]]></additive><booziness><![CDATA[%@]]></booziness><sour_status><![CDATA[%@]]></sour_status><additive_status><![CDATA[%@]]></additive_status><booziness_status><![CDATA[%@]]></booziness_status><yeast_status><![CDATA[1]]></yeast_status></editUserTasteProfile>",striD,strUseriD,valAroma,valSweet,valBitter,valMalt,valYeast,valMouthFeel,strSour,strAddictive,strBooziness,@"1",@"1",@"1"];
                NSLog(@"the xmlstring 4 is =%@",xmlString);
                serviceUrl = [NSURL URLWithString:kEditUserBeerTaste];
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
            NSMutableDictionary*dictReg = [strVal JSONValue];
            NSString *serRslt;
            {
                serRslt=[NSString stringWithFormat:@"%@",[dictReg objectForKey:@"editUserTasteProfile"]];
            }
            
            if ([serRslt integerValue]>0)
            {
               
                NSArray *arrayBeerDet2=[[NSArray alloc]initWithObjects:valAroma,valSweet,valBitter,valMalt,valYeast,valMouthFeel,strSour,strAddictive,strBooziness,@"1",@"1",@"1", nil];
                NSLog(@"the result in arrayBeerDet2 = %@",arrayBeerDet2);
                [[NSUserDefaults standardUserDefaults]setObject:arrayBeerDet2 forKey:@"arrayDetails"];
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
            
            serRslt = nil;
              [applicationDelegate hide_LoadingIndicator];
        }
        else
        {
            [applicationDelegate show_LoadingIndicator];
            
            if ([strSwchAddictive integerValue]==0) {
                strAddictive=@"0";
            }
            if ([strSwchBooziness integerValue]==0) {
                strBooziness=@"0";
            }
            if ([strSwchSour integerValue]==0) {
                strSour=@"0";
            }
            
            NSString *strUseriD= [[NSUserDefaults standardUserDefaults]valueForKey:@"user_id"];
            
            NSURL * serviceUrl;
            NSString * xmlString;
            if ([chkUser integerValue]==2) {
                xmlString =[NSString stringWithFormat:@"<?xml version=\"1.0\" encoding=\"UTF-8\" ?><editBeerProfile><beerId><![CDATA[%@]]></beerId><userId><![CDATA[%@]]></userId><aroma><![CDATA[%@]]></aroma><sweet><![CDATA[%@]]></sweet><bitter><![CDATA[%@]]></bitter><malt><![CDATA[%@]]></malt><yeast><![CDATA[%@]]></yeast><mouthFeel><![CDATA[%@]]></mouthFeel><sour><![CDATA[%@]]></sour><additive><![CDATA[%@]]></additive><booziness><![CDATA[%@]]></booziness><aroma_cmt><![CDATA[%@]]></aroma_cmt><sweet_cmt><![CDATA[%@]]></sweet_cmt><bitter_cmt><![CDATA[%@]]></bitter_cmt><malt_cmt><![CDATA[%@]]></malt_cmt><yeast_cmt><![CDATA[%@]]></yeast_cmt><mouthFeel_cmt><![CDATA[%@]]></mouthFeel_cmt><sour_cmt><![CDATA[%@]]></sour_cmt><additive_cmt><![CDATA[%@]]></additive_cmt><booziness_cmt><![CDATA[%@]]></booziness_cmt></editBeerProfile>",striD,strUseriD,valAroma,valSweet,valBitter,valMalt,valYeast,valMouthFeel,strSour,strAddictive,strBooziness,txtAr,txtSw,txtBi,strSour,strAddictive,strBooziness,@"1",@"1",@"1"];
                
                NSLog(@"the xmlstring is =%@",xmlString);
                serviceUrl = [NSURL URLWithString:@"http://brewhorn.com/app_data/webserviceController/editBeerProfile"];
                
            }
            else{
                xmlString =
                [NSString stringWithFormat:@"<?xml version=\"1.0\" encoding=\"UTF-8\" ?><addBeerProfile><beerId><![CDATA[%@]]></beerId><userId><![CDATA[%@]]></userId><aroma><![CDATA[%@]]></aroma><sweet><![CDATA[%@]]></sweet><bitter><![CDATA[%@]]></bitter><malt><![CDATA[%@]]></malt><yeast><![CDATA[%@]]></yeast><mouthFeel><![CDATA[%@]]></mouthFeel><sour><![CDATA[%@]]></sour><additive><![CDATA[%@]]></additive><booziness><![CDATA[%@]]></booziness></addBeerProfile>",striD,strUseriD,valAroma,valSweet,valBitter,valMalt,valYeast,valMouthFeel,strSour,strAddictive,strBooziness];
                
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
            else{
                serRslt=[NSString stringWithFormat:@"%@",[dictReg objectForKey:@"addBeerProfile"]];
            }
            if ([serRslt integerValue]>0)
            {
                [applicationDelegate hide_LoadingIndicator];
                
                if ([[NSUserDefaults standardUserDefaults]boolForKey:@"AutomateSharingFb"]  && [[NSUserDefaults standardUserDefaults]boolForKey:@"AutomateSharingTwitter"])
                {
                    [applicationDelegate show_LoadingIndicator];
                    [self performSelector:@selector(shareOnFb:) withObject:someTweet afterDelay:0.0];
                    [self performSelector:@selector(shareOnTwitter:) withObject:someTweet afterDelay:3.0];
                }
                else if ([[NSUserDefaults standardUserDefaults]boolForKey:@"AutomateSharingFb"])
                {
                    //                    [self btnFb];
                    [applicationDelegate show_LoadingIndicator];
                    [self performSelector:@selector(shareOnFb:) withObject:someTweet afterDelay:0.0];
                }
                else if ([[NSUserDefaults standardUserDefaults]boolForKey:@"AutomateSharingTwitter"])
                {
                    [applicationDelegate show_LoadingIndicator];
                    [self performSelector:@selector(shareOnTwitter:) withObject:someTweet afterDelay:0.0];
                }
                else
                {
                    HomeViewController *home=[[HomeViewController alloc]init];
                    [self.navigationController pushViewController:home animated:NO];
                }
                UIAlertView *alert=[[UIAlertView alloc] initWithTitle:@"Alert!" message:@"Share your BrewHorn moment now!" delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil];
                // [alert show];
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
    //    for (UIViewController* viewController in self.navigationController.viewControllers)
    //    {
    //
    //        if ([viewController isKindOfClass:[HomeViewController class]] ) {
    //            HomeViewController *home = (HomeViewController*)viewController;
    //            [self.navigationController popToViewController:home animated:YES];
    //        }
    //    }
    //
}
- (IBAction)slider_Sour:(id)sender
{
    chkButton=1;
    valSour=sliderSour.value;
    valSour = lroundf(sliderSour.value);
    [sliderSour setValue:valSour animated:YES];
    strSour=[NSString stringWithFormat:@"%i",(int)valSour];
}

- (IBAction)slider_Addictive:(id)sender
{
    chkButton=2;
    valAdditive=sliderAddictive.value;
    valAdditive = lroundf(sliderAddictive.value);
    [sliderAddictive setValue:valAdditive animated:YES];
    
    strAddictive=[NSString stringWithFormat:@"%i",(int)valAdditive];
}

- (IBAction)slider_Booziness:(id)sender
{
    chkButton=3;
    valBooziness=sliderBooziness.value;
    valBooziness = lroundf(sliderBooziness.value);
    [sliderBooziness setValue:valBooziness animated:YES];
    
    strBooziness=[NSString stringWithFormat:@"%i",(int)valBooziness];
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
    
    if ([strChkUser isEqualToString:@"2"])
    {
        if (strChkVal==4)
        {
            [[[[iToast makeText:@"You prefer somewhat less."] setGravity:iToastGravityTop offsetLeft:0 offsetTop:40] setDuration:500] show:iToastTypeNotice];
        }
        else if (strChkVal==5)
        {
            [[[[iToast makeText:@"You prefer an average amount."] setGravity:iToastGravityTop offsetLeft:0 offsetTop:40] setDuration:500] show:iToastTypeNotice];
        }
        else if (strChkVal==3)
        {
            [[[[iToast makeText:@"You prefer much less."] setGravity:iToastGravityTop offsetLeft:0 offsetTop:40] setDuration:500] show:iToastTypeNotice];
        }
        else if (strChkVal==6)
        {
            [[[[iToast makeText:@"You prefer more than."] setGravity:iToastGravityTop offsetLeft:0 offsetTop:40] setDuration:500] show:iToastTypeNotice];
        }
        else if (strChkVal==7)
        {
            [[[[iToast makeText:@"You prefer much more."] setGravity:iToastGravityTop offsetLeft:0 offsetTop:40] setDuration:500] show:iToastTypeNotice];
        }
        
    }
    else
    {
        if (strChkVal==4)
        {
            [[[[iToast makeText:@"The beer's taste was somewhat less than you prefer."] setGravity:iToastGravityTop offsetLeft:0 offsetTop:40] setDuration:500] show:iToastTypeNotice];
        }
        else if (strChkVal==5)
        {
            [[[[iToast makeText:@"The beer's taste met your baseline taste preferences."] setGravity:iToastGravityTop offsetLeft:0 offsetTop:40] setDuration:500] show:iToastTypeNotice];
        }
        else if (strChkVal==3)
        {
            [[[[iToast makeText:@"The beer's taste was much less than you prefer."] setGravity:iToastGravityTop offsetLeft:0 offsetTop:40] setDuration:500] show:iToastTypeNotice];
        }
        else if (strChkVal==6)
        {
            [[[[iToast makeText:@"The beer's taste was somewhat more than you prefer."] setGravity:iToastGravityTop offsetLeft:0 offsetTop:40] setDuration:500] show:iToastTypeNotice];
        }
        else if (strChkVal==7)
        {
            [[[[iToast makeText:@"The beer's taste was much more than you prefer."] setGravity:iToastGravityTop offsetLeft:0 offsetTop:40] setDuration:500] show:iToastTypeNotice];
        }    }
}

#pragma mark- AlertView delegate methods

- (void)alertView:(UIAlertView *)alertView didDismissWithButtonIndex:(NSInteger)buttonIndex
{
    if ([alertView.message isEqualToString:@"Profile Saved."])
    {
        HomeViewController *homeView=[[HomeViewController alloc]init];
        [self.navigationController pushViewController:homeView animated:YES];
    }
    
    if(alertView.tag==3)
    {
        for (UIViewController* viewController in self.navigationController.viewControllers)
        {
            if ([viewController isKindOfClass:[HomeViewController class]] ) {
                HomeViewController *home = (HomeViewController*)viewController;
                [self.navigationController popToViewController:home animated:YES];
            }
        }
    }
    
    if ([alertView.message isEqualToString:@"Share your BrewHorn moment now!"])
    {
        ShareScreenViewController *home=[[ShareScreenViewController alloc]init];
        home.chkSkip=1;
        [self.navigationController pushViewController:home animated:NO];
    }
    
}
#pragma mark - View Methods of tableview delegate
// Customize the number of sections in the table view.

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    
    if (chkTable!=2) {
        if (tblNewView) {
            return [arrayDiscriptorDetails count];
        }
    }
    else
    {
        if (chkArray==1) {
            return [arrayAromaTable count];
            
        }
        else     if (chkArray==2) {
            return [arrayBitterTable count];
            
        }
        else     if (chkArray==4) {
            return [arrayPicker count];
            
        }
        else  {
            return [arraySweetTable count];
            
        }
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
    if (chkTable!=2) {
        if (tableView==tblNewView) {
            
            if (chkDiscriptor==0) {
                if ([arrayAdditiveValue containsObject:[arrayDiscriptorDetails objectAtIndex:indexPath.row]])
                {
                    
                    cell.accessoryType = UITableViewCellAccessoryCheckmark ;
                }
                else
                {
                    cell.accessoryType = UITableViewCellAccessoryNone ;
                }
            }
            cell.textLabel.text=[arrayDiscriptorDetails objectAtIndex:indexPath.row];
            
        }
        
    }
    else
    {
        if (tableView==tblNewView) {
            
        }
        else
        {
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
            else     if (chkArray==4) {
                if ([strSelectDescriptor isEqualToString:[arrayPicker objectAtIndex:indexPath.row]])
                {
                    
                    cell.accessoryType = UITableViewCellAccessoryCheckmark ;
                }
                else
                {
                    cell.accessoryType = UITableViewCellAccessoryNone ;
                }
                
                cell.textLabel.text=[arrayPicker objectAtIndex:indexPath.row];
                
            }
            
            else  {
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
        }
    }
    cell.backgroundColor=[UIColor clearColor];
    
    return cell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    int i;
    UITableViewCell *cell = [tableView cellForRowAtIndexPath:indexPath];
    if (chkTable!=2) {
        if (cell.accessoryType == UITableViewCellAccessoryCheckmark)
        {
            cell.accessoryType = UITableViewCellAccessoryNone;
            for (i=0; i<[arrayAdditiveValue count]; i++)
            {
                if ([[arrayAdditiveValue objectAtIndex:i]isEqualToString:[arrayDiscriptorDetails objectAtIndex:indexPath.row]])
                {
                    [arrayAdditiveValue removeObjectAtIndex:i];
                }
            }
        }
        else
        {
            [arrayAdditiveValue addObject:[arrayDiscriptorDetails objectAtIndex:indexPath.row]];
        }
        [tblNewView reloadData];
    }
    else
    {
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
            else     if (chkArray==4) {
                chkTable=3;
                
                if (indexPath.row==0) {
                    if (!([strSelectDescriptor isEqualToString:[arrayPicker objectAtIndex:0]])) {
                        if ([arrayDiscriptorDetails count]>0) {
                            [arrayDiscriptorDetails removeAllObjects];
                        }
                    }
                    likeArray1=[[NSMutableArray alloc]initWithObjects:@"Mango",@"Minimal",@"Moderate",@"Heavy",@"Raspberry",@"Blueberry",@"Elderberry",@"Blackberry",@"Apple",@"Cucumber",@"Banana",@"Strawberry",@"Peppers",@"Approcot",@"Kiwi",@"Apple",@"Melon",@"Mulberry",@"Atemoiaberry",@"Seabuckthornberry",@"Raisins",@"Goldenraisins",@"Cranberry",nil];
                    
                    arrayDiscriptorDetails=likeArray1;
                }
                else if (indexPath.row==1)
                {
                    if (!([strSelectDescriptor isEqualToString:[arrayPicker objectAtIndex:1]])) {
                        if ([arrayDiscriptorDetails count]>0) {
                            [arrayDiscriptorDetails removeAllObjects];
                        }
                    }
                    likeArray2=[[NSMutableArray alloc]initWithObjects:@"Minimal",@"Moderate",@"Heavy",@"Vanilla",@"Sage",@"Peppercorn",@"Salt",@"Smoke",@"Rosemary",@"Thyme",@"Coriander",@"Cummin",@"Cinnamon",@"Mint",@"Rosehips",nil];
                    
                    arrayDiscriptorDetails=likeArray2;
                }
                else if (indexPath.row==2)
                {
                    if (!([strSelectDescriptor isEqualToString:[arrayPicker objectAtIndex:2]])) {
                        if ([arrayDiscriptorDetails count]>0) {
                            [arrayDiscriptorDetails removeAllObjects];
                        }
                    }
                    likeArray3=[[NSMutableArray alloc]initWithObjects:@"Minimal",@"Moderate",@"Heavy",@"Oak",@"Pecan",@"Mesquite",@"Aburana",nil];
                    
                    arrayDiscriptorDetails=likeArray3;
                }
                else if (indexPath.row==3)
                {
                    if (!([strSelectDescriptor isEqualToString:[arrayPicker objectAtIndex:3]])) {
                        if ([arrayDiscriptorDetails count]>0) {
                            [arrayDiscriptorDetails removeAllObjects];
                        }
                    }
                    arrayFruitBeer=[[NSMutableArray alloc]initWithObjects:@"Minimal",@"Moderate",@"Heavy",@"Oak",@"Bourbon",@"Whiskey",@"Rum",@"Tequila",@"Chardonnay",@"Whitewine",@"Cabernet",@"Redwine",@"Port",@"Palosanto",nil];
                    
                    arrayDiscriptorDetails=arrayFruitBeer;
                }
                else if (indexPath.row==4)
                {
                    if (!([strSelectDescriptor isEqualToString:[arrayPicker objectAtIndex:4]])) {
                        if ([arrayDiscriptorDetails count]>0) {
                            [arrayDiscriptorDetails removeAllObjects];
                        }
                    }
                    likeArray4=[[NSMutableArray alloc]initWithObjects:@"Minimal",@"Moderate",@"Heavy",@"Pecans",@"Macaroot",@"Chocolate",@"Coffee",@"Oyster",@"Clams",@"Brettanomyces",nil];
                    
                    arrayDiscriptorDetails=likeArray4;
                }
                [tblNewView reloadData];
                viewNew.hidden=NO;
            }
            else  {
                for (i=0; i<[arraySweetValue count]; i++)
                {
                    if ([[arraySweetValue objectAtIndex:i]isEqualToString:[arraySweetTable objectAtIndex:indexPath.row]])
                    {
                        [arraySweetValue removeObjectAtIndex:i];
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
            else     if (chkArray==4) {
                chkTable=3;
                NSLog(@"the array val is %@",likeArray4);
                
                if ([arrayDiscriptorDetails count]>0) {
                    [arrayDiscriptorDetails removeAllObjects];
                }
                if (indexPath.row==0) {
                    likeArray1=[[NSMutableArray alloc]initWithObjects:@"Mango",@"Minimal",@"Moderate",@"Heavy",@"Raspberry",@"Blueberry",@"Elderberry",@"Blackberry",@"Apple",@"Cucumber",@"Banana",@"Strawberry",@"Peppers",@"Approcot",@"Kiwi",@"Apple",@"Melon",@"Mulberry",@"Atemoiaberry",@"Seabuckthornberry",@"Raisins",@"Goldenraisins",@"Cranberry",nil];
                    
                    arrayDiscriptorDetails=likeArray1;
                }
                else if (indexPath.row==1)
                {
                    likeArray2=[[NSMutableArray alloc]initWithObjects:@"Minimal",@"Moderate",@"Heavy",@"Vanilla",@"Sage",@"Peppercorn",@"Salt",@"Smoke",@"Rosemary",@"Thyme",@"Coriander",@"Cummin",@"Cinnamon",@"Mint",@"Rosehips",nil];
                    
                    arrayDiscriptorDetails=likeArray2;
                }
                else if (indexPath.row==2)
                {
                    likeArray3=[[NSMutableArray alloc]initWithObjects:@"Minimal",@"Moderate",@"Heavy",@"Oak",@"Pecan",@"Mesquite",@"Aburana",nil];
                    
                    arrayDiscriptorDetails=likeArray3;
                }
                else if (indexPath.row==3)
                {
                    arrayFruitBeer=[[NSMutableArray alloc]initWithObjects:@"Minimal",@"Moderate",@"Heavy",@"Oak",@"Bourbon",@"Whiskey",@"Rum",@"Tequila",@"Chardonnay",@"Whitewine",@"Cabernet",@"Redwine",@"Port",@"Palosanto",nil];
                    
                    arrayDiscriptorDetails=arrayFruitBeer;
                }
                else if (indexPath.row==4)
                {
                    likeArray4=[[NSMutableArray alloc]initWithObjects:@"Minimal",@"Moderate",@"Heavy",@"Pecans",@"Macaroot",@"Chocolate",@"Coffee",@"Oyster",@"Clams",@"Brettanomyces",nil];
                    
                    arrayDiscriptorDetails=likeArray4;
                }
                NSLog(@"the array val is %@",likeArray4);
                strSelectDescriptor=[arrayPicker objectAtIndex:indexPath.row];
                [tblNewView reloadData];
                viewNew.hidden=NO;
            }
            else  {
                [arraySweetValue addObject:[arraySweetTable objectAtIndex:indexPath.row]];
            }
        }
    }
}

-(void)doneActionSheet:(id)sender
{
    
    [UIView beginAnimations:nil context:nil];
    [UIView setAnimationDuration:0.2];
    self.view.frame=CGRectMake(0,0, 320, [[UIScreen mainScreen] bounds].size.height-20);
    if ([[UIScreen mainScreen]bounds].size.height==568) {
        scrlView.frame=CGRectMake(30, 83, 260, 370);
    }
    else
    {
        scrlView.frame=CGRectMake(30, 83, 260, 300);
    }
    [UIView commitAnimations];
    
    [actionSheet dismissWithClickedButtonIndex:0 animated:YES];
}

-(void)dismissActionSheet:(id)sender
{
    {
        txtAddictive.text=strStyleOldValue;
    }
    
    
    [UIView beginAnimations:nil context:nil];
    [UIView setAnimationDuration:0.2];
    self.view.frame=CGRectMake(0,0, 320, [[UIScreen mainScreen] bounds].size.height-20);
    if ([[UIScreen mainScreen]bounds].size.height==568) {
        scrlView.frame=CGRectMake(30, 83, 260, 370);
    }
    else
    {
        scrlView.frame=CGRectMake(30, 83, 260, 300);
    }
    [UIView commitAnimations];
    [actionSheet dismissWithClickedButtonIndex:0 animated:YES];
}

#pragma mark share with Twitter

- (void)btnFb
{
    NSString *strBrName=[NSString stringWithFormat:@"#%@",[[NSUserDefaults standardUserDefaults]valueForKey:@"Beername"]];
    NSString *strBrwName=[NSString stringWithFormat:@"#%@",[[NSUserDefaults standardUserDefaults]valueForKey:@"Breweryname"]];
    strBrName=[strBrName stringByReplacingOccurrencesOfString:@" " withString:@""];
    strBrwName=[strBrwName stringByReplacingOccurrencesOfString:@" " withString:@""];
    
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
    someTweet = strText;
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

#pragma mark Fb Twitter sharing

- (IBAction)shareOnFb:(id)sender
{
    accountStore = [[ACAccountStore alloc] init];
    ACAccountType *facebookAccountType = [self.accountStore accountTypeWithAccountTypeIdentifier:ACAccountTypeIdentifierFacebook];
    
    NSString *key =@"589751437754650";
    
    NSArray * permissions = @[@"publish_stream", @"publish_actions"];
    //    NSArray * permissions = @[@"email"];
    
    NSMutableDictionary *options = [[NSMutableDictionary alloc] initWithObjectsAndKeys:key, ACFacebookAppIdKey, permissions, ACFacebookPermissionsKey, ACFacebookAudienceOnlyMe, ACFacebookAudienceKey, nil];
    
    [self.accountStore requestAccessToAccountsWithType:facebookAccountType
                                               options:options completion:^(BOOL granted, NSError *error)
     {
         if (granted)
         {
            
             
             
                  if(granted && error == nil)
                  {
                      NSArray *accounts = [self.accountStore accountsWithAccountType:facebookAccountType];
                      self.facebookAccount = [accounts lastObject];
                      
                      NSDictionary *parameters = @{@"message": sender};
                      
                      NSURL *feedURL = [NSURL URLWithString:@"https://graph.facebook.com/me/feed"];
                      
                      SLRequest *feedRequest = [SLRequest
                                                requestForServiceType:SLServiceTypeFacebook
                                                requestMethod:SLRequestMethodPOST
                                                URL:feedURL
                                                parameters:parameters];
                      
                      feedRequest.account =self.facebookAccount;
                      
                      [feedRequest performRequestWithHandler:^(NSData *responseData,
                                                               NSHTTPURLResponse *urlResponse, NSError *error)
                       {
                           if (responseData)
                           {
                               fb=YES;
                               [applicationDelegate hide_LoadingIndicator];
                               NSMutableDictionary  *dict = [NSJSONSerialization JSONObjectWithData:responseData options:NSJSONReadingMutableContainers error:&error];
                               if (![[dict objectForKey:@"id"] length])
                               {
                                   [self performSelectorOnMainThread:@selector(alertPost:) withObject:@"Error while facebook sharing." waitUntilDone:YES];
                                   NSLog(@"%@",dict);
                               }
                               else
                               {
                                   NSLog(@"%@",dict);
                                   [self performSelectorOnMainThread:@selector(alertPost:) withObject:@"Post successfully uploaded on facebook" waitUntilDone:YES];
                               }
                           }
                           else
                           {
                              
                                   NSLog(@"Facebook Sharing Error is %@",[error localizedDescription]);
                               
                               [self performSelectorOnMainThread:@selector(alertPost:) withObject:[error localizedDescription] waitUntilDone:YES];
                           }
                       }];
                      
                  }
                  else
                  {
                      NSLog(@"Error is %@",[error localizedDescription]);
                      [self performSelectorOnMainThread:@selector(alertPost:) withObject:@"Error while facebook sharing." waitUntilDone:YES];
                  }
             
             
             NSArray *accounts = [self.accountStore accountsWithAccountType:facebookAccountType];
             self.facebookAccount = [accounts lastObject];
         }
         else
         {
             NSString *str=[NSString stringWithFormat:@"%@",[error localizedDescription]];
             NSLog(@"Error is : %@",str);
             [[[[iToast makeText:str] setGravity:iToastGravityTop offsetLeft:0 offsetTop:[[UIScreen mainScreen]bounds].size.height-140] setDuration:2000] show:iToastTypeNotice];
             
             [self performSelectorOnMainThread:@selector(alertPost:) withObject:error.localizedDescription waitUntilDone:YES];
         }
         
     }];
}


-(void)shareOnTwitter :(NSString *)text
{
    accountStore = [[ACAccountStore alloc] init];
    ACAccountType *accountType = [accountStore accountTypeWithAccountTypeIdentifier: ACAccountTypeIdentifierTwitter];
    
    [accountStore requestAccessToAccountsWithType:accountType options:nil completion:^(BOOL granted, NSError *error)
     {
         if (granted)
         {
             [applicationDelegate hide_LoadingIndicator];
             NSArray *arrayOfAccounts = [self.accountStore accountsWithAccountType:accountType];
             if ([arrayOfAccounts count] > 0) {
                 self.twitterAccount = [arrayOfAccounts lastObject];
                 
                 NSDictionary *message = @{@"status":someTweet};
                 
                 NSURL *requestURL = [NSURL
                                      URLWithString:@"http://api.twitter.com/1/statuses/update.json"];
                 
                 SLRequest *postRequest = [SLRequest
                                           requestForServiceType:SLServiceTypeTwitter
                                           requestMethod:SLRequestMethodPOST
                                           URL:requestURL parameters:message];
                 
                 
                 postRequest.account = self.twitterAccount;
                 
                 [postRequest performRequestWithHandler:^(NSData *responseData,
                                                          NSHTTPURLResponse *urlResponse, NSError *error)
                  {
                      if (responseData)
                      {
                          NSMutableDictionary  *dict = [NSJSONSerialization JSONObjectWithData:responseData options:NSJSONReadingMutableContainers error:&error];
                          
                          if (error != nil)
                          {
                              NSLog(@"%@Twitter Sharing Error %@",[error localizedDescription]);
                          }
                          
                          if (![dict objectForKey:@"id"])
                          {
                              [self performSelectorOnMainThread:@selector(alertPost:) withObject:@"Error while twitter sharing" waitUntilDone:YES];
                              NSLog(@"%@",dict);
                          }
                          else
                          {
                              [self performSelectorOnMainThread:@selector(alertPost:) withObject:@"Post successfully updated on twitter" waitUntilDone:YES];
                              NSLog(@"%@",dict);
                          }
                      }
                      else
                      {
                          if (error != nil)
                          {
                              NSLog(@"%@Twitter Sharing Error %@",[error localizedDescription]);
                          }
                          [self performSelectorOnMainThread:@selector(alertPost:) withObject:@"Error while twitter sharing" waitUntilDone:YES];
                          
                      }
                      
                  }];
             }
         }
         else
         {
             [self performSelectorOnMainThread:@selector(alertPost:) withObject:@"Please allow BrewHorn to access your Twitter account" waitUntilDone:YES];
         }
     }];
    
}

- (void)alertPost : (NSString *)object
{
    [applicationDelegate show_LoadingIndicator];
    if(fb)
    {
        [[[[iToast makeText:object] setGravity:iToastGravityTop offsetLeft:0 offsetTop:[[UIScreen mainScreen]bounds].size.height-140] setDuration:2000] show:iToastTypeNotice];
    }
    else
    {
        [[[[iToast makeText:object] setGravity:iToastGravityTop offsetLeft:0 offsetTop:[[UIScreen mainScreen]bounds].size.height-160] setDuration:2000] show:iToastTypeNotice];
    }
    
    for (UIViewController* viewController in self.navigationController.viewControllers)
    {
        
        if ([viewController isKindOfClass:[HomeViewController class]] ) {
            HomeViewController *home = (HomeViewController*)viewController;
            [self.navigationController popToViewController:home animated:YES];
        }
    }
}

- (void)showAlertWithMessage:(NSString *)object
{
    UIAlertView *alert = [[UIAlertView alloc]initWithTitle:@"Alert!" message:object delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil, nil];
    alert.tag=3;
    [alert show];
}

- (void)viewDidUnload
{
    [self setLblTop:nil];
    [self setSwitchSour:nil];
    [self setSwitchAddictive:nil];
    [self setSwitchBooziness:nil];
    [self setScrlView:nil];
    [self setTxtSour:nil];
    [self setTxtAddictive:nil];
    [self setTxtFlavour:nil];
    [self setTxtWarmth:nil];
    [self setTblSelectVal:nil];
    [self setViewTbl:nil];
    [self setBtnWarmth:nil];
    [self setBtnFlavour:nil];
    [self setBtnSour:nil];
    [self setBtnInfo:nil];
    [super viewDidUnload];
}
@end
