
#import "EditUserProfileViewController.h"
#import "ChangePasswordViewController.h"
#import "JSON.h"

@interface EditUserProfileViewController ()

@end

@implementation EditUserProfileViewController

@synthesize txtEmail = _txtEmail;
@synthesize txtFirstName = _txtFirstName;
@synthesize txtUserName = _txtUserName;
@synthesize tztLastName = _tztLastName;
@synthesize txtZip = _txtZip;
@synthesize PickerSelectBeer = _PickerSelectBeer;
@synthesize btnSelectBeer = _btnSelectBeer;
@synthesize txt,viewTextFields,viewBackground,toolBar;
@synthesize strBeerExperience,strEmail,strFirstName,strLastName,strUserName,strZip,lblHeader,swchSharingFb,swchSharingTwitter;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}
#pragma mark Life cycle

- (void)viewDidLoad
{
    viewBackground.hidden=YES;
    arraySelectBeer=[[NSMutableArray alloc]init];
    [arraySelectBeer addObject:@"Drinker's Level"];
    [arraySelectBeer addObject:@"Macro Beer Drinker"];
    [arraySelectBeer addObject:@"Thinking about craft"];
    [arraySelectBeer addObject:@"New craft beer drinker"];
    [arraySelectBeer addObject:@"Casual Craft Beer Drinker"];
    [arraySelectBeer addObject:@"Moderate Beer Geek"];
    [arraySelectBeer addObject:@"Serious Beer Geek"];
    _PickerSelectBeer.hidden=YES;
    toolBar.hidden=YES;
    _txtZip.text=strZip;
    _txtUserName.text=strUserName;
    _txtFirstName.text=strFirstName;
    _txtEmail.text=strEmail;
    _tztLastName.text=strLastName;
    txt=strBeerExperience;
    [_btnSelectBeer setTitle:txt forState:UIControlStateNormal];
    
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    
    if ([[NSUserDefaults standardUserDefaults]boolForKey:@"AutomateSharingFb"])
        [swchSharingFb setOn:YES];
    else
        [swchSharingFb setOn:NO];
    
    if([[NSUserDefaults standardUserDefaults]boolForKey:@"AutomateSharingTwitter"])
        [swchSharingTwitter setOn:YES];
    else
        [swchSharingTwitter setOn:NO];
    
    [_btnSelectBeer setTitle:strBeerExperience forState:UIControlStateNormal];
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
    if (textField==_txtEmail) {
        self.view.frame=CGRectMake(0, -100, 320, [[UIScreen mainScreen]bounds].size.height);
    }
    if (textField==_txtZip) {
        self.view.frame=CGRectMake(0, -150, 320, [[UIScreen mainScreen]bounds].size.height);
    }
}

#pragma mark- touch action

-(void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event
{
    [self resignKeyboard];
}

#pragma mark- remove keyboard from screen
-(void)resignKeyboard
{
    [_txtEmail resignFirstResponder];
    [_txtFirstName resignFirstResponder];
    [_txtUserName resignFirstResponder];
    [_txtZip resignFirstResponder];
    [_tztLastName resignFirstResponder];
    if ([[UIScreen mainScreen]bounds].size.height==568) {
        self.view.frame=CGRectMake(0, 0, 320, 568);
        viewTextFields.frame=CGRectMake(35, 97, 250, 370);
    }
    else
    {
        self.view.frame=CGRectMake(0, 0, 320, 480);
        viewTextFields.frame=CGRectMake(35, 81, 250, 311);
    }
    viewBackground.hidden=YES;
}

#pragma mark- Button actions

- (IBAction)btnChangePassword:(id)sender
{
    ChangePasswordViewController *password=[[ChangePasswordViewController alloc]init];
    [self.navigationController pushViewController:password animated:YES];
}
- (IBAction)btnBack:(id)sender
{
    [self resignKeyboard];
    [self.navigationController popViewControllerAnimated:YES];
}

- (IBAction)btnSelectBeer:(id)sender
{
    strPickerOldVal=[NSString stringWithFormat:@"%@",_btnSelectBeer.titleLabel.text];
    viewBackground.hidden=NO;
    [self resignKeyboard];
    if ([[UIScreen mainScreen]bounds].size.height==568) {
        self.view.frame=CGRectMake(0, -110, 320, [[UIScreen mainScreen]bounds].size.height+110);
        viewTextFields.frame=CGRectMake(35, 97, 250, 370);
    }
    else
    {
        self.view.frame=CGRectMake(0, -110, 320, [[UIScreen mainScreen]bounds].size.height+110);
        viewTextFields.frame=CGRectMake(35, 80, 250, 300);
    }
    
    _PickerSelectBeer.hidden=NO;
    toolBar.hidden=NO;
}

- (IBAction)btnSubmit:(id)sender
{
    //to check the range of emailid
    NSString *emailReg = @"[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";
	NSPredicate *emailTest = [NSPredicate predicateWithFormat:@"SELF MATCHES %@", emailReg];
    
    //  NSString *stringToTest  = txtPassword.text;
    
    //characterset for uppercase letters
    //    NSCharacterSet *upperCaseSet = [NSCharacterSet uppercaseLetterCharacterSet];
    //characterset for numeric letters
    //   NSCharacterSet *numbSet = [NSCharacterSet characterSetWithCharactersInString:@"0123456789"];
    
    //  NSRange upperCaseRange;
    //    upperCaseRange = [stringToTest rangeOfCharacterFromSet: upperCaseSet];//Compare range of Uppercase letters.
    
    //   NSRange numberLtr;
    //   numberLtr = [stringToTest rangeOfCharacterFromSet:numbSet];//Compare range of Numric letters.
    [_txtEmail.text stringByReplacingOccurrencesOfString:@" " withString:@""];
    
    if(_txtFirstName.text.length ==0)
    {
        
        UIAlertView *alert=[[UIAlertView alloc] initWithTitle:@"Sign Up" message:@"Enter first name" delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
        [alert show];
        
    }
    
    else if(_tztLastName.text.length ==0)
    {
        
        UIAlertView *alert=[[UIAlertView alloc] initWithTitle:@"Sign Up" message:@"Enter last name" delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
        [alert show];
        
    }
    
    else if(_txtUserName.text.length ==0)
    {
        
        UIAlertView *alert=[[UIAlertView alloc] initWithTitle:@"Sign Up" message:@"Enter user name" delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
        [alert show];
        
    }
    else if(_txtEmail.text.length ==0)
    {
        
        UIAlertView *alert=[[UIAlertView alloc] initWithTitle:@"Sign Up" message:@"Enter email" delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
        [alert show];
        
    }
    
    else if ([emailTest evaluateWithObject:_txtEmail.text] != YES)
	{
        
        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Sign Up"
                                                        message:@"Enter valid email"
                                                       delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
        [alert show];
	}
    
    //    else if (upperCaseRange.location == NSNotFound)
    //    {
    //
    //        UIAlertView *alert=[[UIAlertView alloc] initWithTitle:@"Registration" message:@"Please enter at least one number and one highercase letter." delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil];
    //        [alert show];
    //        [alert release];
    //
    //    }
    //    else if (numberLtr.location == NSNotFound)
    //    {
    //
    //        UIAlertView *alert=[[UIAlertView alloc] initWithTitle:@"Registration" message:@"Please enter at least one number and one highercase letter." delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil];
    //        [alert show];
    //        [alert release];
    //
    //    }
    
    
    //    else if(txt.length ==0)
    //    {
    //
    //        UIAlertView *alert=[[UIAlertView alloc] initWithTitle:@"Sign Up" message:@"Enter Drinker Level" delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
    //        [alert show];
    //
    //    }
    
    else
    {
        [applicationDelegate checkInternetConnection];
        if([applicationDelegate internetWorking] ==0)
        {
            [applicationDelegate show_LoadingIndicator];
            NSString *userName = _txtFirstName.text;
            NSString *email = _txtEmail.text;
            NSString *strid=[[NSUserDefaults standardUserDefaults]valueForKey:@"user_id"];
            NSString * xmlString =[NSString stringWithFormat:EditUserProfile_XML,strid,userName,_tztLastName.text,_txtZip.text,@"",email,_txtUserName.text];
            
            NSLog(@"the xmlstring is =%@",xmlString);
            
            NSURL * serviceUrl = [NSURL URLWithString:kEditUserProfile];
            
            
            NSMutableURLRequest *theRequest = [NSMutableURLRequest requestWithURL:serviceUrl
                                                                      cachePolicy:NSURLRequestUseProtocolCachePolicy timeoutInterval:120.0];
            
            NSDictionary *headerFieldsDict = [NSDictionary dictionaryWithObjectsAndKeys:@"text/xml; charset=utf-8", @"Content-Type", nil];
            [theRequest setAllHTTPHeaderFields:headerFieldsDict];
            [theRequest setHTTPMethod:@"POST"];
            [theRequest setHTTPBody:[xmlString dataUsingEncoding:NSUTF8StringEncoding]];
            NSHTTPURLResponse* urlResponse = nil;
            NSError *error = [[NSError alloc] init];
            NSData *responseData1 = [NSURLConnection sendSynchronousRequest:theRequest returningResponse:&urlResponse error:&error];
            NSString* serRslt= [[NSString alloc] initWithData:responseData1 encoding:NSUTF8StringEncoding];
            NSLog(@"The result is = %@",serRslt);
            NSMutableDictionary*dictReg = [serRslt JSONValue];
            NSLog(@"the result in dict = %@",dictReg);
            NSString *strVal=[NSString stringWithFormat:@"%@",[dictReg objectForKey:@"editUserProfile"]];
            if ([strVal integerValue]>0)
            {
                [applicationDelegate hide_LoadingIndicator];
                
                if([swchSharingFb isOn])
                {
                    [[NSUserDefaults standardUserDefaults]setBool:YES forKey:@"AutomateSharingFb"];
                    [[NSUserDefaults standardUserDefaults]synchronize];
                }
                else
                {
                    [[NSUserDefaults standardUserDefaults]setBool:NO forKey:@"AutomateSharingFb"];
                    [[NSUserDefaults standardUserDefaults]synchronize];
                }
                if([swchSharingTwitter isOn])
                {
                    [[NSUserDefaults standardUserDefaults]setBool:YES forKey:@"AutomateSharingTwitter"];
                    [[NSUserDefaults standardUserDefaults]synchronize];
                }
                else
                {
                    [[NSUserDefaults standardUserDefaults]setBool:NO forKey:@"AutomateSharingTwitter"];
                    [[NSUserDefaults standardUserDefaults]synchronize];
                }
                [applicationDelegate show_LoadingIndicator];
                [self.navigationController popViewControllerAnimated:YES];
                return;
                
            }
            else  if ([strVal integerValue]==-3)
            {
                [applicationDelegate hide_LoadingIndicator];
                UIAlertView *alert=[[UIAlertView alloc] initWithTitle:@"" message:@"Username already exists." delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
                [alert show];
                
            }
            else  if ([strVal integerValue]==-1)
            {
                [applicationDelegate hide_LoadingIndicator];
                UIAlertView *alert=[[UIAlertView alloc] initWithTitle:@"" message:@"User doesn't exists." delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
                [alert show];
                
            }
            else if ([strVal integerValue]==-2)
            {
                [applicationDelegate hide_LoadingIndicator];
                UIAlertView *alert=[[UIAlertView alloc] initWithTitle:@"" message:@"Server error." delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
                [alert show];
                
            }
            else if ([strVal integerValue]==-4)
            {
                [applicationDelegate hide_LoadingIndicator];
                UIAlertView *alert=[[UIAlertView alloc] initWithTitle:@"" message:@"Email address exists." delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
                [alert show];
            }
            serRslt = nil;
        }
        else
        {
            UIAlertView *alertViewNetwrk = [[UIAlertView alloc] initWithTitle:@"Alert!"
                                                                      message:@"Please connect to internet."
                                                                     delegate:nil
                                                            cancelButtonTitle:@"OK"
                                                            otherButtonTitles:nil, nil];
            [alertViewNetwrk show];
            alertViewNetwrk = nil;
        }
    }
}

- (IBAction)btnResign:(id)sender
{
    [self resignKeyboard];
}

- (IBAction)CancelBtn:(id)sender
{
    [_btnSelectBeer setTitle:strPickerOldVal forState:UIControlStateNormal];
    _PickerSelectBeer.hidden=YES;
    toolBar.hidden=YES;
    [self resignKeyboard];
}

- (IBAction)btnDone:(id)sender
{
    if (txt.length==0) {
        txt=[arraySelectBeer objectAtIndex:0];
        [_btnSelectBeer setTitle:txt forState:UIControlStateNormal];
    }
    
    _PickerSelectBeer.hidden=YES;
    toolBar.hidden=YES;
    [self resignKeyboard];
}

//- (IBAction)swtchSharing:(id)sender
//{
//    if (swchSharing.isOn==NO) {
//        //  [swchSharing setOn:NO];
//        [[NSUserDefaults standardUserDefaults]setInteger:2 forKey:@"AutomateSharing"];
//        [[NSUserDefaults standardUserDefaults]synchronize];
//    }
//    else
//    {
//        // [swchSharing setOn:YES];
//        [[NSUserDefaults standardUserDefaults]setInteger:1 forKey:@"AutomateSharing"];
//        [[NSUserDefaults standardUserDefaults]synchronize];
//    }
//}

- (IBAction)swchSharingFb:(id)sender
{
    UISwitch *switchShare = (UISwitch *)sender;
    
    if ([switchShare isOn])
    {
        NSLog(@"now off");
        //[swchSharingFb setOn:NO];
//        [[NSUserDefaults standardUserDefaults]setBool:YES forKey:@"AutomateSharingFb"];
//        [[NSUserDefaults standardUserDefaults]synchronize];
    }
    else
    {
        NSLog(@"NOw on");
        //        [swchSharingFb setOn:YES];
//        [[NSUserDefaults standardUserDefaults]setBool:NO forKey:@"AutomateSharingFb"];
//        [[NSUserDefaults standardUserDefaults]synchronize];
    }
}

- (IBAction)swchSharingTwitter:(id)sender
{
    UISwitch *switchShare = (UISwitch *)sender;
    
    if([switchShare isOn])
    {
        NSLog(@"now off");
        //        [swchSharingTwitter setOn:NO];
//        [[NSUserDefaults standardUserDefaults]setBool:YES forKey:@"AutomateSharingTwitter"];
//        [[NSUserDefaults standardUserDefaults]synchronize];
    }
    else
    {
        NSLog(@"NOw on");
        //        [swchSharingTwitter setOn:YES];
//        [[NSUserDefaults standardUserDefaults]setBool:NO forKey:@"AutomateSharingTwitter"];
//        [[NSUserDefaults standardUserDefaults]synchronize];
    }
}



#pragma mark Server connection methods

-(void) connection:(NSURLConnection *)connection didReceiveData:(NSData *)data
{
    [recieveData appendData:data];
}

-(void) connection:(NSURLConnection *)connection didReceiveResponse:(NSURLResponse *)response{
    
    [recieveData setLength:0];
    
}

-(void) connectionDidFinishLoading:(NSURLConnection *)connection
{
    
    NSString *serRslt=[[NSString alloc] initWithData:recieveData encoding:NSUTF8StringEncoding];
    
    NSLog(@"The result is = %@",serRslt);
    
    NSMutableDictionary*dictReg = [serRslt JSONValue];
    NSLog(@"the result in dict = %@",dictReg);
    
    NSMutableDictionary *dict = [dictReg valueForKey:@"userRegistration"];
    NSLog(@"the final result in dict = %@",dict);
    
    if ([[dict valueForKey:@"status"] integerValue]>0)
    {
        [applicationDelegate hide_LoadingIndicator];
        
        UIAlertView *alert=[[UIAlertView alloc] initWithTitle:@"Welcome" message:@"Sucessfully Registered" delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
        [alert show];
        return;
        
    }
    else  if ([[dict valueForKey:@"status"] integerValue]==-3)
    {
        [applicationDelegate hide_LoadingIndicator];
        UIAlertView *alert=[[UIAlertView alloc] initWithTitle:@"Sign Up" message:@"Username already exist." delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
        [alert show];
        
    }
    else  if ([[dict valueForKey:@"status"] integerValue]==-1)
    {
        [applicationDelegate hide_LoadingIndicator];
        UIAlertView *alert=[[UIAlertView alloc] initWithTitle:@"Sign Up" message:@"Email already exists" delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
        [alert show];
        
    }
    else if ([[dict valueForKey:@"status"] integerValue]==-2)
    {
        [applicationDelegate hide_LoadingIndicator];
        UIAlertView *alert=[[UIAlertView alloc] initWithTitle:@"Sign Up" message:@"Server error." delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
        [alert show];
        
    }
    
    serRslt = nil;
    
}


- (void)connection:(NSURLConnection *)connection didFailWithError:(NSError *)error
{
    [applicationDelegate hide_LoadingIndicator];
    UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:@"Alert!"
                                                        message:@"Please try again later."
                                                       delegate:nil
                                              cancelButtonTitle:@"OK"
                                              otherButtonTitles:nil, nil];
    [alertView show];
    alertView = nil;
}

#pragma mark - Pickerview delegate

- (NSInteger)numberOfComponentsInPickerView:(UIPickerView *)pickerView;
{
    return 1;
}

- (NSInteger)pickerView:(UIPickerView *)pickerView numberOfRowsInComponent:(NSInteger)component;
{
    return [arraySelectBeer count];
    return YES;
}

- (NSString *)pickerView:(UIPickerView *)pickerView titleForRow:(NSInteger)row forComponent:(NSInteger)component;
{
    return [arraySelectBeer objectAtIndex:row];
    // return YES;
}

-(void)pickerView:(UIPickerView *)pickerView didSelectRow:(NSInteger)row
      inComponent:(NSInteger)component
{
    txt=[arraySelectBeer objectAtIndex:row];
    [_btnSelectBeer setTitle:txt forState:UIControlStateNormal];
}

- (void)selectRow:(NSInteger)row inComponent:(NSInteger)component animated:(BOOL)animated
{
    [_PickerSelectBeer selectRow:2 inComponent:0 animated:YES];
}

- (CGFloat)pickerView:(UIPickerView *)pickerView widthForComponent:(NSInteger)component
{
    return 300;
}

- (CGFloat)pickerView:(UIPickerView *)pickerView rowHeightForComponent:(NSInteger)component
{
    return 50;
}

#pragma mark- Memory management actions

- (void)viewDidUnload
{
//    [self setSwchSharing:nil];
    [self setSwchSharingFb:nil];
    [self setSwchSharingTwitter:nil];
    [super viewDidUnload];
}

@end
