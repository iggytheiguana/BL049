//
//  HistoryViewController.m
//  CraftsBeerApp
//
//  Created by Mandeep Singh on 15/10/13.
//  Copyright (c) 2013 Mandeep Singh. All rights reserved.
//

#import "HistoryViewController.h"
#import "JSON.h"

@interface HistoryViewController ()

@end

@implementation HistoryViewController

@synthesize tblHistory;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}
#pragma mark- View life cycle

- (void)viewDidLoad
{
    tblHistory.hidden=YES;
    arrayHistory=[[NSMutableArray alloc]init];
    
    [self getDetails];
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
}
- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}
/// Method to get history details
-(void)getDetails
{
    [applicationDelegate checkInternetConnection];
    if([applicationDelegate internetWorking] ==0)
    {
        [applicationDelegate show_LoadingIndicator];
        NSString *strUserid=[[NSUserDefaults standardUserDefaults]valueForKey:@"user_id"];
        NSString * xmlString =[NSString stringWithFormat:@"<?xml version=\"1.0\" encoding=\"UTF-8\" ?><beerHistory><userId><![CDATA[%@]]></userId></beerHistory>",strUserid];
        if ([arrayHistory count]>0) {
            [arrayHistory removeAllObjects];
        }
        NSLog(@"the xmlstring is =%@",xmlString);
        NSURL * serviceUrl = [NSURL URLWithString:kHistory];
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
        arrayHistory=[dict objectForKey:@"beerHistory"];
        if ([[dict objectForKey:@"beerHistory"] isKindOfClass:[NSString class]]) {
            if ([[dict objectForKey:@"beerHistory"] isEqualToString:@"-1"]) {
                UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Alert!"
                                                                message:@"In-valid User"
                                                               delegate:self
                                                      cancelButtonTitle:@"Ok"
                                                      otherButtonTitles: nil];
                [alert show];
            }
            else if ([[dict objectForKey:@"beerHistory"] isEqualToString:@"-2"])
            {
                UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Alert!"
                                                                message:@"Server Error"
                                                               delegate:self
                                                      cancelButtonTitle:@"Ok"
                                                      otherButtonTitles: nil];
                [alert show];
            }
        }
        else
        {
            if ([arrayHistory count]>0)
            {
                tblHistory.hidden=NO;
                [applicationDelegate hide_LoadingIndicator];
                // btnAddNew.hidden=YES;
                [tblHistory reloadData];
            }
            else{
                // if ([arrayBeer count]>0) {
                //  }
                UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Alert!"
                                                                message:@"No history."
                                                               delegate:self
                                                      cancelButtonTitle:@"Ok"
                                                      otherButtonTitles: nil];
                [alert show];
            }
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
#pragma mark- Button actions
- (IBAction)btnBack:(id)sender
{
    [self.navigationController popViewControllerAnimated:YES];
}

#pragma mark - View Methods of tableview delegate
// Customize the number of sections in the table view.

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    
    return [arrayHistory count];
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
    lblBeerName.text=[[arrayHistory objectAtIndex:indexPath.row]valueForKey:@"beerName"];
    lblBeerName.font=[UIFont systemFontOfSize:15];
    lblBeerName.backgroundColor=[UIColor clearColor];
    lblBeerName.textColor=[UIColor whiteColor];
    [cell.contentView addSubview:lblBeerName];
    
    UILabel *lblBrName=[[UILabel alloc]initWithFrame:CGRectMake(5, 24, 220, 18)];
    lblBrName.text=[[arrayHistory objectAtIndex:indexPath.row]valueForKey:@"brewery"];
    lblBrName.font=[UIFont systemFontOfSize:15];
    [cell.contentView addSubview:lblBrName];
    lblBrName.backgroundColor=[UIColor clearColor];
    lblBrName.textColor=[UIColor whiteColor];
    lblBrName.adjustsFontSizeToFitWidth=YES;
    lblBeerName.adjustsFontSizeToFitWidth=YES;
    cell.backgroundColor=[UIColor clearColor];
    return cell;
    
}

# pragma mark alertview delegate
- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex
{
    [self.navigationController popViewControllerAnimated:YES];
}
@end
