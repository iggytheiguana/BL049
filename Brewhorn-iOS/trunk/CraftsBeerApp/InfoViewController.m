//
//  InfoViewController.m
//  CraftsBeerApp
//
//  Created by Mandeep Singh on 16/08/13.
//  Copyright (c) 2013 Mandeep Singh. All rights reserved.
//

#import "InfoViewController.h"

@interface InfoViewController ()

@end

@implementation InfoViewController
@synthesize webView,scrlView;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}
#pragma mark - View for zooming
-(void)setContentSizeForScrollView
{
    scrlView.contentSize = CGSizeMake(400, 450);
}
- (void)scrollViewDidZoom:(UIScrollView *)scrollView
{
    UIView *subView = [scrollView.subviews objectAtIndex:0];
    
    CGFloat offsetX = (scrollView.bounds.size.width > scrollView.contentSize.width)?
    (scrollView.bounds.size.width - scrollView.contentSize.width) * 0.5 : 0.0;
    
    CGFloat offsetY = (scrollView.bounds.size.height > scrollView.contentSize.height)?
    (scrollView.bounds.size.height - scrollView.contentSize.height) * 0.5 : 0.0;
    
    subView.center = CGPointMake(scrollView.contentSize.width * 0.5 + offsetX,
                                 scrollView.contentSize.height * 0.5 + offsetY);
}
- (UIView *)viewForZoomingInScrollView:(UIScrollView *)scrollView
{
    return webView;
}
#pragma mark - View lifecycle
- (void)viewDidLoad
{
    //UIWebView *webView = [[UIWebView alloc] initWithFrame:CGRectMake(10, 10, 300, 200)];
    webView.backgroundColor=[UIColor clearColor];
    
    NSString *path = [[NSBundle mainBundle] pathForResource:@"BrewHorn Basic Instructions" ofType:@"pdf"];
    NSURL *targetURL = [NSURL fileURLWithPath:path];
    NSURLRequest *request = [NSURLRequest requestWithURL:targetURL];
    [webView loadRequest:request];
    scrlView.maximumZoomScale=3.0;
    scrlView.minimumZoomScale=1.0;
    scrlView.contentSize=CGSizeMake(320, webView.frame.size.height);
    
    //  [self.view addSubview:webView];
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)btnBack:(id)sender
{
    [self dismissViewControllerAnimated:YES completion:nil];
}
- (void)viewDidUnload {
    [self setWebView:nil];
    [self setScrlView:nil];
    [super viewDidUnload];
}
@end
