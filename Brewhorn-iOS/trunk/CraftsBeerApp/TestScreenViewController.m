//
//  TestScreenViewController.m
//  CraftsBeerApp
//
//  Created by Mandeep Singh on 02/08/13.
//  Copyright (c) 2013 Mandeep Singh. All rights reserved.
//

#import "TestScreenViewController.h"
#import "TestScreen2ViewController.h"
#import "HomeViewController.h"
#import "iToast.h"
#import "InfoViewController.h"
#import "ProfileBeerInfoViewController.h"

@interface TestScreenViewController ()

@end

@implementation TestScreenViewController
@synthesize tblArray,viewArray,txtAroma,txtBitter,txtSweet;
@synthesize lblAroma3,lblAroma4,lblAroma5,lblAroma6,lblAroma7;
@synthesize sliderAroma,sliderBitter,sliderSweet,strid,btnAroma,btnBitter,btnSweet;
@synthesize lblBitter3,lblBitter4,lblBitter5,lblBitter6,lblBitter7,lblSweet3,lblSweet4,lblSweet5,lblSweet6,lblSweet7,chkUser,array,scrlView,lblHeader;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}
#pragma mark Button actionsview life cycle
- (void)viewDidLoad
{
    [[[[iToast makeText:@"Tap the parameter for a description."] setGravity:iToastGravityTop offsetLeft:0 offsetTop:[[UIScreen mainScreen]bounds].size.height-140] setDuration:2000] show:iToastTypeNotice];
    
    if ([[UIScreen mainScreen]bounds].size.height==568) {
        scrlView.contentSize=CGSizeMake(260, 480);
    }
    else
    {
        scrlView.contentSize=CGSizeMake(260, 380);
    }
    arrayAromaTable=[[NSMutableArray alloc]initWithObjects:@"flora",@"citrus",@"tropical",@"fruity",@"earthy",@"banana",@"clove",@"barnyard",@"houseblanket",@"rosy",@"vegetal",@"herbal",@"leathery",@"metallic",@"piney",@"resiny",@"bubblegum",@"nutty",@"bready",@"toast",@"malty",@"noble hop", nil];
    
    arrayBitterTable=[[NSMutableArray alloc]initWithObjects:@"citrus",@"tropical",@"flora",@"perfumy",@"grapefruit",@"residual",@"aftertaste",@"catty",@"hoppy",@"herbal",@"lemony",@"piney",@"resiny",@"spicy",@"dank",@"lingering",@"bright",@"pineapple",@"mango", nil];
    
    arraySweetTable=[[NSMutableArray alloc]initWithObjects:@"light",@"moderate",@"heavy",@"cloying",@"candisugar",@"residual",@"aftertaste", nil];
    
    arrayAromaValue=[[NSMutableArray alloc]init];
    arrayBitterValue=[[NSMutableArray alloc]init];
    arraySweetValue=[[NSMutableArray alloc]init];
    
    
    
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
    
    //    if ([chkUser integerValue]==2)
    //    else
    {
        aromaMin=[[arrayDet objectAtIndex:0]integerValue]-2;
        aromaMax=[[arrayDet objectAtIndex:0]integerValue]+2;
        sliderAroma.minimumValue=aromaMin;
        sliderAroma.maximumValue=aromaMax;
        sliderAroma.value=[[arrayDet objectAtIndex:0]floatValue];
        valAroma=[[arrayDet objectAtIndex:0]integerValue];
        strAr=[arrayDet objectAtIndex:0];
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
        strSw=[arrayDet objectAtIndex:1];
        
        lblSweet3.text=[NSString stringWithFormat:@"%i",SweetMin];
        lblSweet4.text=[NSString stringWithFormat:@"%i",SweetMin+1];
        lblSweet5.text=[NSString stringWithFormat:@"%i",SweetMin+2];
        lblSweet6.text=[NSString stringWithFormat:@"%i",SweetMin+3];
        lblSweet7.text=[NSString stringWithFormat:@"%i",SweetMax];
        
        
        
        
        bitterMin=[[arrayDet objectAtIndex:2]integerValue]-2;
        bitterMax=[[arrayDet objectAtIndex:2]integerValue]+2;
        sliderBitter.minimumValue=bitterMin;
        sliderBitter.maximumValue=bitterMax;
        sliderBitter.value=[[arrayDet objectAtIndex:2]floatValue];
        valBitter=[[arrayDet objectAtIndex:2]integerValue];
        strBi=[arrayDet objectAtIndex:2];
        
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
        txtSweet.text=strSweet;
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
        [btnSweet setTitle:@"Comments" forState:UIControlStateNormal];
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

- (IBAction)slider_Bitter:(id)sender {
    chkButton=4;
    valBitter=sliderBitter.value;
    valBitter = lroundf(sliderBitter.value);
    [sliderBitter setValue:valBitter animated:YES];
    
    strBi=[NSString stringWithFormat:@"%i",(int)valBitter];
    NSLog(@"the value is %d",(int)sliderBitter.value);
}

- (IBAction)slider_Sweet:(id)sender {
    chkButton=2;
    valSweet=sliderSweet.value;
    valSweet = lroundf(sliderSweet.value);
    [sliderSweet setValue:valSweet animated:YES];
    
    strSw=[NSString stringWithFormat:@"%i",(int)valSweet];
    NSLog(@"the value is %d",(int)sliderSweet.value);
}

- (IBAction)slider_Aroma:(id)sender {
    chkButton=1;
    valAroma=sliderAroma.value;
    valAroma = lroundf(sliderAroma.value);
    [sliderAroma setValue:valAroma animated:YES];
    
    strAr=[NSString stringWithFormat:@"%i",(int)valAroma];
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
    
    TestScreen2ViewController *beerDetails=[[TestScreen2ViewController alloc]init];
    
    beerDetails.strAr=strAr;
    beerDetails.strBi=strBi;
    beerDetails.strSw=strSw;
    beerDetails.txtSw=txtSweet.text;
    beerDetails.txtBi=txtBitter.text;
    beerDetails.txtSw=txtSweet.text;
    beerDetails.strid=strid;
    beerDetails.chkUser=chkUser;
    beerDetails.array=array;
    
    [self.navigationController pushViewController:beerDetails animated:YES];
    
    txtSweet.text=@"";
    txtBitter.text=@"";
    txtAroma.text=@"";
    [arrayAromaValue removeAllObjects];
    [arrayBitterValue removeAllObjects];
    [arraySweetValue removeAllObjects];
    
    UIAlertView *alert=[[UIAlertView alloc]initWithTitle:@"" message:@"Data Saved." delegate:nil cancelButtonTitle:@"Ok" otherButtonTitles:nil];
    //  [alert show];
}
- (IBAction)btnCancel:(id)sender
{
    [self.navigationController popToViewController:[self.navigationController.viewControllers objectAtIndex:[self.navigationController.viewControllers count]-3] animated:YES];
    return;
}

- (IBAction)btn_info:(id)sender
{
    ProfileBeerInfoViewController *tasteInfo=[[ProfileBeerInfoViewController alloc]init];
    [self.navigationController presentViewController:tasteInfo animated:YES completion:nil];
}

- (IBAction)aroma:(id)sender
{
    UIAlertView *alert=[[UIAlertView alloc]initWithTitle:@"Help" message:@"Through a normal drinking approach, consider whether or not you smell anything familiar. What’s the strength of the aroma?" delegate:nil cancelButtonTitle:@"Thanks" otherButtonTitles:nil];
    [alert show];
}

- (IBAction)sweet:(id)sender
{
    UIAlertView *alert=[[UIAlertView alloc]initWithTitle:@"Help" message:@"How sweet is the beer from a general sugary or sweetener perspective?" delegate:nil cancelButtonTitle:@"Thanks" otherButtonTitles:nil];
    [alert show];
}

- (IBAction)bitter:(id)sender
{
    UIAlertView *alert=[[UIAlertView alloc]initWithTitle:@"Help" message:@"How much bitterness do you experience in the beer?" delegate:nil cancelButtonTitle:@"Thanks" otherButtonTitles:nil];
    [alert show];
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
    [self setBtnSweet:nil];
    [self setBtnBitter:nil];
    [super viewDidUnload];
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
    else  {
        return [arraySweetTable count];
        
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
        else  {
            [arraySweetValue addObject:[arraySweetTable objectAtIndex:indexPath.row]];
        }
    }
    NSLog(@"the array val is %@",arrayAromaValue);
}

@end
