
#import "RegisterViewController.h"
#import "BeerDetails1ViewController.h"
#import "TestScreenViewController.h"
#import "RegisterInfoViewController.h"
#import "Termd&ConditionsViewController.h"
#import "JSON.h"

@interface RegisterViewController ()

@end

@implementation RegisterViewController
@synthesize txtConfirmPassword= _txtConfirmPassword;
@synthesize txtEmail = _txtEmail;
@synthesize txtFirstName = _txtFirstName;
@synthesize txtPassword = _txtPassword;
@synthesize txtUserName = _txtUserName;
@synthesize tztLastName = _tztLastName;
@synthesize txtZip = _txtZip;
@synthesize PickerSelectBeer = _PickerSelectBeer;
@synthesize btnSelectBeer = _btnSelectBeer;
@synthesize txt,viewTextFields,viewBackground,toolBar,lblHeader,btnChkMark,swchSharingFb,swchSharingTwitter;

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
    btnTerms=2;
    viewBackground.hidden=YES;
    arraySelectBeer=[[NSMutableArray alloc]init];
    [arraySelectBeer addObject:@"Drinker's Level"];
    [arraySelectBeer addObject:@"Macro Beer Drinker"];
    [arraySelectBeer addObject:@"Thinking about Craft"];
    [arraySelectBeer addObject:@"New Craft Beer Drinker"];
    [arraySelectBeer addObject:@"Casual Craft Beer Drinker"];
    [arraySelectBeer addObject:@"Moderate Beer Geek"];
    [arraySelectBeer addObject:@"Serious Beer Geek"];
    
    NSLog(@"the array ius %@",arraySelectBeer);
    _PickerSelectBeer.hidden=YES;
    toolBar.hidden=YES;
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
}

-(void)viewWillAppear:(BOOL)animated
{
    [_btnSelectBeer setTitle:@"Experience Level" forState:UIControlStateNormal];
    
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
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
            self.view.frame=CGRectMake(0, 0, 320, 546);
            
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
    if (textField==_txtPassword) {
        self.view.frame=CGRectMake(0, -50, 320, [[UIScreen mainScreen]bounds].size.height);
    }
    if (textField==_txtConfirmPassword) {
        self.view.frame=CGRectMake(0, -100, 320, [[UIScreen mainScreen]bounds].size.height);
    }
    if (textField==_txtEmail) {
        self.view.frame=CGRectMake(0, -150, 320, [[UIScreen mainScreen]bounds].size.height);
    }
    if (textField==_txtZip) {
        self.view.frame=CGRectMake(0, -200, 320, [[UIScreen mainScreen]bounds].size.height);
    }
}

#pragma mark- touch action

-(void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event
{
    [self resignKeyboard];
}

#pragma mark- Button actions

-(void)resignKeyboard
{
    [_txtConfirmPassword resignFirstResponder];
    [_txtEmail resignFirstResponder];
    [_txtFirstName resignFirstResponder];
    [_txtPassword resignFirstResponder];
    [_txtUserName resignFirstResponder];
    [_txtZip resignFirstResponder];
    
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
    [_txtPassword.text stringByReplacingOccurrencesOfString:@" " withString:@""];
    [_txtConfirmPassword.text stringByReplacingOccurrencesOfString:@" " withString:@""];
    
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
    else if(_txtPassword.text.length ==0)
    {
        
        UIAlertView *alert=[[UIAlertView alloc] initWithTitle:@"Sign Up" message:@"Enter Password" delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
        [alert show];
        
    }
    else if(_txtConfirmPassword.text.length ==0)
    {
        
        UIAlertView *alert=[[UIAlertView alloc] initWithTitle:@"Sign Up" message:@"Enter Password" delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
        [alert show];
        
    }
    
    else if (_txtPassword.text.length<8)
    {
        UIAlertView *alert=[[UIAlertView alloc] initWithTitle:@"Sign Up" message:@"Password length should be of minimum 8" delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
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
    
    
    else  if(![_txtPassword.text isEqualToString:_txtConfirmPassword.text])
    {
        
        UIAlertView *alert=[[UIAlertView alloc] initWithTitle:@"Sign Up" message:@"Password and Confirm password does not match" delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
        [alert show];
        
        
    }
    //    else if(txt.length ==0)
    //    {
    //
    //        UIAlertView *alert=[[UIAlertView alloc] initWithTitle:@"Sign Up" message:@"Enter Drinker Level" delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
    //        [alert show];
    //
    //    }
    else if (btnTerms==2)
    {
        UIAlertView *alert=[[UIAlertView alloc] initWithTitle:@"Sign Up" message:@"Accept Terms & Conditions to proceed." delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
        [alert show];
    }
    else
    {
        [applicationDelegate checkInternetConnection];
        if([applicationDelegate internetWorking] ==0)
        {
            
            [applicationDelegate show_LoadingIndicator];
            
            NSString *userName = _txtFirstName.text;
            NSString *email = _txtEmail.text;
            NSString *password = _txtPassword.text;
            
            
            NSString * xmlString =[NSString stringWithFormat:SIGNUP_XML,userName,_tztLastName.text,_txtUserName.text,email,password,_txtZip.text,@""];
            
            NSLog(@"the xmlstring is =%@",xmlString);
            
            
            
            NSURL * serviceUrl = [NSURL URLWithString:kRegServerUrl];
            
            
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
            
            NSString *strVal=[NSString stringWithFormat:@"%@",[dictReg objectForKey:@"userRegistration"]];
            
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
                
                [[NSUserDefaults standardUserDefaults]setValue:strVal forKey:@"user_id"];
                [[NSUserDefaults standardUserDefaults]synchronize];
                
                BeerDetails1ViewController *beerDetails=[[BeerDetails1ViewController alloc]init];
                beerDetails.strChkUser=@"2";
                beerDetails.ChkView=@"1";
                beerDetails.striD=strVal;
                [self.navigationController pushViewController:beerDetails animated:YES];
                
                _txtConfirmPassword.text=@"";
                _txtEmail.text=@"";
                _txtFirstName.text=@"";
                _txtPassword.text=@"";
                _txtUserName.text=@"";
                _txtZip.text=@"";
                _tztLastName.text=@"";
                
                return;
                
                UIAlertView *alert=[[UIAlertView alloc] initWithTitle:@"Welcome" message:@"Sucessfully Registered" delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
                [alert show];
                return;
                
            }
            else  if ([strVal integerValue]==-3)
            {
                [applicationDelegate hide_LoadingIndicator];
                UIAlertView *alert=[[UIAlertView alloc] initWithTitle:@"Sign Up" message:@"Username already exist." delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
                [alert show];
                
            }
            else  if ([strVal integerValue]==-1)
            {
                [applicationDelegate hide_LoadingIndicator];
                UIAlertView *alert=[[UIAlertView alloc] initWithTitle:@"Sign Up" message:@"Email already exists" delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
                [alert show];
                
            }
            else if ([strVal integerValue]==-2)
            {
                [applicationDelegate hide_LoadingIndicator];
                UIAlertView *alert=[[UIAlertView alloc] initWithTitle:@"Sign Up" message:@"Server error." delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
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

- (IBAction)btnInfo:(id)sender
{
    RegisterInfoViewController *registerView=[[RegisterInfoViewController alloc]init];
    [self.navigationController presentViewController:registerView animated:YES completion:nil];
}

- (IBAction)btnTermsAndConditions:(id)sender
{
    [[UIApplication sharedApplication] openURL:[NSURL URLWithString: @"http://www.netsmartz.net/portfolio/"]];
    return;
    Termd_ConditionsViewController *info=[[Termd_ConditionsViewController alloc]init];
    [self presentViewController:info animated:YES completion:nil];
}

- (IBAction)BtnCheck:(id)sender
{
    if (btnChkMark.imageView.image==[UIImage imageNamed:@"checked.png"])
    {
        [btnChkMark setImage:[UIImage imageNamed:@"unchecked.png"] forState:UIControlStateNormal];
        btnTerms=2;
    }
    else
    {
        [btnChkMark setImage:[UIImage imageNamed:@"checked.png"] forState:UIControlStateNormal];
        btnTerms=1;
    }
}

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
        NSLog(@"now on");
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
    // [_btnSelectBeer setTitle:@"" forState:UIControlStateNormal];
    txt=[arraySelectBeer objectAtIndex:row];
    /*
     if ([[UIScreen mainScreen]bounds].size.height==568) {
     self.view.frame=CGRectMake(0, 0, 320, 548);
     viewTextFields.frame=CGRectMake(35, 97, 250, 370);
     }
     else
     {
     self.view.frame=CGRectMake(0, 0, 320, 460);
     viewTextFields.frame=CGRectMake(35, 81, 250, 311);
     
     }
     viewBackground.hidden=YES;
     _PickerSelectBeer.hidden=YES;
     toolBar.hidden=YES;
     */
    //  accountSelected=row+1;
    // setLocation.text=[distance objectAtIndex:0];
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


- (void)viewDidUnload
{
    [self setViewBackground:nil];
    [self setViewTextFields:nil];
    [self setToolBar:nil];
    [self setPickerStyle:nil];
    [self setBtnChkMark:nil];
    [super viewDidUnload];
}

@end
