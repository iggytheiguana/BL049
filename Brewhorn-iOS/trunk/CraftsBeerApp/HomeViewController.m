//
//  HomeViewController.m
//  CraftsBeerApp
//
//  Created by Mandeep Singh on 11/06/13.
//  Copyright (c) 2013 Mandeep Singh. All rights reserved.
//

#import "HomeViewController.h"
#import "addBeerViewController.h"
#import "ShareScreenViewController.h"
#import "userProfileViewController.h"
#import "ViewImageViewController.h"
#import "SearchInfoViewController.h"
#import "JSON.h"
#import "Flurry.h"


@interface HomeViewController ()

@end

@implementation HomeViewController
@synthesize tblBeer,txtBeerName,btnAddNew,createNewBeer,imgSearch,btnSearch,imgTblBeer,lblHeader,lblHints;

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
    lblHints.text=[NSString stringWithFormat:@"Search Hints: \n 1. Search unique beer names by their specific beer name. \n 2. Search generic named beers like \"Pale Ale\" by brewery name."];
    tblBeer.hidden=YES;
    createNewBeer.hidden=YES;
    //  btnAddNew.hidden=YES;
    arrayBeer=[[NSMutableArray alloc]init];
    // tblBeer.hidden=YES;
    // imgTblBeer.hidden=YES;
    txtBeerName.text=@"";
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
}
-(void)viewWillAppear:(BOOL)animated
{
    tblBeer.hidden=YES;
    lblHints.hidden=NO;
    [txtBeerName resignFirstResponder];
    txtBeerName.text=@"";
}
-(void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event
{
    [txtBeerName resignFirstResponder];
}
- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}
#pragma mark - uitextfiel delegate method
- (BOOL)textFieldShouldReturn:(UITextField *)textField
{
    [self earchBeer:self];
    [textField resignFirstResponder];
    return YES;
}

-(void)emptyTable
{
    if ([arrayBeer count]>0) {
        [arrayBeer removeAllObjects];
        tblBeer.hidden=YES;
        lblHints.hidden=NO;
        [tblBeer reloadData];
    }
}
#pragma mark - Button actions
- (IBAction)earchBeer:(id)sender
{
    [Flurry logEvent:@"Search_Beer"];
    NSString *strValue=txtBeerName.text;
    [strValue stringByReplacingOccurrencesOfString:@" " withString:@""];
    
    if (strValue.length==0) {
        UIAlertView *alert=[[UIAlertView alloc]initWithTitle:@"" message:@"Please enter the beer name first." delegate:nil cancelButtonTitle:@"Ok" otherButtonTitles:nil];
        if ([arrayBeer count]>0) {
            [arrayBeer removeAllObjects];
            [tblBeer reloadData];
        }
        tblBeer.hidden=YES;
        lblHints.hidden=NO;
        
        [alert show];
        alert=nil;
    }
    else
    {
        {
            [applicationDelegate checkInternetConnection];
            if([applicationDelegate internetWorking] ==0)
            {
                [applicationDelegate show_LoadingIndicator];
                NSString *beername = txtBeerName.text;
                NSString *strUserid=[[NSUserDefaults standardUserDefaults]valueForKey:@"user_id"];
                NSString * xmlString =
                [NSString stringWithFormat:@"<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?><searchBeer><beerName><![CDATA[%@]]></beerName><userId><![CDATA[%@]]></userId></searchBeer>",beername,strUserid];
                if ([arrayBeer count]>0) {
                    [arrayBeer removeAllObjects];
                }
                NSLog(@"the xmlstring is =%@",xmlString);
                NSURL * serviceUrl = [NSURL URLWithString:kBrreUrl];
                NSMutableURLRequest *theRequest = [NSMutableURLRequest requestWithURL:serviceUrl
                                                                          cachePolicy:NSURLRequestUseProtocolCachePolicy timeoutInterval:120.0];
                NSDictionary *headerFieldsDict = [NSDictionary dictionaryWithObjectsAndKeys:@"text/xml; charset=ISO-8859-1", @"Content-Type", nil];
                [theRequest setAllHTTPHeaderFields:headerFieldsDict];
                [theRequest setHTTPMethod:@"POST"];
                [theRequest setHTTPBody:[xmlString dataUsingEncoding:NSISOLatin1StringEncoding]];
                NSHTTPURLResponse* urlResponse = nil;
                NSError *error = [[NSError alloc] init];
                NSData *responseData1 = [NSURLConnection sendSynchronousRequest:theRequest returningResponse:&urlResponse error:&error];
                NSString* serRslt= [[NSString alloc] initWithData:responseData1 encoding:NSUTF8StringEncoding];
                NSLog(@"The result is = %@",serRslt);
                NSDictionary *dict = [serRslt JSONValue];
                NSLog(@"the dictionary = %@",dict);
                arrayBeer=[dict objectForKey:@"searchBeer"];
                if ([arrayBeer count]>0)
                {
                    tblBeer.hidden=NO;
                    lblHints.hidden=YES;
                    
                    [applicationDelegate hide_LoadingIndicator];
                    tblBeer.hidden=NO;
                    imgTblBeer.hidden=NO;
                    createNewBeer.hidden=YES;
                    imgSearch.hidden=NO;
                    btnSearch.hidden=NO;
                    txtBeerName.hidden=NO;
                    [tblBeer reloadData];
                }
                else{
                    [arrayBeer removeAllObjects];
                    [tblBeer reloadData];
                    tblBeer.hidden=YES;
                    lblHints.hidden=NO;
                    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Alert!"
                                                                    message:@"Would you like to add a/the beer."
                                                                   delegate:self
                                                          cancelButtonTitle:@"No"
                                                          otherButtonTitles:@"Yes", nil];
                    [alert show];
                    createNewBeer.hidden=NO;
                    btnAddNew.hidden=NO;
                    txtBeerName.text=@"";
                }
                serRslt = nil;
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
    [txtBeerName resignFirstResponder];
}
- (IBAction)ddeeerw:(id)sender
{
    addBeerViewController *addBeer=[[addBeerViewController alloc]init];
    addBeer.strEditMode=@"1";
    [self.navigationController pushViewController:addBeer animated:YES];
    [self emptyTable];
    [txtBeerName resignFirstResponder];
}

- (IBAction)btnShare:(id)sender
{
    ShareScreenViewController *share=[[ShareScreenViewController alloc]init];
    share.chkSkip=2;
    [self.navigationController pushViewController:share animated:YES];
    [self emptyTable];
}

- (IBAction)btnEditUserProfile:(id)sender
{
    userProfileViewController *profile=[[userProfileViewController alloc]init];
    [self.navigationController pushViewController:profile animated:YES];
    [self emptyTable];
    return;
    UIAlertView *alert=[[UIAlertView alloc]initWithTitle:@"" message:@"Functionality pending" delegate:nil cancelButtonTitle:@"Ok" otherButtonTitles:nil];
    [alert show];
}

- (IBAction)info:(id)sender
{
    SearchInfoViewController *search=[[SearchInfoViewController alloc]init];
    [self.navigationController presentViewController:search animated:YES completion:nil];
    [self emptyTable];
}

#pragma mark - View Methods of tableview delegate
// Customize the number of sections in the table view.

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    
    return [arrayBeer count];
}
// Customize the appearance of table view cells.
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    NSString * cellIdentifier =[NSString stringWithFormat:@"Cell %d",indexPath.row];
    
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:cellIdentifier];
    if (cell == nil) {
        
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:cellIdentifier];
    }
    if ([cell.contentView subviews]){
        for (UIView *subview in [cell.contentView subviews]) {
            [subview removeFromSuperview];
        }
    }
    
    
    cell.selectionStyle=UITableViewCellSelectionStyleNone;
    UILabel *lblBeerName=[[UILabel alloc]initWithFrame:CGRectMake(5, 4, 220, 18)];
    lblBeerName.text=[[arrayBeer objectAtIndex:indexPath.row]valueForKey:@"beerName"];
    lblBeerName.font=[UIFont systemFontOfSize:15];
    lblBeerName.backgroundColor=[UIColor clearColor];
    lblBeerName.textColor=[UIColor whiteColor];
    [cell.contentView addSubview:lblBeerName];
    
    UILabel *lblBrName=[[UILabel alloc]initWithFrame:CGRectMake(5, 24, 220, 18)];
    lblBrName.text=[[arrayBeer objectAtIndex:indexPath.row]valueForKey:@"brewery"];
    lblBrName.font=[UIFont systemFontOfSize:15];
    [cell.contentView addSubview:lblBrName];
    lblBrName.backgroundColor=[UIColor clearColor];
    lblBrName.textColor=[UIColor whiteColor];
    lblBrName.adjustsFontSizeToFitWidth=YES;
    lblBeerName.adjustsFontSizeToFitWidth=YES;
    cell.backgroundColor=[UIColor clearColor];
    return cell;
    
}
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    ViewImageViewController *viewImg=[[ViewImageViewController alloc]init];
    viewImg.strChkBeer=@"2";
    
    NSMutableArray *array=[arrayBeer objectAtIndex:indexPath.row];
    viewImg.array=[arrayBeer objectAtIndex:indexPath.row];
    if ([[array valueForKey:@"aroma"] integerValue]==0 & [[array valueForKey:@"bitter"]integerValue]==0 & [[array valueForKey:@"malt"] integerValue]==0 & [[array valueForKey:@"mouthFeel"]integerValue]==0 & [[array valueForKey:@"sweet"]integerValue]==0) {
        viewImg.strChkBeer=@"0";
    }
    
    viewImg.strimg=[NSString stringWithFormat:@"%@",[[arrayBeer objectAtIndex:indexPath.row]valueForKey:@"beerBottle"]];
    [self.navigationController pushViewController:viewImg animated:YES];
    [self emptyTable];
}
#pragma mark- AlertView delegate methods
- (void)alertView:(UIAlertView *)alertView didDismissWithButtonIndex:(NSInteger)buttonIndex
{
    if (buttonIndex==1) {
        [self ddeeerw:self];
    }
    else
    {
    }
}
- (void)viewDidUnload {
    [self setImgTblBeer:nil];
    [self setBtnSearch:nil];
    [self setImgSearch:nil];
    [super viewDidUnload];
}
@end
